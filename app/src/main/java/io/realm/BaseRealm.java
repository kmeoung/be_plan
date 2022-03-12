package io.realm;

import android.content.Context;
import android.os.Looper;
import io.realm.RealmCache;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.CheckedRow;
import io.realm.internal.ColumnInfo;
import io.realm.internal.InvalidRow;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.Util;
import io.realm.internal.async.RealmThreadPoolExecutor;
import io.realm.log.RealmLog;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;

/* loaded from: classes.dex */
public abstract class BaseRealm implements Closeable {
    private static final String CLOSED_REALM_MESSAGE = "This Realm instance has already been closed, making it unusable.";
    private static final String INCORRECT_THREAD_CLOSE_MESSAGE = "Realm access from incorrect thread. Realm instance can only be closed on the thread it was created.";
    private static final String INCORRECT_THREAD_MESSAGE = "Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.";
    static final String LISTENER_NOT_ALLOWED_MESSAGE = "Listeners cannot be used on current thread.";
    private static final String NOT_IN_TRANSACTION_MESSAGE = "Changing Realm data can only be done from inside a transaction.";
    protected static final long UNVERSIONED = -1;
    static volatile Context applicationContext;
    static final RealmThreadPoolExecutor asyncTaskExecutor = RealmThreadPoolExecutor.newDefaultExecutor();
    public static final ThreadLocalRealmObjectContext objectContext = new ThreadLocalRealmObjectContext();
    protected final RealmConfiguration configuration;
    private RealmCache realmCache;
    protected final RealmSchema schema;
    protected SharedRealm sharedRealm;
    final long threadId;

    /* loaded from: classes.dex */
    public interface MigrationCallback {
        void migrationComplete();
    }

    public abstract Observable asObservable();

    public BaseRealm(RealmCache realmCache) {
        this(realmCache.getConfiguration());
        this.realmCache = realmCache;
    }

    public BaseRealm(RealmConfiguration realmConfiguration) {
        this.threadId = Thread.currentThread().getId();
        this.configuration = realmConfiguration;
        SharedRealm.SchemaVersionListener schemaVersionListener = null;
        this.realmCache = null;
        this.sharedRealm = SharedRealm.getInstance(realmConfiguration, this instanceof Realm ? new SharedRealm.SchemaVersionListener() { // from class: io.realm.BaseRealm.1
            @Override // io.realm.internal.SharedRealm.SchemaVersionListener
            public void onSchemaVersionChanged(long j) {
                if (BaseRealm.this.realmCache != null) {
                    BaseRealm.this.realmCache.updateSchemaCache((Realm) BaseRealm.this);
                }
            }
        } : schemaVersionListener, true);
        this.schema = new RealmSchema(this);
    }

    public void setAutoRefresh(boolean z) {
        checkIfValid();
        this.sharedRealm.setAutoRefresh(z);
    }

    public boolean isAutoRefresh() {
        return this.sharedRealm.isAutoRefresh();
    }

    public void refresh() {
        checkIfValid();
        if (isInTransaction()) {
            throw new IllegalStateException("Cannot refresh a Realm instance inside a transaction.");
        }
        this.sharedRealm.refresh();
    }

    public boolean isInTransaction() {
        checkIfValid();
        return this.sharedRealm.isInTransaction();
    }

    public <T extends BaseRealm> void addListener(RealmChangeListener<T> realmChangeListener) {
        if (realmChangeListener == null) {
            throw new IllegalArgumentException("Listener should not be null");
        }
        checkIfValid();
        this.sharedRealm.capabilities.checkCanDeliverNotification(LISTENER_NOT_ALLOWED_MESSAGE);
        this.sharedRealm.realmNotifier.addChangeListener(this, realmChangeListener);
    }

    public <T extends BaseRealm> void removeListener(RealmChangeListener<T> realmChangeListener) {
        if (realmChangeListener == null) {
            throw new IllegalArgumentException("Listener should not be null");
        }
        checkIfValid();
        this.sharedRealm.capabilities.checkCanDeliverNotification(LISTENER_NOT_ALLOWED_MESSAGE);
        this.sharedRealm.realmNotifier.removeChangeListener(this, realmChangeListener);
    }

    public void removeAllListeners() {
        checkIfValid();
        this.sharedRealm.capabilities.checkCanDeliverNotification("removeListener cannot be called on current thread.");
        this.sharedRealm.realmNotifier.removeChangeListeners(this);
    }

    public void writeCopyTo(File file) {
        writeEncryptedCopyTo(file, null);
    }

