package com.google.android.gms.internal;

import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class zzchl implements Callable<String> {
    private /* synthetic */ String zzijk;
    private /* synthetic */ zzchj zzjdm;

    public zzchl(zzchj zzchjVar, String str) {
        this.zzjdm = zzchjVar;
        this.zzijk = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() throws Exception {
        zzcfe zziv = this.zzjdm.zzawg().zziv(this.zzijk);
        if (zziv != null) {
            return zziv.getAppInstanceId();
        }
        this.zzjdm.zzawm().zzayt().log("App info was null when attempting to get app instance id");
        return null;
    }
}
