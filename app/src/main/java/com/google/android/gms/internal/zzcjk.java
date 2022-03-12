package com.google.android.gms.internal;

import android.os.RemoteException;

/* loaded from: classes.dex */
public final class zzcjk implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcjd zzjfo;

    public zzcjk(zzcjd zzcjdVar, zzcff zzcffVar) {
        this.zzjfo = zzcjdVar;
        this.zzjds = zzcffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcgb zzcgbVar;
        zzcgbVar = this.zzjfo.zzjfi;
        if (zzcgbVar == null) {
            this.zzjfo.zzawm().zzayr().log("Failed to send measurementEnabled to service");
            return;
        }
        try {
            zzcgbVar.zzb(this.zzjds);
            this.zzjfo.zzxg();
        } catch (RemoteException e) {
            this.zzjfo.zzawm().zzayr().zzj("Failed to send measurementEnabled to the service", e);
        }
    }
}
