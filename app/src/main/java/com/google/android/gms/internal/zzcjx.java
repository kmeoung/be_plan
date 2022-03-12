package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.MainThread;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzckb;

/* loaded from: classes.dex */
public final class zzcjx<T extends Context & zzckb> {
    private final T zzdvw;

    public zzcjx(T t) {
        zzbq.checkNotNull(t);
        this.zzdvw = t;
    }

    private final zzcgj zzawm() {
        return zzchj.zzdu(this.zzdvw).zzawm();
    }

    private final void zzk(Runnable runnable) {
        zzchj zzdu = zzchj.zzdu(this.zzdvw);
        zzdu.zzawm();
        zzdu.zzawl().zzg(new zzcka(this, zzdu, runnable));
    }

    public static boolean zzk(Context context, boolean z) {
        zzbq.checkNotNull(context);
        return zzckn.zzt(context, Build.VERSION.SDK_INT >= 24 ? "com.google.android.gms.measurement.AppMeasurementJobService" : "com.google.android.gms.measurement.AppMeasurementService");
    }

    @MainThread
    public final IBinder onBind(Intent intent) {
        if (intent == null) {
            zzawm().zzayr().log("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzcho(zzchj.zzdu(this.zzdvw));
        }
        zzawm().zzayt().zzj("onBind received unknown action", action);
        return null;
    }

    @MainThread
    public final void onCreate() {
        zzchj.zzdu(this.zzdvw).zzawm().zzayx().log("Local AppMeasurementService is starting up");
    }

    @MainThread
    public final void onDestroy() {
        zzchj.zzdu(this.zzdvw).zzawm().zzayx().log("Local AppMeasurementService is shutting down");
    }

    @MainThread
    public final void onRebind(Intent intent) {
        if (intent == null) {
            zzawm().zzayr().log("onRebind called with null intent");
            return;
        }
        zzawm().zzayx().zzj("onRebind called. action", intent.getAction());
    }

    @MainThread
    public final int onStartCommand(Intent intent, int i, int i2) {
        zzcgj zzawm = zzchj.zzdu(this.zzdvw).zzawm();
        if (intent == null) {
            zzawm.zzayt().log("AppMeasurementService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zzawm.zzayx().zze("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            zzk(new Runnable(this, i2, zzawm, intent) { // from class: com.google.android.gms.internal.zzcjy
                private final zzcjx zzjga;
                private final int zzjgb;
                private final zzcgj zzjgc;
                private final Intent zzjgd;

                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    this.zzjga = this;
                    this.zzjgb = i2;
                    this.zzjgc = zzawm;
                    this.zzjgd = intent;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    this.zzjga.zza(this.zzjgb, this.zzjgc, this.zzjgd);
                }
            });
        }
        return 2;
    }

    @TargetApi(24)
    @MainThread
    public final boolean onStartJob(JobParameters jobParameters) {
        zzcgj zzawm = zzchj.zzdu(this.zzdvw).zzawm();
        String string = jobParameters.getExtras().getString("action");
        zzawm.zzayx().zzj("Local AppMeasurementJobService called. action", string);
        if (!"com.google.android.gms.measurement.UPLOAD".equals(string)) {
            return true;
        }
        zzk(new Runnable(this, zzawm, jobParameters) { // from class: com.google.android.gms.internal.zzcjz
            private final zzcjx zzjga;
            private final zzcgj zzjge;
            private final JobParameters zzjgf;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.zzjga = this;
                this.zzjge = zzawm;
                this.zzjgf = jobParameters;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzjga.zza(this.zzjge, this.zzjgf);
            }
        });
        return true;
    }

    @MainThread
    public final boolean onUnbind(Intent intent) {
        if (intent == null) {
            zzawm().zzayr().log("onUnbind called with null intent");
            return true;
        }
        zzawm().zzayx().zzj("onUnbind called for intent. action", intent.getAction());
        return true;
    }

    public final /* synthetic */ void zza(int i, zzcgj zzcgjVar, Intent intent) {
        if (this.zzdvw.callServiceStopSelfResult(i)) {
            zzcgjVar.zzayx().zzj("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzawm().zzayx().log("Completed wakeful intent.");
            this.zzdvw.zzm(intent);
        }
    }

    public final /* synthetic */ void zza(zzcgj zzcgjVar, JobParameters jobParameters) {
        zzcgjVar.zzayx().log("AppMeasurementJobService processed last upload request.");
        this.zzdvw.zza(jobParameters, false);
    }
}
