package io.fabric.sdk.android.services.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public abstract class AsyncTask<Params, Progress, Result> {
    private static final int KEEP_ALIVE = 1;
    private static final String LOG_TAG = "AsyncTask";
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = (CPU_COUNT * 2) + 1;
    private static final ThreadFactory threadFactory = new ThreadFactory() { // from class: io.fabric.sdk.android.services.concurrency.AsyncTask.1
        private final AtomicInteger count = new AtomicInteger(1);

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AsyncTask #" + this.count.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> poolWorkQueue = new LinkedBlockingQueue(128);
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 1, TimeUnit.SECONDS, poolWorkQueue, threadFactory);
    public static final Executor SERIAL_EXECUTOR = new SerialExecutor();
    private static final InternalHandler handler = new InternalHandler();
    private static volatile Executor defaultExecutor = SERIAL_EXECUTOR;
    private volatile Status status = Status.PENDING;
    private final AtomicBoolean cancelled = new AtomicBoolean();
    private final AtomicBoolean taskInvoked = new AtomicBoolean();
    private final WorkerRunnable<Params, Result> worker = new WorkerRunnable<Params, Result>() { // from class: io.fabric.sdk.android.services.concurrency.AsyncTask.2
        @Override // java.util.concurrent.Callable
        public Result call() throws Exception {
            AsyncTask.this.taskInvoked.set(true);
            Process.setThreadPriority(10);
            return (Result) AsyncTask.this.postResult(AsyncTask.this.doInBackground(this.params));
        }
    };
    private final FutureTask<Result> future = new FutureTask<Result>(this.worker) { // from class: io.fabric.sdk.android.services.concurrency.AsyncTask.3
        @Override // java.util.concurrent.FutureTask
        protected void done() {
            try {
                AsyncTask.this.postResultIfNotInvoked(get());
            } catch (InterruptedException e) {
                Log.w(AsyncTask.LOG_TAG, e);
            } catch (CancellationException unused) {
                AsyncTask.this.postResultIfNotInvoked(null);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
            }
        }
    };

    /* loaded from: classes.dex */
    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    protected abstract Result doInBackground(Params... paramsArr);

    protected void onCancelled() {
    }

    protected void onPostExecute(Result result) {
    }

    public void onPreExecute() {
    }

    protected void onProgressUpdate(Progress... progressArr) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SerialExecutor implements Executor {
        Runnable active;
        final LinkedList<Runnable> tasks;

        private SerialExecutor() {
            this.tasks = new LinkedList<>();
        }

        @Override // java.util.concurrent.Executor
        public synchronized void execute(final Runnable runnable) {
            this.tasks.offer(new Runnable() { // from class: io.fabric.sdk.android.services.concurrency.AsyncTask.SerialExecutor.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        SerialExecutor.this.scheduleNext();
                    }
                }
            });
            if (this.active == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            Runnable poll = this.tasks.poll();
            this.active = poll;
            if (poll != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(this.active);
            }
        }
    }

    public static void init() {
        handler.getLooper();
    }

    public static void setDefaultExecutor(Executor executor) {
        defaultExecutor = executor;
    }

    public void postResultIfNotInvoked(Result result) {
        if (!this.taskInvoked.get()) {
            postResult(result);
        }
    }

    public Result postResult(Result result) {
        handler.obtainMessage(1, new AsyncTaskResult(this, result)).sendToTarget();
        return result;
    }

    public final Status getStatus() {
        return this.status;
    }

    protected void onCancelled(Result result) {
        onCancelled();
    }

    public final boolean isCancelled() {
        return this.cancelled.get();
    }

    public final boolean cancel(boolean z) {
        this.cancelled.set(true);
        return this.future.cancel(z);
    }

    public final Result get() throws InterruptedException, ExecutionException {
        return this.future.get();
    }

    public final Result get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.future.get(j, timeUnit);
    }

    public final AsyncTask<Params, Progress, Result> execute(Params... paramsArr) {
        return executeOnExecutor(defaultExecutor, paramsArr);
    }

    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params... paramsArr) {
        if (this.status != Status.PENDING) {
            switch (this.status) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.status = Status.RUNNING;
        onPreExecute();
        this.worker.params = paramsArr;
        executor.execute(this.future);
        return this;
    }

    public static void execute(Runnable runnable) {
        defaultExecutor.execute(runnable);
    }

    protected final void publishProgress(Progress... progressArr) {
        if (!isCancelled()) {
            handler.obtainMessage(2, new AsyncTaskResult(this, progressArr)).sendToTarget();
        }
    }

    public void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
        this.status = Status.FINISHED;
    }

    /* loaded from: classes.dex */
    public static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult) message.obj;
            switch (message.what) {
                case 1:
                    asyncTaskResult.task.finish(asyncTaskResult.data[0]);
                    return;
                case 2:
                    asyncTaskResult.task.onProgressUpdate(asyncTaskResult.data);
                    return;
                default:
                    return;
            }
        }
    }

    /* loaded from: classes.dex */
    public static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] params;

        private WorkerRunnable() {
        }
    }

    /* loaded from: classes.dex */
    public static class AsyncTaskResult<Data> {
        final Data[] data;
        final AsyncTask task;

        AsyncTaskResult(AsyncTask asyncTask, Data... dataArr) {
            this.task = asyncTask;
            this.data = dataArr;
        }
    }
}
