package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzcht implements Runnable {
    private /* synthetic */ zzcho zzjdt;
    private /* synthetic */ zzcfi zzjdu;

    public zzcht(zzcho zzchoVar, zzcfi zzcfiVar) {
        this.zzjdt = zzchoVar;
        this.zzjdu = zzcfiVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzazz();
        zzchjVar2 = this.zzjdt.zzitk;
        zzchjVar2.zzd(this.zzjdu);
    }
}
