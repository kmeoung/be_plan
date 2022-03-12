package io.realm.internal.modules;

import android.util.JsonReader;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Util;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class CompositeMediator extends RealmProxyMediator {
    private final Map<Class<? extends RealmModel>, RealmProxyMediator> mediators;

    public CompositeMediator(RealmProxyMediator... realmProxyMediatorArr) {
        HashMap hashMap = new HashMap();
        if (realmProxyMediatorArr != null) {
            for (RealmProxyMediator realmProxyMediator : realmProxyMediatorArr) {
                for (Class<? extends RealmModel> cls : realmProxyMediator.getModelClasses()) {
                    hashMap.put(cls, realmProxyMediator);
                }
            }
        }
        this.mediators = Collections.unmodifiableMap(hashMap);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap hashMap = new HashMap();
        for (RealmProxyMediator realmProxyMediator : this.mediators.values()) {
            hashMap.putAll(realmProxyMediator.getExpectedObjectSchemaInfoMap());
        }
        return hashMap;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public ColumnInfo validateTable(Class<? extends RealmModel> cls, SharedRealm sharedRealm, boolean z) {
        return getMediator(cls).validateTable(cls, sharedRealm, z);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public List<String> getFieldNames(Class<? extends RealmModel> cls) {
        return getMediator(cls).getFieldNames(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public String getTableName(Class<? extends RealmModel> cls) {
        return getMediator(cls).getTableName(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
        return (E) getMediator(cls).newInstance(cls, obj, row, columnInfo, z, list);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return this.mediators.keySet();
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean z, Map<RealmModel, RealmObjectProxy> map) {
        return (E) getMediator(Util.getOriginalModelClass(e.getClass())).copyOrUpdate(realm, e, z, map);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        getMediator(Util.getOriginalModelClass(realmModel.getClass())).insert(realm, realmModel, map);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, Collection<? extends RealmModel> collection) {
        getMediator(Util.getOriginalModelClass(Util.getOriginalModelClass(((RealmModel) collection.iterator().next()).getClass()))).insert(realm, collection);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        getMediator(Util.getOriginalModelClass(realmModel.getClass())).insertOrUpdate(realm, realmModel, map);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection) {
        getMediator(Util.getOriginalModelClass(Util.getOriginalModelClass(((RealmModel) collection.iterator().next()).getClass()))).insertOrUpdate(realm, collection);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        return (E) getMediator(cls).createOrUpdateUsingJsonObject(cls, realm, jSONObject, z);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException {
        return (E) getMediator(cls).createUsingJsonStream(cls, realm, jsonReader);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        return (E) getMediator(Util.getOriginalModelClass(e.getClass())).createDetachedCopy(e, i, map);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public boolean transformerApplied() {
        for (Map.Entry<Class<? extends RealmModel>, RealmProxyMediator> entry : this.mediators.entrySet()) {
            if (!entry.getValue().transformerApplied()) {
                return false;
            }
        }
        return true;
    }

    private RealmProxyMediator getMediator(Class<? extends RealmModel> cls) {
        RealmProxyMediator realmProxyMediator = this.mediators.get(cls);
        if (realmProxyMediator != null) {
            return realmProxyMediator;
        }
        throw new IllegalArgumentException(cls.getSimpleName() + " is not part of the schema for this Realm");
    }
}
