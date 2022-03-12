package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class zzab {
    public static boolean DEBUG = Log.isLoggable("Volley", 2);
    private static String TAG = "Volley";

    /* loaded from: classes.dex */
    public static class zza {
        public static final boolean zzbi = zzab.DEBUG;
        private final List<zzac> zzbj = new ArrayList();
        private boolean zzbk = false;

        protected final void finalize() throws Throwable {
            if (!this.zzbk) {
                zzc("Request on the loose");
                zzab.zzc("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        public final synchronized void zza(String str, long j) {
            if (this.zzbk) {
                throw new IllegalStateException("Marker added to finished log");
            }
            this.zzbj.add(new zzac(str, j, SystemClock.elapsedRealtime()));
        }

        public final synchronized void zzc(String str) {
            this.zzbk = true;
            long j = this.zzbj.size() == 0 ? 0L : this.zzbj.get(this.zzbj.size() - 1).time - this.zzbj.get(0).time;
            if (j > 0) {
                long j2 = this.zzbj.get(0).time;
                zzab.zzb("(%-4d ms) %s", Long.valueOf(j), str);
                for (zzac zzacVar : this.zzbj) {
                    long j3 = zzacVar.time;
                    zzab.zzb("(+%-4d) [%2d] %s", Long.valueOf(j3 - j2), Long.valueOf(zzacVar.zzbl), zzacVar.name);
                    j2 = j3;
                }
            }
        }
    }

    public static void zza(String str, Object... objArr) {
        if (DEBUG) {
            Log.v(TAG, zzd(str, objArr));
        }
    }

    public static void zza(Throwable th, String str, Object... objArr) {
        Log.e(TAG, zzd(str, objArr), th);
    }

    public static void zzb(String str, Object... objArr) {
        Log.d(TAG, zzd(str, objArr));
    }

    public static void zzc(String str, Object... objArr) {
        Log.e(TAG, zzd(str, objArr));
    }

    private static String zzd(String str, Object... objArr) {
        if (objArr != null) {
            str = String.format(Locale.US, str, objArr);
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        String str2 = "<unknown>";
        int i = 2;
        while (true) {
            if (i >= stackTrace.length) {
                break;
            } else if (!stackTrace[i].getClass().equals(zzab.class)) {
                String className = stackTrace[i].getClassName();
                String substring = className.substring(className.lastIndexOf(46) + 1);
                String substring2 = substring.substring(substring.lastIndexOf(36) + 1);
                String methodName = stackTrace[i].getMethodName();
                StringBuilder sb = new StringBuilder(String.valueOf(substring2).length() + 1 + String.valueOf(methodName).length());
                sb.append(substring2);
                sb.append(".");
                sb.append(methodName);
                str2 = sb.toString();
                break;
            } else {
                i++;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Long.valueOf(Thread.currentThread().getId()), str2, str);
    }
}
