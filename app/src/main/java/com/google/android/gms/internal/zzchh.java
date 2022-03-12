package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public final class zzchh<V> extends FutureTask<V> implements Comparable<zzchh> {
    private final String zzjbu;
    private /* synthetic */ zzche zzjbv;
    private final long zzjbw;
    final boolean zzjbx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzchh(zzche zzcheVar, Runnable runnable, boolean z, String str) {
        super(runnable, null);
        AtomicLong atomicLong;
        this.zzjbv = zzcheVar;
        zzbq.checkNotNull(str);
        atomicLong = zzche.zzjbt;
        this.zzjbw = atomicLong.getAndIncrement();
        this.zzjbu = str;
        this.zzjbx = false;
        if (this.zzjbw == Long.MAX_VALUE) {
            zzcheVar.zzawm().zzayr().log("Tasks index overflow");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzchh(zzche zzcheVar, Callable<V> callable, boolean z, String str) {
        super(callable);
        AtomicLong atomicLong;
        this.zzjbv = zzcheVar;
        zzbq.checkNotNull(str);
        atomicLong = zzche.zzjbt;
        this.zzjbw = atomicLong.getAndIncrement();
        this.zzjbu = str;
        this.zzjbx = z;
        if (this.zzjbw == Long.MAX_VALUE) {
            zzcheVar.zzawm().zzayr().log("Tasks index overflow");
        }
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(@NonNull zzchh zzchhVar) {
        zzchh zzchhVar2 = zzchhVar;
        if (this.zzjbx != zzchhVar2.zzjbx) {
            return this.zzjbx ? -1 : 1;
        }
        if (this.zzjbw < zzchhVar2.zzjbw) {
            return -1;
        }
        if (this.zzjbw > zzchhVar2.zzjbw) {
            return 1;
        }
        this.zzjbv.zzawm().zzays().zzj("Two tasks share the same index. index", Long.valueOf(this.zzjbw));
        return 0;
    }

    @Override // java.util.concurrent.FutureTask
    protected final void setException(Throwable th) {
        this.zzjbv.zzawm().zzayr().zzj(this.zzjbu, th);
        if (th instanceof zzchf) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
        }
        super.setException(th);
    }
}
