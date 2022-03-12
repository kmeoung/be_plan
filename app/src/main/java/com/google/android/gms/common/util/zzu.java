package com.google.android.gms.common.util;

import android.support.annotation.Nullable;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class zzu {
    private static final Pattern zzgcb = Pattern.compile("\\$\\{(.*?)\\}");

    public static boolean zzgn(@Nullable String str) {
        return str == null || str.trim().isEmpty();
    }
}
