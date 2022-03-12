package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class zzcfj implements Parcelable.Creator<zzcfi> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzcfi createFromParcel(Parcel parcel) {
        int zzd = zzbek.zzd(parcel);
        long j = 0;
        String str = null;
        long j2 = 0;
        long j3 = 0;
        String str2 = null;
        zzckk zzckkVar = null;
        String str3 = null;
        zzcfx zzcfxVar = null;
        zzcfx zzcfxVar2 = null;
        zzcfx zzcfxVar3 = null;
        int i = 0;
        boolean z = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzbek.zzg(parcel, readInt);
                    break;
                case 2:
                    str = zzbek.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzbek.zzq(parcel, readInt);
                    break;
                case 4:
                    zzckkVar = (zzckk) zzbek.zza(parcel, readInt, zzckk.CREATOR);
                    break;
                case 5:
                    j = zzbek.zzi(parcel, readInt);
                    break;
                case 6:
                    z = zzbek.zzc(parcel, readInt);
                    break;
                case 7:
                    str3 = zzbek.zzq(parcel, readInt);
                    break;
                case 8:
                    zzcfxVar = (zzcfx) zzbek.zza(parcel, readInt, zzcfx.CREATOR);
                    break;
                case 9:
                    j2 = zzbek.zzi(parcel, readInt);
                    break;
                case 10:
                    zzcfxVar2 = (zzcfx) zzbek.zza(parcel, readInt, zzcfx.CREATOR);
                    break;
                case 11:
                    j3 = zzbek.zzi(parcel, readInt);
                    break;
                case 12:
                    zzcfxVar3 = (zzcfx) zzbek.zza(parcel, readInt, zzcfx.CREATOR);
                    break;
                default:
                    zzbek.zzb(parcel, readInt);
                    break;
            }
        }
        zzbek.zzaf(parcel, zzd);
        return new zzcfi(i, str, str2, zzckkVar, j, z, str3, zzcfxVar, j2, zzcfxVar2, j3, zzcfxVar3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzcfi[] newArray(int i) {
        return new zzcfi[i];
    }
}
