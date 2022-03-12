package com.google.android.gms.internal;

import android.content.Intent;

/* loaded from: classes.dex */
public final class zzckj extends zzcfp {
    private /* synthetic */ zzcki zzjgm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzckj(zzcki zzckiVar, zzchj zzchjVar) {
        super(zzchjVar);
        this.zzjgm = zzckiVar;
    }

    @Override // com.google.android.gms.internal.zzcfp
    public final void run() {
        this.zzjgm.cancel();
        this.zzjgm.zzawm().zzayx().log("Sending upload intent from DelayedRunnable");
        Intent className = new Intent().setClassName(this.zzjgm.getContext(), "com.google.android.gms.measurement.AppMeasurementReceiver");
        className.setAction("com.google.android.gms.measurement.UPLOAD");
        this.zzjgm.getContext().sendBroadcast(className);
    }
}
