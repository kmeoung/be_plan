package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.HttpConnection;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Deprecated
/* loaded from: classes.dex */
public class IdleConnectionHandler {
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    private final Map<HttpConnection, TimeValues> connectionToTimes = new HashMap();

    public void add(HttpConnection httpConnection, long j, TimeUnit timeUnit) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            httpClientAndroidLog.debug("Adding connection at: " + currentTimeMillis);
        }
        this.connectionToTimes.put(httpConnection, new TimeValues(currentTimeMillis, j, timeUnit));
    }

    public boolean remove(HttpConnection httpConnection) {
        TimeValues remove = this.connectionToTimes.remove(httpConnection);
        if (remove != null) {
            return System.currentTimeMillis() <= remove.timeExpires;
        }
        this.log.warn("Removing a connection that never existed!");
        return true;
    }

    public void removeAll() {
        this.connectionToTimes.clear();
    }

    public void closeIdleConnections(long j) {
        long currentTimeMillis = System.currentTimeMillis() - j;
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            httpClientAndroidLog.debug("Checking for connections, idle timeout: " + currentTimeMillis);
        }
        for (Map.Entry<HttpConnection, TimeValues> entry : this.connectionToTimes.entrySet()) {
            HttpConnection key = entry.getKey();
            long j2 = entry.getValue().timeAdded;
            if (j2 <= currentTimeMillis) {
                if (this.log.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog2 = this.log;
                    httpClientAndroidLog2.debug("Closing idle connection, connection time: " + j2);
                }
                try {
                    key.close();
                } catch (IOException e) {
                    this.log.debug("I/O error closing connection", e);
                }
            }
        }
    }

    public void closeExpiredConnections() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            httpClientAndroidLog.debug("Checking for expired connections, now: " + currentTimeMillis);
        }
        for (Map.Entry<HttpConnection, TimeValues> entry : this.connectionToTimes.entrySet()) {
            HttpConnection key = entry.getKey();
            TimeValues value = entry.getValue();
            if (value.timeExpires <= currentTimeMillis) {
                if (this.log.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog2 = this.log;
                    httpClientAndroidLog2.debug("Closing connection, expired @: " + value.timeExpires);
                }
                try {
                    key.close();
                } catch (IOException e) {
                    this.log.debug("I/O error closing connection", e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class TimeValues {
        private final long timeAdded;
        private final long timeExpires;

        TimeValues(long j, long j2, TimeUnit timeUnit) {
            this.timeAdded = j;
            if (j2 > 0) {
                this.timeExpires = j + timeUnit.toMillis(j2);
            } else {
                this.timeExpires = Long.MAX_VALUE;
            }
        }
    }
}
