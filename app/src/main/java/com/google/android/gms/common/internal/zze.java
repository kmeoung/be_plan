package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes.dex */
public abstract class zze extends zzi<Boolean> {
    private int statusCode;
    private Bundle zzfwf;
    private /* synthetic */ zzd zzfwg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @BinderThread
    public zze(zzd zzdVar, int i, Bundle bundle) {
        super(zzdVar, true);
        this.zzfwg = zzdVar;
        this.statusCode = i;
        this.zzfwf = bundle;
    }

    protected abstract boolean zzakf();

    protected abstract void zzj(ConnectionResult connectionResult);

    @Override // com.google.android.gms.common.internal.zzi
    protected final /* synthetic */ void zzv(Boolean bool) {
        PendingIntent pendingIntent = null;
        if (bool == null) {
            this.zzfwg.zza(1, (int) null);
            return;
        }
        int i = this.statusCode;
        if (i != 0) {
            if (i != 10) {
                this.zzfwg.zza(1, (int) null);
                if (this.zzfwf != null) {
                    pendingIntent = (PendingIntent) this.zzfwf.getParcelable("pendingIntent");
                }
                zzj(new ConnectionResult(this.statusCode, pendingIntent));
                return;
            }
            this.zzfwg.zza(1, (int) null);
            throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
        } else if (!zzakf()) {
            this.zzfwg.zza(1, (int) null);
            zzj(new ConnectionResult(8, null));
        }
    }
}
