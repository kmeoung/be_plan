package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcgl {
    private final int mPriority;
    private /* synthetic */ zzcgj zzizj;
    private final boolean zzizk;
    private final boolean zzizl;

    public zzcgl(zzcgj zzcgjVar, int i, boolean z, boolean z2) {
        this.zzizj = zzcgjVar;
        this.mPriority = i;
        this.zzizk = z;
        this.zzizl = z2;
    }

    public final void log(String str) {
        this.zzizj.zza(this.mPriority, this.zzizk, this.zzizl, str, null, null, null);
    }

    public final void zzd(String str, Object obj, Object obj2, Object obj3) {
        this.zzizj.zza(this.mPriority, this.zzizk, this.zzizl, str, obj, obj2, obj3);
    }

    public final void zze(String str, Object obj, Object obj2) {
        this.zzizj.zza(this.mPriority, this.zzizk, this.zzizl, str, obj, obj2, null);
    }

    public final void zzj(String str, Object obj) {
        this.zzizj.zza(this.mPriority, this.zzizk, this.zzizl, str, obj, null, null);
    }
}
