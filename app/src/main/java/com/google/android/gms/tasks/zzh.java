package com.google.android.gms.tasks;

/* loaded from: classes.dex */
final class zzh implements Runnable {
    private /* synthetic */ Task zzkrh;
    private /* synthetic */ zzg zzkrn;

    public zzh(zzg zzgVar, Task task) {
        this.zzkrn = zzgVar;
        this.zzkrh = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object obj;
        OnFailureListener onFailureListener;
        OnFailureListener onFailureListener2;
        obj = this.zzkrn.mLock;
        synchronized (obj) {
            onFailureListener = this.zzkrn.zzkrm;
            if (onFailureListener != null) {
                onFailureListener2 = this.zzkrn.zzkrm;
                onFailureListener2.onFailure(this.zzkrh.getException());
            }
        }
    }
}
