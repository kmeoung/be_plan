package com.google.android.gms.internal;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzcjg implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzcjd zzjfo;
    private /* synthetic */ AtomicReference zzjfp;

    public zzcjg(zzcjd zzcjdVar, AtomicReference atomicReference, zzcff zzcffVar) {
        this.zzjfo = zzcjdVar;
        this.zzjfp = atomicReference;
        this.zzjds = zzcffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AtomicReference atomicReference;
        zzcgb zzcgbVar;
        synchronized (this.zzjfp) {
            try {
                zzcgbVar = this.zzjfo.zzjfi;
            } catch (RemoteException e) {
                this.zzjfo.zzawm().zzayr().zzj("Failed to get app instance id", e);
                atomicReference = this.zzjfp;
            }
            if (zzcgbVar == null) {
                this.zzjfo.zzawm().zzayr().log("Failed to get app instance id");
                this.zzjfp.notify();
                return;
            }
            this.zzjfp.set(zzcgbVar.zzc(this.zzjds));
            String str = (String) this.zzjfp.get();
            if (str != null) {
                this.zzjfo.zzawa().zzjj(str);
                this.zzjfo.zzawn().zzjac.zzjk(str);
            }
            this.zzjfo.zzxg();
            atomicReference = this.zzjfp;
            atomicReference.notify();
        }
    }
}
