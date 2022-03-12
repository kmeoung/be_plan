package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzffo<E> extends zzfdd<E> {
    private static final zzffo<Object> zzpde;
    private final List<E> zzpdf;

    static {
        zzffo<Object> zzffoVar = new zzffo<>();
        zzpde = zzffoVar;
        zzffoVar.zzbim();
    }

    zzffo() {
        this(new ArrayList(10));
    }

    private zzffo(List<E> list) {
        this.zzpdf = list;
    }

    public static <E> zzffo<E> zzcwf() {
        return (zzffo<E>) zzpde;
    }

    @Override // com.google.android.gms.internal.zzfdd, java.util.AbstractList, java.util.List
    public final void add(int i, E e) {
        zzcti();
        this.zzpdf.add(i, e);
        this.modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final E get(int i) {
        return this.zzpdf.get(i);
    }

    @Override // com.google.android.gms.internal.zzfdd, java.util.AbstractList, java.util.List
    public final E remove(int i) {
        zzcti();
        E remove = this.zzpdf.remove(i);
        this.modCount++;
        return remove;
    }

    @Override // com.google.android.gms.internal.zzfdd, java.util.AbstractList, java.util.List
    public final E set(int i, E e) {
        zzcti();
        E e2 = this.zzpdf.set(i, e);
        this.modCount++;
        return e2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzpdf.size();
    }

    @Override // com.google.android.gms.internal.zzfev
    public final /* synthetic */ zzfev zzln(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.zzpdf);
        return new zzffo(arrayList);
    }
}
