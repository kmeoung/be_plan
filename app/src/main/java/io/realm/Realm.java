package io.realm;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.SystemClock;
import android.util.JsonReader;
import io.realm.BaseRealm;
import io.realm.RealmCache;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmFileException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnIndices;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsObject;
import io.realm.internal.RealmCore;
import io.realm.internal.RealmNotifier;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.async.RealmAsyncTaskImpl;
import io.realm.internal.util.Pair;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;

/* loaded from: classes.dex */
public class Realm extends BaseRealm {
    public static final String DEFAULT_REALM_NAME = "default.realm";
    private static final String NULL_CONFIG_MSG = "A non-null RealmConfiguration must be provided";
    private static RealmConfiguration defaultConfiguration;
    private static final Object defaultConfigurationLock = new Object();

    /* loaded from: classes.dex */
    public interface Transaction {

        /* loaded from: classes.dex */
        public static class Callback {
            public void onError(Exception exc) {
            }

            public void onSuccess() {
            }
        }

        /* loaded from: classes.dex */
        public interface OnError {
            void onError(Throwable th);
        }

        /* loaded from: classes.dex */
        public interface OnSuccess {
            void onSuccess();
        }

        void execute(Realm realm);
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

    private Realm(RealmCache realmCache) {
        super(realmCache);
    }

    @Override // io.realm.BaseRealm
    public Observable<Realm> asObservable() {
        return this.configuration.getRxFactory().from(this);
    }

    public static synchronized void init(Context context) {
        synchronized (Realm.class) {
            if (BaseRealm.applicationContext == null) {
                if (context == null) {
                    throw new IllegalArgumentException("Non-null context required.");
                }
                checkFilesDirAvailable(context);
                RealmCore.loadLibrary(context);
                setDefaultConfiguration(new RealmConfiguration.Builder(context).build());
                ObjectServerFacade.getSyncFacadeIfPossible().init(context);
                BaseRealm.applicationContext = context.getApplicationContext();
                SharedRealm.initialize(new File(context.getFilesDir(), ".realm.temp"));
            }
        }
    }

    private static void checkFilesDirAvailable(Context context) {
        File filesDir = context.getFilesDir();
        if (filesDir != null) {
            if (!filesDir.exists()) {
                try {
                    filesDir.mkdirs();
                } catch (SecurityException unused) {
                }
            } else {
                return;
            }
        }
        if (filesDir == null || !filesDir.exists()) {
            long[] jArr = {1, 2, 5, 10, 16};
            long j = 0;
            int i = -1;
            while (true) {
                if (context.getFilesDir() != null && context.getFilesDir().exists()) {
                    break;
                }
                i++;
                long j2 = jArr[Math.min(i, jArr.length - 1)];
                SystemClock.sleep(j2);
                long j3 = j + j2;
                if (j3 > 200) {
                    break;
                }
                j = j3;
            }
        }
        if (context.getFilesDir() == null || !context.getFilesDir().exists()) {
            throw new IllegalStateException("Context.getFilesDir() returns " + context.getFilesDir() + " which is not an existing directory. See https://issuetracker.google.com/issues/36918154");
        }
    }

    public static Realm getDefaultInstance() {
        RealmConfiguration defaultConfiguration2 = getDefaultConfiguration();
        if (defaultConfiguration2 != null) {
            return (Realm) RealmCache.createRealmOrGetFromCache(defaultConfiguration2, Realm.class);
        }
        if (BaseRealm.applicationContext == null) {
            throw new IllegalStateException("Call `Realm.init(Context)` before calling this method.");
        }
        throw new IllegalStateException("Set default configuration by using `Realm.setDefaultConfiguration(RealmConfiguration)`.");
    }

    public static Realm getInstance(RealmConfiguration realmConfiguration) {
        if (realmConfiguration != null) {
            return (Realm) RealmCache.createRealmOrGetFromCache(realmConfiguration, Realm.class);
        }
        throw new IllegalArgumentException(NULL_CONFIG_MSG);
    }

    public static RealmAsyncTask getInstanceAsync(RealmConfiguration realmConfiguration, Callback callback) {
        if (realmConfiguration != null) {
            return RealmCache.createRealmOrGetFromCacheAsync(realmConfiguration, callback, Realm.class);
        }
        throw new IllegalArgumentException(NULL_CONFIG_MSG);
    }

