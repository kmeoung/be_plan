package cz.msebera.android.httpclient.impl.conn.tsccm;

import cz.msebera.android.httpclient.conn.ClientConnectionOperator;
import cz.msebera.android.httpclient.conn.ConnectionPoolTimeoutException;
import cz.msebera.android.httpclient.conn.OperatedClientConnection;
import cz.msebera.android.httpclient.conn.params.ConnManagerParams;
import cz.msebera.android.httpclient.conn.params.ConnPerRoute;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Deprecated
/* loaded from: classes.dex */
public class ConnPoolByRoute extends AbstractConnPool {
    protected final ConnPerRoute connPerRoute;
    private final long connTTL;
    private final TimeUnit connTTLTimeUnit;
    protected final Queue<BasicPoolEntry> freeConnections;
    protected final Set<BasicPoolEntry> leasedConnections;
    public HttpClientAndroidLog log;
    protected volatile int maxTotalConnections;
    protected volatile int numConnections;
    protected final ClientConnectionOperator operator;
    private final Lock poolLock;
    protected final Map<HttpRoute, RouteSpecificPool> routeToPool;
    protected volatile boolean shutdown;
    protected final Queue<WaitingThread> waitingThreads;

    public ConnPoolByRoute(ClientConnectionOperator clientConnectionOperator, ConnPerRoute connPerRoute, int i) {
        this(clientConnectionOperator, connPerRoute, i, -1L, TimeUnit.MILLISECONDS);
    }

    public ConnPoolByRoute(ClientConnectionOperator clientConnectionOperator, ConnPerRoute connPerRoute, int i, long j, TimeUnit timeUnit) {
        this.log = new HttpClientAndroidLog(getClass());
        Args.notNull(clientConnectionOperator, "Connection operator");
        Args.notNull(connPerRoute, "Connections per route");
        this.poolLock = super.poolLock;
        this.leasedConnections = super.leasedConnections;
        this.operator = clientConnectionOperator;
        this.connPerRoute = connPerRoute;
        this.maxTotalConnections = i;
        this.freeConnections = createFreeConnQueue();
        this.waitingThreads = createWaitingThreadQueue();
        this.routeToPool = createRouteToPoolMap();
        this.connTTL = j;
        this.connTTLTimeUnit = timeUnit;
    }

    protected Lock getLock() {
        return this.poolLock;
    }

    @Deprecated
    public ConnPoolByRoute(ClientConnectionOperator clientConnectionOperator, HttpParams httpParams) {
        this(clientConnectionOperator, ConnManagerParams.getMaxConnectionsPerRoute(httpParams), ConnManagerParams.getMaxTotalConnections(httpParams));
    }

    protected Queue<BasicPoolEntry> createFreeConnQueue() {
        return new LinkedList();
    }

    protected Queue<WaitingThread> createWaitingThreadQueue() {
        return new LinkedList();
    }

    protected Map<HttpRoute, RouteSpecificPool> createRouteToPoolMap() {
        return new HashMap();
    }

    protected RouteSpecificPool newRouteSpecificPool(HttpRoute httpRoute) {
        return new RouteSpecificPool(httpRoute, this.connPerRoute);
    }

    protected WaitingThread newWaitingThread(Condition condition, RouteSpecificPool routeSpecificPool) {
        return new WaitingThread(condition, routeSpecificPool);
    }

