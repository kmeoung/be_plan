package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface zzcgb extends IInterface {
    List<zzckk> zza(zzcff zzcffVar, boolean z) throws RemoteException;

    List<zzcfi> zza(String str, String str2, zzcff zzcffVar) throws RemoteException;

    List<zzckk> zza(String str, String str2, String str3, boolean z) throws RemoteException;

    List<zzckk> zza(String str, String str2, boolean z, zzcff zzcffVar) throws RemoteException;

    void zza(long j, String str, String str2, String str3) throws RemoteException;

    void zza(zzcff zzcffVar) throws RemoteException;

    void zza(zzcfi zzcfiVar, zzcff zzcffVar) throws RemoteException;

    void zza(zzcfx zzcfxVar, zzcff zzcffVar) throws RemoteException;

    void zza(zzcfx zzcfxVar, String str, String str2) throws RemoteException;

    void zza(zzckk zzckkVar, zzcff zzcffVar) throws RemoteException;

    byte[] zza(zzcfx zzcfxVar, String str) throws RemoteException;

    void zzb(zzcff zzcffVar) throws RemoteException;

    void zzb(zzcfi zzcfiVar) throws RemoteException;

    String zzc(zzcff zzcffVar) throws RemoteException;

    void zzd(zzcff zzcffVar) throws RemoteException;

    List<zzcfi> zzj(String str, String str2, String str3) throws RemoteException;
}
