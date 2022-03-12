package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzdys {
    private static final zzdyu zzmhi = new zzdyt();

    public static <K, V> zzdyr<K, V> zza(Comparator<K> comparator) {
        return new zzdyp(comparator);
    }

    public static <A, B> zzdyr<A, B> zza(Map<A, B> map, Comparator<A> comparator) {
        return map.size() < 25 ? zzdyp.zza(new ArrayList(map.keySet()), map, zzmhi, comparator) : zzdzf.zzb(map, comparator);
    }

    public static <A, B, C> zzdyr<A, C> zzb(List<A> list, Map<B, C> map, zzdyu<A, B> zzdyuVar, Comparator<A> comparator) {
        return list.size() < 25 ? zzdyp.zza(list, map, zzdyuVar, comparator) : zzdzh.zzc(list, map, zzdyuVar, comparator);
    }

    public static <A> zzdyu<A, A> zzbrl() {
        return zzmhi;
    }
}
