package cz.msebera.android.httpclient.impl.conn.tsccm;

import cz.msebera.android.httpclient.util.Args;
import java.util.Date;
import java.util.concurrent.locks.Condition;

@Deprecated
/* loaded from: classes.dex */
public class WaitingThread {
    private boolean aborted;
    private final Condition cond;
    private final RouteSpecificPool pool;
    private Thread waiter;

    public WaitingThread(Condition condition, RouteSpecificPool routeSpecificPool) {
        Args.notNull(condition, "Condition");
        this.cond = condition;
        this.pool = routeSpecificPool;
    }

    public final Condition getCondition() {
        return this.cond;
    }

    public final RouteSpecificPool getPool() {
        return this.pool;
    }

    public final Thread getThread() {
        return this.waiter;
    }

    public boolean await(Date date) throws InterruptedException {
        boolean z;
        if (this.waiter != null) {
            throw new IllegalStateException("A thread is already waiting on this object.\ncaller: " + Thread.currentThread() + "\nwaiter: " + this.waiter);
        } else if (this.aborted) {
            throw new InterruptedException("Operation interrupted");
        } else {
            this.waiter = Thread.currentThread();
            try {
                if (date != null) {
                    z = this.cond.awaitUntil(date);
                } else {
                    this.cond.await();
                    z = true;
                }
                if (this.aborted) {
                    throw new InterruptedException("Operation interrupted");
                }
                this.waiter = null;
                return z;
            } catch (Throwable th) {
                this.waiter = null;
                throw th;
            }
        }
    }

    public void wakeup() {
        if (this.waiter == null) {
            throw new IllegalStateException("Nobody waiting on this object.");
        }
        this.cond.signalAll();
    }

    public void interrupt() {
        this.aborted = true;
        this.cond.signalAll();
    }
}
