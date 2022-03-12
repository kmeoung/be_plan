package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
final class zze<TResult> implements zzk<TResult> {
    private final Object mLock = new Object();
    private final Executor zzkcc;
    private OnCompleteListener<TResult> zzkrk;

    public zze(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzkcc = executor;
        this.zzkrk = onCompleteListener;
    }

    @Override // com.google.android.gms.tasks.zzk
    public final void cancel() {
        synchronized (this.mLock) {
            this.zzkrk = null;
        }
    }

    @Override // com.google.android.gms.tasks.zzk
    public final void onComplete(@NonNull Task<TResult> task) {
        synchronized (this.mLock) {
            if (this.zzkrk != null) {
                this.zzkcc.execute(new zzf(this, task));
            }
        }
    }
}
