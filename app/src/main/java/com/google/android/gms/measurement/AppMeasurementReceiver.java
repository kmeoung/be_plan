package com.google.android.gms.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v4.content.WakefulBroadcastReceiver;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzchc;

/* loaded from: classes.dex */
public final class AppMeasurementReceiver extends WakefulBroadcastReceiver implements zzchc {
    private zzcha zzitu;

    @Override // com.google.android.gms.internal.zzchc
    public final BroadcastReceiver.PendingResult doGoAsync() {
        return goAsync();
    }

    @Override // com.google.android.gms.internal.zzchc
    @MainThread
    public final void doStartService(Context context, Intent intent) {
        startWakefulService(context, intent);
    }

    @Override // android.content.BroadcastReceiver
    @MainThread
    public final void onReceive(Context context, Intent intent) {
        if (this.zzitu == null) {
            this.zzitu = new zzcha(this);
        }
        this.zzitu.onReceive(context, intent);
    }
}
