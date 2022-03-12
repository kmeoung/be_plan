package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.measurement.AppMeasurement;

/* loaded from: classes.dex */
public final class zzcji implements Runnable {
    private /* synthetic */ zzcjd zzjfo;
    private /* synthetic */ AppMeasurement.zzb zzjfq;

    public zzcji(zzcjd zzcjdVar, AppMeasurement.zzb zzbVar) {
        this.zzjfo = zzcjdVar;
        this.zzjfq = zzbVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcgb zzcgbVar;
        long j;
        String str;
        String str2;
        String packageName;
        zzcgbVar = this.zzjfo.zzjfi;
        if (zzcgbVar == null) {
            this.zzjfo.zzawm().zzayr().log("Failed to send current screen to service");
            return;
        }
        try {
            if (this.zzjfq == null) {
                j = 0;
                str = null;
                str2 = null;
                packageName = this.zzjfo.getContext().getPackageName();
            } else {
                j = this.zzjfq.zzitr;
                str = this.zzjfq.zzitp;
                str2 = this.zzjfq.zzitq;
                packageName = this.zzjfo.getContext().getPackageName();
            }
            zzcgbVar.zza(j, str, str2, packageName);
            this.zzjfo.zzxg();
        } catch (RemoteException e) {
            this.zzjfo.zzawm().zzayr().zzj("Failed to send current screen to the service", e);
        }
    }
}
