package io.realm.internal.fields;

import io.realm.RealmFieldType;
import io.realm.internal.ColumnInfo;
import io.realm.internal.fields.FieldDescriptor;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes.dex */
public class CachedFieldDescriptor extends FieldDescriptor {
    private final String className;
    private final FieldDescriptor.SchemaProxy schema;

    public CachedFieldDescriptor(FieldDescriptor.SchemaProxy schemaProxy, String str, String str2, Set<RealmFieldType> set, Set<RealmFieldType> set2) {
        super(str2, set, set2);
        this.className = str;
        this.schema = schemaProxy;
    }

    @Override // io.realm.internal.fields.FieldDescriptor
    protected void compileFieldDescription(List<String> list) {
        int size = list.size();
        long[] jArr = new long[size];
        long[] jArr2 = new long[size];
        String str = null;
        RealmFieldType realmFieldType = null;
        String str2 = this.className;
        int i = 0;
        while (i < size) {
            str = list.get(i);
            if (str == null || str.length() <= 0) {
                throw new IllegalArgumentException("Invalid query: Field descriptor contains an empty field.  A field description may not begin with or contain adjacent periods ('.').");
            }
            ColumnInfo columnInfo = this.schema.getColumnInfo(str2);
            if (columnInfo == null) {
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid query: table '%s' not found in this schema.", str2));
            }
            long columnIndex = columnInfo.getColumnIndex(str);
            long j = 0;
            if (columnIndex < 0) {
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid query: field '%s' not found in table '%s'.", str, str2));
            }
            RealmFieldType columnType = columnInfo.getColumnType(str);
            if (i < size - 1) {
                verifyInternalColumnType(str2, str, columnType);
            }
            str2 = columnInfo.getLinkedTable(str);
            jArr[i] = columnIndex;
            if (columnType == RealmFieldType.LINKING_OBJECTS) {
                j = this.schema.getNativeTablePtr(str2);
            }
            jArr2[i] = j;
            i++;
            realmFieldType = columnType;
        }
        setCompilationResults(this.className, str, realmFieldType, jArr, jArr2);
    }
}
