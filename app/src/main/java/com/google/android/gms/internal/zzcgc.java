package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public abstract class zzcgc extends zzee implements zzcgb {
    public zzcgc() {
        attachInterface(this, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        Object obj;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zza((zzcfx) zzef.zza(parcel, zzcfx.CREATOR), (zzcff) zzef.zza(parcel, zzcff.CREATOR));
                parcel2.writeNoException();
                return true;
            case 2:
                zza((zzckk) zzef.zza(parcel, zzckk.CREATOR), (zzcff) zzef.zza(parcel, zzcff.CREATOR));
                parcel2.writeNoException();
                return true;
            case 3:
            case 8:
            default:
                return false;
            case 4:
                zza((zzcff) zzef.zza(parcel, zzcff.CREATOR));
                parcel2.writeNoException();
                return true;
            case 5:
                zza((zzcfx) zzef.zza(parcel, zzcfx.CREATOR), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 6:
                zzb((zzcff) zzef.zza(parcel, zzcff.CREATOR));
                parcel2.writeNoException();
                return true;
            case 7:
                obj = zza((zzcff) zzef.zza(parcel, zzcff.CREATOR), zzef.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(obj);
                return true;
            case 9:
                byte[] zza = zza((zzcfx) zzef.zza(parcel, zzcfx.CREATOR), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeByteArray(zza);
                return true;
            case 10:
                zza(parcel.readLong(), parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 11:
                String zzc = zzc((zzcff) zzef.zza(parcel, zzcff.CREATOR));
                parcel2.writeNoException();
                parcel2.writeString(zzc);
                return true;
            case 12:
                zza((zzcfi) zzef.zza(parcel, zzcfi.CREATOR), (zzcff) zzef.zza(parcel, zzcff.CREATOR));
                parcel2.writeNoException();
                return true;
            case 13:
                zzb((zzcfi) zzef.zza(parcel, zzcfi.CREATOR));
                parcel2.writeNoException();
                return true;
            case 14:
                obj = zza(parcel.readString(), parcel.readString(), zzef.zza(parcel), (zzcff) zzef.zza(parcel, zzcff.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(obj);
                return true;
            case 15:
                obj = zza(parcel.readString(), parcel.readString(), parcel.readString(), zzef.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(obj);
                return true;
            case 16:
                obj = zza(parcel.readString(), parcel.readString(), (zzcff) zzef.zza(parcel, zzcff.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(obj);
                return true;
            case 17:
                obj = zzj(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeTypedList(obj);
                return true;
            case 18:
                zzd((zzcff) zzef.zza(parcel, zzcff.CREATOR));
                parcel2.writeNoException();
                return true;
        }
    }
}
