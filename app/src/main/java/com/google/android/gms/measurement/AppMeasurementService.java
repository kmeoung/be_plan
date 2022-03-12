package com.google.android.gms.measurement;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.MainThread;
import com.google.android.gms.internal.zzcjx;
import com.google.android.gms.internal.zzckb;

/* loaded from: classes.dex */
public final class AppMeasurementService extends Service implements zzckb {
    private zzcjx<AppMeasurementService> zzitv;

    private final zzcjx<AppMeasurementService> zzavv() {
        if (this.zzitv == null) {
            this.zzitv = new zzcjx<>(this);
        }
        return this.zzitv;
    }

    @Override // com.google.android.gms.internal.zzckb
    public final boolean callServiceStopSelfResult(int i) {
        return stopSelfResult(i);
    }

    @Override // android.app.Service
    @MainThread
    public final IBinder onBind(Intent intent) {
        return zzavv().onBind(intent);
    }

    @Override // android.app.Service
    @MainThread
    public final void onCreate() {
        super.onCreate();
        zzavv().onCreate();
    }

    @Override // android.app.Service
    @MainThread
    public final void onDestroy() {
        zzavv().onDestroy();
        super.onDestroy();
    }

    @Override // android.app.Service
    @MainThread
    public final void onRebind(Intent intent) {
        zzavv().onRebind(intent);
    }

    @Override // android.app.Service
    @MainThread
    public final int onStartCommand(Intent intent, int i, int i2) {
        return zzavv().onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    @MainThread
    public final boolean onUnbind(Intent intent) {
        return zzavv().onUnbind(intent);
    }

    @Override // com.google.android.gms.internal.zzckb
    public final void zza(JobParameters jobParameters, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.zzckb
    public final void zzm(Intent intent) {
        AppMeasurementReceiver.completeWakefulIntent(intent);
    }
}
