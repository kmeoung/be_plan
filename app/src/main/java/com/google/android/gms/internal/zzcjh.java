package com.google.android.gms.internal;

import android.os.RemoteException;

/* loaded from: classes.dex */
public final class zzcjh implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcjd zzjfo;

    public zzcjh(zzcjd zzcjdVar, zzcff zzcffVar) {
        this.zzjfo = zzcjdVar;
        this.zzjds = zzcffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcgb zzcgbVar;
        zzcgbVar = this.zzjfo.zzjfi;
        if (zzcgbVar == null) {
            this.zzjfo.zzawm().zzayr().log("Discarding data. Failed to send app launch");
            return;
        }
        try {
            zzcgbVar.zza(this.zzjds);
            this.zzjfo.zza(zzcgbVar, null, this.zzjds);
            this.zzjfo.zzxg();
        } catch (RemoteException e) {
            this.zzjfo.zzawm().zzayr().zzj("Failed to send app launch to the service", e);
        }
    }
}
