package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.conn.ManagedHttpClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.pool.AbstractConnPool;
import cz.msebera.android.httpclient.pool.ConnFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: Access modifiers changed from: package-private */
@ThreadSafe
/* loaded from: classes.dex */
public class CPool extends AbstractConnPool<HttpRoute, ManagedHttpClientConnection, CPoolEntry> {
    private static final AtomicLong COUNTER = new AtomicLong();
    public HttpClientAndroidLog log = new HttpClientAndroidLog(CPool.class);
    private final long timeToLive;
    private final TimeUnit tunit;

    public CPool(ConnFactory<HttpRoute, ManagedHttpClientConnection> connFactory, int i, int i2, long j, TimeUnit timeUnit) {
        super(connFactory, i, i2);
        this.timeToLive = j;
        this.tunit = timeUnit;
    }

    public CPoolEntry createEntry(HttpRoute httpRoute, ManagedHttpClientConnection managedHttpClientConnection) {
        return new CPoolEntry(this.log, Long.toString(COUNTER.getAndIncrement()), httpRoute, managedHttpClientConnection, this.timeToLive, this.tunit);
    }

    public boolean validate(CPoolEntry cPoolEntry) {
        return !cPoolEntry.getConnection().isStale();
    }
}
