package com.google.android.gms.internal;

import java.util.concurrent.BlockingQueue;

/* loaded from: classes.dex */
final class zze implements Runnable {
    private /* synthetic */ zzp zzl;
    private /* synthetic */ zzd zzm;

    public zze(zzd zzdVar, zzp zzpVar) {
        this.zzm = zzdVar;
        this.zzl = zzpVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BlockingQueue blockingQueue;
        try {
            blockingQueue = this.zzm.zzh;
            blockingQueue.put(this.zzl);
        } catch (InterruptedException unused) {
        }
    }
}