    private void closeConnection(BasicPoolEntry basicPoolEntry) {
        OperatedClientConnection connection = basicPoolEntry.getConnection();
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                this.log.debug("I/O error closing connection", e);
            }
        }
    }

    protected RouteSpecificPool getRoutePool(HttpRoute httpRoute, boolean z) {
        this.poolLock.lock();
        try {
            RouteSpecificPool routeSpecificPool = this.routeToPool.get(httpRoute);
            if (routeSpecificPool == null && z) {
                routeSpecificPool = newRouteSpecificPool(httpRoute);
                this.routeToPool.put(httpRoute, routeSpecificPool);
            }
            return routeSpecificPool;
        } finally {
            this.poolLock.unlock();
        }
    }

    public int getConnectionsInPool(HttpRoute httpRoute) {
        this.poolLock.lock();
        int i = 0;
        try {
            RouteSpecificPool routePool = getRoutePool(httpRoute, false);
            if (routePool != null) {
                i = routePool.getEntryCount();
            }
            return i;
        } finally {
            this.poolLock.unlock();
        }
    }

    public int getConnectionsInPool() {
        this.poolLock.lock();
        try {
            return this.numConnections;
        } finally {
            this.poolLock.unlock();
        }
    }

    @Override // cz.msebera.android.httpclient.impl.conn.tsccm.AbstractConnPool
    public PoolEntryRequest requestPoolEntry(final HttpRoute httpRoute, final Object obj) {
        final WaitingThreadAborter waitingThreadAborter = new WaitingThreadAborter();
        return new PoolEntryRequest() { // from class: cz.msebera.android.httpclient.impl.conn.tsccm.ConnPoolByRoute.1
            @Override // cz.msebera.android.httpclient.impl.conn.tsccm.PoolEntryRequest
            public void abortRequest() {
                ConnPoolByRoute.this.poolLock.lock();
                try {
                    waitingThreadAborter.abort();
                } finally {
                    ConnPoolByRoute.this.poolLock.unlock();
                }
            }

            @Override // cz.msebera.android.httpclient.impl.conn.tsccm.PoolEntryRequest
            public BasicPoolEntry getPoolEntry(long j, TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
                return ConnPoolByRoute.this.getEntryBlocking(httpRoute, obj, j, timeUnit, waitingThreadAborter);
            }
        };
    }

    protected BasicPoolEntry getEntryBlocking(HttpRoute httpRoute, Object obj, long j, TimeUnit timeUnit, WaitingThreadAborter waitingThreadAborter) throws ConnectionPoolTimeoutException, InterruptedException {
        BasicPoolEntry basicPoolEntry = null;
        Date date = j > 0 ? new Date(System.currentTimeMillis() + timeUnit.toMillis(j)) : null;
        this.poolLock.lock();
        try {
            RouteSpecificPool routePool = getRoutePool(httpRoute, true);
            WaitingThread waitingThread = null;
            while (basicPoolEntry == null) {
                Asserts.check(!this.shutdown, "Connection pool shut down");
                if (this.log.isDebugEnabled()) {
                    this.log.debug("[" + httpRoute + "] total kept alive: " + this.freeConnections.size() + ", total issued: " + this.leasedConnections.size() + ", total allocated: " + this.numConnections + " out of " + this.maxTotalConnections);
                }
                basicPoolEntry = getFreeEntry(routePool, obj);
                if (basicPoolEntry != null) {
                    break;
                }
                boolean z = routePool.getCapacity() > 0;
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Available capacity: " + routePool.getCapacity() + " out of " + routePool.getMaxEntries() + " [" + httpRoute + "][" + obj + "]");
                }
                if (z && this.numConnections < this.maxTotalConnections) {
                    basicPoolEntry = createEntry(routePool, this.operator);
                } else if (!z || this.freeConnections.isEmpty()) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Need to wait for connection [" + httpRoute + "][" + obj + "]");
                    }
                    if (waitingThread == null) {
                        waitingThread = newWaitingThread(this.poolLock.newCondition(), routePool);
                        waitingThreadAborter.setWaitingThread(waitingThread);
                    }
                    routePool.queueThread(waitingThread);
                    this.waitingThreads.add(waitingThread);
                    boolean await = waitingThread.await(date);
                    routePool.removeThread(waitingThread);
                    this.waitingThreads.remove(waitingThread);
                    if (!await && date != null && date.getTime() <= System.currentTimeMillis()) {
                        throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
                    }
                } else {
                    deleteLeastUsedEntry();
                    routePool = getRoutePool(httpRoute, true);
                    basicPoolEntry = createEntry(routePool, this.operator);
                }
            }
            return basicPoolEntry;
        } finally {
            this.poolLock.unlock();
        }
    }

    @Override // cz.msebera.android.httpclient.impl.conn.tsccm.AbstractConnPool
    public void freeEntry(BasicPoolEntry basicPoolEntry, boolean z, long j, TimeUnit timeUnit) {
        String str;
        HttpRoute plannedRoute = basicPoolEntry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Releasing connection [" + plannedRoute + "][" + basicPoolEntry.getState() + "]");
        }
        this.poolLock.lock();
        try {
            if (this.shutdown) {
                closeConnection(basicPoolEntry);
                return;
            }
            this.leasedConnections.remove(basicPoolEntry);
            RouteSpecificPool routePool = getRoutePool(plannedRoute, true);
            if (!z || routePool.getCapacity() < 0) {
                closeConnection(basicPoolEntry);
                routePool.dropEntry();
                this.numConnections--;
            } else {
                if (this.log.isDebugEnabled()) {
                    if (j > 0) {
                        str = "for " + j + " " + timeUnit;
                    } else {
                        str = "indefinitely";
                    }
                    this.log.debug("Pooling connection [" + plannedRoute + "][" + basicPoolEntry.getState() + "]; keep alive " + str);
                }
                routePool.freeEntry(basicPoolEntry);
                basicPoolEntry.updateExpiry(j, timeUnit);
                this.freeConnections.add(basicPoolEntry);
            }
            notifyWaitingThread(routePool);
        } finally {
            this.poolLock.unlock();
        }
    }

    protected BasicPoolEntry getFreeEntry(RouteSpecificPool routeSpecificPool, Object obj) {
        this.poolLock.lock();
        boolean z = false;
        BasicPoolEntry basicPoolEntry = null;
        while (!z) {
            try {
                basicPoolEntry = routeSpecificPool.allocEntry(obj);
                if (basicPoolEntry != null) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Getting free connection [" + routeSpecificPool.getRoute() + "][" + obj + "]");
                    }
                    this.freeConnections.remove(basicPoolEntry);
                    if (basicPoolEntry.isExpired(System.currentTimeMillis())) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Closing expired free connection [" + routeSpecificPool.getRoute() + "][" + obj + "]");
                        }
                        closeConnection(basicPoolEntry);
                        routeSpecificPool.dropEntry();
                        this.numConnections--;
                    } else {
                        this.leasedConnections.add(basicPoolEntry);
                    }
                } else if (this.log.isDebugEnabled()) {
                    this.log.debug("No free connections [" + routeSpecificPool.getRoute() + "][" + obj + "]");
                }
                z = true;
            } finally {
                this.poolLock.unlock();
            }
        }
        return basicPoolEntry;
    }

    protected BasicPoolEntry createEntry(RouteSpecificPool routeSpecificPool, ClientConnectionOperator clientConnectionOperator) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Creating new connection [" + routeSpecificPool.getRoute() + "]");
        }
        BasicPoolEntry basicPoolEntry = new BasicPoolEntry(clientConnectionOperator, routeSpecificPool.getRoute(), this.connTTL, this.connTTLTimeUnit);
        this.poolLock.lock();
        try {
            routeSpecificPool.createdEntry(basicPoolEntry);
            this.numConnections++;
            this.leasedConnections.add(basicPoolEntry);
            return basicPoolEntry;
        } finally {
            this.poolLock.unlock();
        }
    }

    protected void deleteEntry(BasicPoolEntry basicPoolEntry) {
        HttpRoute plannedRoute = basicPoolEntry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Deleting connection [" + plannedRoute + "][" + basicPoolEntry.getState() + "]");
        }
        this.poolLock.lock();
        try {
            closeConnection(basicPoolEntry);
            RouteSpecificPool routePool = getRoutePool(plannedRoute, true);
            routePool.deleteEntry(basicPoolEntry);
            this.numConnections--;
            if (routePool.isUnused()) {
                this.routeToPool.remove(plannedRoute);
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    protected void deleteLeastUsedEntry() {
        this.poolLock.lock();
        try {
            BasicPoolEntry remove = this.freeConnections.remove();
            if (remove != null) {
                deleteEntry(remove);
            } else if (this.log.isDebugEnabled()) {
                this.log.debug("No free connection to delete");
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    @Override // cz.msebera.android.httpclient.impl.conn.tsccm.AbstractConnPool
    protected void handleLostEntry(HttpRoute httpRoute) {
        this.poolLock.lock();
        try {
            RouteSpecificPool routePool = getRoutePool(httpRoute, true);
            routePool.dropEntry();
            if (routePool.isUnused()) {
                this.routeToPool.remove(httpRoute);
            }
            this.numConnections--;
            notifyWaitingThread(routePool);
        } finally {
            this.poolLock.unlock();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x006d A[Catch: all -> 0x0039, TRY_LEAVE, TryCatch #0 {all -> 0x0039, blocks: (B:4:0x0007, B:6:0x000d, B:8:0x0015, B:9:0x0034, B:12:0x003b, B:14:0x0043, B:16:0x004b, B:17:0x0052, B:18:0x005b, B:20:0x0063, B:23:0x006d), top: B:28:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void notifyWaitingThread(cz.msebera.android.httpclient.impl.conn.tsccm.RouteSpecificPool r4) {
        /*
            r3 = this;
            java.util.concurrent.locks.Lock r0 = r3.poolLock
            r0.lock()
            if (r4 == 0) goto L_0x003b
            boolean r0 = r4.hasThread()     // Catch: all -> 0x0039
            if (r0 == 0) goto L_0x003b
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r0 = r3.log     // Catch: all -> 0x0039
            boolean r0 = r0.isDebugEnabled()     // Catch: all -> 0x0039
            if (r0 == 0) goto L_0x0034
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r0 = r3.log     // Catch: all -> 0x0039
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: all -> 0x0039
            r1.<init>()     // Catch: all -> 0x0039
            java.lang.String r2 = "Notifying thread waiting on pool ["
            r1.append(r2)     // Catch: all -> 0x0039
            cz.msebera.android.httpclient.conn.routing.HttpRoute r2 = r4.getRoute()     // Catch: all -> 0x0039
            r1.append(r2)     // Catch: all -> 0x0039
            java.lang.String r2 = "]"
            r1.append(r2)     // Catch: all -> 0x0039
            java.lang.String r1 = r1.toString()     // Catch: all -> 0x0039
            r0.debug(r1)     // Catch: all -> 0x0039
        L_0x0034:
            cz.msebera.android.httpclient.impl.conn.tsccm.WaitingThread r4 = r4.nextThread()     // Catch: all -> 0x0039
            goto L_0x006b
        L_0x0039:
            r4 = move-exception
            goto L_0x0076
        L_0x003b:
            java.util.Queue<cz.msebera.android.httpclient.impl.conn.tsccm.WaitingThread> r4 = r3.waitingThreads     // Catch: all -> 0x0039
            boolean r4 = r4.isEmpty()     // Catch: all -> 0x0039
            if (r4 != 0) goto L_0x005b
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r4 = r3.log     // Catch: all -> 0x0039
            boolean r4 = r4.isDebugEnabled()     // Catch: all -> 0x0039
            if (r4 == 0) goto L_0x0052
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r4 = r3.log     // Catch: all -> 0x0039
            java.lang.String r0 = "Notifying thread waiting on any pool"
            r4.debug(r0)     // Catch: all -> 0x0039
        L_0x0052:
            java.util.Queue<cz.msebera.android.httpclient.impl.conn.tsccm.WaitingThread> r4 = r3.waitingThreads     // Catch: all -> 0x0039
            java.lang.Object r4 = r4.remove()     // Catch: all -> 0x0039
            cz.msebera.android.httpclient.impl.conn.tsccm.WaitingThread r4 = (cz.msebera.android.httpclient.impl.conn.tsccm.WaitingThread) r4     // Catch: all -> 0x0039
            goto L_0x006b
        L_0x005b:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r4 = r3.log     // Catch: all -> 0x0039
            boolean r4 = r4.isDebugEnabled()     // Catch: all -> 0x0039
            if (r4 == 0) goto L_0x006a
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r4 = r3.log     // Catch: all -> 0x0039
            java.lang.String r0 = "Notifying no-one, there are no waiting threads"
            r4.debug(r0)     // Catch: all -> 0x0039
        L_0x006a:
            r4 = 0
        L_0x006b:
            if (r4 == 0) goto L_0x0070
            r4.wakeup()     // Catch: all -> 0x0039
        L_0x0070:
            java.util.concurrent.locks.Lock r4 = r3.poolLock
            r4.unlock()
            return
        L_0x0076:
            java.util.concurrent.locks.Lock r0 = r3.poolLock
            r0.unlock()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.conn.tsccm.ConnPoolByRoute.notifyWaitingThread(cz.msebera.android.httpclient.impl.conn.tsccm.RouteSpecificPool):void");
    }

    @Override // cz.msebera.android.httpclient.impl.conn.tsccm.AbstractConnPool
    public void deleteClosedConnections() {
        this.poolLock.lock();
        try {
            Iterator<BasicPoolEntry> it = this.freeConnections.iterator();
            while (it.hasNext()) {
                BasicPoolEntry next = it.next();
                if (!next.getConnection().isOpen()) {
                    it.remove();
                    deleteEntry(next);
                }
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    @Override // cz.msebera.android.httpclient.impl.conn.tsccm.AbstractConnPool
    public void closeIdleConnections(long j, TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        if (j <= 0) {
            j = 0;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Closing connections idle longer than " + j + " " + timeUnit);
        }
        long currentTimeMillis = System.currentTimeMillis() - timeUnit.toMillis(j);
        this.poolLock.lock();
        try {
            Iterator<BasicPoolEntry> it = this.freeConnections.iterator();
            while (it.hasNext()) {
                BasicPoolEntry next = it.next();
                if (next.getUpdated() <= currentTimeMillis) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Closing connection last used @ " + new Date(next.getUpdated()));
                    }
                    it.remove();
                    deleteEntry(next);
                }
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    @Override // cz.msebera.android.httpclient.impl.conn.tsccm.AbstractConnPool
    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        long currentTimeMillis = System.currentTimeMillis();
        this.poolLock.lock();
        try {
            Iterator<BasicPoolEntry> it = this.freeConnections.iterator();
            while (it.hasNext()) {
                BasicPoolEntry next = it.next();
                if (next.isExpired(currentTimeMillis)) {
                    if (this.log.isDebugEnabled()) {
                        HttpClientAndroidLog httpClientAndroidLog = this.log;
                        httpClientAndroidLog.debug("Closing connection expired @ " + new Date(next.getExpiry()));
                    }
                    it.remove();
                    deleteEntry(next);
                }
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    @Override // cz.msebera.android.httpclient.impl.conn.tsccm.AbstractConnPool
    public void shutdown() {
        this.poolLock.lock();
        try {
            if (!this.shutdown) {
                this.shutdown = true;
                Iterator<BasicPoolEntry> it = this.leasedConnections.iterator();
                while (it.hasNext()) {
                    it.remove();
                    closeConnection(it.next());
                }
                Iterator<BasicPoolEntry> it2 = this.freeConnections.iterator();
                while (it2.hasNext()) {
                    BasicPoolEntry next = it2.next();
                    it2.remove();
                    if (this.log.isDebugEnabled()) {
                        HttpClientAndroidLog httpClientAndroidLog = this.log;
                        httpClientAndroidLog.debug("Closing connection [" + next.getPlannedRoute() + "][" + next.getState() + "]");
                    }
                    closeConnection(next);
                }
                Iterator<WaitingThread> it3 = this.waitingThreads.iterator();
                while (it3.hasNext()) {
                    it3.remove();
                    it3.next().wakeup();
                }
                this.routeToPool.clear();
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    public void setMaxTotalConnections(int i) {
        this.poolLock.lock();
        try {
            this.maxTotalConnections = i;
        } finally {
            this.poolLock.unlock();
        }
    }

    public int getMaxTotalConnections() {
        return this.maxTotalConnections;
    }
}
