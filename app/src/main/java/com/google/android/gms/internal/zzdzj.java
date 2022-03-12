package com.google.android.gms.internal;

import java.util.Iterator;

/* loaded from: classes.dex */
public final class zzdzj implements Iterator<zzdzk> {
    private int zzmhz;
    private /* synthetic */ zzdzi zzmia;

    public zzdzj(zzdzi zzdziVar) {
        int i;
        this.zzmia = zzdziVar;
        i = this.zzmia.length;
        this.zzmhz = i - 1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzmhz >= 0;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ zzdzk next() {
        long j;
        j = this.zzmia.value;
        long j2 = j & (1 << this.zzmhz);
        zzdzk zzdzkVar = new zzdzk();
        zzdzkVar.zzmib = j2 == 0;
        zzdzkVar.zzmic = (int) Math.pow(2.0d, this.zzmhz);
        this.zzmhz--;
        return zzdzkVar;
    }

    @Override // java.util.Iterator
    public final void remove() {
    }
}
