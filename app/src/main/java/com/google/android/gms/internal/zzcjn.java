package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzcjn implements Runnable {
    private /* synthetic */ String zzijk;
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ String zzjdv;
    private /* synthetic */ String zzjdw;
    private /* synthetic */ zzcjd zzjfo;
    private /* synthetic */ AtomicReference zzjfp;

    public zzcjn(zzcjd zzcjdVar, AtomicReference atomicReference, String str, String str2, String str3, zzcff zzcffVar) {
        this.zzjfo = zzcjdVar;
        this.zzjfp = atomicReference;
        this.zzijk = str;
        this.zzjdv = str2;
        this.zzjdw = str3;
        this.zzjds = zzcffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AtomicReference atomicReference;
        zzcgb zzcgbVar;
        AtomicReference atomicReference2;
        List<zzcfi> zzj;
        synchronized (this.zzjfp) {
            try {
                zzcgbVar = this.zzjfo.zzjfi;
            } catch (RemoteException e) {
                this.zzjfo.zzawm().zzayr().zzd("Failed to get conditional properties", zzcgj.zzje(this.zzijk), this.zzjdv, e);
                this.zzjfp.set(Collections.emptyList());
                atomicReference = this.zzjfp;
            }
            if (zzcgbVar == null) {
                this.zzjfo.zzawm().zzayr().zzd("Failed to get conditional properties", zzcgj.zzje(this.zzijk), this.zzjdv, this.zzjdw);
                this.zzjfp.set(Collections.emptyList());
                this.zzjfp.notify();
                return;
            }
            if (TextUtils.isEmpty(this.zzijk)) {
                atomicReference2 = this.zzjfp;
                zzj = zzcgbVar.zza(this.zzjdv, this.zzjdw, this.zzjds);
            } else {
                atomicReference2 = this.zzjfp;
                zzj = zzcgbVar.zzj(this.zzijk, this.zzjdv, this.zzjdw);
            }
            atomicReference2.set(zzj);
            this.zzjfo.zzxg();
            atomicReference = this.zzjfp;
            atomicReference.notify();
        }
    }
}
