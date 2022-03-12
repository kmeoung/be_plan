package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzcjs implements Runnable {
    private /* synthetic */ zzcgb zzjfx;
    private /* synthetic */ zzcjr zzjfy;

    public zzcjs(zzcjr zzcjrVar, zzcgb zzcgbVar) {
        this.zzjfy = zzcjrVar;
        this.zzjfx = zzcgbVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzjfy) {
            this.zzjfy.zzjfv = false;
            if (!this.zzjfy.zzjfo.isConnected()) {
                this.zzjfy.zzjfo.zzawm().zzayx().log("Connected to service");
                this.zzjfy.zzjfo.zza(this.zzjfx);
            }
        }
    }
}
