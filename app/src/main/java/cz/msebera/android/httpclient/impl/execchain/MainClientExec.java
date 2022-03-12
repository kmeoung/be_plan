package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthProtocolState;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.client.AuthenticationStrategy;
import cz.msebera.android.httpclient.client.NonRepeatableRequestException;
import cz.msebera.android.httpclient.client.UserTokenHandler;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy;
import cz.msebera.android.httpclient.conn.ConnectionRequest;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.routing.BasicRouteDirector;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.HttpRouteDirector;
import cz.msebera.android.httpclient.conn.routing.RouteTracker;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.auth.HttpAuthenticator;
import cz.msebera.android.httpclient.impl.conn.ConnectionShutdownException;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpRequestExecutor;
import cz.msebera.android.httpclient.protocol.ImmutableHttpProcessor;
import cz.msebera.android.httpclient.protocol.RequestTargetHost;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Immutable
/* loaded from: classes.dex */
public class MainClientExec implements ClientExecChain {
    private final HttpAuthenticator authenticator;
    private final HttpClientConnectionManager connManager;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    public HttpClientAndroidLog log;
    private final AuthenticationStrategy proxyAuthStrategy;
    private final HttpProcessor proxyHttpProcessor;
    private final HttpRequestExecutor requestExecutor;
    private final ConnectionReuseStrategy reuseStrategy;
    private final HttpRouteDirector routeDirector;
    private final AuthenticationStrategy targetAuthStrategy;
    private final UserTokenHandler userTokenHandler;

    public MainClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpProcessor httpProcessor, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        this.log = new HttpClientAndroidLog(getClass());
        Args.notNull(httpRequestExecutor, "HTTP request executor");
        Args.notNull(httpClientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        Args.notNull(httpProcessor, "Proxy HTTP processor");
        Args.notNull(authenticationStrategy, "Target authentication strategy");
        Args.notNull(authenticationStrategy2, "Proxy authentication strategy");
        Args.notNull(userTokenHandler, "User token handler");
        this.authenticator = new HttpAuthenticator();
        this.routeDirector = new BasicRouteDirector();
        this.requestExecutor = httpRequestExecutor;
        this.connManager = httpClientConnectionManager;
        this.reuseStrategy = connectionReuseStrategy;
        this.keepAliveStrategy = connectionKeepAliveStrategy;
        this.proxyHttpProcessor = httpProcessor;
        this.targetAuthStrategy = authenticationStrategy;
        this.proxyAuthStrategy = authenticationStrategy2;
        this.userTokenHandler = userTokenHandler;
    }

