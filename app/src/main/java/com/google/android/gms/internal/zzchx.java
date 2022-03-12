package com.google.android.gms.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzchx implements Callable<List<zzcfi>> {
    private /* synthetic */ String zzijk;
    private /* synthetic */ zzcho zzjdt;
    private /* synthetic */ String zzjdv;
    private /* synthetic */ String zzjdw;

    public zzchx(zzcho zzchoVar, String str, String str2, String str3) {
        this.zzjdt = zzchoVar;
        this.zzijk = str;
        this.zzjdv = str2;
        this.zzjdw = str3;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzcfi> call() throws Exception {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzazz();
        zzchjVar2 = this.zzjdt.zzitk;
        return zzchjVar2.zzawg().zzh(this.zzijk, this.zzjdv, this.zzjdw);
    }
}
