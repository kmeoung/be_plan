package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

/* loaded from: classes.dex */
public final class zzcgw {
    private final String zzbfo;
    private boolean zzfjj;
    private final boolean zzjap = true;
    private boolean zzjaq;
    private /* synthetic */ zzcgu zzjar;

    public zzcgw(zzcgu zzcguVar, String str, boolean z) {
        this.zzjar = zzcguVar;
        zzbq.zzgh(str);
        this.zzbfo = str;
    }

    @WorkerThread
    public final boolean get() {
        SharedPreferences zzayz;
        if (!this.zzjaq) {
            this.zzjaq = true;
            zzayz = this.zzjar.zzayz();
            this.zzfjj = zzayz.getBoolean(this.zzbfo, this.zzjap);
        }
        return this.zzfjj;
    }

    @WorkerThread
    public final void set(boolean z) {
        SharedPreferences zzayz;
        zzayz = this.zzjar.zzayz();
        SharedPreferences.Editor edit = zzayz.edit();
        edit.putBoolean(this.zzbfo, z);
        edit.apply();
        this.zzfjj = z;
    }
}
