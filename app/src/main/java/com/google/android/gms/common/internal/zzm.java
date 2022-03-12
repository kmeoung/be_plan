package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes.dex */
public final class zzm implements zzj {
    private /* synthetic */ zzd zzfwg;

    public zzm(zzd zzdVar) {
        this.zzfwg = zzdVar;
    }

    @Override // com.google.android.gms.common.internal.zzj
    public final void zzf(@NonNull ConnectionResult connectionResult) {
        zzg zzgVar;
        zzg zzgVar2;
        if (connectionResult.isSuccess()) {
            this.zzfwg.zza((zzan) null, this.zzfwg.zzakd());
            return;
        }
        zzgVar = this.zzfwg.zzfvy;
        if (zzgVar != null) {
            zzgVar2 = this.zzfwg.zzfvy;
            zzgVar2.onConnectionFailed(connectionResult);
        }
    }
}