    public MainClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        this(httpRequestExecutor, httpClientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, new ImmutableHttpProcessor(new RequestTargetHost()), authenticationStrategy, authenticationStrategy2, userTokenHandler);
    }

    @Override // cz.msebera.android.httpclient.impl.execchain.ClientExecChain
    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        ConnectionHolder connectionHolder;
        ConnectionHolder connectionHolder2;
        Object obj;
        int i;
        HttpClientConnection httpClientConnection;
        HttpResponse httpResponse;
        String str;
        Object obj2;
        HttpRoute httpRoute2 = httpRoute;
        HttpExecutionAware httpExecutionAware2 = httpExecutionAware;
        Args.notNull(httpRoute2, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        AuthState targetAuthState = httpClientContext.getTargetAuthState();
        if (targetAuthState == null) {
            targetAuthState = new AuthState();
            httpClientContext.setAttribute("http.auth.target-scope", targetAuthState);
        }
        AuthState proxyAuthState = httpClientContext.getProxyAuthState();
        if (proxyAuthState == null) {
            proxyAuthState = new AuthState();
            httpClientContext.setAttribute("http.auth.proxy-scope", proxyAuthState);
        }
        if (httpRequestWrapper instanceof HttpEntityEnclosingRequest) {
            RequestEntityProxy.enhance((HttpEntityEnclosingRequest) httpRequestWrapper);
        }
        Object userToken = httpClientContext.getUserToken();
        ConnectionRequest requestConnection = this.connManager.requestConnection(httpRoute2, userToken);
        if (httpExecutionAware2 != null) {
            if (httpExecutionAware.isAborted()) {
                requestConnection.cancel();
                throw new RequestAbortedException("Request aborted");
            }
            httpExecutionAware2.setCancellable(requestConnection);
        }
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        try {
            int connectionRequestTimeout = requestConfig.getConnectionRequestTimeout();
            HttpClientConnection httpClientConnection2 = requestConnection.get(connectionRequestTimeout > 0 ? connectionRequestTimeout : 0L, TimeUnit.MILLISECONDS);
            httpClientContext.setAttribute("http.connection", httpClientConnection2);
            if (requestConfig.isStaleConnectionCheckEnabled() && httpClientConnection2.isOpen()) {
                this.log.debug("Stale connection check");
                if (httpClientConnection2.isStale()) {
                    this.log.debug("Stale connection detected");
                    httpClientConnection2.close();
                }
            }
            ConnectionHolder connectionHolder3 = new ConnectionHolder(this.log, this.connManager, httpClientConnection2);
            if (httpExecutionAware2 != null) {
                try {
                    try {
                        httpExecutionAware2.setCancellable(connectionHolder3);
                    } catch (ConnectionShutdownException e) {
                        InterruptedIOException interruptedIOException = new InterruptedIOException("Connection has been shut down");
                        interruptedIOException.initCause(e);
                        throw interruptedIOException;
                    }
                } catch (HttpException e2) {
                    e = e2;
                    connectionHolder = connectionHolder3;
                    connectionHolder.abortConnection();
                    throw e;
                } catch (IOException e3) {
                    e = e3;
                    connectionHolder = connectionHolder3;
                    connectionHolder.abortConnection();
                    throw e;
                } catch (RuntimeException e4) {
                    e = e4;
                    connectionHolder = connectionHolder3;
                    connectionHolder.abortConnection();
                    throw e;
                }
            }
            int i2 = 1;
            int i3 = 1;
            while (true) {
                if (i3 > i2 && !RequestEntityProxy.isRepeatable(httpRequestWrapper)) {
                    throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
                } else if (httpExecutionAware2 == null || !httpExecutionAware.isAborted()) {
                    try {
                        if (!httpClientConnection2.isOpen()) {
                            this.log.debug("Opening connection " + httpRoute2);
                            i = i3;
                            obj = userToken;
                            connectionHolder2 = connectionHolder3;
                            httpClientConnection = httpClientConnection2;
                            try {
                                try {
                                    establishRoute(proxyAuthState, httpClientConnection2, httpRoute2, httpRequestWrapper, httpClientContext);
                                } catch (TunnelRefusedException e5) {
                                    if (this.log.isDebugEnabled()) {
                                        this.log.debug(e5.getMessage());
                                    }
                                    httpResponse = e5.getResponse();
                                    connectionHolder = connectionHolder2;
                                    if (obj == null) {
                                        obj2 = this.userTokenHandler.getUserToken(httpClientContext);
                                        httpClientContext.setAttribute("http.user-token", obj2);
                                    } else {
                                        obj2 = obj;
                                    }
                                    if (obj2 != null) {
                                        connectionHolder.setState(obj2);
                                    }
                                    HttpEntity entity = httpResponse.getEntity();
                                    if (entity != null && entity.isStreaming()) {
                                        return new HttpResponseProxy(httpResponse, connectionHolder);
                                    }
                                    connectionHolder.releaseConnection();
                                    return new HttpResponseProxy(httpResponse, null);
                                }
                            } catch (HttpException e6) {
                                e = e6;
                                connectionHolder = connectionHolder2;
                                connectionHolder.abortConnection();
                                throw e;
                            } catch (IOException e7) {
                                e = e7;
                                connectionHolder = connectionHolder2;
                                connectionHolder.abortConnection();
                                throw e;
                            } catch (RuntimeException e8) {
                                e = e8;
                                connectionHolder = connectionHolder2;
                                connectionHolder.abortConnection();
                                throw e;
                            }
                        } else {
                            i = i3;
                            connectionHolder2 = connectionHolder3;
                            obj = userToken;
                            httpClientConnection = httpClientConnection2;
                        }
                        try {
                            int socketTimeout = requestConfig.getSocketTimeout();
                            if (socketTimeout >= 0) {
                                httpClientConnection.setSocketTimeout(socketTimeout);
                            }
                            if (httpExecutionAware2 == null || !httpExecutionAware.isAborted()) {
                                if (this.log.isDebugEnabled()) {
                                    this.log.debug("Executing request " + httpRequestWrapper.getRequestLine());
                                }
                                if (!httpRequestWrapper.containsHeader("Authorization")) {
                                    if (this.log.isDebugEnabled()) {
                                        this.log.debug("Target auth state: " + targetAuthState.getState());
                                    }
                                    this.authenticator.generateAuthResponse(httpRequestWrapper, targetAuthState, httpClientContext);
                                }
                                if (!httpRequestWrapper.containsHeader("Proxy-Authorization") && !httpRoute.isTunnelled()) {
                                    if (this.log.isDebugEnabled()) {
                                        this.log.debug("Proxy auth state: " + proxyAuthState.getState());
                                    }
                                    this.authenticator.generateAuthResponse(httpRequestWrapper, proxyAuthState, httpClientContext);
                                }
                                HttpResponse execute = this.requestExecutor.execute(httpRequestWrapper, httpClientConnection, httpClientContext);
                                if (this.reuseStrategy.keepAlive(execute, httpClientContext)) {
                                    long keepAliveDuration = this.keepAliveStrategy.getKeepAliveDuration(execute, httpClientContext);
                                    if (this.log.isDebugEnabled()) {
                                        if (keepAliveDuration > 0) {
                                            str = "for " + keepAliveDuration + " " + TimeUnit.MILLISECONDS;
                                        } else {
                                            str = "indefinitely";
                                        }
                                        this.log.debug("Connection can be kept alive " + str);
                                    }
                                    connectionHolder = connectionHolder2;
                                    try {
                                        connectionHolder.setValidFor(keepAliveDuration, TimeUnit.MILLISECONDS);
                                        connectionHolder.markReusable();
                                    } catch (HttpException e9) {
                                        e = e9;
                                        connectionHolder.abortConnection();
                                        throw e;
                                    } catch (IOException e10) {
                                        e = e10;
                                        connectionHolder.abortConnection();
                                        throw e;
                                    } catch (RuntimeException e11) {
                                        e = e11;
                                        connectionHolder.abortConnection();
                                        throw e;
                                    }
                                } else {
                                    connectionHolder = connectionHolder2;
                                    connectionHolder.markNonReusable();
                                }
                                if (!needAuthentication(targetAuthState, proxyAuthState, httpRoute2, execute, httpClientContext)) {
                                    httpResponse = execute;
                                    break;
                                }
                                HttpEntity entity2 = execute.getEntity();
                                if (connectionHolder.isReusable()) {
                                    EntityUtils.consume(entity2);
                                } else {
                                    httpClientConnection.close();
                                    if (proxyAuthState.getState() == AuthProtocolState.SUCCESS && proxyAuthState.getAuthScheme() != null && proxyAuthState.getAuthScheme().isConnectionBased()) {
                                        this.log.debug("Resetting proxy auth state");
                                        proxyAuthState.reset();
                                    }
                                    if (targetAuthState.getState() == AuthProtocolState.SUCCESS && targetAuthState.getAuthScheme() != null && targetAuthState.getAuthScheme().isConnectionBased()) {
                                        this.log.debug("Resetting target auth state");
                                        targetAuthState.reset();
                                    }
                                }
                                HttpRequest original = httpRequestWrapper.getOriginal();
                                if (!original.containsHeader("Authorization")) {
                                    httpRequestWrapper.removeHeaders("Authorization");
                                }
                                if (!original.containsHeader("Proxy-Authorization")) {
                                    httpRequestWrapper.removeHeaders("Proxy-Authorization");
                                }
                                i3 = i + 1;
                                connectionHolder3 = connectionHolder;
                                httpClientConnection2 = httpClientConnection;
                                userToken = obj;
                                i2 = 1;
                                httpRoute2 = httpRoute;
                                httpExecutionAware2 = httpExecutionAware;
                            } else {
                                throw new RequestAbortedException("Request aborted");
                            }
                        } catch (HttpException e12) {
                            e = e12;
                            connectionHolder = connectionHolder2;
                        } catch (IOException e13) {
                            e = e13;
                            connectionHolder = connectionHolder2;
                        } catch (RuntimeException e14) {
                            e = e14;
                            connectionHolder = connectionHolder2;
                        }
                    } catch (HttpException e15) {
                        e = e15;
                        connectionHolder = connectionHolder3;
                    } catch (IOException e16) {
                        e = e16;
                        connectionHolder = connectionHolder3;
                    } catch (RuntimeException e17) {
                        e = e17;
                        connectionHolder = connectionHolder3;
                    }
                } else {
                    throw new RequestAbortedException("Request aborted");
                }
            }
        } catch (InterruptedException e18) {
            Thread.currentThread().interrupt();
            throw new RequestAbortedException("Request aborted", e18);
        } catch (ExecutionException e19) {
            e = e19;
            Throwable cause = e.getCause();
            if (cause != null) {
                e = cause;
            }
            throw new RequestAbortedException("Request execution failed", e);
        }
    }

    void establishRoute(AuthState authState, HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpRequest httpRequest, HttpClientContext httpClientContext) throws HttpException, IOException {
        int nextStep;
        int connectTimeout = httpClientContext.getRequestConfig().getConnectTimeout();
        RouteTracker routeTracker = new RouteTracker(httpRoute);
        do {
            HttpRoute route = routeTracker.toRoute();
            nextStep = this.routeDirector.nextStep(httpRoute, route);
            int i = 0;
            switch (nextStep) {
                case -1:
                    throw new HttpException("Unable to establish route: planned = " + httpRoute + "; current = " + route);
                case 0:
                    this.connManager.routeComplete(httpClientConnection, httpRoute, httpClientContext);
                    continue;
                case 1:
                    HttpClientConnectionManager httpClientConnectionManager = this.connManager;
                    if (connectTimeout > 0) {
                        i = connectTimeout;
                    }
                    httpClientConnectionManager.connect(httpClientConnection, httpRoute, i, httpClientContext);
                    routeTracker.connectTarget(httpRoute.isSecure());
                    continue;
                case 2:
                    this.connManager.connect(httpClientConnection, httpRoute, connectTimeout > 0 ? connectTimeout : 0, httpClientContext);
                    routeTracker.connectProxy(httpRoute.getProxyHost(), false);
                    continue;
                case 3:
                    boolean createTunnelToTarget = createTunnelToTarget(authState, httpClientConnection, httpRoute, httpRequest, httpClientContext);
                    this.log.debug("Tunnel to target created.");
                    routeTracker.tunnelTarget(createTunnelToTarget);
                    continue;
                case 4:
                    int hopCount = route.getHopCount() - 1;
                    boolean createTunnelToProxy = createTunnelToProxy(httpRoute, hopCount, httpClientContext);
                    this.log.debug("Tunnel to proxy created.");
                    routeTracker.tunnelProxy(httpRoute.getHopTarget(hopCount), createTunnelToProxy);
                    continue;
                case 5:
                    this.connManager.upgrade(httpClientConnection, httpRoute, httpClientContext);
                    routeTracker.layerProtocol(httpRoute.isSecure());
                    continue;
                default:
                    throw new IllegalStateException("Unknown step indicator " + nextStep + " from RouteDirector.");
            }
        } while (nextStep > 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00ad, code lost:
        if (r17.reuseStrategy.keepAlive(r7, r22) == false) goto L_0x00bf;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00af, code lost:
        r17.log.debug("Connection kept alive");
        cz.msebera.android.httpclient.util.EntityUtils.consume(r7.getEntity());
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00bf, code lost:
        r19.close();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean createTunnelToTarget(cz.msebera.android.httpclient.auth.AuthState r18, cz.msebera.android.httpclient.HttpClientConnection r19, cz.msebera.android.httpclient.conn.routing.HttpRoute r20, cz.msebera.android.httpclient.HttpRequest r21, cz.msebera.android.httpclient.client.protocol.HttpClientContext r22) throws cz.msebera.android.httpclient.HttpException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.execchain.MainClientExec.createTunnelToTarget(cz.msebera.android.httpclient.auth.AuthState, cz.msebera.android.httpclient.HttpClientConnection, cz.msebera.android.httpclient.conn.routing.HttpRoute, cz.msebera.android.httpclient.HttpRequest, cz.msebera.android.httpclient.client.protocol.HttpClientContext):boolean");
    }

    private boolean createTunnelToProxy(HttpRoute httpRoute, int i, HttpClientContext httpClientContext) throws HttpException {
        throw new HttpException("Proxy chains are not supported.");
    }

    private boolean needAuthentication(AuthState authState, AuthState authState2, HttpRoute httpRoute, HttpResponse httpResponse, HttpClientContext httpClientContext) {
        if (!httpClientContext.getRequestConfig().isAuthenticationEnabled()) {
            return false;
        }
        HttpHost targetHost = httpClientContext.getTargetHost();
        if (targetHost == null) {
            targetHost = httpRoute.getTargetHost();
        }
        if (targetHost.getPort() < 0) {
            targetHost = new HttpHost(targetHost.getHostName(), httpRoute.getTargetHost().getPort(), targetHost.getSchemeName());
        }
        boolean isAuthenticationRequested = this.authenticator.isAuthenticationRequested(targetHost, httpResponse, this.targetAuthStrategy, authState, httpClientContext);
        HttpHost proxyHost = httpRoute.getProxyHost();
        if (proxyHost == null) {
            proxyHost = httpRoute.getTargetHost();
        }
        boolean isAuthenticationRequested2 = this.authenticator.isAuthenticationRequested(proxyHost, httpResponse, this.proxyAuthStrategy, authState2, httpClientContext);
        if (isAuthenticationRequested) {
            return this.authenticator.handleAuthChallenge(targetHost, httpResponse, this.targetAuthStrategy, authState, httpClientContext);
        }
        if (isAuthenticationRequested2) {
            return this.authenticator.handleAuthChallenge(proxyHost, httpResponse, this.proxyAuthStrategy, authState2, httpClientContext);
        }
        return false;
    }
}
