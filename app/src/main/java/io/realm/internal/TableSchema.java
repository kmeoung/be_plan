package io.realm.internal;

import io.realm.RealmFieldType;

/* loaded from: classes.dex */
public interface TableSchema {
    long addColumn(RealmFieldType realmFieldType, String str);

    void removeColumn(long j);

    void renameColumn(long j, String str);
}
