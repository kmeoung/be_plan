package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.conn.OperatedClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.RouteTracker;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.pool.PoolEntry;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Deprecated
/* loaded from: classes.dex */
class HttpPoolEntry extends PoolEntry<HttpRoute, OperatedClientConnection> {
    public HttpClientAndroidLog log;
    private final RouteTracker tracker;

    public HttpPoolEntry(HttpClientAndroidLog httpClientAndroidLog, String str, HttpRoute httpRoute, OperatedClientConnection operatedClientConnection, long j, TimeUnit timeUnit) {
        super(str, httpRoute, operatedClientConnection, j, timeUnit);
        this.log = httpClientAndroidLog;
        this.tracker = new RouteTracker(httpRoute);
    }

    @Override // cz.msebera.android.httpclient.pool.PoolEntry
    public boolean isExpired(long j) {
        boolean isExpired = super.isExpired(j);
        if (isExpired && this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            httpClientAndroidLog.debug("Connection " + this + " expired @ " + new Date(getExpiry()));
        }
        return isExpired;
    }

    public RouteTracker getTracker() {
        return this.tracker;
    }

    public HttpRoute getPlannedRoute() {
        return getRoute();
    }

    public HttpRoute getEffectiveRoute() {
        return this.tracker.toRoute();
    }

    @Override // cz.msebera.android.httpclient.pool.PoolEntry
    public boolean isClosed() {
        return !getConnection().isOpen();
    }

    @Override // cz.msebera.android.httpclient.pool.PoolEntry
    public void close() {
        try {
            getConnection().close();
        } catch (IOException e) {
            this.log.debug("I/O error closing connection", e);
        }
    }
}
