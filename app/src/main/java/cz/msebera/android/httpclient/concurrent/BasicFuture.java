package cz.msebera.android.httpclient.concurrent;

import cz.msebera.android.httpclient.util.Args;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public class BasicFuture<T> implements Future<T>, Cancellable {
    private final FutureCallback<T> callback;
    private volatile boolean cancelled;
    private volatile boolean completed;
    private volatile Exception ex;
    private volatile T result;

    public BasicFuture(FutureCallback<T> futureCallback) {
        this.callback = futureCallback;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.completed;
    }

    private T getResult() throws ExecutionException {
        if (this.ex == null) {
            return this.result;
        }
        throw new ExecutionException(this.ex);
    }

    @Override // java.util.concurrent.Future
    public synchronized T get() throws InterruptedException, ExecutionException {
        while (!this.completed) {
            wait();
        }
        return getResult();
    }

    @Override // java.util.concurrent.Future
    public synchronized T get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        Args.notNull(timeUnit, "Time unit");
        long millis = timeUnit.toMillis(j);
        long currentTimeMillis = millis <= 0 ? 0L : System.currentTimeMillis();
        if (this.completed) {
            return getResult();
        } else if (millis <= 0) {
            throw new TimeoutException();
        } else {
            long j2 = millis;
            do {
                wait(j2);
                if (this.completed) {
                    return getResult();
                }
                j2 = millis - (System.currentTimeMillis() - currentTimeMillis);
            } while (j2 > 0);
            throw new TimeoutException();
        }
    }

    public boolean completed(T t) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.result = t;
            notifyAll();
            if (this.callback != null) {
                this.callback.completed(t);
            }
            return true;
        }
    }

    public boolean failed(Exception exc) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.ex = exc;
            notifyAll();
            if (this.callback != null) {
                this.callback.failed(exc);
            }
            return true;
        }
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.cancelled = true;
            notifyAll();
            if (this.callback != null) {
                this.callback.cancelled();
            }
            return true;
        }
    }

    @Override // cz.msebera.android.httpclient.concurrent.Cancellable
    public boolean cancel() {
        return cancel(true);
    }
}
