package com.google.android.gms.internal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class zzdyr<K, V> implements Iterable<Map.Entry<K, V>> {
    public abstract boolean containsKey(K k);

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzdyr)) {
            return false;
        }
        zzdyr zzdyrVar = (zzdyr) obj;
        if (!getComparator().equals(zzdyrVar.getComparator()) || size() != zzdyrVar.size()) {
            return false;
        }
        Iterator<Map.Entry<K, V>> it = iterator();
        Iterator<Map.Entry<K, V>> it2 = zzdyrVar.iterator();
        while (it.hasNext()) {
            if (!it.next().equals(it2.next())) {
                return false;
            }
        }
        return true;
    }

    public abstract V get(K k);

    public abstract Comparator<K> getComparator();

    public int hashCode() {
        int hashCode = getComparator().hashCode();
        Iterator<Map.Entry<K, V>> it = iterator();
        while (it.hasNext()) {
            hashCode = (hashCode * 31) + it.next().hashCode();
        }
        return hashCode;
    }

    public abstract int indexOf(K k);

    public abstract boolean isEmpty();

    @Override // java.lang.Iterable
    public abstract Iterator<Map.Entry<K, V>> iterator();

    public abstract int size();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{");
        Iterator<Map.Entry<K, V>> it = iterator();
        boolean z = true;
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append("(");
            sb.append(next.getKey());
            sb.append("=>");
            sb.append(next.getValue());
            sb.append(")");
        }
        sb.append("};");
        return sb.toString();
    }

    public abstract void zza(zzdzc<K, V> zzdzcVar);

    public abstract zzdyr<K, V> zzbe(K k);

    public abstract Iterator<Map.Entry<K, V>> zzbf(K k);

    public abstract K zzbg(K k);

    public abstract K zzbri();

    public abstract K zzbrj();

    public abstract Iterator<Map.Entry<K, V>> zzbrk();

    public abstract zzdyr<K, V> zzf(K k, V v);
}
