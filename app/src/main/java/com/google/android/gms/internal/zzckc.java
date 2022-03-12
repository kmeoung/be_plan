package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.util.zzd;

/* loaded from: classes.dex */
public final class zzckc extends zzcii {
    private Handler mHandler;
    private final zzcfp zzjgi = new zzckd(this, this.zzitk);
    private final zzcfp zzjgj = new zzcke(this, this.zzitk);
    private long zzjgh = zzwh().elapsedRealtime();

    public zzckc(zzchj zzchjVar) {
        super(zzchjVar);
    }

    private final void zzbaj() {
        synchronized (this) {
            if (this.mHandler == null) {
                this.mHandler = new Handler(Looper.getMainLooper());
            }
        }
    }

    @WorkerThread
    public final void zzbak() {
        zzut();
        zzbr(false);
        zzavy().zzai(zzwh().elapsedRealtime());
    }

    @WorkerThread
    public final void zzbd(long j) {
        zzcfp zzcfpVar;
        long j2;
        zzut();
        zzbaj();
        this.zzjgi.cancel();
        this.zzjgj.cancel();
        zzawm().zzayx().zzj("Activity resumed, time", Long.valueOf(j));
        this.zzjgh = j;
        if (zzwh().currentTimeMillis() - zzawn().zzjak.get() > zzawn().zzjam.get()) {
            zzawn().zzjal.set(true);
            zzawn().zzjan.set(0L);
        }
        if (zzawn().zzjal.get()) {
            zzcfpVar = this.zzjgi;
            j2 = zzawn().zzjaj.get();
        } else {
            zzcfpVar = this.zzjgj;
            j2 = 3600000;
        }
        zzcfpVar.zzr(Math.max(0L, j2 - zzawn().zzjan.get()));
    }

    @WorkerThread
    public final void zzbe(long j) {
        zzut();
        zzbaj();
        this.zzjgi.cancel();
        this.zzjgj.cancel();
        zzawm().zzayx().zzj("Activity paused, time", Long.valueOf(j));
        if (this.zzjgh != 0) {
            zzawn().zzjan.set(zzawn().zzjan.get() + (j - this.zzjgh));
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
        return false;
    }

    @WorkerThread
    public final boolean zzbr(boolean z) {
        zzut();
        zzwu();
        long elapsedRealtime = zzwh().elapsedRealtime();
        zzawn().zzjam.set(zzwh().currentTimeMillis());
        long j = elapsedRealtime - this.zzjgh;
        if (z || j >= 1000) {
            zzawn().zzjan.set(j);
            zzawm().zzayx().zzj("Recording user engagement, ms", Long.valueOf(j));
            Bundle bundle = new Bundle();
            bundle.putLong("_et", j);
            zzciz.zza(zzawe().zzbac(), bundle);
            zzawa().zzc("auto", "_e", bundle);
            this.zzjgh = elapsedRealtime;
            this.zzjgj.cancel();
            this.zzjgj.zzr(Math.max(0L, 3600000 - zzawn().zzjan.get()));
            return true;
        }
        zzawm().zzayx().zzj("Screen exposed for less than 1000 ms. Event not sent. time", Long.valueOf(j));
        return false;
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
