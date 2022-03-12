package com.google.firebase.iid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.util.Log;

/* loaded from: classes.dex */
public class FirebaseInstanceIdService extends zzb {
    @Override // com.google.firebase.iid.zzb
    public final void handleIntent(Intent intent) {
        if ("com.google.firebase.iid.TOKEN_REFRESH".equals(intent.getAction())) {
            onTokenRefresh();
            return;
        }
        zzi zza = zzi.zza(this, intent.getStringExtra("subtype"), (Bundle) null);
        String stringExtra = intent.getStringExtra("CMD");
        if (Log.isLoggable("InstanceID", 3)) {
            String str = zza.zzico;
            String valueOf = String.valueOf(intent.getExtras());
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 22 + String.valueOf(stringExtra).length() + String.valueOf(valueOf).length());
            sb.append("Received command [");
            sb.append(str);
            sb.append("]: ");
            sb.append(stringExtra);
            sb.append(" ");
            sb.append(valueOf);
            Log.d("InstanceID", sb.toString());
        }
        if ("gcm.googleapis.com/refresh".equals(intent.getStringExtra("from"))) {
            zza.zzchd();
        } else if ("RST".equals(stringExtra)) {
            zza.zzchb();
        } else if ("RST_FULL".equals(stringExtra)) {
            zza.zzchc();
        } else if ("SYNC".equals(stringExtra)) {
            zza.zzchd();
        }
    }

    @WorkerThread
    public void onTokenRefresh() {
    }

    @Override // com.google.firebase.iid.zzb
    protected final Intent zzp(Intent intent) {
        return zzaa.zzchl().zznva.poll();
    }
}
