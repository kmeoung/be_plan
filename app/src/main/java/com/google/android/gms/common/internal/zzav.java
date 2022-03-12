package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzed;

/* loaded from: classes.dex */
public final class zzav extends zzed implements zzat {
    public zzav(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ICertData");
    }

    @Override // com.google.android.gms.common.internal.zzat
    public final IObjectWrapper zzafo() throws RemoteException {
        Parcel zza = zza(1, zzaz());
        IObjectWrapper zzap = IObjectWrapper.zza.zzap(zza.readStrongBinder());
        zza.recycle();
        return zzap;
    }

    @Override // com.google.android.gms.common.internal.zzat
    public final int zzafp() throws RemoteException {
        Parcel zza = zza(2, zzaz());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }
}
