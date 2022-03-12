package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcir implements Runnable {
    private /* synthetic */ zzcik zzjeh;
    private /* synthetic */ long zzjel;

    public zzcir(zzcik zzcikVar, long j) {
        this.zzjeh = zzcikVar;
        this.zzjel = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjeh.zzawn().zzjak.set(this.zzjel);
        this.zzjeh.zzawm().zzayw().zzj("Session timeout duration set", Long.valueOf(this.zzjel));
    }
}
