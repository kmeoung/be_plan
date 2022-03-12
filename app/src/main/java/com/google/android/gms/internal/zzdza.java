package com.google.android.gms.internal;

import java.util.Comparator;

/* loaded from: classes.dex */
public interface zzdza<K, V> {
    K getKey();

    V getValue();

    boolean isEmpty();

    int size();

    zzdza zza(Object obj, Object obj2, int i, zzdza zzdzaVar, zzdza zzdzaVar2);

    zzdza<K, V> zza(K k, V v, Comparator<K> comparator);

    zzdza<K, V> zza(K k, Comparator<K> comparator);

    void zza(zzdzc<K, V> zzdzcVar);

    boolean zzbrp();

    zzdza<K, V> zzbrr();

    zzdza<K, V> zzbrs();

    zzdza<K, V> zzbrt();

    zzdza<K, V> zzbru();
}
