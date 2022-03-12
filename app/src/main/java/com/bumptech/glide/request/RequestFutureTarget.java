package com.bumptech.glide.request;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public class RequestFutureTarget<R> implements FutureTarget<R>, RequestListener<R>, Runnable {
    private static final Waiter DEFAULT_WAITER = new Waiter();
    private final boolean assertBackgroundThread;
    @Nullable
    private GlideException exception;
    private final int height;
    private boolean isCancelled;
    private boolean loadFailed;
    private final Handler mainHandler;
    @Nullable
    private Request request;
    @Nullable
    private R resource;
    private boolean resultReceived;
    private final Waiter waiter;
    private final int width;

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onDestroy() {
    }

    @Override // com.bumptech.glide.request.target.Target
    public void onLoadCleared(Drawable drawable) {
    }

    @Override // com.bumptech.glide.request.target.Target
    public void onLoadStarted(Drawable drawable) {
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
    }

    @Override // com.bumptech.glide.request.target.Target
    public void removeCallback(SizeReadyCallback sizeReadyCallback) {
    }

    public RequestFutureTarget(Handler handler, int i, int i2) {
        this(handler, i, i2, true, DEFAULT_WAITER);
    }

    RequestFutureTarget(Handler handler, int i, int i2, boolean z, Waiter waiter) {
        this.mainHandler = handler;
        this.width = i;
        this.height = i2;
        this.assertBackgroundThread = z;
        this.waiter = waiter;
    }

    @Override // java.util.concurrent.Future
    public synchronized boolean cancel(boolean z) {
        if (isDone()) {
            return false;
        }
        this.isCancelled = true;
        this.waiter.notifyAll(this);
        if (z) {
            clearOnMainThread();
        }
        return true;
    }

    @Override // java.util.concurrent.Future
    public synchronized boolean isCancelled() {
        return this.isCancelled;
    }

    @Override // java.util.concurrent.Future
    public synchronized boolean isDone() {
        boolean z;
        if (!this.isCancelled && !this.resultReceived) {
            if (!this.loadFailed) {
                z = false;
            }
        }
        z = true;
        return z;
    }

    @Override // java.util.concurrent.Future
    public R get() throws InterruptedException, ExecutionException {
        try {
            return doGet(null);
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    @Override // java.util.concurrent.Future
    public R get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return doGet(Long.valueOf(timeUnit.toMillis(j)));
    }

    @Override // com.bumptech.glide.request.target.Target
    public void getSize(SizeReadyCallback sizeReadyCallback) {
        sizeReadyCallback.onSizeReady(this.width, this.height);
    }

    @Override // com.bumptech.glide.request.target.Target
    public void setRequest(@Nullable Request request) {
        this.request = request;
    }

    @Override // com.bumptech.glide.request.target.Target
    @Nullable
    public Request getRequest() {
        return this.request;
    }

    @Override // com.bumptech.glide.request.target.Target
    public synchronized void onLoadFailed(Drawable drawable) {
    }

    @Override // com.bumptech.glide.request.target.Target
    public synchronized void onResourceReady(R r, Transition<? super R> transition) {
    }

    private synchronized R doGet(Long l) throws ExecutionException, InterruptedException, TimeoutException {
        if (this.assertBackgroundThread && !isDone()) {
            Util.assertBackgroundThread();
        }
        if (this.isCancelled) {
            throw new CancellationException();
        } else if (this.loadFailed) {
            throw new ExecutionException(this.exception);
        } else if (this.resultReceived) {
            return this.resource;
        } else {
            if (l == null) {
                this.waiter.waitForTimeout(this, 0L);
            } else if (l.longValue() > 0) {
                this.waiter.waitForTimeout(this, l.longValue());
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            } else if (this.loadFailed) {
                throw new GlideExecutionException(this.exception);
            } else if (this.isCancelled) {
                throw new CancellationException();
            } else if (!this.resultReceived) {
                throw new TimeoutException();
            } else {
                return this.resource;
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.request != null) {
            this.request.clear();
            this.request = null;
        }
    }

    private void clearOnMainThread() {
        this.mainHandler.post(this);
    }

    @Override // com.bumptech.glide.request.RequestListener
    public synchronized boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<R> target, boolean z) {
        this.loadFailed = true;
        this.exception = glideException;
        this.waiter.notifyAll(this);
        return false;
    }

    @Override // com.bumptech.glide.request.RequestListener
    public synchronized boolean onResourceReady(R r, Object obj, Target<R> target, DataSource dataSource, boolean z) {
        this.resultReceived = true;
        this.resource = r;
        this.waiter.notifyAll(this);
        return false;
    }

    /* loaded from: classes.dex */
    public static class Waiter {
        Waiter() {
        }

        public void waitForTimeout(Object obj, long j) throws InterruptedException {
            obj.wait(j);
        }

        public void notifyAll(Object obj) {
            obj.notifyAll();
        }
    }

    /* loaded from: classes.dex */
    public static class GlideExecutionException extends ExecutionException {
        private final GlideException cause;

        GlideExecutionException(GlideException glideException) {
            this.cause = glideException;
        }

        @Override // java.lang.Throwable
        public void printStackTrace() {
            ThrowableExtension.printStackTrace(this, System.err);
        }

        @Override // java.lang.Throwable
        public void printStackTrace(PrintStream printStream) {
            super.printStackTrace(printStream);
            printStream.print("Caused by: ");
            ThrowableExtension.printStackTrace(this.cause, printStream);
        }

        @Override // java.lang.Throwable
        public void printStackTrace(PrintWriter printWriter) {
            super.printStackTrace(printWriter);
            printWriter.print("Caused by: ");
            ThrowableExtension.printStackTrace(this.cause, printWriter);
        }
    }
}
