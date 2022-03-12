package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;
import java.util.List;
import java.util.Map;

@WorkerThread
/* loaded from: classes.dex */
final class zzcgq implements Runnable {
    private final String mPackageName;
    private final int zzbzn;
    private final Throwable zzdcz;
    private final zzcgp zzizm;
    private final byte[] zzizn;
    private final Map<String, List<String>> zzizo;

    private zzcgq(String str, zzcgp zzcgpVar, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        zzbq.checkNotNull(zzcgpVar);
        this.zzizm = zzcgpVar;
        this.zzbzn = i;
        this.zzdcz = th;
        this.zzizn = bArr;
        this.mPackageName = str;
        this.zzizo = map;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzizm.zza(this.mPackageName, this.zzbzn, this.zzdcz, this.zzizn, this.zzizo);
    }
}
