package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public final class zzciz extends zzcii {
    protected zzcjc zzjes;
    private volatile AppMeasurement.zzb zzjet;
    private AppMeasurement.zzb zzjeu;
    private long zzjev;
    private final Map<Activity, zzcjc> zzjew = new ArrayMap();
    private final CopyOnWriteArrayList<AppMeasurement.zza> zzjex = new CopyOnWriteArrayList<>();
    private boolean zzjey;
    private AppMeasurement.zzb zzjez;
    private String zzjfa;

    public zzciz(zzchj zzchjVar) {
        super(zzchjVar);
    }

    @MainThread
    private final void zza(Activity activity, zzcjc zzcjcVar, boolean z) {
        AppMeasurement.zzb zzbVar = null;
        AppMeasurement.zzb zzbVar2 = this.zzjet != null ? this.zzjet : (this.zzjeu == null || Math.abs(zzwh().elapsedRealtime() - this.zzjev) >= 1000) ? null : this.zzjeu;
        if (zzbVar2 != null) {
            zzbVar = new AppMeasurement.zzb(zzbVar2);
        }
        boolean z2 = true;
        this.zzjey = true;
        try {
            try {
                Iterator<AppMeasurement.zza> it = this.zzjex.iterator();
                while (it.hasNext()) {
                    try {
                        z2 &= it.next().zza(zzbVar, zzcjcVar);
                    } catch (Exception e) {
                        zzawm().zzayr().zzj("onScreenChangeCallback threw exception", e);
                    }
                }
            } catch (Exception e2) {
                zzawm().zzayr().zzj("onScreenChangeCallback loop threw exception", e2);
            }
            AppMeasurement.zzb zzbVar3 = this.zzjet == null ? this.zzjeu : this.zzjet;
            if (z2) {
                if (zzcjcVar.zzitq == null) {
                    zzcjcVar.zzitq = zzjs(activity.getClass().getCanonicalName());
                }
                zzcjc zzcjcVar2 = new zzcjc(zzcjcVar);
                this.zzjeu = this.zzjet;
                this.zzjev = zzwh().elapsedRealtime();
                this.zzjet = zzcjcVar2;
                zzawl().zzg(new zzcja(this, z, zzbVar3, zzcjcVar2));
            }
        } finally {
            this.zzjey = false;
        }
    }

    @WorkerThread
    public final void zza(@NonNull zzcjc zzcjcVar) {
        zzavy().zzai(zzwh().elapsedRealtime());
        if (zzawk().zzbr(zzcjcVar.zzjfg)) {
            zzcjcVar.zzjfg = false;
        }
    }

    public static void zza(AppMeasurement.zzb zzbVar, Bundle bundle) {
        if (bundle != null && zzbVar != null && !bundle.containsKey("_sc")) {
            if (zzbVar.zzitp != null) {
                bundle.putString("_sn", zzbVar.zzitp);
            }
            bundle.putString("_sc", zzbVar.zzitq);
            bundle.putLong("_si", zzbVar.zzitr);
        }
    }

    private static String zzjs(String str) {
        String[] split = str.split("\\.");
        if (split.length == 0) {
            return str.substring(0, 36);
        }
        String str2 = split[split.length - 1];
        return str2.length() > 36 ? str2.substring(0, 36) : str2;
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @MainThread
    public final void onActivityDestroyed(Activity activity) {
        this.zzjew.remove(activity);
    }

    @MainThread
    public final void onActivityPaused(Activity activity) {
        zzcjc zzq = zzq(activity);
        this.zzjeu = this.zzjet;
        this.zzjev = zzwh().elapsedRealtime();
        this.zzjet = null;
        zzawl().zzg(new zzcjb(this, zzq));
    }

    @MainThread
    public final void onActivityResumed(Activity activity) {
        zza(activity, zzq(activity), false);
        zzcfa zzavy = zzavy();
        zzavy.zzawl().zzg(new zzcfd(zzavy, zzavy.zzwh().elapsedRealtime()));
    }

    @MainThread
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zzcjc zzcjcVar;
        if (bundle != null && (zzcjcVar = this.zzjew.get(activity)) != null) {
            Bundle bundle2 = new Bundle();
            bundle2.putLong("id", zzcjcVar.zzitr);
            bundle2.putString("name", zzcjcVar.zzitp);
            bundle2.putString("referrer_name", zzcjcVar.zzitq);
            bundle.putBundle("com.google.firebase.analytics.screen_service", bundle2);
        }
    }

    @MainThread
    public final void registerOnScreenChangeCallback(@NonNull AppMeasurement.zza zzaVar) {
        if (zzaVar == null) {
            zzawm().zzayt().log("Attempting to register null OnScreenChangeCallback");
            return;
        }
        this.zzjex.remove(zzaVar);
        this.zzjex.add(zzaVar);
    }

    @MainThread
    public final void setCurrentScreen(@NonNull Activity activity, @Size(max = 36, min = 1) @Nullable String str, @Size(max = 36, min = 1) @Nullable String str2) {
        if (activity == null) {
            zzawm().zzayt().log("setCurrentScreen must be called with a non-null activity");
            return;
        }
        zzawl();
        if (!zzche.zzas()) {
            zzawm().zzayt().log("setCurrentScreen must be called from the main thread");
        } else if (this.zzjey) {
            zzawm().zzayt().log("Cannot call setCurrentScreen from onScreenChangeCallback");
        } else if (this.zzjet == null) {
            zzawm().zzayt().log("setCurrentScreen cannot be called while no activity active");
        } else if (this.zzjew.get(activity) == null) {
            zzawm().zzayt().log("setCurrentScreen must be called with an activity in the activity lifecycle");
        } else {
            if (str2 == null) {
                str2 = zzjs(activity.getClass().getCanonicalName());
            }
            boolean equals = this.zzjet.zzitq.equals(str2);
            boolean zzas = zzckn.zzas(this.zzjet.zzitp, str);
            if (equals && zzas) {
                zzawm().zzayu().log("setCurrentScreen cannot be called with the same class and name");
            } else if (str != null && (str.length() <= 0 || str.length() > 100)) {
                zzawm().zzayt().zzj("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            } else if (str2 == null || (str2.length() > 0 && str2.length() <= 100)) {
                zzawm().zzayx().zze("Setting current screen to name, class", str == null ? "null" : str, str2);
                zzcjc zzcjcVar = new zzcjc(str, str2, zzawi().zzbam());
                this.zzjew.put(activity, zzcjcVar);
                zza(activity, zzcjcVar, true);
            } else {
                zzawm().zzayt().zzj("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            }
        }
    }

    @MainThread
    public final void unregisterOnScreenChangeCallback(@NonNull AppMeasurement.zza zzaVar) {
        this.zzjex.remove(zzaVar);
    }

    @WorkerThread
    public final void zza(String str, AppMeasurement.zzb zzbVar) {
        zzut();
        synchronized (this) {
            if (this.zzjfa == null || this.zzjfa.equals(str) || zzbVar != null) {
                this.zzjfa = str;
                this.zzjez = zzbVar;
            }
        }
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
    public final zzcjc zzbac() {
        zzwu();
        zzut();
        return this.zzjes;
    }

    public final AppMeasurement.zzb zzbad() {
        AppMeasurement.zzb zzbVar = this.zzjet;
        if (zzbVar == null) {
            return null;
        }
        return new AppMeasurement.zzb(zzbVar);
    }

    @MainThread
    public final zzcjc zzq(@NonNull Activity activity) {
        zzbq.checkNotNull(activity);
        zzcjc zzcjcVar = this.zzjew.get(activity);
        if (zzcjcVar != null) {
            return zzcjcVar;
        }
        zzcjc zzcjcVar2 = new zzcjc(null, zzjs(activity.getClass().getCanonicalName()), zzawi().zzbam());
        this.zzjew.put(activity, zzcjcVar2);
        return zzcjcVar2;
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
