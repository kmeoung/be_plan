package io.fabric.sdk.android.services.concurrency.internal;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/* loaded from: classes.dex */
public abstract class AbstractFuture<V> implements Future<V> {
    private final Sync<V> sync = new Sync<>();

    protected void interruptTask() {
    }

    static final CancellationException cancellationExceptionWithCause(String str, Throwable th) {
        CancellationException cancellationException = new CancellationException(str);
        cancellationException.initCause(th);
        return cancellationException;
    }

    @Override // java.util.concurrent.Future
    public V get(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException, ExecutionException {
        return this.sync.get(timeUnit.toNanos(j));
    }

    @Override // java.util.concurrent.Future
    public V get() throws InterruptedException, ExecutionException {
        return this.sync.get();
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.sync.isDone();
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.sync.isCancelled();
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        if (!this.sync.cancel(z)) {
            return false;
        }
        if (!z) {
            return true;
        }
        interruptTask();
        return true;
    }

    protected final boolean wasInterrupted() {
        return this.sync.wasInterrupted();
    }

    public boolean set(V v) {
        return this.sync.set(v);
    }

    public boolean setException(Throwable th) {
        if (th != null) {
            return this.sync.setException(th);
        }
        throw new NullPointerException();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class Sync<V> extends AbstractQueuedSynchronizer {
        static final int CANCELLED = 4;
        static final int COMPLETED = 2;
        static final int COMPLETING = 1;
        static final int INTERRUPTED = 8;
        static final int RUNNING = 0;
        private static final long serialVersionUID = 0;
        private Throwable exception;
        private V value;

        Sync() {
        }

        @Override // java.util.concurrent.locks.AbstractQueuedSynchronizer
        protected int tryAcquireShared(int i) {
            return isDone() ? 1 : -1;
        }

        @Override // java.util.concurrent.locks.AbstractQueuedSynchronizer
        protected boolean tryReleaseShared(int i) {
            setState(i);
            return true;
        }

        V get(long j) throws TimeoutException, CancellationException, ExecutionException, InterruptedException {
            if (tryAcquireSharedNanos(-1, j)) {
                return getValue();
            }
            throw new TimeoutException("Timeout waiting for task.");
        }

        V get() throws CancellationException, ExecutionException, InterruptedException {
            acquireSharedInterruptibly(-1);
            return getValue();
        }

        private V getValue() throws CancellationException, ExecutionException {
            int state = getState();
            if (state != 2) {
                if (state == 4 || state == 8) {
                    throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", this.exception);
                }
                throw new IllegalStateException("Error, synchronizer in invalid state: " + state);
            } else if (this.exception == null) {
                return this.value;
            } else {
                throw new ExecutionException(this.exception);
            }
        }

        boolean isDone() {
            return (getState() & 14) != 0;
        }

        boolean isCancelled() {
            return (getState() & 12) != 0;
        }

        boolean wasInterrupted() {
            return getState() == 8;
        }

        boolean set(V v) {
            return complete(v, null, 2);
        }

        boolean setException(Throwable th) {
            return complete(null, th, 2);
        }

        boolean cancel(boolean z) {
            return complete(null, null, z ? 8 : 4);
        }

        private boolean complete(V v, Throwable th, int i) {
            boolean compareAndSetState = compareAndSetState(0, 1);
            if (compareAndSetState) {
                this.value = v;
                if ((i & 12) != 0) {
                    th = new CancellationException("Future.cancel() was called.");
                }
                this.exception = th;
                releaseShared(i);
            } else if (getState() == 1) {
                acquireShared(-1);
            }
            return compareAndSetState;
        }
    }
}
