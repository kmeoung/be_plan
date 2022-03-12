package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzcip implements Runnable {
    private /* synthetic */ String zzijk;
    private /* synthetic */ String zzjdv;
    private /* synthetic */ String zzjdw;
    private /* synthetic */ zzcik zzjeh;
    private /* synthetic */ AtomicReference zzjej;
    private /* synthetic */ boolean zzjek;

    public zzcip(zzcik zzcikVar, AtomicReference atomicReference, String str, String str2, String str3, boolean z) {
        this.zzjeh = zzcikVar;
        this.zzjej = atomicReference;
        this.zzijk = str;
        this.zzjdv = str2;
        this.zzjdw = str3;
        this.zzjek = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjeh.zzitk.zzawd().zza(this.zzjej, this.zzijk, this.zzjdv, this.zzjdw, this.zzjek);
    }
}
