package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes.dex */
public final class zzbzx extends zzed implements zzbzv {
    public zzbzx(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.flags.IFlagProvider");
    }

    @Override // com.google.android.gms.internal.zzbzv
    public final boolean getBooleanFlagValue(String str, boolean z, int i) throws RemoteException {
        Parcel zzaz = zzaz();
        zzaz.writeString(str);
        zzef.zza(zzaz, z);
        zzaz.writeInt(i);
        Parcel zza = zza(2, zzaz);
        boolean zza2 = zzef.zza(zza);
        zza.recycle();
        return zza2;
    }

    @Override // com.google.android.gms.internal.zzbzv
    public final int getIntFlagValue(String str, int i, int i2) throws RemoteException {
        Parcel zzaz = zzaz();
        zzaz.writeString(str);
        zzaz.writeInt(i);
        zzaz.writeInt(i2);
        Parcel zza = zza(3, zzaz);
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    @Override // com.google.android.gms.internal.zzbzv
    public final long getLongFlagValue(String str, long j, int i) throws RemoteException {
        Parcel zzaz = zzaz();
        zzaz.writeString(str);
        zzaz.writeLong(j);
        zzaz.writeInt(i);
        Parcel zza = zza(4, zzaz);
        long readLong = zza.readLong();
        zza.recycle();
        return readLong;
    }

    @Override // com.google.android.gms.internal.zzbzv
    public final String getStringFlagValue(String str, String str2, int i) throws RemoteException {
        Parcel zzaz = zzaz();
        zzaz.writeString(str);
        zzaz.writeString(str2);
        zzaz.writeInt(i);
        Parcel zza = zza(5, zzaz);
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    @Override // com.google.android.gms.internal.zzbzv
    public final void init(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, iObjectWrapper);
        zzb(1, zzaz);
    }
}
