package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.auth.AuthSchemeProvider;
import cz.msebera.android.httpclient.client.AuthenticationStrategy;
import cz.msebera.android.httpclient.client.BackoffManager;
import cz.msebera.android.httpclient.client.ConnectionBackoffStrategy;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.CredentialsProvider;
import cz.msebera.android.httpclient.client.HttpRequestRetryHandler;
import cz.msebera.android.httpclient.client.RedirectStrategy;
import cz.msebera.android.httpclient.client.ServiceUnavailableRetryStrategy;
import cz.msebera.android.httpclient.client.UserTokenHandler;
import cz.msebera.android.httpclient.client.config.CookieSpecs;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.entity.InputStreamFactory;
import cz.msebera.android.httpclient.client.protocol.RequestAcceptEncoding;
import cz.msebera.android.httpclient.client.protocol.RequestAddCookies;
import cz.msebera.android.httpclient.client.protocol.RequestAuthCache;
import cz.msebera.android.httpclient.client.protocol.RequestClientConnControl;
import cz.msebera.android.httpclient.client.protocol.RequestDefaultHeaders;
import cz.msebera.android.httpclient.client.protocol.RequestExpectContinue;
import cz.msebera.android.httpclient.client.protocol.ResponseContentEncoding;
import cz.msebera.android.httpclient.client.protocol.ResponseProcessCookies;
import cz.msebera.android.httpclient.config.ConnectionConfig;
import cz.msebera.android.httpclient.config.Lookup;
import cz.msebera.android.httpclient.config.RegistryBuilder;
import cz.msebera.android.httpclient.config.SocketConfig;
import cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.SchemePortResolver;
import cz.msebera.android.httpclient.conn.routing.HttpRoutePlanner;
import cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.socket.LayeredConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.socket.PlainConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.DefaultHostnameVerifier;
import cz.msebera.android.httpclient.conn.ssl.SSLConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.X509HostnameVerifier;
import cz.msebera.android.httpclient.conn.util.PublicSuffixMatcher;
import cz.msebera.android.httpclient.conn.util.PublicSuffixMatcherLoader;
import cz.msebera.android.httpclient.cookie.CookieSpecProvider;
import cz.msebera.android.httpclient.impl.DefaultConnectionReuseStrategy;
import cz.msebera.android.httpclient.impl.NoConnectionReuseStrategy;
import cz.msebera.android.httpclient.impl.auth.BasicSchemeFactory;
import cz.msebera.android.httpclient.impl.auth.DigestSchemeFactory;
import cz.msebera.android.httpclient.impl.auth.NTLMSchemeFactory;
import cz.msebera.android.httpclient.impl.conn.DefaultProxyRoutePlanner;
import cz.msebera.android.httpclient.impl.conn.DefaultRoutePlanner;
import cz.msebera.android.httpclient.impl.conn.DefaultSchemePortResolver;
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager;
import cz.msebera.android.httpclient.impl.conn.SystemDefaultRoutePlanner;
import cz.msebera.android.httpclient.impl.cookie.DefaultCookieSpecProvider;
import cz.msebera.android.httpclient.impl.cookie.IgnoreSpecProvider;
import cz.msebera.android.httpclient.impl.cookie.NetscapeDraftSpecProvider;
import cz.msebera.android.httpclient.impl.cookie.RFC6265CookieSpecProvider;
import cz.msebera.android.httpclient.impl.execchain.BackoffStrategyExec;
import cz.msebera.android.httpclient.impl.execchain.ClientExecChain;
import cz.msebera.android.httpclient.impl.execchain.MainClientExec;
import cz.msebera.android.httpclient.impl.execchain.ProtocolExec;
import cz.msebera.android.httpclient.impl.execchain.RedirectExec;
import cz.msebera.android.httpclient.impl.execchain.RetryExec;
import cz.msebera.android.httpclient.impl.execchain.ServiceUnavailableRetryExec;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpProcessorBuilder;
import cz.msebera.android.httpclient.protocol.HttpRequestExecutor;
import cz.msebera.android.httpclient.protocol.ImmutableHttpProcessor;
import cz.msebera.android.httpclient.protocol.RequestContent;
import cz.msebera.android.httpclient.protocol.RequestTargetHost;
import cz.msebera.android.httpclient.protocol.RequestUserAgent;
import cz.msebera.android.httpclient.ssl.SSLContexts;
import cz.msebera.android.httpclient.util.TextUtils;
import cz.msebera.android.httpclient.util.VersionInfo;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

