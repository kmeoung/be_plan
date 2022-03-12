package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

/* loaded from: classes.dex */
public abstract class zzl extends zzee implements zzk {
    public zzl() {
        attachInterface(this, "com.google.android.gms.dynamic.IFragmentWrapper");
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        IInterface iInterface;
        int i3;
        boolean z;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        IObjectWrapper iObjectWrapper = null;
        switch (i) {
            case 2:
                iInterface = zzapl();
                parcel2.writeNoException();
                zzef.zza(parcel2, iInterface);
                return true;
            case 3:
                Bundle arguments = getArguments();
                parcel2.writeNoException();
                zzef.zzb(parcel2, arguments);
                return true;
            case 4:
                i3 = getId();
                parcel2.writeNoException();
                parcel2.writeInt(i3);
                return true;
            case 5:
                iInterface = zzapm();
                parcel2.writeNoException();
                zzef.zza(parcel2, iInterface);
                return true;
            case 6:
                iInterface = zzapn();
                parcel2.writeNoException();
                zzef.zza(parcel2, iInterface);
                return true;
            case 7:
                z = getRetainInstance();
                parcel2.writeNoException();
                zzef.zza(parcel2, z);
                return true;
            case 8:
                String tag = getTag();
                parcel2.writeNoException();
                parcel2.writeString(tag);
                return true;
            case 9:
                iInterface = zzapo();
                parcel2.writeNoException();
                zzef.zza(parcel2, iInterface);
                return true;
            case 10:
                i3 = getTargetRequestCode();
                parcel2.writeNoException();
                parcel2.writeInt(i3);
                return true;
            case 11:
                z = getUserVisibleHint();
                parcel2.writeNoException();
                zzef.zza(parcel2, z);
                return true;
            case 12:
                iInterface = getView();
                parcel2.writeNoException();
                zzef.zza(parcel2, iInterface);
                return true;
            case 13:
                z = isAdded();
                parcel2.writeNoException();
                zzef.zza(parcel2, z);
                return true;
            case 14:
                z = isDetached();
                parcel2.writeNoException();
                zzef.zza(parcel2, z);
                return true;
            case 15:
                z = isHidden();
                parcel2.writeNoException();
                zzef.zza(parcel2, z);
                return true;
            case 16:
                z = isInLayout();
                parcel2.writeNoException();
                zzef.zza(parcel2, z);
                return true;
            case 17:
                z = isRemoving();
                parcel2.writeNoException();
                zzef.zza(parcel2, z);
                return true;
            case 18:
                z = isResumed();
                parcel2.writeNoException();
                zzef.zza(parcel2, z);
                return true;
            case 19:
                z = isVisible();
                parcel2.writeNoException();
                zzef.zza(parcel2, z);
                return true;
            case 20:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                    iObjectWrapper = queryLocalInterface instanceof IObjectWrapper ? (IObjectWrapper) queryLocalInterface : new zzm(readStrongBinder);
                }
                zzv(iObjectWrapper);
                parcel2.writeNoException();
                return true;
            case 21:
                setHasOptionsMenu(zzef.zza(parcel));
                parcel2.writeNoException();
                return true;
            case 22:
                setMenuVisibility(zzef.zza(parcel));
                parcel2.writeNoException();
                return true;
            case 23:
                setRetainInstance(zzef.zza(parcel));
                parcel2.writeNoException();
                return true;
            case 24:
                setUserVisibleHint(zzef.zza(parcel));
                parcel2.writeNoException();
                return true;
            case 25:
                startActivity((Intent) zzef.zza(parcel, Intent.CREATOR));
                parcel2.writeNoException();
                return true;
            case 26:
                startActivityForResult((Intent) zzef.zza(parcel, Intent.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 27:
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                if (readStrongBinder2 != null) {
                    IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                    iObjectWrapper = queryLocalInterface2 instanceof IObjectWrapper ? (IObjectWrapper) queryLocalInterface2 : new zzm(readStrongBinder2);
                }
                zzw(iObjectWrapper);
                parcel2.writeNoException();
                return true;
            default:
                return false;
        }
    }
}
