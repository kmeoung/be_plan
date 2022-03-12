package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes.dex */
public final class zzo extends zze {
    private /* synthetic */ zzd zzfwg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @BinderThread
    public zzo(zzd zzdVar, @Nullable int i, Bundle bundle) {
        super(zzdVar, i, null);
        this.zzfwg = zzdVar;
    }

    @Override // com.google.android.gms.common.internal.zze
    protected final boolean zzakf() {
        this.zzfwg.zzfvs.zzf(ConnectionResult.zzfhy);
        return true;
    }

    @Override // com.google.android.gms.common.internal.zze
    protected final void zzj(ConnectionResult connectionResult) {
        this.zzfwg.zzfvs.zzf(connectionResult);
        this.zzfwg.onConnectionFailed(connectionResult);
    }
}
