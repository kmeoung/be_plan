package cz.msebera.android.httpclient.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;

@Deprecated
/* loaded from: classes.dex */
public interface HostNameResolver {
    InetAddress resolve(String str) throws IOException;
}
