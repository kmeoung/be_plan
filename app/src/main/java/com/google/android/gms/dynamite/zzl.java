package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;

/* loaded from: classes.dex */
public final class zzl extends zzed implements zzk {
    public zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoader");
    }

    @Override // com.google.android.gms.dynamite.zzk
    public final int zza(IObjectWrapper iObjectWrapper, String str, boolean z) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, iObjectWrapper);
        zzaz.writeString(str);
        zzef.zza(zzaz, z);
        Parcel zza = zza(3, zzaz);
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    @Override // com.google.android.gms.dynamite.zzk
    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String str, int i) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, iObjectWrapper);
        zzaz.writeString(str);
        zzaz.writeInt(i);
        Parcel zza = zza(2, zzaz);
        IObjectWrapper zzap = IObjectWrapper.zza.zzap(zza.readStrongBinder());
        zza.recycle();
        return zzap;
    }
}
