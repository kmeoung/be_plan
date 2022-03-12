package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzq;

/* loaded from: classes.dex */
public final class FirebaseInstanceIdReceiver extends WakefulBroadcastReceiver {
    private static boolean zzhzg = false;
    private static zzh zznty;
    private static zzh zzntz;

    private final void zza(Context context, Intent intent, String str) {
        String str2 = null;
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        if (Build.VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        String stringExtra = intent.getStringExtra("gcm.rawData64");
        boolean z = false;
        if (stringExtra != null) {
            intent.putExtra("rawData", Base64.decode(stringExtra, 0));
            intent.removeExtra("gcm.rawData64");
        }
        String stringExtra2 = intent.getStringExtra("from");
        if ("google.com/iid".equals(stringExtra2) || "gcm.googleapis.com/refresh".equals(stringExtra2) || "com.google.firebase.INSTANCE_ID_EVENT".equals(str)) {
            str2 = "com.google.firebase.INSTANCE_ID_EVENT";
        } else if ("com.google.android.c2dm.intent.RECEIVE".equals(str) || "com.google.firebase.MESSAGING_EVENT".equals(str)) {
            str2 = "com.google.firebase.MESSAGING_EVENT";
        } else {
            Log.d("FirebaseInstanceId", "Unexpected intent");
        }
        int i = -1;
        if (str2 != null) {
            if (zzq.isAtLeastO() && context.getApplicationInfo().targetSdkVersion > 25) {
                z = true;
            }
            if (z) {
                if (isOrderedBroadcast()) {
                    setResultCode(-1);
                }
                zzan(context, str2).zza(intent, goAsync());
            } else {
                i = zzaa.zzchl().zza(context, str2, intent);
            }
        }
        if (isOrderedBroadcast()) {
            setResultCode(i);
        }
    }

    private static synchronized zzh zzan(Context context, String str) {
        synchronized (FirebaseInstanceIdReceiver.class) {
            if ("com.google.firebase.MESSAGING_EVENT".equals(str)) {
                if (zzntz == null) {
                    zzntz = new zzh(context, str);
                }
                return zzntz;
            }
            if (zznty == null) {
                zznty = new zzh(context, str);
            }
            return zznty;
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Parcelable parcelableExtra = intent.getParcelableExtra("wrapped_intent");
            if (parcelableExtra instanceof Intent) {
                zza(context, (Intent) parcelableExtra, intent.getAction());
            } else {
                zza(context, intent, intent.getAction());
            }
        }
    }
}
