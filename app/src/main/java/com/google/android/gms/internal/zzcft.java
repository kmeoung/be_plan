package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzcft {
    final String mAppId;
    final String mName;
    final long zziwp;
    final long zziwq;
    final long zziwr;
    final long zziws;
    final Long zziwt;
    final Long zziwu;
    final Boolean zziwv;

    public zzcft(String str, String str2, long j, long j2, long j3, long j4, Long l, Long l2, Boolean bool) {
        zzbq.zzgh(str);
        zzbq.zzgh(str2);
        boolean z = false;
        zzbq.checkArgument(j >= 0);
        zzbq.checkArgument(j2 >= 0);
        zzbq.checkArgument(j4 >= 0 ? true : z);
        this.mAppId = str;
        this.mName = str2;
        this.zziwp = j;
        this.zziwq = j2;
        this.zziwr = j3;
        this.zziws = j4;
        this.zziwt = l;
        this.zziwu = l2;
        this.zziwv = bool;
    }

    public final zzcft zza(Long l, Long l2, Boolean bool) {
        return new zzcft(this.mAppId, this.mName, this.zziwp, this.zziwq, this.zziwr, this.zziws, l, l2, (bool == null || bool.booleanValue()) ? bool : null);
    }

    public final zzcft zzayk() {
        return new zzcft(this.mAppId, this.mName, this.zziwp + 1, this.zziwq + 1, this.zziwr, this.zziws, this.zziwt, this.zziwu, this.zziwv);
    }

    public final zzcft zzba(long j) {
        return new zzcft(this.mAppId, this.mName, this.zziwp, this.zziwq, j, this.zziws, this.zziwt, this.zziwu, this.zziwv);
    }

    public final zzcft zzbb(long j) {
        return new zzcft(this.mAppId, this.mName, this.zziwp, this.zziwq, this.zziwr, j, this.zziwt, this.zziwu, this.zziwv);
    }
}
