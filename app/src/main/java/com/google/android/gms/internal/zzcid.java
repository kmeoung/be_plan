package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzcid implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcho zzjdt;
    private /* synthetic */ zzckk zzjdy;

    public zzcid(zzcho zzchoVar, zzckk zzckkVar, zzcff zzcffVar) {
        this.zzjdt = zzchoVar;
        this.zzjdy = zzckkVar;
        this.zzjds = zzcffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzazz();
        zzchjVar2 = this.zzjdt.zzitk;
        zzchjVar2.zzb(this.zzjdy, this.zzjds);
    }
}
