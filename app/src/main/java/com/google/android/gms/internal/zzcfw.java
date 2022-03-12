package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class zzcfw implements Parcelable.Creator<zzcfu> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzcfu createFromParcel(Parcel parcel) {
        int zzd = zzbek.zzd(parcel);
        Bundle bundle = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            if ((65535 & readInt) != 2) {
                zzbek.zzb(parcel, readInt);
            } else {
                bundle = zzbek.zzs(parcel, readInt);
            }
        }
        zzbek.zzaf(parcel, zzd);
        return new zzcfu(bundle);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzcfu[] newArray(int i) {
        return new zzcfu[i];
    }
}
