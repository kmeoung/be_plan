package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

/* loaded from: classes.dex */
public final class zzcgz {
    private String mValue;
    private final String zzbfo;
    private boolean zzjaq;
    private /* synthetic */ zzcgu zzjar;
    private final String zzjaw = null;

    public zzcgz(zzcgu zzcguVar, String str, String str2) {
        this.zzjar = zzcguVar;
        zzbq.zzgh(str);
        this.zzbfo = str;
    }

    @WorkerThread
    public final String zzazf() {
        SharedPreferences zzayz;
        if (!this.zzjaq) {
            this.zzjaq = true;
            zzayz = this.zzjar.zzayz();
            this.mValue = zzayz.getString(this.zzbfo, null);
        }
        return this.mValue;
    }

    @WorkerThread
    public final void zzjk(String str) {
        SharedPreferences zzayz;
        if (!zzckn.zzas(str, this.mValue)) {
            zzayz = this.zzjar.zzayz();
            SharedPreferences.Editor edit = zzayz.edit();
            edit.putString(this.zzbfo, str);
            edit.apply();
            this.mValue = str;
        }
    }
}
