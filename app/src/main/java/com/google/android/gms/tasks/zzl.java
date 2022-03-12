package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.ArrayDeque;
import java.util.Queue;

/* loaded from: classes.dex */
public final class zzl<TResult> {
    private final Object mLock = new Object();
    private Queue<zzk<TResult>> zzkrq;
    private boolean zzkrr;

    public final void zza(@NonNull zzk<TResult> zzkVar) {
        synchronized (this.mLock) {
            if (this.zzkrq == null) {
                this.zzkrq = new ArrayDeque();
            }
            this.zzkrq.add(zzkVar);
        }
    }

    public final void zzb(@NonNull Task<TResult> task) {
        zzk<TResult> poll;
        synchronized (this.mLock) {
            if (this.zzkrq != null && !this.zzkrr) {
                this.zzkrr = true;
                while (true) {
                    synchronized (this.mLock) {
                        poll = this.zzkrq.poll();
                        if (poll == null) {
                            this.zzkrr = false;
                            return;
                        }
                    }
                    poll.onComplete(task);
                }
            }
        }
    }
}
