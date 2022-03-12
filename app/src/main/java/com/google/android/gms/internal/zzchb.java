package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzchb implements Runnable {
    private /* synthetic */ Context val$context;
    private /* synthetic */ BroadcastReceiver.PendingResult zzdlr;
    private /* synthetic */ zzchj zzjay;
    private /* synthetic */ long zzjaz;
    private /* synthetic */ Bundle zzjba;
    private /* synthetic */ zzcgj zzjbb;

    public zzchb(zzcha zzchaVar, zzchj zzchjVar, long j, Bundle bundle, Context context, zzcgj zzcgjVar, BroadcastReceiver.PendingResult pendingResult) {
        this.zzjay = zzchjVar;
        this.zzjaz = j;
        this.zzjba = bundle;
        this.val$context = context;
        this.zzjbb = zzcgjVar;
        this.zzdlr = pendingResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzckm zzag = this.zzjay.zzawg().zzag(this.zzjay.zzawb().getAppId(), "_fot");
        long longValue = (zzag == null || !(zzag.mValue instanceof Long)) ? 0L : ((Long) zzag.mValue).longValue();
        long j = this.zzjaz;
        if (longValue > 0 && (j >= longValue || j <= 0)) {
            j = longValue - 1;
        }
        if (j > 0) {
            this.zzjba.putLong("click_timestamp", j);
        }
        AppMeasurement.getInstance(this.val$context).logEventInternal("auto", "_cmp", this.zzjba);
        this.zzjbb.zzayx().log("Install campaign recorded");
        if (this.zzdlr != null) {
            this.zzdlr.finish();
        }
    }
}
