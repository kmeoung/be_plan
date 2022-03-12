package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.protocol.RequestClientConnControl;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpRequestExecutor;
import cz.msebera.android.httpclient.protocol.ImmutableHttpProcessor;
import cz.msebera.android.httpclient.protocol.RequestContent;
import cz.msebera.android.httpclient.protocol.RequestTargetHost;
import cz.msebera.android.httpclient.protocol.RequestUserAgent;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.VersionInfo;
import java.net.URI;
import java.net.URISyntaxException;

@Immutable
/* loaded from: classes.dex */
public class MinimalClientExec implements ClientExecChain {
    private final HttpClientConnectionManager connManager;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final HttpRequestExecutor requestExecutor;
    private final ConnectionReuseStrategy reuseStrategy;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    private final HttpProcessor httpProcessor = new ImmutableHttpProcessor(new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(VersionInfo.getUserAgent("Apache-HttpClient", "cz.msebera.android.httpclient.client", getClass())));

    public MinimalClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        Args.notNull(httpRequestExecutor, "HTTP request executor");
        Args.notNull(httpClientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        this.requestExecutor = httpRequestExecutor;
        this.connManager = httpClientConnectionManager;
        this.reuseStrategy = connectionReuseStrategy;
        this.keepAliveStrategy = connectionKeepAliveStrategy;
    }

    static void rewriteRequestURI(HttpRequestWrapper httpRequestWrapper, HttpRoute httpRoute) throws ProtocolException {
        URI uri;
        try {
            URI uri2 = httpRequestWrapper.getURI();
            if (uri2 != null) {
                if (uri2.isAbsolute()) {
                    uri = URIUtils.rewriteURI(uri2, null, true);
                } else {
                    uri = URIUtils.rewriteURI(uri2);
                }
                httpRequestWrapper.setURI(uri);
            }
        } catch (URISyntaxException e) {
            throw new ProtocolException("Invalid URI: " + httpRequestWrapper.getRequestLine().getUri(), e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00b9 A[Catch: RuntimeException -> 0x0063, IOException -> 0x0066, HttpException -> 0x0069, ConnectionShutdownException -> 0x006c, TryCatch #3 {HttpException -> 0x0069, ConnectionShutdownException -> 0x006c, IOException -> 0x0066, RuntimeException -> 0x0063, blocks: (B:17:0x004e, B:19:0x0054, B:20:0x005e, B:21:0x005f, B:30:0x006f, B:32:0x0075, B:36:0x007f, B:37:0x0087, B:39:0x008d, B:40:0x0090, B:42:0x0098, B:44:0x00a4, B:47:0x00b9, B:48:0x00bd, B:50:0x00e9, B:51:0x00f8, B:52:0x00fb, B:54:0x0101, B:57:0x0108, B:59:0x010e), top: B:80:0x004e }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00e9 A[Catch: RuntimeException -> 0x0063, IOException -> 0x0066, HttpException -> 0x0069, ConnectionShutdownException -> 0x006c, TryCatch #3 {HttpException -> 0x0069, ConnectionShutdownException -> 0x006c, IOException -> 0x0066, RuntimeException -> 0x0063, blocks: (B:17:0x004e, B:19:0x0054, B:20:0x005e, B:21:0x005f, B:30:0x006f, B:32:0x0075, B:36:0x007f, B:37:0x0087, B:39:0x008d, B:40:0x0090, B:42:0x0098, B:44:0x00a4, B:47:0x00b9, B:48:0x00bd, B:50:0x00e9, B:51:0x00f8, B:52:0x00fb, B:54:0x0101, B:57:0x0108, B:59:0x010e), top: B:80:0x004e }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00f8 A[Catch: RuntimeException -> 0x0063, IOException -> 0x0066, HttpException -> 0x0069, ConnectionShutdownException -> 0x006c, TryCatch #3 {HttpException -> 0x0069, ConnectionShutdownException -> 0x006c, IOException -> 0x0066, RuntimeException -> 0x0063, blocks: (B:17:0x004e, B:19:0x0054, B:20:0x005e, B:21:0x005f, B:30:0x006f, B:32:0x0075, B:36:0x007f, B:37:0x0087, B:39:0x008d, B:40:0x0090, B:42:0x0098, B:44:0x00a4, B:47:0x00b9, B:48:0x00bd, B:50:0x00e9, B:51:0x00f8, B:52:0x00fb, B:54:0x0101, B:57:0x0108, B:59:0x010e), top: B:80:0x004e }] */
    @Override // cz.msebera.android.httpclient.impl.execchain.ClientExecChain
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public cz.msebera.android.httpclient.client.methods.CloseableHttpResponse execute(cz.msebera.android.httpclient.conn.routing.HttpRoute r7, cz.msebera.android.httpclient.client.methods.HttpRequestWrapper r8, cz.msebera.android.httpclient.client.protocol.HttpClientContext r9, cz.msebera.android.httpclient.client.methods.HttpExecutionAware r10) throws java.io.IOException, cz.msebera.android.httpclient.HttpException {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.execchain.MinimalClientExec.execute(cz.msebera.android.httpclient.conn.routing.HttpRoute, cz.msebera.android.httpclient.client.methods.HttpRequestWrapper, cz.msebera.android.httpclient.client.protocol.HttpClientContext, cz.msebera.android.httpclient.client.methods.HttpExecutionAware):cz.msebera.android.httpclient.client.methods.CloseableHttpResponse");
    }
}
