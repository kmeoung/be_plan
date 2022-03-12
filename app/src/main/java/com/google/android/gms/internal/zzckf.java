package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzckf implements Runnable {
    private /* synthetic */ long zzitz;
    private /* synthetic */ zzckc zzjgk;

    public zzckf(zzckc zzckcVar, long j) {
        this.zzjgk = zzckcVar;
        this.zzitz = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjgk.zzbd(this.zzitz);
    }
}
