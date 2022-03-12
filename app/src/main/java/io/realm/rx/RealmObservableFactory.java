package io.realm.rx;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.IdentityHashMap;
import java.util.Map;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/* loaded from: classes.dex */
public class RealmObservableFactory implements RxObservableFactory {
    ThreadLocal<StrongReferenceCounter<RealmResults>> resultsRefs = new ThreadLocal<StrongReferenceCounter<RealmResults>>() { // from class: io.realm.rx.RealmObservableFactory.1
        @Override // java.lang.ThreadLocal
        public StrongReferenceCounter<RealmResults> initialValue() {
            return new StrongReferenceCounter<>();
        }
    };
    ThreadLocal<StrongReferenceCounter<RealmList>> listRefs = new ThreadLocal<StrongReferenceCounter<RealmList>>() { // from class: io.realm.rx.RealmObservableFactory.2
        @Override // java.lang.ThreadLocal
        public StrongReferenceCounter<RealmList> initialValue() {
            return new StrongReferenceCounter<>();
        }
    };
    ThreadLocal<StrongReferenceCounter<RealmModel>> objectRefs = new ThreadLocal<StrongReferenceCounter<RealmModel>>() { // from class: io.realm.rx.RealmObservableFactory.3
        @Override // java.lang.ThreadLocal
        public StrongReferenceCounter<RealmModel> initialValue() {
            return new StrongReferenceCounter<>();
        }
    };

    public int hashCode() {
        return 37;
    }

