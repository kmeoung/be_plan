package com.google.android.gms.internal;

import android.content.ComponentName;

/* loaded from: classes.dex */
final class zzcjt implements Runnable {
    private /* synthetic */ ComponentName val$name;
    private /* synthetic */ zzcjr zzjfy;

    public zzcjt(zzcjr zzcjrVar, ComponentName componentName) {
        this.zzjfy = zzcjrVar;
        this.val$name = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjfy.zzjfo.onServiceDisconnected(this.val$name);
    }
}
