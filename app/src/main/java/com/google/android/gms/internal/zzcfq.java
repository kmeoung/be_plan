package com.google.android.gms.internal;

import android.os.Looper;

/* loaded from: classes.dex */
public final class zzcfq implements Runnable {
    private /* synthetic */ zzcfp zziwj;

    public zzcfq(zzcfp zzcfpVar) {
        this.zziwj = zzcfpVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        zzchj zzchjVar;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            zzchjVar = this.zziwj.zzitk;
            zzchjVar.zzawl().zzg(this);
            return;
        }
        boolean zzdr = this.zziwj.zzdr();
        this.zziwj.zzdss = 0L;
        if (zzdr) {
            z = this.zziwj.zziwi;
            if (z) {
                this.zziwj.run();
            }
        }
    }
}
