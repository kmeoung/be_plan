package cz.msebera.android.httpclient.conn;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.Immutable;
import java.io.InterruptedIOException;

@Immutable
/* loaded from: classes.dex */
public class ConnectTimeoutException extends InterruptedIOException {
    private static final long serialVersionUID = -4816682903149535989L;
    private final HttpHost host;

    public ConnectTimeoutException() {
        this.host = null;
    }

    public ConnectTimeoutException(String str) {
        super(str);
        this.host = null;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ConnectTimeoutException(java.io.IOException r4, cz.msebera.android.httpclient.HttpHost r5, java.net.InetAddress... r6) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Connect to "
            r0.append(r1)
            if (r5 == 0) goto L_0x0011
            java.lang.String r1 = r5.toHostString()
            goto L_0x0013
        L_0x0011:
            java.lang.String r1 = "remote host"
        L_0x0013:
            r0.append(r1)
            if (r6 == 0) goto L_0x0031
            int r1 = r6.length
            if (r1 <= 0) goto L_0x0031
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = " "
            r1.append(r2)
            java.util.List r6 = java.util.Arrays.asList(r6)
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            goto L_0x0033
        L_0x0031:
            java.lang.String r6 = ""
        L_0x0033:
            r0.append(r6)
            if (r4 == 0) goto L_0x0054
            java.lang.String r6 = r4.getMessage()
            if (r6 == 0) goto L_0x0054
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r1 = " failed: "
            r6.append(r1)
            java.lang.String r1 = r4.getMessage()
            r6.append(r1)
            java.lang.String r6 = r6.toString()
            goto L_0x0056
        L_0x0054:
            java.lang.String r6 = " timed out"
        L_0x0056:
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            r3.<init>(r6)
            r3.host = r5
            r3.initCause(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.conn.ConnectTimeoutException.<init>(java.io.IOException, cz.msebera.android.httpclient.HttpHost, java.net.InetAddress[]):void");
    }

    public HttpHost getHost() {
        return this.host;
    }
}
