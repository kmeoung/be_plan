package com.google.android.gms.internal;

import android.os.RemoteException;

/* loaded from: classes.dex */
public final class zzcjf implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcjd zzjfo;

    public zzcjf(zzcjd zzcjdVar, zzcff zzcffVar) {
        this.zzjfo = zzcjdVar;
        this.zzjds = zzcffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcgb zzcgbVar;
        zzcgbVar = this.zzjfo.zzjfi;
        if (zzcgbVar == null) {
            this.zzjfo.zzawm().zzayr().log("Failed to reset data on the service; null service");
            return;
        }
        try {
            zzcgbVar.zzd(this.zzjds);
        } catch (RemoteException e) {
            this.zzjfo.zzawm().zzayr().zzj("Failed to reset data on the service", e);
        }
        this.zzjfo.zzxg();
    }
}
