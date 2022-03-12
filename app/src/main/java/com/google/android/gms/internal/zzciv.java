package com.google.android.gms.internal;

import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class zzciv implements Callable<String> {
    private /* synthetic */ zzcik zzjeh;

    public zzciv(zzcik zzcikVar) {
        this.zzjeh = zzcikVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0070  */
    @Override // java.util.concurrent.Callable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.String call() throws java.lang.Exception {
        /*
            r10 = this;
            com.google.android.gms.internal.zzcik r0 = r10.zzjeh
            com.google.android.gms.internal.zzcgu r0 = r0.zzawn()
            java.lang.String r0 = r0.zzazb()
            if (r0 == 0) goto L_0x000d
            return r0
        L_0x000d:
            com.google.android.gms.internal.zzcik r0 = r10.zzjeh
            com.google.android.gms.internal.zzcik r0 = r0.zzawa()
            com.google.android.gms.internal.zzche r1 = r0.zzawl()
            boolean r1 = r1.zzazg()
            r2 = 0
            if (r1 == 0) goto L_0x002c
            com.google.android.gms.internal.zzcgj r0 = r0.zzawm()
            com.google.android.gms.internal.zzcgl r0 = r0.zzayr()
            java.lang.String r1 = "Cannot retrieve app instance id from analytics worker thread"
        L_0x0028:
            r0.log(r1)
            goto L_0x0068
        L_0x002c:
            r0.zzawl()
            boolean r1 = com.google.android.gms.internal.zzche.zzas()
            if (r1 == 0) goto L_0x0040
            com.google.android.gms.internal.zzcgj r0 = r0.zzawm()
            com.google.android.gms.internal.zzcgl r0 = r0.zzayr()
            java.lang.String r1 = "Cannot retrieve app instance id from main thread"
            goto L_0x0028
        L_0x0040:
            com.google.android.gms.common.util.zzd r1 = r0.zzwh()
            long r1 = r1.elapsedRealtime()
            r3 = 120000(0x1d4c0, double:5.9288E-319)
            java.lang.String r5 = r0.zzbc(r3)
            com.google.android.gms.common.util.zzd r6 = r0.zzwh()
            long r6 = r6.elapsedRealtime()
            long r8 = r6 - r1
            if (r5 != 0) goto L_0x0067
            int r1 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r1 >= 0) goto L_0x0067
            long r1 = r3 - r8
            java.lang.String r0 = r0.zzbc(r1)
            r2 = r0
            goto L_0x0068
        L_0x0067:
            r2 = r5
        L_0x0068:
            if (r2 != 0) goto L_0x0070
            java.util.concurrent.TimeoutException r0 = new java.util.concurrent.TimeoutException
            r0.<init>()
            throw r0
        L_0x0070:
            com.google.android.gms.internal.zzcik r0 = r10.zzjeh
            com.google.android.gms.internal.zzcgu r0 = r0.zzawn()
            r0.zzjj(r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzciv.call():java.lang.Object");
    }
}
