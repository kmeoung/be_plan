package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbq;

/* loaded from: classes.dex */
public final class zzcfx extends zzbej {
    public static final Parcelable.Creator<zzcfx> CREATOR = new zzcfy();
    public final String name;
    public final String zzivk;
    public final zzcfu zziwy;
    public final long zziwz;

    public zzcfx(zzcfx zzcfxVar, long j) {
        zzbq.checkNotNull(zzcfxVar);
        this.name = zzcfxVar.name;
        this.zziwy = zzcfxVar.zziwy;
        this.zzivk = zzcfxVar.zzivk;
        this.zziwz = j;
    }

    public zzcfx(String str, zzcfu zzcfuVar, String str2, long j) {
        this.name = str;
        this.zziwy = zzcfuVar;
        this.zzivk = str2;
        this.zziwz = j;
    }

    public final String toString() {
        String str = this.zzivk;
        String str2 = this.name;
        String valueOf = String.valueOf(this.zziwy);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(str2).length() + String.valueOf(valueOf).length());
        sb.append("origin=");
        sb.append(str);
        sb.append(",name=");
        sb.append(str2);
        sb.append(",params=");
        sb.append(valueOf);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbem.zze(parcel);
        zzbem.zza(parcel, 2, this.name, false);
        zzbem.zza(parcel, 3, (Parcelable) this.zziwy, i, false);
        zzbem.zza(parcel, 4, this.zzivk, false);
        zzbem.zza(parcel, 5, this.zziwz);
        zzbem.zzai(parcel, zze);
    }
}
