package io.realm.internal.async;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class RealmThreadPoolExecutor extends ThreadPoolExecutor {
    private static final int CORE_POOL_SIZE = calculateCorePoolSize();
    private static final int QUEUE_SIZE = 100;
    private static final String SYS_CPU_DIR = "/sys/devices/system/cpu/";
    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = this.pauseLock.newCondition();

    public static RealmThreadPoolExecutor newDefaultExecutor() {
        return new RealmThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE);
    }

    public static RealmThreadPoolExecutor newSingleThreadExecutor() {
        return new RealmThreadPoolExecutor(1, 1);
    }

    @SuppressFBWarnings({"DMI_HARDCODED_ABSOLUTE_FILENAME"})
    private static int calculateCorePoolSize() {
        int countFilesInDir = countFilesInDir(SYS_CPU_DIR, "cpu[0-9]+");
        if (countFilesInDir <= 0) {
            countFilesInDir = Runtime.getRuntime().availableProcessors();
        }
        if (countFilesInDir <= 0) {
            return 1;
        }
        return 1 + (countFilesInDir * 2);
    }

    private static int countFilesInDir(String str, String str2) {
        final Pattern compile = Pattern.compile(str2);
        try {
            File[] listFiles = new File(str).listFiles(new FileFilter() { // from class: io.realm.internal.async.RealmThreadPoolExecutor.1
                @Override // java.io.FileFilter
                public boolean accept(File file) {
                    return compile.matcher(file.getName()).matches();
                }
            });
            if (listFiles == null) {
                return 0;
            }
            return listFiles.length;
        } catch (SecurityException unused) {
            return 0;
        }
    }

    private RealmThreadPoolExecutor(int i, int i2) {
        super(i, i2, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(100));
    }

    public Future<?> submitTransaction(Runnable runnable) {
        return super.submit(new BgPriorityRunnable(runnable));
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    protected void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        this.pauseLock.lock();
        while (this.isPaused) {
            try {
                try {
                    this.unpaused.await();
                } catch (InterruptedException unused) {
                    thread.interrupt();
                }
            } finally {
                this.pauseLock.unlock();
            }
        }
    }

    public void pause() {
        this.pauseLock.lock();
        try {
            this.isPaused = true;
        } finally {
            this.pauseLock.unlock();
        }
    }

    public void resume() {
        this.pauseLock.lock();
        try {
            this.isPaused = false;
            this.unpaused.signalAll();
        } finally {
            this.pauseLock.unlock();
        }
    }
}
