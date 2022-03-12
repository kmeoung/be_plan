package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthScope;
import cz.msebera.android.httpclient.auth.UsernamePasswordCredentials;
import cz.msebera.android.httpclient.client.CredentialsProvider;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.client.BasicCredentialsProvider;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Immutable
/* loaded from: classes.dex */
public class ProtocolExec implements ClientExecChain {
    private final HttpProcessor httpProcessor;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    private final ClientExecChain requestExecutor;

    public ProtocolExec(ClientExecChain clientExecChain, HttpProcessor httpProcessor) {
        Args.notNull(clientExecChain, "HTTP client request executor");
        Args.notNull(httpProcessor, "HTTP protocol processor");
        this.requestExecutor = clientExecChain;
        this.httpProcessor = httpProcessor;
    }

    void rewriteRequestURI(HttpRequestWrapper httpRequestWrapper, HttpRoute httpRoute) throws ProtocolException {
        URI uri = httpRequestWrapper.getURI();
        if (uri != null) {
            try {
                httpRequestWrapper.setURI(URIUtils.rewriteURIForRoute(uri, httpRoute));
            } catch (URISyntaxException e) {
                throw new ProtocolException("Invalid URI: " + uri, e);
            }
        }
    }

    @Override // cz.msebera.android.httpclient.impl.execchain.ClientExecChain
    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        URI uri;
        String userInfo;
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        HttpRequest original = httpRequestWrapper.getOriginal();
        HttpHost httpHost = null;
        if (original instanceof HttpUriRequest) {
            uri = ((HttpUriRequest) original).getURI();
        } else {
            String uri2 = original.getRequestLine().getUri();
            try {
                uri = URI.create(uri2);
            } catch (IllegalArgumentException e) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Unable to parse '" + uri2 + "' as a valid URI; request URI and Host header may be inconsistent", e);
                }
                uri = null;
            }
        }
        httpRequestWrapper.setURI(uri);
        rewriteRequestURI(httpRequestWrapper, httpRoute);
        HttpHost httpHost2 = (HttpHost) httpRequestWrapper.getParams().getParameter(ClientPNames.VIRTUAL_HOST);
        if (httpHost2 != null && httpHost2.getPort() == -1) {
            int port = httpRoute.getTargetHost().getPort();
            if (port != -1) {
                httpHost2 = new HttpHost(httpHost2.getHostName(), port, httpHost2.getSchemeName());
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Using virtual host" + httpHost2);
            }
        }
        if (httpHost2 != null) {
            httpHost = httpHost2;
        } else if (!(uri == null || !uri.isAbsolute() || uri.getHost() == null)) {
            httpHost = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
        }
        if (httpHost == null) {
            httpHost = httpRequestWrapper.getTarget();
        }
        if (httpHost == null) {
            httpHost = httpRoute.getTargetHost();
        }
        if (!(uri == null || (userInfo = uri.getUserInfo()) == null)) {
            CredentialsProvider credentialsProvider = httpClientContext.getCredentialsProvider();
            if (credentialsProvider == null) {
                credentialsProvider = new BasicCredentialsProvider();
                httpClientContext.setCredentialsProvider(credentialsProvider);
            }
            credentialsProvider.setCredentials(new AuthScope(httpHost), new UsernamePasswordCredentials(userInfo));
        }
        httpClientContext.setAttribute("http.target_host", httpHost);
        httpClientContext.setAttribute("http.route", httpRoute);
        httpClientContext.setAttribute("http.request", httpRequestWrapper);
        this.httpProcessor.process(httpRequestWrapper, httpClientContext);
        CloseableHttpResponse execute = this.requestExecutor.execute(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
        try {
            httpClientContext.setAttribute("http.response", execute);
            this.httpProcessor.process(execute, httpClientContext);
            return execute;
        } catch (HttpException e2) {
            execute.close();
            throw e2;
        } catch (IOException e3) {
            execute.close();
            throw e3;
        } catch (RuntimeException e4) {
            execute.close();
            throw e4;
        }
    }
}
