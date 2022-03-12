package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcil implements Runnable {
    private /* synthetic */ boolean val$enabled;
    private /* synthetic */ zzcik zzjeh;

    public zzcil(zzcik zzcikVar, boolean z) {
        this.zzjeh = zzcikVar;
        this.val$enabled = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjeh.zzbo(this.val$enabled);
    }
}
