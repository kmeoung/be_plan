package com.google.android.gms.internal;

import com.google.android.gms.measurement.AppMeasurement;

/* loaded from: classes.dex */
final class zzcig implements Runnable {
    private /* synthetic */ String zzijk;
    private /* synthetic */ zzcho zzjdt;
    private /* synthetic */ String zzjdz;
    private /* synthetic */ String zzjea;
    private /* synthetic */ long zzjeb;

    public zzcig(zzcho zzchoVar, String str, String str2, String str3, long j) {
        this.zzjdt = zzchoVar;
        this.zzjdz = str;
        this.zzijk = str2;
        this.zzjea = str3;
        this.zzjeb = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        if (this.zzjdz == null) {
            zzchjVar2 = this.zzjdt.zzitk;
            zzchjVar2.zzawe().zza(this.zzijk, (AppMeasurement.zzb) null);
            return;
        }
        AppMeasurement.zzb zzbVar = new AppMeasurement.zzb();
        zzbVar.zzitp = this.zzjea;
        zzbVar.zzitq = this.zzjdz;
        zzbVar.zzitr = this.zzjeb;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzawe().zza(this.zzijk, zzbVar);
    }
}
