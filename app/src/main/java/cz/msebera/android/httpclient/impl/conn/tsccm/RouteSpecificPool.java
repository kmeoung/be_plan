package cz.msebera.android.httpclient.impl.conn.tsccm;

import cz.msebera.android.httpclient.conn.params.ConnPerRoute;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.util.LinkedList;
import java.util.Queue;

@Deprecated
/* loaded from: classes.dex */
public class RouteSpecificPool {
    protected final ConnPerRoute connPerRoute;
    protected final LinkedList<BasicPoolEntry> freeEntries;
    public HttpClientAndroidLog log;
    protected final int maxEntries;
    protected int numEntries;
    protected final HttpRoute route;
    protected final Queue<WaitingThread> waitingThreads;

    @Deprecated
    public RouteSpecificPool(HttpRoute httpRoute, int i) {
        this.log = new HttpClientAndroidLog(getClass());
        this.route = httpRoute;
        this.maxEntries = i;
        this.connPerRoute = new ConnPerRoute() { // from class: cz.msebera.android.httpclient.impl.conn.tsccm.RouteSpecificPool.1
            @Override // cz.msebera.android.httpclient.conn.params.ConnPerRoute
            public int getMaxForRoute(HttpRoute httpRoute2) {
                return RouteSpecificPool.this.maxEntries;
            }
        };
        this.freeEntries = new LinkedList<>();
        this.waitingThreads = new LinkedList();
        this.numEntries = 0;
    }

    public RouteSpecificPool(HttpRoute httpRoute, ConnPerRoute connPerRoute) {
        this.log = new HttpClientAndroidLog(getClass());
        this.route = httpRoute;
        this.connPerRoute = connPerRoute;
        this.maxEntries = connPerRoute.getMaxForRoute(httpRoute);
        this.freeEntries = new LinkedList<>();
        this.waitingThreads = new LinkedList();
        this.numEntries = 0;
    }

    public final HttpRoute getRoute() {
        return this.route;
    }

    public final int getMaxEntries() {
        return this.maxEntries;
    }

    public boolean isUnused() {
        return this.numEntries < 1 && this.waitingThreads.isEmpty();
    }

    public int getCapacity() {
        return this.connPerRoute.getMaxForRoute(this.route) - this.numEntries;
    }

    public final int getEntryCount() {
        return this.numEntries;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry allocEntry(java.lang.Object r4) {
        /*
            r3 = this;
            java.util.LinkedList<cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry> r0 = r3.freeEntries
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0034
            java.util.LinkedList<cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry> r0 = r3.freeEntries
            java.util.LinkedList<cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry> r1 = r3.freeEntries
            int r1 = r1.size()
            java.util.ListIterator r0 = r0.listIterator(r1)
        L_0x0014:
            boolean r1 = r0.hasPrevious()
            if (r1 == 0) goto L_0x0034
            java.lang.Object r1 = r0.previous()
            cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry r1 = (cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry) r1
            java.lang.Object r2 = r1.getState()
            if (r2 == 0) goto L_0x0030
            java.lang.Object r2 = r1.getState()
            boolean r2 = cz.msebera.android.httpclient.util.LangUtils.equals(r4, r2)
            if (r2 == 0) goto L_0x0014
        L_0x0030:
            r0.remove()
            return r1
        L_0x0034:
            int r4 = r3.getCapacity()
            if (r4 != 0) goto L_0x005e
            java.util.LinkedList<cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry> r4 = r3.freeEntries
            boolean r4 = r4.isEmpty()
            if (r4 != 0) goto L_0x005e
            java.util.LinkedList<cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry> r4 = r3.freeEntries
            java.lang.Object r4 = r4.remove()
            cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry r4 = (cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry) r4
            r4.shutdownEntry()
            cz.msebera.android.httpclient.conn.OperatedClientConnection r0 = r4.getConnection()
            r0.close()     // Catch: IOException -> 0x0055
            goto L_0x005d
        L_0x0055:
            r0 = move-exception
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r3.log
            java.lang.String r2 = "I/O error closing connection"
            r1.debug(r2, r0)
        L_0x005d:
            return r4
        L_0x005e:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.conn.tsccm.RouteSpecificPool.allocEntry(java.lang.Object):cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry");
    }

    public void freeEntry(BasicPoolEntry basicPoolEntry) {
        if (this.numEntries < 1) {
            throw new IllegalStateException("No entry created for this pool. " + this.route);
        } else if (this.numEntries <= this.freeEntries.size()) {
            throw new IllegalStateException("No entry allocated from this pool. " + this.route);
        } else {
            this.freeEntries.add(basicPoolEntry);
        }
    }

    public void createdEntry(BasicPoolEntry basicPoolEntry) {
        Args.check(this.route.equals(basicPoolEntry.getPlannedRoute()), "Entry not planned for this pool");
        this.numEntries++;
    }

    public boolean deleteEntry(BasicPoolEntry basicPoolEntry) {
        boolean remove = this.freeEntries.remove(basicPoolEntry);
        if (remove) {
            this.numEntries--;
        }
        return remove;
    }

    public void dropEntry() {
        Asserts.check(this.numEntries > 0, "There is no entry that could be dropped");
        this.numEntries--;
    }

    public void queueThread(WaitingThread waitingThread) {
        Args.notNull(waitingThread, "Waiting thread");
        this.waitingThreads.add(waitingThread);
    }

    public boolean hasThread() {
        return !this.waitingThreads.isEmpty();
    }

    public WaitingThread nextThread() {
        return this.waitingThreads.peek();
    }

    public void removeThread(WaitingThread waitingThread) {
        if (waitingThread != null) {
            this.waitingThreads.remove(waitingThread);
        }
    }
}
