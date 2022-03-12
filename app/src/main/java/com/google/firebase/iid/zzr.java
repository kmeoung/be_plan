package com.google.firebase.iid;

import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.iid.MessengerCompat;
import com.google.android.gms.iid.zzi;

/* loaded from: classes.dex */
final class zzr {
    private static String zznuo = zzi.class.getName();
    final Messenger zzicr;
    final MessengerCompat zznup;

    public zzr(IBinder iBinder) throws RemoteException {
        String interfaceDescriptor = iBinder.getInterfaceDescriptor();
        if ("android.os.IMessenger".equals(interfaceDescriptor)) {
            this.zzicr = new Messenger(iBinder);
            this.zznup = null;
        } else if (zznuo.equals(interfaceDescriptor)) {
            this.zznup = new MessengerCompat(iBinder);
            this.zzicr = null;
        } else {
            String valueOf = String.valueOf(interfaceDescriptor);
            Log.w("MessengerIpcClient", valueOf.length() != 0 ? "Invalid interface descriptor: ".concat(valueOf) : new String("Invalid interface descriptor: "));
            throw new RemoteException();
        }
    }
}
