package io.realm;

import io.realm.RealmModel;
import io.realm.internal.Collection;
import io.realm.internal.InvalidRow;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.SortDescriptor;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import java.util.AbstractList;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;

/* loaded from: classes.dex */
public abstract class OrderedRealmCollectionImpl<E extends RealmModel> extends AbstractList<E> implements OrderedRealmCollection<E> {
    private static final String NOT_SUPPORTED_MESSAGE = "This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.";
    final String className;
    final Class<E> classSpec;
    final Collection collection;
    final BaseRealm realm;

    @Override // io.realm.RealmCollection, io.realm.internal.ManagableObject
    public boolean isManaged() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ void add(int i, Object obj) {
        add(i, (int) ((RealmModel) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean add(Object obj) {
        return add((OrderedRealmCollectionImpl<E>) ((RealmModel) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        return set(i, (int) ((RealmModel) obj));
    }

    public OrderedRealmCollectionImpl(BaseRealm baseRealm, Collection collection, Class<E> cls) {
        this(baseRealm, collection, cls, null);
    }

    public OrderedRealmCollectionImpl(BaseRealm baseRealm, Collection collection, String str) {
        this(baseRealm, collection, null, str);
    }

    private OrderedRealmCollectionImpl(BaseRealm baseRealm, Collection collection, Class<E> cls, String str) {
        this.realm = baseRealm;
        this.collection = collection;
        this.classSpec = cls;
        this.className = str;
    }

    public Table getTable() {
        return this.collection.getTable();
    }

    public Collection getCollection() {
        return this.collection;
    }

    @Override // io.realm.RealmCollection, io.realm.internal.ManagableObject
    public boolean isValid() {
        return this.collection.isValid();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public boolean contains(Object obj) {
        if (!isLoaded() || ((obj instanceof RealmObjectProxy) && ((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm() == InvalidRow.INSTANCE)) {
            return false;
        }
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (it.next().equals(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i) {
        this.realm.checkIfValid();
        return (E) this.realm.get(this.classSpec, this.className, this.collection.getUncheckedRow(i));
    }

    @Override // io.realm.OrderedRealmCollection
    public E first() {
        return firstImpl(true, null);
    }

    @Override // io.realm.OrderedRealmCollection
    public E first(E e) {
        return firstImpl(false, e);
    }

    private E firstImpl(boolean z, E e) {
        UncheckedRow firstUncheckedRow = this.collection.firstUncheckedRow();
        if (firstUncheckedRow != null) {
            return (E) this.realm.get(this.classSpec, this.className, firstUncheckedRow);
        }
        if (!z) {
            return e;
        }
        throw new IndexOutOfBoundsException("No results were found.");
    }

    @Override // io.realm.OrderedRealmCollection
    public E last() {
        return lastImpl(true, null);
    }

    @Override // io.realm.OrderedRealmCollection
    public E last(E e) {
        return lastImpl(false, e);
    }

    private E lastImpl(boolean z, E e) {
        UncheckedRow lastUncheckedRow = this.collection.lastUncheckedRow();
        if (lastUncheckedRow != null) {
            return (E) this.realm.get(this.classSpec, this.className, lastUncheckedRow);
        }
        if (!z) {
            return e;
        }
        throw new IndexOutOfBoundsException("No results were found.");
    }

    @Override // io.realm.OrderedRealmCollection
    public void deleteFromRealm(int i) {
        this.realm.checkIfValidAndInTransaction();
        this.collection.delete(i);
    }

    @Override // io.realm.RealmCollection
    public boolean deleteAllFromRealm() {
        this.realm.checkIfValid();
        if (size() <= 0) {
            return false;
        }
        this.collection.clear();
        return true;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<E> iterator() {
        return new RealmCollectionIterator();
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator() {
        return new RealmCollectionListIterator(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator(int i) {
        return new RealmCollectionListIterator(i);
    }

    private long getColumnIndexForSort(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Non-empty field name required.");
        } else if (str.contains(".")) {
            throw new IllegalArgumentException("Aggregates on child object fields are not supported: " + str);
        } else {
            long columnIndex = this.collection.getTable().getColumnIndex(str);
            if (columnIndex >= 0) {
                return columnIndex;
            }
            throw new IllegalArgumentException(String.format(Locale.US, "Field '%s' does not exist.", str));
        }
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str) {
        return createLoadedResults(this.collection.sort(SortDescriptor.getInstanceForSort(getSchemaConnector(), this.collection.getTable(), str, Sort.ASCENDING)));
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort) {
        return createLoadedResults(this.collection.sort(SortDescriptor.getInstanceForSort(getSchemaConnector(), this.collection.getTable(), str, sort)));
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String[] strArr, Sort[] sortArr) {
        return createLoadedResults(this.collection.sort(SortDescriptor.getInstanceForSort(getSchemaConnector(), this.collection.getTable(), strArr, sortArr)));
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        if (!isLoaded()) {
            return 0;
        }
        long size = this.collection.size();
        if (size > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) size;
    }

    @Override // io.realm.RealmCollection
    public Number min(String str) {
        this.realm.checkIfValid();
        return this.collection.aggregateNumber(Collection.Aggregate.MINIMUM, getColumnIndexForSort(str));
    }

    @Override // io.realm.RealmCollection
    public Date minDate(String str) {
        this.realm.checkIfValid();
        return this.collection.aggregateDate(Collection.Aggregate.MINIMUM, getColumnIndexForSort(str));
    }

    @Override // io.realm.RealmCollection
    public Number max(String str) {
        this.realm.checkIfValid();
        return this.collection.aggregateNumber(Collection.Aggregate.MAXIMUM, getColumnIndexForSort(str));
    }

    @Override // io.realm.RealmCollection
    public Date maxDate(String str) {
        this.realm.checkIfValid();
        return this.collection.aggregateDate(Collection.Aggregate.MAXIMUM, getColumnIndexForSort(str));
    }

    @Override // io.realm.RealmCollection
    public Number sum(String str) {
        this.realm.checkIfValid();
        return this.collection.aggregateNumber(Collection.Aggregate.SUM, getColumnIndexForSort(str));
    }

    @Override // io.realm.RealmCollection
    public double average(String str) {
        this.realm.checkIfValid();
        return this.collection.aggregateNumber(Collection.Aggregate.AVERAGE, getColumnIndexForSort(str)).doubleValue();
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public E remove(int i) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean removeAll(java.util.Collection<?> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Deprecated
    public E set(int i, E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean retainAll(java.util.Collection<?> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteLastFromRealm() {
        this.realm.checkIfValidAndInTransaction();
        return this.collection.deleteLast();
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteFirstFromRealm() {
        this.realm.checkIfValidAndInTransaction();
        return this.collection.deleteFirst();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Deprecated
    public boolean add(E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Deprecated
    public void add(int i, E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public boolean addAll(int i, java.util.Collection<? extends E> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean addAll(java.util.Collection<? extends E> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    /* loaded from: classes.dex */
    public class RealmCollectionIterator extends Collection.Iterator<E> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        RealmCollectionIterator() {
            super(r1.collection);
            OrderedRealmCollectionImpl.this = r1;
        }

        @Override // io.realm.internal.Collection.Iterator
        public E convertRowToObject(UncheckedRow uncheckedRow) {
            return (E) OrderedRealmCollectionImpl.this.realm.get(OrderedRealmCollectionImpl.this.classSpec, OrderedRealmCollectionImpl.this.className, uncheckedRow);
        }
    }

    @Override // io.realm.OrderedRealmCollection
    public OrderedRealmCollectionSnapshot<E> createSnapshot() {
        if (this.className != null) {
            return new OrderedRealmCollectionSnapshot<>(this.realm, this.collection, this.className);
        }
        return new OrderedRealmCollectionSnapshot<>(this.realm, this.collection, this.classSpec);
    }

    /* loaded from: classes.dex */
    public class RealmCollectionListIterator extends Collection.ListIterator<E> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        RealmCollectionListIterator(int i) {
            super(r1.collection, i);
            OrderedRealmCollectionImpl.this = r1;
        }

        @Override // io.realm.internal.Collection.Iterator
        public E convertRowToObject(UncheckedRow uncheckedRow) {
            return (E) OrderedRealmCollectionImpl.this.realm.get(OrderedRealmCollectionImpl.this.classSpec, OrderedRealmCollectionImpl.this.className, uncheckedRow);
        }
    }

    public RealmResults<E> createLoadedResults(Collection collection) {
        RealmResults<E> realmResults;
        if (this.className != null) {
            realmResults = new RealmResults<>(this.realm, collection, this.className);
        } else {
            realmResults = new RealmResults<>(this.realm, collection, this.classSpec);
        }
        realmResults.load();
        return realmResults;
    }

    private SchemaConnector getSchemaConnector() {
        return new SchemaConnector(this.realm.getSchema());
    }
}
