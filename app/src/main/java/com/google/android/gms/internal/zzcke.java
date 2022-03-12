package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;

/* loaded from: classes.dex */
public final class zzcke extends zzcfp {
    private /* synthetic */ zzckc zzjgk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzcke(zzckc zzckcVar, zzchj zzchjVar) {
        super(zzchjVar);
        this.zzjgk = zzckcVar;
    }

    @Override // com.google.android.gms.internal.zzcfp
    @WorkerThread
    public final void run() {
        this.zzjgk.zzbak();
    }
}
