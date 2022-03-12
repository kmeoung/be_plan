package io.realm;

import android.util.JsonReader;
import io.realm.BaseRealm;
import io.realm.annotations.RealmModule;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kr.timehub.beplan.entry.database.DbStopWatch;
import org.json.JSONException;
import org.json.JSONObject;

@RealmModule
/* loaded from: classes.dex */
class DefaultRealmModuleMediator extends RealmProxyMediator {
    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;

    @Override // io.realm.internal.RealmProxyMediator
    public boolean transformerApplied() {
        return true;
    }

    DefaultRealmModuleMediator() {
    }

    static {
        HashSet hashSet = new HashSet();
        hashSet.add(DbStopWatch.class);
        MODEL_CLASSES = Collections.unmodifiableSet(hashSet);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap hashMap = new HashMap();
        hashMap.put(DbStopWatch.class, DbStopWatchRealmProxy.getExpectedObjectSchemaInfo());
        return hashMap;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public ColumnInfo validateTable(Class<? extends RealmModel> cls, SharedRealm sharedRealm, boolean z) {
        checkClass(cls);
        if (cls.equals(DbStopWatch.class)) {
            return DbStopWatchRealmProxy.validateTable(sharedRealm, z);
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public List<String> getFieldNames(Class<? extends RealmModel> cls) {
        checkClass(cls);
        if (cls.equals(DbStopWatch.class)) {
            return DbStopWatchRealmProxy.getFieldNames();
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public String getTableName(Class<? extends RealmModel> cls) {
        checkClass(cls);
        if (cls.equals(DbStopWatch.class)) {
            return DbStopWatchRealmProxy.getTableName();
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        try {
            realmObjectContext.set((BaseRealm) obj, row, columnInfo, z, list);
            checkClass(cls);
            if (cls.equals(DbStopWatch.class)) {
                return cls.cast(new DbStopWatchRealmProxy());
            }
            throw getMissingProxyClassException(cls);
        } finally {
            realmObjectContext.clear();
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean z, Map<RealmModel, RealmObjectProxy> map) {
        Class<?> superclass = e instanceof RealmObjectProxy ? e.getClass().getSuperclass() : e.getClass();
        if (superclass.equals(DbStopWatch.class)) {
            return (E) ((RealmModel) superclass.cast(DbStopWatchRealmProxy.copyOrUpdate(realm, (DbStopWatch) e, z, map)));
        }
        throw getMissingProxyClassException(superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(DbStopWatch.class)) {
            DbStopWatchRealmProxy.insert(realm, (DbStopWatch) realmModel, map);
            return;
        }
        throw getMissingProxyClassException(superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap hashMap = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel realmModel = (RealmModel) it.next();
            Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
            if (superclass.equals(DbStopWatch.class)) {
                DbStopWatchRealmProxy.insert(realm, (DbStopWatch) realmModel, hashMap);
                if (!it.hasNext()) {
                    return;
                }
                if (superclass.equals(DbStopWatch.class)) {
                    DbStopWatchRealmProxy.insert(realm, it, hashMap);
                    return;
                }
                throw getMissingProxyClassException(superclass);
            }
            throw getMissingProxyClassException(superclass);
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(DbStopWatch.class)) {
            DbStopWatchRealmProxy.insertOrUpdate(realm, (DbStopWatch) realmModel, map);
            return;
        }
        throw getMissingProxyClassException(superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap hashMap = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel realmModel = (RealmModel) it.next();
            Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
            if (superclass.equals(DbStopWatch.class)) {
                DbStopWatchRealmProxy.insertOrUpdate(realm, (DbStopWatch) realmModel, hashMap);
                if (!it.hasNext()) {
                    return;
                }
                if (superclass.equals(DbStopWatch.class)) {
                    DbStopWatchRealmProxy.insertOrUpdate(realm, it, hashMap);
                    return;
                }
                throw getMissingProxyClassException(superclass);
            }
            throw getMissingProxyClassException(superclass);
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        checkClass(cls);
        if (cls.equals(DbStopWatch.class)) {
            return cls.cast(DbStopWatchRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException {
        checkClass(cls);
        if (cls.equals(DbStopWatch.class)) {
            return cls.cast(DbStopWatchRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Class<? super Object> superclass = e.getClass().getSuperclass();
        if (superclass.equals(DbStopWatch.class)) {
            return (E) ((RealmModel) superclass.cast(DbStopWatchRealmProxy.createDetachedCopy((DbStopWatch) e, 0, i, map)));
        }
        throw getMissingProxyClassException(superclass);
    }
}
