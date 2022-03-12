package io.realm;

import io.realm.BaseRealm;
import io.realm.exceptions.RealmException;
import io.realm.internal.CheckedRow;
import io.realm.internal.OsObject;
import io.realm.internal.Table;
import io.realm.log.RealmLog;
import java.io.File;
import java.util.Locale;
import rx.Observable;

/* loaded from: classes.dex */
public class DynamicRealm extends BaseRealm {

    /* loaded from: classes.dex */
    public interface Transaction {
        void execute(DynamicRealm dynamicRealm);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void beginTransaction() {
        super.beginTransaction();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void cancelTransaction() {
        super.cancelTransaction();
    }

    @Override // io.realm.BaseRealm, java.io.Closeable, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void commitTransaction() {
        super.commitTransaction();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void deleteAll() {
        super.deleteAll();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ RealmConfiguration getConfiguration() {
        return super.getConfiguration();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ String getPath() {
        return super.getPath();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ RealmSchema getSchema() {
        return super.getSchema();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ long getVersion() {
        return super.getVersion();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isAutoRefresh() {
        return super.isAutoRefresh();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isClosed() {
        return super.isClosed();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isInTransaction() {
        return super.isInTransaction();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void refresh() {
        super.refresh();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void setAutoRefresh(boolean z) {
        super.setAutoRefresh(z);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void stopWaitForChange() {
        super.stopWaitForChange();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean waitForChange() {
        return super.waitForChange();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void writeCopyTo(File file) {
        super.writeCopyTo(file);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void writeEncryptedCopyTo(File file, byte[] bArr) {
        super.writeEncryptedCopyTo(file, bArr);
    }

    private DynamicRealm(RealmCache realmCache) {
        super(realmCache);
    }

    private DynamicRealm(RealmConfiguration realmConfiguration) {
        super(realmConfiguration);
    }

    public static DynamicRealm getInstance(RealmConfiguration realmConfiguration) {
        if (realmConfiguration != null) {
            return (DynamicRealm) RealmCache.createRealmOrGetFromCache(realmConfiguration, DynamicRealm.class);
        }
        throw new IllegalArgumentException("A non-null RealmConfiguration must be provided");
    }

    public static RealmAsyncTask getInstanceAsync(RealmConfiguration realmConfiguration, Callback callback) {
        if (realmConfiguration != null) {
            return RealmCache.createRealmOrGetFromCacheAsync(realmConfiguration, callback, DynamicRealm.class);
        }
        throw new IllegalArgumentException("A non-null RealmConfiguration must be provided");
    }

    public DynamicRealmObject createObject(String str) {
        checkIfValid();
        Table table = this.schema.getTable(str);
        if (!table.hasPrimaryKey()) {
            return new DynamicRealmObject(this, CheckedRow.getFromRow(OsObject.create(table)));
        }
        throw new RealmException(String.format(Locale.US, "'%s' has a primary key, use 'createObject(String, Object)' instead.", str));
    }

    public DynamicRealmObject createObject(String str, Object obj) {
        return new DynamicRealmObject(this, CheckedRow.getFromRow(OsObject.createWithPrimaryKey(this.schema.getTable(str), obj)));
    }

    public RealmQuery<DynamicRealmObject> where(String str) {
        checkIfValid();
        if (this.sharedRealm.hasTable(Table.getTableNameForClass(str))) {
            return RealmQuery.createDynamicQuery(this, str);
        }
        throw new IllegalArgumentException("Class does not exist in the Realm and cannot be queried: " + str);
    }

    public void addChangeListener(RealmChangeListener<DynamicRealm> realmChangeListener) {
        addListener(realmChangeListener);
    }

    public void removeChangeListener(RealmChangeListener<DynamicRealm> realmChangeListener) {
        removeListener(realmChangeListener);
    }

    public void removeAllChangeListeners() {
        removeAllListeners();
    }

    public void delete(String str) {
        checkIfValid();
        checkIfInTransaction();
        this.schema.getTable(str).clear();
    }

    public void executeTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction should not be null");
        }
        beginTransaction();
        try {
            transaction.execute(this);
            commitTransaction();
        } catch (RuntimeException e) {
            if (isInTransaction()) {
                cancelTransaction();
            } else {
                RealmLog.warn("Could not cancel transaction, not currently in a transaction.", new Object[0]);
            }
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DynamicRealm createInstance(RealmCache realmCache) {
        return new DynamicRealm(realmCache);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DynamicRealm createInstance(RealmConfiguration realmConfiguration) {
        return new DynamicRealm(realmConfiguration);
    }

    @Override // io.realm.BaseRealm
    public Observable<DynamicRealm> asObservable() {
        return this.configuration.getRxFactory().from(this);
    }

    /* loaded from: classes.dex */
    public static abstract class Callback extends BaseRealm.InstanceCallback<DynamicRealm> {
        public abstract void onSuccess(DynamicRealm dynamicRealm);

        @Override // io.realm.BaseRealm.InstanceCallback
        public void onError(Throwable th) {
            super.onError(th);
        }
    }
}
