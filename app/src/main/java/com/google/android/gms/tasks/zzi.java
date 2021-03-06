package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
final class zzi<TResult> implements zzk<TResult> {
    private final Object mLock = new Object();
    private final Executor zzkcc;
    private OnSuccessListener<? super TResult> zzkro;

    public zzi(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzkcc = executor;
        this.zzkro = onSuccessListener;
    }

    @Override // com.google.android.gms.tasks.zzk
    public final void cancel() {
        synchronized (this.mLock) {
            this.zzkro = null;
        }
    }

    @Override // com.google.android.gms.tasks.zzk
    public final void onComplete(@NonNull Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzkro != null) {
                    this.zzkcc.execute(new zzj(this, task));
                }
            }
        }
    }
}
