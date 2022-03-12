package com.google.android.gms.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class zzcfs {
    final String mAppId;
    final String mName;
    private String mOrigin;
    final long zzffr;
    final long zziwn;
    final zzcfu zziwo;

    public zzcfs(zzchj zzchjVar, String str, String str2, String str3, long j, long j2, Bundle bundle) {
        zzbq.zzgh(str2);
        zzbq.zzgh(str3);
        this.mAppId = str2;
        this.mName = str3;
        this.mOrigin = TextUtils.isEmpty(str) ? null : str;
        this.zzffr = j;
        this.zziwn = j2;
        if (this.zziwn != 0 && this.zziwn > this.zzffr) {
            zzchjVar.zzawm().zzayt().zzj("Event created with reverse previous/current timestamps. appId", zzcgj.zzje(str2));
        }
        this.zziwo = zza(zzchjVar, bundle);
    }

    private zzcfs(zzchj zzchjVar, String str, String str2, String str3, long j, long j2, zzcfu zzcfuVar) {
        zzbq.zzgh(str2);
        zzbq.zzgh(str3);
        zzbq.checkNotNull(zzcfuVar);
        this.mAppId = str2;
        this.mName = str3;
        this.mOrigin = TextUtils.isEmpty(str) ? null : str;
        this.zzffr = j;
        this.zziwn = j2;
        if (this.zziwn != 0 && this.zziwn > this.zzffr) {
            zzchjVar.zzawm().zzayt().zzj("Event created with reverse previous/current timestamps. appId", zzcgj.zzje(str2));
        }
        this.zziwo = zzcfuVar;
    }

    private static zzcfu zza(zzchj zzchjVar, Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return new zzcfu(new Bundle());
        }
        Bundle bundle2 = new Bundle(bundle);
        Iterator<String> it = bundle2.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (next == null) {
                zzchjVar.zzawm().zzayr().log("Param name can't be null");
            } else {
                Object zzk = zzchjVar.zzawi().zzk(next, bundle2.get(next));
                if (zzk == null) {
                    zzchjVar.zzawm().zzayt().zzj("Param value can't be null", zzchjVar.zzawh().zzjc(next));
                } else {
                    zzchjVar.zzawi().zza(bundle2, next, zzk);
                }
            }
            it.remove();
        }
        return new zzcfu(bundle2);
    }

    public final String toString() {
        String str = this.mAppId;
        String str2 = this.mName;
        String valueOf = String.valueOf(this.zziwo);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 33 + String.valueOf(str2).length() + String.valueOf(valueOf).length());
        sb.append("Event{appId='");
        sb.append(str);
        sb.append("', name='");
        sb.append(str2);
        sb.append("', params=");
        sb.append(valueOf);
        sb.append("}");
        return sb.toString();
    }

    public final zzcfs zza(zzchj zzchjVar, long j) {
        return new zzcfs(zzchjVar, this.mOrigin, this.mAppId, this.mName, this.zzffr, j, this.zziwo);
    }
}
