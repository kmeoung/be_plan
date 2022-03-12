package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzcif implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcho zzjdt;

    public zzcif(zzcho zzchoVar, zzcff zzcffVar) {
        this.zzjdt = zzchoVar;
        this.zzjds = zzcffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzazz();
        zzchjVar2 = this.zzjdt.zzitk;
        zzchjVar2.zzf(this.zzjds);
    }
}
