package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzciw implements Runnable {
    private /* synthetic */ zzcik zzjeh;
    private /* synthetic */ AtomicReference zzjej;

    public zzciw(zzcik zzcikVar, AtomicReference atomicReference) {
        this.zzjeh = zzcikVar;
        this.zzjej = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjeh.zzawd().zza(this.zzjej);
    }
}
