package com.google.android.gms.internal;

import android.os.Bundle;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class zzcfv implements Iterator<String> {
    private Iterator<String> zziww;
    private /* synthetic */ zzcfu zziwx;

    public zzcfv(zzcfu zzcfuVar) {
        Bundle bundle;
        this.zziwx = zzcfuVar;
        bundle = this.zziwx.zzdyg;
        this.zziww = bundle.keySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zziww.hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zziww.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }
}
