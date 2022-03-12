package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzcia implements Runnable {
    private /* synthetic */ String zzijk;
    private /* synthetic */ zzcho zzjdt;
    private /* synthetic */ zzcfx zzjdx;

    public zzcia(zzcho zzchoVar, zzcfx zzcfxVar, String str) {
        this.zzjdt = zzchoVar;
        this.zzjdx = zzcfxVar;
        this.zzijk = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzazz();
        zzchjVar2 = this.zzjdt.zzitk;
        zzchjVar2.zzb(this.zzjdx, this.zzijk);
    }
}
