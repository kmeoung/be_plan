package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzcjw implements Runnable {
    private /* synthetic */ zzcjr zzjfy;

    public zzcjw(zzcjr zzcjrVar) {
        this.zzjfy = zzcjrVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjfy.zzjfo.zzjfi = null;
        this.zzjfy.zzjfo.zzbah();
    }
}
