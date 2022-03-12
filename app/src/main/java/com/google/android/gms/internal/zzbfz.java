package com.google.android.gms.internal;

import android.os.Process;

/* loaded from: classes.dex */
final class zzbfz implements Runnable {
    private final int mPriority;
    private final Runnable zzv;

    public zzbfz(Runnable runnable, int i) {
        this.zzv = runnable;
        this.mPriority = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Process.setThreadPriority(this.mPriority);
        this.zzv.run();
    }
}
