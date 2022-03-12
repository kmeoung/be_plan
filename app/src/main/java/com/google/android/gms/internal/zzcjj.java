package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcjj extends zzcfp {
    private /* synthetic */ zzcjd zzjfo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzcjj(zzcjd zzcjdVar, zzchj zzchjVar) {
        super(zzchjVar);
        this.zzjfo = zzcjdVar;
    }

    @Override // com.google.android.gms.internal.zzcfp
    public final void run() {
        this.zzjfo.zzawm().zzayt().log("Tasks have been queued for a long time");
    }
}
