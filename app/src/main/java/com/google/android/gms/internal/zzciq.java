package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzciq implements Runnable {
    private /* synthetic */ zzcik zzjeh;
    private /* synthetic */ long zzjel;

    public zzciq(zzcik zzcikVar, long j) {
        this.zzjeh = zzcikVar;
        this.zzjel = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjeh.zzawn().zzjaj.set(this.zzjel);
        this.zzjeh.zzawm().zzayw().zzj("Minimum session duration set", Long.valueOf(this.zzjel));
    }
}
