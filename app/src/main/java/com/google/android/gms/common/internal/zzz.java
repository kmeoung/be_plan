package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import com.google.android.gms.internal.zzbej;
import com.google.android.gms.internal.zzbem;

/* loaded from: classes.dex */
public final class zzz extends zzbej {
    public static final Parcelable.Creator<zzz> CREATOR = new zzaa();
    private int version;
    private int zzfwx;
    private int zzfwy;
    String zzfwz;
    IBinder zzfxa;
    Scope[] zzfxb;
    Bundle zzfxc;
    Account zzfxd;
    zzc[] zzfxe;

    public zzz(int i) {
        this.version = 3;
        this.zzfwy = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzfwx = i;
    }

    public zzz(int i, int i2, int i3, String str, IBinder iBinder, Scope[] scopeArr, Bundle bundle, Account account, zzc[] zzcVarArr) {
        this.version = i;
        this.zzfwx = i2;
        this.zzfwy = i3;
        if ("com.google.android.gms".equals(str)) {
            this.zzfwz = "com.google.android.gms";
        } else {
            this.zzfwz = str;
        }
        if (i < 2) {
            Account account2 = null;
            zzan zzanVar = null;
            if (iBinder != null) {
                if (iBinder != null) {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
                    zzanVar = queryLocalInterface instanceof zzan ? (zzan) queryLocalInterface : new zzap(iBinder);
                }
                account2 = zza.zza(zzanVar);
            }
            this.zzfxd = account2;
        } else {
            this.zzfxa = iBinder;
            this.zzfxd = account;
        }
        this.zzfxb = scopeArr;
        this.zzfxc = bundle;
        this.zzfxe = zzcVarArr;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbem.zze(parcel);
        zzbem.zzc(parcel, 1, this.version);
        zzbem.zzc(parcel, 2, this.zzfwx);
        zzbem.zzc(parcel, 3, this.zzfwy);
        zzbem.zza(parcel, 4, this.zzfwz, false);
        zzbem.zza(parcel, 5, this.zzfxa, false);
        zzbem.zza(parcel, 6, (Parcelable[]) this.zzfxb, i, false);
        zzbem.zza(parcel, 7, this.zzfxc, false);
        zzbem.zza(parcel, 8, (Parcelable) this.zzfxd, i, false);
        zzbem.zza(parcel, 10, (Parcelable[]) this.zzfxe, i, false);
        zzbem.zzai(parcel, zze);
    }
}
