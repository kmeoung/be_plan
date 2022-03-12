package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class zzfga implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private /* synthetic */ zzffu zzped;
    private boolean zzpee;
    private Iterator<Map.Entry<K, V>> zzpef;

    private zzfga(zzffu zzffuVar) {
        this.zzped = zzffuVar;
        this.pos = -1;
    }

    public /* synthetic */ zzfga(zzffu zzffuVar, zzffv zzffvVar) {
        this(zzffuVar);
    }

    private final Iterator<Map.Entry<K, V>> zzcwp() {
        Map map;
        if (this.zzpef == null) {
            map = this.zzped.zzpdx;
            this.zzpef = map.entrySet().iterator();
        }
        return this.zzpef;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        List list;
        int i = this.pos + 1;
        list = this.zzped.zzpdw;
        return i < list.size() || zzcwp().hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        List list;
        Object next;
        List list2;
        this.zzpee = true;
        int i = this.pos + 1;
        this.pos = i;
        list = this.zzped.zzpdw;
        if (i < list.size()) {
            list2 = this.zzped.zzpdw;
            next = list2.get(this.pos);
        } else {
            next = zzcwp().next();
        }
        return (Map.Entry) next;
    }

    @Override // java.util.Iterator
    public final void remove() {
        List list;
        if (!this.zzpee) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzpee = false;
        this.zzped.zzcwl();
        int i = this.pos;
        list = this.zzped.zzpdw;
        if (i < list.size()) {
            zzffu zzffuVar = this.zzped;
            int i2 = this.pos;
            this.pos = i2 - 1;
            zzffuVar.zzlr(i2);
            return;
        }
        zzcwp().remove();
    }
}
