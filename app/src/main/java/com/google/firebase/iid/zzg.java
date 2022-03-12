package com.google.firebase.iid;

import android.util.Log;

/* loaded from: classes.dex */
public final class zzg implements Runnable {
    private /* synthetic */ zzd zzntp;
    private /* synthetic */ zzf zzntq;

    public zzg(zzf zzfVar, zzd zzdVar) {
        this.zzntq = zzfVar;
        this.zzntp = zzdVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzb zzbVar;
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "bg processing of the intent starting now");
        }
        zzbVar = this.zzntq.zznto;
        zzbVar.handleIntent(this.zzntp.intent);
        this.zzntp.finish();
    }
}
