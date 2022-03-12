package com.google.android.gms.internal;

import java.lang.Comparable;
import java.util.Comparator;

/* loaded from: classes.dex */
public final class zzdzl<A extends Comparable<A>> implements Comparator<A> {
    private static zzdzl zzmid = new zzdzl();

    private zzdzl() {
    }

    public static <T extends Comparable<T>> zzdzl<T> zze(Class<T> cls) {
        return zzmid;
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((Comparable) obj).compareTo((Comparable) obj2);
    }
}
