package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.internal.zzbq;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class zzn<TResult> extends Task<TResult> {
    private final Object mLock = new Object();
    private final zzl<TResult> zzkru = new zzl<>();
    private boolean zzkrv;
    private TResult zzkrw;
    private Exception zzkrx;

    /* loaded from: classes.dex */
    static class zza extends LifecycleCallback {
        private final List<WeakReference<zzk<?>>> zzewl = new ArrayList();

        private zza(zzci zzciVar) {
            super(zzciVar);
            this.zzfrj.zza("TaskOnStopCallback", this);
        }

        public static zza zzr(Activity activity) {
            zzci zzn = zzn(activity);
            zza zzaVar = (zza) zzn.zza("TaskOnStopCallback", zza.class);
            return zzaVar == null ? new zza(zzn) : zzaVar;
        }

        @Override // com.google.android.gms.common.api.internal.LifecycleCallback
        @MainThread
        public final void onStop() {
            synchronized (this.zzewl) {
                for (WeakReference<zzk<?>> weakReference : this.zzewl) {
                    zzk<?> zzkVar = weakReference.get();
                    if (zzkVar != null) {
                        zzkVar.cancel();
                    }
                }
                this.zzewl.clear();
            }
        }

        public final <T> void zzb(zzk<T> zzkVar) {
            synchronized (this.zzewl) {
                this.zzewl.add(new WeakReference<>(zzkVar));
            }
        }
    }

    private final void zzbiy() {
        zzbq.zza(this.zzkrv, "Task is not yet complete");
    }

    private final void zzbiz() {
        zzbq.zza(!this.zzkrv, "Task is already complete");
    }

    private final void zzbja() {
        synchronized (this.mLock) {
            if (this.zzkrv) {
                this.zzkru.zzb(this);
            }
        }
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zze zzeVar = new zze(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.zzkru.zza(zzeVar);
        zza.zzr(activity).zzb(zzeVar);
        zzbja();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, onCompleteListener);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzkru.zza(new zze(executor, onCompleteListener));
        zzbja();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzg zzgVar = new zzg(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.zzkru.zza(zzgVar);
        zza.zzr(activity).zzb(zzgVar);
        zzbja();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzkru.zza(new zzg(executor, onFailureListener));
        zzbja();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzi zziVar = new zzi(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.zzkru.zza(zziVar);
        zza.zzr(activity).zzb(zziVar);
        zzbja();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, onSuccessListener);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzkru.zza(new zzi(executor, onSuccessListener));
        zzbja();
        return this;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        zzn zznVar = new zzn();
        this.zzkru.zza(new zza(executor, continuation, zznVar));
        zzbja();
        return zznVar;
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @Override // com.google.android.gms.tasks.Task
    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        zzn zznVar = new zzn();
        this.zzkru.zza(new zzc(executor, continuation, zznVar));
        zzbja();
        return zznVar;
    }

    @Override // com.google.android.gms.tasks.Task
    @Nullable
    public final Exception getException() {
        Exception exc;
        synchronized (this.mLock) {
            exc = this.zzkrx;
        }
        return exc;
    }

    @Override // com.google.android.gms.tasks.Task
    public final TResult getResult() {
        TResult tresult;
        synchronized (this.mLock) {
            zzbiy();
            if (this.zzkrx != null) {
                throw new RuntimeExecutionException(this.zzkrx);
            }
            tresult = this.zzkrw;
        }
        return tresult;
    }

    @Override // com.google.android.gms.tasks.Task
    public final <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        TResult tresult;
        synchronized (this.mLock) {
            zzbiy();
            if (cls.isInstance(this.zzkrx)) {
                throw cls.cast(this.zzkrx);
            } else if (this.zzkrx != null) {
                throw new RuntimeExecutionException(this.zzkrx);
            } else {
                tresult = this.zzkrw;
            }
        }
        return tresult;
    }

    @Override // com.google.android.gms.tasks.Task
    public final boolean isComplete() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzkrv;
        }
        return z;
    }

    @Override // com.google.android.gms.tasks.Task
    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzkrv && this.zzkrx == null;
        }
        return z;
    }

    public final void setException(@NonNull Exception exc) {
        zzbq.checkNotNull(exc, "Exception must not be null");
        synchronized (this.mLock) {
            zzbiz();
            this.zzkrv = true;
            this.zzkrx = exc;
        }
        this.zzkru.zzb(this);
    }

    public final void setResult(TResult tresult) {
        synchronized (this.mLock) {
            zzbiz();
            this.zzkrv = true;
            this.zzkrw = tresult;
        }
        this.zzkru.zzb(this);
    }

    public final boolean trySetException(@NonNull Exception exc) {
        zzbq.checkNotNull(exc, "Exception must not be null");
        synchronized (this.mLock) {
            if (this.zzkrv) {
                return false;
            }
            this.zzkrv = true;
            this.zzkrx = exc;
            this.zzkru.zzb(this);
            return true;
        }
    }

    public final boolean trySetResult(TResult tresult) {
        synchronized (this.mLock) {
            if (this.zzkrv) {
                return false;
            }
            this.zzkrv = true;
            this.zzkrw = tresult;
            this.zzkru.zzb(this);
            return true;
        }
    }
}
