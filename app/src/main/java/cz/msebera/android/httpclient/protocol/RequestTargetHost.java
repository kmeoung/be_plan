package cz.msebera.android.httpclient.protocol;

import cz.msebera.android.httpclient.HttpConnection;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpInetConnection;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.net.InetAddress;

@Immutable
/* loaded from: classes.dex */
public class RequestTargetHost implements HttpRequestInterceptor {
    @Override // cz.msebera.android.httpclient.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        HttpCoreContext adapt = HttpCoreContext.adapt(httpContext);
        ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
        if ((!httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT") || !protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) && !httpRequest.containsHeader("Host")) {
            HttpHost targetHost = adapt.getTargetHost();
            if (targetHost == null) {
                HttpConnection connection = adapt.getConnection();
                if (connection instanceof HttpInetConnection) {
                    HttpInetConnection httpInetConnection = (HttpInetConnection) connection;
                    InetAddress remoteAddress = httpInetConnection.getRemoteAddress();
                    int remotePort = httpInetConnection.getRemotePort();
                    if (remoteAddress != null) {
                        targetHost = new HttpHost(remoteAddress.getHostName(), remotePort);
                    }
                }
                if (targetHost == null) {
                    if (!protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                        throw new ProtocolException("Target host missing");
                    }
                    return;
                }
            }
            httpRequest.addHeader("Host", targetHost.toHostString());
        }
    }
}
