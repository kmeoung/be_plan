package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzchz implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcho zzjdt;
    private /* synthetic */ zzcfx zzjdx;

    public zzchz(zzcho zzchoVar, zzcfx zzcfxVar, zzcff zzcffVar) {
        this.zzjdt = zzchoVar;
        this.zzjdx = zzcfxVar;
        this.zzjds = zzcffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzazz();
        zzchjVar2 = this.zzjdt.zzitk;
        zzchjVar2.zzb(this.zzjdx, this.zzjds);
    }
}
