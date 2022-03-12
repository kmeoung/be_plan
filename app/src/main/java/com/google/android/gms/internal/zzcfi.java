package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbq;

/* loaded from: classes.dex */
public final class zzcfi extends zzbej {
    public static final Parcelable.Creator<zzcfi> CREATOR = new zzcfj();
    public String packageName;
    private int versionCode;
    public String zzivk;
    public zzckk zzivl;
    public long zzivm;
    public boolean zzivn;
    public String zzivo;
    public zzcfx zzivp;
    public long zzivq;
    public zzcfx zzivr;
    public long zzivs;
    public zzcfx zzivt;

    public zzcfi(int i, String str, String str2, zzckk zzckkVar, long j, boolean z, String str3, zzcfx zzcfxVar, long j2, zzcfx zzcfxVar2, long j3, zzcfx zzcfxVar3) {
        this.versionCode = i;
        this.packageName = str;
        this.zzivk = str2;
        this.zzivl = zzckkVar;
        this.zzivm = j;
        this.zzivn = z;
        this.zzivo = str3;
        this.zzivp = zzcfxVar;
        this.zzivq = j2;
        this.zzivr = zzcfxVar2;
        this.zzivs = j3;
        this.zzivt = zzcfxVar3;
    }

    public zzcfi(zzcfi zzcfiVar) {
        this.versionCode = 1;
        zzbq.checkNotNull(zzcfiVar);
        this.packageName = zzcfiVar.packageName;
        this.zzivk = zzcfiVar.zzivk;
        this.zzivl = zzcfiVar.zzivl;
        this.zzivm = zzcfiVar.zzivm;
        this.zzivn = zzcfiVar.zzivn;
        this.zzivo = zzcfiVar.zzivo;
        this.zzivp = zzcfiVar.zzivp;
        this.zzivq = zzcfiVar.zzivq;
        this.zzivr = zzcfiVar.zzivr;
        this.zzivs = zzcfiVar.zzivs;
        this.zzivt = zzcfiVar.zzivt;
    }

    public zzcfi(String str, String str2, zzckk zzckkVar, long j, boolean z, String str3, zzcfx zzcfxVar, long j2, zzcfx zzcfxVar2, long j3, zzcfx zzcfxVar3) {
        this.versionCode = 1;
        this.packageName = str;
        this.zzivk = str2;
        this.zzivl = zzckkVar;
        this.zzivm = j;
        this.zzivn = z;
        this.zzivo = str3;
        this.zzivp = zzcfxVar;
        this.zzivq = j2;
        this.zzivr = zzcfxVar2;
        this.zzivs = j3;
        this.zzivt = zzcfxVar3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbem.zze(parcel);
        zzbem.zzc(parcel, 1, this.versionCode);
        zzbem.zza(parcel, 2, this.packageName, false);
        zzbem.zza(parcel, 3, this.zzivk, false);
        zzbem.zza(parcel, 4, (Parcelable) this.zzivl, i, false);
        zzbem.zza(parcel, 5, this.zzivm);
        zzbem.zza(parcel, 6, this.zzivn);
        zzbem.zza(parcel, 7, this.zzivo, false);
        zzbem.zza(parcel, 8, (Parcelable) this.zzivp, i, false);
        zzbem.zza(parcel, 9, this.zzivq);
        zzbem.zza(parcel, 10, (Parcelable) this.zzivr, i, false);
        zzbem.zza(parcel, 11, this.zzivs);
        zzbem.zza(parcel, 12, (Parcelable) this.zzivt, i, false);
        zzbem.zzai(parcel, zze);
    }
}
