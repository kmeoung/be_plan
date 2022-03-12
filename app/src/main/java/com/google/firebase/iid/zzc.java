package com.google.firebase.iid;

import android.content.Intent;

/* loaded from: classes.dex */
final class zzc implements Runnable {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ Intent zzibw;
    private /* synthetic */ zzb zzntm;

    public zzc(zzb zzbVar, Intent intent, Intent intent2) {
        this.zzntm = zzbVar;
        this.val$intent = intent;
        this.zzibw = intent2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzntm.handleIntent(this.val$intent);
        this.zzntm.zzh(this.zzibw);
    }
}
