package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzchk implements Runnable {
    private /* synthetic */ zzchj zzjdm;

    public zzchk(zzchj zzchjVar) {
        this.zzjdm = zzchjVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjdm.zzazk();
        this.zzjdm.start();
    }
}
