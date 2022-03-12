package okhttp3.internal.http;

import cz.msebera.android.httpclient.HttpStatus;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpRetryException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http2.ConnectionShutdownException;

/* loaded from: classes.dex */
public final class RetryAndFollowUpInterceptor implements Interceptor {
    private static final int MAX_FOLLOW_UPS = 20;
    private Object callStackTrace;
    private volatile boolean canceled;
    private final OkHttpClient client;
    private final boolean forWebSocket;
    private StreamAllocation streamAllocation;

    public RetryAndFollowUpInterceptor(OkHttpClient okHttpClient, boolean z) {
        this.client = okHttpClient;
        this.forWebSocket = z;
    }

    public void cancel() {
        this.canceled = true;
        StreamAllocation streamAllocation = this.streamAllocation;
        if (streamAllocation != null) {
            streamAllocation.cancel();
        }
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public void setCallStackTrace(Object obj) {
        this.callStackTrace = obj;
    }

    public StreamAllocation streamAllocation() {
        return this.streamAllocation;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response build;
        Request followUpRequest;
        Request request = chain.request();
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        Call call = realInterceptorChain.call();
        EventListener eventListener = realInterceptorChain.eventListener();
        this.streamAllocation = new StreamAllocation(this.client.connectionPool(), createAddress(request.url()), call, eventListener, this.callStackTrace);
        Response response = null;
        int i = 0;
        while (!this.canceled) {
            try {
                try {
                    try {
                        Response proceed = realInterceptorChain.proceed(request, this.streamAllocation, null, null);
                        build = response != null ? proceed.newBuilder().priorResponse(response.newBuilder().body(null).build()).build() : proceed;
                        followUpRequest = followUpRequest(build);
                    } catch (IOException e) {
                        if (!recover(e, !(e instanceof ConnectionShutdownException), request)) {
                            throw e;
                        }
                    }
                } catch (RouteException e2) {
                    if (!recover(e2.getLastConnectException(), false, request)) {
                        throw e2.getLastConnectException();
                    }
                }
                if (followUpRequest == null) {
                    if (!this.forWebSocket) {
                        this.streamAllocation.release();
                    }
                    return build;
                }
                Util.closeQuietly(build.body());
                int i2 = i + 1;
                if (i2 > 20) {
                    this.streamAllocation.release();
                    throw new ProtocolException("Too many follow-up requests: " + i2);
                } else if (followUpRequest.body() instanceof UnrepeatableRequestBody) {
                    this.streamAllocation.release();
                    throw new HttpRetryException("Cannot retry streamed HTTP body", build.code());
                } else {
                    if (!sameConnection(build, followUpRequest.url())) {
                        this.streamAllocation.release();
                        this.streamAllocation = new StreamAllocation(this.client.connectionPool(), createAddress(followUpRequest.url()), call, eventListener, this.callStackTrace);
                    } else if (this.streamAllocation.codec() != null) {
                        throw new IllegalStateException("Closing the body of " + build + " didn't close its backing stream. Bad interceptor?");
                    }
                    response = build;
                    request = followUpRequest;
                    i = i2;
                }
            } catch (Throwable th) {
                this.streamAllocation.streamFailed(null);
                this.streamAllocation.release();
                throw th;
            }
        }
        this.streamAllocation.release();
        throw new IOException("Canceled");
    }

    private Address createAddress(HttpUrl httpUrl) {
        CertificatePinner certificatePinner;
        HostnameVerifier hostnameVerifier;
        SSLSocketFactory sSLSocketFactory = null;
        if (httpUrl.isHttps()) {
            sSLSocketFactory = this.client.sslSocketFactory();
            hostnameVerifier = this.client.hostnameVerifier();
            certificatePinner = this.client.certificatePinner();
        } else {
            hostnameVerifier = null;
            certificatePinner = null;
        }
        return new Address(httpUrl.host(), httpUrl.port(), this.client.dns(), this.client.socketFactory(), sSLSocketFactory, hostnameVerifier, certificatePinner, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
    }

    private boolean recover(IOException iOException, boolean z, Request request) {
        this.streamAllocation.streamFailed(iOException);
        if (!this.client.retryOnConnectionFailure()) {
            return false;
        }
        return (!z || !(request.body() instanceof UnrepeatableRequestBody)) && isRecoverable(iOException, z) && this.streamAllocation.hasMoreRoutes();
    }

    private boolean isRecoverable(IOException iOException, boolean z) {
        if (iOException instanceof ProtocolException) {
            return false;
        }
        return iOException instanceof InterruptedIOException ? (iOException instanceof SocketTimeoutException) && !z : (!(iOException instanceof SSLHandshakeException) || !(iOException.getCause() instanceof CertificateException)) && !(iOException instanceof SSLPeerUnverifiedException);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Request followUpRequest(Response response) throws IOException {
        String header;
        HttpUrl resolve;
        Proxy proxy;
        if (response == null) {
            throw new IllegalStateException();
        }
        RealConnection connection = this.streamAllocation.connection();
        RequestBody requestBody = null;
        Route route = connection != null ? connection.route() : null;
        int code = response.code();
        String method = response.request().method();
        switch (code) {
            case 300:
            case HttpStatus.SC_MOVED_PERMANENTLY /* 301 */:
            case HttpStatus.SC_MOVED_TEMPORARILY /* 302 */:
            case HttpStatus.SC_SEE_OTHER /* 303 */:
                break;
            case 307:
            case StatusLine.HTTP_PERM_REDIRECT /* 308 */:
                if (!method.equals("GET") && !method.equals("HEAD")) {
                    return null;
                }
                break;
            case HttpStatus.SC_UNAUTHORIZED /* 401 */:
                return this.client.authenticator().authenticate(route, response);
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /* 407 */:
                if (route != null) {
                    proxy = route.proxy();
                } else {
                    proxy = this.client.proxy();
                }
                if (proxy.type() == Proxy.Type.HTTP) {
                    return this.client.proxyAuthenticator().authenticate(route, response);
                }
                throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
                if (!this.client.retryOnConnectionFailure() || (response.request().body() instanceof UnrepeatableRequestBody)) {
                    return null;
                }
                if (response.priorResponse() == null || response.priorResponse().code() != 408) {
                    return response.request();
                }
                return null;
            default:
                return null;
        }
        if (!this.client.followRedirects() || (header = response.header("Location")) == null || (resolve = response.request().url().resolve(header)) == null) {
            return null;
        }
        if (!resolve.scheme().equals(response.request().url().scheme()) && !this.client.followSslRedirects()) {
            return null;
        }
        Request.Builder newBuilder = response.request().newBuilder();
        if (HttpMethod.permitsRequestBody(method)) {
            boolean redirectsWithBody = HttpMethod.redirectsWithBody(method);
            if (HttpMethod.redirectsToGet(method)) {
                newBuilder.method("GET", null);
            } else {
                if (redirectsWithBody) {
                    requestBody = response.request().body();
                }
                newBuilder.method(method, requestBody);
            }
            if (!redirectsWithBody) {
                newBuilder.removeHeader("Transfer-Encoding");
                newBuilder.removeHeader("Content-Length");
                newBuilder.removeHeader("Content-Type");
            }
        }
        if (!sameConnection(response, resolve)) {
            newBuilder.removeHeader("Authorization");
        }
        return newBuilder.url(resolve).build();
    }

    private boolean sameConnection(Response response, HttpUrl httpUrl) {
        HttpUrl url = response.request().url();
        return url.host().equals(httpUrl.host()) && url.port() == httpUrl.port() && url.scheme().equals(httpUrl.scheme());
    }
}
