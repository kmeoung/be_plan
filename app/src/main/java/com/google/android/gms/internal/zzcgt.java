package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzcgt implements Runnable {
    private /* synthetic */ boolean zzizs;
    private /* synthetic */ zzcgs zzizt;

    public zzcgt(zzcgs zzcgsVar, boolean z) {
        this.zzizt = zzcgsVar;
        this.zzizs = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzchj zzchjVar;
        zzchjVar = this.zzizt.zzitk;
        zzchjVar.zzbn(this.zzizs);
    }
}
