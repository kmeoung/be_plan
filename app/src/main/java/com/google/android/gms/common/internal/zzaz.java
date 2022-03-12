package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
final class zzaz implements zzay {
    private final IBinder zzakd;

    public zzaz(IBinder iBinder) {
        this.zzakd = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.zzakd;
    }

    @Override // com.google.android.gms.common.internal.zzay
    public final void zza(zzaw zzawVar, zzz zzzVar) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
            obtain.writeStrongBinder(zzawVar.asBinder());
            obtain.writeInt(1);
            zzzVar.writeToParcel(obtain, 0);
            this.zzakd.transact(46, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
