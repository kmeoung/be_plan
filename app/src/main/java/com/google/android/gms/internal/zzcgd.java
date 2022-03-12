package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class zzcgd extends zzed implements zzcgb {
    public zzcgd(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final List<zzckk> zza(zzcff zzcffVar, boolean z) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzcffVar);
        zzef.zza(zzaz, z);
        Parcel zza = zza(7, zzaz);
        ArrayList createTypedArrayList = zza.createTypedArrayList(zzckk.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final List<zzcfi> zza(String str, String str2, zzcff zzcffVar) throws RemoteException {
        Parcel zzaz = zzaz();
        zzaz.writeString(str);
        zzaz.writeString(str2);
        zzef.zza(zzaz, zzcffVar);
        Parcel zza = zza(16, zzaz);
        ArrayList createTypedArrayList = zza.createTypedArrayList(zzcfi.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final List<zzckk> zza(String str, String str2, String str3, boolean z) throws RemoteException {
        Parcel zzaz = zzaz();
        zzaz.writeString(str);
        zzaz.writeString(str2);
        zzaz.writeString(str3);
        zzef.zza(zzaz, z);
        Parcel zza = zza(15, zzaz);
        ArrayList createTypedArrayList = zza.createTypedArrayList(zzckk.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final List<zzckk> zza(String str, String str2, boolean z, zzcff zzcffVar) throws RemoteException {
        Parcel zzaz = zzaz();
        zzaz.writeString(str);
        zzaz.writeString(str2);
        zzef.zza(zzaz, z);
        zzef.zza(zzaz, zzcffVar);
        Parcel zza = zza(14, zzaz);
        ArrayList createTypedArrayList = zza.createTypedArrayList(zzckk.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final void zza(long j, String str, String str2, String str3) throws RemoteException {
        Parcel zzaz = zzaz();
        zzaz.writeLong(j);
        zzaz.writeString(str);
        zzaz.writeString(str2);
        zzaz.writeString(str3);
        zzb(10, zzaz);
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final void zza(zzcff zzcffVar) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzcffVar);
        zzb(4, zzaz);
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final void zza(zzcfi zzcfiVar, zzcff zzcffVar) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzcfiVar);
        zzef.zza(zzaz, zzcffVar);
        zzb(12, zzaz);
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final void zza(zzcfx zzcfxVar, zzcff zzcffVar) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzcfxVar);
        zzef.zza(zzaz, zzcffVar);
        zzb(1, zzaz);
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final void zza(zzcfx zzcfxVar, String str, String str2) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzcfxVar);
        zzaz.writeString(str);
        zzaz.writeString(str2);
        zzb(5, zzaz);
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final void zza(zzckk zzckkVar, zzcff zzcffVar) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzckkVar);
        zzef.zza(zzaz, zzcffVar);
        zzb(2, zzaz);
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final byte[] zza(zzcfx zzcfxVar, String str) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzcfxVar);
        zzaz.writeString(str);
        Parcel zza = zza(9, zzaz);
        byte[] createByteArray = zza.createByteArray();
        zza.recycle();
        return createByteArray;
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final void zzb(zzcff zzcffVar) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzcffVar);
        zzb(6, zzaz);
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final void zzb(zzcfi zzcfiVar) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzcfiVar);
        zzb(13, zzaz);
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final String zzc(zzcff zzcffVar) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzcffVar);
        Parcel zza = zza(11, zzaz);
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final void zzd(zzcff zzcffVar) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzcffVar);
        zzb(18, zzaz);
    }

    @Override // com.google.android.gms.internal.zzcgb
    public final List<zzcfi> zzj(String str, String str2, String str3) throws RemoteException {
        Parcel zzaz = zzaz();
        zzaz.writeString(str);
        zzaz.writeString(str2);
        zzaz.writeString(str3);
        Parcel zza = zza(17, zzaz);
        ArrayList createTypedArrayList = zza.createTypedArrayList(zzcfi.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }
}
