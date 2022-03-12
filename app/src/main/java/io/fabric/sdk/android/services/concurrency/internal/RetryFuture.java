package io.fabric.sdk.android.services.concurrency.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class RetryFuture<T> extends AbstractFuture<T> implements Runnable {
    private final RetryThreadPoolExecutor executor;
    RetryState retryState;
    private final AtomicReference<Thread> runner = new AtomicReference<>();
    private final Callable<T> task;

    public RetryFuture(Callable<T> callable, RetryState retryState, RetryThreadPoolExecutor retryThreadPoolExecutor) {
        this.task = callable;
        this.retryState = retryState;
        this.executor = retryThreadPoolExecutor;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!isDone()) {
            if (this.runner.compareAndSet(null, Thread.currentThread())) {
                try {
                    set(this.task.call());
                } catch (Throwable th) {
                    try {
                        if (getRetryPolicy().shouldRetry(getRetryCount(), th)) {
                            long delayMillis = getBackoff().getDelayMillis(getRetryCount());
                            this.retryState = this.retryState.nextRetryState();
                            this.executor.schedule(this, delayMillis, TimeUnit.MILLISECONDS);
                        } else {
                            setException(th);
                        }
                    } finally {
                        this.runner.getAndSet(null);
                    }
                }
            }
        }
    }

    private RetryPolicy getRetryPolicy() {
        return this.retryState.getRetryPolicy();
    }

    private Backoff getBackoff() {
        return this.retryState.getBackoff();
    }

    private int getRetryCount() {
        return this.retryState.getRetryCount();
    }

    @Override // io.fabric.sdk.android.services.concurrency.internal.AbstractFuture
    protected void interruptTask() {
        Thread andSet = this.runner.getAndSet(null);
        if (andSet != null) {
            andSet.interrupt();
        }
    }
}
