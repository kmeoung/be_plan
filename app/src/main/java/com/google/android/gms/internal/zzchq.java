package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzchq implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcho zzjdt;
    private /* synthetic */ zzcfi zzjdu;

    public zzchq(zzcho zzchoVar, zzcfi zzcfiVar, zzcff zzcffVar) {
        this.zzjdt = zzchoVar;
        this.zzjdu = zzcfiVar;
        this.zzjds = zzcffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzazz();
        zzchjVar2 = this.zzjdt.zzitk;
        zzchjVar2.zzc(this.zzjdu, this.zzjds);
    }
}
