package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;

/* loaded from: classes.dex */
public final class zzckh {
    private long mStartTime;
    private final zzd zzasd;

    public zzckh(zzd zzdVar) {
        zzbq.checkNotNull(zzdVar);
        this.zzasd = zzdVar;
    }

    public final void clear() {
        this.mStartTime = 0L;
    }

    public final void start() {
        this.mStartTime = this.zzasd.elapsedRealtime();
    }

    public final boolean zzt(long j) {
        return this.mStartTime == 0 || this.zzasd.elapsedRealtime() - this.mStartTime >= 3600000;
    }
}
