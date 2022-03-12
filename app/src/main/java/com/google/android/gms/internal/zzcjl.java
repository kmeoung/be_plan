package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes.dex */
public final class zzcjl implements Runnable {
    private /* synthetic */ String zzijk;
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcfx zzjdx;
    private /* synthetic */ zzcjd zzjfo;
    private /* synthetic */ boolean zzjfr = true;
    private /* synthetic */ boolean zzjfs;

    public zzcjl(zzcjd zzcjdVar, boolean z, boolean z2, zzcfx zzcfxVar, zzcff zzcffVar, String str) {
        this.zzjfo = zzcjdVar;
        this.zzjfs = z2;
        this.zzjdx = zzcfxVar;
        this.zzjds = zzcffVar;
        this.zzijk = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcgb zzcgbVar;
        zzcgbVar = this.zzjfo.zzjfi;
        if (zzcgbVar == null) {
            this.zzjfo.zzawm().zzayr().log("Discarding data. Failed to send event to service");
            return;
        }
        if (this.zzjfr) {
            this.zzjfo.zza(zzcgbVar, this.zzjfs ? null : this.zzjdx, this.zzjds);
        } else {
            try {
                if (TextUtils.isEmpty(this.zzijk)) {
                    zzcgbVar.zza(this.zzjdx, this.zzjds);
                } else {
                    zzcgbVar.zza(this.zzjdx, this.zzijk, this.zzjfo.zzawm().zzayy());
                }
            } catch (RemoteException e) {
                this.zzjfo.zzawm().zzayr().zzj("Failed to send event to the service", e);
            }
        }
        this.zzjfo.zzxg();
    }
}
