package com.google.android.gms.internal;

import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import java.util.concurrent.BlockingQueue;

/* loaded from: classes.dex */
public final class zzl extends Thread {
    private final zzb zzi;
    private final zzw zzj;
    private volatile boolean zzk = false;
    private final BlockingQueue<zzp<?>> zzw;
    private final zzk zzx;

    public zzl(BlockingQueue<zzp<?>> blockingQueue, zzk zzkVar, zzb zzbVar, zzw zzwVar) {
        this.zzw = blockingQueue;
        this.zzx = zzkVar;
        this.zzi = zzbVar;
        this.zzj = zzwVar;
    }

    public final void quit() {
        this.zzk = true;
        interrupt();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Process.setThreadPriority(10);
        while (true) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            try {
                zzp<?> take = this.zzw.take();
                try {
                    take.zzb("network-queue-take");
                    TrafficStats.setThreadStatsTag(take.zzc());
                    zzn zza = this.zzx.zza(take);
                    take.zzb("network-http-complete");
                    if (!zza.zzz || !take.zzl()) {
                        zzt<?> zza2 = take.zza(zza);
                        take.zzb("network-parse-complete");
                        if (take.zzh() && zza2.zzbe != null) {
                            this.zzi.zza(take.getUrl(), zza2.zzbe);
                            take.zzb("network-cache-written");
                        }
                        take.zzk();
                        this.zzj.zza(take, zza2);
                    } else {
                        take.zzc("not-modified");
                    }
                } catch (zzaa e) {
                    e.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
                    this.zzj.zza(take, e);
                } catch (Exception e2) {
                    zzab.zza(e2, "Unhandled exception %s", e2.toString());
                    zzaa zzaaVar = new zzaa(e2);
                    zzaaVar.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
                    this.zzj.zza(take, zzaaVar);
                }
            } catch (InterruptedException unused) {
                if (this.zzk) {
                    return;
                }
            }
        }
    }
}
