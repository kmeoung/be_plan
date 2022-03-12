package com.google.android.gms.internal;

import android.os.StrictMode;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class zzbzz {
    public static <T> T zzb(Callable<T> callable) throws Exception {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        try {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
            return callable.call();
        } finally {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }
}