    public static void setDefaultConfiguration(RealmConfiguration realmConfiguration) {
        if (realmConfiguration == null) {
            throw new IllegalArgumentException(NULL_CONFIG_MSG);
        }
        synchronized (defaultConfigurationLock) {
            defaultConfiguration = realmConfiguration;
        }
    }

    public static RealmConfiguration getDefaultConfiguration() {
        RealmConfiguration realmConfiguration;
        synchronized (defaultConfigurationLock) {
            realmConfiguration = defaultConfiguration;
        }
        return realmConfiguration;
    }

    public static void removeDefaultConfiguration() {
        synchronized (defaultConfigurationLock) {
            defaultConfiguration = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Realm createInstance(RealmCache realmCache) {
        RealmConfiguration configuration = realmCache.getConfiguration();
        try {
            return createAndValidateFromCache(realmCache);
        } catch (RealmMigrationNeededException e) {
            if (configuration.shouldDeleteRealmIfMigrationNeeded()) {
                deleteRealm(configuration);
            } else {
                try {
                    if (configuration.getMigration() != null) {
                        migrateRealm(configuration, e);
                    }
                } catch (FileNotFoundException e2) {
                    throw new RealmFileException(RealmFileException.Kind.NOT_FOUND, e2);
                }
            }
            return createAndValidateFromCache(realmCache);
        }
    }

    private static Realm createAndValidateFromCache(RealmCache realmCache) {
        Realm realm = new Realm(realmCache);
        RealmConfiguration realmConfiguration = realm.configuration;
        long version = realm.getVersion();
        long schemaVersion = realmConfiguration.getSchemaVersion();
        ColumnIndices findColumnIndices = RealmCache.findColumnIndices(realmCache.getTypedColumnIndicesArray(), schemaVersion);
        if (findColumnIndices != null) {
            realm.schema.setInitialColumnIndices(findColumnIndices);
        } else {
            if (!realmConfiguration.isSyncConfiguration() && version != -1) {
                if (version < schemaVersion) {
                    realm.doClose();
                    throw new RealmMigrationNeededException(realmConfiguration.getPath(), String.format(Locale.US, "Realm on disk need to migrate from v%s to v%s", Long.valueOf(version), Long.valueOf(schemaVersion)));
                } else if (schemaVersion < version) {
                    realm.doClose();
                    throw new IllegalArgumentException(String.format(Locale.US, "Realm on disk is newer than the one specified: v%s vs. v%s", Long.valueOf(version), Long.valueOf(schemaVersion)));
                }
            }
            try {
                initializeRealm(realm);
            } catch (RuntimeException e) {
                realm.doClose();
                throw e;
            }
        }
        return realm;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0079 A[Catch: all -> 0x00be, Exception -> 0x00c5, LOOP:0: B:20:0x0073->B:22:0x0079, LOOP_END, TryCatch #1 {Exception -> 0x00c5, blocks: (B:3:0x0003, B:7:0x0017, B:9:0x0029, B:11:0x002f, B:13:0x0044, B:15:0x004a, B:16:0x0051, B:17:0x0052, B:19:0x0066, B:20:0x0073, B:22:0x0079, B:23:0x0099, B:26:0x00a0, B:29:0x00ab), top: B:49:0x0003, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void initializeRealm(io.realm.Realm r17) {
        /*
            r1 = r17
            r3 = 1
            r1.beginTransaction(r3)     // Catch: all -> 0x00c1, Exception -> 0x00c5
            io.realm.RealmConfiguration r4 = r17.getConfiguration()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            long r5 = r17.getVersion()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            r7 = -1
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x0016
            r7 = 1
            goto L_0x0017
        L_0x0016:
            r7 = 0
        L_0x0017:
            long r8 = r4.getSchemaVersion()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            io.realm.internal.RealmProxyMediator r10 = r4.getSchemaMediator()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            java.util.Set r11 = r10.getModelClasses()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            boolean r12 = r4.isSyncConfiguration()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            if (r12 == 0) goto L_0x0042
            boolean r12 = r4.isReadOnly()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            if (r12 != 0) goto L_0x0065
            io.realm.internal.OsSchemaInfo r12 = new io.realm.internal.OsSchemaInfo     // Catch: all -> 0x00c1, Exception -> 0x00c5
            java.util.Map r13 = r10.getExpectedObjectSchemaInfoMap()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            java.util.Collection r13 = r13.values()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            r12.<init>(r13)     // Catch: all -> 0x00c1, Exception -> 0x00c5
            io.realm.internal.SharedRealm r13 = r1.sharedRealm     // Catch: all -> 0x00c1, Exception -> 0x00c5
            r13.updateSchema(r12, r8)     // Catch: all -> 0x00c1, Exception -> 0x00c5
            goto L_0x0066
        L_0x0042:
            if (r7 == 0) goto L_0x0065
            boolean r12 = r4.isReadOnly()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            if (r12 == 0) goto L_0x0052
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException     // Catch: all -> 0x00c1, Exception -> 0x00c5
            java.lang.String r4 = "Cannot create the Realm schema in a read-only file."
            r3.<init>(r4)     // Catch: all -> 0x00c1, Exception -> 0x00c5
            throw r3     // Catch: all -> 0x00c1, Exception -> 0x00c5
        L_0x0052:
            io.realm.internal.OsSchemaInfo r12 = new io.realm.internal.OsSchemaInfo     // Catch: all -> 0x00c1, Exception -> 0x00c5
            java.util.Map r13 = r10.getExpectedObjectSchemaInfoMap()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            java.util.Collection r13 = r13.values()     // Catch: all -> 0x00c1, Exception -> 0x00c5
            r12.<init>(r13)     // Catch: all -> 0x00c1, Exception -> 0x00c5
            io.realm.internal.SharedRealm r13 = r1.sharedRealm     // Catch: all -> 0x00c1, Exception -> 0x00c5
            r13.updateSchema(r12, r8)     // Catch: all -> 0x00c1, Exception -> 0x00c5
            goto L_0x0066
        L_0x0065:
            r3 = 0
        L_0x0066:
            java.util.HashMap r12 = new java.util.HashMap     // Catch: all -> 0x00be, Exception -> 0x00c5
            int r13 = r11.size()     // Catch: all -> 0x00be, Exception -> 0x00c5
            r12.<init>(r13)     // Catch: all -> 0x00be, Exception -> 0x00c5
            java.util.Iterator r11 = r11.iterator()     // Catch: all -> 0x00be, Exception -> 0x00c5
        L_0x0073:
            boolean r13 = r11.hasNext()     // Catch: all -> 0x00be, Exception -> 0x00c5
            if (r13 == 0) goto L_0x0099
            java.lang.Object r13 = r11.next()     // Catch: all -> 0x00be, Exception -> 0x00c5
            java.lang.Class r13 = (java.lang.Class) r13     // Catch: all -> 0x00be, Exception -> 0x00c5
            java.lang.String r14 = r10.getTableName(r13)     // Catch: all -> 0x00be, Exception -> 0x00c5
            java.lang.String r14 = io.realm.internal.Table.getClassNameForTable(r14)     // Catch: all -> 0x00be, Exception -> 0x00c5
            io.realm.internal.util.Pair r14 = io.realm.internal.util.Pair.create(r13, r14)     // Catch: all -> 0x00be, Exception -> 0x00c5
            io.realm.internal.SharedRealm r15 = r1.sharedRealm     // Catch: all -> 0x00be, Exception -> 0x00c5
            boolean r2 = r4.isSyncConfiguration()     // Catch: all -> 0x00be, Exception -> 0x00c5
            io.realm.internal.ColumnInfo r2 = r10.validateTable(r13, r15, r2)     // Catch: all -> 0x00be, Exception -> 0x00c5
            r12.put(r14, r2)     // Catch: all -> 0x00be, Exception -> 0x00c5
            goto L_0x0073
        L_0x0099:
            io.realm.RealmSchema r2 = r17.getSchema()     // Catch: all -> 0x00be, Exception -> 0x00c5
            if (r7 == 0) goto L_0x00a0
            r5 = r8
        L_0x00a0:
            r2.setInitialColumnIndices(r5, r12)     // Catch: all -> 0x00be, Exception -> 0x00c5
            io.realm.Realm$Transaction r2 = r4.getInitialDataTransaction()     // Catch: all -> 0x00be, Exception -> 0x00c5
            if (r2 == 0) goto L_0x00ae
            if (r7 == 0) goto L_0x00ae
            r2.execute(r1)     // Catch: all -> 0x00be, Exception -> 0x00c5
        L_0x00ae:
            if (r3 == 0) goto L_0x00b4
            r17.commitTransaction()
            goto L_0x00bd
        L_0x00b4:
            boolean r2 = r17.isInTransaction()
            if (r2 == 0) goto L_0x00bd
            r17.cancelTransaction()
        L_0x00bd:
            return
        L_0x00be:
            r0 = move-exception
            r2 = r0
            goto L_0x00c8
        L_0x00c1:
            r0 = move-exception
            r2 = r0
            r3 = 0
            goto L_0x00c8
        L_0x00c5:
            r0 = move-exception
            r2 = r0
            throw r2     // Catch: all -> 0x00c1
        L_0x00c8:
            if (r3 == 0) goto L_0x00ce
            r17.commitTransaction()
            goto L_0x00d7
        L_0x00ce:
            boolean r3 = r17.isInTransaction()
            if (r3 == 0) goto L_0x00d7
            r17.cancelTransaction()
        L_0x00d7:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.Realm.initializeRealm(io.realm.Realm):void");
    }

    public <E extends RealmModel> void createAllFromJson(Class<E> cls, JSONArray jSONArray) {
        if (!(cls == null || jSONArray == null)) {
            checkIfValid();
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONArray.getJSONObject(i), false);
                } catch (JSONException e) {
                    throw new RealmException("Could not map JSON", e);
                }
            }
        }
    }

    public <E extends RealmModel> void createOrUpdateAllFromJson(Class<E> cls, JSONArray jSONArray) {
        if (!(cls == null || jSONArray == null)) {
            checkIfValid();
            checkHasPrimaryKey(cls);
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONArray.getJSONObject(i), true);
                } catch (JSONException e) {
                    throw new RealmException("Could not map JSON", e);
                }
            }
        }
    }

    public <E extends RealmModel> void createAllFromJson(Class<E> cls, String str) {
        if (cls != null && str != null && str.length() != 0) {
            try {
                createAllFromJson(cls, new JSONArray(str));
            } catch (JSONException e) {
                throw new RealmException("Could not create JSON array from string", e);
            }
        }
    }

    public <E extends RealmModel> void createOrUpdateAllFromJson(Class<E> cls, String str) {
        if (cls != null && str != null && str.length() != 0) {
            checkIfValid();
            checkHasPrimaryKey(cls);
            try {
                createOrUpdateAllFromJson(cls, new JSONArray(str));
            } catch (JSONException e) {
                throw new RealmException("Could not create JSON array from string", e);
            }
        }
    }

    @TargetApi(11)
    public <E extends RealmModel> void createAllFromJson(Class<E> cls, InputStream inputStream) throws IOException {
        if (cls != null && inputStream != null) {
            checkIfValid();
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            try {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    this.configuration.getSchemaMediator().createUsingJsonStream(cls, this, jsonReader);
                }
                jsonReader.endArray();
            } finally {
                jsonReader.close();
            }
        }
    }

    @TargetApi(11)
    public <E extends RealmModel> void createOrUpdateAllFromJson(Class<E> cls, InputStream inputStream) {
        Throwable th;
        JSONException e;
        if (cls != null && inputStream != null) {
            checkIfValid();
            checkHasPrimaryKey(cls);
            Scanner scanner = null;
            try {
                try {
                    scanner = getFullStringScanner(inputStream);
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (JSONException e2) {
                e = e2;
            }
            try {
                JSONArray jSONArray = new JSONArray(scanner.next());
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONArray.getJSONObject(i), true);
                }
                if (scanner != null) {
                    scanner.close();
                }
            } catch (JSONException e3) {
                e = e3;
                throw new RealmException("Failed to read JSON", e);
            } catch (Throwable th3) {
                th = th3;
                if (scanner != null) {
                    scanner.close();
                }
                throw th;
            }
        }
    }

    public <E extends RealmModel> E createObjectFromJson(Class<E> cls, JSONObject jSONObject) {
        if (cls == null || jSONObject == null) {
            return null;
        }
        checkIfValid();
        try {
            return (E) this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONObject, false);
        } catch (JSONException e) {
            throw new RealmException("Could not map JSON", e);
        }
    }

    public <E extends RealmModel> E createOrUpdateObjectFromJson(Class<E> cls, JSONObject jSONObject) {
        if (cls == null || jSONObject == null) {
            return null;
        }
        checkIfValid();
        checkHasPrimaryKey(cls);
        try {
            return (E) this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONObject, true);
        } catch (JSONException e) {
            throw new RealmException("Could not map JSON", e);
        }
    }

    public <E extends RealmModel> E createObjectFromJson(Class<E> cls, String str) {
        if (cls == null || str == null || str.length() == 0) {
            return null;
        }
        try {
            return (E) createObjectFromJson(cls, new JSONObject(str));
        } catch (JSONException e) {
            throw new RealmException("Could not create Json object from string", e);
        }
    }

    public <E extends RealmModel> E createOrUpdateObjectFromJson(Class<E> cls, String str) {
        if (cls == null || str == null || str.length() == 0) {
            return null;
        }
        checkIfValid();
        checkHasPrimaryKey(cls);
        try {
            return (E) createOrUpdateObjectFromJson(cls, new JSONObject(str));
        } catch (JSONException e) {
            throw new RealmException("Could not create Json object from string", e);
        }
    }

    @TargetApi(11)
    public <E extends RealmModel> E createObjectFromJson(Class<E> cls, InputStream inputStream) throws IOException {
        Scanner scanner;
        Throwable th;
        JSONException e;
        E e2;
        if (cls == null || inputStream == null) {
            return null;
        }
        checkIfValid();
        try {
            if (this.schema.getTable(cls).hasPrimaryKey()) {
                try {
                    scanner = getFullStringScanner(inputStream);
                } catch (JSONException e3) {
                    e = e3;
                }
                try {
                    e2 = (E) this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, new JSONObject(scanner.next()), false);
                    if (scanner != null) {
                        scanner.close();
                    }
                } catch (JSONException e4) {
                    e = e4;
                    throw new RealmException("Failed to read JSON", e);
                } catch (Throwable th2) {
                    th = th2;
                    if (scanner != null) {
                        scanner.close();
                    }
                    throw th;
                }
            } else {
                JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                try {
                    e2 = (E) this.configuration.getSchemaMediator().createUsingJsonStream(cls, this, jsonReader);
                } finally {
                    jsonReader.close();
                }
            }
            return e2;
        } catch (Throwable th3) {
            th = th3;
            scanner = null;
        }
    }

    @TargetApi(11)
    public <E extends RealmModel> E createOrUpdateObjectFromJson(Class<E> cls, InputStream inputStream) {
        Throwable th;
        JSONException e;
        Scanner fullStringScanner;
        Scanner scanner = null;
        if (cls == null || inputStream == null) {
            return null;
        }
        checkIfValid();
        checkHasPrimaryKey(cls);
        try {
            try {
                fullStringScanner = getFullStringScanner(inputStream);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (JSONException e2) {
            e = e2;
        }
        try {
            E e3 = (E) createOrUpdateObjectFromJson(cls, new JSONObject(fullStringScanner.next()));
            if (fullStringScanner != null) {
                fullStringScanner.close();
            }
            return e3;
        } catch (JSONException e4) {
            e = e4;
            throw new RealmException("Failed to read JSON", e);
        } catch (Throwable th3) {
            th = th3;
            scanner = fullStringScanner;
            if (scanner != null) {
                scanner.close();
            }
            throw th;
        }
    }

    private Scanner getFullStringScanner(InputStream inputStream) {
        return new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
    }

    public <E extends RealmModel> E createObject(Class<E> cls) {
        checkIfValid();
        return (E) createObjectInternal(cls, true, Collections.emptyList());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public <E extends RealmModel> E createObjectInternal(Class<E> cls, boolean z, List<String> list) {
        Table table = this.schema.getTable(cls);
        if (!table.hasPrimaryKey()) {
            return (E) this.configuration.getSchemaMediator().newInstance(cls, this, OsObject.create(table), this.schema.getColumnInfo(cls), z, list);
        }
        throw new RealmException(String.format(Locale.US, "'%s' has a primary key, use 'createObject(Class<E>, Object)' instead.", table.getClassName()));
    }

    public <E extends RealmModel> E createObject(Class<E> cls, Object obj) {
        checkIfValid();
        return (E) createObjectInternal(cls, obj, true, Collections.emptyList());
    }

    <E extends RealmModel> E createObjectInternal(Class<E> cls, Object obj, boolean z, List<String> list) {
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, OsObject.createWithPrimaryKey(this.schema.getTable(cls), obj), this.schema.getColumnInfo(cls), z, list);
    }

    public <E extends RealmModel> E copyToRealm(E e) {
        checkNotNullObject(e);
        return (E) copyOrUpdate(e, false, new HashMap());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <E extends RealmModel> E copyToRealmOrUpdate(E e) {
        checkNotNullObject(e);
        checkHasPrimaryKey(e.getClass());
        return (E) copyOrUpdate(e, true, new HashMap());
    }

    public <E extends RealmModel> List<E> copyToRealm(Iterable<E> iterable) {
        if (iterable == null) {
            return new ArrayList();
        }
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        for (E e : iterable) {
            checkNotNullObject(e);
            arrayList.add(copyOrUpdate(e, false, hashMap));
        }
        return arrayList;
    }

    public void insert(Collection<? extends RealmModel> collection) {
        checkIfValidAndInTransaction();
        if (collection == null) {
            throw new IllegalArgumentException("Null objects cannot be inserted into Realm.");
        } else if (!collection.isEmpty()) {
            this.configuration.getSchemaMediator().insert(this, collection);
        }
    }

    public void insert(RealmModel realmModel) {
        checkIfValidAndInTransaction();
        if (realmModel == null) {
            throw new IllegalArgumentException("Null object cannot be inserted into Realm.");
        }
        this.configuration.getSchemaMediator().insert(this, realmModel, new HashMap());
    }

    public void insertOrUpdate(Collection<? extends RealmModel> collection) {
        checkIfValidAndInTransaction();
        if (collection == null) {
            throw new IllegalArgumentException("Null objects cannot be inserted into Realm.");
        } else if (!collection.isEmpty()) {
            this.configuration.getSchemaMediator().insertOrUpdate(this, collection);
        }
    }

    public void insertOrUpdate(RealmModel realmModel) {
        checkIfValidAndInTransaction();
        if (realmModel == null) {
            throw new IllegalArgumentException("Null object cannot be inserted into Realm.");
        }
        this.configuration.getSchemaMediator().insertOrUpdate(this, realmModel, new HashMap());
    }

    public <E extends RealmModel> List<E> copyToRealmOrUpdate(Iterable<E> iterable) {
        if (iterable == null) {
            return new ArrayList(0);
        }
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        for (E e : iterable) {
            checkNotNullObject(e);
            arrayList.add(copyOrUpdate(e, true, hashMap));
        }
        return arrayList;
    }

    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> iterable) {
        return copyFromRealm(iterable, Integer.MAX_VALUE);
    }

    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> iterable, int i) {
        checkMaxDepth(i);
        if (iterable == null) {
            return new ArrayList(0);
        }
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        for (E e : iterable) {
            checkValidObjectForDetach(e);
            arrayList.add(createDetachedCopy(e, i, hashMap));
        }
        return arrayList;
    }

    public <E extends RealmModel> E copyFromRealm(E e) {
        return (E) copyFromRealm((Realm) e, Integer.MAX_VALUE);
    }

    public <E extends RealmModel> E copyFromRealm(E e, int i) {
        checkMaxDepth(i);
        checkValidObjectForDetach(e);
        return (E) createDetachedCopy(e, i, new HashMap());
    }

    public <E extends RealmModel> RealmQuery<E> where(Class<E> cls) {
        checkIfValid();
        return RealmQuery.createQuery(this, cls);
    }

    public void addChangeListener(RealmChangeListener<Realm> realmChangeListener) {
        addListener(realmChangeListener);
    }

    public void removeChangeListener(RealmChangeListener<Realm> realmChangeListener) {
        removeListener(realmChangeListener);
    }

    public void removeAllChangeListeners() {
        removeAllListeners();
    }

    public void executeTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction should not be null");
        }
        beginTransaction();
        try {
            transaction.execute(this);
            commitTransaction();
        } catch (Throwable th) {
            if (isInTransaction()) {
                cancelTransaction();
            } else {
                RealmLog.warn("Could not cancel transaction, not currently in a transaction.", new Object[0]);
            }
            throw th;
        }
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction) {
        return executeTransactionAsync(transaction, null, null);
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, Transaction.OnSuccess onSuccess) {
        if (onSuccess != null) {
            return executeTransactionAsync(transaction, onSuccess, null);
        }
        throw new IllegalArgumentException("onSuccess callback can't be null");
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, Transaction.OnError onError) {
        if (onError != null) {
            return executeTransactionAsync(transaction, null, onError);
        }
        throw new IllegalArgumentException("onError callback can't be null");
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, Transaction.OnSuccess onSuccess, Transaction.OnError onError) {
        checkIfValid();
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction should not be null");
        }
        boolean canDeliverNotification = this.sharedRealm.capabilities.canDeliverNotification();
        if (!(onSuccess == null && onError == null)) {
            this.sharedRealm.capabilities.checkCanDeliverNotification("Callback cannot be delivered on current thread.");
        }
        return new RealmAsyncTaskImpl(asyncTaskExecutor.submitTransaction(new AnonymousClass1(getConfiguration(), transaction, canDeliverNotification, onSuccess, this.sharedRealm.realmNotifier, onError)), asyncTaskExecutor);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: io.realm.Realm$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements Runnable {
        final /* synthetic */ boolean val$canDeliverNotification;
        final /* synthetic */ Transaction.OnError val$onError;
        final /* synthetic */ Transaction.OnSuccess val$onSuccess;
        final /* synthetic */ RealmConfiguration val$realmConfiguration;
        final /* synthetic */ RealmNotifier val$realmNotifier;
        final /* synthetic */ Transaction val$transaction;

        AnonymousClass1(RealmConfiguration realmConfiguration, Transaction transaction, boolean z, Transaction.OnSuccess onSuccess, RealmNotifier realmNotifier, Transaction.OnError onError) {
            this.val$realmConfiguration = realmConfiguration;
            this.val$transaction = transaction;
            this.val$canDeliverNotification = z;
            this.val$onSuccess = onSuccess;
            this.val$realmNotifier = realmNotifier;
            this.val$onError = onError;
        }

        @Override // java.lang.Runnable
        public void run() {
            final SharedRealm.VersionID versionID;
            if (!Thread.currentThread().isInterrupted()) {
                Realm instance = Realm.getInstance(this.val$realmConfiguration);
                instance.beginTransaction();
                final Throwable th = null;
                try {
                    this.val$transaction.execute(instance);
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        if (instance.isInTransaction()) {
                            instance.cancelTransaction();
                        }
                        instance.close();
                        versionID = null;
                    } finally {
                    }
                }
                if (Thread.currentThread().isInterrupted()) {
                    try {
                        if (instance.isInTransaction()) {
                            instance.cancelTransaction();
                        }
                    } finally {
                    }
                } else {
                    instance.commitTransaction();
                    versionID = instance.sharedRealm.getVersionID();
                    try {
                        if (instance.isInTransaction()) {
                            instance.cancelTransaction();
                        }
                        if (this.val$canDeliverNotification) {
                            if (versionID != null && this.val$onSuccess != null) {
                                this.val$realmNotifier.post(new Runnable() { // from class: io.realm.Realm.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        if (Realm.this.isClosed()) {
                                            AnonymousClass1.this.val$onSuccess.onSuccess();
                                        } else if (Realm.this.sharedRealm.getVersionID().compareTo(versionID) < 0) {
                                            Realm.this.sharedRealm.realmNotifier.addTransactionCallback(new Runnable() { // from class: io.realm.Realm.1.1.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    AnonymousClass1.this.val$onSuccess.onSuccess();
                                                }
                                            });
                                        } else {
                                            AnonymousClass1.this.val$onSuccess.onSuccess();
                                        }
                                    }
                                });
                            } else if (th != null) {
                                this.val$realmNotifier.post(new Runnable() { // from class: io.realm.Realm.1.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        if (AnonymousClass1.this.val$onError != null) {
                                            AnonymousClass1.this.val$onError.onError(th);
                                            return;
                                        }
                                        throw new RealmException("Async transaction failed", th);
                                    }
                                });
                            }
                        } else if (th != null) {
                            throw new RealmException("Async transaction failed", th);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public void delete(Class<? extends RealmModel> cls) {
        checkIfValid();
        this.schema.getTable(cls).clear();
    }

    private <E extends RealmModel> E copyOrUpdate(E e, boolean z, Map<RealmModel, RealmObjectProxy> map) {
        checkIfValid();
        return (E) this.configuration.getSchemaMediator().copyOrUpdate(this, e, z, map);
    }

    private <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        checkIfValid();
        return (E) this.configuration.getSchemaMediator().createDetachedCopy(e, i, map);
    }

    private <E extends RealmModel> void checkNotNullObject(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Null objects cannot be copied into Realm.");
        }
    }

    private void checkHasPrimaryKey(Class<? extends RealmModel> cls) {
        if (!this.schema.getTable(cls).hasPrimaryKey()) {
            throw new IllegalArgumentException("A RealmObject with no @PrimaryKey cannot be updated: " + cls.toString());
        }
    }

    private void checkMaxDepth(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("maxDepth must be > 0. It was: " + i);
        }
    }

