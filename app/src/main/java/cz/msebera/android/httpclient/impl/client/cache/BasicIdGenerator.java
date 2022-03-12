package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.GuardedBy;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Formatter;
import java.util.Locale;

@ThreadSafe
/* loaded from: classes.dex */
class BasicIdGenerator {
    @GuardedBy("this")
    private long count;
    private final String hostname;
    private final SecureRandom rnd;

    public BasicIdGenerator() {
        String str;
        try {
            str = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException unused) {
            str = "localhost";
        }
        this.hostname = str;
        try {
            this.rnd = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        }
    }

    public synchronized void generate(StringBuilder sb) {
        this.count++;
        int nextInt = this.rnd.nextInt();
        sb.append(System.currentTimeMillis());
        sb.append('.');
        Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format("%1$016x-%2$08x", Long.valueOf(this.count), Integer.valueOf(nextInt));
        formatter.close();
        sb.append('.');
        sb.append(this.hostname);
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();
        generate(sb);
        return sb.toString();
    }
}
