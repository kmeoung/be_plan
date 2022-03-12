package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
final class zzg<TResult> implements zzk<TResult> {
    private final Object mLock = new Object();
    private final Executor zzkcc;
    private OnFailureListener zzkrm;

    public zzg(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzkcc = executor;
        this.zzkrm = onFailureListener;
    }

    @Override // com.google.android.gms.tasks.zzk
    public final void cancel() {
        synchronized (this.mLock) {
            this.zzkrm = null;
        }
    }

    @Override // com.google.android.gms.tasks.zzk
    public final void onComplete(@NonNull Task<TResult> task) {
        if (!task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzkrm != null) {
                    this.zzkcc.execute(new zzh(this, task));
                }
            }
        }
    }
}
