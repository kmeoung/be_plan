package io.realm.internal.async;

import android.os.Process;

/* loaded from: classes.dex */
public class BgPriorityRunnable implements Runnable {
    private final Runnable runnable;

    public BgPriorityRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        Process.setThreadPriority(10);
        this.runnable.run();
    }
}
