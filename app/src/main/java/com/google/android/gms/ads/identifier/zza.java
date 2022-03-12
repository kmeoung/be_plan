package com.google.android.gms.ads.identifier;

import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zza extends Thread {
    private /* synthetic */ Map zzamc;

    public zza(AdvertisingIdClient advertisingIdClient, Map map) {
        this.zzamc = map;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        new zze().zzc(this.zzamc);
    }
}
