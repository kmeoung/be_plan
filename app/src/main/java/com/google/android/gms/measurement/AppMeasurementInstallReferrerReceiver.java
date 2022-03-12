package com.google.android.gms.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzchc;

/* loaded from: classes.dex */
public final class AppMeasurementInstallReferrerReceiver extends BroadcastReceiver implements zzchc {
    private zzcha zzitu;

    @Override // com.google.android.gms.internal.zzchc
    public final BroadcastReceiver.PendingResult doGoAsync() {
        return goAsync();
    }

    @Override // com.google.android.gms.internal.zzchc
    public final void doStartService(Context context, Intent intent) {
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
