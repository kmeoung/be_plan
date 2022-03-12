package com.google.android.gms.tasks;

/* loaded from: classes.dex */
final class zzf implements Runnable {
    private /* synthetic */ Task zzkrh;
    private /* synthetic */ zze zzkrl;

    public zzf(zze zzeVar, Task task) {
        this.zzkrl = zzeVar;
        this.zzkrh = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object obj;
        OnCompleteListener onCompleteListener;
        OnCompleteListener onCompleteListener2;
        obj = this.zzkrl.mLock;
        synchronized (obj) {
            onCompleteListener = this.zzkrl.zzkrk;
            if (onCompleteListener != null) {
                onCompleteListener2 = this.zzkrl.zzkrk;
                onCompleteListener2.onComplete(this.zzkrh);
            }
        }
    }
}
