package com.google.android.gms.internal;

import android.os.Process;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/* loaded from: classes.dex */
public final class zzchi extends Thread {
    private /* synthetic */ zzche zzjbv;
    private final Object zzjby = new Object();
    private final BlockingQueue<zzchh<?>> zzjbz;

    public zzchi(zzche zzcheVar, String str, BlockingQueue<zzchh<?>> blockingQueue) {
        this.zzjbv = zzcheVar;
        zzbq.checkNotNull(str);
        zzbq.checkNotNull(blockingQueue);
        this.zzjbz = blockingQueue;
        setName(str);
    }

    private final void zza(InterruptedException interruptedException) {
        this.zzjbv.zzawm().zzayt().zzj(String.valueOf(getName()).concat(" was interrupted"), interruptedException);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Object obj;
        Semaphore semaphore;
        Object obj2;
        zzchi zzchiVar;
        zzchi zzchiVar2;
        Object obj3;
        Object obj4;
        Semaphore semaphore2;
        Object obj5;
        zzchi zzchiVar3;
        zzchi zzchiVar4;
        boolean z;
        Semaphore semaphore3;
        boolean z2 = false;
        while (!z2) {
            try {
                semaphore3 = this.zzjbv.zzjbr;
                semaphore3.acquire();
                z2 = true;
            } catch (InterruptedException e) {
                zza(e);
            }
        }
        try {
            int i = Process.getThreadPriority(Process.myTid());
            while (true) {
                zzchh<?> poll = this.zzjbz.poll();
                if (poll == null) {
                    synchronized (this.zzjby) {
                        if (this.zzjbz.peek() == null) {
                            z = this.zzjbv.zzjbs;
                            if (!z) {
                                try {
                                    this.zzjby.wait(30000L);
                                } catch (InterruptedException e2) {
                                    zza(e2);
                                }
                            }
                        }
                    }
                    obj3 = this.zzjbv.zzjbq;
                    synchronized (obj3) {
                        if (this.zzjbz.peek() == null) {
                            break;
                        }
                    }
                } else {
                    if (!poll.zzjbx) {
                        i = 10;
                    }
                    Process.setThreadPriority(i);
                    poll.run();
                }
            }
            obj4 = this.zzjbv.zzjbq;
            synchronized (obj4) {
                semaphore2 = this.zzjbv.zzjbr;
                semaphore2.release();
                obj5 = this.zzjbv.zzjbq;
                obj5.notifyAll();
                zzchiVar3 = this.zzjbv.zzjbk;
                if (this == zzchiVar3) {
                    this.zzjbv.zzjbk = null;
                } else {
                    zzchiVar4 = this.zzjbv.zzjbl;
                    if (this == zzchiVar4) {
                        this.zzjbv.zzjbl = null;
                    } else {
                        this.zzjbv.zzawm().zzayr().log("Current scheduler thread is neither worker nor network");
                    }
                }
            }
        } catch (Throwable th) {
            obj = this.zzjbv.zzjbq;
            synchronized (obj) {
                semaphore = this.zzjbv.zzjbr;
                semaphore.release();
                obj2 = this.zzjbv.zzjbq;
                obj2.notifyAll();
                zzchiVar = this.zzjbv.zzjbk;
                if (this == zzchiVar) {
                    this.zzjbv.zzjbk = null;
                } else {
                    zzchiVar2 = this.zzjbv.zzjbl;
                    if (this == zzchiVar2) {
                        this.zzjbv.zzjbl = null;
                    } else {
                        this.zzjbv.zzawm().zzayr().log("Current scheduler thread is neither worker nor network");
                    }
                }
                throw th;
            }
        }
    }

    public final void zzrb() {
        synchronized (this.zzjby) {
            this.zzjby.notifyAll();
        }
    }
}
