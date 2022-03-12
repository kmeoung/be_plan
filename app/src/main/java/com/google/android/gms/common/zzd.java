package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbek;

/* loaded from: classes.dex */
public final class zzd implements Parcelable.Creator<zzc> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzc createFromParcel(Parcel parcel) {
        int zzd = zzbek.zzd(parcel);
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzbek.zzq(parcel, readInt);
                    break;
                case 2:
                    i = zzbek.zzg(parcel, readInt);
                    break;
                default:
                    zzbek.zzb(parcel, readInt);
                    break;
            }
        }
        zzbek.zzaf(parcel, zzd);
        return new zzc(str, i);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzc[] newArray(int i) {
        return new zzc[i];
    }
}