@NotThreadSafe
/* loaded from: classes.dex */
public class HttpClientBuilder {
    private boolean authCachingDisabled;
    private Lookup<AuthSchemeProvider> authSchemeRegistry;
    private boolean automaticRetriesDisabled;
    private BackoffManager backoffManager;
    private List<Closeable> closeables;
    private HttpClientConnectionManager connManager;
    private boolean connManagerShared;
    private ConnectionBackoffStrategy connectionBackoffStrategy;
    private boolean connectionStateDisabled;
    private boolean contentCompressionDisabled;
    private Map<String, InputStreamFactory> contentDecoderMap;
    private boolean cookieManagementDisabled;
    private Lookup<CookieSpecProvider> cookieSpecRegistry;
    private CookieStore cookieStore;
    private CredentialsProvider credentialsProvider;
    private ConnectionConfig defaultConnectionConfig;
    private Collection<? extends Header> defaultHeaders;
    private RequestConfig defaultRequestConfig;
    private SocketConfig defaultSocketConfig;
    private boolean evictExpiredConnections;
    private boolean evictIdleConnections;
    private HostnameVerifier hostnameVerifier;
    private HttpProcessor httpprocessor;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private long maxIdleTime;
    private TimeUnit maxIdleTimeUnit;
    private HttpHost proxy;
    private AuthenticationStrategy proxyAuthStrategy;
    private PublicSuffixMatcher publicSuffixMatcher;
    private boolean redirectHandlingDisabled;
    private RedirectStrategy redirectStrategy;
    private HttpRequestExecutor requestExec;
    private LinkedList<HttpRequestInterceptor> requestFirst;
    private LinkedList<HttpRequestInterceptor> requestLast;
    private LinkedList<HttpResponseInterceptor> responseFirst;
    private LinkedList<HttpResponseInterceptor> responseLast;
    private HttpRequestRetryHandler retryHandler;
    private ConnectionReuseStrategy reuseStrategy;
    private HttpRoutePlanner routePlanner;
    private SchemePortResolver schemePortResolver;
    private ServiceUnavailableRetryStrategy serviceUnavailStrategy;
    private LayeredConnectionSocketFactory sslSocketFactory;
    private SSLContext sslcontext;
    private boolean systemProperties;
    private AuthenticationStrategy targetAuthStrategy;
    private String userAgent;
    private UserTokenHandler userTokenHandler;
    private int maxConnTotal = 0;
    private int maxConnPerRoute = 0;
    private long connTimeToLive = -1;
    private TimeUnit connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;

    protected ClientExecChain decorateMainExec(ClientExecChain clientExecChain) {
        return clientExecChain;
    }

    protected ClientExecChain decorateProtocolExec(ClientExecChain clientExecChain) {
        return clientExecChain;
    }

    public static HttpClientBuilder create() {
        return new HttpClientBuilder();
    }

    public final HttpClientBuilder setRequestExecutor(HttpRequestExecutor httpRequestExecutor) {
        this.requestExec = httpRequestExecutor;
        return this;
    }

