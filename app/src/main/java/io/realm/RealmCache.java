package io.realm;

import io.realm.BaseRealm;
import io.realm.exceptions.RealmFileException;
import io.realm.internal.ColumnIndices;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.RealmNotifier;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.Util;
import io.realm.internal.android.AndroidCapabilities;
import io.realm.internal.android.AndroidRealmNotifier;
import io.realm.internal.async.RealmAsyncTaskImpl;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class RealmCache {
    private static final String ASYNC_CALLBACK_NULL_MSG = "The callback cannot be null.";
    private static final String ASYNC_NOT_ALLOWED_MSG = "Realm instances cannot be loaded asynchronously on a non-looper thread.";
    private static final String DIFFERENT_KEY_MESSAGE = "Wrong key used to decrypt Realm.";
    private static final int MAX_ENTRIES_IN_TYPED_COLUMN_INDICES_ARRAY = 4;
    private static final String WRONG_REALM_CLASS_MESSAGE = "The type of Realm class must be Realm or DynamicRealm.";
    private static final List<WeakReference<RealmCache>> cachesList = new LinkedList();
    private static final Collection<RealmCache> leakedCaches = new ConcurrentLinkedQueue();
    private RealmConfiguration configuration;
    private final String realmPath;
    private final ColumnIndices[] typedColumnIndicesArray = new ColumnIndices[4];
    private final AtomicBoolean isLeaked = new AtomicBoolean(false);
    private final EnumMap<RealmCacheType, RefAndCount> refAndCountMap = new EnumMap<>(RealmCacheType.class);

    /* loaded from: classes.dex */
    public interface Callback {
        void onResult(int i);
    }

    /* loaded from: classes.dex */
    public interface Callback0 {
        void onCall();
    }

    /* loaded from: classes.dex */
    public static class RefAndCount {
        private int globalCount;
        private final ThreadLocal<Integer> localCount;
        private final ThreadLocal<BaseRealm> localRealm;

        private RefAndCount() {
            this.localRealm = new ThreadLocal<>();
            this.localCount = new ThreadLocal<>();
            this.globalCount = 0;
        }

        static /* synthetic */ int access$808(RefAndCount refAndCount) {
            int i = refAndCount.globalCount;
            refAndCount.globalCount = i + 1;
            return i;
        }

        static /* synthetic */ int access$810(RefAndCount refAndCount) {
            int i = refAndCount.globalCount;
            refAndCount.globalCount = i - 1;
            return i;
        }
    }

    /* loaded from: classes.dex */
    public enum RealmCacheType {
        TYPED_REALM,
        DYNAMIC_REALM;

        static RealmCacheType valueOf(Class<? extends BaseRealm> cls) {
            if (cls == Realm.class) {
                return TYPED_REALM;
            }
            if (cls == DynamicRealm.class) {
                return DYNAMIC_REALM;
            }
            throw new IllegalArgumentException(RealmCache.WRONG_REALM_CLASS_MESSAGE);
        }
    }

    /* loaded from: classes.dex */
    public static class CreateRealmRunnable<T extends BaseRealm> implements Runnable {
        private final BaseRealm.InstanceCallback<T> callback;
        private final CountDownLatch canReleaseBackgroundInstanceLatch = new CountDownLatch(1);
        private final RealmConfiguration configuration;
        private Future future;
        private final RealmNotifier notifier;
        private final Class<T> realmClass;

        CreateRealmRunnable(RealmNotifier realmNotifier, RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
            this.configuration = realmConfiguration;
            this.realmClass = cls;
            this.callback = instanceCallback;
            this.notifier = realmNotifier;
        }

        public void setFuture(Future future) {
            this.future = future;
        }

        /* JADX WARN: Removed duplicated region for block: B:30:0x006f  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r7 = this;
                r0 = 0
                r1 = 0
                io.realm.RealmConfiguration r2 = r7.configuration     // Catch: all -> 0x0036, Throwable -> 0x0039, InterruptedException -> 0x005b
                java.lang.Class<T extends io.realm.BaseRealm> r3 = r7.realmClass     // Catch: all -> 0x0036, Throwable -> 0x0039, InterruptedException -> 0x005b
                io.realm.BaseRealm r2 = io.realm.RealmCache.createRealmOrGetFromCache(r2, r3)     // Catch: all -> 0x0036, Throwable -> 0x0039, InterruptedException -> 0x005b
                io.realm.internal.RealmNotifier r1 = r7.notifier     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
                io.realm.RealmCache$CreateRealmRunnable$1 r3 = new io.realm.RealmCache$CreateRealmRunnable$1     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
                r3.<init>()     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
                boolean r1 = r1.post(r3)     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
                if (r1 != 0) goto L_0x001c
                java.util.concurrent.CountDownLatch r1 = r7.canReleaseBackgroundInstanceLatch     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
                r1.countDown()     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
            L_0x001c:
                java.util.concurrent.CountDownLatch r1 = r7.canReleaseBackgroundInstanceLatch     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
                r3 = 2
                java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.SECONDS     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
                boolean r1 = r1.await(r3, r5)     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
                if (r1 != 0) goto L_0x002f
                java.lang.String r1 = "Timeout for creating Realm instance in foreground thread in `CreateRealmRunnable` "
                java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
                io.realm.log.RealmLog.warn(r1, r3)     // Catch: Throwable -> 0x0032, InterruptedException -> 0x0034, all -> 0x006c
            L_0x002f:
                if (r2 == 0) goto L_0x006b
                goto L_0x0068
            L_0x0032:
                r1 = move-exception
                goto L_0x003d
            L_0x0034:
                r1 = move-exception
                goto L_0x005f
            L_0x0036:
                r0 = move-exception
                r2 = r1
                goto L_0x006d
            L_0x0039:
                r2 = move-exception
                r6 = r2
                r2 = r1
                r1 = r6
            L_0x003d:
                io.realm.internal.ObjectServerFacade r3 = io.realm.internal.ObjectServerFacade.getSyncFacadeIfPossible()     // Catch: all -> 0x006c
                boolean r3 = r3.wasDownloadInterrupted(r1)     // Catch: all -> 0x006c
                if (r3 != 0) goto L_0x0058
                java.lang.String r3 = "`CreateRealmRunnable` failed."
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch: all -> 0x006c
                io.realm.log.RealmLog.error(r1, r3, r0)     // Catch: all -> 0x006c
                io.realm.internal.RealmNotifier r0 = r7.notifier     // Catch: all -> 0x006c
                io.realm.RealmCache$CreateRealmRunnable$2 r3 = new io.realm.RealmCache$CreateRealmRunnable$2     // Catch: all -> 0x006c
                r3.<init>()     // Catch: all -> 0x006c
                r0.post(r3)     // Catch: all -> 0x006c
            L_0x0058:
                if (r2 == 0) goto L_0x006b
                goto L_0x0068
            L_0x005b:
                r2 = move-exception
                r6 = r2
                r2 = r1
                r1 = r6
            L_0x005f:
                java.lang.String r3 = "`CreateRealmRunnable` has been interrupted."
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch: all -> 0x006c
                io.realm.log.RealmLog.warn(r1, r3, r0)     // Catch: all -> 0x006c
                if (r2 == 0) goto L_0x006b
            L_0x0068:
                r2.close()
            L_0x006b:
                return
            L_0x006c:
                r0 = move-exception
            L_0x006d:
                if (r2 == 0) goto L_0x0072
                r2.close()
            L_0x0072:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.RealmCache.CreateRealmRunnable.run():void");
        }
    }

    private RealmCache(String str) {
        this.realmPath = str;
        for (RealmCacheType realmCacheType : RealmCacheType.values()) {
            this.refAndCountMap.put((EnumMap<RealmCacheType, RefAndCount>) realmCacheType, (RealmCacheType) new RefAndCount());
        }
    }

    private static RealmCache getCache(String str, boolean z) {
        RealmCache realmCache;
        synchronized (cachesList) {
            Iterator<WeakReference<RealmCache>> it = cachesList.iterator();
            realmCache = null;
            while (it.hasNext()) {
                RealmCache realmCache2 = it.next().get();
                if (realmCache2 == null) {
                    it.remove();
                } else if (realmCache2.realmPath.equals(str)) {
                    realmCache = realmCache2;
                }
            }
            if (realmCache == null && z) {
                realmCache = new RealmCache(str);
                cachesList.add(new WeakReference<>(realmCache));
            }
        }
        return realmCache;
    }

    public static <T extends BaseRealm> RealmAsyncTask createRealmOrGetFromCacheAsync(RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
        return getCache(realmConfiguration.getPath(), true).doCreateRealmOrGetFromCacheAsync(realmConfiguration, instanceCallback, cls);
    }

    private synchronized <T extends BaseRealm> RealmAsyncTask doCreateRealmOrGetFromCacheAsync(RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
        Future<?> submitTransaction;
        AndroidCapabilities androidCapabilities = new AndroidCapabilities();
        androidCapabilities.checkCanDeliverNotification(ASYNC_NOT_ALLOWED_MSG);
        if (instanceCallback == null) {
            throw new IllegalArgumentException(ASYNC_CALLBACK_NULL_MSG);
        }
        CreateRealmRunnable createRealmRunnable = new CreateRealmRunnable(new AndroidRealmNotifier(null, androidCapabilities), realmConfiguration, instanceCallback, cls);
        submitTransaction = BaseRealm.asyncTaskExecutor.submitTransaction(createRealmRunnable);
        createRealmRunnable.setFuture(submitTransaction);
        return new RealmAsyncTaskImpl(submitTransaction, BaseRealm.asyncTaskExecutor);
    }

    public static <E extends BaseRealm> E createRealmOrGetFromCache(RealmConfiguration realmConfiguration, Class<E> cls) {
        return (E) getCache(realmConfiguration.getPath(), true).doCreateRealmOrGetFromCache(realmConfiguration, cls);
    }

    private synchronized <E extends BaseRealm> E doCreateRealmOrGetFromCache(RealmConfiguration realmConfiguration, Class<E> cls) {
        RefAndCount refAndCount;
        BaseRealm baseRealm;
        Throwable th;
        refAndCount = this.refAndCountMap.get(RealmCacheType.valueOf(cls));
        if (getTotalGlobalRefCount() == 0) {
            copyAssetFileIfNeeded(realmConfiguration);
            boolean realmExists = realmConfiguration.realmExists();
            SharedRealm sharedRealm = null;
            try {
                sharedRealm = SharedRealm.getInstance(realmConfiguration);
                if (!realmExists) {
                    ObjectServerFacade.getSyncFacadeIfPossible().downloadRemoteChanges(realmConfiguration);
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                if (Table.primaryKeyTableNeedsMigration(sharedRealm)) {
                    sharedRealm.beginTransaction();
                    if (Table.migratePrimaryKeyTableIfNeeded(sharedRealm)) {
                        sharedRealm.commitTransaction();
                    } else {
                        sharedRealm.cancelTransaction();
                    }
                }
                if (sharedRealm != null) {
                    sharedRealm.close();
                }
                this.configuration = realmConfiguration;
            } catch (Throwable th3) {
                th = th3;
                if (sharedRealm != null) {
                    sharedRealm.close();
                }
                throw th;
            }
        } else {
            validateConfiguration(realmConfiguration);
        }
        if (refAndCount.localRealm.get() == null) {
            if (cls == Realm.class) {
                baseRealm = Realm.createInstance(this);
            } else if (cls == DynamicRealm.class) {
                baseRealm = DynamicRealm.createInstance(this);
            } else {
                throw new IllegalArgumentException(WRONG_REALM_CLASS_MESSAGE);
            }
            refAndCount.localRealm.set(baseRealm);
            refAndCount.localCount.set(0);
            if (cls == Realm.class && refAndCount.globalCount == 0) {
                storeColumnIndices(this.typedColumnIndicesArray, baseRealm.schema.getImmutableColumnIndicies());
            }
            RefAndCount.access$808(refAndCount);
        }
        refAndCount.localCount.set(Integer.valueOf(((Integer) refAndCount.localCount.get()).intValue() + 1));
        return (E) ((BaseRealm) refAndCount.localRealm.get());
    }

    public synchronized void release(BaseRealm baseRealm) {
        String path = baseRealm.getPath();
        RefAndCount refAndCount = this.refAndCountMap.get(RealmCacheType.valueOf((Class<? extends BaseRealm>) baseRealm.getClass()));
        Integer num = (Integer) refAndCount.localCount.get();
        if (num == null) {
            num = 0;
        }
        if (num.intValue() <= 0) {
            RealmLog.warn("%s has been closed already. refCount is %s", path, num);
            return;
        }
        Integer valueOf = Integer.valueOf(num.intValue() - 1);
        if (valueOf.intValue() == 0) {
            refAndCount.localCount.set(null);
            refAndCount.localRealm.set(null);
            RefAndCount.access$810(refAndCount);
            if (refAndCount.globalCount < 0) {
                throw new IllegalStateException("Global reference counter of Realm" + path + " got corrupted.");
            }
            if ((baseRealm instanceof Realm) && refAndCount.globalCount == 0) {
                Arrays.fill(this.typedColumnIndicesArray, (Object) null);
            }
            baseRealm.doClose();
            if (getTotalGlobalRefCount() == 0) {
                this.configuration = null;
                ObjectServerFacade.getFacade(baseRealm.getConfiguration().isSyncConfiguration()).realmClosed(baseRealm.getConfiguration());
            }
        } else {
            refAndCount.localCount.set(valueOf);
        }
    }

    private void validateConfiguration(RealmConfiguration realmConfiguration) {
        if (!this.configuration.equals(realmConfiguration)) {
            if (!Arrays.equals(this.configuration.getEncryptionKey(), realmConfiguration.getEncryptionKey())) {
                throw new IllegalArgumentException(DIFFERENT_KEY_MESSAGE);
            }
            RealmMigration migration = realmConfiguration.getMigration();
            RealmMigration migration2 = this.configuration.getMigration();
            if (migration2 == null || migration == null || !migration2.getClass().equals(migration.getClass()) || migration.equals(migration2)) {
                throw new IllegalArgumentException("Configurations cannot be different if used to open the same file. \nCached configuration: \n" + this.configuration + "\n\nNew configuration: \n" + realmConfiguration);
            }
            throw new IllegalArgumentException("Configurations cannot be different if used to open the same file. The most likely cause is that equals() and hashCode() are not overridden in the migration class: " + realmConfiguration.getMigration().getClass().getCanonicalName());
        }
    }

    public static void invokeWithGlobalRefCount(RealmConfiguration realmConfiguration, Callback callback) {
        synchronized (cachesList) {
            RealmCache cache = getCache(realmConfiguration.getPath(), false);
            if (cache == null) {
                callback.onResult(0);
            } else {
                cache.doInvokeWithGlobalRefCount(callback);
            }
        }
    }

    private synchronized void doInvokeWithGlobalRefCount(Callback callback) {
        callback.onResult(getTotalGlobalRefCount());
    }

    public synchronized void updateSchemaCache(Realm realm) {
        if (this.refAndCountMap.get(RealmCacheType.TYPED_REALM).localRealm.get() != null) {
            ColumnIndices[] columnIndicesArr = this.typedColumnIndicesArray;
            ColumnIndices updateSchemaCache = realm.updateSchemaCache(columnIndicesArr);
            if (updateSchemaCache != null) {
                storeColumnIndices(columnIndicesArr, updateSchemaCache);
            }
        }
    }

    public synchronized void invokeWithLock(Callback0 callback0) {
        callback0.onCall();
    }

    private static void copyAssetFileIfNeeded(RealmConfiguration realmConfiguration) {
        if (realmConfiguration.hasAssetFile()) {
            copyFileIfNeeded(realmConfiguration.getAssetFilePath(), new File(realmConfiguration.getRealmDirectory(), realmConfiguration.getRealmFileName()));
        }
        String syncServerCertificateAssetName = ObjectServerFacade.getFacade(realmConfiguration.isSyncConfiguration()).getSyncServerCertificateAssetName(realmConfiguration);
        if (!Util.isEmptyString(syncServerCertificateAssetName)) {
            copyFileIfNeeded(syncServerCertificateAssetName, new File(ObjectServerFacade.getFacade(realmConfiguration.isSyncConfiguration()).getSyncServerCertificateFilePath(realmConfiguration)));
        }
    }

    private static void copyFileIfNeeded(String str, File file) {
        Throwable th;
        FileOutputStream fileOutputStream;
        InputStream inputStream;
        IOException e;
        if (!file.exists()) {
            IOException e2 = null;
            try {
                try {
                    inputStream = BaseRealm.applicationContext.getAssets().open(str);
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e3) {
                e = e3;
            } catch (Throwable th3) {
                th = th3;
                inputStream = null;
                fileOutputStream = null;
            }
            try {
                if (inputStream == null) {
                    throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, "Invalid input stream to the asset file: " + str);
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read <= -1) {
                            break;
                        }
                        fileOutputStream2.write(bArr, 0, read);
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e4) {
                            e2 = e4;
                        }
                    }
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e5) {
                            e2 = e5;
                            if (e2 == null) {
                            }
                        }
                    }
                    if (e2 != null) {
                        throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, e2);
                    }
                } catch (IOException e6) {
                    e = e6;
                    throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, "Could not resolve the path to the asset file: " + str, e);
                }
            } catch (IOException e7) {
                e = e7;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e8) {
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused) {
                    }
                }
                throw th;
            }
        }
    }

    public static int getLocalThreadCount(RealmConfiguration realmConfiguration) {
        RealmCache cache = getCache(realmConfiguration.getPath(), false);
        if (cache == null) {
            return 0;
        }
        int i = 0;
        for (RefAndCount refAndCount : cache.refAndCountMap.values()) {
            Integer num = (Integer) refAndCount.localCount.get();
            i += num != null ? num.intValue() : 0;
        }
        return i;
    }

    public static ColumnIndices findColumnIndices(ColumnIndices[] columnIndicesArr, long j) {
        for (int length = columnIndicesArr.length - 1; length >= 0; length--) {
            ColumnIndices columnIndices = columnIndicesArr[length];
            if (columnIndices != null && columnIndices.getSchemaVersion() == j) {
                return columnIndices;
            }
        }
        return null;
    }

    private static int storeColumnIndices(ColumnIndices[] columnIndicesArr, ColumnIndices columnIndices) {
        int i = -1;
        long j = Long.MAX_VALUE;
        for (int length = columnIndicesArr.length - 1; length >= 0; length--) {
            if (columnIndicesArr[length] == null) {
                columnIndicesArr[length] = columnIndices;
                return length;
            }
            ColumnIndices columnIndices2 = columnIndicesArr[length];
            if (columnIndices2.getSchemaVersion() <= j) {
                j = columnIndices2.getSchemaVersion();
                i = length;
            }
        }
        columnIndicesArr[i] = columnIndices;
        return i;
    }

    public RealmConfiguration getConfiguration() {
        return this.configuration;
    }

    public ColumnIndices[] getTypedColumnIndicesArray() {
        return this.typedColumnIndicesArray;
    }

    private int getTotalGlobalRefCount() {
        int i = 0;
        for (RefAndCount refAndCount : this.refAndCountMap.values()) {
            i += refAndCount.globalCount;
        }
        return i;
    }

    public void leak() {
        if (!this.isLeaked.getAndSet(true)) {
            leakedCaches.add(this);
        }
    }
}
