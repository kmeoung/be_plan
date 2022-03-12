package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzt<T> {
    public final T result;
    public final zzc zzbe;
    public final zzaa zzbf;
    public boolean zzbg;

    private zzt(zzaa zzaaVar) {
        this.zzbg = false;
        this.result = null;
        this.zzbe = null;
        this.zzbf = zzaaVar;
    }

    private zzt(T t, zzc zzcVar) {
        this.zzbg = false;
        this.result = t;
        this.zzbe = zzcVar;
        this.zzbf = null;
    }

    public static <T> zzt<T> zza(T t, zzc zzcVar) {
        return new zzt<>(t, zzcVar);
    }

    public static <T> zzt<T> zzc(zzaa zzaaVar) {
        return new zzt<>(zzaaVar);
    }
}
