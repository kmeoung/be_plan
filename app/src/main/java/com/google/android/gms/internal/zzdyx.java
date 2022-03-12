package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
final class zzdyx<T> implements Iterator<T> {
    private Iterator<Map.Entry<T, Void>> zzmhm;

    public zzdyx(Iterator<Map.Entry<T, Void>> it) {
        this.zzmhm = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzmhm.hasNext();
    }

    @Override // java.util.Iterator
    public final T next() {
        return this.zzmhm.next().getKey();
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.zzmhm.remove();
    }
}
