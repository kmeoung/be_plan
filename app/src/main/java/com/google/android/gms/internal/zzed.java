package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public class zzed implements IInterface {
    private final IBinder zzakd;
    private final String zzake;

    public zzed(IBinder iBinder, String str) {
        this.zzakd = iBinder;
        this.zzake = str;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.zzakd;
    }

    public final Parcel zza(int i, Parcel parcel) throws RemoteException {
        parcel = Parcel.obtain();
        try {
            this.zzakd.transact(i, parcel, parcel, 0);
            parcel.readException();
            return parcel;
        } catch (RuntimeException e) {
            throw e;
        } finally {
            parcel.recycle();
        }
    }

    public final Parcel zzaz() {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken(this.zzake);
        return obtain;
    }

    public final void zzb(int i, Parcel parcel) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            this.zzakd.transact(i, parcel, obtain, 0);
            obtain.readException();
        } finally {
            parcel.recycle();
            obtain.recycle();
        }
    }

    public final void zzc(int i, Parcel parcel) throws RemoteException {
        try {
            this.zzakd.transact(i, parcel, null, 1);
        } finally {
            parcel.recycle();
        }
    }
}
