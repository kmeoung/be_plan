package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;
import java.net.URL;
import java.util.Map;

@WorkerThread
/* loaded from: classes.dex */
public final class zzcgr implements Runnable {
    private final String mPackageName;
    private final URL zzbwa;
    private final byte[] zzgdd;
    private final zzcgp zzizp;
    private final Map<String, String> zzizq;
    private /* synthetic */ zzcgn zzizr;

    public zzcgr(zzcgn zzcgnVar, String str, URL url, byte[] bArr, Map<String, String> map, zzcgp zzcgpVar) {
        this.zzizr = zzcgnVar;
        zzbq.zzgh(str);
        zzbq.checkNotNull(url);
        zzbq.checkNotNull(zzcgpVar);
        this.zzbwa = url;
        this.zzgdd = bArr;
        this.zzizp = zzcgpVar;
        this.mPackageName = str;
        this.zzizq = map;
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00ee A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x012b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            Method dump skipped, instructions count: 350
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcgr.run():void");
    }
}
