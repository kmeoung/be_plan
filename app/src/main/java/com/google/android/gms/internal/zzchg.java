package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import java.lang.Thread;

/* loaded from: classes.dex */
public final class zzchg implements Thread.UncaughtExceptionHandler {
    private final String zzjbu;
    private /* synthetic */ zzche zzjbv;

    public zzchg(zzche zzcheVar, String str) {
        this.zzjbv = zzcheVar;
        zzbq.checkNotNull(str);
        this.zzjbu = str;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        this.zzjbv.zzawm().zzayr().zzj(this.zzjbu, th);
    }
}
