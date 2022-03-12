package io.realm;

import io.realm.internal.ColumnIndices;
import io.realm.internal.ColumnInfo;
import io.realm.internal.Table;
import io.realm.internal.Util;
import io.realm.internal.util.Pair;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class RealmSchema {
    static final String EMPTY_STRING_MSG = "Null or empty class names are not allowed";
    private ColumnIndices columnIndices;
    private final BaseRealm realm;
    private final Map<String, Table> dynamicClassToTable = new HashMap();
    private final Map<Class<? extends RealmModel>, Table> classToTable = new HashMap();
    private final Map<Class<? extends RealmModel>, RealmObjectSchema> classToSchema = new HashMap();
    private final Map<String, RealmObjectSchema> dynamicClassToSchema = new HashMap();

    @Deprecated
    public void close() {
    }

    public RealmSchema(BaseRealm baseRealm) {
        this.realm = baseRealm;
    }

    public RealmObjectSchema get(String str) {
        checkEmpty(str, EMPTY_STRING_MSG);
        String tableNameForClass = Table.getTableNameForClass(str);
        if (!this.realm.getSharedRealm().hasTable(tableNameForClass)) {
            return null;
        }
        return new RealmObjectSchema(this.realm, this, this.realm.getSharedRealm().getTable(tableNameForClass));
    }

    public Set<RealmObjectSchema> getAll() {
        int size = (int) this.realm.getSharedRealm().size();
        LinkedHashSet linkedHashSet = new LinkedHashSet(size);
        for (int i = 0; i < size; i++) {
            String tableName = this.realm.getSharedRealm().getTableName(i);
            if (Table.isModelTable(tableName)) {
                linkedHashSet.add(new RealmObjectSchema(this.realm, this, this.realm.getSharedRealm().getTable(tableName)));
            }
        }
        return linkedHashSet;
    }

    public RealmObjectSchema create(String str) {
        checkEmpty(str, EMPTY_STRING_MSG);
        String tableNameForClass = Table.getTableNameForClass(str);
        if (tableNameForClass.length() <= 56) {
            return new RealmObjectSchema(this.realm, this, this.realm.getSharedRealm().createTable(tableNameForClass));
        }
        throw new IllegalArgumentException("Class name is too long. Limit is 56 characters: " + str.length());
    }

    public void remove(String str) {
        this.realm.checkNotInSync();
        checkEmpty(str, EMPTY_STRING_MSG);
        String tableNameForClass = Table.getTableNameForClass(str);
        checkHasTable(str, "Cannot remove class because it is not in this Realm: " + str);
        Table table = getTable(str);
        if (table.hasPrimaryKey()) {
            table.setPrimaryKey((String) null);
        }
        this.realm.getSharedRealm().removeTable(tableNameForClass);
    }

    public RealmObjectSchema rename(String str, String str2) {
        String str3;
        this.realm.checkNotInSync();
        checkEmpty(str, "Class names cannot be empty or null");
        checkEmpty(str2, "Class names cannot be empty or null");
        String tableNameForClass = Table.getTableNameForClass(str);
        String tableNameForClass2 = Table.getTableNameForClass(str2);
        checkHasTable(str, "Cannot rename class because it doesn't exist in this Realm: " + str);
        if (this.realm.getSharedRealm().hasTable(tableNameForClass2)) {
            throw new IllegalArgumentException(str + " cannot be renamed because the new class already exists: " + str2);
        }
        Table table = getTable(str);
        if (table.hasPrimaryKey()) {
            str3 = table.getColumnName(table.getPrimaryKey());
            table.setPrimaryKey((String) null);
        } else {
            str3 = null;
        }
        this.realm.getSharedRealm().renameTable(tableNameForClass, tableNameForClass2);
        Table table2 = this.realm.getSharedRealm().getTable(tableNameForClass2);
        if (str3 != null) {
            table2.setPrimaryKey(str3);
        }
        return new RealmObjectSchema(this.realm, this, table2);
    }

    public boolean contains(String str) {
        return this.realm.getSharedRealm().hasTable(Table.getTableNameForClass(str));
    }

    private void checkEmpty(String str, String str2) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(str2);
        }
    }

    private void checkHasTable(String str, String str2) {
        if (!this.realm.getSharedRealm().hasTable(Table.getTableNameForClass(str))) {
            throw new IllegalArgumentException(str2);
        }
    }

    public Table getTable(String str) {
        String tableNameForClass = Table.getTableNameForClass(str);
        Table table = this.dynamicClassToTable.get(tableNameForClass);
        if (table != null) {
            return table;
        }
        Table table2 = this.realm.getSharedRealm().getTable(tableNameForClass);
        this.dynamicClassToTable.put(tableNameForClass, table2);
        return table2;
    }

    public Table getTable(Class<? extends RealmModel> cls) {
        Table table = this.classToTable.get(cls);
        if (table != null) {
            return table;
        }
        Class<? extends RealmModel> originalModelClass = Util.getOriginalModelClass(cls);
        if (isProxyClass(originalModelClass, cls)) {
            table = this.classToTable.get(originalModelClass);
        }
        if (table == null) {
            table = this.realm.getSharedRealm().getTable(this.realm.getConfiguration().getSchemaMediator().getTableName(originalModelClass));
            this.classToTable.put(originalModelClass, table);
        }
        if (isProxyClass(originalModelClass, cls)) {
            this.classToTable.put(cls, table);
        }
        return table;
    }

    public RealmObjectSchema getSchemaForClass(Class<? extends RealmModel> cls) {
        RealmObjectSchema realmObjectSchema = this.classToSchema.get(cls);
        if (realmObjectSchema != null) {
            return realmObjectSchema;
        }
        Class<? extends RealmModel> originalModelClass = Util.getOriginalModelClass(cls);
        if (isProxyClass(originalModelClass, cls)) {
            realmObjectSchema = this.classToSchema.get(originalModelClass);
        }
        if (realmObjectSchema == null) {
            RealmObjectSchema realmObjectSchema2 = new RealmObjectSchema(this.realm, this, getTable(cls), getColumnInfo(originalModelClass));
            this.classToSchema.put(originalModelClass, realmObjectSchema2);
            realmObjectSchema = realmObjectSchema2;
        }
        if (isProxyClass(originalModelClass, cls)) {
            this.classToSchema.put(cls, realmObjectSchema);
        }
        return realmObjectSchema;
    }

    public RealmObjectSchema getSchemaForClass(String str) {
        String tableNameForClass = Table.getTableNameForClass(str);
        RealmObjectSchema realmObjectSchema = this.dynamicClassToSchema.get(tableNameForClass);
        if (realmObjectSchema != null) {
            return realmObjectSchema;
        }
        if (!this.realm.getSharedRealm().hasTable(tableNameForClass)) {
            throw new IllegalArgumentException("The class " + str + " doesn't exist in this Realm.");
        }
        RealmObjectSchema realmObjectSchema2 = new RealmObjectSchema(this.realm, this, this.realm.getSharedRealm().getTable(tableNameForClass));
        this.dynamicClassToSchema.put(tableNameForClass, realmObjectSchema2);
        return realmObjectSchema2;
    }

    public final void setInitialColumnIndices(ColumnIndices columnIndices) {
        if (this.columnIndices != null) {
            throw new IllegalStateException("An instance of ColumnIndices is already set.");
        }
        this.columnIndices = new ColumnIndices(columnIndices, true);
    }

    public final void setInitialColumnIndices(long j, Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo> map) {
        if (this.columnIndices != null) {
            throw new IllegalStateException("An instance of ColumnIndices is already set.");
        }
        this.columnIndices = new ColumnIndices(j, map);
    }

    public void updateColumnIndices(ColumnIndices columnIndices) {
        this.columnIndices.copyFrom(columnIndices);
    }

    final boolean isProxyClass(Class<? extends RealmModel> cls, Class<? extends RealmModel> cls2) {
        return cls.equals(cls2);
    }

    public final ColumnIndices getImmutableColumnIndicies() {
        checkIndices();
        return new ColumnIndices(this.columnIndices, false);
    }

    public final boolean haveColumnInfo() {
        return this.columnIndices != null;
    }

    public final long getSchemaVersion() {
        checkIndices();
        return this.columnIndices.getSchemaVersion();
    }

    public final ColumnInfo getColumnInfo(Class<? extends RealmModel> cls) {
        checkIndices();
        return this.columnIndices.getColumnInfo(cls);
    }

    public final ColumnInfo getColumnInfo(String str) {
        checkIndices();
        return this.columnIndices.getColumnInfo(str);
    }

    private void checkIndices() {
        if (!haveColumnInfo()) {
            throw new IllegalStateException("Attempt to use column index before set.");
        }
    }
}
