package io.realm;

import io.realm.RealmModel;

/* loaded from: classes.dex */
public interface RealmObjectChangeListener<T extends RealmModel> {
    void onChange(T t, ObjectChangeSet objectChangeSet);
}
