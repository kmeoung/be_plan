package com.google.android.gms.common.api.internal;

import android.os.Bundle;

/* loaded from: classes.dex */
final class zzck implements Runnable {
    private /* synthetic */ String zzao;
    private /* synthetic */ LifecycleCallback zzfrn;
    private /* synthetic */ zzcj zzfro;

    public zzck(zzcj zzcjVar, LifecycleCallback lifecycleCallback, String str) {
        this.zzfro = zzcjVar;
        this.zzfrn = lifecycleCallback;
        this.zzao = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Bundle bundle;
        Bundle bundle2;
        Bundle bundle3;
        i = this.zzfro.zzbzn;
        if (i > 0) {
            LifecycleCallback lifecycleCallback = this.zzfrn;
            bundle = this.zzfro.zzfrm;
            if (bundle != null) {
                bundle3 = this.zzfro.zzfrm;
                bundle2 = bundle3.getBundle(this.zzao);
            } else {
                bundle2 = null;
            }
            lifecycleCallback.onCreate(bundle2);
        }
        i2 = this.zzfro.zzbzn;
        if (i2 >= 2) {
            this.zzfrn.onStart();
        }
        i3 = this.zzfro.zzbzn;
        if (i3 >= 3) {
            this.zzfrn.onResume();
        }
        i4 = this.zzfro.zzbzn;
        if (i4 >= 4) {
            this.zzfrn.onStop();
        }
        i5 = this.zzfro.zzbzn;
        if (i5 >= 5) {
            this.zzfrn.onDestroy();
        }
    }
}
