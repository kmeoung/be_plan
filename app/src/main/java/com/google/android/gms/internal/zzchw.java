package com.google.android.gms.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzchw implements Callable<List<zzcfi>> {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcho zzjdt;
    private /* synthetic */ String zzjdv;
    private /* synthetic */ String zzjdw;

    public zzchw(zzcho zzchoVar, zzcff zzcffVar, String str, String str2) {
        this.zzjdt = zzchoVar;
        this.zzjds = zzcffVar;
        this.zzjdv = str;
        this.zzjdw = str2;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzcfi> call() throws Exception {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzazz();
        zzchjVar2 = this.zzjdt.zzitk;
        return zzchjVar2.zzawg().zzh(this.zzjds.packageName, this.zzjdv, this.zzjdw);
    }
}
