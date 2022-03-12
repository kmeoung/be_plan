package io.realm;

import io.realm.internal.ColumnInfo;
import io.realm.internal.Table;
import io.realm.internal.fields.FieldDescriptor;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class RealmObjectSchema {
    private static final Map<Class<?>, FieldMetaData> SUPPORTED_LINKED_FIELDS;
    private static final Map<Class<?>, FieldMetaData> SUPPORTED_SIMPLE_FIELDS;
    private final ColumnInfo columnInfo;
    private final BaseRealm realm;
    private final RealmSchema schema;
    private final Table table;

    /* loaded from: classes.dex */
    public interface Function {
        void apply(DynamicRealmObject dynamicRealmObject);
    }

    @Deprecated
    public void close() {
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(String.class, new FieldMetaData(RealmFieldType.STRING, true));
        hashMap.put(Short.TYPE, new FieldMetaData(RealmFieldType.INTEGER, false));
        hashMap.put(Short.class, new FieldMetaData(RealmFieldType.INTEGER, true));
        hashMap.put(Integer.TYPE, new FieldMetaData(RealmFieldType.INTEGER, false));
        hashMap.put(Integer.class, new FieldMetaData(RealmFieldType.INTEGER, true));
        hashMap.put(Long.TYPE, new FieldMetaData(RealmFieldType.INTEGER, false));
        hashMap.put(Long.class, new FieldMetaData(RealmFieldType.INTEGER, true));
        hashMap.put(Float.TYPE, new FieldMetaData(RealmFieldType.FLOAT, false));
        hashMap.put(Float.class, new FieldMetaData(RealmFieldType.FLOAT, true));
        hashMap.put(Double.TYPE, new FieldMetaData(RealmFieldType.DOUBLE, false));
        hashMap.put(Double.class, new FieldMetaData(RealmFieldType.DOUBLE, true));
        hashMap.put(Boolean.TYPE, new FieldMetaData(RealmFieldType.BOOLEAN, false));
        hashMap.put(Boolean.class, new FieldMetaData(RealmFieldType.BOOLEAN, true));
        hashMap.put(Byte.TYPE, new FieldMetaData(RealmFieldType.INTEGER, false));
        hashMap.put(Byte.class, new FieldMetaData(RealmFieldType.INTEGER, true));
        hashMap.put(byte[].class, new FieldMetaData(RealmFieldType.BINARY, true));
        hashMap.put(Date.class, new FieldMetaData(RealmFieldType.DATE, true));
        SUPPORTED_SIMPLE_FIELDS = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(RealmObject.class, new FieldMetaData(RealmFieldType.OBJECT, false));
        hashMap2.put(RealmList.class, new FieldMetaData(RealmFieldType.LIST, false));
        SUPPORTED_LINKED_FIELDS = Collections.unmodifiableMap(hashMap2);
    }

    public RealmObjectSchema(BaseRealm baseRealm, RealmSchema realmSchema, Table table) {
        this(baseRealm, realmSchema, table, new DynamicColumnIndices(table));
    }

    public RealmObjectSchema(BaseRealm baseRealm, RealmSchema realmSchema, Table table, ColumnInfo columnInfo) {
        this.schema = realmSchema;
        this.realm = baseRealm;
        this.table = table;
        this.columnInfo = columnInfo;
    }

    public String getClassName() {
        return this.table.getClassName();
    }

    public RealmObjectSchema setClassName(String str) {
        String str2;
        this.realm.checkNotInSync();
        checkEmpty(str);
        String tableNameForClass = Table.getTableNameForClass(str);
        if (tableNameForClass.length() > 56) {
            throw new IllegalArgumentException("Class name is too long. Limit is 56 characters: '" + str + "' (" + Integer.toString(str.length()) + ")");
        } else if (this.realm.sharedRealm.hasTable(tableNameForClass)) {
            throw new IllegalArgumentException("Class already exists: " + str);
        } else {
            String str3 = null;
            if (this.table.hasPrimaryKey()) {
                str2 = this.table.getName();
                String primaryKey = getPrimaryKey();
                this.table.setPrimaryKey((String) null);
                str3 = primaryKey;
            } else {
                str2 = null;
            }
            this.realm.sharedRealm.renameTable(this.table.getName(), tableNameForClass);
            if (str3 != null && !str3.isEmpty()) {
                try {
                    this.table.setPrimaryKey(str3);
                } catch (Exception e) {
                    this.realm.sharedRealm.renameTable(this.table.getName(), str2);
                    throw e;
                }
            }
            return this;
        }
    }

    public RealmObjectSchema addField(String str, Class<?> cls, FieldAttribute... fieldAttributeArr) {
        FieldMetaData fieldMetaData = SUPPORTED_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData != null) {
            checkNewFieldName(str);
            boolean z = fieldMetaData.defaultNullable;
            if (containsAttribute(fieldAttributeArr, FieldAttribute.REQUIRED)) {
                z = false;
            }
            long addColumn = this.table.addColumn(fieldMetaData.realmType, str, z);
            try {
                addModifiers(str, fieldAttributeArr);
                return this;
            } catch (Exception e) {
                this.table.removeColumn(addColumn);
                throw e;
            }
        } else if (SUPPORTED_LINKED_FIELDS.containsKey(cls)) {
            throw new IllegalArgumentException("Use addRealmObjectField() instead to add fields that link to other RealmObjects: " + str);
        } else {
            throw new IllegalArgumentException(String.format(Locale.US, "Realm doesn't support this field type: %s(%s)", str, cls));
        }
    }

    public RealmObjectSchema addRealmObjectField(String str, RealmObjectSchema realmObjectSchema) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
        this.table.addColumnLink(RealmFieldType.OBJECT, str, this.realm.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    public RealmObjectSchema addRealmListField(String str, RealmObjectSchema realmObjectSchema) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
        this.table.addColumnLink(RealmFieldType.LIST, str, this.realm.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    public RealmObjectSchema removeField(String str) {
        this.realm.checkNotInSync();
        checkLegalName(str);
        if (!hasField(str)) {
            throw new IllegalStateException(str + " does not exist.");
        }
        long columnIndex = getColumnIndex(str);
        if (this.table.getPrimaryKey() == columnIndex) {
            this.table.setPrimaryKey((String) null);
        }
        this.table.removeColumn(columnIndex);
        return this;
    }

    public RealmObjectSchema renameField(String str, String str2) {
        this.realm.checkNotInSync();
        checkLegalName(str);
        checkFieldExists(str);
        checkLegalName(str2);
        checkFieldNameIsAvailable(str2);
        this.table.renameColumn(getColumnIndex(str), str2);
        return this;
    }

    public boolean hasField(String str) {
        return this.table.getColumnIndex(str) != -1;
    }

    public RealmObjectSchema addIndex(String str) {
        checkLegalName(str);
        checkFieldExists(str);
        long columnIndex = getColumnIndex(str);
        if (this.table.hasSearchIndex(columnIndex)) {
            throw new IllegalStateException(str + " already has an index.");
        }
        this.table.addSearchIndex(columnIndex);
        return this;
    }

    public boolean hasIndex(String str) {
        checkLegalName(str);
        checkFieldExists(str);
        return this.table.hasSearchIndex(this.table.getColumnIndex(str));
    }

    public RealmObjectSchema removeIndex(String str) {
        this.realm.checkNotInSync();
        checkLegalName(str);
        checkFieldExists(str);
        long columnIndex = getColumnIndex(str);
        if (!this.table.hasSearchIndex(columnIndex)) {
            throw new IllegalStateException("Field is not indexed: " + str);
        }
        this.table.removeSearchIndex(columnIndex);
        return this;
    }

    public RealmObjectSchema addPrimaryKey(String str) {
        checkLegalName(str);
        checkFieldExists(str);
        if (this.table.hasPrimaryKey()) {
            throw new IllegalStateException("A primary key is already defined");
        }
        this.table.setPrimaryKey(str);
        long columnIndex = getColumnIndex(str);
        if (!this.table.hasSearchIndex(columnIndex)) {
            this.table.addSearchIndex(columnIndex);
        }
        return this;
    }

    public RealmObjectSchema removePrimaryKey() {
        this.realm.checkNotInSync();
        if (!this.table.hasPrimaryKey()) {
            throw new IllegalStateException(getClassName() + " doesn't have a primary key.");
        }
        long primaryKey = this.table.getPrimaryKey();
        if (this.table.hasSearchIndex(primaryKey)) {
            this.table.removeSearchIndex(primaryKey);
        }
        this.table.setPrimaryKey("");
        return this;
    }

    public RealmObjectSchema setRequired(String str, boolean z) {
        long columnIndex = this.table.getColumnIndex(str);
        boolean isRequired = isRequired(str);
        RealmFieldType columnType = this.table.getColumnType(columnIndex);
        if (columnType == RealmFieldType.OBJECT) {
            throw new IllegalArgumentException("Cannot modify the required state for RealmObject references: " + str);
        } else if (columnType == RealmFieldType.LIST) {
            throw new IllegalArgumentException("Cannot modify the required state for RealmList references: " + str);
        } else if (z && isRequired) {
            throw new IllegalStateException("Field is already required: " + str);
        } else if (z || isRequired) {
            if (z) {
                this.table.convertColumnToNotNullable(columnIndex);
            } else {
                this.table.convertColumnToNullable(columnIndex);
            }
            return this;
        } else {
            throw new IllegalStateException("Field is already nullable: " + str);
        }
    }

    public RealmObjectSchema setNullable(String str, boolean z) {
        setRequired(str, !z);
        return this;
    }

    public boolean isRequired(String str) {
        return !this.table.isColumnNullable(getColumnIndex(str));
    }

    public boolean isNullable(String str) {
        return this.table.isColumnNullable(getColumnIndex(str));
    }

    public boolean isPrimaryKey(String str) {
        return getColumnIndex(str) == this.table.getPrimaryKey();
    }

    public boolean hasPrimaryKey() {
        return this.table.hasPrimaryKey();
    }

    public String getPrimaryKey() {
        if (this.table.hasPrimaryKey()) {
            return this.table.getColumnName(this.table.getPrimaryKey());
        }
        throw new IllegalStateException(getClassName() + " doesn't have a primary key.");
    }

    public Set<String> getFieldNames() {
        int columnCount = (int) this.table.getColumnCount();
        LinkedHashSet linkedHashSet = new LinkedHashSet(columnCount);
        for (int i = 0; i < columnCount; i++) {
            linkedHashSet.add(this.table.getColumnName(i));
        }
        return linkedHashSet;
    }

    public RealmObjectSchema transform(Function function) {
        if (function != null) {
            long size = this.table.size();
            for (long j = 0; j < size; j++) {
                function.apply(new DynamicRealmObject(this.realm, this.table.getCheckedRow(j)));
            }
        }
        return this;
    }

    public RealmFieldType getFieldType(String str) {
        return this.table.getColumnType(getColumnIndex(str));
    }

    public final FieldDescriptor getColumnIndices(String str, RealmFieldType... realmFieldTypeArr) {
        return FieldDescriptor.createStandardFieldDescriptor(getSchemaConnector(), getTable(), str, realmFieldTypeArr);
    }

    RealmObjectSchema add(String str, RealmFieldType realmFieldType, boolean z, boolean z2, boolean z3) {
        long addColumn = this.table.addColumn(realmFieldType, str, !z3);
        if (z2) {
            this.table.addSearchIndex(addColumn);
        }
        if (z) {
            this.table.setPrimaryKey(str);
        }
        return this;
    }

    RealmObjectSchema add(String str, RealmFieldType realmFieldType, RealmObjectSchema realmObjectSchema) {
        this.table.addColumnLink(realmFieldType, str, this.realm.getSharedRealm().getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    public long getAndCheckFieldIndex(String str) {
        long columnIndex = this.columnInfo.getColumnIndex(str);
        if (columnIndex >= 0) {
            return columnIndex;
        }
        throw new IllegalArgumentException("Field does not exist: " + str);
    }

    public Table getTable() {
        return this.table;
    }

    private SchemaConnector getSchemaConnector() {
        return new SchemaConnector(this.schema);
    }

    long getFieldIndex(String str) {
        return this.columnInfo.getColumnIndex(str);
    }

    private void addModifiers(String str, FieldAttribute[] fieldAttributeArr) {
        if (fieldAttributeArr != null) {
            boolean z = false;
            try {
                if (fieldAttributeArr.length > 0) {
                    if (containsAttribute(fieldAttributeArr, FieldAttribute.INDEXED)) {
                        addIndex(str);
                        z = true;
                    }
                    if (containsAttribute(fieldAttributeArr, FieldAttribute.PRIMARY_KEY)) {
                        addPrimaryKey(str);
                    }
                }
            } catch (Exception e) {
                long columnIndex = getColumnIndex(str);
                if (z) {
                    this.table.removeSearchIndex(columnIndex);
                }
                throw ((RuntimeException) e);
            }
        }
    }

    private boolean containsAttribute(FieldAttribute[] fieldAttributeArr, FieldAttribute fieldAttribute) {
        if (fieldAttributeArr == null || fieldAttributeArr.length == 0) {
            return false;
        }
        for (FieldAttribute fieldAttribute2 : fieldAttributeArr) {
            if (fieldAttribute2 == fieldAttribute) {
                return true;
            }
        }
        return false;
    }

    private void checkNewFieldName(String str) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
    }

    private void checkLegalName(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Field name can not be null or empty");
        } else if (str.contains(".")) {
            throw new IllegalArgumentException("Field name can not contain '.'");
        }
    }

    private void checkFieldNameIsAvailable(String str) {
        if (this.table.getColumnIndex(str) != -1) {
            throw new IllegalArgumentException("Field already exists in '" + getClassName() + "': " + str);
        }
    }

    private void checkFieldExists(String str) {
        if (this.table.getColumnIndex(str) == -1) {
            throw new IllegalArgumentException("Field name doesn't exist on object '" + getClassName() + "': " + str);
        }
    }

    private long getColumnIndex(String str) {
        long columnIndex = this.table.getColumnIndex(str);
        if (columnIndex != -1) {
            return columnIndex;
        }
        throw new IllegalArgumentException(String.format(Locale.US, "Field name '%s' does not exist on schema for '%s'", str, getClassName()));
    }

    private void checkEmpty(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Null or empty class names are not allowed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class DynamicColumnIndices extends ColumnInfo {
        private final Table table;

        DynamicColumnIndices(Table table) {
            super((ColumnInfo) null, false);
            this.table = table;
        }

        @Override // io.realm.internal.ColumnInfo
        public long getColumnIndex(String str) {
            return this.table.getColumnIndex(str);
        }

        @Override // io.realm.internal.ColumnInfo
        public RealmFieldType getColumnType(String str) {
            throw new UnsupportedOperationException("DynamicColumnIndices do not support 'getColumnType'");
        }

        @Override // io.realm.internal.ColumnInfo
        public String getLinkedTable(String str) {
            throw new UnsupportedOperationException("DynamicColumnIndices do not support 'getLinkedTable'");
        }

        @Override // io.realm.internal.ColumnInfo
        public void copyFrom(ColumnInfo columnInfo) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot be copied");
        }

        @Override // io.realm.internal.ColumnInfo
        public ColumnInfo copy(boolean z) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot be copied");
        }

        @Override // io.realm.internal.ColumnInfo
        protected void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot copy");
        }
    }

    /* loaded from: classes.dex */
    private static final class FieldMetaData {
        final boolean defaultNullable;
        final RealmFieldType realmType;

        FieldMetaData(RealmFieldType realmFieldType, boolean z) {
            this.realmType = realmFieldType;
            this.defaultNullable = z;
        }
    }
}
