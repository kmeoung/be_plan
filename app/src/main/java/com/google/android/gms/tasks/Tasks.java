package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public final class Tasks {

    /* loaded from: classes.dex */
    public static final class zza implements zzb {
        private final CountDownLatch zzaoi;

        private zza() {
            this.zzaoi = new CountDownLatch(1);
        }

        /* synthetic */ zza(zzo zzoVar) {
            this();
        }

        public final void await() throws InterruptedException {
            this.zzaoi.await();
        }

        public final boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
            return this.zzaoi.await(j, timeUnit);
        }

        @Override // com.google.android.gms.tasks.OnFailureListener
        public final void onFailure(@NonNull Exception exc) {
            this.zzaoi.countDown();
        }

        @Override // com.google.android.gms.tasks.OnSuccessListener
        public final void onSuccess(Object obj) {
            this.zzaoi.countDown();
        }
    }

    /* loaded from: classes.dex */
    public interface zzb extends OnFailureListener, OnSuccessListener<Object> {
    }

    /* loaded from: classes.dex */
    public static final class zzc implements zzb {
        private final Object mLock = new Object();
        private final zzn<Void> zzkrs;
        private Exception zzkrx;
        private final int zzkrz;
        private int zzksa;
        private int zzksb;

        public zzc(int i, zzn<Void> zznVar) {
            this.zzkrz = i;
            this.zzkrs = zznVar;
        }

        private final void zzbjb() {
            if (this.zzksa + this.zzksb != this.zzkrz) {
                return;
            }
            if (this.zzkrx == null) {
                this.zzkrs.setResult(null);
                return;
            }
            zzn<Void> zznVar = this.zzkrs;
            int i = this.zzksb;
            int i2 = this.zzkrz;
            StringBuilder sb = new StringBuilder(54);
            sb.append(i);
            sb.append(" out of ");
            sb.append(i2);
            sb.append(" underlying tasks failed");
            zznVar.setException(new ExecutionException(sb.toString(), this.zzkrx));
        }

        @Override // com.google.android.gms.tasks.OnFailureListener
        public final void onFailure(@NonNull Exception exc) {
            synchronized (this.mLock) {
                this.zzksb++;
                this.zzkrx = exc;
                zzbjb();
            }
        }

        @Override // com.google.android.gms.tasks.OnSuccessListener
        public final void onSuccess(Object obj) {
            synchronized (this.mLock) {
                this.zzksa++;
                zzbjb();
            }
        }
    }

    private Tasks() {
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task) throws ExecutionException, InterruptedException {
        zzbq.zzgi("Must not be called on the main application thread");
        zzbq.checkNotNull(task, "Task must not be null");
        if (task.isComplete()) {
            return (TResult) zzc(task);
        }
        zza zzaVar = new zza(null);
        zza(task, zzaVar);
        zzaVar.await();
        return (TResult) zzc(task);
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task, long j, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        zzbq.zzgi("Must not be called on the main application thread");
        zzbq.checkNotNull(task, "Task must not be null");
        zzbq.checkNotNull(timeUnit, "TimeUnit must not be null");
        if (task.isComplete()) {
            return (TResult) zzc(task);
        }
        zza zzaVar = new zza(null);
        zza(task, zzaVar);
        if (zzaVar.await(j, timeUnit)) {
            return (TResult) zzc(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }

    public static <TResult> Task<TResult> call(@NonNull Callable<TResult> callable) {
        return call(TaskExecutors.MAIN_THREAD, callable);
    }

    public static <TResult> Task<TResult> call(@NonNull Executor executor, @NonNull Callable<TResult> callable) {
        zzbq.checkNotNull(executor, "Executor must not be null");
        zzbq.checkNotNull(callable, "Callback must not be null");
        zzn zznVar = new zzn();
        executor.execute(new zzo(zznVar, callable));
        return zznVar;
    }

    public static <TResult> Task<TResult> forException(@NonNull Exception exc) {
        zzn zznVar = new zzn();
        zznVar.setException(exc);
        return zznVar;
    }

    public static <TResult> Task<TResult> forResult(TResult tresult) {
        zzn zznVar = new zzn();
        zznVar.setResult(tresult);
        return zznVar;
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.isEmpty()) {
            return forResult(null);
        }
        Iterator<? extends Task<?>> it = collection.iterator();
        while (it.hasNext()) {
            if (((Task) it.next()) == null) {
                throw new NullPointerException("null tasks are not accepted");
            }
        }
        zzn zznVar = new zzn();
        zzc zzcVar = new zzc(collection.size(), zznVar);
        Iterator<? extends Task<?>> it2 = collection.iterator();
        while (it2.hasNext()) {
            zza((Task) it2.next(), zzcVar);
        }
        return zznVar;
    }

    public static Task<Void> whenAll(Task<?>... taskArr) {
        return taskArr.length == 0 ? forResult(null) : whenAll(Arrays.asList(taskArr));
    }

    private static void zza(Task<?> task, zzb zzbVar) {
        task.addOnSuccessListener(TaskExecutors.zzkrt, zzbVar);
        task.addOnFailureListener(TaskExecutors.zzkrt, zzbVar);
    }

    private static <TResult> TResult zzc(Task<TResult> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }
}
