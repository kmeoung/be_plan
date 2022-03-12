package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.BaseRealm;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kr.timehub.beplan.entry.database.DbStopWatch;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class DbStopWatchRealmProxy extends DbStopWatch implements RealmObjectProxy, DbStopWatchRealmProxyInterface {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private DbStopWatchColumnInfo columnInfo;
    private ProxyState<DbStopWatch> proxyState;

    public static String getTableName() {
        return "class_DbStopWatch";
    }

    /* loaded from: classes.dex */
    public static final class DbStopWatchColumnInfo extends ColumnInfo {
        long CGSEQIndex;
        long PSEQIndex;
        long TSEQIndex;
        long cgTitleIndex;
        long ellIndex;
        long endTimeIndex;
        long pTitleIndex;
        long startTimeIndex;
        long tTitleIndex;

        DbStopWatchColumnInfo(SharedRealm sharedRealm, Table table) {
            super(9);
            this.PSEQIndex = addColumnDetails(table, "PSEQ", RealmFieldType.INTEGER);
            this.CGSEQIndex = addColumnDetails(table, "CGSEQ", RealmFieldType.INTEGER);
            this.TSEQIndex = addColumnDetails(table, "TSEQ", RealmFieldType.INTEGER);
            this.pTitleIndex = addColumnDetails(table, "pTitle", RealmFieldType.STRING);
            this.cgTitleIndex = addColumnDetails(table, "cgTitle", RealmFieldType.STRING);
            this.tTitleIndex = addColumnDetails(table, "tTitle", RealmFieldType.STRING);
            this.startTimeIndex = addColumnDetails(table, "startTime", RealmFieldType.INTEGER);
            this.endTimeIndex = addColumnDetails(table, "endTime", RealmFieldType.INTEGER);
            this.ellIndex = addColumnDetails(table, "ell", RealmFieldType.INTEGER);
        }

        DbStopWatchColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        public final ColumnInfo copy(boolean z) {
            return new DbStopWatchColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            DbStopWatchColumnInfo dbStopWatchColumnInfo = (DbStopWatchColumnInfo) columnInfo;
            DbStopWatchColumnInfo dbStopWatchColumnInfo2 = (DbStopWatchColumnInfo) columnInfo2;
            dbStopWatchColumnInfo2.PSEQIndex = dbStopWatchColumnInfo.PSEQIndex;
            dbStopWatchColumnInfo2.CGSEQIndex = dbStopWatchColumnInfo.CGSEQIndex;
            dbStopWatchColumnInfo2.TSEQIndex = dbStopWatchColumnInfo.TSEQIndex;
            dbStopWatchColumnInfo2.pTitleIndex = dbStopWatchColumnInfo.pTitleIndex;
            dbStopWatchColumnInfo2.cgTitleIndex = dbStopWatchColumnInfo.cgTitleIndex;
            dbStopWatchColumnInfo2.tTitleIndex = dbStopWatchColumnInfo.tTitleIndex;
            dbStopWatchColumnInfo2.startTimeIndex = dbStopWatchColumnInfo.startTimeIndex;
            dbStopWatchColumnInfo2.endTimeIndex = dbStopWatchColumnInfo.endTimeIndex;
            dbStopWatchColumnInfo2.ellIndex = dbStopWatchColumnInfo.ellIndex;
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("PSEQ");
        arrayList.add("CGSEQ");
        arrayList.add("TSEQ");
        arrayList.add("pTitle");
        arrayList.add("cgTitle");
        arrayList.add("tTitle");
        arrayList.add("startTime");
        arrayList.add("endTime");
        arrayList.add("ell");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    public DbStopWatchRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState == null) {
            BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
            this.columnInfo = (DbStopWatchColumnInfo) realmObjectContext.getColumnInfo();
            this.proxyState = new ProxyState<>(this);
            this.proxyState.setRealm$realm(realmObjectContext.getRealm());
            this.proxyState.setRow$realm(realmObjectContext.getRow());
            this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
            this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
        }
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public int realmGet$PSEQ() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.PSEQIndex);
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public void realmSet$PSEQ(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.PSEQIndex, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.PSEQIndex, row$realm.getIndex(), i, true);
        }
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public int realmGet$CGSEQ() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.CGSEQIndex);
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public void realmSet$CGSEQ(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.CGSEQIndex, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.CGSEQIndex, row$realm.getIndex(), i, true);
        }
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public int realmGet$TSEQ() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.TSEQIndex);
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public void realmSet$TSEQ(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.TSEQIndex, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.TSEQIndex, row$realm.getIndex(), i, true);
        }
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public String realmGet$pTitle() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.pTitleIndex);
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public void realmSet$pTitle(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.pTitleIndex);
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.pTitleIndex, str);
            }
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.pTitleIndex, row$realm.getIndex(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.pTitleIndex, row$realm.getIndex(), str, true);
            }
        }
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public String realmGet$cgTitle() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.cgTitleIndex);
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public void realmSet$cgTitle(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.cgTitleIndex);
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.cgTitleIndex, str);
            }
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.cgTitleIndex, row$realm.getIndex(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.cgTitleIndex, row$realm.getIndex(), str, true);
            }
        }
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public String realmGet$tTitle() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.tTitleIndex);
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public void realmSet$tTitle(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.tTitleIndex);
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.tTitleIndex, str);
            }
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.tTitleIndex, row$realm.getIndex(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.tTitleIndex, row$realm.getIndex(), str, true);
            }
        }
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public long realmGet$startTime() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.startTimeIndex);
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public void realmSet$startTime(long j) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.startTimeIndex, j);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.startTimeIndex, row$realm.getIndex(), j, true);
        }
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public long realmGet$endTime() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.endTimeIndex);
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public void realmSet$endTime(long j) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.endTimeIndex, j);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.endTimeIndex, row$realm.getIndex(), j, true);
        }
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public long realmGet$ell() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.ellIndex);
    }

    @Override // kr.timehub.beplan.entry.database.DbStopWatch, io.realm.DbStopWatchRealmProxyInterface
    public void realmSet$ell(long j) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.ellIndex, j);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.ellIndex, row$realm.getIndex(), j, true);
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("DbStopWatch");
        builder.addProperty("PSEQ", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("CGSEQ", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("TSEQ", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("pTitle", RealmFieldType.STRING, false, false, false);
        builder.addProperty("cgTitle", RealmFieldType.STRING, false, false, false);
        builder.addProperty("tTitle", RealmFieldType.STRING, false, false, false);
        builder.addProperty("startTime", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("endTime", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("ell", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static DbStopWatchColumnInfo validateTable(SharedRealm sharedRealm, boolean z) {
        if (!sharedRealm.hasTable("class_DbStopWatch")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'DbStopWatch' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_DbStopWatch");
        long columnCount = table.getColumnCount();
        if (columnCount != 9) {
            if (columnCount < 9) {
                String path = sharedRealm.getPath();
                throw new RealmMigrationNeededException(path, "Field count is less than expected - expected 9 but was " + columnCount);
            } else if (z) {
                RealmLog.debug("Field count is more than expected - expected 9 but was %1$d", Long.valueOf(columnCount));
            } else {
                String path2 = sharedRealm.getPath();
                throw new RealmMigrationNeededException(path2, "Field count is more than expected - expected 9 but was " + columnCount);
            }
        }
        HashMap hashMap = new HashMap();
        for (long j = 0; j < columnCount; j++) {
            hashMap.put(table.getColumnName(j), table.getColumnType(j));
        }
        DbStopWatchColumnInfo dbStopWatchColumnInfo = new DbStopWatchColumnInfo(sharedRealm, table);
        if (table.hasPrimaryKey()) {
            String path3 = sharedRealm.getPath();
            throw new RealmMigrationNeededException(path3, "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        } else if (!hashMap.containsKey("PSEQ")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'PSEQ' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        } else if (hashMap.get("PSEQ") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'PSEQ' in existing Realm file.");
        } else if (table.isColumnNullable(dbStopWatchColumnInfo.PSEQIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'PSEQ' does support null values in the existing Realm file. Use corresponding boxed type for field 'PSEQ' or migrate using RealmObjectSchema.setNullable().");
        } else if (!hashMap.containsKey("CGSEQ")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'CGSEQ' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        } else if (hashMap.get("CGSEQ") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'CGSEQ' in existing Realm file.");
        } else if (table.isColumnNullable(dbStopWatchColumnInfo.CGSEQIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'CGSEQ' does support null values in the existing Realm file. Use corresponding boxed type for field 'CGSEQ' or migrate using RealmObjectSchema.setNullable().");
        } else if (!hashMap.containsKey("TSEQ")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'TSEQ' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        } else if (hashMap.get("TSEQ") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'TSEQ' in existing Realm file.");
        } else if (table.isColumnNullable(dbStopWatchColumnInfo.TSEQIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'TSEQ' does support null values in the existing Realm file. Use corresponding boxed type for field 'TSEQ' or migrate using RealmObjectSchema.setNullable().");
        } else if (!hashMap.containsKey("pTitle")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'pTitle' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        } else if (hashMap.get("pTitle") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'pTitle' in existing Realm file.");
        } else if (!table.isColumnNullable(dbStopWatchColumnInfo.pTitleIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'pTitle' is required. Either set @Required to field 'pTitle' or migrate using RealmObjectSchema.setNullable().");
        } else if (!hashMap.containsKey("cgTitle")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'cgTitle' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        } else if (hashMap.get("cgTitle") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'cgTitle' in existing Realm file.");
        } else if (!table.isColumnNullable(dbStopWatchColumnInfo.cgTitleIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'cgTitle' is required. Either set @Required to field 'cgTitle' or migrate using RealmObjectSchema.setNullable().");
        } else if (!hashMap.containsKey("tTitle")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'tTitle' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        } else if (hashMap.get("tTitle") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'tTitle' in existing Realm file.");
        } else if (!table.isColumnNullable(dbStopWatchColumnInfo.tTitleIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'tTitle' is required. Either set @Required to field 'tTitle' or migrate using RealmObjectSchema.setNullable().");
        } else if (!hashMap.containsKey("startTime")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'startTime' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        } else if (hashMap.get("startTime") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'startTime' in existing Realm file.");
        } else if (table.isColumnNullable(dbStopWatchColumnInfo.startTimeIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'startTime' does support null values in the existing Realm file. Use corresponding boxed type for field 'startTime' or migrate using RealmObjectSchema.setNullable().");
        } else if (!hashMap.containsKey("endTime")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'endTime' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        } else if (hashMap.get("endTime") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'endTime' in existing Realm file.");
        } else if (table.isColumnNullable(dbStopWatchColumnInfo.endTimeIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'endTime' does support null values in the existing Realm file. Use corresponding boxed type for field 'endTime' or migrate using RealmObjectSchema.setNullable().");
        } else if (!hashMap.containsKey("ell")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'ell' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        } else if (hashMap.get("ell") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'ell' in existing Realm file.");
        } else if (!table.isColumnNullable(dbStopWatchColumnInfo.ellIndex)) {
            return dbStopWatchColumnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'ell' does support null values in the existing Realm file. Use corresponding boxed type for field 'ell' or migrate using RealmObjectSchema.setNullable().");
        }
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    public static DbStopWatch createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        DbStopWatch dbStopWatch = (DbStopWatch) realm.createObjectInternal(DbStopWatch.class, true, Collections.emptyList());
        if (jSONObject.has("PSEQ")) {
            if (jSONObject.isNull("PSEQ")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'PSEQ' to null.");
            }
            dbStopWatch.realmSet$PSEQ(jSONObject.getInt("PSEQ"));
        }
        if (jSONObject.has("CGSEQ")) {
            if (jSONObject.isNull("CGSEQ")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'CGSEQ' to null.");
            }
            dbStopWatch.realmSet$CGSEQ(jSONObject.getInt("CGSEQ"));
        }
        if (jSONObject.has("TSEQ")) {
            if (jSONObject.isNull("TSEQ")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'TSEQ' to null.");
            }
            dbStopWatch.realmSet$TSEQ(jSONObject.getInt("TSEQ"));
        }
        if (jSONObject.has("pTitle")) {
            if (jSONObject.isNull("pTitle")) {
                dbStopWatch.realmSet$pTitle(null);
            } else {
                dbStopWatch.realmSet$pTitle(jSONObject.getString("pTitle"));
            }
        }
        if (jSONObject.has("cgTitle")) {
            if (jSONObject.isNull("cgTitle")) {
                dbStopWatch.realmSet$cgTitle(null);
            } else {
                dbStopWatch.realmSet$cgTitle(jSONObject.getString("cgTitle"));
            }
        }
        if (jSONObject.has("tTitle")) {
            if (jSONObject.isNull("tTitle")) {
                dbStopWatch.realmSet$tTitle(null);
            } else {
                dbStopWatch.realmSet$tTitle(jSONObject.getString("tTitle"));
            }
        }
        if (jSONObject.has("startTime")) {
            if (jSONObject.isNull("startTime")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'startTime' to null.");
            }
            dbStopWatch.realmSet$startTime(jSONObject.getLong("startTime"));
        }
        if (jSONObject.has("endTime")) {
            if (jSONObject.isNull("endTime")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'endTime' to null.");
            }
            dbStopWatch.realmSet$endTime(jSONObject.getLong("endTime"));
        }
        if (jSONObject.has("ell")) {
            if (jSONObject.isNull("ell")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'ell' to null.");
            }
            dbStopWatch.realmSet$ell(jSONObject.getLong("ell"));
        }
        return dbStopWatch;
    }

    @TargetApi(11)
    public static DbStopWatch createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        DbStopWatch dbStopWatch = new DbStopWatch();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("PSEQ")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'PSEQ' to null.");
                }
                dbStopWatch.realmSet$PSEQ(jsonReader.nextInt());
            } else if (nextName.equals("CGSEQ")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'CGSEQ' to null.");
                }
                dbStopWatch.realmSet$CGSEQ(jsonReader.nextInt());
            } else if (nextName.equals("TSEQ")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'TSEQ' to null.");
                }
                dbStopWatch.realmSet$TSEQ(jsonReader.nextInt());
            } else if (nextName.equals("pTitle")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    dbStopWatch.realmSet$pTitle(null);
                } else {
                    dbStopWatch.realmSet$pTitle(jsonReader.nextString());
                }
            } else if (nextName.equals("cgTitle")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    dbStopWatch.realmSet$cgTitle(null);
                } else {
                    dbStopWatch.realmSet$cgTitle(jsonReader.nextString());
                }
            } else if (nextName.equals("tTitle")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    dbStopWatch.realmSet$tTitle(null);
                } else {
                    dbStopWatch.realmSet$tTitle(jsonReader.nextString());
                }
            } else if (nextName.equals("startTime")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'startTime' to null.");
                }
                dbStopWatch.realmSet$startTime(jsonReader.nextLong());
            } else if (nextName.equals("endTime")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'endTime' to null.");
                }
                dbStopWatch.realmSet$endTime(jsonReader.nextLong());
            } else if (!nextName.equals("ell")) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.skipValue();
                throw new IllegalArgumentException("Trying to set non-nullable field 'ell' to null.");
            } else {
                dbStopWatch.realmSet$ell(jsonReader.nextLong());
            }
        }
        jsonReader.endObject();
        return (DbStopWatch) realm.copyToRealm((Realm) dbStopWatch);
    }

    public static DbStopWatch copyOrUpdate(Realm realm, DbStopWatch dbStopWatch, boolean z, Map<RealmModel, RealmObjectProxy> map) {
        boolean z2 = dbStopWatch instanceof RealmObjectProxy;
        if (z2) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) dbStopWatch;
            if (!(realmObjectProxy.realmGet$proxyState().getRealm$realm() == null || realmObjectProxy.realmGet$proxyState().getRealm$realm().threadId == realm.threadId)) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
        }
        if (z2) {
            RealmObjectProxy realmObjectProxy2 = (RealmObjectProxy) dbStopWatch;
            if (realmObjectProxy2.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy2.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return dbStopWatch;
            }
        }
        BaseRealm.objectContext.get();
        RealmObjectProxy realmObjectProxy3 = map.get(dbStopWatch);
        if (realmObjectProxy3 != null) {
            return (DbStopWatch) realmObjectProxy3;
        }
        return copy(realm, dbStopWatch, z, map);
    }

    public static DbStopWatch copy(Realm realm, DbStopWatch dbStopWatch, boolean z, Map<RealmModel, RealmObjectProxy> map) {
        RealmObjectProxy realmObjectProxy = map.get(dbStopWatch);
        if (realmObjectProxy != null) {
            return (DbStopWatch) realmObjectProxy;
        }
        DbStopWatch dbStopWatch2 = (DbStopWatch) realm.createObjectInternal(DbStopWatch.class, false, Collections.emptyList());
        map.put(dbStopWatch, (RealmObjectProxy) dbStopWatch2);
        DbStopWatch dbStopWatch3 = dbStopWatch;
        DbStopWatch dbStopWatch4 = dbStopWatch2;
        dbStopWatch4.realmSet$PSEQ(dbStopWatch3.realmGet$PSEQ());
        dbStopWatch4.realmSet$CGSEQ(dbStopWatch3.realmGet$CGSEQ());
        dbStopWatch4.realmSet$TSEQ(dbStopWatch3.realmGet$TSEQ());
        dbStopWatch4.realmSet$pTitle(dbStopWatch3.realmGet$pTitle());
        dbStopWatch4.realmSet$cgTitle(dbStopWatch3.realmGet$cgTitle());
        dbStopWatch4.realmSet$tTitle(dbStopWatch3.realmGet$tTitle());
        dbStopWatch4.realmSet$startTime(dbStopWatch3.realmGet$startTime());
        dbStopWatch4.realmSet$endTime(dbStopWatch3.realmGet$endTime());
        dbStopWatch4.realmSet$ell(dbStopWatch3.realmGet$ell());
        return dbStopWatch2;
    }

    public static long insert(Realm realm, DbStopWatch dbStopWatch, Map<RealmModel, Long> map) {
        if (dbStopWatch instanceof RealmObjectProxy) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) dbStopWatch;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getIndex();
            }
        }
        Table table = realm.getTable(DbStopWatch.class);
        long nativePtr = table.getNativePtr();
        DbStopWatchColumnInfo dbStopWatchColumnInfo = (DbStopWatchColumnInfo) realm.schema.getColumnInfo(DbStopWatch.class);
        long createRow = OsObject.createRow(table);
        map.put(dbStopWatch, Long.valueOf(createRow));
        DbStopWatch dbStopWatch2 = dbStopWatch;
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.PSEQIndex, createRow, dbStopWatch2.realmGet$PSEQ(), false);
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.CGSEQIndex, createRow, dbStopWatch2.realmGet$CGSEQ(), false);
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.TSEQIndex, createRow, dbStopWatch2.realmGet$TSEQ(), false);
        String realmGet$pTitle = dbStopWatch2.realmGet$pTitle();
        if (realmGet$pTitle != null) {
            Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.pTitleIndex, createRow, realmGet$pTitle, false);
        }
        String realmGet$cgTitle = dbStopWatch2.realmGet$cgTitle();
        if (realmGet$cgTitle != null) {
            Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.cgTitleIndex, createRow, realmGet$cgTitle, false);
        }
        String realmGet$tTitle = dbStopWatch2.realmGet$tTitle();
        if (realmGet$tTitle != null) {
            Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.tTitleIndex, createRow, realmGet$tTitle, false);
        }
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.startTimeIndex, createRow, dbStopWatch2.realmGet$startTime(), false);
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.endTimeIndex, createRow, dbStopWatch2.realmGet$endTime(), false);
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.ellIndex, createRow, dbStopWatch2.realmGet$ell(), false);
        return createRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(DbStopWatch.class);
        long nativePtr = table.getNativePtr();
        DbStopWatchColumnInfo dbStopWatchColumnInfo = (DbStopWatchColumnInfo) realm.schema.getColumnInfo(DbStopWatch.class);
        while (it.hasNext()) {
            DbStopWatch dbStopWatch = (DbStopWatch) it.next();
            if (!map.containsKey(dbStopWatch)) {
                if (dbStopWatch instanceof RealmObjectProxy) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) dbStopWatch;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(dbStopWatch, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getIndex()));
                    }
                }
                long createRow = OsObject.createRow(table);
                map.put(dbStopWatch, Long.valueOf(createRow));
                DbStopWatch dbStopWatch2 = dbStopWatch;
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.PSEQIndex, createRow, dbStopWatch2.realmGet$PSEQ(), false);
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.CGSEQIndex, createRow, dbStopWatch2.realmGet$CGSEQ(), false);
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.TSEQIndex, createRow, dbStopWatch2.realmGet$TSEQ(), false);
                String realmGet$pTitle = dbStopWatch2.realmGet$pTitle();
                if (realmGet$pTitle != null) {
                    Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.pTitleIndex, createRow, realmGet$pTitle, false);
                }
                String realmGet$cgTitle = dbStopWatch2.realmGet$cgTitle();
                if (realmGet$cgTitle != null) {
                    Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.cgTitleIndex, createRow, realmGet$cgTitle, false);
                }
                String realmGet$tTitle = dbStopWatch2.realmGet$tTitle();
                if (realmGet$tTitle != null) {
                    Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.tTitleIndex, createRow, realmGet$tTitle, false);
                }
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.startTimeIndex, createRow, dbStopWatch2.realmGet$startTime(), false);
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.endTimeIndex, createRow, dbStopWatch2.realmGet$endTime(), false);
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.ellIndex, createRow, dbStopWatch2.realmGet$ell(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, DbStopWatch dbStopWatch, Map<RealmModel, Long> map) {
        if (dbStopWatch instanceof RealmObjectProxy) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) dbStopWatch;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getIndex();
            }
        }
        Table table = realm.getTable(DbStopWatch.class);
        long nativePtr = table.getNativePtr();
        DbStopWatchColumnInfo dbStopWatchColumnInfo = (DbStopWatchColumnInfo) realm.schema.getColumnInfo(DbStopWatch.class);
        long createRow = OsObject.createRow(table);
        map.put(dbStopWatch, Long.valueOf(createRow));
        DbStopWatch dbStopWatch2 = dbStopWatch;
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.PSEQIndex, createRow, dbStopWatch2.realmGet$PSEQ(), false);
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.CGSEQIndex, createRow, dbStopWatch2.realmGet$CGSEQ(), false);
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.TSEQIndex, createRow, dbStopWatch2.realmGet$TSEQ(), false);
        String realmGet$pTitle = dbStopWatch2.realmGet$pTitle();
        if (realmGet$pTitle != null) {
            Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.pTitleIndex, createRow, realmGet$pTitle, false);
        } else {
            Table.nativeSetNull(nativePtr, dbStopWatchColumnInfo.pTitleIndex, createRow, false);
        }
        String realmGet$cgTitle = dbStopWatch2.realmGet$cgTitle();
        if (realmGet$cgTitle != null) {
            Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.cgTitleIndex, createRow, realmGet$cgTitle, false);
        } else {
            Table.nativeSetNull(nativePtr, dbStopWatchColumnInfo.cgTitleIndex, createRow, false);
        }
        String realmGet$tTitle = dbStopWatch2.realmGet$tTitle();
        if (realmGet$tTitle != null) {
            Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.tTitleIndex, createRow, realmGet$tTitle, false);
        } else {
            Table.nativeSetNull(nativePtr, dbStopWatchColumnInfo.tTitleIndex, createRow, false);
        }
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.startTimeIndex, createRow, dbStopWatch2.realmGet$startTime(), false);
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.endTimeIndex, createRow, dbStopWatch2.realmGet$endTime(), false);
        Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.ellIndex, createRow, dbStopWatch2.realmGet$ell(), false);
        return createRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(DbStopWatch.class);
        long nativePtr = table.getNativePtr();
        DbStopWatchColumnInfo dbStopWatchColumnInfo = (DbStopWatchColumnInfo) realm.schema.getColumnInfo(DbStopWatch.class);
        while (it.hasNext()) {
            DbStopWatch dbStopWatch = (DbStopWatch) it.next();
            if (!map.containsKey(dbStopWatch)) {
                if (dbStopWatch instanceof RealmObjectProxy) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) dbStopWatch;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(dbStopWatch, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getIndex()));
                    }
                }
                long createRow = OsObject.createRow(table);
                map.put(dbStopWatch, Long.valueOf(createRow));
                DbStopWatch dbStopWatch2 = dbStopWatch;
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.PSEQIndex, createRow, dbStopWatch2.realmGet$PSEQ(), false);
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.CGSEQIndex, createRow, dbStopWatch2.realmGet$CGSEQ(), false);
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.TSEQIndex, createRow, dbStopWatch2.realmGet$TSEQ(), false);
                String realmGet$pTitle = dbStopWatch2.realmGet$pTitle();
                if (realmGet$pTitle != null) {
                    Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.pTitleIndex, createRow, realmGet$pTitle, false);
                } else {
                    Table.nativeSetNull(nativePtr, dbStopWatchColumnInfo.pTitleIndex, createRow, false);
                }
                String realmGet$cgTitle = dbStopWatch2.realmGet$cgTitle();
                if (realmGet$cgTitle != null) {
                    Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.cgTitleIndex, createRow, realmGet$cgTitle, false);
                } else {
                    Table.nativeSetNull(nativePtr, dbStopWatchColumnInfo.cgTitleIndex, createRow, false);
                }
                String realmGet$tTitle = dbStopWatch2.realmGet$tTitle();
                if (realmGet$tTitle != null) {
                    Table.nativeSetString(nativePtr, dbStopWatchColumnInfo.tTitleIndex, createRow, realmGet$tTitle, false);
                } else {
                    Table.nativeSetNull(nativePtr, dbStopWatchColumnInfo.tTitleIndex, createRow, false);
                }
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.startTimeIndex, createRow, dbStopWatch2.realmGet$startTime(), false);
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.endTimeIndex, createRow, dbStopWatch2.realmGet$endTime(), false);
                Table.nativeSetLong(nativePtr, dbStopWatchColumnInfo.ellIndex, createRow, dbStopWatch2.realmGet$ell(), false);
            }
        }
    }

    public static DbStopWatch createDetachedCopy(DbStopWatch dbStopWatch, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        DbStopWatch dbStopWatch2;
        if (i > i2 || dbStopWatch == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(dbStopWatch);
        if (cacheData == null) {
            dbStopWatch2 = new DbStopWatch();
            map.put(dbStopWatch, new RealmObjectProxy.CacheData<>(i, dbStopWatch2));
        } else if (i >= cacheData.minDepth) {
            return (DbStopWatch) cacheData.object;
        } else {
            dbStopWatch2 = (DbStopWatch) cacheData.object;
            cacheData.minDepth = i;
        }
        DbStopWatch dbStopWatch3 = dbStopWatch2;
        DbStopWatch dbStopWatch4 = dbStopWatch;
        dbStopWatch3.realmSet$PSEQ(dbStopWatch4.realmGet$PSEQ());
        dbStopWatch3.realmSet$CGSEQ(dbStopWatch4.realmGet$CGSEQ());
        dbStopWatch3.realmSet$TSEQ(dbStopWatch4.realmGet$TSEQ());
        dbStopWatch3.realmSet$pTitle(dbStopWatch4.realmGet$pTitle());
        dbStopWatch3.realmSet$cgTitle(dbStopWatch4.realmGet$cgTitle());
        dbStopWatch3.realmSet$tTitle(dbStopWatch4.realmGet$tTitle());
        dbStopWatch3.realmSet$startTime(dbStopWatch4.realmGet$startTime());
        dbStopWatch3.realmSet$endTime(dbStopWatch4.realmGet$endTime());
        dbStopWatch3.realmSet$ell(dbStopWatch4.realmGet$ell());
        return dbStopWatch2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("DbStopWatch = proxy[");
        sb.append("{PSEQ:");
        sb.append(realmGet$PSEQ());
        sb.append("}");
        sb.append(",");
        sb.append("{CGSEQ:");
        sb.append(realmGet$CGSEQ());
        sb.append("}");
        sb.append(",");
        sb.append("{TSEQ:");
        sb.append(realmGet$TSEQ());
        sb.append("}");
        sb.append(",");
        sb.append("{pTitle:");
        sb.append(realmGet$pTitle() != null ? realmGet$pTitle() : "null");
        sb.append("}");
        sb.append(",");
        sb.append("{cgTitle:");
        sb.append(realmGet$cgTitle() != null ? realmGet$cgTitle() : "null");
        sb.append("}");
        sb.append(",");
        sb.append("{tTitle:");
        sb.append(realmGet$tTitle() != null ? realmGet$tTitle() : "null");
        sb.append("}");
        sb.append(",");
        sb.append("{startTime:");
        sb.append(realmGet$startTime());
        sb.append("}");
        sb.append(",");
        sb.append("{endTime:");
        sb.append(realmGet$endTime());
        sb.append("}");
        sb.append(",");
        sb.append("{ell:");
        sb.append(realmGet$ell());
        sb.append("}");
        sb.append("]");
        return sb.toString();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    public int hashCode() {
        String path = this.proxyState.getRealm$realm().getPath();
        String name = this.proxyState.getRow$realm().getTable().getName();
        long index = this.proxyState.getRow$realm().getIndex();
        int i = 0;
        int hashCode = (527 + (path != null ? path.hashCode() : 0)) * 31;
        if (name != null) {
            i = name.hashCode();
        }
        return ((hashCode + i) * 31) + ((int) (index ^ (index >>> 32)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DbStopWatchRealmProxy dbStopWatchRealmProxy = (DbStopWatchRealmProxy) obj;
        String path = this.proxyState.getRealm$realm().getPath();
        String path2 = dbStopWatchRealmProxy.proxyState.getRealm$realm().getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = dbStopWatchRealmProxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getIndex() == dbStopWatchRealmProxy.proxyState.getRow$realm().getIndex();
        }
        return false;
    }
}
