package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzciu implements Runnable {
    private /* synthetic */ zzcik zzjeh;
    private /* synthetic */ AtomicReference zzjej;
    private /* synthetic */ boolean zzjek;

    public zzciu(zzcik zzcikVar, AtomicReference atomicReference, boolean z) {
        this.zzjeh = zzcikVar;
        this.zzjej = atomicReference;
        this.zzjek = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjeh.zzawd().zza(this.zzjej, this.zzjek);
    }
}
