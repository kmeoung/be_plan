package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
final class zza<TResult, TContinuationResult> implements zzk<TResult> {
    private final Executor zzkcc;
    private final Continuation<TResult, TContinuationResult> zzkrf;
    private final zzn<TContinuationResult> zzkrg;

    public zza(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzn<TContinuationResult> zznVar) {
        this.zzkcc = executor;
        this.zzkrf = continuation;
        this.zzkrg = zznVar;
    }

    @Override // com.google.android.gms.tasks.zzk
    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.tasks.zzk
    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzkcc.execute(new zzb(this, task));
    }
}
