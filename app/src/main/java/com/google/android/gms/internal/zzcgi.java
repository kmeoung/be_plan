package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;

/* loaded from: classes.dex */
public final class zzcgi extends zzd<zzcgb> {
    public zzcgi(Context context, Looper looper, zzf zzfVar, zzg zzgVar) {
        super(context, looper, 93, zzfVar, zzgVar, null);
    }

    @Override // com.google.android.gms.common.internal.zzd
    public final /* synthetic */ zzcgb zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
        return queryLocalInterface instanceof zzcgb ? (zzcgb) queryLocalInterface : new zzcgd(iBinder);
    }

    @Override // com.google.android.gms.common.internal.zzd
    @NonNull
    protected final String zzhf() {
        return "com.google.android.gms.measurement.START";
    }

    @Override // com.google.android.gms.common.internal.zzd
    @NonNull
    public final String zzhg() {
        return "com.google.android.gms.measurement.internal.IMeasurementService";
    }
}
