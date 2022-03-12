package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcgk implements Runnable {
    private /* synthetic */ String zzizi;
    private /* synthetic */ zzcgj zzizj;

    public zzcgk(zzcgj zzcgjVar, String str) {
        this.zzizj = zzcgjVar;
        this.zzizi = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcgu zzawn = this.zzizj.zzitk.zzawn();
        if (!zzawn.isInitialized()) {
            this.zzizj.zzk(6, "Persisted config not initialized. Not logging error/warn");
        } else {
            zzawn.zzizv.zzg(this.zzizi, 1L);
        }
    }
}
