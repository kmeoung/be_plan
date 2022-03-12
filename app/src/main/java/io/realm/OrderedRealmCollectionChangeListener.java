package io.realm;

/* loaded from: classes.dex */
public interface OrderedRealmCollectionChangeListener<T> {
    void onChange(T t, OrderedCollectionChangeSet orderedCollectionChangeSet);
}
