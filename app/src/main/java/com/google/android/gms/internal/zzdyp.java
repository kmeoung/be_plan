package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzdyp<K, V> extends zzdyr<K, V> {
    private final K[] zzmhb;
    private final V[] zzmhc;
    private final Comparator<K> zzmhd;

    public zzdyp(Comparator<K> comparator) {
        this.zzmhb = (K[]) new Object[0];
        this.zzmhc = (V[]) new Object[0];
        this.zzmhd = comparator;
    }

    private zzdyp(Comparator<K> comparator, K[] kArr, V[] vArr) {
        this.zzmhb = kArr;
        this.zzmhc = vArr;
        this.zzmhd = comparator;
    }

    public static <A, B, C> zzdyp<A, C> zza(List<A> list, Map<B, C> map, zzdyu<A, B> zzdyuVar, Comparator<A> comparator) {
        Collections.sort(list, comparator);
        int size = list.size();
        Object[] objArr = new Object[size];
        Object[] objArr2 = new Object[size];
        int i = 0;
        for (A a : list) {
            objArr[i] = a;
            objArr2[i] = map.get(zzdyuVar.zzbj(a));
            i++;
        }
        return new zzdyp<>(comparator, objArr, objArr2);
    }

    private static <T> T[] zza(T[] tArr, int i) {
        int length = tArr.length - 1;
        T[] tArr2 = (T[]) new Object[length];
        System.arraycopy(tArr, 0, tArr2, 0, i);
        System.arraycopy(tArr, i + 1, tArr2, i, length - i);
        return tArr2;
    }

    private static <T> T[] zza(T[] tArr, int i, T t) {
        int length = tArr.length + 1;
        T[] tArr2 = (T[]) new Object[length];
        System.arraycopy(tArr, 0, tArr2, 0, i);
        tArr2[i] = t;
        System.arraycopy(tArr, i, tArr2, i + 1, (length - i) - 1);
        return tArr2;
    }

    private static <T> T[] zzb(T[] tArr, int i, T t) {
        int length = tArr.length;
        T[] tArr2 = (T[]) new Object[length];
        System.arraycopy(tArr, 0, tArr2, 0, length);
        tArr2[i] = t;
        return tArr2;
    }

    private final int zzbh(K k) {
        int i = 0;
        while (i < this.zzmhb.length && this.zzmhd.compare(this.zzmhb[i], k) < 0) {
            i++;
        }
        return i;
    }

    private final int zzbi(K k) {
        int i = 0;
        for (K k2 : this.zzmhb) {
            if (this.zzmhd.compare(k, k2) == 0) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private final Iterator<Map.Entry<K, V>> zzj(int i, boolean z) {
        return new zzdyq(this, i, z);
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final boolean containsKey(K k) {
        return zzbi(k) != -1;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final V get(K k) {
        int zzbi = zzbi(k);
        if (zzbi != -1) {
            return this.zzmhc[zzbi];
        }
        return null;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final Comparator<K> getComparator() {
        return this.zzmhd;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final int indexOf(K k) {
        return zzbi(k);
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final boolean isEmpty() {
        return this.zzmhb.length == 0;
    }

    @Override // com.google.android.gms.internal.zzdyr, java.lang.Iterable
    public final Iterator<Map.Entry<K, V>> iterator() {
        return zzj(0, false);
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final int size() {
        return this.zzmhb.length;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final void zza(zzdzc<K, V> zzdzcVar) {
        for (int i = 0; i < this.zzmhb.length; i++) {
            zzdzcVar.zzg(this.zzmhb[i], this.zzmhc[i]);
        }
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final zzdyr<K, V> zzbe(K k) {
        int zzbi = zzbi(k);
        if (zzbi == -1) {
            return this;
        }
        return new zzdyp(this.zzmhd, zza(this.zzmhb, zzbi), zza(this.zzmhc, zzbi));
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final Iterator<Map.Entry<K, V>> zzbf(K k) {
        return zzj(zzbh(k), false);
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final K zzbg(K k) {
        int zzbi = zzbi(k);
        if (zzbi == -1) {
            throw new IllegalArgumentException("Can't find predecessor of nonexistent key");
        } else if (zzbi > 0) {
            return this.zzmhb[zzbi - 1];
        } else {
            return null;
        }
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final K zzbri() {
        if (this.zzmhb.length > 0) {
            return this.zzmhb[0];
        }
        return null;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final K zzbrj() {
        if (this.zzmhb.length > 0) {
            return this.zzmhb[this.zzmhb.length - 1];
        }
        return null;
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final Iterator<Map.Entry<K, V>> zzbrk() {
        return zzj(this.zzmhb.length - 1, true);
    }

    @Override // com.google.android.gms.internal.zzdyr
    public final zzdyr<K, V> zzf(K k, V v) {
        int zzbi = zzbi(k);
        if (zzbi != -1) {
            if (this.zzmhb[zzbi] == k && this.zzmhc[zzbi] == v) {
                return this;
            }
            return new zzdyp(this.zzmhd, zzb(this.zzmhb, zzbi, k), zzb(this.zzmhc, zzbi, v));
        } else if (this.zzmhb.length > 25) {
            HashMap hashMap = new HashMap(this.zzmhb.length + 1);
            for (int i = 0; i < this.zzmhb.length; i++) {
                hashMap.put(this.zzmhb[i], this.zzmhc[i]);
            }
            hashMap.put(k, v);
            return zzdzf.zzb(hashMap, this.zzmhd);
        } else {
            int zzbh = zzbh(k);
            return new zzdyp(this.zzmhd, zza(this.zzmhb, zzbh, k), zza(this.zzmhc, zzbh, v));
        }
    }
}
