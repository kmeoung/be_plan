package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
final class zzc<TResult, TContinuationResult> implements OnFailureListener, OnSuccessListener<TContinuationResult>, zzk<TResult> {
    private final Executor zzkcc;
    private final Continuation<TResult, Task<TContinuationResult>> zzkrf;
    private final zzn<TContinuationResult> zzkrg;

    public zzc(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation, @NonNull zzn<TContinuationResult> zznVar) {
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
        this.zzkcc.execute(new zzd(this, task));
    }

    @Override // com.google.android.gms.tasks.OnFailureListener
    public final void onFailure(@NonNull Exception exc) {
        this.zzkrg.setException(exc);
    }

    @Override // com.google.android.gms.tasks.OnSuccessListener
    public final void onSuccess(TContinuationResult tcontinuationresult) {
        this.zzkrg.setResult(tcontinuationresult);
    }
}