    private <E extends RealmModel> void checkValidObjectForDetach(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Null objects cannot be copied from Realm.");
        } else if (!RealmObject.isManaged(e) || !RealmObject.isValid(e)) {
            throw new IllegalArgumentException("Only valid managed objects can be copied from Realm.");
        } else if (e instanceof DynamicRealmObject) {
            throw new IllegalArgumentException("DynamicRealmObject cannot be copied from Realm.");
        }
    }

    public static void migrateRealm(RealmConfiguration realmConfiguration) throws FileNotFoundException {
        migrateRealm(realmConfiguration, (RealmMigration) null);
    }

    private static void migrateRealm(RealmConfiguration realmConfiguration, RealmMigrationNeededException realmMigrationNeededException) throws FileNotFoundException {
        BaseRealm.migrateRealm(realmConfiguration, null, new BaseRealm.MigrationCallback() { // from class: io.realm.Realm.2
            @Override // io.realm.BaseRealm.MigrationCallback
            public void migrationComplete() {
            }
        }, realmMigrationNeededException);
    }

    public static void migrateRealm(RealmConfiguration realmConfiguration, RealmMigration realmMigration) throws FileNotFoundException {
        BaseRealm.migrateRealm(realmConfiguration, realmMigration, new BaseRealm.MigrationCallback() { // from class: io.realm.Realm.3
            @Override // io.realm.BaseRealm.MigrationCallback
            public void migrationComplete() {
            }
        }, null);
    }

