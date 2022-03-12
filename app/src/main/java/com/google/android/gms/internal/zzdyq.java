package com.google.android.gms.internal;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzdyq implements Iterator<Map.Entry<K, V>> {
    private int zzmhe;
    private /* synthetic */ int zzmhf;
    private /* synthetic */ boolean zzmhg;
    private /* synthetic */ zzdyp zzmhh;

    public zzdyq(zzdyp zzdypVar, int i, boolean z) {
        this.zzmhh = zzdypVar;
        this.zzmhf = i;
        this.zzmhg = z;
        this.zzmhe = this.zzmhf;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        Object[] objArr;
        if (this.zzmhg) {
            return this.zzmhe >= 0;
        }
        int i = this.zzmhe;
        objArr = this.zzmhh.zzmhb;
        return i < objArr.length;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        Object[] objArr;
        Object[] objArr2;
        objArr = this.zzmhh.zzmhb;
        Object obj = objArr[this.zzmhe];
        objArr2 = this.zzmhh.zzmhc;
        Object obj2 = objArr2[this.zzmhe];
        this.zzmhe = this.zzmhg ? this.zzmhe - 1 : this.zzmhe + 1;
        return new AbstractMap.SimpleImmutableEntry(obj, obj2);
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Can't remove elements from ImmutableSortedMap");
    }
}
