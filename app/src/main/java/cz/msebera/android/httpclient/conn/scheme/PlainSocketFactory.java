package cz.msebera.android.httpclient.conn.scheme;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

@Immutable
@Deprecated
/* loaded from: classes.dex */
public class PlainSocketFactory implements SocketFactory, SchemeSocketFactory {
    private final HostNameResolver nameResolver;

    @Override // cz.msebera.android.httpclient.conn.scheme.SocketFactory
    public final boolean isSecure(Socket socket) {
        return false;
    }

    public static PlainSocketFactory getSocketFactory() {
        return new PlainSocketFactory();
    }

    @Deprecated
    public PlainSocketFactory(HostNameResolver hostNameResolver) {
        this.nameResolver = hostNameResolver;
    }

    public PlainSocketFactory() {
        this.nameResolver = null;
    }

    @Override // cz.msebera.android.httpclient.conn.scheme.SchemeSocketFactory
    public Socket createSocket(HttpParams httpParams) {
        return new Socket();
    }

    @Override // cz.msebera.android.httpclient.conn.scheme.SocketFactory
    public Socket createSocket() {
        return new Socket();
    }

    @Override // cz.msebera.android.httpclient.conn.scheme.SchemeSocketFactory
    public Socket connectSocket(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams) throws IOException, ConnectTimeoutException {
        Args.notNull(inetSocketAddress, "Remote address");
        Args.notNull(httpParams, "HTTP parameters");
        if (socket == null) {
            socket = createSocket();
        }
        if (inetSocketAddress2 != null) {
            socket.setReuseAddress(HttpConnectionParams.getSoReuseaddr(httpParams));
            socket.bind(inetSocketAddress2);
        }
        int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
        try {
            socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
            socket.connect(inetSocketAddress, connectionTimeout);
            return socket;
        } catch (SocketTimeoutException unused) {
            throw new ConnectTimeoutException("Connect to " + inetSocketAddress + " timed out");
        }
    }

    @Override // cz.msebera.android.httpclient.conn.scheme.SocketFactory
    @Deprecated
    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        InetSocketAddress inetSocketAddress;
        InetAddress inetAddress2;
        if (inetAddress != null || i2 > 0) {
            if (i2 <= 0) {
                i2 = 0;
            }
            inetSocketAddress = new InetSocketAddress(inetAddress, i2);
        } else {
            inetSocketAddress = null;
        }
        if (this.nameResolver != null) {
            inetAddress2 = this.nameResolver.resolve(str);
        } else {
            inetAddress2 = InetAddress.getByName(str);
        }
        return connectSocket(socket, new InetSocketAddress(inetAddress2, i), inetSocketAddress, httpParams);
    }
}
