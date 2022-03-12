package io.realm;

import io.realm.RealmModel;
import io.realm.internal.ObserverPairList;
import io.realm.internal.OsObject;
import io.realm.internal.PendingRow;
import io.realm.internal.Row;
import io.realm.internal.UncheckedRow;
import java.util.List;

/* loaded from: classes.dex */
public final class ProxyState<E extends RealmModel> implements PendingRow.FrontEnd {
    private static QueryCallback queryCallback = new QueryCallback();
    private boolean acceptDefaultValue;
    private List<String> excludeFields;
    private E model;
    private OsObject osObject;
    private BaseRealm realm;
    private Row row;
    private boolean underConstruction = true;
    private ObserverPairList<OsObject.ObjectObserverPair> observerPairs = new ObserverPairList<>();

    /* loaded from: classes.dex */
    public static class RealmChangeListenerWrapper<T extends RealmModel> implements RealmObjectChangeListener<T> {
        private final RealmChangeListener<T> listener;

        public RealmChangeListenerWrapper(RealmChangeListener<T> realmChangeListener) {
            if (realmChangeListener == null) {
                throw new IllegalArgumentException("Listener should not be null");
            }
            this.listener = realmChangeListener;
        }

        @Override // io.realm.RealmObjectChangeListener
        public void onChange(T t, ObjectChangeSet objectChangeSet) {
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
    public static class QueryCallback implements ObserverPairList.Callback<OsObject.ObjectObserverPair> {
        private QueryCallback() {
        }

        public void onCalled(OsObject.ObjectObserverPair objectObserverPair, Object obj) {
            objectObserverPair.onChange((RealmModel) obj, null);
        }
    }

    public ProxyState() {
    }

    public ProxyState(E e) {
        this.model = e;
    }

    public BaseRealm getRealm$realm() {
        return this.realm;
    }

    public void setRealm$realm(BaseRealm baseRealm) {
        this.realm = baseRealm;
    }

    public Row getRow$realm() {
        return this.row;
    }

    public void setRow$realm(Row row) {
        this.row = row;
    }

    public boolean getAcceptDefaultValue$realm() {
        return this.acceptDefaultValue;
    }

    public void setAcceptDefaultValue$realm(boolean z) {
        this.acceptDefaultValue = z;
    }

    public List<String> getExcludeFields$realm() {
        return this.excludeFields;
    }

    public void setExcludeFields$realm(List<String> list) {
        this.excludeFields = list;
    }

    private void notifyQueryFinished() {
        this.observerPairs.foreach(queryCallback);
    }

    public void addChangeListener(RealmObjectChangeListener<E> realmObjectChangeListener) {
        if (this.row instanceof PendingRow) {
            this.observerPairs.add(new OsObject.ObjectObserverPair(this.model, realmObjectChangeListener));
        } else if (this.row instanceof UncheckedRow) {
            registerToObjectNotifier();
            if (this.osObject != null) {
                this.osObject.addListener(this.model, realmObjectChangeListener);
            }
        }
    }

    public void removeChangeListener(RealmObjectChangeListener<E> realmObjectChangeListener) {
        if (this.osObject != null) {
            this.osObject.removeListener(this.model, realmObjectChangeListener);
        } else {
            this.observerPairs.remove(this.model, realmObjectChangeListener);
        }
    }

    public void removeAllChangeListeners() {
        if (this.osObject != null) {
            this.osObject.removeListener(this.model);
        } else {
            this.observerPairs.clear();
        }
    }

    public boolean isUnderConstruction() {
        return this.underConstruction;
    }

    public void setConstructionFinished() {
        this.underConstruction = false;
        this.excludeFields = null;
    }

    private void registerToObjectNotifier() {
        if (this.realm.sharedRealm != null && !this.realm.sharedRealm.isClosed() && this.row.isAttached() && this.osObject == null) {
            this.osObject = new OsObject(this.realm.sharedRealm, (UncheckedRow) this.row);
            this.osObject.setObserverPairs(this.observerPairs);
            this.observerPairs = null;
        }
    }

    public boolean isLoaded() {
        return !(this.row instanceof PendingRow);
    }

    public void load() {
        if (this.row instanceof PendingRow) {
            ((PendingRow) this.row).executeQuery();
        }
    }

    @Override // io.realm.internal.PendingRow.FrontEnd
    public void onQueryFinished(Row row) {
        this.row = row;
        notifyQueryFinished();
        if (row.isAttached()) {
            registerToObjectNotifier();
        }
    }
}
