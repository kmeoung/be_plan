package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbek;

/* loaded from: classes.dex */
public final class zzn implements Parcelable.Creator<zzm> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzm createFromParcel(Parcel parcel) {
        int zzd = zzbek.zzd(parcel);
        String str = null;
        IBinder iBinder = null;
        boolean z = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzbek.zzq(parcel, readInt);
                    break;
                case 2:
                    iBinder = zzbek.zzr(parcel, readInt);
                    break;
                case 3:
                    z = zzbek.zzc(parcel, readInt);
                    break;
                default:
                    zzbek.zzb(parcel, readInt);
                    break;
            }
        }
        zzbek.zzaf(parcel, zzd);
        return new zzm(str, iBinder, z);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzm[] newArray(int i) {
        return new zzm[i];
    }
}
