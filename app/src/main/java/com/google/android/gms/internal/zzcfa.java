package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzcfa extends zzcih {
    private long zzity;
    private final Map<String, Integer> zzitx = new ArrayMap();
    private final Map<String, Long> zzitw = new ArrayMap();

    public zzcfa(zzchj zzchjVar) {
        super(zzchjVar);
    }

    @WorkerThread
    private final void zza(long j, AppMeasurement.zzb zzbVar) {
        if (zzbVar == null) {
            zzawm().zzayx().log("Not logging ad exposure. No active activity");
        } else if (j < 1000) {
            zzawm().zzayx().zzj("Not logging ad exposure. Less than 1000 ms. exposure", Long.valueOf(j));
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong("_xt", j);
            zzciz.zza(zzbVar, bundle);
            zzawa().zzc("am", "_xa", bundle);
        }
    }

    @WorkerThread
    private final void zza(String str, long j, AppMeasurement.zzb zzbVar) {
        if (zzbVar == null) {
            zzawm().zzayx().log("Not logging ad unit exposure. No active activity");
        } else if (j < 1000) {
            zzawm().zzayx().zzj("Not logging ad unit exposure. Less than 1000 ms. exposure", Long.valueOf(j));
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("_ai", str);
            bundle.putLong("_xt", j);
            zzciz.zza(zzbVar, bundle);
            zzawa().zzc("am", "_xu", bundle);
        }
    }

    @WorkerThread
    public final void zzaj(long j) {
        for (String str : this.zzitw.keySet()) {
            this.zzitw.put(str, Long.valueOf(j));
        }
        if (!this.zzitw.isEmpty()) {
            this.zzity = j;
        }
    }

    @WorkerThread
    public final void zze(String str, long j) {
        zzut();
        zzbq.zzgh(str);
        if (this.zzitx.isEmpty()) {
            this.zzity = j;
        }
        Integer num = this.zzitx.get(str);
        if (num != null) {
            this.zzitx.put(str, Integer.valueOf(num.intValue() + 1));
        } else if (this.zzitx.size() >= 100) {
            zzawm().zzayt().log("Too many ads visible");
        } else {
            this.zzitx.put(str, 1);
            this.zzitw.put(str, Long.valueOf(j));
        }
    }

    @WorkerThread
    public final void zzf(String str, long j) {
        zzut();
        zzbq.zzgh(str);
        Integer num = this.zzitx.get(str);
        if (num != null) {
            zzcjc zzbac = zzawe().zzbac();
            int intValue = num.intValue() - 1;
            if (intValue == 0) {
                this.zzitx.remove(str);
                Long l = this.zzitw.get(str);
                if (l == null) {
                    zzawm().zzayr().log("First ad unit exposure time was never set");
                } else {
                    this.zzitw.remove(str);
                    zza(str, j - l.longValue(), zzbac);
                }
                if (!this.zzitx.isEmpty()) {
                    return;
                }
                if (this.zzity == 0) {
                    zzawm().zzayr().log("First ad exposure time was never set");
                    return;
                }
                zza(j - this.zzity, zzbac);
                this.zzity = 0L;
                return;
            }
            this.zzitx.put(str, Integer.valueOf(intValue));
            return;
        }
        zzawm().zzayr().zzj("Call to endAdUnitExposure for unknown ad unit id", str);
    }

    public final void beginAdUnitExposure(String str) {
        if (str == null || str.length() == 0) {
            zzawm().zzayr().log("Ad unit id must be a non-empty string");
            return;
        }
        zzawl().zzg(new zzcfb(this, str, zzwh().elapsedRealtime()));
    }

    public final void endAdUnitExposure(String str) {
        if (str == null || str.length() == 0) {
            zzawm().zzayr().log("Ad unit id must be a non-empty string");
            return;
        }
        zzawl().zzg(new zzcfc(this, str, zzwh().elapsedRealtime()));
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    public final void zzai(long j) {
        zzcjc zzbac = zzawe().zzbac();
        for (String str : this.zzitw.keySet()) {
            zza(str, j - this.zzitw.get(str).longValue(), zzbac);
        }
        if (!this.zzitw.isEmpty()) {
            zza(j - this.zzity, zzbac);
        }
        zzaj(j);
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

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzut() {
        super.zzut();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzd zzwh() {
        return super.zzwh();
    }
}
