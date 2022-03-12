package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public final class zzj {
    private static IntentFilter zzgbr = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static long zzgbs = 0;
    private static float zzgbt = Float.NaN;

    @TargetApi(20)
    public static int zzct(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return -1;
        }
        Intent registerReceiver = context.getApplicationContext().registerReceiver(null, zzgbr);
        int i = 0;
        int i2 = ((registerReceiver == null ? 0 : registerReceiver.getIntExtra("plugged", 0)) & 7) != 0 ? 1 : 0;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return -1;
        }
        if (zzq.zzama() ? powerManager.isInteractive() : powerManager.isScreenOn()) {
            i = 1;
        }
        return (i << 1) | i2;
    }

    public static synchronized float zzcu(Context context) {
        synchronized (zzj.class) {
            if (SystemClock.elapsedRealtime() - zzgbs >= 60000 || Float.isNaN(zzgbt)) {
                Intent registerReceiver = context.getApplicationContext().registerReceiver(null, zzgbr);
                if (registerReceiver != null) {
                    zzgbt = registerReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1) / registerReceiver.getIntExtra("scale", -1);
                }
                zzgbs = SystemClock.elapsedRealtime();
                return zzgbt;
            }
            return zzgbt;
        }
    }
}
