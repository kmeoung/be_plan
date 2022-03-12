package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.util.zzd;

/* loaded from: classes.dex */
public final class zzcki extends zzcii {
    private final AlarmManager zzdsw = (AlarmManager) getContext().getSystemService(NotificationCompat.CATEGORY_ALARM);
    private Integer zzdsx;
    private final zzcfp zzjgl;

    public zzcki(zzchj zzchjVar) {
        super(zzchjVar);
        this.zzjgl = new zzckj(this, zzchjVar);
    }

    private final int getJobId() {
        if (this.zzdsx == null) {
            String valueOf = String.valueOf(getContext().getPackageName());
            this.zzdsx = Integer.valueOf((valueOf.length() != 0 ? "measurement".concat(valueOf) : new String("measurement")).hashCode());
        }
        return this.zzdsx.intValue();
    }

    @TargetApi(24)
    private final void zzbal() {
        zzawm().zzayx().zzj("Cancelling job. JobID", Integer.valueOf(getJobId()));
        ((JobScheduler) getContext().getSystemService("jobscheduler")).cancel(getJobId());
    }

    private final PendingIntent zzyu() {
        Intent className = new Intent().setClassName(getContext(), "com.google.android.gms.measurement.AppMeasurementReceiver");
        className.setAction("com.google.android.gms.measurement.UPLOAD");
        return PendingIntent.getBroadcast(getContext(), 0, className, 0);
    }

    public final void cancel() {
        zzwu();
        this.zzdsw.cancel(zzyu());
        this.zzjgl.cancel();
        if (Build.VERSION.SDK_INT >= 24) {
            zzbal();
        }
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzavw() {
        super.zzavw();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzavx() {
        super.zzavx();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfa zzavy() {
        return super.zzavy();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfh zzavz() {
        return super.zzavz();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcik zzawa() {
        return super.zzawa();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcge zzawb() {
        return super.zzawb();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfr zzawc() {
        return super.zzawc();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcjd zzawd() {
        return super.zzawd();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzciz zzawe() {
        return super.zzawe();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgf zzawf() {
        return super.zzawf();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfl zzawg() {
        return super.zzawg();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgh zzawh() {
        return super.zzawh();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzckn zzawi() {
        return super.zzawi();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzchd zzawj() {
        return super.zzawj();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzckc zzawk() {
        return super.zzawk();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzche zzawl() {
        return super.zzawl();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgj zzawm() {
        return super.zzawm();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgu zzawn() {
        return super.zzawn();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfk zzawo() {
        return super.zzawo();
    }

    @Override // com.google.android.gms.internal.zzcii
    protected final boolean zzaxn() {
        this.zzdsw.cancel(zzyu());
        if (Build.VERSION.SDK_INT < 24) {
            return false;
        }
        zzbal();
        return false;
    }

    public final void zzr(long j) {
        zzwu();
        if (!zzcha.zzbi(getContext())) {
            zzawm().zzayw().log("Receiver not registered/enabled");
        }
        if (!zzcjx.zzk(getContext(), false)) {
            zzawm().zzayw().log("Service not registered/enabled");
        }
        cancel();
        long elapsedRealtime = zzwh().elapsedRealtime() + j;
        if (j < Math.max(0L, zzcfz.zziyf.get().longValue()) && !this.zzjgl.zzdr()) {
            zzawm().zzayx().log("Scheduling upload with DelayedRunnable");
            this.zzjgl.zzr(j);
        }
        if (Build.VERSION.SDK_INT >= 24) {
            zzawm().zzayx().log("Scheduling upload with JobScheduler");
            JobInfo.Builder builder = new JobInfo.Builder(getJobId(), new ComponentName(getContext(), "com.google.android.gms.measurement.AppMeasurementJobService"));
            builder.setMinimumLatency(j);
            builder.setOverrideDeadline(j << 1);
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putString("action", "com.google.android.gms.measurement.UPLOAD");
            builder.setExtras(persistableBundle);
            JobInfo build = builder.build();
            zzawm().zzayx().zzj("Scheduling job. JobID", Integer.valueOf(getJobId()));
            ((JobScheduler) getContext().getSystemService("jobscheduler")).schedule(build);
            return;
        }
        zzawm().zzayx().log("Scheduling upload with AlarmManager");
        this.zzdsw.setInexactRepeating(2, elapsedRealtime, Math.max(zzcfz.zziya.get().longValue(), j), zzyu());
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzut() {
        super.zzut();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzd zzwh() {
        return super.zzwh();
    }
}
