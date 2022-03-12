package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

/* loaded from: classes.dex */
public final class zzcgx {
    private final String zzbfo;
    private long zzdot;
    private boolean zzjaq;
    private /* synthetic */ zzcgu zzjar;
    private final long zzjas;

    public zzcgx(zzcgu zzcguVar, String str, long j) {
        this.zzjar = zzcguVar;
        zzbq.zzgh(str);
        this.zzbfo = str;
        this.zzjas = j;
    }

    @WorkerThread
    public final long get() {
        SharedPreferences zzayz;
        if (!this.zzjaq) {
            this.zzjaq = true;
            zzayz = this.zzjar.zzayz();
            this.zzdot = zzayz.getLong(this.zzbfo, this.zzjas);
        }
        return this.zzdot;
    }

    @WorkerThread
    public final void set(long j) {
        SharedPreferences zzayz;
        zzayz = this.zzjar.zzayz();
        SharedPreferences.Editor edit = zzayz.edit();
        edit.putLong(this.zzbfo, j);
        edit.apply();
        this.zzdot = j;
    }
}
