package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

/* loaded from: classes.dex */
public abstract class zzax extends zzee implements zzaw {
    public zzax() {
        attachInterface(this, "com.google.android.gms.common.internal.IGmsCallbacks");
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zza(parcel.readInt(), parcel.readStrongBinder(), (Bundle) zzef.zza(parcel, Bundle.CREATOR));
                break;
            case 2:
                zza(parcel.readInt(), (Bundle) zzef.zza(parcel, Bundle.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
