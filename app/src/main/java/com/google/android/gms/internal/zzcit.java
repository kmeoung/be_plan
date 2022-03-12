package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcit implements Runnable {
    private /* synthetic */ String val$name;
    private /* synthetic */ String zzjdv;
    private /* synthetic */ zzcik zzjeh;
    private /* synthetic */ long zzjem;
    private /* synthetic */ Object zzjer;

    public zzcit(zzcik zzcikVar, String str, String str2, Object obj, long j) {
        this.zzjeh = zzcikVar;
        this.zzjdv = str;
        this.val$name = str2;
        this.zzjer = obj;
        this.zzjem = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjeh.zza(this.zzjdv, this.val$name, this.zzjer, this.zzjem);
    }
}
