package com.crashlytics.android.core;

import android.os.Looper;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class CrashlyticsBackgroundWorker {
    private final ExecutorService executorService;

    public CrashlyticsBackgroundWorker(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public <T> T submitAndWait(Callable<T> callable) {
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                return this.executorService.submit(callable).get(4L, TimeUnit.SECONDS);
            }
            return this.executorService.submit(callable).get();
        } catch (RejectedExecutionException unused) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Executor is shut down because we're handling a fatal crash.");
            return null;
        } catch (Exception e) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Failed to execute task.", e);
            return null;
        }
    }

    public Future<?> submit(final Runnable runnable) {
        try {
            return this.executorService.submit(new Runnable() { // from class: com.crashlytics.android.core.CrashlyticsBackgroundWorker.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        runnable.run();
                    } catch (Exception e) {
                        Fabric.getLogger().e(CrashlyticsCore.TAG, "Failed to execute task.", e);
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Executor is shut down because we're handling a fatal crash.");
            return null;
        }
    }

    public <T> Future<T> submit(final Callable<T> callable) {
        try {
            return this.executorService.submit(new Callable<T>() { // from class: com.crashlytics.android.core.CrashlyticsBackgroundWorker.2
                /* JADX WARN: Type inference failed for: r0v3, types: [T, java.lang.Object] */
                @Override // java.util.concurrent.Callable
                public T call() throws Exception {
                    try {
                        return callable.call();
                    } catch (Exception e) {
                        Fabric.getLogger().e(CrashlyticsCore.TAG, "Failed to execute task.", e);
                        return null;
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Executor is shut down because we're handling a fatal crash.");
            return null;
        }
    }
}
