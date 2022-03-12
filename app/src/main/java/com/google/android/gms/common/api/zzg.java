package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbek;

/* loaded from: classes.dex */
public final class zzg implements Parcelable.Creator<Status> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Status createFromParcel(Parcel parcel) {
        int zzd = zzbek.zzd(parcel);
        String str = null;
        int i = 0;
        PendingIntent pendingIntent = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            int i3 = 65535 & readInt;
            if (i3 != 1000) {
                switch (i3) {
                    case 1:
                        i2 = zzbek.zzg(parcel, readInt);
                        continue;
                    case 2:
                        str = zzbek.zzq(parcel, readInt);
                        continue;
                    case 3:
                        pendingIntent = (PendingIntent) zzbek.zza(parcel, readInt, PendingIntent.CREATOR);
                        continue;
                    default:
                        zzbek.zzb(parcel, readInt);
                        continue;
                }
            } else {
                i = zzbek.zzg(parcel, readInt);
            }
        }
        zzbek.zzaf(parcel, zzd);
        return new Status(i, i2, str, pendingIntent);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Status[] newArray(int i) {
        return new Status[i];
    }
}
