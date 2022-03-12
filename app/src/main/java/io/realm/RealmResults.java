package io.realm;

import android.annotation.SuppressLint;
import io.realm.RealmModel;
import io.realm.internal.CheckedRow;
import io.realm.internal.Row;
import io.realm.internal.SortDescriptor;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import rx.Observable;

/* loaded from: classes.dex */
public class RealmResults<E extends RealmModel> extends OrderedRealmCollectionImpl<E> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.OrderedRealmCollectionImpl
    @Deprecated
    public /* bridge */ /* synthetic */ void add(int i, RealmModel realmModel) {
        super.add(i, (int) realmModel);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.OrderedRealmCollectionImpl
    @Deprecated
    public /* bridge */ /* synthetic */ boolean add(RealmModel realmModel) {
        return super.add((RealmResults<E>) realmModel);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean addAll(int i, Collection collection) {
        return super.addAll(i, collection);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean addAll(Collection collection) {
        return super.addAll(collection);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ double average(String str) {
        return super.average(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ OrderedRealmCollectionSnapshot createSnapshot() {
        return super.createSnapshot();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ boolean deleteAllFromRealm() {
        return super.deleteAllFromRealm();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ boolean deleteFirstFromRealm() {
        return super.deleteFirstFromRealm();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ void deleteFromRealm(int i) {
        super.deleteFromRealm(i);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ boolean deleteLastFromRealm() {
        return super.deleteLastFromRealm();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmModel first() {
        return super.first();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmModel first(RealmModel realmModel) {
        return super.first(realmModel);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ RealmModel get(int i) {
        return super.get(i);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection, io.realm.internal.ManagableObject
    public /* bridge */ /* synthetic */ boolean isManaged() {
        return super.isManaged();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection, io.realm.internal.ManagableObject
    public /* bridge */ /* synthetic */ boolean isValid() {
        return super.isValid();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmModel last() {
        return super.last();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmModel last(RealmModel realmModel) {
        return super.last(realmModel);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ ListIterator listIterator() {
        return super.listIterator();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ ListIterator listIterator(int i) {
        return super.listIterator(i);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number max(String str) {
        return super.max(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Date maxDate(String str) {
        return super.maxDate(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number min(String str) {
        return super.min(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Date minDate(String str) {
        return super.minDate(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ RealmModel remove(int i) {
        return super.remove(i);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.OrderedRealmCollectionImpl
    @Deprecated
    public /* bridge */ /* synthetic */ RealmModel set(int i, RealmModel realmModel) {
        return super.set(i, (int) realmModel);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmResults sort(String str) {
        return super.sort(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmResults sort(String str, Sort sort) {
        return super.sort(str, sort);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmResults sort(String[] strArr, Sort[] sortArr) {
        return super.sort(strArr, sortArr);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number sum(String str) {
        return super.sum(str);
    }

    @SuppressLint({"unused"})
    static <T extends RealmModel> RealmResults<T> createBacklinkResults(BaseRealm baseRealm, Row row, Class<T> cls, String str) {
        Table table = baseRealm.getSchema().getTable(cls);
        return new RealmResults<>(baseRealm, io.realm.internal.Collection.createBacklinksCollection(baseRealm.sharedRealm, (UncheckedRow) row, table, str), cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static RealmResults<DynamicRealmObject> createDynamicBacklinkResults(DynamicRealm dynamicRealm, CheckedRow checkedRow, Table table, String str) {
        return new RealmResults<>(dynamicRealm, io.realm.internal.Collection.createBacklinksCollection(dynamicRealm.sharedRealm, checkedRow, table, str), Table.getClassNameForTable(table.getName()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RealmResults(BaseRealm baseRealm, io.realm.internal.Collection collection, Class<E> cls) {
        super(baseRealm, collection, cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RealmResults(BaseRealm baseRealm, io.realm.internal.Collection collection, String str) {
        super(baseRealm, collection, str);
    }

    @Override // io.realm.RealmCollection
    public RealmQuery<E> where() {
        this.realm.checkIfValid();
        return RealmQuery.createQueryFromResult(this);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    @Override // io.realm.RealmCollection
    public boolean isLoaded() {
        this.realm.checkIfValid();
        return this.collection.isLoaded();
    }

    @Override // io.realm.RealmCollection
    public boolean load() {
        this.realm.checkIfValid();
        this.collection.load();
        return true;
    }

    public void addChangeListener(RealmChangeListener<RealmResults<E>> realmChangeListener) {
        checkForAddRemoveListener(realmChangeListener, true);
        this.collection.addListener((io.realm.internal.Collection) this, (RealmChangeListener<io.realm.internal.Collection>) realmChangeListener);
    }

    public void addChangeListener(OrderedRealmCollectionChangeListener<RealmResults<E>> orderedRealmCollectionChangeListener) {
        checkForAddRemoveListener(orderedRealmCollectionChangeListener, true);
        this.collection.addListener((io.realm.internal.Collection) this, (OrderedRealmCollectionChangeListener<io.realm.internal.Collection>) orderedRealmCollectionChangeListener);
    }

    private void checkForAddRemoveListener(Object obj, boolean z) {
        if (!z || obj != null) {
            this.realm.checkIfValid();
            this.realm.sharedRealm.capabilities.checkCanDeliverNotification("Listeners cannot be used on current thread.");
            return;
        }
        throw new IllegalArgumentException("Listener should not be null");
    }

    public void removeAllChangeListeners() {
        checkForAddRemoveListener(null, false);
        this.collection.removeAllListeners();
    }

    @Deprecated
    public void removeChangeListeners() {
        removeAllChangeListeners();
    }

    public void removeChangeListener(RealmChangeListener<RealmResults<E>> realmChangeListener) {
        checkForAddRemoveListener(realmChangeListener, true);
        this.collection.removeListener((io.realm.internal.Collection) this, (RealmChangeListener<io.realm.internal.Collection>) realmChangeListener);
    }

    public void removeChangeListener(OrderedRealmCollectionChangeListener<RealmResults<E>> orderedRealmCollectionChangeListener) {
        checkForAddRemoveListener(orderedRealmCollectionChangeListener, true);
        this.collection.removeListener((io.realm.internal.Collection) this, (OrderedRealmCollectionChangeListener<io.realm.internal.Collection>) orderedRealmCollectionChangeListener);
    }

    public Observable<RealmResults<E>> asObservable() {
        if (this.realm instanceof Realm) {
            return this.realm.configuration.getRxFactory().from((Realm) this.realm, this);
        }
        if (this.realm instanceof DynamicRealm) {
            return this.realm.configuration.getRxFactory().from((DynamicRealm) this.realm, (RealmResults<DynamicRealmObject>) this);
        }
        throw new UnsupportedOperationException(this.realm.getClass() + " does not support RxJava.");
    }

    @Deprecated
    public RealmResults<E> distinct(String str) {
        return createLoadedResults(this.collection.distinct(SortDescriptor.getInstanceForDistinct(new SchemaConnector(this.realm.getSchema()), this.collection.getTable(), str)));
    }

    @Deprecated
    public RealmResults<E> distinctAsync(String str) {
        return where().distinctAsync(str);
    }

    @Deprecated
    public RealmResults<E> distinct(String str, String... strArr) {
        return where().distinct(str, strArr);
    }
}
