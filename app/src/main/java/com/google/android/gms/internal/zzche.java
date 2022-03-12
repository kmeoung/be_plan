package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import java.lang.Thread;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public final class zzche extends zzcii {
    private static final AtomicLong zzjbt = new AtomicLong(Long.MIN_VALUE);
    private ExecutorService zzibs;
    private zzchi zzjbk;
    private zzchi zzjbl;
    private volatile boolean zzjbs;
    private final Object zzjbq = new Object();
    private final Semaphore zzjbr = new Semaphore(2);
    private final PriorityBlockingQueue<zzchh<?>> zzjbm = new PriorityBlockingQueue<>();
    private final BlockingQueue<zzchh<?>> zzjbn = new LinkedBlockingQueue();
    private final Thread.UncaughtExceptionHandler zzjbo = new zzchg(this, "Thread death: Uncaught exception on worker thread");
    private final Thread.UncaughtExceptionHandler zzjbp = new zzchg(this, "Thread death: Uncaught exception on network thread");

    public zzche(zzchj zzchjVar) {
        super(zzchjVar);
    }

    private final void zza(zzchh<?> zzchhVar) {
        synchronized (this.zzjbq) {
            this.zzjbm.add(zzchhVar);
            if (this.zzjbk == null) {
                this.zzjbk = new zzchi(this, "Measurement Worker", this.zzjbm);
                this.zzjbk.setUncaughtExceptionHandler(this.zzjbo);
                this.zzjbk.start();
            } else {
                this.zzjbk.zzrb();
            }
        }
    }

    public static boolean zzas() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzavw() {
        super.zzavw();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final void zzavx() {
        if (Thread.currentThread() != this.zzjbl) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfa zzavy() {
        return super.zzavy();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfh zzavz() {
        return super.zzavz();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcik zzawa() {
        return super.zzawa();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcge zzawb() {
        return super.zzawb();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfr zzawc() {
        return super.zzawc();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcjd zzawd() {
        return super.zzawd();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzciz zzawe() {
        return super.zzawe();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgf zzawf() {
        return super.zzawf();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfl zzawg() {
        return super.zzawg();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgh zzawh() {
        return super.zzawh();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzckn zzawi() {
        return super.zzawi();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzchd zzawj() {
        return super.zzawj();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzckc zzawk() {
        return super.zzawk();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzche zzawl() {
        return super.zzawl();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgj zzawm() {
        return super.zzawm();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgu zzawn() {
        return super.zzawn();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfk zzawo() {
        return super.zzawo();
    }

    @Override // com.google.android.gms.internal.zzcii
    protected final boolean zzaxn() {
        return false;
    }

    public final boolean zzazg() {
        return Thread.currentThread() == this.zzjbk;
    }

    public final ExecutorService zzazh() {
        ExecutorService executorService;
        synchronized (this.zzjbq) {
            if (this.zzibs == null) {
                this.zzibs = new ThreadPoolExecutor(0, 1, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue(100));
            }
            executorService = this.zzibs;
        }
        return executorService;
    }

    public final <V> Future<V> zzc(Callable<V> callable) throws IllegalStateException {
        zzwu();
        zzbq.checkNotNull(callable);
        zzchh<?> zzchhVar = new zzchh<>(this, (Callable<?>) callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzjbk) {
            if (!this.zzjbm.isEmpty()) {
                zzawm().zzayt().log("Callable skipped the worker queue.");
            }
            zzchhVar.run();
            return zzchhVar;
        }
        zza(zzchhVar);
        return zzchhVar;
    }

    public final <V> Future<V> zzd(Callable<V> callable) throws IllegalStateException {
        zzwu();
        zzbq.checkNotNull(callable);
        zzchh<?> zzchhVar = new zzchh<>(this, (Callable<?>) callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzjbk) {
            zzchhVar.run();
            return zzchhVar;
        }
        zza(zzchhVar);
        return zzchhVar;
    }

    public final void zzg(Runnable runnable) throws IllegalStateException {
        zzwu();
        zzbq.checkNotNull(runnable);
        zza(new zzchh<>(this, runnable, false, "Task exception on worker thread"));
    }

    public final void zzh(Runnable runnable) throws IllegalStateException {
        zzwu();
        zzbq.checkNotNull(runnable);
        zzchh<?> zzchhVar = new zzchh<>(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzjbq) {
            this.zzjbn.add(zzchhVar);
            if (this.zzjbl == null) {
                this.zzjbl = new zzchi(this, "Measurement Network", this.zzjbn);
                this.zzjbl.setUncaughtExceptionHandler(this.zzjbp);
                this.zzjbl.start();
            } else {
                this.zzjbl.zzrb();
            }
        }
    }

    @Override // com.google.android.gms.internal.zzcih
    public final void zzut() {
        if (Thread.currentThread() != this.zzjbk) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzd zzwh() {
        return super.zzwh();
    }
}
