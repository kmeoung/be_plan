package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.protocol.RequestAcceptEncoding;
import cz.msebera.android.httpclient.client.protocol.ResponseContentEncoding;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.conn.ClientConnectionManager;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.IOException;

@Deprecated
/* loaded from: classes.dex */
public class DecompressingHttpClient implements HttpClient {
    private final HttpRequestInterceptor acceptEncodingInterceptor;
    private final HttpClient backend;
    private final HttpResponseInterceptor contentEncodingInterceptor;

    public DecompressingHttpClient() {
        this(new DefaultHttpClient());
    }

    public DecompressingHttpClient(HttpClient httpClient) {
        this(httpClient, new RequestAcceptEncoding(), new ResponseContentEncoding());
    }

    DecompressingHttpClient(HttpClient httpClient, HttpRequestInterceptor httpRequestInterceptor, HttpResponseInterceptor httpResponseInterceptor) {
        this.backend = httpClient;
        this.acceptEncodingInterceptor = httpRequestInterceptor;
        this.contentEncodingInterceptor = httpResponseInterceptor;
    }

    @Override // cz.msebera.android.httpclient.client.HttpClient
    public HttpParams getParams() {
        return this.backend.getParams();
    }

    @Override // cz.msebera.android.httpclient.client.HttpClient
    public ClientConnectionManager getConnectionManager() {
        return this.backend.getConnectionManager();
    }

    @Override // cz.msebera.android.httpclient.client.HttpClient
    public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException {
        return execute(getHttpHost(httpUriRequest), httpUriRequest, (HttpContext) null);
    }

    public HttpClient getHttpClient() {
        return this.backend;
    }

    HttpHost getHttpHost(HttpUriRequest httpUriRequest) {
        return URIUtils.extractHost(httpUriRequest.getURI());
    }

    @Override // cz.msebera.android.httpclient.client.HttpClient
    public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        return execute(getHttpHost(httpUriRequest), httpUriRequest, httpContext);
    }

    @Override // cz.msebera.android.httpclient.client.HttpClient
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException, ClientProtocolException {
        return execute(httpHost, httpRequest, (HttpContext) null);
    }

    @Override // cz.msebera.android.httpclient.client.HttpClient
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        HttpRequest httpRequest2;
        if (httpContext == null) {
            try {
                httpContext = new BasicHttpContext();
            } catch (HttpException e) {
                throw new ClientProtocolException(e);
            }
        }
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            httpRequest2 = new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest) httpRequest);
        } else {
            httpRequest2 = new RequestWrapper(httpRequest);
        }
        this.acceptEncodingInterceptor.process(httpRequest2, httpContext);
        HttpResponse execute = this.backend.execute(httpHost, httpRequest2, httpContext);
        try {
            try {
                this.contentEncodingInterceptor.process(execute, httpContext);
                if (Boolean.TRUE.equals(httpContext.getAttribute(ResponseContentEncoding.UNCOMPRESSED))) {
                    execute.removeHeaders("Content-Length");
                    execute.removeHeaders("Content-Encoding");
                    execute.removeHeaders(HttpHeaders.CONTENT_MD5);
                }
                return execute;
            } catch (HttpException e2) {
                EntityUtils.consume(execute.getEntity());
                throw e2;
            }
        } catch (IOException e3) {
            EntityUtils.consume(execute.getEntity());
            throw e3;
        } catch (RuntimeException e4) {
            EntityUtils.consume(execute.getEntity());
            throw e4;
        }
    }

    @Override // cz.msebera.android.httpclient.client.HttpClient
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return (T) execute(getHttpHost(httpUriRequest), httpUriRequest, responseHandler);
    }

    @Override // cz.msebera.android.httpclient.client.HttpClient
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException {
        return (T) execute(getHttpHost(httpUriRequest), httpUriRequest, responseHandler, httpContext);
    }

    @Override // cz.msebera.android.httpclient.client.HttpClient
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return (T) execute(httpHost, httpRequest, responseHandler, null);
    }

    @Override // cz.msebera.android.httpclient.client.HttpClient
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException {
        HttpResponse execute = execute(httpHost, httpRequest, httpContext);
        try {
            return (T) responseHandler.handleResponse(execute);
        } finally {
            HttpEntity entity = execute.getEntity();
            if (entity != null) {
                EntityUtils.consume(entity);
            }
        }
    }
}
