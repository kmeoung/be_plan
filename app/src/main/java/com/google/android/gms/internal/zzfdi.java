package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzfdi implements Iterator {
    private final int limit;
    private int position = 0;
    private /* synthetic */ zzfdh zzpan;

    public zzfdi(zzfdh zzfdhVar) {
        this.zzpan = zzfdhVar;
        this.limit = this.zzpan.size();
    }

    private final byte nextByte() {
        try {
            zzfdh zzfdhVar = this.zzpan;
            int i = this.position;
            this.position = i + 1;
            return zzfdhVar.zzkd(i);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.position < this.limit;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        return Byte.valueOf(nextByte());
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
