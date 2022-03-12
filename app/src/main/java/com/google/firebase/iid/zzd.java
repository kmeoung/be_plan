package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Intent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzd {
    final Intent intent;
    private final BroadcastReceiver.PendingResult zziby;
    private boolean zzibz = false;
    private final ScheduledFuture<?> zzica;

    public zzd(Intent intent, BroadcastReceiver.PendingResult pendingResult, ScheduledExecutorService scheduledExecutorService) {
        this.intent = intent;
        this.zziby = pendingResult;
        this.zzica = scheduledExecutorService.schedule(new zze(this, intent), 9500L, TimeUnit.MILLISECONDS);
    }

    public final synchronized void finish() {
        if (!this.zzibz) {
            this.zziby.finish();
            this.zzica.cancel(false);
            this.zzibz = true;
        }
    }
}
