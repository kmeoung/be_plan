package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

/* loaded from: classes.dex */
public final class zzcgu extends zzcii {
    static final Pair<String, Long> zzizu = new Pair<>("", 0L);
    private SharedPreferences zzdvp;
    private String zzjad;
    private boolean zzjae;
    private long zzjaf;
    private String zzjag;
    private long zzjah;
    public boolean zzjao;
    public final zzcgy zzizv = new zzcgy(this, "health_monitor", Math.max(0L, zzcfz.zzixk.get().longValue()));
    public final zzcgx zzizw = new zzcgx(this, "last_upload", 0);
    public final zzcgx zzizx = new zzcgx(this, "last_upload_attempt", 0);
    public final zzcgx zzizy = new zzcgx(this, "backoff", 0);
    public final zzcgx zzizz = new zzcgx(this, "last_delete_stale", 0);
    public final zzcgx zzjaj = new zzcgx(this, "time_before_start", 10000);
    public final zzcgx zzjak = new zzcgx(this, "session_timeout", 1800000);
    public final zzcgw zzjal = new zzcgw(this, "start_new_session", true);
    public final zzcgx zzjam = new zzcgx(this, "last_pause_time", 0);
    public final zzcgx zzjan = new zzcgx(this, "time_active", 0);
    public final zzcgx zzjaa = new zzcgx(this, "midnight_offset", 0);
    public final zzcgx zzjab = new zzcgx(this, "first_open_time", 0);
    public final zzcgz zzjac = new zzcgz(this, "app_instance_id", null);
    private final Object zzjai = new Object();

    public zzcgu(zzchj zzchjVar) {
        super(zzchjVar);
    }

    @WorkerThread
    public final SharedPreferences zzayz() {
        zzut();
        zzwu();
        return this.zzdvp;
    }

    @WorkerThread
    public final void setMeasurementEnabled(boolean z) {
        zzut();
        zzawm().zzayx().zzj("Setting measurementEnabled", Boolean.valueOf(z));
        SharedPreferences.Editor edit = zzayz().edit();
        edit.putBoolean("measurement_enabled", z);
        edit.apply();
    }

    @Override // com.google.android.gms.internal.zzcii
    protected final boolean zzaxn() {
        return true;
    }

    @Override // com.google.android.gms.internal.zzcii
    protected final void zzaym() {
        this.zzdvp = getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzjao = this.zzdvp.getBoolean("has_been_opened", false);
        if (!this.zzjao) {
            SharedPreferences.Editor edit = this.zzdvp.edit();
            edit.putBoolean("has_been_opened", true);
            edit.apply();
        }
    }

    @WorkerThread
    public final String zzaza() {
        zzut();
        return zzayz().getString("gmp_app_id", null);
    }

    public final String zzazb() {
        synchronized (this.zzjai) {
            if (Math.abs(zzwh().elapsedRealtime() - this.zzjah) >= 1000) {
                return null;
            }
            return this.zzjag;
        }
    }

    @WorkerThread
    public final Boolean zzazc() {
        zzut();
        if (!zzayz().contains("use_service")) {
            return null;
        }
        return Boolean.valueOf(zzayz().getBoolean("use_service", false));
    }

    @WorkerThread
    public final void zzazd() {
        zzut();
        zzawm().zzayx().log("Clearing collection preferences.");
        boolean contains = zzayz().contains("measurement_enabled");
        boolean z = true;
        if (contains) {
            z = zzbm(true);
        }
        SharedPreferences.Editor edit = zzayz().edit();
        edit.clear();
        edit.apply();
        if (contains) {
            setMeasurementEnabled(z);
        }
    }

    @WorkerThread
    public final String zzaze() {
        zzut();
        String string = zzayz().getString("previous_os_version", null);
        zzawc().zzwu();
        String str = Build.VERSION.RELEASE;
        if (!TextUtils.isEmpty(str) && !str.equals(string)) {
            SharedPreferences.Editor edit = zzayz().edit();
            edit.putString("previous_os_version", str);
            edit.apply();
        }
        return string;
    }

    @WorkerThread
    public final void zzbl(boolean z) {
        zzut();
        zzawm().zzayx().zzj("Setting useService", Boolean.valueOf(z));
        SharedPreferences.Editor edit = zzayz().edit();
        edit.putBoolean("use_service", z);
        edit.apply();
    }

    @WorkerThread
    public final boolean zzbm(boolean z) {
        zzut();
        return zzayz().getBoolean("measurement_enabled", z);
    }

    @WorkerThread
    @NonNull
    public final Pair<String, Boolean> zzjg(String str) {
        zzut();
        long elapsedRealtime = zzwh().elapsedRealtime();
        if (this.zzjad != null && elapsedRealtime < this.zzjaf) {
            return new Pair<>(this.zzjad, Boolean.valueOf(this.zzjae));
        }
        this.zzjaf = elapsedRealtime + zzawo().zza(str, zzcfz.zzixj);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
            if (advertisingIdInfo != null) {
                this.zzjad = advertisingIdInfo.getId();
                this.zzjae = advertisingIdInfo.isLimitAdTrackingEnabled();
            }
            if (this.zzjad == null) {
                this.zzjad = "";
            }
        } catch (Throwable th) {
            zzawm().zzayw().zzj("Unable to get advertising id", th);
            this.zzjad = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair<>(this.zzjad, Boolean.valueOf(this.zzjae));
    }

    @WorkerThread
    public final String zzjh(String str) {
        zzut();
        String str2 = (String) zzjg(str).first;
        MessageDigest zzed = zzckn.zzed(CommonUtils.MD5_INSTANCE);
        if (zzed == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, zzed.digest(str2.getBytes())));
    }

    @WorkerThread
    public final void zzji(String str) {
        zzut();
        SharedPreferences.Editor edit = zzayz().edit();
        edit.putString("gmp_app_id", str);
        edit.apply();
    }

    public final void zzjj(String str) {
        synchronized (this.zzjai) {
            this.zzjag = str;
            this.zzjah = zzwh().elapsedRealtime();
        }
    }
}
