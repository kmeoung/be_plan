package com.google.android.gms.common.util;

import android.os.SystemClock;

/* loaded from: classes.dex */
public final class zzh implements zzd {
    private static zzh zzgbl = new zzh();

    private zzh() {
    }

    public static zzd zzalu() {
        return zzgbl;
    }

    @Override // com.google.android.gms.common.util.zzd
    public final long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override // com.google.android.gms.common.util.zzd
    public final long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    @Override // com.google.android.gms.common.util.zzd
    public final long nanoTime() {
        return System.nanoTime();
    }
}
