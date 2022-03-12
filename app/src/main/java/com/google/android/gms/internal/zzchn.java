package com.google.android.gms.internal;

import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzchn implements zzcgp {
    private /* synthetic */ zzchj zzjdm;

    public zzchn(zzchj zzchjVar) {
        this.zzjdm = zzchjVar;
    }

    @Override // com.google.android.gms.internal.zzcgp
    public final void zza(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        this.zzjdm.zzb(str, i, th, bArr, map);
    }
}
