package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class TaskCompletionSource<TResult> {
    private final zzn<TResult> zzkrs = new zzn<>();

    @NonNull
    public Task<TResult> getTask() {
        return this.zzkrs;
    }

    public void setException(@NonNull Exception exc) {
        this.zzkrs.setException(exc);
    }

    public void setResult(TResult tresult) {
        this.zzkrs.setResult(tresult);
    }

    public boolean trySetException(@NonNull Exception exc) {
        return this.zzkrs.trySetException(exc);
    }

    public boolean trySetResult(TResult tresult) {
        return this.zzkrs.trySetResult(tresult);
    }
}
