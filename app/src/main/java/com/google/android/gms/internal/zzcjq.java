package com.google.android.gms.internal;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzcjq implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ boolean zzjek;
    private /* synthetic */ zzcjd zzjfo;
    private /* synthetic */ AtomicReference zzjfp;

    public zzcjq(zzcjd zzcjdVar, AtomicReference atomicReference, zzcff zzcffVar, boolean z) {
        this.zzjfo = zzcjdVar;
        this.zzjfp = atomicReference;
        this.zzjds = zzcffVar;
        this.zzjek = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AtomicReference atomicReference;
        zzcgb zzcgbVar;
        synchronized (this.zzjfp) {
            try {
                zzcgbVar = this.zzjfo.zzjfi;
            } catch (RemoteException e) {
                this.zzjfo.zzawm().zzayr().zzj("Failed to get user properties", e);
                atomicReference = this.zzjfp;
            }
            if (zzcgbVar == null) {
                this.zzjfo.zzawm().zzayr().log("Failed to get user properties");
                this.zzjfp.notify();
                return;
            }
            this.zzjfp.set(zzcgbVar.zza(this.zzjds, this.zzjek));
            this.zzjfo.zzxg();
            atomicReference = this.zzjfp;
            atomicReference.notify();
        }
    }
}
