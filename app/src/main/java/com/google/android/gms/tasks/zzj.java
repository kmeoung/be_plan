package com.google.android.gms.tasks;

/* loaded from: classes.dex */
final class zzj implements Runnable {
    private /* synthetic */ Task zzkrh;
    private /* synthetic */ zzi zzkrp;

    public zzj(zzi zziVar, Task task) {
        this.zzkrp = zziVar;
        this.zzkrh = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object obj;
        OnSuccessListener onSuccessListener;
        OnSuccessListener onSuccessListener2;
        obj = this.zzkrp.mLock;
        synchronized (obj) {
            onSuccessListener = this.zzkrp.zzkro;
            if (onSuccessListener != null) {
                onSuccessListener2 = this.zzkrp.zzkro;
                onSuccessListener2.onSuccess(this.zzkrh.getResult());
            }
        }
    }
}
