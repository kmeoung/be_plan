package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzbej;
import com.google.android.gms.internal.zzbem;

/* loaded from: classes.dex */
public final class zzm extends zzbej {
    public static final Parcelable.Creator<zzm> CREATOR = new zzn();
    private final String zzfim;
    private final zzg zzfin;
    private final boolean zzfio;

    public zzm(String str, IBinder iBinder, boolean z) {
        this.zzfim = str;
        this.zzfin = zzaj(iBinder);
        this.zzfio = z;
    }

    public zzm(String str, zzg zzgVar, boolean z) {
        this.zzfim = str;
        this.zzfin = zzgVar;
        this.zzfio = z;
    }

    private static zzg zzaj(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        try {
            IObjectWrapper zzafo = zzau.zzal(iBinder).zzafo();
            byte[] bArr = zzafo == null ? null : (byte[]) zzn.zzx(zzafo);
            if (bArr != null) {
                return new zzh(bArr);
            }
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
            return null;
        } catch (RemoteException e) {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", e);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder;
        int zze = zzbem.zze(parcel);
        zzbem.zza(parcel, 1, this.zzfim, false);
        if (this.zzfin == null) {
            Log.w("GoogleCertificatesQuery", "certificate binder is null");
            iBinder = null;
        } else {
            iBinder = this.zzfin.asBinder();
        }
        zzbem.zza(parcel, 2, iBinder, false);
        zzbem.zza(parcel, 3, this.zzfio);
        zzbem.zzai(parcel, zze);
    }
}
