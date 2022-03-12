package com.google.android.gms.tasks;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class TaskExecutors {
    public static final Executor MAIN_THREAD = new zza();
    static final Executor zzkrt = new zzm();

    /* loaded from: classes.dex */
    static final class zza implements Executor {
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override // java.util.concurrent.Executor
        public final void execute(@NonNull Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

    private TaskExecutors() {
    }
}
