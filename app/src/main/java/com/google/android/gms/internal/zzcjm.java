package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes.dex */
public final class zzcjm implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcjd zzjfo;
    private /* synthetic */ boolean zzjfr = true;
    private /* synthetic */ boolean zzjfs;
    private /* synthetic */ zzcfi zzjft;
    private /* synthetic */ zzcfi zzjfu;

    public zzcjm(zzcjd zzcjdVar, boolean z, boolean z2, zzcfi zzcfiVar, zzcff zzcffVar, zzcfi zzcfiVar2) {
        this.zzjfo = zzcjdVar;
        this.zzjfs = z2;
        this.zzjft = zzcfiVar;
        this.zzjds = zzcffVar;
        this.zzjfu = zzcfiVar2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcgb zzcgbVar;
        zzcgbVar = this.zzjfo.zzjfi;
        if (zzcgbVar == null) {
            this.zzjfo.zzawm().zzayr().log("Discarding data. Failed to send conditional user property to service");
            return;
        }
        if (this.zzjfr) {
            this.zzjfo.zza(zzcgbVar, this.zzjfs ? null : this.zzjft, this.zzjds);
        } else {
            try {
                if (TextUtils.isEmpty(this.zzjfu.packageName)) {
                    zzcgbVar.zza(this.zzjft, this.zzjds);
                } else {
                    zzcgbVar.zzb(this.zzjft);
                }
            } catch (RemoteException e) {
                this.zzjfo.zzawm().zzayr().zzj("Failed to send conditional user property to the service", e);
            }
        }
        this.zzjfo.zzxg();
    }
}
