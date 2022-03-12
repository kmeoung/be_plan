package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes.dex */
public final class zza extends zzao {
    public static Account zza(zzan zzanVar) {
        if (zzanVar == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return zzanVar.getAccount();
        } catch (RemoteException unused) {
            Log.w("AccountAccessor", "Remote account accessor probably died");
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean equals(Object obj) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.common.internal.zzan
    public final Account getAccount() {
        throw new NoSuchMethodError();
    }
}