    @Override // io.realm.rx.RxObservableFactory
    public Observable<Realm> from(Realm realm) {
        final RealmConfiguration configuration = realm.getConfiguration();
        return Observable.create(new Observable.OnSubscribe<Realm>() { // from class: io.realm.rx.RealmObservableFactory.4
            public void call(final Subscriber<? super Realm> subscriber) {
                final Realm instance = Realm.getInstance(configuration);
                final RealmChangeListener<Realm> realmChangeListener = new RealmChangeListener<Realm>() { // from class: io.realm.rx.RealmObservableFactory.4.1
                    public void onChange(Realm realm2) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(instance);
                        }
                    }
                };
                instance.addChangeListener(realmChangeListener);
                subscriber.add(Subscriptions.create(new Action0() { // from class: io.realm.rx.RealmObservableFactory.4.2
                    public void call() {
                        instance.removeChangeListener(realmChangeListener);
                        instance.close();
                    }
                }));
                subscriber.onNext(instance);
            }
        });
    }

    @Override // io.realm.rx.RxObservableFactory
    public Observable<DynamicRealm> from(DynamicRealm dynamicRealm) {
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        return Observable.create(new Observable.OnSubscribe<DynamicRealm>() { // from class: io.realm.rx.RealmObservableFactory.5
            public void call(final Subscriber<? super DynamicRealm> subscriber) {
                final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                final RealmChangeListener<DynamicRealm> realmChangeListener = new RealmChangeListener<DynamicRealm>() { // from class: io.realm.rx.RealmObservableFactory.5.1
                    public void onChange(DynamicRealm dynamicRealm2) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(instance);
                        }
                    }
                };
                instance.addChangeListener(realmChangeListener);
                subscriber.add(Subscriptions.create(new Action0() { // from class: io.realm.rx.RealmObservableFactory.5.2
                    public void call() {
                        instance.removeChangeListener(realmChangeListener);
                        instance.close();
                    }
                }));
                subscriber.onNext(instance);
            }
        });
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E extends RealmModel> Observable<RealmResults<E>> from(Realm realm, final RealmResults<E> realmResults) {
        final RealmConfiguration configuration = realm.getConfiguration();
        return Observable.create(new Observable.OnSubscribe<RealmResults<E>>() { // from class: io.realm.rx.RealmObservableFactory.6
            public void call(final Subscriber<? super RealmResults<E>> subscriber) {
                final Realm instance = Realm.getInstance(configuration);
                RealmObservableFactory.this.resultsRefs.get().acquireReference(realmResults);
                final RealmChangeListener<RealmResults<E>> realmChangeListener = new RealmChangeListener<RealmResults<E>>() { // from class: io.realm.rx.RealmObservableFactory.6.1
                    public void onChange(RealmResults<E> realmResults2) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(realmResults);
                        }
                    }
                };
                realmResults.addChangeListener(realmChangeListener);
                subscriber.add(Subscriptions.create(new Action0() { // from class: io.realm.rx.RealmObservableFactory.6.2
                    public void call() {
                        realmResults.removeChangeListener(realmChangeListener);
                        instance.close();
                        RealmObservableFactory.this.resultsRefs.get().releaseReference(realmResults);
                    }
                }));
                subscriber.onNext(realmResults);
            }
        });
    }

    @Override // io.realm.rx.RxObservableFactory
    public Observable<RealmResults<DynamicRealmObject>> from(DynamicRealm dynamicRealm, final RealmResults<DynamicRealmObject> realmResults) {
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        return Observable.create(new Observable.OnSubscribe<RealmResults<DynamicRealmObject>>() { // from class: io.realm.rx.RealmObservableFactory.7
            public void call(final Subscriber<? super RealmResults<DynamicRealmObject>> subscriber) {
                final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                RealmObservableFactory.this.resultsRefs.get().acquireReference(realmResults);
                final RealmChangeListener<RealmResults<DynamicRealmObject>> realmChangeListener = new RealmChangeListener<RealmResults<DynamicRealmObject>>() { // from class: io.realm.rx.RealmObservableFactory.7.1
                    public void onChange(RealmResults<DynamicRealmObject> realmResults2) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(realmResults);
                        }
                    }
                };
                realmResults.addChangeListener(realmChangeListener);
                subscriber.add(Subscriptions.create(new Action0() { // from class: io.realm.rx.RealmObservableFactory.7.2
                    public void call() {
                        realmResults.removeChangeListener(realmChangeListener);
                        instance.close();
                        RealmObservableFactory.this.resultsRefs.get().releaseReference(realmResults);
                    }
                }));
                subscriber.onNext(realmResults);
            }
        });
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E extends RealmModel> Observable<RealmList<E>> from(Realm realm, final RealmList<E> realmList) {
        final RealmConfiguration configuration = realm.getConfiguration();
        return Observable.create(new Observable.OnSubscribe<RealmList<E>>() { // from class: io.realm.rx.RealmObservableFactory.8
            public void call(final Subscriber<? super RealmList<E>> subscriber) {
                final Realm instance = Realm.getInstance(configuration);
                RealmObservableFactory.this.listRefs.get().acquireReference(realmList);
                final RealmChangeListener<RealmList<E>> realmChangeListener = new RealmChangeListener<RealmList<E>>() { // from class: io.realm.rx.RealmObservableFactory.8.1
                    public void onChange(RealmList<E> realmList2) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(realmList);
                        }
                    }
                };
                realmList.addChangeListener(realmChangeListener);
                subscriber.add(Subscriptions.create(new Action0() { // from class: io.realm.rx.RealmObservableFactory.8.2
                    public void call() {
                        realmList.removeChangeListener(realmChangeListener);
                        instance.close();
                        RealmObservableFactory.this.listRefs.get().releaseReference(realmList);
                    }
                }));
                subscriber.onNext(realmList);
            }
        });
    }

    @Override // io.realm.rx.RxObservableFactory
    public Observable<RealmList<DynamicRealmObject>> from(DynamicRealm dynamicRealm, final RealmList<DynamicRealmObject> realmList) {
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        return Observable.create(new Observable.OnSubscribe<RealmList<DynamicRealmObject>>() { // from class: io.realm.rx.RealmObservableFactory.9
            public void call(final Subscriber<? super RealmList<DynamicRealmObject>> subscriber) {
                final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                RealmObservableFactory.this.listRefs.get().acquireReference(realmList);
                final RealmChangeListener<RealmList<DynamicRealmObject>> realmChangeListener = new RealmChangeListener<RealmList<DynamicRealmObject>>() { // from class: io.realm.rx.RealmObservableFactory.9.1
                    public void onChange(RealmList<DynamicRealmObject> realmList2) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(realmList);
                        }
                    }
                };
                realmList.addChangeListener(realmChangeListener);
                subscriber.add(Subscriptions.create(new Action0() { // from class: io.realm.rx.RealmObservableFactory.9.2
                    public void call() {
                        realmList.removeChangeListener(realmChangeListener);
                        instance.close();
                        RealmObservableFactory.this.listRefs.get().releaseReference(realmList);
                    }
                }));
                subscriber.onNext(realmList);
            }
        });
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E extends RealmModel> Observable<E> from(Realm realm, final E e) {
        final RealmConfiguration configuration = realm.getConfiguration();
        return Observable.create(new Observable.OnSubscribe<E>() { // from class: io.realm.rx.RealmObservableFactory.10
            public void call(final Subscriber<? super E> subscriber) {
                final Realm instance = Realm.getInstance(configuration);
                RealmObservableFactory.this.objectRefs.get().acquireReference(e);
                final RealmChangeListener<E> realmChangeListener = new RealmChangeListener<E>() { // from class: io.realm.rx.RealmObservableFactory.10.1
                    public void onChange(RealmModel realmModel) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(realmModel);
                        }
                    }
                };
                RealmObject.addChangeListener(e, (RealmChangeListener<RealmModel>) realmChangeListener);
                subscriber.add(Subscriptions.create(new Action0() { // from class: io.realm.rx.RealmObservableFactory.10.2
                    public void call() {
                        RealmObject.removeChangeListener(e, realmChangeListener);
                        instance.close();
                        RealmObservableFactory.this.objectRefs.get().releaseReference(e);
                    }
                }));
                subscriber.onNext(e);
            }
        });
    }

    @Override // io.realm.rx.RxObservableFactory
    public Observable<DynamicRealmObject> from(DynamicRealm dynamicRealm, final DynamicRealmObject dynamicRealmObject) {
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        return Observable.create(new Observable.OnSubscribe<DynamicRealmObject>() { // from class: io.realm.rx.RealmObservableFactory.11
            public void call(final Subscriber<? super DynamicRealmObject> subscriber) {
                final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                RealmObservableFactory.this.objectRefs.get().acquireReference(dynamicRealmObject);
                final RealmChangeListener<DynamicRealmObject> realmChangeListener = new RealmChangeListener<DynamicRealmObject>() { // from class: io.realm.rx.RealmObservableFactory.11.1
                    public void onChange(DynamicRealmObject dynamicRealmObject2) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(dynamicRealmObject2);
                        }
                    }
                };
                RealmObject.addChangeListener(dynamicRealmObject, realmChangeListener);
                subscriber.add(Subscriptions.create(new Action0() { // from class: io.realm.rx.RealmObservableFactory.11.2
                    public void call() {
                        RealmObject.removeChangeListener(dynamicRealmObject, realmChangeListener);
                        instance.close();
                        RealmObservableFactory.this.objectRefs.get().releaseReference(dynamicRealmObject);
                    }
                }));
                subscriber.onNext(dynamicRealmObject);
            }
        });
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E extends RealmModel> Observable<RealmQuery<E>> from(Realm realm, RealmQuery<E> realmQuery) {
        throw new RuntimeException("RealmQuery not supported yet.");
    }

    @Override // io.realm.rx.RxObservableFactory
    public Observable<RealmQuery<DynamicRealmObject>> from(DynamicRealm dynamicRealm, RealmQuery<DynamicRealmObject> realmQuery) {
        throw new RuntimeException("RealmQuery not supported yet.");
    }

    public boolean equals(Object obj) {
        return obj instanceof RealmObservableFactory;
    }

    /* loaded from: classes.dex */
    public static class StrongReferenceCounter<K> {
        private final Map<K, Integer> references;

        private StrongReferenceCounter() {
            this.references = new IdentityHashMap();
        }

        public void acquireReference(K k) {
            Integer num = this.references.get(k);
            if (num == null) {
                this.references.put(k, 1);
            } else {
                this.references.put(k, Integer.valueOf(num.intValue() + 1));
            }
        }

        public void releaseReference(K k) {
            Integer num = this.references.get(k);
            if (num == null) {
                throw new IllegalStateException("Object does not have any references: " + k);
            } else if (num.intValue() > 1) {
                this.references.put(k, Integer.valueOf(num.intValue() - 1));
            } else if (num.intValue() == 1) {
                this.references.remove(k);
            } else {
                throw new IllegalStateException("Invalid reference count: " + num);
            }
        }
    }
}
