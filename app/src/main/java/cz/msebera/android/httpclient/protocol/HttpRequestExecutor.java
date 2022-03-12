package cz.msebera.android.httpclient.protocol;

import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;

@Immutable
/* loaded from: classes.dex */
public class HttpRequestExecutor {
    public static final int DEFAULT_WAIT_FOR_CONTINUE = 3000;
    private final int waitForContinue;

    public HttpRequestExecutor(int i) {
        this.waitForContinue = Args.positive(i, "Wait for continue time");
    }

    public HttpRequestExecutor() {
        this(3000);
    }

    protected boolean canResponseHaveBody(HttpRequest httpRequest, HttpResponse httpResponse) {
        int statusCode;
        return ("HEAD".equalsIgnoreCase(httpRequest.getRequestLine().getMethod()) || (statusCode = httpResponse.getStatusLine().getStatusCode()) < 200 || statusCode == 204 || statusCode == 304 || statusCode == 205) ? false : true;
    }

    public HttpResponse execute(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext httpContext) throws IOException, HttpException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpClientConnection, "Client connection");
        Args.notNull(httpContext, "HTTP context");
        try {
            HttpResponse doSendRequest = doSendRequest(httpRequest, httpClientConnection, httpContext);
            return doSendRequest == null ? doReceiveResponse(httpRequest, httpClientConnection, httpContext) : doSendRequest;
        } catch (HttpException e) {
            closeConnection(httpClientConnection);
            throw e;
        } catch (IOException e2) {
            closeConnection(httpClientConnection);
            throw e2;
        } catch (RuntimeException e3) {
            closeConnection(httpClientConnection);
            throw e3;
        }
    }

    private static void closeConnection(HttpClientConnection httpClientConnection) {
        try {
            httpClientConnection.close();
        } catch (IOException unused) {
        }
    }

    public void preProcess(HttpRequest httpRequest, HttpProcessor httpProcessor, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpProcessor, "HTTP processor");
        Args.notNull(httpContext, "HTTP context");
        httpContext.setAttribute("http.request", httpRequest);
        httpProcessor.process(httpRequest, httpContext);
    }

    protected HttpResponse doSendRequest(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext httpContext) throws IOException, HttpException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpClientConnection, "Client connection");
        Args.notNull(httpContext, "HTTP context");
        httpContext.setAttribute("http.connection", httpClientConnection);
        httpContext.setAttribute("http.request_sent", Boolean.FALSE);
        httpClientConnection.sendRequestHeader(httpRequest);
        HttpResponse httpResponse = null;
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            boolean z = true;
            ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpRequest;
            if (httpEntityEnclosingRequest.expectContinue() && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                httpClientConnection.flush();
                if (httpClientConnection.isResponseAvailable(this.waitForContinue)) {
                    HttpResponse receiveResponseHeader = httpClientConnection.receiveResponseHeader();
                    if (canResponseHaveBody(httpRequest, receiveResponseHeader)) {
                        httpClientConnection.receiveResponseEntity(receiveResponseHeader);
                    }
                    int statusCode = receiveResponseHeader.getStatusLine().getStatusCode();
                    if (statusCode >= 200) {
                        z = false;
                        httpResponse = receiveResponseHeader;
                    } else if (statusCode != 100) {
                        throw new ProtocolException("Unexpected response: " + receiveResponseHeader.getStatusLine());
                    }
                }
            }
            if (z) {
                httpClientConnection.sendRequestEntity(httpEntityEnclosingRequest);
            }
        }
        httpClientConnection.flush();
        httpContext.setAttribute("http.request_sent", Boolean.TRUE);
        return httpResponse;
    }

    protected HttpResponse doReceiveResponse(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpClientConnection, "Client connection");
        Args.notNull(httpContext, "HTTP context");
        HttpResponse httpResponse = null;
        int i = 0;
        while (true) {
            if (httpResponse != null && i >= 200) {
                return httpResponse;
            }
            httpResponse = httpClientConnection.receiveResponseHeader();
            if (canResponseHaveBody(httpRequest, httpResponse)) {
                httpClientConnection.receiveResponseEntity(httpResponse);
            }
            i = httpResponse.getStatusLine().getStatusCode();
        }
    }

    public void postProcess(HttpResponse httpResponse, HttpProcessor httpProcessor, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        Args.notNull(httpProcessor, "HTTP processor");
        Args.notNull(httpContext, "HTTP context");
        httpContext.setAttribute("http.response", httpResponse);
        httpProcessor.process(httpResponse, httpContext);
    }
}
