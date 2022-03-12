package io.realm;

import io.realm.ProxyState;
import io.realm.annotations.RealmClass;
import io.realm.internal.InvalidRow;
import io.realm.internal.ManagableObject;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import rx.Observable;

@RealmClass
/* loaded from: classes.dex */
public abstract class RealmObject implements RealmModel, ManagableObject {
    public final void deleteFromRealm() {
        deleteFromRealm(this);
    }

    public static <E extends RealmModel> void deleteFromRealm(E e) {
        if (!(e instanceof RealmObjectProxy)) {
            throw new IllegalArgumentException("Object not managed by Realm, so it cannot be removed.");
        }
        RealmObjectProxy realmObjectProxy = (RealmObjectProxy) e;
        if (realmObjectProxy.realmGet$proxyState().getRow$realm() == null) {
            throw new IllegalStateException("Object malformed: missing object in Realm. Make sure to instantiate RealmObjects with Realm.createObject()");
        } else if (realmObjectProxy.realmGet$proxyState().getRealm$realm() == null) {
            throw new IllegalStateException("Object malformed: missing Realm. Make sure to instantiate RealmObjects with Realm.createObject()");
        } else {
            realmObjectProxy.realmGet$proxyState().getRealm$realm().checkIfValid();
            Row row$realm = realmObjectProxy.realmGet$proxyState().getRow$realm();
            row$realm.getTable().moveLastOver(row$realm.getIndex());
            realmObjectProxy.realmGet$proxyState().setRow$realm(InvalidRow.INSTANCE);
        }
    }

    @Override // io.realm.internal.ManagableObject
    public final boolean isValid() {
        return isValid(this);
    }

    public static <E extends RealmModel> boolean isValid(E e) {
        if (!(e instanceof RealmObjectProxy)) {
            return true;
        }
        Row row$realm = ((RealmObjectProxy) e).realmGet$proxyState().getRow$realm();
        return row$realm != null && row$realm.isAttached();
    }

    public final boolean isLoaded() {
        return isLoaded(this);
    }

    public static <E extends RealmModel> boolean isLoaded(E e) {
        if (!(e instanceof RealmObjectProxy)) {
            return true;
        }
        RealmObjectProxy realmObjectProxy = (RealmObjectProxy) e;
        realmObjectProxy.realmGet$proxyState().getRealm$realm().checkIfValid();
        return realmObjectProxy.realmGet$proxyState().isLoaded();
    }

    @Override // io.realm.internal.ManagableObject
    public boolean isManaged() {
        return isManaged(this);
    }

    public static <E extends RealmModel> boolean isManaged(E e) {
        return e instanceof RealmObjectProxy;
    }

    public final boolean load() {
        return load(this);
    }

    public static <E extends RealmModel> boolean load(E e) {
        if (isLoaded(e)) {
            return true;
        }
        if (!(e instanceof RealmObjectProxy)) {
            return false;
        }
        ((RealmObjectProxy) e).realmGet$proxyState().load();
        return true;
    }

    public final <E extends RealmModel> void addChangeListener(RealmObjectChangeListener<E> realmObjectChangeListener) {
        addChangeListener(this, (RealmObjectChangeListener<RealmObject>) realmObjectChangeListener);
    }

    public final <E extends RealmModel> void addChangeListener(RealmChangeListener<E> realmChangeListener) {
        addChangeListener(this, (RealmChangeListener<RealmObject>) realmChangeListener);
    }

    public static <E extends RealmModel> void addChangeListener(E e, RealmObjectChangeListener<E> realmObjectChangeListener) {
        if (e == null) {
            throw new IllegalArgumentException("Object should not be null");
        } else if (realmObjectChangeListener == null) {
            throw new IllegalArgumentException("Listener should not be null");
        } else if (e instanceof RealmObjectProxy) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) e;
            BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
            realm$realm.checkIfValid();
            realm$realm.sharedRealm.capabilities.checkCanDeliverNotification("Listeners cannot be used on current thread.");
            realmObjectProxy.realmGet$proxyState().addChangeListener(realmObjectChangeListener);
        } else {
            throw new IllegalArgumentException("Cannot add listener from this unmanaged RealmObject (created outside of Realm)");
        }
    }

    public static <E extends RealmModel> void addChangeListener(E e, RealmChangeListener<E> realmChangeListener) {
        addChangeListener(e, new ProxyState.RealmChangeListenerWrapper(realmChangeListener));
    }

    public final void removeChangeListener(RealmObjectChangeListener realmObjectChangeListener) {
        removeChangeListener(this, realmObjectChangeListener);
    }

    public final void removeChangeListener(RealmChangeListener realmChangeListener) {
        removeChangeListener(this, realmChangeListener);
    }

    public static <E extends RealmModel> void removeChangeListener(E e, RealmObjectChangeListener realmObjectChangeListener) {
        if (e == null) {
            throw new IllegalArgumentException("Object should not be null");
        } else if (realmObjectChangeListener == null) {
            throw new IllegalArgumentException("Listener should not be null");
        } else if (e instanceof RealmObjectProxy) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) e;
            BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
            realm$realm.checkIfValid();
            realm$realm.sharedRealm.capabilities.checkCanDeliverNotification("Listeners cannot be used on current thread.");
            realmObjectProxy.realmGet$proxyState().removeChangeListener(realmObjectChangeListener);
        } else {
            throw new IllegalArgumentException("Cannot remove listener from this unmanaged RealmObject (created outside of Realm)");
        }
    }

    public static <E extends RealmModel> void removeChangeListener(E e, RealmChangeListener<E> realmChangeListener) {
        removeChangeListener(e, new ProxyState.RealmChangeListenerWrapper(realmChangeListener));
    }

    @Deprecated
    public final void removeChangeListeners() {
        removeChangeListeners(this);
    }

    public final void removeAllChangeListeners() {
        removeAllChangeListeners(this);
    }

    @Deprecated
    public static <E extends RealmModel> void removeChangeListeners(E e) {
        removeAllChangeListeners(e);
    }

    public static <E extends RealmModel> void removeAllChangeListeners(E e) {
        if (e instanceof RealmObjectProxy) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) e;
            BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
            realm$realm.checkIfValid();
            realm$realm.sharedRealm.capabilities.checkCanDeliverNotification("Listeners cannot be used on current thread.");
            realmObjectProxy.realmGet$proxyState().removeAllChangeListeners();
            return;
        }
        throw new IllegalArgumentException("Cannot remove listeners from this unmanaged RealmObject (created outside of Realm)");
    }

    public final <E extends RealmObject> Observable<E> asObservable() {
        return asObservable(this);
    }

    public static <E extends RealmModel> Observable<E> asObservable(E e) {
        if (e instanceof RealmObjectProxy) {
            BaseRealm realm$realm = ((RealmObjectProxy) e).realmGet$proxyState().getRealm$realm();
            if (realm$realm instanceof Realm) {
                return realm$realm.configuration.getRxFactory().from((Realm) realm$realm, (Realm) e);
            }
            if (realm$realm instanceof DynamicRealm) {
                return realm$realm.configuration.getRxFactory().from((DynamicRealm) realm$realm, (DynamicRealmObject) e);
            }
            throw new UnsupportedOperationException(realm$realm.getClass() + " does not support RxJava. See https://realm.io/docs/java/latest/#rxjava for more details.");
        }
        throw new IllegalArgumentException("Cannot create Observables from unmanaged RealmObjects");
    }
}
