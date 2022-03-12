package com.google.android.gms.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzj implements Runnable {
    private final zzp zzt;
    private final zzt zzu;
    private final Runnable zzv;

    public zzj(zzh zzhVar, zzp zzpVar, zzt zztVar, Runnable runnable) {
        this.zzt = zzpVar;
        this.zzu = zztVar;
        this.zzv = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzu.zzbf == null) {
            this.zzt.zza((zzp) this.zzu.result);
        } else {
            this.zzt.zzb(this.zzu.zzbf);
        }
        if (this.zzu.zzbg) {
            this.zzt.zzb("intermediate-response");
        } else {
            this.zzt.zzc("done");
        }
        if (this.zzv != null) {
            this.zzv.run();
        }
    }
}
