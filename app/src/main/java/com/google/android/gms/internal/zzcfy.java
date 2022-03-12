package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class zzcfy implements Parcelable.Creator<zzcfx> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzcfx createFromParcel(Parcel parcel) {
        int zzd = zzbek.zzd(parcel);
        String str = null;
        long j = 0;
        zzcfu zzcfuVar = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = zzbek.zzq(parcel, readInt);
                    break;
                case 3:
                    zzcfuVar = (zzcfu) zzbek.zza(parcel, readInt, zzcfu.CREATOR);
                    break;
                case 4:
                    str2 = zzbek.zzq(parcel, readInt);
                    break;
                case 5:
                    j = zzbek.zzi(parcel, readInt);
                    break;
                default:
                    zzbek.zzb(parcel, readInt);
                    break;
            }
        }
        zzbek.zzaf(parcel, zzd);
        return new zzcfx(str, zzcfuVar, str2, j);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzcfx[] newArray(int i) {
        return new zzcfx[i];
    }
}
