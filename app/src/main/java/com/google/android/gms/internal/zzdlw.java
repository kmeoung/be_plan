package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* loaded from: classes.dex */
public final class zzdlw {
    @NonNull
    public static <T> T checkNotNull(@Nullable T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }
}
