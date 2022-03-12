package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class zzs {
    private final AtomicInteger zzaw;
    private final Map<String, Queue<zzp<?>>> zzax;
    private final Set<zzp<?>> zzay;
    private final PriorityBlockingQueue<zzp<?>> zzaz;
    private final PriorityBlockingQueue<zzp<?>> zzba;
    private final zzl[] zzbb;
    private zzd zzbc;
    private final List<Object> zzbd;
    private final zzb zzi;
    private final zzw zzj;
    private final zzk zzx;

    public zzs(zzb zzbVar, zzk zzkVar) {
        this(zzbVar, zzkVar, 4);
    }

    private zzs(zzb zzbVar, zzk zzkVar, int i) {
        this(zzbVar, zzkVar, 4, new zzh(new Handler(Looper.getMainLooper())));
    }

    private zzs(zzb zzbVar, zzk zzkVar, int i, zzw zzwVar) {
        this.zzaw = new AtomicInteger();
        this.zzax = new HashMap();
        this.zzay = new HashSet();
        this.zzaz = new PriorityBlockingQueue<>();
        this.zzba = new PriorityBlockingQueue<>();
        this.zzbd = new ArrayList();
        this.zzi = zzbVar;
        this.zzx = zzkVar;
        this.zzbb = new zzl[4];
        this.zzj = zzwVar;
    }

    public final void start() {
        zzl[] zzlVarArr;
        if (this.zzbc != null) {
            this.zzbc.quit();
        }
        for (zzl zzlVar : this.zzbb) {
            if (zzlVar != null) {
                zzlVar.quit();
            }
        }
        this.zzbc = new zzd(this.zzaz, this.zzba, this.zzi, this.zzj);
        this.zzbc.start();
        for (int i = 0; i < this.zzbb.length; i++) {
            zzl zzlVar2 = new zzl(this.zzba, this.zzx, this.zzi, this.zzj);
            this.zzbb[i] = zzlVar2;
            zzlVar2.start();
        }
    }

    public final <T> zzp<T> zzc(zzp<T> zzpVar) {
        zzpVar.zza(this);
        synchronized (this.zzay) {
            this.zzay.add(zzpVar);
        }
        zzpVar.zza(this.zzaw.incrementAndGet());
        zzpVar.zzb("add-to-queue");
        if (!zzpVar.zzh()) {
            this.zzba.add(zzpVar);
            return zzpVar;
        }
        synchronized (this.zzax) {
            String zzd = zzpVar.zzd();
            if (this.zzax.containsKey(zzd)) {
                Queue<zzp<?>> queue = this.zzax.get(zzd);
                if (queue == null) {
                    queue = new LinkedList<>();
                }
                queue.add(zzpVar);
                this.zzax.put(zzd, queue);
                if (zzab.DEBUG) {
                    zzab.zza("Request for cacheKey=%s is in flight, putting on hold.", zzd);
                }
            } else {
                this.zzax.put(zzd, null);
                this.zzaz.add(zzpVar);
            }
        }
        return zzpVar;
    }

    public final <T> void zzd(zzp<T> zzpVar) {
        synchronized (this.zzay) {
            this.zzay.remove(zzpVar);
        }
        synchronized (this.zzbd) {
            Iterator<Object> it = this.zzbd.iterator();
            while (it.hasNext()) {
                it.next();
            }
        }
        if (zzpVar.zzh()) {
            synchronized (this.zzax) {
                String zzd = zzpVar.zzd();
                Queue<zzp<?>> remove = this.zzax.remove(zzd);
                if (remove != null) {
                    if (zzab.DEBUG) {
                        zzab.zza("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf(remove.size()), zzd);
                    }
                    this.zzaz.addAll(remove);
                }
            }
        }
    }
}
