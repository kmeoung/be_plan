package io.realm;

import io.realm.RealmModel;
import io.realm.internal.ManagableObject;
import java.util.Collection;
import java.util.Date;

/* loaded from: classes.dex */
public interface RealmCollection<E extends RealmModel> extends Collection<E>, ManagableObject {
    double average(String str);

    @Override // java.util.Collection, java.util.List, io.realm.RealmCollection
    boolean contains(Object obj);

    boolean deleteAllFromRealm();

    boolean isLoaded();

    boolean isManaged();

    boolean isValid();

    boolean load();

    Number max(String str);

    Date maxDate(String str);

    Number min(String str);

    Date minDate(String str);

    Number sum(String str);

    RealmQuery<E> where();
}
