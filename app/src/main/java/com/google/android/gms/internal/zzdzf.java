package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzdzf<K, V> extends zzdyr<K, V> {
    private Comparator<K> zzmhd;
    private zzdza<K, V> zzmhu;

    private zzdzf(zzdza<K, V> zzdzaVar, Comparator<K> comparator) {
        this.zzmhu = zzdzaVar;
        this.zzmhd = comparator;
    }

    public static <A, B> zzdzf<A, B> zzb(Map<A, B> map, Comparator<A> comparator) {
        return zzdzh.zzc(new ArrayList(map.keySet()), map, zzdys.zzbrl(), comparator);
    }

    private final zzdza<K, V> zzbn(K k) {
        zzdza<K, V> zzdzaVar = this.zzmhu;
        while (!zzdzaVar.isEmpty()) {
            int compare = this.zzmhd.compare(k, zzdzaVar.getKey());
            if (compare < 0) {
                zzdzaVar = zzdzaVar.zzbrr();
            } else if (compare == 0) {
                return zzdzaVar;
            } else {
                zzdzaVar = zzdzaVar.zzbrs();
            }
        }
        return null;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final boolean containsKey(K k) {
        return zzbn(k) != null;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final V get(K k) {
        zzdza<K, V> zzbn = zzbn(k);
        if (zzbn != null) {
            return zzbn.getValue();
        }
        return null;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final Comparator<K> getComparator() {
        return this.zzmhd;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final int indexOf(K k) {
        zzdza<K, V> zzdzaVar = this.zzmhu;
        int i = 0;
        while (!zzdzaVar.isEmpty()) {
            int compare = this.zzmhd.compare(k, zzdzaVar.getKey());
            if (compare == 0) {
                return i + zzdzaVar.zzbrr().size();
            }
            if (compare < 0) {
                zzdzaVar = zzdzaVar.zzbrr();
            } else {
                i += zzdzaVar.zzbrr().size() + 1;
                zzdzaVar = zzdzaVar.zzbrs();
            }
        }
        return -1;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final boolean isEmpty() {
        return this.zzmhu.isEmpty();
    }

    @Override // com.google.android.gms.internal.zzdyr, java.lang.Iterable
    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzdyv(this.zzmhu, null, this.zzmhd, false);
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final int size() {
        return this.zzmhu.size();
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final void zza(zzdzc<K, V> zzdzcVar) {
        this.zzmhu.zza(zzdzcVar);
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final zzdyr<K, V> zzbe(K k) {
        return !containsKey(k) ? this : new zzdzf(this.zzmhu.zza(k, this.zzmhd).zza(null, null, zzdzb.zzmhp, null, null), this.zzmhd);
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final Iterator<Map.Entry<K, V>> zzbf(K k) {
        return new zzdyv(this.zzmhu, k, this.zzmhd, false);
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final K zzbg(K k) {
        zzdza<K, V> zzdzaVar = this.zzmhu;
        zzdza<K, V> zzdzaVar2 = null;
        while (!zzdzaVar.isEmpty()) {
            int compare = this.zzmhd.compare(k, zzdzaVar.getKey());
            if (compare == 0) {
                if (!zzdzaVar.zzbrr().isEmpty()) {
                    zzdza<K, V> zzbrr = zzdzaVar.zzbrr();
                    while (!zzbrr.zzbrs().isEmpty()) {
                        zzbrr = zzbrr.zzbrs();
                    }
                    return zzbrr.getKey();
                } else if (zzdzaVar2 != null) {
                    return zzdzaVar2.getKey();
                } else {
                    return null;
                }
            } else if (compare < 0) {
                zzdzaVar = zzdzaVar.zzbrr();
            } else {
                zzdzaVar = zzdzaVar.zzbrs();
                zzdzaVar2 = zzdzaVar;
            }
        }
        String valueOf = String.valueOf(k);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 50);
        sb.append("Couldn't find predecessor key of non-present key: ");
        sb.append(valueOf);
        throw new IllegalArgumentException(sb.toString());
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final K zzbri() {
        return this.zzmhu.zzbrt().getKey();
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final K zzbrj() {
        return this.zzmhu.zzbru().getKey();
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final Iterator<Map.Entry<K, V>> zzbrk() {
        return new zzdyv(this.zzmhu, null, this.zzmhd, true);
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final zzdyr<K, V> zzf(K k, V v) {
        return new zzdzf(this.zzmhu.zza(k, v, this.zzmhd).zza(null, null, zzdzb.zzmhp, null, null), this.zzmhd);
    }
}
