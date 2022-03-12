package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;

/* loaded from: classes.dex */
public final class zzcgy {
    private final long zzdvt;
    private /* synthetic */ zzcgu zzjar;
    private String zzjat;
    private final String zzjau;
    private final String zzjav;

    private zzcgy(zzcgu zzcguVar, String str, long j) {
        this.zzjar = zzcguVar;
        zzbq.zzgh(str);
        zzbq.checkArgument(j > 0);
        this.zzjat = String.valueOf(str).concat(":start");
        this.zzjau = String.valueOf(str).concat(":count");
        this.zzjav = String.valueOf(str).concat(":value");
        this.zzdvt = j;
    }

    @WorkerThread
    private final void zzzr() {
        SharedPreferences zzayz;
        this.zzjar.zzut();
        long currentTimeMillis = this.zzjar.zzwh().currentTimeMillis();
        zzayz = this.zzjar.zzayz();
        SharedPreferences.Editor edit = zzayz.edit();
        edit.remove(this.zzjau);
        edit.remove(this.zzjav);
        edit.putLong(this.zzjat, currentTimeMillis);
        edit.apply();
    }

    @WorkerThread
    private final long zzzt() {
        SharedPreferences zzayz;
        zzayz = this.zzjar.zzayz();
        return zzayz.getLong(this.zzjat, 0L);
    }

    @WorkerThread
    public final void zzg(String str, long j) {
        SharedPreferences zzayz;
        SharedPreferences zzayz2;
        SharedPreferences zzayz3;
        this.zzjar.zzut();
        if (zzzt() == 0) {
            zzzr();
        }
        if (str == null) {
            str = "";
        }
        zzayz = this.zzjar.zzayz();
        long j2 = zzayz.getLong(this.zzjau, 0L);
        if (j2 <= 0) {
            zzayz3 = this.zzjar.zzayz();
            SharedPreferences.Editor edit = zzayz3.edit();
            edit.putString(this.zzjav, str);
            edit.putLong(this.zzjau, 1L);
            edit.apply();
            return;
        }
        long j3 = j2 + 1;
        boolean z = (this.zzjar.zzawi().zzban().nextLong() & Long.MAX_VALUE) < Long.MAX_VALUE / j3;
        zzayz2 = this.zzjar.zzayz();
        SharedPreferences.Editor edit2 = zzayz2.edit();
        if (z) {
            edit2.putString(this.zzjav, str);
        }
        edit2.putLong(this.zzjau, j3);
        edit2.apply();
    }

    @WorkerThread
    public final Pair<String, Long> zzzs() {
        long j;
        SharedPreferences zzayz;
        SharedPreferences zzayz2;
        this.zzjar.zzut();
        this.zzjar.zzut();
        long zzzt = zzzt();
        if (zzzt == 0) {
            zzzr();
            j = 0;
        } else {
            j = Math.abs(zzzt - this.zzjar.zzwh().currentTimeMillis());
        }
        if (j < this.zzdvt) {
            return null;
        }
        if (j > (this.zzdvt << 1)) {
            zzzr();
            return null;
        }
        zzayz = this.zzjar.zzayz();
        String string = zzayz.getString(this.zzjav, null);
        zzayz2 = this.zzjar.zzayz();
        long j2 = zzayz2.getLong(this.zzjau, 0L);
        zzzr();
        return (string == null || j2 <= 0) ? zzcgu.zzizu : new Pair<>(string, Long.valueOf(j2));
    }
}
