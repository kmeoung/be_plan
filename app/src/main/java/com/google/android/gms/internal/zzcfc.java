package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcfc implements Runnable {
    private /* synthetic */ String zzbdq;
    private /* synthetic */ long zzitz;
    private /* synthetic */ zzcfa zziua;

    public zzcfc(zzcfa zzcfaVar, String str, long j) {
        this.zziua = zzcfaVar;
        this.zzbdq = str;
        this.zzitz = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zziua.zzf(this.zzbdq, this.zzitz);
    }
}
