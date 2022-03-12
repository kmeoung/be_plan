package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class zzcfg implements Parcelable.Creator<zzcff> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzcff createFromParcel(Parcel parcel) {
        int zzd = zzbek.zzd(parcel);
        long j = 0;
        String str = null;
        long j2 = -2147483648L;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        boolean z = true;
        boolean z2 = false;
        int i = 0;
        boolean z3 = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = zzbek.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzbek.zzq(parcel, readInt);
                    break;
                case 4:
                    str3 = zzbek.zzq(parcel, readInt);
                    break;
                case 5:
                    str4 = zzbek.zzq(parcel, readInt);
                    break;
                case 6:
                    j = zzbek.zzi(parcel, readInt);
                    break;
                case 7:
                    j3 = zzbek.zzi(parcel, readInt);
                    break;
                case 8:
                    str5 = zzbek.zzq(parcel, readInt);
                    break;
                case 9:
                    z = zzbek.zzc(parcel, readInt);
                    break;
                case 10:
                    z2 = zzbek.zzc(parcel, readInt);
                    break;
                case 11:
                    j2 = zzbek.zzi(parcel, readInt);
                    break;
                case 12:
                    str6 = zzbek.zzq(parcel, readInt);
                    break;
                case 13:
                    j4 = zzbek.zzi(parcel, readInt);
                    break;
                case 14:
                    j5 = zzbek.zzi(parcel, readInt);
                    break;
                case 15:
                    i = zzbek.zzg(parcel, readInt);
                    break;
                case 16:
                    z3 = zzbek.zzc(parcel, readInt);
                    break;
                default:
                    zzbek.zzb(parcel, readInt);
                    break;
            }
        }
        zzbek.zzaf(parcel, zzd);
        return new zzcff(str, str2, str3, str4, j, j3, str5, z, z2, j2, str6, j4, j5, i, z3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzcff[] newArray(int i) {
        return new zzcff[i];
    }
}
