package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.WorkerThread;

/* loaded from: classes.dex */
public final class zzckd extends zzcfp {
    private /* synthetic */ zzckc zzjgk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzckd(zzckc zzckcVar, zzchj zzchjVar) {
        super(zzchjVar);
        this.zzjgk = zzckcVar;
    }

    @Override // com.google.android.gms.internal.zzcfp
    @WorkerThread
    public final void run() {
        zzckc zzckcVar = this.zzjgk;
        zzckcVar.zzut();
        zzckcVar.zzawm().zzayx().zzj("Session started, time", Long.valueOf(zzckcVar.zzwh().elapsedRealtime()));
        zzckcVar.zzawn().zzjal.set(false);
        zzckcVar.zzawa().zzc("auto", "_s", new Bundle());
        zzckcVar.zzawn().zzjam.set(zzckcVar.zzwh().currentTimeMillis());
    }
}
