package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzu;
import com.google.android.gms.common.util.zzy;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzcwr {
    private static boolean DEBUG = false;
    private static String TAG = "WakeLock";
    private static String zzjzk = "*gcore*:";
    private final Context mContext;
    private final String zzgat;
    private final String zzgav;
    private final PowerManager.WakeLock zzjzl;
    private WorkSource zzjzm;
    private final int zzjzn;
    private final String zzjzo;
    private boolean zzjzp;
    private final Map<String, Integer[]> zzjzq;
    private int zzjzr;

    public zzcwr(Context context, int i, String str) {
        this(context, 1, str, null, context == null ? null : context.getPackageName());
    }

    @SuppressLint({"UnwrappedWakeLock"})
    private zzcwr(Context context, int i, String str, String str2, String str3) {
        this(context, 1, str, null, str3, null);
    }

    @SuppressLint({"UnwrappedWakeLock"})
    private zzcwr(Context context, int i, String str, String str2, String str3, String str4) {
        this.zzjzp = true;
        this.zzjzq = new HashMap();
        zzbq.zzh(str, "Wake lock name can NOT be empty");
        this.zzjzn = i;
        this.zzjzo = null;
        this.zzgav = null;
        this.mContext = context.getApplicationContext();
        if (!"com.google.android.gms".equals(context.getPackageName())) {
            String valueOf = String.valueOf(zzjzk);
            String valueOf2 = String.valueOf(str);
            this.zzgat = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        } else {
            this.zzgat = str;
        }
        this.zzjzl = ((PowerManager) context.getSystemService("power")).newWakeLock(i, str);
        if (zzy.zzcv(this.mContext)) {
            this.zzjzm = zzy.zzaa(context, zzu.zzgn(str3) ? context.getPackageName() : str3);
            WorkSource workSource = this.zzjzm;
            if (workSource != null && zzy.zzcv(this.mContext)) {
                if (this.zzjzm != null) {
                    this.zzjzm.add(workSource);
                } else {
                    this.zzjzm = workSource;
                }
                try {
                    this.zzjzl.setWorkSource(this.zzjzm);
                } catch (IllegalArgumentException e) {
                    Log.wtf(TAG, e.toString());
                }
            }
        }
    }

    private final String zzku(String str) {
        if (!this.zzjzp || TextUtils.isEmpty(null)) {
            return this.zzjzo;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x004d, code lost:
        if (r13 == false) goto L_0x004f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0055, code lost:
        if (r11.zzjzr == 0) goto L_0x0057;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0057, code lost:
        com.google.android.gms.common.stats.zze.zzalt();
        com.google.android.gms.common.stats.zze.zza(r11.mContext, com.google.android.gms.common.stats.zzc.zza(r11.zzjzl, r4), 7, r11.zzgat, r4, null, r11.zzjzn, com.google.android.gms.common.util.zzy.zzb(r11.zzjzm), 1000);
        r11.zzjzr++;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void acquire(long r12) {
        /*
            r11 = this;
            r12 = 0
            java.lang.String r4 = r11.zzku(r12)
            monitor-enter(r11)
            java.util.Map<java.lang.String, java.lang.Integer[]> r12 = r11.zzjzq     // Catch: all -> 0x0081
            boolean r12 = r12.isEmpty()     // Catch: all -> 0x0081
            r13 = 0
            if (r12 == 0) goto L_0x0013
            int r12 = r11.zzjzr     // Catch: all -> 0x0081
            if (r12 <= 0) goto L_0x0022
        L_0x0013:
            android.os.PowerManager$WakeLock r12 = r11.zzjzl     // Catch: all -> 0x0081
            boolean r12 = r12.isHeld()     // Catch: all -> 0x0081
            if (r12 != 0) goto L_0x0022
            java.util.Map<java.lang.String, java.lang.Integer[]> r12 = r11.zzjzq     // Catch: all -> 0x0081
            r12.clear()     // Catch: all -> 0x0081
            r11.zzjzr = r13     // Catch: all -> 0x0081
        L_0x0022:
            boolean r12 = r11.zzjzp     // Catch: all -> 0x0081
            r10 = 1
            if (r12 == 0) goto L_0x004f
            java.util.Map<java.lang.String, java.lang.Integer[]> r12 = r11.zzjzq     // Catch: all -> 0x0081
            java.lang.Object r12 = r12.get(r4)     // Catch: all -> 0x0081
            java.lang.Integer[] r12 = (java.lang.Integer[]) r12     // Catch: all -> 0x0081
            if (r12 != 0) goto L_0x0040
            java.util.Map<java.lang.String, java.lang.Integer[]> r12 = r11.zzjzq     // Catch: all -> 0x0081
            java.lang.Integer[] r0 = new java.lang.Integer[r10]     // Catch: all -> 0x0081
            java.lang.Integer r1 = java.lang.Integer.valueOf(r10)     // Catch: all -> 0x0081
            r0[r13] = r1     // Catch: all -> 0x0081
            r12.put(r4, r0)     // Catch: all -> 0x0081
            r13 = 1
            goto L_0x004d
        L_0x0040:
            r0 = r12[r13]     // Catch: all -> 0x0081
            int r0 = r0.intValue()     // Catch: all -> 0x0081
            int r0 = r0 + r10
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: all -> 0x0081
            r12[r13] = r0     // Catch: all -> 0x0081
        L_0x004d:
            if (r13 != 0) goto L_0x0057
        L_0x004f:
            boolean r12 = r11.zzjzp     // Catch: all -> 0x0081
            if (r12 != 0) goto L_0x0078
            int r12 = r11.zzjzr     // Catch: all -> 0x0081
            if (r12 != 0) goto L_0x0078
        L_0x0057:
            com.google.android.gms.common.stats.zze.zzalt()     // Catch: all -> 0x0081
            android.content.Context r0 = r11.mContext     // Catch: all -> 0x0081
            android.os.PowerManager$WakeLock r12 = r11.zzjzl     // Catch: all -> 0x0081
            java.lang.String r1 = com.google.android.gms.common.stats.zzc.zza(r12, r4)     // Catch: all -> 0x0081
            r2 = 7
            java.lang.String r3 = r11.zzgat     // Catch: all -> 0x0081
            r5 = 0
            int r6 = r11.zzjzn     // Catch: all -> 0x0081
            android.os.WorkSource r12 = r11.zzjzm     // Catch: all -> 0x0081
            java.util.List r7 = com.google.android.gms.common.util.zzy.zzb(r12)     // Catch: all -> 0x0081
            r8 = 1000(0x3e8, double:4.94E-321)
            com.google.android.gms.common.stats.zze.zza(r0, r1, r2, r3, r4, r5, r6, r7, r8)     // Catch: all -> 0x0081
            int r12 = r11.zzjzr     // Catch: all -> 0x0081
            int r12 = r12 + r10
            r11.zzjzr = r12     // Catch: all -> 0x0081
        L_0x0078:
            monitor-exit(r11)     // Catch: all -> 0x0081
            android.os.PowerManager$WakeLock r12 = r11.zzjzl
            r0 = 1000(0x3e8, double:4.94E-321)
            r12.acquire(r0)
            return
        L_0x0081:
            r12 = move-exception
            monitor-exit(r11)     // Catch: all -> 0x0081
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcwr.acquire(long):void");
    }

    public final boolean isHeld() {
        return this.zzjzl.isHeld();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0033, code lost:
        if (r1 == false) goto L_0x0035;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003b, code lost:
        if (r10.zzjzr == 1) goto L_0x003d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003d, code lost:
        com.google.android.gms.common.stats.zze.zzalt();
        com.google.android.gms.common.stats.zze.zza(r10.mContext, com.google.android.gms.common.stats.zzc.zza(r10.zzjzl, r5), 8, r10.zzgat, r5, null, r10.zzjzn, com.google.android.gms.common.util.zzy.zzb(r10.zzjzm));
        r10.zzjzr--;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void release() {
        /*
            r10 = this;
            r0 = 0
            java.lang.String r5 = r10.zzku(r0)
            monitor-enter(r10)
            boolean r0 = r10.zzjzp     // Catch: all -> 0x0064
            r9 = 1
            if (r0 == 0) goto L_0x0035
            java.util.Map<java.lang.String, java.lang.Integer[]> r0 = r10.zzjzq     // Catch: all -> 0x0064
            java.lang.Object r0 = r0.get(r5)     // Catch: all -> 0x0064
            java.lang.Integer[] r0 = (java.lang.Integer[]) r0     // Catch: all -> 0x0064
            r1 = 0
            if (r0 != 0) goto L_0x0017
            goto L_0x0033
        L_0x0017:
            r2 = r0[r1]     // Catch: all -> 0x0064
            int r2 = r2.intValue()     // Catch: all -> 0x0064
            if (r2 != r9) goto L_0x0026
            java.util.Map<java.lang.String, java.lang.Integer[]> r0 = r10.zzjzq     // Catch: all -> 0x0064
            r0.remove(r5)     // Catch: all -> 0x0064
            r1 = 1
            goto L_0x0033
        L_0x0026:
            r2 = r0[r1]     // Catch: all -> 0x0064
            int r2 = r2.intValue()     // Catch: all -> 0x0064
            int r2 = r2 - r9
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: all -> 0x0064
            r0[r1] = r2     // Catch: all -> 0x0064
        L_0x0033:
            if (r1 != 0) goto L_0x003d
        L_0x0035:
            boolean r0 = r10.zzjzp     // Catch: all -> 0x0064
            if (r0 != 0) goto L_0x005d
            int r0 = r10.zzjzr     // Catch: all -> 0x0064
            if (r0 != r9) goto L_0x005d
        L_0x003d:
            com.google.android.gms.common.stats.zze.zzalt()     // Catch: all -> 0x0064
            android.content.Context r1 = r10.mContext     // Catch: all -> 0x0064
            android.os.PowerManager$WakeLock r0 = r10.zzjzl     // Catch: all -> 0x0064
            java.lang.String r2 = com.google.android.gms.common.stats.zzc.zza(r0, r5)     // Catch: all -> 0x0064
            r3 = 8
            java.lang.String r4 = r10.zzgat     // Catch: all -> 0x0064
            r6 = 0
            int r7 = r10.zzjzn     // Catch: all -> 0x0064
            android.os.WorkSource r0 = r10.zzjzm     // Catch: all -> 0x0064
            java.util.List r8 = com.google.android.gms.common.util.zzy.zzb(r0)     // Catch: all -> 0x0064
            com.google.android.gms.common.stats.zze.zza(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch: all -> 0x0064
            int r0 = r10.zzjzr     // Catch: all -> 0x0064
            int r0 = r0 - r9
            r10.zzjzr = r0     // Catch: all -> 0x0064
        L_0x005d:
            monitor-exit(r10)     // Catch: all -> 0x0064
            android.os.PowerManager$WakeLock r0 = r10.zzjzl
            r0.release()
            return
        L_0x0064:
            r0 = move-exception
            monitor-exit(r10)     // Catch: all -> 0x0064
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcwr.release():void");
    }

    public final void setReferenceCounted(boolean z) {
        this.zzjzl.setReferenceCounted(false);
        this.zzjzp = false;
    }
}
