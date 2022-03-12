package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;

/* loaded from: classes.dex */
public final class zzcja implements Runnable {
    private /* synthetic */ boolean zzjfb;
    private /* synthetic */ AppMeasurement.zzb zzjfc;
    private /* synthetic */ zzcjc zzjfd;
    private /* synthetic */ zzciz zzjfe;

    public zzcja(zzciz zzcizVar, boolean z, AppMeasurement.zzb zzbVar, zzcjc zzcjcVar) {
        this.zzjfe = zzcizVar;
        this.zzjfb = z;
        this.zzjfc = zzbVar;
        this.zzjfd = zzcjcVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzjfb && this.zzjfe.zzjes != null) {
            this.zzjfe.zza(this.zzjfe.zzjes);
        }
        if (this.zzjfc == null || this.zzjfc.zzitr != this.zzjfd.zzitr || !zzckn.zzas(this.zzjfc.zzitq, this.zzjfd.zzitq) || !zzckn.zzas(this.zzjfc.zzitp, this.zzjfd.zzitp)) {
            Bundle bundle = new Bundle();
            zzciz.zza(this.zzjfd, bundle);
            if (this.zzjfc != null) {
                if (this.zzjfc.zzitp != null) {
                    bundle.putString("_pn", this.zzjfc.zzitp);
                }
                bundle.putString("_pc", this.zzjfc.zzitq);
                bundle.putLong("_pi", this.zzjfc.zzitr);
            }
            this.zzjfe.zzawa().zzc("auto", "_vs", bundle);
        }
        this.zzjfe.zzjes = this.zzjfd;
        this.zzjfe.zzawd().zza(this.zzjfd);
    }
}
