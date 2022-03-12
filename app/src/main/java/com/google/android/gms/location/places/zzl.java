package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbek;

/* loaded from: classes.dex */
public final class zzl implements Parcelable.Creator<PlaceReport> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ PlaceReport createFromParcel(Parcel parcel) {
        int zzd = zzbek.zzd(parcel);
        String str = null;
        int i = 0;
        String str2 = null;
        String str3 = null;
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
                    str3 = zzbek.zzq(parcel, readInt);
                    break;
                default:
                    zzbek.zzb(parcel, readInt);
                    break;
            }
        }
        zzbek.zzaf(parcel, zzd);
        return new PlaceReport(i, str, str2, str3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ PlaceReport[] newArray(int i) {
        return new PlaceReport[i];
    }
}
