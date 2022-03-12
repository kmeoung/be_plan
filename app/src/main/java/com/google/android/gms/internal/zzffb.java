package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzffb<K> implements Iterator<Map.Entry<K, Object>> {
    private Iterator<Map.Entry<K, Object>> zzmhm;

    public zzffb(Iterator<Map.Entry<K, Object>> it) {
        this.zzmhm = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzmhm.hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        Map.Entry<K, Object> next = this.zzmhm.next();
        return next.getValue() instanceof zzfey ? new zzffa(next) : next;
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.zzmhm.remove();
    }
}
