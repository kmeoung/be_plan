package com.google.android.gms.internal;

import java.util.Iterator;

/* loaded from: classes.dex */
final class zzdzi implements Iterable<zzdzk> {
    private final int length;
    private long value;

    public zzdzi(int i) {
        int i2 = i + 1;
        this.length = (int) Math.floor(Math.log(i2) / Math.log(2.0d));
        this.value = i2 & (((long) Math.pow(2.0d, this.length)) - 1);
    }

    @Override // java.lang.Iterable
    public final Iterator<zzdzk> iterator() {
        return new zzdzj(this);
    }
}
