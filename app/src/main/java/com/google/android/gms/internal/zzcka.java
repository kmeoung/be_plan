package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcka implements Runnable {
    private /* synthetic */ zzchj zzjay;
    private /* synthetic */ Runnable zzjgg;

    public zzcka(zzcjx zzcjxVar, zzchj zzchjVar, Runnable runnable) {
        this.zzjay = zzchjVar;
        this.zzjgg = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjay.zzazz();
        this.zzjay.zzi(this.zzjgg);
        this.zzjay.zzazv();
    }
}
