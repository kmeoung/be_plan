package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.auth.AuthScheme;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.client.RedirectException;
import cz.msebera.android.httpclient.client.RedirectStrategy;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.HttpRoutePlanner;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@ThreadSafe
/* loaded from: classes.dex */
public class RedirectExec implements ClientExecChain {
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    private final RedirectStrategy redirectStrategy;
    private final ClientExecChain requestExecutor;
    private final HttpRoutePlanner routePlanner;

    public RedirectExec(ClientExecChain clientExecChain, HttpRoutePlanner httpRoutePlanner, RedirectStrategy redirectStrategy) {
        Args.notNull(clientExecChain, "HTTP client request executor");
        Args.notNull(httpRoutePlanner, "HTTP route planner");
        Args.notNull(redirectStrategy, "HTTP redirect strategy");
        this.requestExecutor = clientExecChain;
        this.routePlanner = httpRoutePlanner;
        this.redirectStrategy = redirectStrategy;
    }

    @Override // cz.msebera.android.httpclient.impl.execchain.ClientExecChain
    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        CloseableHttpResponse execute;
        AuthScheme authScheme;
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        List<URI> redirectLocations = httpClientContext.getRedirectLocations();
        if (redirectLocations != null) {
            redirectLocations.clear();
        }
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        int maxRedirects = requestConfig.getMaxRedirects() > 0 ? requestConfig.getMaxRedirects() : 50;
        HttpRequestWrapper httpRequestWrapper2 = httpRequestWrapper;
        int i = 0;
        while (true) {
            execute = this.requestExecutor.execute(httpRoute, httpRequestWrapper2, httpClientContext, httpExecutionAware);
            try {
                if (!requestConfig.isRedirectsEnabled() || !this.redirectStrategy.isRedirected(httpRequestWrapper2, execute, httpClientContext)) {
                    break;
                } else if (i >= maxRedirects) {
                    throw new RedirectException("Maximum redirects (" + maxRedirects + ") exceeded");
                } else {
                    i++;
                    HttpUriRequest redirect = this.redirectStrategy.getRedirect(httpRequestWrapper2, execute, httpClientContext);
                    if (!redirect.headerIterator().hasNext()) {
                        redirect.setHeaders(httpRequestWrapper.getOriginal().getAllHeaders());
                    }
                    httpRequestWrapper2 = HttpRequestWrapper.wrap(redirect);
                    if (httpRequestWrapper2 instanceof HttpEntityEnclosingRequest) {
                        RequestEntityProxy.enhance((HttpEntityEnclosingRequest) httpRequestWrapper2);
                    }
                    URI uri = httpRequestWrapper2.getURI();
                    HttpHost extractHost = URIUtils.extractHost(uri);
                    if (extractHost == null) {
                        throw new ProtocolException("Redirect URI does not specify a valid host name: " + uri);
                    }
                    if (!httpRoute.getTargetHost().equals(extractHost)) {
                        AuthState targetAuthState = httpClientContext.getTargetAuthState();
                        if (targetAuthState != null) {
                            this.log.debug("Resetting target auth state");
                            targetAuthState.reset();
                        }
                        AuthState proxyAuthState = httpClientContext.getProxyAuthState();
                        if (!(proxyAuthState == null || (authScheme = proxyAuthState.getAuthScheme()) == null || !authScheme.isConnectionBased())) {
                            this.log.debug("Resetting proxy auth state");
                            proxyAuthState.reset();
                        }
                    }
                    httpRoute = this.routePlanner.determineRoute(extractHost, httpRequestWrapper2, httpClientContext);
                    if (this.log.isDebugEnabled()) {
                        HttpClientAndroidLog httpClientAndroidLog = this.log;
                        httpClientAndroidLog.debug("Redirecting to '" + uri + "' via " + httpRoute);
                    }
                    EntityUtils.consume(execute.getEntity());
                }
            } catch (HttpException e) {
                try {
                    try {
                        EntityUtils.consume(execute.getEntity());
                    } catch (IOException e2) {
                        this.log.debug("I/O error while releasing connection", e2);
                        throw e;
                    }
                    throw e;
                } finally {
                    execute.close();
                }
            } catch (IOException e3) {
                throw e3;
            } catch (RuntimeException e4) {
                throw e4;
            }
        }
        return execute;
    }
}
