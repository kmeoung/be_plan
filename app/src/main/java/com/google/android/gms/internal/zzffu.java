package com.google.android.gms.internal;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* loaded from: classes.dex */
public class zzffu<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzkqq;
    private final int zzpdv;
    private List<zzffz> zzpdw;
    private Map<K, V> zzpdx;
    private volatile zzfgb zzpdy;
    private Map<K, V> zzpdz;

    private zzffu(int i) {
        this.zzpdv = i;
        this.zzpdw = Collections.emptyList();
        this.zzpdx = Collections.emptyMap();
        this.zzpdz = Collections.emptyMap();
    }

    public /* synthetic */ zzffu(int i, zzffv zzffvVar) {
        this(i);
    }

    private final int zza(K k) {
        int size = this.zzpdw.size() - 1;
        if (size >= 0) {
            int compareTo = k.compareTo((Comparable) this.zzpdw.get(size).getKey());
            if (compareTo > 0) {
                return -(size + 2);
            }
            if (compareTo == 0) {
                return size;
            }
        }
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) / 2;
            int compareTo2 = k.compareTo((Comparable) this.zzpdw.get(i2).getKey());
            if (compareTo2 < 0) {
                size = i2 - 1;
            } else if (compareTo2 <= 0) {
                return i2;
            } else {
                i = i2 + 1;
            }
        }
        return -(i + 1);
    }

    public final void zzcwl() {
        if (this.zzkqq) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzcwm() {
        zzcwl();
        if (this.zzpdx.isEmpty() && !(this.zzpdx instanceof TreeMap)) {
            this.zzpdx = new TreeMap();
            this.zzpdz = ((TreeMap) this.zzpdx).descendingMap();
        }
        return (SortedMap) this.zzpdx;
    }

    public static <FieldDescriptorType extends zzfed<FieldDescriptorType>> zzffu<FieldDescriptorType, Object> zzlp(int i) {
        return new zzffv(i);
    }

    public final V zzlr(int i) {
        zzcwl();
        V v = (V) this.zzpdw.remove(i).getValue();
        if (!this.zzpdx.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zzcwm().entrySet().iterator();
            this.zzpdw.add(new zzffz(this, it.next()));
            it.remove();
        }
        return v;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzcwl();
        if (!this.zzpdw.isEmpty()) {
            this.zzpdw.clear();
        }
        if (!this.zzpdx.isEmpty()) {
            this.zzpdx.clear();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza((zzffu<K, V>) comparable) >= 0 || this.zzpdx.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zzpdy == null) {
            this.zzpdy = new zzfgb(this, null);
        }
        return this.zzpdy;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzffu)) {
            return super.equals(obj);
        }
        zzffu zzffuVar = (zzffu) obj;
        int size = size();
        if (size != zzffuVar.size()) {
            return false;
        }
        int zzcwj = zzcwj();
        if (zzcwj != zzffuVar.zzcwj()) {
            return entrySet().equals(zzffuVar.entrySet());
        }
        for (int i = 0; i < zzcwj; i++) {
            if (!zzlq(i).equals(zzffuVar.zzlq(i))) {
                return false;
            }
        }
        if (zzcwj != size) {
            return this.zzpdx.equals(zzffuVar.zzpdx);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza((zzffu<K, V>) comparable);
        return zza >= 0 ? (V) this.zzpdw.get(zza).getValue() : this.zzpdx.get(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int zzcwj = zzcwj();
        int i = 0;
        for (int i2 = 0; i2 < zzcwj; i2++) {
            i += this.zzpdw.get(i2).hashCode();
        }
        return this.zzpdx.size() > 0 ? i + this.zzpdx.hashCode() : i;
    }

    public final boolean isImmutable() {
        return this.zzkqq;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((zzffu<K, V>) ((Comparable) obj), (Comparable) obj2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzcwl();
        Comparable comparable = (Comparable) obj;
        int zza = zza((zzffu<K, V>) comparable);
        if (zza >= 0) {
            return (V) zzlr(zza);
        }
        if (this.zzpdx.isEmpty()) {
            return null;
        }
        return this.zzpdx.remove(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzpdw.size() + this.zzpdx.size();
    }

    public final V zza(K k, V v) {
        zzcwl();
        int zza = zza((zzffu<K, V>) k);
        if (zza >= 0) {
            return (V) this.zzpdw.get(zza).setValue(v);
        }
        zzcwl();
        if (this.zzpdw.isEmpty() && !(this.zzpdw instanceof ArrayList)) {
            this.zzpdw = new ArrayList(this.zzpdv);
        }
        int i = -(zza + 1);
        if (i >= this.zzpdv) {
            return zzcwm().put(k, v);
        }
        if (this.zzpdw.size() == this.zzpdv) {
            zzffz remove = this.zzpdw.remove(this.zzpdv - 1);
            zzcwm().put((K) ((Comparable) remove.getKey()), (V) remove.getValue());
        }
        this.zzpdw.add(i, new zzffz(this, k, v));
        return null;
    }

    public void zzbim() {
        if (!this.zzkqq) {
            this.zzpdx = this.zzpdx.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzpdx);
            this.zzpdz = this.zzpdz.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzpdz);
            this.zzkqq = true;
        }
    }

    public final int zzcwj() {
        return this.zzpdw.size();
    }

    public final Iterable<Map.Entry<K, V>> zzcwk() {
        return this.zzpdx.isEmpty() ? zzffw.zzcwn() : this.zzpdx.entrySet();
    }

    public final Map.Entry<K, V> zzlq(int i) {
        return this.zzpdw.get(i);
    }
}
