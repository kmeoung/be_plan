package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcix implements Runnable {
    private /* synthetic */ zzcik zzjeh;

    public zzcix(zzcik zzcikVar) {
        this.zzjeh = zzcikVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcik zzcikVar = this.zzjeh;
        zzcikVar.zzut();
        zzcikVar.zzwu();
        zzcikVar.zzawm().zzayw().log("Resetting analytics data (FE)");
        zzcikVar.zzawd().resetAnalyticsData();
    }
}
