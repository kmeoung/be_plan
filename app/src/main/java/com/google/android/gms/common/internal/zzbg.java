package com.google.android.gms.common.internal;

import android.support.annotation.Nullable;

/* loaded from: classes.dex */
public final class zzbg {
    public static boolean equal(@Nullable Object obj, @Nullable Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static zzbi zzw(Object obj) {
        return new zzbi(obj);
    }
}
