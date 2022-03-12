package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import java.lang.Thread;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class CrashlyticsUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final CrashListener crashListener;
    private final Thread.UncaughtExceptionHandler defaultHandler;
    private final AtomicBoolean isHandlingException = new AtomicBoolean(false);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface CrashListener {
        void onUncaughtException(Thread thread, Throwable th);
    }

    public CrashlyticsUncaughtExceptionHandler(CrashListener crashListener, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.crashListener = crashListener;
        this.defaultHandler = uncaughtExceptionHandler;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Thread$UncaughtExceptionHandler] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.Thread] */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.Thread] */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.util.concurrent.atomic.AtomicBoolean] */
    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        this.isHandlingException.set(true);
        try {
            try {
                this.crashListener.onUncaughtException(thread, th);
            } catch (Exception e) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the uncaught exception handler", e);
            }
        } finally {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
            this.defaultHandler.uncaughtException(thread, th);
            this.isHandlingException.set(false);
        }
    }

    public boolean isHandlingException() {
        return this.isHandlingException.get();
    }
}
