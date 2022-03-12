package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.zzm;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;

/* loaded from: classes.dex */
public final class zzbc extends zzed implements zzba {
    public zzbc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
    }

    @Override // com.google.android.gms.common.internal.zzba
    public final boolean zza(zzm zzmVar, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, zzmVar);
        zzef.zza(zzaz, iObjectWrapper);
        Parcel zza = zza(5, zzaz);
        boolean zza2 = zzef.zza(zza);
        zza.recycle();
        return zza2;
    }
}
