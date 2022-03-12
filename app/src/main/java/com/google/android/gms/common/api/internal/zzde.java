package com.google.android.gms.common.api.internal;

import android.os.Bundle;

/* loaded from: classes.dex */
final class zzde implements Runnable {
    private /* synthetic */ String zzao;
    private /* synthetic */ LifecycleCallback zzfrn;
    private /* synthetic */ zzdd zzfsc;

    public zzde(zzdd zzddVar, LifecycleCallback lifecycleCallback, String str) {
        this.zzfsc = zzddVar;
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
        i = this.zzfsc.zzbzn;
        if (i > 0) {
            LifecycleCallback lifecycleCallback = this.zzfrn;
            bundle = this.zzfsc.zzfrm;
            if (bundle != null) {
                bundle3 = this.zzfsc.zzfrm;
                bundle2 = bundle3.getBundle(this.zzao);
            } else {
                bundle2 = null;
            }
            lifecycleCallback.onCreate(bundle2);
        }
        i2 = this.zzfsc.zzbzn;
        if (i2 >= 2) {
            this.zzfrn.onStart();
        }
        i3 = this.zzfsc.zzbzn;
        if (i3 >= 3) {
            this.zzfrn.onResume();
        }
        i4 = this.zzfsc.zzbzn;
        if (i4 >= 4) {
            this.zzfrn.onStop();
        }
        i5 = this.zzfsc.zzbzn;
        if (i5 >= 5) {
            this.zzfrn.onDestroy();
        }
    }
}
