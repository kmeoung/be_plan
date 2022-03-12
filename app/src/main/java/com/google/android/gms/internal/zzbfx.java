package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class zzbfx implements Executor {
    private final Handler mHandler;

    public zzbfx(Looper looper) {
        this.mHandler = new Handler(looper);
    }

    @Override // java.util.concurrent.Executor
    public final void execute(@NonNull Runnable runnable) {
        this.mHandler.post(runnable);
    }
}
