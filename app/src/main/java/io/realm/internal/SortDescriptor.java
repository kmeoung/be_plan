package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.Sort;
import io.realm.internal.fields.FieldDescriptor;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@KeepMember
/* loaded from: classes.dex */
public class SortDescriptor {
    private final boolean[] ascendings;
    private final long[][] columnIndices;
    private final Table table;
    static final Set<RealmFieldType> SORT_VALID_FIELD_TYPES = Collections.unmodifiableSet(new HashSet(Arrays.asList(RealmFieldType.BOOLEAN, RealmFieldType.INTEGER, RealmFieldType.FLOAT, RealmFieldType.DOUBLE, RealmFieldType.STRING, RealmFieldType.DATE)));
    static final Set<RealmFieldType> DISTINCT_VALID_FIELD_TYPES = Collections.unmodifiableSet(new HashSet(Arrays.asList(RealmFieldType.BOOLEAN, RealmFieldType.INTEGER, RealmFieldType.STRING, RealmFieldType.DATE)));

    public static SortDescriptor getInstanceForSort(FieldDescriptor.SchemaProxy schemaProxy, Table table, String str, Sort sort) {
        return getInstanceForSort(schemaProxy, table, new String[]{str}, new Sort[]{sort});
    }

    public static SortDescriptor getInstanceForSort(FieldDescriptor.SchemaProxy schemaProxy, Table table, String[] strArr, Sort[] sortArr) {
        if (sortArr == null || sortArr.length == 0) {
            throw new IllegalArgumentException("You must provide at least one sort order.");
        } else if (strArr.length == sortArr.length) {
            return getInstance(schemaProxy, table, strArr, sortArr, FieldDescriptor.OBJECT_LINK_FIELD_TYPE, SORT_VALID_FIELD_TYPES, "Sort is not supported");
        } else {
            throw new IllegalArgumentException("Number of fields and sort orders do not match.");
        }
    }

    public static SortDescriptor getInstanceForDistinct(FieldDescriptor.SchemaProxy schemaProxy, Table table, String str) {
        return getInstanceForDistinct(schemaProxy, table, new String[]{str});
    }

    public static SortDescriptor getInstanceForDistinct(FieldDescriptor.SchemaProxy schemaProxy, Table table, String[] strArr) {
        return getInstance(schemaProxy, table, strArr, null, FieldDescriptor.NO_LINK_FIELD_TYPE, DISTINCT_VALID_FIELD_TYPES, "Distinct is not supported");
    }

    private static SortDescriptor getInstance(FieldDescriptor.SchemaProxy schemaProxy, Table table, String[] strArr, Sort[] sortArr, Set<RealmFieldType> set, Set<RealmFieldType> set2, String str) {
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("You must provide at least one field name.");
        }
        long[][] jArr = new long[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            FieldDescriptor createFieldDescriptor = FieldDescriptor.createFieldDescriptor(schemaProxy, table, strArr[i], set, null);
            checkFieldType(createFieldDescriptor, set2, str, strArr[i]);
            jArr[i] = createFieldDescriptor.getColumnIndices();
        }
        return new SortDescriptor(table, jArr, sortArr);
    }

    static SortDescriptor getTestInstance(Table table, long[] jArr) {
        return new SortDescriptor(table, new long[][]{jArr}, null);
    }

    private static void checkFieldType(FieldDescriptor fieldDescriptor, Set<RealmFieldType> set, String str, String str2) {
        if (!set.contains(fieldDescriptor.getFinalColumnType())) {
            throw new IllegalArgumentException(String.format(Locale.US, "%s on '%s' field '%s' in '%s'.", str, fieldDescriptor.getFinalColumnType(), fieldDescriptor.getFinalColumnName(), str2));
        }
    }

    private SortDescriptor(Table table, long[][] jArr, Sort[] sortArr) {
        this.table = table;
        this.columnIndices = jArr;
        if (sortArr != null) {
            this.ascendings = new boolean[sortArr.length];
            for (int i = 0; i < sortArr.length; i++) {
                this.ascendings[i] = sortArr[i].getValue();
            }
            return;
        }
        this.ascendings = null;
    }

    @KeepMember
    long[][] getColumnIndices() {
        return this.columnIndices;
    }

    @KeepMember
    boolean[] getAscendings() {
        return this.ascendings;
    }

    @KeepMember
    private long getTablePtr() {
        return this.table.getNativePtr();
    }
}