    public void writeEncryptedCopyTo(File file, byte[] bArr) {
        if (file == null) {
            throw new IllegalArgumentException("The destination argument cannot be null");
        }
        checkIfValid();
        this.sharedRealm.writeCopy(file, bArr);
    }

    public boolean waitForChange() {
        checkIfValid();
        if (isInTransaction()) {
            throw new IllegalStateException("Cannot wait for changes inside of a transaction.");
        } else if (Looper.myLooper() != null) {
            throw new IllegalStateException("Cannot wait for changes inside a Looper thread. Use RealmChangeListeners instead.");
        } else {
            boolean waitForChange = this.sharedRealm.waitForChange();
            if (waitForChange) {
                this.sharedRealm.refresh();
            }
            return waitForChange;
        }
    }

    public void stopWaitForChange() {
        if (this.realmCache != null) {
            this.realmCache.invokeWithLock(new RealmCache.Callback0() { // from class: io.realm.BaseRealm.2
                @Override // io.realm.RealmCache.Callback0
                public void onCall() {
                    if (BaseRealm.this.sharedRealm == null || BaseRealm.this.sharedRealm.isClosed()) {
                        throw new IllegalStateException(BaseRealm.CLOSED_REALM_MESSAGE);
                    }
                    BaseRealm.this.sharedRealm.stopWaitForChange();
                }
            });
            return;
        }
        throw new IllegalStateException(CLOSED_REALM_MESSAGE);
    }

    public void beginTransaction() {
        beginTransaction(false);
    }

    public void beginTransaction(boolean z) {
        checkIfValid();
        this.sharedRealm.beginTransaction(z);
    }

    public void commitTransaction() {
        checkIfValid();
        this.sharedRealm.commitTransaction();
    }

    public void cancelTransaction() {
        checkIfValid();
        this.sharedRealm.cancelTransaction();
    }

    public void checkIfValid() {
        if (this.sharedRealm == null || this.sharedRealm.isClosed()) {
            throw new IllegalStateException(CLOSED_REALM_MESSAGE);
        } else if (this.threadId != Thread.currentThread().getId()) {
            throw new IllegalStateException(INCORRECT_THREAD_MESSAGE);
        }
    }

    public void checkIfInTransaction() {
        if (!this.sharedRealm.isInTransaction()) {
            throw new IllegalStateException(NOT_IN_TRANSACTION_MESSAGE);
        }
    }

    public void checkIfValidAndInTransaction() {
        if (!isInTransaction()) {
            throw new IllegalStateException(NOT_IN_TRANSACTION_MESSAGE);
        }
    }

    public void checkNotInSync() {
        if (this.configuration.isSyncConfiguration()) {
            throw new IllegalArgumentException("You cannot perform changes to a schema. Please update app and restart.");
        }
    }

    public String getPath() {
        return this.configuration.getPath();
    }

    public RealmConfiguration getConfiguration() {
        return this.configuration;
    }

