package io.realm.internal;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmChangeListener;
import io.realm.internal.ObserverPairList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.NoSuchElementException;

@Keep
/* loaded from: classes.dex */
public class Collection implements NativeObject {
    public static final byte AGGREGATE_FUNCTION_AVERAGE = 3;
    public static final byte AGGREGATE_FUNCTION_MAXIMUM = 2;
    public static final byte AGGREGATE_FUNCTION_MINIMUM = 1;
    public static final byte AGGREGATE_FUNCTION_SUM = 4;
    private static final String CLOSED_REALM_MESSAGE = "This Realm instance has already been closed, making it unusable.";
    public static final byte MODE_EMPTY = 0;
    public static final byte MODE_LINKVIEW = 3;
    public static final byte MODE_QUERY = 2;
    public static final byte MODE_TABLE = 1;
    public static final byte MODE_TABLEVIEW = 4;
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private final NativeContext context;
    private boolean isSnapshot;
    private boolean loaded;
    private final long nativePtr;
    private final ObserverPairList<CollectionObserverPair> observerPairs;
    private final SharedRealm sharedRealm;
    private final Table table;

    private static native Object nativeAggregate(long j, long j2, byte b);

    private static native void nativeClear(long j);

    private static native boolean nativeContains(long j, long j2);

    private static native long nativeCreateResults(long j, long j2, SortDescriptor sortDescriptor, SortDescriptor sortDescriptor2);

    private static native long nativeCreateResultsFromBacklinks(long j, long j2, long j3, long j4);

    private static native long nativeCreateResultsFromLinkView(long j, long j2, SortDescriptor sortDescriptor);

    private static native long nativeCreateSnapshot(long j);

    private static native void nativeDelete(long j, long j2);

    private static native boolean nativeDeleteFirst(long j);

    private static native boolean nativeDeleteLast(long j);

    private static native long nativeDistinct(long j, SortDescriptor sortDescriptor);

    private static native long nativeFirstRow(long j);

    private static native long nativeGetFinalizerPtr();

    private static native byte nativeGetMode(long j);

    private static native long nativeGetRow(long j, int i);

    private static native long nativeIndexOf(long j, long j2);

    private static native long nativeIndexOfBySourceRowIndex(long j, long j2);

    private static native boolean nativeIsValid(long j);

    private static native long nativeLastRow(long j);

    private static native long nativeSize(long j);

    private static native long nativeSort(long j, SortDescriptor sortDescriptor);

    private native void nativeStartListening(long j);

    private native void nativeStopListening(long j);

    private static native long nativeWhere(long j);

    /* loaded from: classes.dex */
    public static class CollectionObserverPair<T> extends ObserverPairList.ObserverPair<T, Object> {
        public CollectionObserverPair(T t, Object obj) {
            super(t, obj);
        }

