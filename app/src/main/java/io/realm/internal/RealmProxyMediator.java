package io.realm.internal;

import android.util.JsonReader;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.exceptions.RealmException;
import io.realm.internal.RealmObjectProxy;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public abstract class RealmProxyMediator {
    public abstract <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean z, Map<RealmModel, RealmObjectProxy> map);

    public abstract <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map);

    public abstract <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException;

    public abstract <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException;

    public abstract Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap();

    public abstract List<String> getFieldNames(Class<? extends RealmModel> cls);

    public abstract Set<Class<? extends RealmModel>> getModelClasses();

    public abstract String getTableName(Class<? extends RealmModel> cls);

    public abstract void insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map);

    public abstract void insert(Realm realm, Collection<? extends RealmModel> collection);

    public abstract void insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map);

    public abstract void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection);

    public abstract <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list);

    public boolean transformerApplied() {
        return false;
    }

    public abstract ColumnInfo validateTable(Class<? extends RealmModel> cls, SharedRealm sharedRealm, boolean z);

    public boolean equals(Object obj) {
        if (!(obj instanceof RealmProxyMediator)) {
            return false;
        }
        return getModelClasses().equals(((RealmProxyMediator) obj).getModelClasses());
    }

    public int hashCode() {
        return getModelClasses().hashCode();
    }

    public static void checkClass(Class<? extends RealmModel> cls) {
        if (cls == null) {
            throw new NullPointerException("A class extending RealmObject must be provided");
        }
    }

    public static RealmException getMissingProxyClassException(Class<? extends RealmModel> cls) {
        return new RealmException(cls + " is not part of the schema for this Realm.");
    }
}
