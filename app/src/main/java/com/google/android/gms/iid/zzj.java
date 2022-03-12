package com.google.android.gms.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;

/* loaded from: classes.dex */
public final class zzj extends zzed implements zzi {
    public zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.iid.IMessengerCompat");
    }

    @Override // com.google.android.gms.iid.zzi
    public final void send(Message message) throws RemoteException {
        Parcel zzaz = zzaz();
        zzef.zza(zzaz, message);
        zzc(1, zzaz);
    }
}
