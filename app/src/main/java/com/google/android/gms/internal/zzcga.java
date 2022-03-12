package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;

/* loaded from: classes.dex */
public final class zzcga<V> {
    private final String zzbfo;
    private final V zzdup;
    private final zzbdv<V> zzduq;

    private zzcga(String str, zzbdv<V> zzbdvVar, V v) {
        zzbq.checkNotNull(zzbdvVar);
        this.zzduq = zzbdvVar;
        this.zzdup = v;
        this.zzbfo = str;
    }

    public static zzcga<Long> zzb(String str, long j, long j2) {
        return new zzcga<>(str, zzbdv.zza(str, Long.valueOf(j2)), Long.valueOf(j));
    }

    public static zzcga<Boolean> zzb(String str, boolean z, boolean z2) {
        return new zzcga<>(str, zzbdv.zze(str, z2), Boolean.valueOf(z));
    }

    public static zzcga<String> zzi(String str, String str2, String str3) {
        return new zzcga<>(str, zzbdv.zzs(str, str3), str2);
    }

    public static zzcga<Integer> zzm(String str, int i, int i2) {
        return new zzcga<>(str, zzbdv.zza(str, Integer.valueOf(i2)), Integer.valueOf(i));
    }

    public final V get() {
        return this.zzdup;
    }

    public final V get(V v) {
        return v != null ? v : this.zzdup;
    }

    public final String getKey() {
        return this.zzbfo;
    }
}
