package com.google.android.gms.internal;

import java.util.Map;

/* loaded from: classes.dex */
final class zzffa<K> implements Map.Entry<K, Object> {
    private Map.Entry<K, zzfey> zzpcv;

    private zzffa(Map.Entry<K, zzfey> entry) {
        this.zzpcv = entry;
    }

    @Override // java.util.Map.Entry
    public final K getKey() {
        return this.zzpcv.getKey();
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        if (this.zzpcv.getValue() == null) {
            return null;
        }
        return zzfey.zzcwa();
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (obj instanceof zzffi) {
            return this.zzpcv.getValue().zzj((zzffi) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }
}
