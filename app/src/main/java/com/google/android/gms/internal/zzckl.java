package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class zzckl implements Parcelable.Creator<zzckk> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzckk createFromParcel(Parcel parcel) {
        int zzd = zzbek.zzd(parcel);
        String str = null;
        long j = 0;
        Long l = null;
        Float f = null;
        String str2 = null;
        String str3 = null;
        Double d = null;
        int i = 0;
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
                    j = zzbek.zzi(parcel, readInt);
                    break;
                case 4:
                    l = zzbek.zzj(parcel, readInt);
                    break;
                case 5:
                    f = zzbek.zzm(parcel, readInt);
                    break;
                case 6:
                    str2 = zzbek.zzq(parcel, readInt);
                    break;
                case 7:
                    str3 = zzbek.zzq(parcel, readInt);
                    break;
                case 8:
                    d = zzbek.zzo(parcel, readInt);
                    break;
                default:
                    zzbek.zzb(parcel, readInt);
                    break;
            }
        }
        zzbek.zzaf(parcel, zzd);
        return new zzckk(i, str, j, l, f, str2, str3, d);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzckk[] newArray(int i) {
        return new zzckk[i];
    }
}
