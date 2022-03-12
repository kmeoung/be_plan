package io.realm.internal;

import io.realm.CompactOnLaunchCallback;
import io.realm.RealmConfiguration;
import io.realm.internal.Collection;
import io.realm.internal.android.AndroidCapabilities;
import io.realm.internal.android.AndroidRealmNotifier;
import java.io.Closeable;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public final class SharedRealm implements Closeable, NativeObject {
    public static final byte FILE_EXCEPTION_KIND_ACCESS_ERROR = 0;
    public static final byte FILE_EXCEPTION_KIND_BAD_HISTORY = 1;
    public static final byte FILE_EXCEPTION_KIND_EXISTS = 3;
    public static final byte FILE_EXCEPTION_KIND_FORMAT_UPGRADE_REQUIRED = 6;
    public static final byte FILE_EXCEPTION_KIND_INCOMPATIBLE_LOCK_FILE = 5;
    public static final byte FILE_EXCEPTION_KIND_NOT_FOUND = 4;
    public static final byte FILE_EXCEPTION_KIND_PERMISSION_DENIED = 2;
    private static final byte SCHEMA_MODE_VALUE_ADDITIVE = 3;
    private static final byte SCHEMA_MODE_VALUE_AUTOMATIC = 0;
    private static final byte SCHEMA_MODE_VALUE_MANUAL = 4;
    private static final byte SCHEMA_MODE_VALUE_READONLY = 1;
    private static final byte SCHEMA_MODE_VALUE_RESET_FILE = 2;
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private static volatile File temporaryDirectory;
    public final Capabilities capabilities;
    private final RealmConfiguration configuration;
    private long lastSchemaVersion;
    private final long nativePtr;
    public final RealmNotifier realmNotifier;
    private final SchemaVersionListener schemaChangeListener;
    private final List<WeakReference<PendingRow>> pendingRows = new CopyOnWriteArrayList();
    public final List<WeakReference<Collection>> collections = new CopyOnWriteArrayList();
    public final List<WeakReference<Collection.Iterator>> iterators = new ArrayList();
    final NativeContext context = new NativeContext();

    /* loaded from: classes.dex */
    public interface SchemaVersionListener {
        void onSchemaVersionChanged(long j);
    }

    private static native void nativeBeginTransaction(long j);

    private static native void nativeCancelTransaction(long j);

    private static native void nativeCloseConfig(long j);

    private static native void nativeCloseSharedRealm(long j);

    private static native void nativeCommitTransaction(long j);

    private static native boolean nativeCompact(long j);

    private static native long nativeCreateConfig(String str, byte[] bArr, byte b, boolean z, boolean z2, long j, boolean z3, boolean z4, CompactOnLaunchCallback compactOnLaunchCallback, String str2, String str3, String str4, String str5, boolean z5, String str6);

    private static native long nativeCreateTable(long j, String str);

    private static native long nativeGetFinalizerPtr();

    private static native long nativeGetSharedRealm(long j, RealmNotifier realmNotifier);

    private static native long nativeGetTable(long j, String str);

    private static native String nativeGetTableName(long j, int i);

    private static native long nativeGetVersion(long j);

    private static native long[] nativeGetVersionID(long j);

    private static native boolean nativeHasTable(long j, String str);

    private static native void nativeInit(String str);

    private static native boolean nativeIsAutoRefresh(long j);

    private static native boolean nativeIsClosed(long j);

    private static native boolean nativeIsEmpty(long j);

    private static native boolean nativeIsInTransaction(long j);

    private static native long nativeReadGroup(long j);

    private static native void nativeRefresh(long j);

    private static native void nativeRemoveTable(long j, String str);

    private static native void nativeRenameTable(long j, String str, String str2);

    private static native boolean nativeRequiresMigration(long j, long j2);

    private static native void nativeSetAutoRefresh(long j, boolean z);

    private static native void nativeSetVersion(long j, long j2);

    private static native long nativeSize(long j);

    private static native void nativeStopWaitForChange(long j);

    private static native void nativeUpdateSchema(long j, long j2, long j3);

    private static native boolean nativeWaitForChange(long j);

    private static native void nativeWriteCopy(long j, String str, byte[] bArr);

    public static void initialize(File file) {
        if (temporaryDirectory == null) {
            if (file == null) {
                throw new IllegalArgumentException("'tempDirectory' must not be null.");
            }
            String absolutePath = file.getAbsolutePath();
            if (file.isDirectory() || file.mkdirs() || file.isDirectory()) {
                if (!absolutePath.endsWith("/")) {
                    absolutePath = absolutePath + "/";
                }
                nativeInit(absolutePath);
                temporaryDirectory = file;
                return;
            }
            throw new IOException("failed to create temporary directory: " + absolutePath);
        }
    }

    public static File getTemporaryDirectory() {
        return temporaryDirectory;
    }

    /* loaded from: classes.dex */
    public enum Durability {
        FULL(0),
        MEM_ONLY(1);
        
        final int value;

        Durability(int i) {
            this.value = i;
        }
    }

    /* loaded from: classes.dex */
    public enum SchemaMode {
        SCHEMA_MODE_AUTOMATIC((byte) 0),
        SCHEMA_MODE_READONLY((byte) 1),
        SCHEMA_MODE_RESET_FILE((byte) 2),
        SCHEMA_MODE_ADDITIVE((byte) 3),
        SCHEMA_MODE_MANUAL((byte) 4);
        
        final byte value;

        SchemaMode(byte b) {
            this.value = b;
        }

        public byte getNativeValue() {
            return this.value;
        }
    }

    /* loaded from: classes.dex */
    public static class VersionID implements Comparable<VersionID> {
        public final long index;
        public final long version;

        VersionID(long j, long j2) {
            this.version = j;
            this.index = j2;
        }

        public int compareTo(VersionID versionID) {
            if (versionID == null) {
                throw new IllegalArgumentException("Version cannot be compared to a null value.");
            } else if (this.version > versionID.version) {
                return 1;
            } else {
                return this.version < versionID.version ? -1 : 0;
            }
        }

        public String toString() {
            return "VersionID{version=" + this.version + ", index=" + this.index + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            VersionID versionID = (VersionID) obj;
            return this.version == versionID.version && this.index == versionID.index;
        }

        public int hashCode() {
            return (((super.hashCode() * 31) + ((int) (this.version ^ (this.version >>> 32)))) * 31) + ((int) (this.index ^ (this.index >>> 32)));
        }
    }

    private SharedRealm(long j, RealmConfiguration realmConfiguration, SchemaVersionListener schemaVersionListener) {
        AndroidCapabilities androidCapabilities = new AndroidCapabilities();
        AndroidRealmNotifier androidRealmNotifier = new AndroidRealmNotifier(this, androidCapabilities);
        this.nativePtr = nativeGetSharedRealm(j, androidRealmNotifier);
        this.configuration = realmConfiguration;
        this.capabilities = androidCapabilities;
        this.realmNotifier = androidRealmNotifier;
        this.schemaChangeListener = schemaVersionListener;
        this.context.addReference(this);
        this.lastSchemaVersion = schemaVersionListener == null ? -1L : getSchemaVersion();
        nativeSetAutoRefresh(this.nativePtr, androidCapabilities.canDeliverNotification());
    }

    public static SharedRealm getInstance(RealmConfiguration realmConfiguration) {
        return getInstance(realmConfiguration, null, false);
    }

    public static SharedRealm getInstance(RealmConfiguration realmConfiguration, SchemaVersionListener schemaVersionListener, boolean z) {
        Object[] userAndServerUrl = ObjectServerFacade.getSyncFacadeIfPossible().getUserAndServerUrl(realmConfiguration);
        String str = (String) userAndServerUrl[0];
        String str2 = (String) userAndServerUrl[1];
        long nativeCreateConfig = nativeCreateConfig(realmConfiguration.getPath(), realmConfiguration.getEncryptionKey(), (str2 != null ? SchemaMode.SCHEMA_MODE_ADDITIVE : SchemaMode.SCHEMA_MODE_MANUAL).getNativeValue(), realmConfiguration.getDurability() == Durability.MEM_ONLY, false, realmConfiguration.getSchemaVersion(), true, z, realmConfiguration.getCompactOnLaunchCallback(), str2, (String) userAndServerUrl[2], str, (String) userAndServerUrl[3], Boolean.TRUE.equals(userAndServerUrl[4]), (String) userAndServerUrl[5]);
        try {
            ObjectServerFacade.getSyncFacadeIfPossible().wrapObjectStoreSessionIfRequired(realmConfiguration);
            return new SharedRealm(nativeCreateConfig, realmConfiguration, schemaVersionListener);
        } finally {
            nativeCloseConfig(nativeCreateConfig);
        }
    }

    public void beginTransaction() {
        beginTransaction(false);
    }

    public void beginTransaction(boolean z) {
        if (z || !this.configuration.isReadOnly()) {
            detachIterators();
            executePendingRowQueries();
            nativeBeginTransaction(this.nativePtr);
            invokeSchemaChangeListenerIfSchemaChanged();
            return;
        }
        throw new IllegalStateException("Write transactions cannot be used when a Realm is marked as read-only.");
    }

    public void commitTransaction() {
        nativeCommitTransaction(this.nativePtr);
    }

    public void cancelTransaction() {
        nativeCancelTransaction(this.nativePtr);
    }

    public boolean isInTransaction() {
        return nativeIsInTransaction(this.nativePtr);
    }

    public void setSchemaVersion(long j) {
        nativeSetVersion(this.nativePtr, j);
    }

    public long getSchemaVersion() {
        return nativeGetVersion(this.nativePtr);
    }

    public long getGroupNative() {
        return nativeReadGroup(this.nativePtr);
    }

    public boolean hasTable(String str) {
        return nativeHasTable(this.nativePtr, str);
    }

    public Table getTable(String str) {
        return new Table(this, nativeGetTable(this.nativePtr, str));
    }

    public Table createTable(String str) {
        return new Table(this, nativeCreateTable(this.nativePtr, str));
    }

    public void renameTable(String str, String str2) {
        nativeRenameTable(this.nativePtr, str, str2);
    }

    public void removeTable(String str) {
        nativeRemoveTable(this.nativePtr, str);
    }

    public String getTableName(int i) {
        return nativeGetTableName(this.nativePtr, i);
    }

    public long size() {
        return nativeSize(this.nativePtr);
    }

    public String getPath() {
        return this.configuration.getPath();
    }

    public boolean isEmpty() {
        return nativeIsEmpty(this.nativePtr);
    }

    public void refresh() {
        nativeRefresh(this.nativePtr);
        invokeSchemaChangeListenerIfSchemaChanged();
    }

    public VersionID getVersionID() {
        long[] nativeGetVersionID = nativeGetVersionID(this.nativePtr);
        return new VersionID(nativeGetVersionID[0], nativeGetVersionID[1]);
    }

    public boolean isClosed() {
        return nativeIsClosed(this.nativePtr);
    }

    public void writeCopy(File file, byte[] bArr) {
        if (!file.isFile() || !file.exists()) {
            nativeWriteCopy(this.nativePtr, file.getAbsolutePath(), bArr);
            return;
        }
        throw new IllegalArgumentException("The destination file must not exist");
    }

    public boolean waitForChange() {
        return nativeWaitForChange(this.nativePtr);
    }

    public void stopWaitForChange() {
        nativeStopWaitForChange(this.nativePtr);
    }

    public boolean compact() {
        return nativeCompact(this.nativePtr);
    }

    public void updateSchema(OsSchemaInfo osSchemaInfo, long j) {
        nativeUpdateSchema(this.nativePtr, osSchemaInfo.getNativePtr(), j);
    }

    public void setAutoRefresh(boolean z) {
        this.capabilities.checkCanDeliverNotification(null);
        nativeSetAutoRefresh(this.nativePtr, z);
    }

    public boolean isAutoRefresh() {
        return nativeIsAutoRefresh(this.nativePtr);
    }

    public boolean requiresMigration(long j) {
        return nativeRequiresMigration(this.nativePtr, j);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.realmNotifier != null) {
            this.realmNotifier.close();
        }
        synchronized (this.context) {
            nativeCloseSharedRealm(this.nativePtr);
        }
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    public void invokeSchemaChangeListenerIfSchemaChanged() {
        if (this.schemaChangeListener != null) {
            long j = this.lastSchemaVersion;
            long schemaVersion = getSchemaVersion();
            if (schemaVersion != j) {
                this.lastSchemaVersion = schemaVersion;
                this.schemaChangeListener.onSchemaVersionChanged(schemaVersion);
            }
        }
    }

    public void addIterator(Collection.Iterator iterator) {
        this.iterators.add(new WeakReference<>(iterator));
    }

    void detachIterators() {
        for (WeakReference<Collection.Iterator> weakReference : this.iterators) {
            Collection.Iterator iterator = weakReference.get();
            if (iterator != null) {
                iterator.detach();
            }
        }
        this.iterators.clear();
    }

    public void invalidateIterators() {
        for (WeakReference<Collection.Iterator> weakReference : this.iterators) {
            Collection.Iterator iterator = weakReference.get();
            if (iterator != null) {
                iterator.invalidate();
            }
        }
        this.iterators.clear();
    }

    public void addPendingRow(PendingRow pendingRow) {
        this.pendingRows.add(new WeakReference<>(pendingRow));
    }

    public void removePendingRow(PendingRow pendingRow) {
        for (WeakReference<PendingRow> weakReference : this.pendingRows) {
            PendingRow pendingRow2 = weakReference.get();
            if (pendingRow2 == null || pendingRow2 == pendingRow) {
                this.pendingRows.remove(weakReference);
            }
        }
    }

    private void executePendingRowQueries() {
        for (WeakReference<PendingRow> weakReference : this.pendingRows) {
            PendingRow pendingRow = weakReference.get();
            if (pendingRow != null) {
                pendingRow.executeQuery();
            }
        }
        this.pendingRows.clear();
    }
}
