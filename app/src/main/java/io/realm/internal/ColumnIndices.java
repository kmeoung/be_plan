package io.realm.internal;

import io.realm.RealmModel;
import io.realm.internal.util.Pair;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class ColumnIndices {
    private final Map<Class<? extends RealmModel>, ColumnInfo> classes;
    private final Map<String, ColumnInfo> classesByName;
    private final Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo> classesToColumnInfo;
    private final boolean mutable;
    private long schemaVersion;

    public ColumnIndices(long j, Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo> map) {
        this(j, new HashMap(map), true);
        for (Map.Entry<Pair<Class<? extends RealmModel>, String>, ColumnInfo> entry : map.entrySet()) {
            ColumnInfo value = entry.getValue();
            if (this.mutable != value.isMutable()) {
                throw new IllegalArgumentException("ColumnInfo mutability does not match ColumnIndices");
            }
            Pair<Class<? extends RealmModel>, String> key = entry.getKey();
            this.classes.put(key.first, value);
            this.classesByName.put(key.second, value);
        }
    }

    public ColumnIndices(ColumnIndices columnIndices, boolean z) {
        this(columnIndices.schemaVersion, new HashMap(columnIndices.classesToColumnInfo.size()), z);
        for (Map.Entry<Pair<Class<? extends RealmModel>, String>, ColumnInfo> entry : columnIndices.classesToColumnInfo.entrySet()) {
            ColumnInfo copy = entry.getValue().copy(z);
            Pair<Class<? extends RealmModel>, String> key = entry.getKey();
            this.classes.put(key.first, copy);
            this.classesByName.put(key.second, copy);
            this.classesToColumnInfo.put(key, copy);
        }
    }

    private ColumnIndices(long j, Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo> map, boolean z) {
        this.schemaVersion = j;
        this.classesToColumnInfo = map;
        this.mutable = z;
        this.classes = new HashMap(map.size());
        this.classesByName = new HashMap(map.size());
    }

    public long getSchemaVersion() {
        return this.schemaVersion;
    }

    public ColumnInfo getColumnInfo(Class<? extends RealmModel> cls) {
        return this.classes.get(cls);
    }

    public ColumnInfo getColumnInfo(String str) {
        return this.classesByName.get(str);
    }

    @Deprecated
    public long getColumnIndex(Class<? extends RealmModel> cls, String str) {
        ColumnInfo columnInfo = getColumnInfo(cls);
        if (columnInfo == null) {
            return -1L;
        }
        return columnInfo.getColumnIndex(str);
    }

    public void copyFrom(ColumnIndices columnIndices) {
        if (!this.mutable) {
            throw new UnsupportedOperationException("Attempt to modify immutable cache");
        }
        for (Map.Entry<String, ColumnInfo> entry : this.classesByName.entrySet()) {
            ColumnInfo columnInfo = columnIndices.classesByName.get(entry.getKey());
            if (columnInfo == null) {
                throw new IllegalStateException("Failed to copy ColumnIndices cache for class: " + entry.getKey());
            }
            entry.getValue().copyFrom(columnInfo);
        }
        this.schemaVersion = columnIndices.schemaVersion;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ColumnIndices[");
        sb.append(this.schemaVersion);
        sb.append(",");
        sb.append(this.mutable);
        sb.append(",");
        if (this.classes != null) {
            boolean z = false;
            for (Map.Entry<String, ColumnInfo> entry : this.classesByName.entrySet()) {
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
}
