package com.google.android.gms.measurement;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.annotation.MainThread;
import com.google.android.gms.internal.zzcjx;
import com.google.android.gms.internal.zzckb;

@TargetApi(24)
/* loaded from: classes.dex */
public final class AppMeasurementJobService extends JobService implements zzckb {
    private zzcjx<AppMeasurementJobService> zzitv;

    private final zzcjx<AppMeasurementJobService> zzavv() {
        if (this.zzitv == null) {
            this.zzitv = new zzcjx<>(this);
        }
        return this.zzitv;
    }

    @Override // com.google.android.gms.internal.zzckb
    public final boolean callServiceStopSelfResult(int i) {
        throw new UnsupportedOperationException();
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

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        return zzavv().onStartJob(jobParameters);
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override // android.app.Service
    @MainThread
    public final boolean onUnbind(Intent intent) {
        return zzavv().onUnbind(intent);
    }

    @Override // com.google.android.gms.internal.zzckb
    @TargetApi(24)
    public final void zza(JobParameters jobParameters, boolean z) {
        jobFinished(jobParameters, false);
    }

    @Override // com.google.android.gms.internal.zzckb
    public final void zzm(Intent intent) {
    }
}
