package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcfd implements Runnable {
    private /* synthetic */ long zzitz;
    private /* synthetic */ zzcfa zziua;

    public zzcfd(zzcfa zzcfaVar, long j) {
        this.zziua = zzcfaVar;
        this.zzitz = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zziua.zzaj(this.zzitz);
    }
}
