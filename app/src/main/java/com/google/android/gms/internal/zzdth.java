package com.google.android.gms.internal;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
final class zzdth {
    private final ConcurrentHashMap<zzdti, List<Throwable>> zzlwb = new ConcurrentHashMap<>(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzlwc = new ReferenceQueue<>();

    public final List<Throwable> zza(Throwable th, boolean z) {
        while (true) {
            Reference<? extends Throwable> poll = this.zzlwc.poll();
            if (poll != null) {
                this.zzlwb.remove(poll);
            } else {
                return this.zzlwb.get(new zzdti(th, null));
            }
        }
    }
}
