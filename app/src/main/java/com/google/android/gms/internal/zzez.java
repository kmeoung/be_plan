package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import io.fabric.sdk.android.services.common.AdvertisingInfoServiceStrategy;

/* loaded from: classes.dex */
public final class zzez extends zzed implements zzex {
    public zzez(IBinder iBinder) {
        super(iBinder, AdvertisingInfoServiceStrategy.AdvertisingInterface.ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
    }

    @Override // com.google.android.gms.internal.zzex
    public final String getId() throws RemoteException {
        Parcel zza = zza(1, zzaz());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    @Override // com.google.android.gms.internal.zzex
    public final boolean zzb(boolean z) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, true);
        Parcel zza = zza(2, zzaz);
        boolean zza2 = zzef.zza(zza);
        zza.recycle();
        return zza2;
    }
}
