package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzdzh<A, B, C> {
    private final Map<B, C> values;
    private final List<A> zzmhv;
    private final zzdyu<A, B> zzmhw;
    private zzdze<A, C> zzmhx;
    private zzdze<A, C> zzmhy;

    private zzdzh(List<A> list, Map<B, C> map, zzdyu<A, B> zzdyuVar) {
        this.zzmhv = list;
        this.values = map;
        this.zzmhw = zzdyuVar;
    }

    private final C zzbo(A a) {
        return this.values.get(this.zzmhw.zzbj(a));
    }

    public static <A, B, C> zzdzf<A, C> zzc(List<A> list, Map<B, C> map, zzdyu<A, B> zzdyuVar, Comparator<A> comparator) {
        int i;
        zzdzh zzdzhVar = new zzdzh(list, map, zzdyuVar);
        Collections.sort(list, comparator);
        Iterator<zzdzk> it = new zzdzi(list.size()).iterator();
        int size = list.size();
        while (it.hasNext()) {
            zzdzk next = it.next();
            size -= next.zzmic;
            if (next.zzmib) {
                i = zzdzb.zzmhp;
            } else {
                zzdzhVar.zzf(zzdzb.zzmhp, next.zzmic, size);
                size -= next.zzmic;
                i = zzdzb.zzmho;
            }
            zzdzhVar.zzf(i, next.zzmic, size);
        }
        return new zzdzf<>(zzdzhVar.zzmhx == null ? zzdyz.zzbrq() : zzdzhVar.zzmhx, comparator);
    }

    private final void zzf(int i, int i2, int i3) {
        zzdza<A, C> zzu = zzu(i3 + 1, i2 - 1);
        A a = this.zzmhv.get(i3);
        zzdze<A, C> zzdzdVar = i == zzdzb.zzmho ? new zzdzd<>(a, zzbo(a), null, zzu) : new zzdyy<>(a, zzbo(a), null, zzu);
        if (this.zzmhx == null) {
            this.zzmhx = zzdzdVar;
        } else {
            this.zzmhy.zza(zzdzdVar);
        }
        this.zzmhy = zzdzdVar;
    }

    private final zzdza<A, C> zzu(int i, int i2) {
        if (i2 == 0) {
            return zzdyz.zzbrq();
        }
        if (i2 == 1) {
            A a = this.zzmhv.get(i);
            return new zzdyy(a, zzbo(a), null, null);
        }
        int i3 = i2 / 2;
        int i4 = i + i3;
        zzdza<A, C> zzu = zzu(i, i3);
        zzdza<A, C> zzu2 = zzu(i4 + 1, i3);
        A a2 = this.zzmhv.get(i4);
        return new zzdyy(a2, zzbo(a2), zzu, zzu2);
    }
}
