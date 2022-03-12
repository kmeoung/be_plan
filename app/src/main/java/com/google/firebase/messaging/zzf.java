package com.google.firebase.messaging;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbek;

/* loaded from: classes.dex */
public final class zzf implements Parcelable.Creator<RemoteMessage> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ RemoteMessage createFromParcel(Parcel parcel) {
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
        return new RemoteMessage(bundle);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ RemoteMessage[] newArray(int i) {
        return new RemoteMessage[i];
    }
}
