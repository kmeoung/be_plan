package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;

/* loaded from: classes.dex */
public final class zzi {
    private static Boolean zzgbm;
    private static Boolean zzgbn;
    private static Boolean zzgbo;
    private static Boolean zzgbp;
    private static Boolean zzgbq;

    /* JADX WARN: Code restructure failed: missing block: B:22:0x003c, code lost:
        if (com.google.android.gms.common.util.zzi.zzgbn.booleanValue() != false) goto L_0x003e;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean zza(android.content.res.Resources r4) {
        /*
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.Boolean r1 = com.google.android.gms.common.util.zzi.zzgbm
            if (r1 != 0) goto L_0x0045
            android.content.res.Configuration r1 = r4.getConfiguration()
            int r1 = r1.screenLayout
            r1 = r1 & 15
            r2 = 3
            r3 = 1
            if (r1 <= r2) goto L_0x0016
            r1 = 1
            goto L_0x0017
        L_0x0016:
            r1 = 0
        L_0x0017:
            if (r1 != 0) goto L_0x003e
            java.lang.Boolean r1 = com.google.android.gms.common.util.zzi.zzgbn
            if (r1 != 0) goto L_0x0036
            android.content.res.Configuration r4 = r4.getConfiguration()
            int r1 = r4.screenLayout
            r1 = r1 & 15
            if (r1 > r2) goto L_0x002f
            int r4 = r4.smallestScreenWidthDp
            r1 = 600(0x258, float:8.41E-43)
            if (r4 < r1) goto L_0x002f
            r4 = 1
            goto L_0x0030
        L_0x002f:
            r4 = 0
        L_0x0030:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            com.google.android.gms.common.util.zzi.zzgbn = r4
        L_0x0036:
            java.lang.Boolean r4 = com.google.android.gms.common.util.zzi.zzgbn
            boolean r4 = r4.booleanValue()
            if (r4 == 0) goto L_0x003f
        L_0x003e:
            r0 = 1
        L_0x003f:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r0)
            com.google.android.gms.common.util.zzi.zzgbm = r4
        L_0x0045:
            java.lang.Boolean r4 = com.google.android.gms.common.util.zzi.zzgbm
            boolean r4 = r4.booleanValue()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.zzi.zza(android.content.res.Resources):boolean");
    }

    @TargetApi(20)
    public static boolean zzcp(Context context) {
        if (zzgbo == null) {
            zzgbo = Boolean.valueOf(zzq.zzama() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch"));
        }
        return zzgbo.booleanValue();
    }

    @TargetApi(24)
    public static boolean zzcq(Context context) {
        return (!zzq.isAtLeastN() || zzcr(context)) && zzcp(context);
    }

    @TargetApi(21)
    public static boolean zzcr(Context context) {
        if (zzgbp == null) {
            zzgbp = Boolean.valueOf(zzq.zzamb() && context.getPackageManager().hasSystemFeature("cn.google"));
        }
        return zzgbp.booleanValue();
    }

    public static boolean zzcs(Context context) {
        if (zzgbq == null) {
            zzgbq = Boolean.valueOf(context.getPackageManager().hasSystemFeature("android.hardware.type.iot") || context.getPackageManager().hasSystemFeature("android.hardware.type.embedded"));
        }
        return zzgbq.booleanValue();
    }
}
