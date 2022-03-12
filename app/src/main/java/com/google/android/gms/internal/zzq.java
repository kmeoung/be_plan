package com.google.android.gms.internal;

import com.google.android.gms.internal.zzab;

/* loaded from: classes.dex */
public final class zzq implements Runnable {
    private /* synthetic */ String zzao;
    private /* synthetic */ long zzap;
    private /* synthetic */ zzp zzaq;

    public zzq(zzp zzpVar, String str, long j) {
        this.zzaq = zzpVar;
        this.zzao = str;
        this.zzap = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzab.zza zzaVar;
        zzab.zza zzaVar2;
        zzaVar = this.zzaq.zzab;
        zzaVar.zza(this.zzao, this.zzap);
        zzaVar2 = this.zzaq.zzab;
        zzaVar2.zzc(toString());
    }
}