    public long getVersion() {
        return this.sharedRealm.getSchemaVersion();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.threadId != Thread.currentThread().getId()) {
            throw new IllegalStateException(INCORRECT_THREAD_CLOSE_MESSAGE);
        } else if (this.realmCache != null) {
            this.realmCache.release(this);
        } else {
            doClose();
        }
    }

    public void doClose() {
        this.realmCache = null;
        if (this.sharedRealm != null) {
            this.sharedRealm.close();
            this.sharedRealm = null;
        }
        if (this.schema != null) {
            this.schema.close();
        }
    }

    public boolean isClosed() {
        if (this.threadId == Thread.currentThread().getId()) {
            return this.sharedRealm == null || this.sharedRealm.isClosed();
        }
        throw new IllegalStateException(INCORRECT_THREAD_MESSAGE);
    }

    public boolean isEmpty() {
        checkIfValid();
        return this.sharedRealm.isEmpty();
    }

    void setVersion(long j) {
        this.sharedRealm.setSchemaVersion(j);
    }

    public RealmSchema getSchema() {
        return this.schema;
    }

    public <E extends RealmModel> E get(Class<E> cls, String str, UncheckedRow uncheckedRow) {
        if (str != null) {
            return new DynamicRealmObject(this, CheckedRow.getFromRow(uncheckedRow));
        }
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, uncheckedRow, this.schema.getColumnInfo(cls), false, Collections.emptyList());
    }

    <E extends RealmModel> E get(Class<E> cls, long j, boolean z, List<String> list) {
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, this.schema.getTable(cls).getUncheckedRow(j), this.schema.getColumnInfo(cls), z, list);
    }

    public <E extends RealmModel> E get(Class<E> cls, String str, long j) {
        boolean z = str != null;
        Table table = z ? this.schema.getTable(str) : this.schema.getTable(cls);
        if (z) {
            return new DynamicRealmObject(this, j != -1 ? table.getCheckedRow(j) : InvalidRow.INSTANCE);
        }
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, j != -1 ? table.getUncheckedRow(j) : InvalidRow.INSTANCE, this.schema.getColumnInfo(cls), false, Collections.emptyList());
    }

    public void deleteAll() {
        checkIfValid();
        for (RealmObjectSchema realmObjectSchema : this.schema.getAll()) {
            this.schema.getTable(realmObjectSchema.getClassName()).clear();
        }
    }

    public static boolean deleteRealm(final RealmConfiguration realmConfiguration) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        RealmCache.invokeWithGlobalRefCount(realmConfiguration, new RealmCache.Callback() { // from class: io.realm.BaseRealm.3
            @Override // io.realm.RealmCache.Callback
            public void onResult(int i) {
                if (i != 0) {
                    throw new IllegalStateException("It's not allowed to delete the file associated with an open Realm. Remember to close() all the instances of the Realm before deleting its file: " + realmConfiguration.getPath());
                }
                atomicBoolean.set(Util.deleteRealm(realmConfiguration.getPath(), realmConfiguration.getRealmDirectory(), realmConfiguration.getRealmFileName()));
            }
        });
        return atomicBoolean.get();
    }

    public static boolean compactRealm(RealmConfiguration realmConfiguration) {
        SharedRealm instance = SharedRealm.getInstance(realmConfiguration);
        Boolean valueOf = Boolean.valueOf(instance.compact());
        instance.close();
        return valueOf.booleanValue();
    }

    public static void migrateRealm(final RealmConfiguration realmConfiguration, final RealmMigration realmMigration, final MigrationCallback migrationCallback, RealmMigrationNeededException realmMigrationNeededException) throws FileNotFoundException {
        if (realmConfiguration == null) {
            throw new IllegalArgumentException("RealmConfiguration must be provided");
        } else if (realmConfiguration.isSyncConfiguration()) {
            throw new IllegalArgumentException("Manual migrations are not supported for synced Realms");
        } else if (realmMigration == null && realmConfiguration.getMigration() == null) {
            throw new RealmMigrationNeededException(realmConfiguration.getPath(), "RealmMigration must be provided", realmMigrationNeededException);
        } else {
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            RealmCache.invokeWithGlobalRefCount(realmConfiguration, new RealmCache.Callback() { // from class: io.realm.BaseRealm.4
                /* JADX WARN: Removed duplicated region for block: B:30:0x0088  */
                @Override // io.realm.RealmCache.Callback
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void onResult(int r8) {
                    /*
                        r7 = this;
                        if (r8 == 0) goto L_0x001f
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        r0.<init>()
                        java.lang.String r1 = "Cannot migrate a Realm file that is already open: "
                        r0.append(r1)
                        io.realm.RealmConfiguration r1 = r1
                        java.lang.String r1 = r1.getPath()
                        r0.append(r1)
                        java.lang.String r0 = r0.toString()
                        r8.<init>(r0)
                        throw r8
                    L_0x001f:
                        java.io.File r8 = new java.io.File
                        io.realm.RealmConfiguration r0 = r1
                        java.lang.String r0 = r0.getPath()
                        r8.<init>(r0)
                        boolean r8 = r8.exists()
                        if (r8 != 0) goto L_0x0037
                        java.util.concurrent.atomic.AtomicBoolean r8 = r2
                        r0 = 1
                        r8.set(r0)
                        return
                    L_0x0037:
                        io.realm.RealmMigration r8 = r3
                        if (r8 != 0) goto L_0x0043
                        io.realm.RealmConfiguration r8 = r1
                        io.realm.RealmMigration r8 = r8.getMigration()
                    L_0x0041:
                        r0 = r8
                        goto L_0x0046
                    L_0x0043:
                        io.realm.RealmMigration r8 = r3
                        goto L_0x0041
                    L_0x0046:
                        r8 = 0
                        io.realm.RealmConfiguration r1 = r1     // Catch: all -> 0x0079, RuntimeException -> 0x007d
                        io.realm.DynamicRealm r6 = io.realm.DynamicRealm.createInstance(r1)     // Catch: all -> 0x0079, RuntimeException -> 0x007d
                        r6.beginTransaction()     // Catch: all -> 0x0075, RuntimeException -> 0x0077
                        long r2 = r6.getVersion()     // Catch: all -> 0x0075, RuntimeException -> 0x0077
                        io.realm.RealmConfiguration r8 = r1     // Catch: all -> 0x0075, RuntimeException -> 0x0077
                        long r4 = r8.getSchemaVersion()     // Catch: all -> 0x0075, RuntimeException -> 0x0077
                        r1 = r6
                        r0.migrate(r1, r2, r4)     // Catch: all -> 0x0075, RuntimeException -> 0x0077
                        io.realm.RealmConfiguration r8 = r1     // Catch: all -> 0x0075, RuntimeException -> 0x0077
                        long r0 = r8.getSchemaVersion()     // Catch: all -> 0x0075, RuntimeException -> 0x0077
                        r6.setVersion(r0)     // Catch: all -> 0x0075, RuntimeException -> 0x0077
                        r6.commitTransaction()     // Catch: all -> 0x0075, RuntimeException -> 0x0077
                        if (r6 == 0) goto L_0x0074
                        r6.close()
                        io.realm.BaseRealm$MigrationCallback r8 = r4
                        r8.migrationComplete()
                    L_0x0074:
                        return
                    L_0x0075:
                        r8 = move-exception
                        goto L_0x0086
                    L_0x0077:
                        r8 = move-exception
                        goto L_0x0080
                    L_0x0079:
                        r0 = move-exception
                        r6 = r8
                        r8 = r0
                        goto L_0x0086
                    L_0x007d:
                        r0 = move-exception
                        r6 = r8
                        r8 = r0
                    L_0x0080:
                        if (r6 == 0) goto L_0x0085
                        r6.cancelTransaction()     // Catch: all -> 0x0075
                    L_0x0085:
                        throw r8     // Catch: all -> 0x0075
                    L_0x0086:
                        if (r6 == 0) goto L_0x0090
                        r6.close()
                        io.realm.BaseRealm$MigrationCallback r0 = r4
                        r0.migrationComplete()
                    L_0x0090:
                        throw r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: io.realm.BaseRealm.AnonymousClass4.onResult(int):void");
                }
            });
            if (atomicBoolean.get()) {
                throw new FileNotFoundException("Cannot migrate a Realm file which doesn't exist: " + realmConfiguration.getPath());
            }
        }
    }

    protected void finalize() throws Throwable {
        if (this.sharedRealm != null && !this.sharedRealm.isClosed()) {
            RealmLog.warn("Remember to call close() on all Realm instances. Realm %s is being finalized without being closed, this can lead to running out of native memory.", this.configuration.getPath());
            if (this.realmCache != null) {
                this.realmCache.leak();
            }
        }
        super.finalize();
    }

    public SharedRealm getSharedRealm() {
        return this.sharedRealm;
    }

    /* loaded from: classes.dex */
    public static final class RealmObjectContext {
        private boolean acceptDefaultValue;
        private ColumnInfo columnInfo;
        private List<String> excludeFields;
        private BaseRealm realm;
        private Row row;

        public void set(BaseRealm baseRealm, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
            this.realm = baseRealm;
            this.row = row;
            this.columnInfo = columnInfo;
            this.acceptDefaultValue = z;
            this.excludeFields = list;
        }

        public BaseRealm getRealm() {
            return this.realm;
        }

        public Row getRow() {
            return this.row;
        }

        public ColumnInfo getColumnInfo() {
            return this.columnInfo;
        }

        public boolean getAcceptDefaultValue() {
            return this.acceptDefaultValue;
        }

        public List<String> getExcludeFields() {
            return this.excludeFields;
        }

        public void clear() {
            this.realm = null;
            this.row = null;
            this.columnInfo = null;
            this.acceptDefaultValue = false;
            this.excludeFields = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class ThreadLocalRealmObjectContext extends ThreadLocal<RealmObjectContext> {
        ThreadLocalRealmObjectContext() {
        }

        @Override // java.lang.ThreadLocal
        public RealmObjectContext initialValue() {
            return new RealmObjectContext();
        }
    }

    /* loaded from: classes.dex */
    public static abstract class InstanceCallback<T extends BaseRealm> {
        public abstract void onSuccess(T t);

        public void onError(Throwable th) {
            throw new RealmException("Exception happens when initializing Realm in the background thread.", th);
        }
    }
}