        public void onChange(T t, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (this.listener instanceof OrderedRealmCollectionChangeListener) {
                ((OrderedRealmCollectionChangeListener) this.listener).onChange(t, orderedCollectionChangeSet);
            } else if (this.listener instanceof RealmChangeListener) {
                ((RealmChangeListener) this.listener).onChange(t);
            } else {
                throw new RuntimeException("Unsupported listener type: " + this.listener);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class RealmChangeListenerWrapper<T> implements OrderedRealmCollectionChangeListener<T> {
        private final RealmChangeListener<T> listener;

        RealmChangeListenerWrapper(RealmChangeListener<T> realmChangeListener) {
            this.listener = realmChangeListener;
        }

        @Override // io.realm.OrderedRealmCollectionChangeListener
        public void onChange(T t, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            this.listener.onChange(t);
        }

        public boolean equals(Object obj) {
            return (obj instanceof RealmChangeListenerWrapper) && this.listener == ((RealmChangeListenerWrapper) obj).listener;
        }

        public int hashCode() {
            return this.listener.hashCode();
        }
    }

    /* loaded from: classes.dex */
    public static class Callback implements ObserverPairList.Callback<CollectionObserverPair> {
        private final OrderedCollectionChangeSet changeSet;

        Callback(OrderedCollectionChangeSet orderedCollectionChangeSet) {
            this.changeSet = orderedCollectionChangeSet;
        }

        public void onCalled(CollectionObserverPair collectionObserverPair, Object obj) {
            collectionObserverPair.onChange(obj, this.changeSet);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Iterator<T> implements java.util.Iterator<T> {
        Collection iteratorCollection;
        protected int pos = -1;

        protected abstract T convertRowToObject(UncheckedRow uncheckedRow);

        public Iterator(Collection collection) {
            if (collection.sharedRealm.isClosed()) {
                throw new IllegalStateException(Collection.CLOSED_REALM_MESSAGE);
            }
            this.iteratorCollection = collection;
            if (!collection.isSnapshot) {
                if (collection.sharedRealm.isInTransaction()) {
                    detach();
                } else {
                    this.iteratorCollection.sharedRealm.addIterator(this);
                }
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            checkValid();
            return ((long) (this.pos + 1)) < this.iteratorCollection.size();
        }

        @Override // java.util.Iterator
        public T next() {
            checkValid();
            this.pos++;
            if (this.pos < this.iteratorCollection.size()) {
                return get(this.pos);
            }
            throw new NoSuchElementException("Cannot access index " + this.pos + " when size is " + this.iteratorCollection.size() + ". Remember to check hasNext() before using next().");
        }

        @Override // java.util.Iterator
        @Deprecated
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported by RealmResults iterators.");
        }

        public void detach() {
            this.iteratorCollection = this.iteratorCollection.createSnapshot();
        }

        public void invalidate() {
            this.iteratorCollection = null;
        }

        void checkValid() {
            if (this.iteratorCollection == null) {
                throw new ConcurrentModificationException("No outside changes to a Realm is allowed while iterating a living Realm collection.");
            }
        }

        T get(int i) {
            return convertRowToObject(this.iteratorCollection.getUncheckedRow(i));
        }
    }

    /* loaded from: classes.dex */
    public static abstract class ListIterator<T> extends Iterator<T> implements java.util.ListIterator<T> {
        public ListIterator(Collection collection, int i) {
            super(collection);
            if (i < 0 || i > this.iteratorCollection.size()) {
                throw new IndexOutOfBoundsException("Starting location must be a valid index: [0, " + (this.iteratorCollection.size() - 1) + "]. Yours was " + i);
            }
            this.pos = i - 1;
        }

        @Override // java.util.ListIterator
        @Deprecated
        public void add(T t) {
            throw new UnsupportedOperationException("Adding an element is not supported. Use Realm.createObject() instead.");
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            checkValid();
            return this.pos >= 0;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            checkValid();
            return this.pos + 1;
        }

        @Override // java.util.ListIterator
        public T previous() {
            checkValid();
            try {
                this.pos--;
                return get(this.pos);
            } catch (IndexOutOfBoundsException unused) {
                throw new NoSuchElementException("Cannot access index less than zero. This was " + this.pos + ". Remember to check hasPrevious() before using previous().");
            }
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            checkValid();
            return this.pos;
        }

        @Override // java.util.ListIterator
        @Deprecated
        public void set(T t) {
            throw new UnsupportedOperationException("Replacing and element is not supported.");
        }
    }

    /* loaded from: classes.dex */
    public enum Aggregate {
        MINIMUM((byte) 1),
        MAXIMUM((byte) 2),
        AVERAGE((byte) 3),
        SUM((byte) 4);
        
        private final byte value;

        Aggregate(byte b) {
            this.value = b;
        }

        public byte getValue() {
            return this.value;
        }
    }

    /* loaded from: classes.dex */
    public enum Mode {
        EMPTY,
        TABLE,
        QUERY,
        LINKVIEW,
        TABLEVIEW;

        static Mode getByValue(byte b) {
            switch (b) {
                case 0:
                    return EMPTY;
                case 1:
                    return TABLE;
                case 2:
                    return QUERY;
                case 3:
                    return LINKVIEW;
                case 4:
                    return TABLEVIEW;
                default:
                    throw new IllegalArgumentException("Invalid value: " + ((int) b));
            }
        }
    }

    public static Collection createBacklinksCollection(SharedRealm sharedRealm, UncheckedRow uncheckedRow, Table table, String str) {
        return new Collection(sharedRealm, table, nativeCreateResultsFromBacklinks(sharedRealm.getNativePtr(), uncheckedRow.getNativePtr(), table.getNativePtr(), table.getColumnIndex(str)), true);
    }

    public Collection(SharedRealm sharedRealm, TableQuery tableQuery, SortDescriptor sortDescriptor, SortDescriptor sortDescriptor2) {
        this.isSnapshot = false;
        this.observerPairs = new ObserverPairList<>();
        tableQuery.validateQuery();
        this.nativePtr = nativeCreateResults(sharedRealm.getNativePtr(), tableQuery.getNativePtr(), sortDescriptor, sortDescriptor2);
        this.sharedRealm = sharedRealm;
        this.context = sharedRealm.context;
        this.table = tableQuery.getTable();
        this.context.addReference(this);
        this.loaded = false;
    }

    public Collection(SharedRealm sharedRealm, TableQuery tableQuery, SortDescriptor sortDescriptor) {
        this(sharedRealm, tableQuery, sortDescriptor, (SortDescriptor) null);
    }

    public Collection(SharedRealm sharedRealm, TableQuery tableQuery) {
        this(sharedRealm, tableQuery, (SortDescriptor) null, (SortDescriptor) null);
    }

    public Collection(SharedRealm sharedRealm, LinkView linkView, SortDescriptor sortDescriptor) {
        this.isSnapshot = false;
        this.observerPairs = new ObserverPairList<>();
        this.nativePtr = nativeCreateResultsFromLinkView(sharedRealm.getNativePtr(), linkView.getNativePtr(), sortDescriptor);
        this.sharedRealm = sharedRealm;
        this.context = sharedRealm.context;
        this.table = linkView.getTargetTable();
        this.context.addReference(this);
        this.loaded = true;
    }

    private Collection(SharedRealm sharedRealm, Table table, long j) {
        this(sharedRealm, table, j, false);
    }

    private Collection(SharedRealm sharedRealm, Table table, long j, boolean z) {
        this.isSnapshot = false;
        this.observerPairs = new ObserverPairList<>();
        this.sharedRealm = sharedRealm;
        this.context = sharedRealm.context;
        this.table = table;
        this.nativePtr = j;
        this.context.addReference(this);
        this.loaded = z;
    }

    public Collection createSnapshot() {
        if (this.isSnapshot) {
            return this;
        }
        Collection collection = new Collection(this.sharedRealm, this.table, nativeCreateSnapshot(this.nativePtr));
        collection.isSnapshot = true;
        return collection;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    public UncheckedRow getUncheckedRow(int i) {
        return this.table.getUncheckedRowByPointer(nativeGetRow(this.nativePtr, i));
    }

    public UncheckedRow firstUncheckedRow() {
        long nativeFirstRow = nativeFirstRow(this.nativePtr);
        if (nativeFirstRow != 0) {
            return this.table.getUncheckedRowByPointer(nativeFirstRow);
        }
        return null;
    }

    public UncheckedRow lastUncheckedRow() {
        long nativeLastRow = nativeLastRow(this.nativePtr);
        if (nativeLastRow != 0) {
            return this.table.getUncheckedRowByPointer(nativeLastRow);
        }
        return null;
    }

    public Table getTable() {
        return this.table;
    }

    public TableQuery where() {
        return new TableQuery(this.context, this.table, nativeWhere(this.nativePtr));
    }

    public Number aggregateNumber(Aggregate aggregate, long j) {
        return (Number) nativeAggregate(this.nativePtr, j, aggregate.getValue());
    }

    public Date aggregateDate(Aggregate aggregate, long j) {
        return (Date) nativeAggregate(this.nativePtr, j, aggregate.getValue());
    }

    public long size() {
        return nativeSize(this.nativePtr);
    }

    public void clear() {
        nativeClear(this.nativePtr);
    }

    public Collection sort(SortDescriptor sortDescriptor) {
        return new Collection(this.sharedRealm, this.table, nativeSort(this.nativePtr, sortDescriptor));
    }

    public Collection distinct(SortDescriptor sortDescriptor) {
        return new Collection(this.sharedRealm, this.table, nativeDistinct(this.nativePtr, sortDescriptor));
    }

    public boolean contains(UncheckedRow uncheckedRow) {
        return nativeContains(this.nativePtr, uncheckedRow.getNativePtr());
    }

    public int indexOf(UncheckedRow uncheckedRow) {
        long nativeIndexOf = nativeIndexOf(this.nativePtr, uncheckedRow.getNativePtr());
        if (nativeIndexOf > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) nativeIndexOf;
    }

    public int indexOf(long j) {
        long nativeIndexOfBySourceRowIndex = nativeIndexOfBySourceRowIndex(this.nativePtr, j);
        if (nativeIndexOfBySourceRowIndex > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) nativeIndexOfBySourceRowIndex;
    }

    public void delete(long j) {
        nativeDelete(this.nativePtr, j);
    }

    public boolean deleteFirst() {
        return nativeDeleteFirst(this.nativePtr);
    }

    public boolean deleteLast() {
        return nativeDeleteLast(this.nativePtr);
    }

    public <T> void addListener(T t, OrderedRealmCollectionChangeListener<T> orderedRealmCollectionChangeListener) {
        if (this.observerPairs.isEmpty()) {
            nativeStartListening(this.nativePtr);
        }
        this.observerPairs.add(new CollectionObserverPair(t, orderedRealmCollectionChangeListener));
    }

    public <T> void addListener(T t, RealmChangeListener<T> realmChangeListener) {
        addListener((Collection) t, (OrderedRealmCollectionChangeListener<Collection>) new RealmChangeListenerWrapper(realmChangeListener));
    }

    public <T> void removeListener(T t, OrderedRealmCollectionChangeListener<T> orderedRealmCollectionChangeListener) {
        this.observerPairs.remove(t, orderedRealmCollectionChangeListener);
        if (this.observerPairs.isEmpty()) {
            nativeStopListening(this.nativePtr);
        }
    }

    public <T> void removeListener(T t, RealmChangeListener<T> realmChangeListener) {
        removeListener((Collection) t, (OrderedRealmCollectionChangeListener<Collection>) new RealmChangeListenerWrapper(realmChangeListener));
    }

    public void removeAllListeners() {
        this.observerPairs.clear();
        nativeStopListening(this.nativePtr);
    }

    public boolean isValid() {
        return nativeIsValid(this.nativePtr);
    }

    private void notifyChangeListeners(long j) {
        if (j != 0 || !isLoaded()) {
            boolean z = this.loaded;
            this.loaded = true;
            this.observerPairs.foreach(new Callback((j == 0 || !z) ? null : new CollectionChangeSet(j)));
        }
    }

    public Mode getMode() {
        return Mode.getByValue(nativeGetMode(this.nativePtr));
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void load() {
        if (!this.loaded) {
            notifyChangeListeners(0L);
        }
    }
}
