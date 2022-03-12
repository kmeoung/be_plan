package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes.dex */
public final class zzh extends Handler {
    private /* synthetic */ zzd zzfwg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzh(zzd zzdVar, Looper looper) {
        super(looper);
        this.zzfwg = zzdVar;
    }

    private static void zza(Message message) {
        ((zzi) message.obj).unregister();
    }

    private static boolean zzb(Message message) {
        return message.what == 2 || message.what == 1 || message.what == 7;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        zzf zzfVar;
        zzf zzfVar2;
        ConnectionResult connectionResult;
        boolean zzake;
        ConnectionResult connectionResult2;
        boolean z;
        if (this.zzfwg.zzfwd.get() != message.arg1) {
            if (zzb(message)) {
                zza(message);
            }
        } else if ((message.what == 1 || message.what == 7 || message.what == 4 || message.what == 5) && !this.zzfwg.isConnecting()) {
            zza(message);
        } else {
            PendingIntent pendingIntent = null;
            if (message.what == 4) {
                this.zzfwg.zzfwb = new ConnectionResult(message.arg2);
                zzake = this.zzfwg.zzake();
                if (zzake) {
                    z = this.zzfwg.zzfwc;
                    if (!z) {
                        this.zzfwg.zza(3, (int) null);
                        return;
                    }
                }
                connectionResult2 = this.zzfwg.zzfwb;
                ConnectionResult connectionResult3 = connectionResult2 != null ? this.zzfwg.zzfwb : new ConnectionResult(8);
                this.zzfwg.zzfvs.zzf(connectionResult3);
                this.zzfwg.onConnectionFailed(connectionResult3);
            } else if (message.what == 5) {
                connectionResult = this.zzfwg.zzfwb;
                ConnectionResult connectionResult4 = connectionResult != null ? this.zzfwg.zzfwb : new ConnectionResult(8);
                this.zzfwg.zzfvs.zzf(connectionResult4);
                this.zzfwg.onConnectionFailed(connectionResult4);
            } else if (message.what == 3) {
                if (message.obj instanceof PendingIntent) {
                    pendingIntent = (PendingIntent) message.obj;
                }
                ConnectionResult connectionResult5 = new ConnectionResult(message.arg2, pendingIntent);
                this.zzfwg.zzfvs.zzf(connectionResult5);
                this.zzfwg.onConnectionFailed(connectionResult5);
            } else if (message.what == 6) {
                this.zzfwg.zza(5, (int) null);
                zzfVar = this.zzfwg.zzfvx;
                if (zzfVar != null) {
                    zzfVar2 = this.zzfwg.zzfvx;
                    zzfVar2.onConnectionSuspended(message.arg2);
                }
                this.zzfwg.onConnectionSuspended(message.arg2);
                this.zzfwg.zza(5, 1, (int) null);
            } else if (message.what == 2 && !this.zzfwg.isConnected()) {
                zza(message);
            } else if (zzb(message)) {
                ((zzi) message.obj).zzakg();
            } else {
                int i = message.what;
                StringBuilder sb = new StringBuilder(45);
                sb.append("Don't know how to handle message: ");
                sb.append(i);
                Log.wtf("GmsClient", sb.toString(), new Exception());
            }
        }
    }
}
