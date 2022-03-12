package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import com.google.android.gms.common.internal.zzbq;

/* loaded from: classes.dex */
public final class zzcha {
    private final zzchc zzjax;

    public zzcha(zzchc zzchcVar) {
        zzbq.checkNotNull(zzchcVar);
        this.zzjax = zzchcVar;
    }

    public static boolean zzbi(Context context) {
        ActivityInfo receiverInfo;
        zzbq.checkNotNull(context);
        try {
            PackageManager packageManager = context.getPackageManager();
            if (!(packageManager == null || (receiverInfo = packageManager.getReceiverInfo(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementReceiver"), 2)) == null)) {
                if (receiverInfo.enabled) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    @MainThread
    public final void onReceive(Context context, Intent intent) {
        zzchj zzdu = zzchj.zzdu(context);
        zzcgj zzawm = zzdu.zzawm();
        if (intent == null) {
            zzawm.zzayt().log("Receiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zzawm.zzayx().zzj("Local receiver got", action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            Intent className = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            className.setAction("com.google.android.gms.measurement.UPLOAD");
            zzawm.zzayx().log("Starting wakeful intent.");
            this.zzjax.doStartService(context, className);
        } else if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            BroadcastReceiver.PendingResult doGoAsync = this.zzjax.doGoAsync();
            String stringExtra = intent.getStringExtra("referrer");
            if (stringExtra == null) {
                zzawm.zzayx().log("Install referrer extras are null");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                    return;
                }
                return;
            }
            zzawm.zzayv().zzj("Install referrer extras are", stringExtra);
            if (!stringExtra.contains("?")) {
                String valueOf = String.valueOf(stringExtra);
                stringExtra = valueOf.length() != 0 ? "?".concat(valueOf) : new String("?");
            }
            Bundle zzp = zzdu.zzawi().zzp(Uri.parse(stringExtra));
            if (zzp == null) {
                zzawm.zzayx().log("No campaign defined in install referrer broadcast");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                    return;
                }
                return;
            }
            long longExtra = 1000 * intent.getLongExtra("referrer_timestamp_seconds", 0L);
            if (longExtra == 0) {
                zzawm.zzayt().log("Install referrer is missing timestamp");
            }
            zzdu.zzawl().zzg(new zzchb(this, zzdu, longExtra, zzp, context, zzawm, doGoAsync));
        }
    }
}
