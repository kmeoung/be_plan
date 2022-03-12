package com.google.android.gms.internal;

import java.util.Map;

/* loaded from: classes.dex */
public final class zzffz implements Comparable<zzffz>, Map.Entry<K, V> {
    private V value;
    private final Comparable zzpec;
    private /* synthetic */ zzffu zzped;

    /* JADX WARN: Multi-variable type inference failed */
    public zzffz(zzffu zzffuVar, K k, V v) {
        this.zzped = zzffuVar;
        this.zzpec = k;
        this.value = v;
    }

    public zzffz(zzffu zzffuVar, Map.Entry<K, V> entry) {
        this(zzffuVar, (Comparable) entry.getKey(), entry.getValue());
    }

    private static boolean equals(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zzffz zzffzVar) {
        return ((Comparable) getKey()).compareTo((Comparable) zzffzVar.getKey());
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return equals(this.zzpec, entry.getKey()) && equals(this.value, entry.getValue());
    }

    @Override // java.util.Map.Entry
    public final /* synthetic */ Object getKey() {
        return this.zzpec;
    }

    @Override // java.util.Map.Entry
    public final V getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        int i = 0;
        int hashCode = this.zzpec == null ? 0 : this.zzpec.hashCode();
        if (this.value != 0) {
            i = this.value.hashCode();
        }
        return hashCode ^ i;
    }

    @Override // java.util.Map.Entry
    public final V setValue(V v) {
        this.zzped.zzcwl();
        V v2 = this.value;
        this.value = v;
        return v2;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzpec);
        String valueOf2 = String.valueOf(this.value);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length());
        sb.append(valueOf);
        sb.append("=");
        sb.append(valueOf2);
        return sb.toString();
    }
}
