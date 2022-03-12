package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzckg implements Runnable {
    private /* synthetic */ long zzitz;
    private /* synthetic */ zzckc zzjgk;

    public zzckg(zzckc zzckcVar, long j) {
        this.zzjgk = zzckcVar;
        this.zzitz = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjgk.zzbe(this.zzitz);
    }
}
