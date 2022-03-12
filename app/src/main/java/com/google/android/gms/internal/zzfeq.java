package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* loaded from: classes.dex */
final class zzfeq extends zzfdd<Integer> implements zzfeu, zzffn, RandomAccess {
    private static final zzfeq zzpcq;
    private int size;
    private int[] zzpcr;

    static {
        zzfeq zzfeqVar = new zzfeq();
        zzpcq = zzfeqVar;
        zzfeqVar.zzbim();
    }

    zzfeq() {
        this(new int[10], 0);
    }

    private zzfeq(int[] iArr, int i) {
        this.zzpcr = iArr;
        this.size = i;
    }

    private final void zzai(int i, int i2) {
        zzcti();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzlm(i));
        }
        if (this.size < this.zzpcr.length) {
            System.arraycopy(this.zzpcr, i, this.zzpcr, i + 1, this.size - i);
        } else {
            int[] iArr = new int[((this.size * 3) / 2) + 1];
            System.arraycopy(this.zzpcr, 0, iArr, 0, i);
            System.arraycopy(this.zzpcr, i, iArr, i + 1, this.size - i);
            this.zzpcr = iArr;
        }
        this.zzpcr[i] = i2;
        this.size++;
        this.modCount++;
    }

    public static zzfeq zzcvq() {
        return zzpcq;
    }

    private final void zzll(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzlm(i));
        }
    }

    private final String zzlm(int i) {
        int i2 = this.size;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(i);
        sb.append(", Size:");
        sb.append(i2);
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.zzfdd, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzai(i, ((Integer) obj).intValue());
    }

    @Override // com.google.android.gms.internal.zzfdd, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Integer> collection) {
        zzcti();
        zzfer.checkNotNull(collection);
        if (!(collection instanceof zzfeq)) {
            return super.addAll(collection);
        }
        zzfeq zzfeqVar = (zzfeq) collection;
        if (zzfeqVar.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < zzfeqVar.size) {
            throw new OutOfMemoryError();
        }
        int i = this.size + zzfeqVar.size;
        if (i > this.zzpcr.length) {
            this.zzpcr = Arrays.copyOf(this.zzpcr, i);
        }
        System.arraycopy(zzfeqVar.zzpcr, 0, this.zzpcr, this.size, zzfeqVar.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.zzfdd, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfeq)) {
            return super.equals(obj);
        }
        zzfeq zzfeqVar = (zzfeq) obj;
        if (this.size != zzfeqVar.size) {
            return false;
        }
        int[] iArr = zzfeqVar.zzpcr;
        for (int i = 0; i < this.size; i++) {
            if (this.zzpcr[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    @Override // com.google.android.gms.internal.zzfeu
    public final int getInt(int i) {
        zzll(i);
        return this.zzpcr[i];
    }

    @Override // com.google.android.gms.internal.zzfdd, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.zzpcr[i2];
        }
        return i;
    }

    @Override // com.google.android.gms.internal.zzfdd, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzcti();
        zzll(i);
        int i2 = this.zzpcr[i];
        System.arraycopy(this.zzpcr, i + 1, this.zzpcr, i, this.size - i);
        this.size--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    @Override // com.google.android.gms.internal.zzfdd, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzcti();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Integer.valueOf(this.zzpcr[i]))) {
                System.arraycopy(this.zzpcr, i + 1, this.zzpcr, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.zzfdd, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        zzcti();
        zzll(i);
        int i2 = this.zzpcr[i];
        this.zzpcr[i] = intValue;
        return Integer.valueOf(i2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.zzfeu
    /* renamed from: zzlj */
    public final zzfeu zzln(int i) {
        if (i >= this.size) {
            return new zzfeq(Arrays.copyOf(this.zzpcr, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    @Override // com.google.android.gms.internal.zzfeu
    public final void zzlk(int i) {
        zzai(this.size, i);
    }
}
