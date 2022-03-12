package com.google.android.gms.internal;

import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzcib implements Callable<byte[]> {
    private /* synthetic */ String zzijk;
    private /* synthetic */ zzcho zzjdt;
    private /* synthetic */ zzcfx zzjdx;

    public zzcib(zzcho zzchoVar, zzcfx zzcfxVar, String str) {
        this.zzjdt = zzchoVar;
        this.zzjdx = zzcfxVar;
        this.zzijk = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ byte[] call() throws Exception {
        zzchj zzchjVar;
        zzchj zzchjVar2;
        zzchjVar = this.zzjdt.zzitk;
        zzchjVar.zzazz();
        zzchjVar2 = this.zzjdt.zzitk;
        return zzchjVar2.zza(this.zzjdx, this.zzijk);
    }
}
