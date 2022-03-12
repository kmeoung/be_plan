package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzcju implements Runnable {
    private /* synthetic */ zzcjr zzjfy;
    private /* synthetic */ zzcgb zzjfz;

    public zzcju(zzcjr zzcjrVar, zzcgb zzcgbVar) {
        this.zzjfy = zzcjrVar;
        this.zzjfz = zzcgbVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzjfy) {
            this.zzjfy.zzjfv = false;
            if (!this.zzjfy.zzjfo.isConnected()) {
                this.zzjfy.zzjfo.zzawm().zzayw().log("Connected to remote service");
                this.zzjfy.zzjfo.zza(this.zzjfz);
            }
        }
    }
}
