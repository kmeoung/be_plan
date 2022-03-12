package com.google.android.gms.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzcie implements Callable<List<zzckm>> {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcho zzjdt;

    public zzcie(zzcho zzchoVar, zzcff zzcffVar) {
        this.zzjdt = zzchoVar;
        this.zzjds = zzcffVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzckm> call() throws Exception {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzazz();
        zzchjVar2 = this.zzjdt.zzitk;
        return zzchjVar2.zzawg().zziu(this.zzjds.packageName);
    }
}
