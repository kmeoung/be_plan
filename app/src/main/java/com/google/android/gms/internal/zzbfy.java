package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class zzbfy implements ThreadFactory {
    private final int mPriority;
    private final String zzgch;
    private final AtomicInteger zzgci;
    private final ThreadFactory zzgcj;

    public zzbfy(String str) {
        this(str, 0);
    }

    private zzbfy(String str, int i) {
        this.zzgci = new AtomicInteger();
        this.zzgcj = Executors.defaultThreadFactory();
        this.zzgch = (String) zzbq.checkNotNull(str, "Name must not be null");
        this.mPriority = 0;
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread newThread = this.zzgcj.newThread(new zzbfz(runnable, 0));
        String str = this.zzgch;
        int andIncrement = this.zzgci.getAndIncrement();
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 13);
        sb.append(str);
        sb.append("[");
        sb.append(andIncrement);
        sb.append("]");
        newThread.setName(sb.toString());
        return newThread;
    }
}