    public static boolean deleteRealm(RealmConfiguration realmConfiguration) {
        return BaseRealm.deleteRealm(realmConfiguration);
    }

    public static boolean compactRealm(RealmConfiguration realmConfiguration) {
        if (!realmConfiguration.isSyncConfiguration()) {
            return BaseRealm.compactRealm(realmConfiguration);
        }
        throw new UnsupportedOperationException("Compacting is not supported yet on synced Realms. See https://github.com/realm/realm-core/issues/2345");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Table getTable(Class<? extends RealmModel> cls) {
        return this.schema.getTable(cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ColumnIndices updateSchemaCache(ColumnIndices[] columnIndicesArr) {
        long schemaVersion = this.sharedRealm.getSchemaVersion();
        ColumnIndices columnIndices = null;
        if (schemaVersion == this.schema.getSchemaVersion()) {
            return null;
        }
        ColumnIndices findColumnIndices = RealmCache.findColumnIndices(columnIndicesArr, schemaVersion);
        if (findColumnIndices == null) {
            RealmProxyMediator schemaMediator = getConfiguration().getSchemaMediator();
            Set<Class<? extends RealmModel>> modelClasses = schemaMediator.getModelClasses();
            HashMap hashMap = new HashMap(modelClasses.size());
            try {
                for (Class<? extends RealmModel> cls : modelClasses) {
                    hashMap.put(Pair.create(cls, Table.getClassNameForTable(schemaMediator.getTableName(cls))), schemaMediator.validateTable(cls, this.sharedRealm, true));
                }
                columnIndices = new ColumnIndices(schemaVersion, hashMap);
                findColumnIndices = columnIndices;
            } catch (RealmMigrationNeededException e) {
                throw e;
            }
        }
        this.schema.updateColumnIndices(findColumnIndices);
        return columnIndices;
    }

    public static Object getDefaultModule() {
        try {
            Constructor<?> constructor = Class.forName("io.realm.DefaultRealmModule").getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return constructor.newInstance(new Object[0]);
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (IllegalAccessException e) {
            throw new RealmException("Could not create an instance of io.realm.DefaultRealmModule", e);
        } catch (InstantiationException e2) {
            throw new RealmException("Could not create an instance of io.realm.DefaultRealmModule", e2);
        } catch (InvocationTargetException e3) {
            throw new RealmException("Could not create an instance of io.realm.DefaultRealmModule", e3);
        }
    }

    public static int getGlobalInstanceCount(RealmConfiguration realmConfiguration) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        RealmCache.invokeWithGlobalRefCount(realmConfiguration, new RealmCache.Callback() { // from class: io.realm.Realm.4
            @Override // io.realm.RealmCache.Callback
            public void onResult(int i) {
                atomicInteger.set(i);
            }
        });
        return atomicInteger.get();
    }

    public static int getLocalInstanceCount(RealmConfiguration realmConfiguration) {
        return RealmCache.getLocalThreadCount(realmConfiguration);
    }

    /* loaded from: classes.dex */
    public static abstract class Callback extends BaseRealm.InstanceCallback<Realm> {
        public abstract void onSuccess(Realm realm);

        @Override // io.realm.BaseRealm.InstanceCallback
        public void onError(Throwable th) {
            super.onError(th);
        }
    }
}
