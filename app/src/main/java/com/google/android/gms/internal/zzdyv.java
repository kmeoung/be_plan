package com.google.android.gms.internal;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

/* loaded from: classes.dex */
public final class zzdyv<K, V> implements Iterator<Map.Entry<K, V>> {
    private final Stack<zzdze<K, V>> zzmhj = new Stack<>();
    private final boolean zzmhk;

    public zzdyv(zzdza<K, V> zzdzaVar, K k, Comparator<K> comparator, boolean z) {
        this.zzmhk = z;
        while (!zzdzaVar.isEmpty()) {
            int compare = k != null ? z ? comparator.compare(k, zzdzaVar.getKey()) : comparator.compare(zzdzaVar.getKey(), k) : 1;
            if (compare < 0) {
                zzdzaVar = !z ? zzdzaVar.zzbrs() : zzdzaVar.zzbrr();
            } else if (compare == 0) {
                this.zzmhj.push((zzdze) zzdzaVar);
                return;
            } else {
                this.zzmhj.push((zzdze) zzdzaVar);
                if (z) {
                }
            }
        }
    }

    public final Map.Entry<K, V> next() {
        try {
            zzdze<K, V> pop = this.zzmhj.pop();
            AbstractMap.SimpleEntry simpleEntry = new AbstractMap.SimpleEntry(pop.getKey(), pop.getValue());
            if (this.zzmhk) {
                for (zzdza<K, V> zzbrr = pop.zzbrr(); !zzbrr.isEmpty(); zzbrr = zzbrr.zzbrs()) {
                    this.zzmhj.push((zzdze) zzbrr);
                }
            } else {
                for (zzdza<K, V> zzbrs = pop.zzbrs(); !zzbrs.isEmpty(); zzbrs = zzbrs.zzbrr()) {
                    this.zzmhj.push((zzdze) zzbrs);
                }
            }
            return simpleEntry;
        } catch (EmptyStackException unused) {
            throw new NoSuchElementException();
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzmhj.size() > 0;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("remove called on immutable collection");
    }
}
