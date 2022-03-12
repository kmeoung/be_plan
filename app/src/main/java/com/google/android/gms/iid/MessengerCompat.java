package com.google.android.gms.iid;

import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.ReflectedParcelable;

/* loaded from: classes.dex */
public class MessengerCompat implements ReflectedParcelable {
    public static final Parcelable.Creator<MessengerCompat> CREATOR = new zzk();
    private Messenger zzicr;
    private zzi zzics;

    public MessengerCompat(IBinder iBinder) {
        zzi zziVar;
        if (Build.VERSION.SDK_INT >= 21) {
            this.zzicr = new Messenger(iBinder);
            return;
        }
        if (iBinder == null) {
            zziVar = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.iid.IMessengerCompat");
            zziVar = queryLocalInterface instanceof zzi ? (zzi) queryLocalInterface : new zzj(iBinder);
        }
        this.zzics = zziVar;
    }

    private final IBinder getBinder() {
        return this.zzicr != null ? this.zzicr.getBinder() : this.zzics.asBinder();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return getBinder().equals(((MessengerCompat) obj).getBinder());
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public int hashCode() {
        return getBinder().hashCode();
    }

    public final void send(Message message) throws RemoteException {
        if (this.zzicr != null) {
            this.zzicr.send(message);
        } else {
            this.zzics.send(message);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.zzicr != null ? this.zzicr.getBinder() : this.zzics.asBinder());
    }
}
