package com.google.android.gms.tasks;

import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class zzo implements Runnable {
    private /* synthetic */ Callable val$callable;
    private /* synthetic */ zzn zzkry;

    public zzo(zzn zznVar, Callable callable) {
        this.zzkry = zznVar;
        this.val$callable = callable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.zzkry.setResult(this.val$callable.call());
        } catch (Exception e) {
            this.zzkry.setException(e);
        }
    }
}
