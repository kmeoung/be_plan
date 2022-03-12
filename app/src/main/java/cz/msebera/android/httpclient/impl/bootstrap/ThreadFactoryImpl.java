package cz.msebera.android.httpclient.impl.bootstrap;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
class ThreadFactoryImpl implements ThreadFactory {
    private final AtomicLong count;
    private final ThreadGroup group;
    private final String namePrefix;

    public ThreadFactoryImpl(String str, ThreadGroup threadGroup) {
        this.namePrefix = str;
        this.group = threadGroup;
        this.count = new AtomicLong();
    }

    public ThreadFactoryImpl(String str) {
        this(str, null);
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        ThreadGroup threadGroup = this.group;
        return new Thread(threadGroup, runnable, this.namePrefix + "-" + this.count.incrementAndGet());
    }
}
