package io.realm.internal;

import io.realm.RealmFieldType;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class ColumnInfo {
    private final Map<String, ColumnDetails> indicesMap;
    private final boolean mutable;

    public abstract ColumnInfo copy(boolean z);

    protected abstract void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2);

    /* loaded from: classes.dex */
    public static final class ColumnDetails {
        public final long columnIndex;
        public final RealmFieldType columnType;
        public final String linkTable;

        ColumnDetails(long j, RealmFieldType realmFieldType, String str) {
            this.columnIndex = j;
            this.columnType = realmFieldType;
            this.linkTable = str;
        }

        public String toString() {
            return "ColumnDetails[" + this.columnIndex + ", " + this.columnType + ", " + this.linkTable + "]";
        }
    }

    public ColumnInfo(int i) {
        this(i, true);
    }

    public ColumnInfo(ColumnInfo columnInfo, boolean z) {
        this(columnInfo == null ? 0 : columnInfo.indicesMap.size(), z);
        if (columnInfo != null) {
            this.indicesMap.putAll(columnInfo.indicesMap);
        }
    }

    private ColumnInfo(int i, boolean z) {
        this.indicesMap = new HashMap(i);
        this.mutable = z;
    }

    public final boolean isMutable() {
        return this.mutable;
    }

    public long getColumnIndex(String str) {
        ColumnDetails columnDetails = this.indicesMap.get(str);
        if (columnDetails == null) {
            return -1L;
        }
        return columnDetails.columnIndex;
    }

    public RealmFieldType getColumnType(String str) {
        ColumnDetails columnDetails = this.indicesMap.get(str);
        return columnDetails == null ? RealmFieldType.UNSUPPORTED_TABLE : columnDetails.columnType;
    }

    public String getLinkedTable(String str) {
        ColumnDetails columnDetails = this.indicesMap.get(str);
        if (columnDetails == null) {
            return null;
        }
        return columnDetails.linkTable;
    }

    public void copyFrom(ColumnInfo columnInfo) {
        if (!this.mutable) {
            throw new UnsupportedOperationException("Attempt to modify an immutable ColumnInfo");
        } else if (columnInfo == null) {
            throw new NullPointerException("Attempt to copy null ColumnInfo");
        } else {
            this.indicesMap.clear();
            this.indicesMap.putAll(columnInfo.indicesMap);
            copy(columnInfo, this);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ColumnInfo[");
        sb.append(this.mutable);
        sb.append(",");
        if (this.indicesMap != null) {
            boolean z = false;
            for (Map.Entry<String, ColumnDetails> entry : this.indicesMap.entrySet()) {
                if (z) {
                    sb.append(",");
                }
                sb.append(entry.getKey());
                sb.append("->");
                sb.append(entry.getValue());
                z = true;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public final long addColumnDetails(Table table, String str, RealmFieldType realmFieldType) {
        long columnIndex = table.getColumnIndex(str);
        if (columnIndex >= 0) {
            this.indicesMap.put(str, new ColumnDetails(columnIndex, realmFieldType, (realmFieldType == RealmFieldType.OBJECT || realmFieldType == RealmFieldType.LIST) ? table.getLinkTarget(columnIndex).getClassName() : null));
        }
        return columnIndex;
    }

    protected final void addBacklinkDetails(SharedRealm sharedRealm, String str, String str2, String str3) {
        this.indicesMap.put(str, new ColumnDetails(sharedRealm.getTable(Table.getTableNameForClass(str2)).getColumnIndex(str3), RealmFieldType.LINKING_OBJECTS, str2));
    }

    public Map<String, ColumnDetails> getIndicesMap() {
        return this.indicesMap;
    }
}
