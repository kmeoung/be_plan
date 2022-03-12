package com.google.android.gms.internal;

import java.util.Comparator;

/* loaded from: classes.dex */
public final class zzdyz<K, V> implements zzdza<K, V> {
    private static final zzdyz zzmhn = new zzdyz();

    private zzdyz() {
    }

    public static <K, V> zzdyz<K, V> zzbrq() {
        return zzmhn;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final K getKey() {
        return null;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final V getValue() {
        return null;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final boolean isEmpty() {
        return true;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final int size() {
        return 0;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza zza(Object obj, Object obj2, int i, zzdza zzdzaVar, zzdza zzdzaVar2) {
        return this;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zza(K k, V v, Comparator<K> comparator) {
        return new zzdzd(k, v);
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zza(K k, Comparator<K> comparator) {
        return this;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final void zza(zzdzc<K, V> zzdzcVar) {
    }

    @Override // com.google.android.gms.internal.zzdza
    public final boolean zzbrp() {
        return false;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zzbrr() {
        return this;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zzbrs() {
        return this;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zzbrt() {
        return this;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zzbru() {
        return this;
    }
}