    @Deprecated
    public final HttpClientBuilder setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        this.hostnameVerifier = x509HostnameVerifier;
        return this;
    }

    public final HttpClientBuilder setSSLHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    public final HttpClientBuilder setPublicSuffixMatcher(PublicSuffixMatcher publicSuffixMatcher) {
        this.publicSuffixMatcher = publicSuffixMatcher;
        return this;
    }

    public final HttpClientBuilder setSslcontext(SSLContext sSLContext) {
        this.sslcontext = sSLContext;
        return this;
    }

    public final HttpClientBuilder setSSLSocketFactory(LayeredConnectionSocketFactory layeredConnectionSocketFactory) {
        this.sslSocketFactory = layeredConnectionSocketFactory;
        return this;
    }

    public final HttpClientBuilder setMaxConnTotal(int i) {
        this.maxConnTotal = i;
        return this;
    }

    public final HttpClientBuilder setMaxConnPerRoute(int i) {
        this.maxConnPerRoute = i;
        return this;
    }

    public final HttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig) {
        this.defaultSocketConfig = socketConfig;
        return this;
    }

    public final HttpClientBuilder setDefaultConnectionConfig(ConnectionConfig connectionConfig) {
        this.defaultConnectionConfig = connectionConfig;
        return this;
    }

    public final HttpClientBuilder setConnectionTimeToLive(long j, TimeUnit timeUnit) {
        this.connTimeToLive = j;
        this.connTimeToLiveTimeUnit = timeUnit;
        return this;
    }

    public final HttpClientBuilder setConnectionManager(HttpClientConnectionManager httpClientConnectionManager) {
        this.connManager = httpClientConnectionManager;
        return this;
    }

    public final HttpClientBuilder setConnectionManagerShared(boolean z) {
        this.connManagerShared = z;
        return this;
    }

    public final HttpClientBuilder setConnectionReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        this.reuseStrategy = connectionReuseStrategy;
        return this;
    }

    public final HttpClientBuilder setKeepAliveStrategy(ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        this.keepAliveStrategy = connectionKeepAliveStrategy;
        return this;
    }

    public final HttpClientBuilder setTargetAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.targetAuthStrategy = authenticationStrategy;
        return this;
    }

    public final HttpClientBuilder setProxyAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.proxyAuthStrategy = authenticationStrategy;
        return this;
    }

    public final HttpClientBuilder setUserTokenHandler(UserTokenHandler userTokenHandler) {
        this.userTokenHandler = userTokenHandler;
        return this;
    }

    public final HttpClientBuilder disableConnectionState() {
        this.connectionStateDisabled = true;
        return this;
    }

    public final HttpClientBuilder setSchemePortResolver(SchemePortResolver schemePortResolver) {
        this.schemePortResolver = schemePortResolver;
        return this;
    }

    public final HttpClientBuilder setUserAgent(String str) {
        this.userAgent = str;
        return this;
    }

    public final HttpClientBuilder setDefaultHeaders(Collection<? extends Header> collection) {
        this.defaultHeaders = collection;
        return this;
    }

    public final HttpClientBuilder addInterceptorFirst(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseFirst == null) {
            this.responseFirst = new LinkedList<>();
        }
        this.responseFirst.addFirst(httpResponseInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorLast(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseLast == null) {
            this.responseLast = new LinkedList<>();
        }
        this.responseLast.addLast(httpResponseInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorFirst(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestFirst == null) {
            this.requestFirst = new LinkedList<>();
        }
        this.requestFirst.addFirst(httpRequestInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorLast(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestLast == null) {
            this.requestLast = new LinkedList<>();
        }
        this.requestLast.addLast(httpRequestInterceptor);
        return this;
    }

    public final HttpClientBuilder disableCookieManagement() {
        this.cookieManagementDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableContentCompression() {
        this.contentCompressionDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableAuthCaching() {
        this.authCachingDisabled = true;
        return this;
    }

    public final HttpClientBuilder setHttpProcessor(HttpProcessor httpProcessor) {
        this.httpprocessor = httpProcessor;
        return this;
    }

    public final HttpClientBuilder setRetryHandler(HttpRequestRetryHandler httpRequestRetryHandler) {
        this.retryHandler = httpRequestRetryHandler;
        return this;
    }

    public final HttpClientBuilder disableAutomaticRetries() {
        this.automaticRetriesDisabled = true;
        return this;
    }

    public final HttpClientBuilder setProxy(HttpHost httpHost) {
        this.proxy = httpHost;
        return this;
    }

    public final HttpClientBuilder setRoutePlanner(HttpRoutePlanner httpRoutePlanner) {
        this.routePlanner = httpRoutePlanner;
        return this;
    }

    public final HttpClientBuilder setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
        return this;
    }

    public final HttpClientBuilder disableRedirectHandling() {
        this.redirectHandlingDisabled = true;
        return this;
    }

    public final HttpClientBuilder setConnectionBackoffStrategy(ConnectionBackoffStrategy connectionBackoffStrategy) {
        this.connectionBackoffStrategy = connectionBackoffStrategy;
        return this;
    }

    public final HttpClientBuilder setBackoffManager(BackoffManager backoffManager) {
        this.backoffManager = backoffManager;
        return this;
    }

    public final HttpClientBuilder setServiceUnavailableRetryStrategy(ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy) {
        this.serviceUnavailStrategy = serviceUnavailableRetryStrategy;
        return this;
    }

    public final HttpClientBuilder setDefaultCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        return this;
    }

    public final HttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
        return this;
    }

    public final HttpClientBuilder setDefaultAuthSchemeRegistry(Lookup<AuthSchemeProvider> lookup) {
        this.authSchemeRegistry = lookup;
        return this;
    }

    public final HttpClientBuilder setDefaultCookieSpecRegistry(Lookup<CookieSpecProvider> lookup) {
        this.cookieSpecRegistry = lookup;
        return this;
    }

    public final HttpClientBuilder setContentDecoderRegistry(Map<String, InputStreamFactory> map) {
        this.contentDecoderMap = map;
        return this;
    }

    public final HttpClientBuilder setDefaultRequestConfig(RequestConfig requestConfig) {
        this.defaultRequestConfig = requestConfig;
        return this;
    }

    public final HttpClientBuilder useSystemProperties() {
        this.systemProperties = true;
        return this;
    }

    public final HttpClientBuilder evictExpiredConnections() {
        this.evictExpiredConnections = true;
        return this;
    }

    public final HttpClientBuilder evictIdleConnections(Long l, TimeUnit timeUnit) {
        this.evictIdleConnections = true;
        this.maxIdleTime = l.longValue();
        this.maxIdleTimeUnit = timeUnit;
        return this;
    }

    protected ClientExecChain createMainExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpProcessor httpProcessor, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        return new MainClientExec(httpRequestExecutor, httpClientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpProcessor, authenticationStrategy, authenticationStrategy2, userTokenHandler);
    }

    public void addCloseable(Closeable closeable) {
        if (closeable != null) {
            if (this.closeables == null) {
                this.closeables = new ArrayList();
            }
            this.closeables.add(closeable);
        }
    }

    private static String[] split(String str) {
        if (TextUtils.isBlank(str)) {
            return null;
        }
        return str.split(" *, *");
    }

    public CloseableHttpClient build() {
        final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
        HttpRoutePlanner httpRoutePlanner;
        PublicSuffixMatcher publicSuffixMatcher = this.publicSuffixMatcher;
        if (publicSuffixMatcher == null) {
            publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();
        }
        HttpRequestExecutor httpRequestExecutor = this.requestExec;
        if (httpRequestExecutor == null) {
            httpRequestExecutor = new HttpRequestExecutor();
        }
        HttpClientConnectionManager httpClientConnectionManager = this.connManager;
        if (httpClientConnectionManager == null) {
            ConnectionSocketFactory connectionSocketFactory = this.sslSocketFactory;
            if (connectionSocketFactory == null) {
                String[] split = this.systemProperties ? split(System.getProperty("https.protocols")) : null;
                String[] split2 = this.systemProperties ? split(System.getProperty("https.cipherSuites")) : null;
                HostnameVerifier hostnameVerifier = this.hostnameVerifier;
                if (hostnameVerifier == null) {
                    hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
                }
                if (this.sslcontext != null) {
                    connectionSocketFactory = new SSLConnectionSocketFactory(this.sslcontext, split, split2, hostnameVerifier);
                } else if (this.systemProperties) {
                    connectionSocketFactory = new SSLConnectionSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault(), split, split2, hostnameVerifier);
                } else {
                    connectionSocketFactory = new SSLConnectionSocketFactory(SSLContexts.createDefault(), hostnameVerifier);
                }
            }
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager2 = new PoolingHttpClientConnectionManager(RegistryBuilder.create().register(HttpHost.DEFAULT_SCHEME_NAME, PlainConnectionSocketFactory.getSocketFactory()).register("https", connectionSocketFactory).build(), null, null, null, this.connTimeToLive, this.connTimeToLiveTimeUnit != null ? this.connTimeToLiveTimeUnit : TimeUnit.MILLISECONDS);
            if (this.defaultSocketConfig != null) {
                poolingHttpClientConnectionManager2.setDefaultSocketConfig(this.defaultSocketConfig);
            }
            if (this.defaultConnectionConfig != null) {
                poolingHttpClientConnectionManager2.setDefaultConnectionConfig(this.defaultConnectionConfig);
            }
            if (this.systemProperties && "true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true"))) {
                int parseInt = Integer.parseInt(System.getProperty("http.maxConnections", "5"));
                poolingHttpClientConnectionManager2.setDefaultMaxPerRoute(parseInt);
                poolingHttpClientConnectionManager2.setMaxTotal(parseInt * 2);
            }
            if (this.maxConnTotal > 0) {
                poolingHttpClientConnectionManager2.setMaxTotal(this.maxConnTotal);
            }
            if (this.maxConnPerRoute > 0) {
                poolingHttpClientConnectionManager2.setDefaultMaxPerRoute(this.maxConnPerRoute);
            }
            poolingHttpClientConnectionManager = poolingHttpClientConnectionManager2;
        } else {
            poolingHttpClientConnectionManager = httpClientConnectionManager;
        }
        ConnectionReuseStrategy connectionReuseStrategy = this.reuseStrategy;
        if (connectionReuseStrategy == null) {
            if (!this.systemProperties) {
                connectionReuseStrategy = DefaultConnectionReuseStrategy.INSTANCE;
            } else if ("true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true"))) {
                connectionReuseStrategy = DefaultConnectionReuseStrategy.INSTANCE;
            } else {
                connectionReuseStrategy = NoConnectionReuseStrategy.INSTANCE;
            }
        }
        ConnectionKeepAliveStrategy connectionKeepAliveStrategy = this.keepAliveStrategy;
        if (connectionKeepAliveStrategy == null) {
            connectionKeepAliveStrategy = DefaultConnectionKeepAliveStrategy.INSTANCE;
        }
        AuthenticationStrategy authenticationStrategy = this.targetAuthStrategy;
        if (authenticationStrategy == null) {
            authenticationStrategy = TargetAuthenticationStrategy.INSTANCE;
        }
        AuthenticationStrategy authenticationStrategy2 = this.proxyAuthStrategy;
        if (authenticationStrategy2 == null) {
            authenticationStrategy2 = ProxyAuthenticationStrategy.INSTANCE;
        }
        UserTokenHandler userTokenHandler = this.userTokenHandler;
        if (userTokenHandler == null) {
            if (!this.connectionStateDisabled) {
                userTokenHandler = DefaultUserTokenHandler.INSTANCE;
            } else {
                userTokenHandler = NoopUserTokenHandler.INSTANCE;
            }
        }
        String str = this.userAgent;
        if (str == null) {
            if (this.systemProperties) {
                str = System.getProperty("http.agent");
            }
            if (str == null) {
                str = VersionInfo.getUserAgent("Apache-HttpClient", "cz.msebera.android.httpclient.client", getClass());
            }
        }
        ClientExecChain decorateMainExec = decorateMainExec(createMainExec(httpRequestExecutor, poolingHttpClientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, new ImmutableHttpProcessor(new RequestTargetHost(), new RequestUserAgent(str)), authenticationStrategy, authenticationStrategy2, userTokenHandler));
        HttpProcessor httpProcessor = this.httpprocessor;
        if (httpProcessor == null) {
            HttpProcessorBuilder create = HttpProcessorBuilder.create();
            if (this.requestFirst != null) {
                Iterator<HttpRequestInterceptor> it = this.requestFirst.iterator();
                while (it.hasNext()) {
                    create.addFirst(it.next());
                }
            }
            if (this.responseFirst != null) {
                Iterator<HttpResponseInterceptor> it2 = this.responseFirst.iterator();
                while (it2.hasNext()) {
                    create.addFirst(it2.next());
                }
            }
            create.addAll(new RequestDefaultHeaders(this.defaultHeaders), new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(str), new RequestExpectContinue());
            if (!this.cookieManagementDisabled) {
                create.add(new RequestAddCookies());
            }
            if (!this.contentCompressionDisabled) {
                if (this.contentDecoderMap != null) {
                    ArrayList arrayList = new ArrayList(this.contentDecoderMap.keySet());
                    Collections.sort(arrayList);
                    create.add(new RequestAcceptEncoding(arrayList));
                } else {
                    create.add(new RequestAcceptEncoding());
                }
            }
            if (!this.authCachingDisabled) {
                create.add(new RequestAuthCache());
            }
            if (!this.cookieManagementDisabled) {
                create.add(new ResponseProcessCookies());
            }
            if (!this.contentCompressionDisabled) {
                if (this.contentDecoderMap != null) {
                    RegistryBuilder create2 = RegistryBuilder.create();
                    for (Map.Entry<String, InputStreamFactory> entry : this.contentDecoderMap.entrySet()) {
                        create2.register(entry.getKey(), entry.getValue());
                    }
                    create.add(new ResponseContentEncoding(create2.build()));
                } else {
                    create.add(new ResponseContentEncoding());
                }
            }
            if (this.requestLast != null) {
                Iterator<HttpRequestInterceptor> it3 = this.requestLast.iterator();
                while (it3.hasNext()) {
                    create.addLast(it3.next());
                }
            }
            if (this.responseLast != null) {
                Iterator<HttpResponseInterceptor> it4 = this.responseLast.iterator();
                while (it4.hasNext()) {
                    create.addLast(it4.next());
                }
            }
            httpProcessor = create.build();
        }
        RedirectExec decorateProtocolExec = decorateProtocolExec(new ProtocolExec(decorateMainExec, httpProcessor));
        if (!this.automaticRetriesDisabled) {
            HttpRequestRetryHandler httpRequestRetryHandler = this.retryHandler;
            if (httpRequestRetryHandler == null) {
                httpRequestRetryHandler = DefaultHttpRequestRetryHandler.INSTANCE;
            }
            decorateProtocolExec = new RetryExec(decorateProtocolExec, httpRequestRetryHandler);
        }
        HttpRoutePlanner httpRoutePlanner2 = this.routePlanner;
        if (httpRoutePlanner2 == null) {
            SchemePortResolver schemePortResolver = this.schemePortResolver;
            if (schemePortResolver == null) {
                schemePortResolver = DefaultSchemePortResolver.INSTANCE;
            }
            if (this.proxy != null) {
                httpRoutePlanner = new DefaultProxyRoutePlanner(this.proxy, schemePortResolver);
            } else if (this.systemProperties) {
                httpRoutePlanner = new SystemDefaultRoutePlanner(schemePortResolver, ProxySelector.getDefault());
            } else {
                httpRoutePlanner = new DefaultRoutePlanner(schemePortResolver);
            }
        } else {
            httpRoutePlanner = httpRoutePlanner2;
        }
        if (!this.redirectHandlingDisabled) {
            RedirectStrategy redirectStrategy = this.redirectStrategy;
            if (redirectStrategy == null) {
                redirectStrategy = DefaultRedirectStrategy.INSTANCE;
            }
            decorateProtocolExec = new RedirectExec(decorateProtocolExec, httpRoutePlanner, redirectStrategy);
        }
        ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy = this.serviceUnavailStrategy;
        if (serviceUnavailableRetryStrategy != null) {
            decorateProtocolExec = new ServiceUnavailableRetryExec(decorateProtocolExec, serviceUnavailableRetryStrategy);
        }
        BackoffStrategyExec backoffStrategyExec = (this.backoffManager == null || this.connectionBackoffStrategy == null) ? decorateProtocolExec : new BackoffStrategyExec(decorateProtocolExec, this.connectionBackoffStrategy, this.backoffManager);
        Lookup lookup = this.authSchemeRegistry;
        if (lookup == null) {
            lookup = RegistryBuilder.create().register("Basic", new BasicSchemeFactory()).register("Digest", new DigestSchemeFactory()).register("NTLM", new NTLMSchemeFactory()).build();
        }
        Lookup lookup2 = this.cookieSpecRegistry;
        if (lookup2 == null) {
            DefaultCookieSpecProvider defaultCookieSpecProvider = new DefaultCookieSpecProvider(publicSuffixMatcher);
            lookup2 = RegistryBuilder.create().register(CookieSpecs.DEFAULT, defaultCookieSpecProvider).register("best-match", defaultCookieSpecProvider).register("compatibility", defaultCookieSpecProvider).register(CookieSpecs.STANDARD, new RFC6265CookieSpecProvider(RFC6265CookieSpecProvider.CompatibilityLevel.RELAXED, publicSuffixMatcher)).register(CookieSpecs.STANDARD_STRICT, new RFC6265CookieSpecProvider(RFC6265CookieSpecProvider.CompatibilityLevel.STRICT, publicSuffixMatcher)).register("netscape", new NetscapeDraftSpecProvider()).register("ignoreCookies", new IgnoreSpecProvider()).build();
        }
        CookieStore cookieStore = this.cookieStore;
        if (cookieStore == null) {
            cookieStore = new BasicCookieStore();
        }
        CredentialsProvider credentialsProvider = this.credentialsProvider;
        if (credentialsProvider == null) {
            if (this.systemProperties) {
                credentialsProvider = new SystemDefaultCredentialsProvider();
            } else {
                credentialsProvider = new BasicCredentialsProvider();
            }
        }
        ArrayList arrayList2 = this.closeables != null ? new ArrayList(this.closeables) : null;
        if (!this.connManagerShared) {
            if (arrayList2 == null) {
                arrayList2 = new ArrayList(1);
            }
            if (this.evictExpiredConnections || this.evictIdleConnections) {
                final IdleConnectionEvictor idleConnectionEvictor = new IdleConnectionEvictor(poolingHttpClientConnectionManager, this.maxIdleTime > 0 ? this.maxIdleTime : 10L, this.maxIdleTimeUnit != null ? this.maxIdleTimeUnit : TimeUnit.SECONDS);
                arrayList2.add(new Closeable() { // from class: cz.msebera.android.httpclient.impl.client.HttpClientBuilder.1
                    @Override // java.io.Closeable, java.lang.AutoCloseable
                    public void close() throws IOException {
                        idleConnectionEvictor.shutdown();
                    }
                });
                idleConnectionEvictor.start();
            }
            arrayList2.add(new Closeable() { // from class: cz.msebera.android.httpclient.impl.client.HttpClientBuilder.2
                @Override // java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    poolingHttpClientConnectionManager.shutdown();
                }
            });
        }
        return new InternalHttpClient(backoffStrategyExec, poolingHttpClientConnectionManager, httpRoutePlanner, lookup2, lookup, cookieStore, credentialsProvider, this.defaultRequestConfig != null ? this.defaultRequestConfig : RequestConfig.DEFAULT, arrayList2);
    }
}
