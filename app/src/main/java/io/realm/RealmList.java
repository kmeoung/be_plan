package io.realm;

import io.realm.RealmModel;
import io.realm.internal.Collection;
import io.realm.internal.InvalidRow;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.SortDescriptor;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import rx.Observable;

/* loaded from: classes.dex */
public class RealmList<E extends RealmModel> extends AbstractList<E> implements OrderedRealmCollection<E> {
    private static final String NULL_OBJECTS_NOT_ALLOWED_MESSAGE = "RealmList does not accept null values";
    private static final String ONLY_IN_MANAGED_MODE_MESSAGE = "This method is only available in managed mode";
    public static final String REMOVE_OUTSIDE_TRANSACTION_ERROR = "Objects can only be removed from inside a write transaction";
    protected String className;
    protected Class<E> clazz;
    private final Collection collection;
    protected BaseRealm realm;
    private List<E> unmanagedList;
    final LinkView view;

    @Override // io.realm.RealmCollection
    public boolean isLoaded() {
        return true;
    }

    @Override // io.realm.RealmCollection
    public boolean load() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ void add(int i, Object obj) {
        add(i, (int) ((RealmModel) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean add(Object obj) {
        return add((RealmList<E>) ((RealmModel) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        return set(i, (int) ((RealmModel) obj));
    }

    public RealmList() {
        this.collection = null;
        this.view = null;
        this.unmanagedList = new ArrayList();
    }

    public RealmList(E... eArr) {
        if (eArr == null) {
            throw new IllegalArgumentException("The objects argument cannot be null");
        }
        this.collection = null;
        this.view = null;
        this.unmanagedList = new ArrayList(eArr.length);
        Collections.addAll(this.unmanagedList, eArr);
    }

    RealmList(Class<E> cls, LinkView linkView, BaseRealm baseRealm) {
        this.collection = new Collection(baseRealm.sharedRealm, linkView, (SortDescriptor) null);
        this.clazz = cls;
        this.view = linkView;
        this.realm = baseRealm;
    }

    public RealmList(String str, LinkView linkView, BaseRealm baseRealm) {
        this.collection = new Collection(baseRealm.sharedRealm, linkView, (SortDescriptor) null);
        this.view = linkView;
        this.realm = baseRealm;
        this.className = str;
    }

    @Override // io.realm.RealmCollection, io.realm.internal.ManagableObject
    public boolean isValid() {
        if (this.realm == null) {
            return true;
        }
        if (this.realm.isClosed()) {
            return false;
        }
        return isAttached();
    }

    @Override // io.realm.RealmCollection, io.realm.internal.ManagableObject
    public boolean isManaged() {
        return this.realm != null;
    }

    private boolean isAttached() {
        return this.view != null && this.view.isAttached();
    }

    public void add(int i, E e) {
        checkValidObject(e);
        if (isManaged()) {
            checkValidView();
            if (i < 0 || i > size()) {
                throw new IndexOutOfBoundsException("Invalid index " + i + ", size is " + size());
            }
            this.view.insert(i, ((RealmObjectProxy) copyToRealmIfNeeded(e)).realmGet$proxyState().getRow$realm().getIndex());
        } else {
            this.unmanagedList.add(i, e);
        }
        this.modCount++;
    }

    public boolean add(E e) {
        checkValidObject(e);
        if (isManaged()) {
            checkValidView();
            this.view.add(((RealmObjectProxy) copyToRealmIfNeeded(e)).realmGet$proxyState().getRow$realm().getIndex());
        } else {
            this.unmanagedList.add(e);
        }
        this.modCount++;
        return true;
    }

    public E set(int i, E e) {
        checkValidObject(e);
        if (!isManaged()) {
            return this.unmanagedList.set(i, e);
        }
        checkValidView();
        E e2 = get(i);
        this.view.set(i, ((RealmObjectProxy) copyToRealmIfNeeded(e)).realmGet$proxyState().getRow$realm().getIndex());
        return e2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private E copyToRealmIfNeeded(E e) {
        if (e instanceof RealmObjectProxy) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) e;
            if (realmObjectProxy instanceof DynamicRealmObject) {
                String className = this.view.getTargetTable().getClassName();
                if (realmObjectProxy.realmGet$proxyState().getRealm$realm() == this.realm) {
                    String type = ((DynamicRealmObject) e).getType();
                    if (className.equals(type)) {
                        return e;
                    }
                    throw new IllegalArgumentException(String.format(Locale.US, "The object has a different type from list's. Type of the list is '%s', type of object is '%s'.", className, type));
                } else if (this.realm.threadId == realmObjectProxy.realmGet$proxyState().getRealm$realm().threadId) {
                    throw new IllegalArgumentException("Cannot copy DynamicRealmObject between Realm instances.");
                } else {
                    throw new IllegalStateException("Cannot copy an object to a Realm instance created in another thread.");
                }
            } else if (realmObjectProxy.realmGet$proxyState().getRow$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(this.realm.getPath())) {
                if (this.realm == realmObjectProxy.realmGet$proxyState().getRealm$realm()) {
                    return e;
                }
                throw new IllegalArgumentException("Cannot copy an object from another Realm instance.");
            }
        }
        Realm realm = (Realm) this.realm;
        if (realm.getTable(e.getClass()).hasPrimaryKey()) {
            return (E) realm.copyToRealmOrUpdate((Realm) e);
        }
        return (E) realm.copyToRealm((Realm) e);
    }

    public void move(int i, int i2) {
        if (isManaged()) {
            checkValidView();
            this.view.move(i, i2);
            return;
        }
        checkIndex(i);
        checkIndex(i2);
        E remove = this.unmanagedList.remove(i);
        if (i2 > i) {
            this.unmanagedList.add(i2 - 1, remove);
        } else {
            this.unmanagedList.add(i2, remove);
        }
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        if (isManaged()) {
            checkValidView();
            this.view.clear();
        } else {
            this.unmanagedList.clear();
        }
        this.modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public E remove(int i) {
        E e;
        if (isManaged()) {
            checkValidView();
            e = get(i);
            this.view.remove(i);
        } else {
            e = this.unmanagedList.remove(i);
        }
        this.modCount++;
        return e;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        if (!isManaged() || this.realm.isInTransaction()) {
            return super.remove(obj);
        }
        throw new IllegalStateException(REMOVE_OUTSIDE_TRANSACTION_ERROR);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean removeAll(java.util.Collection<?> collection) {
        if (!isManaged() || this.realm.isInTransaction()) {
            return super.removeAll(collection);
        }
        throw new IllegalStateException(REMOVE_OUTSIDE_TRANSACTION_ERROR);
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteFirstFromRealm() {
        if (!isManaged()) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        } else if (size() <= 0) {
            return false;
        } else {
            deleteFromRealm(0);
            this.modCount++;
            return true;
        }
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteLastFromRealm() {
        if (!isManaged()) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        } else if (size() <= 0) {
            return false;
        } else {
            deleteFromRealm(size() - 1);
            this.modCount++;
            return true;
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i) {
        if (!isManaged()) {
            return this.unmanagedList.get(i);
        }
        checkValidView();
        return (E) this.realm.get(this.clazz, this.className, this.view.getTargetRowIndex(i));
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
        if (isManaged()) {
            checkValidView();
            if (!this.view.isEmpty()) {
                return get(0);
            }
        } else if (this.unmanagedList != null && !this.unmanagedList.isEmpty()) {
            return this.unmanagedList.get(0);
        }
        if (!z) {
            return e;
        }
        throw new IndexOutOfBoundsException("The list is empty.");
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
        if (isManaged()) {
            checkValidView();
            if (!this.view.isEmpty()) {
                return get(((int) this.view.size()) - 1);
            }
        } else if (this.unmanagedList != null && !this.unmanagedList.isEmpty()) {
            return this.unmanagedList.get(this.unmanagedList.size() - 1);
        }
        if (!z) {
            return e;
        }
        throw new IndexOutOfBoundsException("The list is empty.");
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str) {
        return sort(str, Sort.ASCENDING);
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort) {
        if (isManaged()) {
            return where().findAllSorted(str, sort);
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String[] strArr, Sort[] sortArr) {
        if (isManaged()) {
            return where().findAllSorted(strArr, sortArr);
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // io.realm.OrderedRealmCollection
    public void deleteFromRealm(int i) {
        if (isManaged()) {
            checkValidView();
            this.view.removeTargetRow(i);
            this.modCount++;
            return;
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        if (!isManaged()) {
            return this.unmanagedList.size();
        }
        checkValidView();
        long size = this.view.size();
        if (size < 2147483647L) {
            return (int) size;
        }
        return Integer.MAX_VALUE;
    }

    @Override // io.realm.RealmCollection
    public RealmQuery<E> where() {
        if (isManaged()) {
            checkValidView();
            return RealmQuery.createQueryFromList(this);
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // io.realm.RealmCollection
    public Number min(String str) {
        if (isManaged()) {
            return where().min(str);
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // io.realm.RealmCollection
    public Number max(String str) {
        if (isManaged()) {
            return where().max(str);
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // io.realm.RealmCollection
    public Number sum(String str) {
        if (isManaged()) {
            return where().sum(str);
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // io.realm.RealmCollection
    public double average(String str) {
        if (isManaged()) {
            return where().average(str);
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // io.realm.RealmCollection
    public Date maxDate(String str) {
        if (isManaged()) {
            return where().maximumDate(str);
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // io.realm.RealmCollection
    public Date minDate(String str) {
        if (isManaged()) {
            return where().minimumDate(str);
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // io.realm.RealmCollection
    public boolean deleteAllFromRealm() {
        if (isManaged()) {
            checkValidView();
            if (size() <= 0) {
                return false;
            }
            this.view.removeAllTargetRows();
            this.modCount++;
            return true;
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public boolean contains(Object obj) {
        if (!isManaged()) {
            return this.unmanagedList.contains(obj);
        }
        this.realm.checkIfValid();
        if ((obj instanceof RealmObjectProxy) && ((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm() == InvalidRow.INSTANCE) {
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

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<E> iterator() {
        if (isManaged()) {
            return new RealmItr();
        }
        return super.iterator();
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator(int i) {
        if (isManaged()) {
            return new RealmListItr(i);
        }
        return super.listIterator(i);
    }

    private void checkValidObject(E e) {
        if (e == null) {
            throw new IllegalArgumentException(NULL_OBJECTS_NOT_ALLOWED_MESSAGE);
        }
    }

    private void checkIndex(int i) {
        int size = size();
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + i + ", size is " + size);
        }
    }

    private void checkValidView() {
        this.realm.checkIfValid();
        if (this.view == null || !this.view.isAttached()) {
            throw new IllegalStateException("Realm instance has been closed or this object or its parent has been deleted.");
        }
    }

    @Override // io.realm.OrderedRealmCollection
    public OrderedRealmCollectionSnapshot<E> createSnapshot() {
        if (!isManaged()) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }
        checkValidView();
        if (this.className != null) {
            return new OrderedRealmCollectionSnapshot<>(this.realm, new Collection(this.realm.sharedRealm, this.view, (SortDescriptor) null), this.className);
        }
        return new OrderedRealmCollectionSnapshot<>(this.realm, new Collection(this.realm.sharedRealm, this.view, (SortDescriptor) null), this.clazz);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append((isManaged() ? this.clazz : getClass()).getSimpleName());
        sb.append("@[");
        if (!isManaged() || isAttached()) {
            for (int i = 0; i < size(); i++) {
                if (isManaged()) {
                    sb.append(((RealmObjectProxy) get(i)).realmGet$proxyState().getRow$realm().getIndex());
                } else {
                    sb.append(System.identityHashCode(get(i)));
                }
                if (i < size() - 1) {
                    sb.append(',');
                }
            }
        } else {
            sb.append("invalid");
        }
        sb.append("]");
        return sb.toString();
    }

    public Observable<RealmList<E>> asObservable() {
        if (this.realm instanceof Realm) {
            return this.realm.configuration.getRxFactory().from((Realm) this.realm, this);
        }
        if (this.realm instanceof DynamicRealm) {
            return this.realm.configuration.getRxFactory().from((DynamicRealm) this.realm, (RealmList<DynamicRealmObject>) this);
        }
        throw new UnsupportedOperationException(this.realm.getClass() + " does not support RxJava.");
    }

    private void checkForAddRemoveListener(Object obj, boolean z) {
        if (!z || obj != null) {
            this.realm.checkIfValid();
            this.realm.sharedRealm.capabilities.checkCanDeliverNotification("Listeners cannot be used on current thread.");
            return;
        }
        throw new IllegalArgumentException("Listener should not be null");
    }

    public void addChangeListener(OrderedRealmCollectionChangeListener<RealmList<E>> orderedRealmCollectionChangeListener) {
        checkForAddRemoveListener(orderedRealmCollectionChangeListener, true);
        this.collection.addListener((Collection) this, (OrderedRealmCollectionChangeListener<Collection>) orderedRealmCollectionChangeListener);
    }

    public void removeChangeListener(OrderedRealmCollectionChangeListener<RealmList<E>> orderedRealmCollectionChangeListener) {
        checkForAddRemoveListener(orderedRealmCollectionChangeListener, true);
        this.collection.removeListener((Collection) this, (OrderedRealmCollectionChangeListener<Collection>) orderedRealmCollectionChangeListener);
    }

    public void addChangeListener(RealmChangeListener<RealmList<E>> realmChangeListener) {
        checkForAddRemoveListener(realmChangeListener, true);
        this.collection.addListener((Collection) this, (RealmChangeListener<Collection>) realmChangeListener);
    }

    public void removeChangeListener(RealmChangeListener<RealmList<E>> realmChangeListener) {
        checkForAddRemoveListener(realmChangeListener, true);
        this.collection.removeListener((Collection) this, (RealmChangeListener<Collection>) realmChangeListener);
    }

    public void removeAllChangeListeners() {
        checkForAddRemoveListener(null, false);
        this.collection.removeAllListeners();
    }

    /* loaded from: classes.dex */
    public class RealmItr implements Iterator<E> {
        int cursor;
        int expectedModCount;
        int lastRet;

        private RealmItr() {
            RealmList.this = r1;
            this.cursor = 0;
            this.lastRet = -1;
            this.expectedModCount = RealmList.this.modCount;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            RealmList.this.realm.checkIfValid();
            checkConcurrentModification();
            return this.cursor != RealmList.this.size();
        }

        @Override // java.util.Iterator
        public E next() {
            RealmList.this.realm.checkIfValid();
            checkConcurrentModification();
            int i = this.cursor;
            try {
                E e = (E) RealmList.this.get(i);
                this.lastRet = i;
                this.cursor = i + 1;
                return e;
            } catch (IndexOutOfBoundsException unused) {
                checkConcurrentModification();
                throw new NoSuchElementException("Cannot access index " + i + " when size is " + RealmList.this.size() + ". Remember to check hasNext() before using next().");
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            RealmList.this.realm.checkIfValid();
            if (this.lastRet < 0) {
                throw new IllegalStateException("Cannot call remove() twice. Must call next() in between.");
            }
            checkConcurrentModification();
            try {
                RealmList.this.remove(this.lastRet);
                if (this.lastRet < this.cursor) {
                    this.cursor--;
                }
                this.lastRet = -1;
                this.expectedModCount = RealmList.this.modCount;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkConcurrentModification() {
            if (RealmList.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /* loaded from: classes.dex */
    public class RealmListItr extends RealmList<E>.RealmItr implements ListIterator<E> {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.ListIterator
        public /* bridge */ /* synthetic */ void add(Object obj) {
            add((RealmListItr) ((RealmModel) obj));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.ListIterator
        public /* bridge */ /* synthetic */ void set(Object obj) {
            set((RealmListItr) ((RealmModel) obj));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        RealmListItr(int i) {
            super();
            RealmList.this = r4;
            if (i < 0 || i > r4.size()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Starting location must be a valid index: [0, ");
                sb.append(r4.size() - 1);
                sb.append("]. Index was ");
                sb.append(i);
                throw new IndexOutOfBoundsException(sb.toString());
            }
            this.cursor = i;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        @Override // java.util.ListIterator
        public E previous() {
            checkConcurrentModification();
            int i = this.cursor - 1;
            try {
                E e = (E) RealmList.this.get(i);
                this.cursor = i;
                this.lastRet = i;
                return e;
            } catch (IndexOutOfBoundsException unused) {
                checkConcurrentModification();
                throw new NoSuchElementException("Cannot access index less than zero. This was " + i + ". Remember to check hasPrevious() before using previous().");
            }
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.cursor;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.cursor - 1;
        }

        public void set(E e) {
            RealmList.this.realm.checkIfValid();
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            checkConcurrentModification();
            try {
                RealmList.this.set(this.lastRet, (int) e);
                this.expectedModCount = RealmList.this.modCount;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            RealmList.this.realm.checkIfValid();
            checkConcurrentModification();
            try {
                int i = this.cursor;
                RealmList.this.add(i, (int) e);
                this.lastRet = -1;
                this.cursor = i + 1;
                this.expectedModCount = RealmList.this.modCount;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
