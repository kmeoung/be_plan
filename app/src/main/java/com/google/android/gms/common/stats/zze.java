package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.zzj;
import java.util.List;

/* loaded from: classes.dex */
public final class zze {
    private static boolean zzgae = false;
    private static zze zzgbe = new zze();
    private static Boolean zzgbf;

    public static void zza(Context context, String str, int i, String str2, String str3, String str4, int i2, List<String> list) {
        zza(context, str, 8, str2, str3, str4, i2, list, 0L);
    }

    public static void zza(Context context, String str, int i, String str2, String str3, String str4, int i2, List<String> list, long j) {
        List<String> list2 = list;
        if (zzgbf == null) {
            zzgbf = false;
        }
        if (zzgbf.booleanValue()) {
            if (TextUtils.isEmpty(str)) {
                String valueOf = String.valueOf(str);
                Log.e("WakeLockTracker", valueOf.length() != 0 ? "missing wakeLock key. ".concat(valueOf) : new String("missing wakeLock key. "));
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (7 == i || 8 == i || 10 == i || 11 == i) {
                if (list2 != null && list.size() == 1 && "com.google.android.gms".equals(list2.get(0))) {
                    list2 = null;
                }
                long elapsedRealtime = SystemClock.elapsedRealtime();
                int zzct = zzj.zzct(context);
                String packageName = context.getPackageName();
                try {
                    context.startService(new Intent().setComponent(zzb.zzgaj).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", new WakeLockEvent(currentTimeMillis, i, str2, i2, list2, str, elapsedRealtime, zzct, str3, "com.google.android.gms".equals(packageName) ? null : packageName, zzj.zzcu(context), j, str4)));
                } catch (Exception e) {
                    Log.wtf("WakeLockTracker", e);
                }
            }
        }
    }

    public static zze zzalt() {
        return zzgbe;
    }
}
