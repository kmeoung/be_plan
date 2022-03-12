package io.realm;

import io.realm.RealmModel;
import io.realm.internal.Collection;
import io.realm.internal.LinkView;
import io.realm.internal.PendingRow;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SortDescriptor;
import io.realm.internal.Table;
import io.realm.internal.TableQuery;
import io.realm.internal.fields.FieldDescriptor;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes.dex */
public class RealmQuery<E extends RealmModel> {
    private static final String ASYNC_QUERY_WRONG_THREAD_MESSAGE = "Async query cannot be created on current thread.";
    private static final String EMPTY_VALUES = "Non-empty 'values' must be provided.";
    private static final String TYPE_MISMATCH = "Field '%s': type mismatch - %s expected.";
    private String className;
    private Class<E> clazz;
    private LinkView linkView;
    private final TableQuery query;
    private final BaseRealm realm;
    private final RealmObjectSchema schema;
    private final Table table;

    public static <E extends RealmModel> RealmQuery<E> createQuery(Realm realm, Class<E> cls) {
        return new RealmQuery<>(realm, cls);
    }

    public static <E extends RealmModel> RealmQuery<E> createDynamicQuery(DynamicRealm dynamicRealm, String str) {
        return new RealmQuery<>(dynamicRealm, str);
    }

    public static <E extends RealmModel> RealmQuery<E> createQueryFromResult(RealmResults<E> realmResults) {
        if (realmResults.classSpec == null) {
            return new RealmQuery<>((RealmResults<DynamicRealmObject>) realmResults, realmResults.className);
        }
        return new RealmQuery<>(realmResults, realmResults.classSpec);
    }

    public static <E extends RealmModel> RealmQuery<E> createQueryFromList(RealmList<E> realmList) {
        if (realmList.clazz == null) {
            return new RealmQuery<>(realmList.realm, realmList.view, realmList.className);
        }
        return new RealmQuery<>(realmList.realm, realmList.view, realmList.clazz);
    }

    private RealmQuery(Realm realm, Class<E> cls) {
        this.realm = realm;
        this.clazz = cls;
        this.schema = realm.getSchema().getSchemaForClass(cls);
        this.table = this.schema.getTable();
        this.linkView = null;
        this.query = this.table.where();
    }

    private RealmQuery(RealmResults<E> realmResults, Class<E> cls) {
        this.realm = realmResults.realm;
        this.clazz = cls;
        this.schema = this.realm.getSchema().getSchemaForClass(cls);
        this.table = realmResults.getTable();
        this.linkView = null;
        this.query = realmResults.getCollection().where();
    }

    private RealmQuery(BaseRealm baseRealm, LinkView linkView, Class<E> cls) {
        this.realm = baseRealm;
        this.clazz = cls;
        this.schema = baseRealm.getSchema().getSchemaForClass(cls);
        this.table = this.schema.getTable();
        this.linkView = linkView;
        this.query = linkView.where();
    }

    private RealmQuery(BaseRealm baseRealm, String str) {
        this.realm = baseRealm;
        this.className = str;
        this.schema = baseRealm.getSchema().getSchemaForClass(str);
        this.table = this.schema.getTable();
        this.query = this.table.where();
    }

    private RealmQuery(RealmResults<DynamicRealmObject> realmResults, String str) {
        this.realm = realmResults.realm;
        this.className = str;
        this.schema = this.realm.getSchema().getSchemaForClass(str);
        this.table = this.schema.getTable();
        this.query = realmResults.getCollection().where();
    }

    private RealmQuery(BaseRealm baseRealm, LinkView linkView, String str) {
        this.realm = baseRealm;
        this.className = str;
        this.schema = baseRealm.getSchema().getSchemaForClass(str);
        this.table = this.schema.getTable();
        this.linkView = linkView;
        this.query = linkView.where();
    }

    public boolean isValid() {
        if (this.realm == null || this.realm.isClosed()) {
            return false;
        }
        if (this.linkView != null) {
            return this.linkView.isAttached();
        }
        return this.table != null && this.table.isValid();
    }

    public RealmQuery<E> isNull(String str) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, new RealmFieldType[0]);
        this.query.isNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        return this;
    }

    public RealmQuery<E> isNotNull(String str) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, new RealmFieldType[0]);
        this.query.isNotNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        return this;
    }

    public RealmQuery<E> equalTo(String str, String str2) {
        return equalTo(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> equalTo(String str, String str2, Case r4) {
        this.realm.checkIfValid();
        return equalToWithoutThreadValidation(str, str2, r4);
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String str, String str2, Case r7) {
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.STRING);
        this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), str2, r7);
        return this;
    }

    public RealmQuery<E> equalTo(String str, Byte b) {
        this.realm.checkIfValid();
        return equalToWithoutThreadValidation(str, b);
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String str, Byte b) {
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        if (b == null) {
            this.query.isNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), b.byteValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, byte[] bArr) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.BINARY);
        if (bArr == null) {
            this.query.isNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), bArr);
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, Short sh) {
        this.realm.checkIfValid();
        return equalToWithoutThreadValidation(str, sh);
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String str, Short sh) {
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        if (sh == null) {
            this.query.isNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), sh.shortValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, Integer num) {
        this.realm.checkIfValid();
        return equalToWithoutThreadValidation(str, num);
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String str, Integer num) {
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        if (num == null) {
            this.query.isNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), num.intValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, Long l) {
        this.realm.checkIfValid();
        return equalToWithoutThreadValidation(str, l);
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String str, Long l) {
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        if (l == null) {
            this.query.isNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), l.longValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, Double d) {
        this.realm.checkIfValid();
        return equalToWithoutThreadValidation(str, d);
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String str, Double d) {
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DOUBLE);
        if (d == null) {
            this.query.isNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), d.doubleValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, Float f) {
        this.realm.checkIfValid();
        return equalToWithoutThreadValidation(str, f);
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String str, Float f) {
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.FLOAT);
        if (f == null) {
            this.query.isNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), f.floatValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, Boolean bool) {
        this.realm.checkIfValid();
        return equalToWithoutThreadValidation(str, bool);
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String str, Boolean bool) {
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.BOOLEAN);
        if (bool == null) {
            this.query.isNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), bool.booleanValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, Date date) {
        this.realm.checkIfValid();
        return equalToWithoutThreadValidation(str, date);
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String str, Date date) {
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DATE);
        this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), date);
        return this;
    }

    public RealmQuery<E> in(String str, String[] strArr) {
        return in(str, strArr, Case.SENSITIVE);
    }

    public RealmQuery<E> in(String str, String[] strArr, Case r6) {
        this.realm.checkIfValid();
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException(EMPTY_VALUES);
        }
        beginGroupWithoutThreadValidation().equalToWithoutThreadValidation(str, strArr[0], r6);
        for (int i = 1; i < strArr.length; i++) {
            orWithoutThreadValidation().equalToWithoutThreadValidation(str, strArr[i], r6);
        }
        return endGroupWithoutThreadValidation();
    }

    public RealmQuery<E> in(String str, Byte[] bArr) {
        this.realm.checkIfValid();
        if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException(EMPTY_VALUES);
        }
        beginGroupWithoutThreadValidation().equalToWithoutThreadValidation(str, bArr[0]);
        for (int i = 1; i < bArr.length; i++) {
            orWithoutThreadValidation().equalToWithoutThreadValidation(str, bArr[i]);
        }
        return endGroupWithoutThreadValidation();
    }

    public RealmQuery<E> in(String str, Short[] shArr) {
        this.realm.checkIfValid();
        if (shArr == null || shArr.length == 0) {
            throw new IllegalArgumentException(EMPTY_VALUES);
        }
        beginGroupWithoutThreadValidation().equalToWithoutThreadValidation(str, shArr[0]);
        for (int i = 1; i < shArr.length; i++) {
            orWithoutThreadValidation().equalToWithoutThreadValidation(str, shArr[i]);
        }
        return endGroupWithoutThreadValidation();
    }

    public RealmQuery<E> in(String str, Integer[] numArr) {
        this.realm.checkIfValid();
        if (numArr == null || numArr.length == 0) {
            throw new IllegalArgumentException(EMPTY_VALUES);
        }
        beginGroupWithoutThreadValidation().equalToWithoutThreadValidation(str, numArr[0]);
        for (int i = 1; i < numArr.length; i++) {
            orWithoutThreadValidation().equalToWithoutThreadValidation(str, numArr[i]);
        }
        return endGroupWithoutThreadValidation();
    }

    public RealmQuery<E> in(String str, Long[] lArr) {
        this.realm.checkIfValid();
        if (lArr == null || lArr.length == 0) {
            throw new IllegalArgumentException(EMPTY_VALUES);
        }
        beginGroupWithoutThreadValidation().equalToWithoutThreadValidation(str, lArr[0]);
        for (int i = 1; i < lArr.length; i++) {
            orWithoutThreadValidation().equalToWithoutThreadValidation(str, lArr[i]);
        }
        return endGroupWithoutThreadValidation();
    }

    public RealmQuery<E> in(String str, Double[] dArr) {
        this.realm.checkIfValid();
        if (dArr == null || dArr.length == 0) {
            throw new IllegalArgumentException(EMPTY_VALUES);
        }
        beginGroupWithoutThreadValidation().equalToWithoutThreadValidation(str, dArr[0]);
        for (int i = 1; i < dArr.length; i++) {
            orWithoutThreadValidation().equalToWithoutThreadValidation(str, dArr[i]);
        }
        return endGroupWithoutThreadValidation();
    }

    public RealmQuery<E> in(String str, Float[] fArr) {
        this.realm.checkIfValid();
        if (fArr == null || fArr.length == 0) {
            throw new IllegalArgumentException(EMPTY_VALUES);
        }
        beginGroupWithoutThreadValidation().equalToWithoutThreadValidation(str, fArr[0]);
        for (int i = 1; i < fArr.length; i++) {
            orWithoutThreadValidation().equalToWithoutThreadValidation(str, fArr[i]);
        }
        return endGroupWithoutThreadValidation();
    }

    public RealmQuery<E> in(String str, Boolean[] boolArr) {
        this.realm.checkIfValid();
        if (boolArr == null || boolArr.length == 0) {
            throw new IllegalArgumentException(EMPTY_VALUES);
        }
        beginGroupWithoutThreadValidation().equalToWithoutThreadValidation(str, boolArr[0]);
        for (int i = 1; i < boolArr.length; i++) {
            orWithoutThreadValidation().equalToWithoutThreadValidation(str, boolArr[i]);
        }
        return endGroupWithoutThreadValidation();
    }

    public RealmQuery<E> in(String str, Date[] dateArr) {
        this.realm.checkIfValid();
        if (dateArr == null || dateArr.length == 0) {
            throw new IllegalArgumentException(EMPTY_VALUES);
        }
        beginGroupWithoutThreadValidation().equalToWithoutThreadValidation(str, dateArr[0]);
        for (int i = 1; i < dateArr.length; i++) {
            orWithoutThreadValidation().equalToWithoutThreadValidation(str, dateArr[i]);
        }
        return endGroupWithoutThreadValidation();
    }

    public RealmQuery<E> notEqualTo(String str, String str2) {
        return notEqualTo(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> notEqualTo(String str, String str2, Case r8) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.STRING);
        if (columnIndices.length() <= 1 || r8.getValue()) {
            this.query.notEqualTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), str2, r8);
            return this;
        }
        throw new IllegalArgumentException("Link queries cannot be case insensitive - coming soon.");
    }

    public RealmQuery<E> notEqualTo(String str, Byte b) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        if (b == null) {
            this.query.isNotNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.notEqualTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), b.byteValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, byte[] bArr) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.BINARY);
        if (bArr == null) {
            this.query.isNotNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.notEqualTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), bArr);
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, Short sh) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        if (sh == null) {
            this.query.isNotNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.notEqualTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), sh.shortValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, Integer num) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        if (num == null) {
            this.query.isNotNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.notEqualTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), num.intValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, Long l) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        if (l == null) {
            this.query.isNotNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.notEqualTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), l.longValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, Double d) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DOUBLE);
        if (d == null) {
            this.query.isNotNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.notEqualTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), d.doubleValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, Float f) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.FLOAT);
        if (f == null) {
            this.query.isNotNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.notEqualTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), f.floatValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, Boolean bool) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.BOOLEAN);
        if (bool == null) {
            this.query.isNotNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.equalTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), !bool.booleanValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, Date date) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DATE);
        if (date == null) {
            this.query.isNotNull(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        } else {
            this.query.notEqualTo(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), date);
        }
        return this;
    }

    public RealmQuery<E> greaterThan(String str, int i) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        this.query.greaterThan(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), i);
        return this;
    }

    public RealmQuery<E> greaterThan(String str, long j) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        this.query.greaterThan(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), j);
        return this;
    }

    public RealmQuery<E> greaterThan(String str, double d) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DOUBLE);
        this.query.greaterThan(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), d);
        return this;
    }

    public RealmQuery<E> greaterThan(String str, float f) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.FLOAT);
        this.query.greaterThan(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), f);
        return this;
    }

    public RealmQuery<E> greaterThan(String str, Date date) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DATE);
        this.query.greaterThan(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), date);
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, int i) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        this.query.greaterThanOrEqual(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), i);
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, long j) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        this.query.greaterThanOrEqual(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), j);
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, double d) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DOUBLE);
        this.query.greaterThanOrEqual(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), d);
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, float f) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.FLOAT);
        this.query.greaterThanOrEqual(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), f);
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, Date date) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DATE);
        this.query.greaterThanOrEqual(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), date);
        return this;
    }

    public RealmQuery<E> lessThan(String str, int i) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        this.query.lessThan(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), i);
        return this;
    }

    public RealmQuery<E> lessThan(String str, long j) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        this.query.lessThan(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), j);
        return this;
    }

    public RealmQuery<E> lessThan(String str, double d) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DOUBLE);
        this.query.lessThan(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), d);
        return this;
    }

    public RealmQuery<E> lessThan(String str, float f) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.FLOAT);
        this.query.lessThan(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), f);
        return this;
    }

    public RealmQuery<E> lessThan(String str, Date date) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DATE);
        this.query.lessThan(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), date);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, int i) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        this.query.lessThanOrEqual(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), i);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, long j) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.INTEGER);
        this.query.lessThanOrEqual(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), j);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, double d) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DOUBLE);
        this.query.lessThanOrEqual(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), d);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, float f) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.FLOAT);
        this.query.lessThanOrEqual(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), f);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, Date date) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.DATE);
        this.query.lessThanOrEqual(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), date);
        return this;
    }

    public RealmQuery<E> between(String str, int i, int i2) {
        this.realm.checkIfValid();
        this.query.between(this.schema.getColumnIndices(str, RealmFieldType.INTEGER).getColumnIndices(), i, i2);
        return this;
    }

    public RealmQuery<E> between(String str, long j, long j2) {
        this.realm.checkIfValid();
        this.query.between(this.schema.getColumnIndices(str, RealmFieldType.INTEGER).getColumnIndices(), j, j2);
        return this;
    }

    public RealmQuery<E> between(String str, double d, double d2) {
        this.realm.checkIfValid();
        this.query.between(this.schema.getColumnIndices(str, RealmFieldType.DOUBLE).getColumnIndices(), d, d2);
        return this;
    }

    public RealmQuery<E> between(String str, float f, float f2) {
        this.realm.checkIfValid();
        this.query.between(this.schema.getColumnIndices(str, RealmFieldType.FLOAT).getColumnIndices(), f, f2);
        return this;
    }

    public RealmQuery<E> between(String str, Date date, Date date2) {
        this.realm.checkIfValid();
        this.query.between(this.schema.getColumnIndices(str, RealmFieldType.DATE).getColumnIndices(), date, date2);
        return this;
    }

    public RealmQuery<E> contains(String str, String str2) {
        return contains(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> contains(String str, String str2, Case r7) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.STRING);
        this.query.contains(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), str2, r7);
        return this;
    }

    public RealmQuery<E> beginsWith(String str, String str2) {
        return beginsWith(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> beginsWith(String str, String str2, Case r7) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.STRING);
        this.query.beginsWith(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), str2, r7);
        return this;
    }

    public RealmQuery<E> endsWith(String str, String str2) {
        return endsWith(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> endsWith(String str, String str2, Case r7) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.STRING);
        this.query.endsWith(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), str2, r7);
        return this;
    }

    public RealmQuery<E> like(String str, String str2) {
        return like(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> like(String str, String str2, Case r7) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.STRING);
        this.query.like(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers(), str2, r7);
        return this;
    }

    public RealmQuery<E> beginGroup() {
        this.realm.checkIfValid();
        return beginGroupWithoutThreadValidation();
    }

    private RealmQuery<E> beginGroupWithoutThreadValidation() {
        this.query.group();
        return this;
    }

    public RealmQuery<E> endGroup() {
        this.realm.checkIfValid();
        return endGroupWithoutThreadValidation();
    }

    private RealmQuery<E> endGroupWithoutThreadValidation() {
        this.query.endGroup();
        return this;
    }

    public RealmQuery<E> or() {
        this.realm.checkIfValid();
        return orWithoutThreadValidation();
    }

    private RealmQuery<E> orWithoutThreadValidation() {
        this.query.or();
        return this;
    }

    public RealmQuery<E> not() {
        this.realm.checkIfValid();
        this.query.not();
        return this;
    }

    public RealmQuery<E> isEmpty(String str) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.STRING, RealmFieldType.BINARY, RealmFieldType.LIST, RealmFieldType.LINKING_OBJECTS);
        this.query.isEmpty(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        return this;
    }

    public RealmQuery<E> isNotEmpty(String str) {
        this.realm.checkIfValid();
        FieldDescriptor columnIndices = this.schema.getColumnIndices(str, RealmFieldType.STRING, RealmFieldType.BINARY, RealmFieldType.LIST, RealmFieldType.LINKING_OBJECTS);
        this.query.isNotEmpty(columnIndices.getColumnIndices(), columnIndices.getNativeTablePointers());
        return this;
    }

    public RealmResults<E> distinct(String str) {
        this.realm.checkIfValid();
        return createRealmResults(this.query, null, SortDescriptor.getInstanceForDistinct(getSchemaConnector(), this.query.getTable(), str), true);
    }

    public RealmResults<E> distinctAsync(String str) {
        this.realm.checkIfValid();
        this.realm.sharedRealm.capabilities.checkCanDeliverNotification(ASYNC_QUERY_WRONG_THREAD_MESSAGE);
        return createRealmResults(this.query, null, SortDescriptor.getInstanceForDistinct(getSchemaConnector(), this.query.getTable(), str), false);
    }

    public RealmResults<E> distinct(String str, String... strArr) {
        this.realm.checkIfValid();
        String[] strArr2 = new String[strArr.length + 1];
        strArr2[0] = str;
        System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
        return createRealmResults(this.query, null, SortDescriptor.getInstanceForDistinct(getSchemaConnector(), this.table, strArr2), true);
    }

    public Number sum(String str) {
        this.realm.checkIfValid();
        long andCheckFieldIndex = this.schema.getAndCheckFieldIndex(str);
        switch (this.table.getColumnType(andCheckFieldIndex)) {
            case INTEGER:
                return Long.valueOf(this.query.sumInt(andCheckFieldIndex));
            case FLOAT:
                return Double.valueOf(this.query.sumFloat(andCheckFieldIndex));
            case DOUBLE:
                return Double.valueOf(this.query.sumDouble(andCheckFieldIndex));
            default:
                throw new IllegalArgumentException(String.format(Locale.US, TYPE_MISMATCH, str, "int, float or double"));
        }
    }

    public double average(String str) {
        this.realm.checkIfValid();
        long andCheckFieldIndex = this.schema.getAndCheckFieldIndex(str);
        switch (this.table.getColumnType(andCheckFieldIndex)) {
            case INTEGER:
                return this.query.averageInt(andCheckFieldIndex);
            case FLOAT:
                return this.query.averageFloat(andCheckFieldIndex);
            case DOUBLE:
                return this.query.averageDouble(andCheckFieldIndex);
            default:
                throw new IllegalArgumentException(String.format(Locale.US, TYPE_MISMATCH, str, "int, float or double"));
        }
    }

    public Number min(String str) {
        this.realm.checkIfValid();
        long andCheckFieldIndex = this.schema.getAndCheckFieldIndex(str);
        switch (this.table.getColumnType(andCheckFieldIndex)) {
            case INTEGER:
                return this.query.minimumInt(andCheckFieldIndex);
            case FLOAT:
                return this.query.minimumFloat(andCheckFieldIndex);
            case DOUBLE:
                return this.query.minimumDouble(andCheckFieldIndex);
            default:
                throw new IllegalArgumentException(String.format(Locale.US, TYPE_MISMATCH, str, "int, float or double"));
        }
    }

    public Date minimumDate(String str) {
        this.realm.checkIfValid();
        return this.query.minimumDate(this.schema.getAndCheckFieldIndex(str));
    }

    public Number max(String str) {
        this.realm.checkIfValid();
        long andCheckFieldIndex = this.schema.getAndCheckFieldIndex(str);
        switch (this.table.getColumnType(andCheckFieldIndex)) {
            case INTEGER:
                return this.query.maximumInt(andCheckFieldIndex);
            case FLOAT:
                return this.query.maximumFloat(andCheckFieldIndex);
            case DOUBLE:
                return this.query.maximumDouble(andCheckFieldIndex);
            default:
                throw new IllegalArgumentException(String.format(Locale.US, TYPE_MISMATCH, str, "int, float or double"));
        }
    }

    public Date maximumDate(String str) {
        this.realm.checkIfValid();
        return this.query.maximumDate(this.schema.getAndCheckFieldIndex(str));
    }

    public long count() {
        this.realm.checkIfValid();
        return this.query.count();
    }

    public RealmResults<E> findAll() {
        this.realm.checkIfValid();
        return createRealmResults(this.query, null, null, true);
    }

    public RealmResults<E> findAllAsync() {
        this.realm.checkIfValid();
        this.realm.sharedRealm.capabilities.checkCanDeliverNotification(ASYNC_QUERY_WRONG_THREAD_MESSAGE);
        return createRealmResults(this.query, null, null, false);
    }

    public RealmResults<E> findAllSorted(String str, Sort sort) {
        this.realm.checkIfValid();
        return createRealmResults(this.query, SortDescriptor.getInstanceForSort(getSchemaConnector(), this.query.getTable(), str, sort), null, true);
    }

    public RealmResults<E> findAllSortedAsync(String str, Sort sort) {
        this.realm.checkIfValid();
        this.realm.sharedRealm.capabilities.checkCanDeliverNotification(ASYNC_QUERY_WRONG_THREAD_MESSAGE);
        return createRealmResults(this.query, SortDescriptor.getInstanceForSort(getSchemaConnector(), this.query.getTable(), str, sort), null, false);
    }

    public RealmResults<E> findAllSorted(String str) {
        return findAllSorted(str, Sort.ASCENDING);
    }

    public RealmResults<E> findAllSortedAsync(String str) {
        return findAllSortedAsync(str, Sort.ASCENDING);
    }

    public RealmResults<E> findAllSorted(String[] strArr, Sort[] sortArr) {
        this.realm.checkIfValid();
        return createRealmResults(this.query, SortDescriptor.getInstanceForSort(getSchemaConnector(), this.query.getTable(), strArr, sortArr), null, true);
    }

    private boolean isDynamicQuery() {
        return this.className != null;
    }

    public RealmResults<E> findAllSortedAsync(String[] strArr, Sort[] sortArr) {
        this.realm.checkIfValid();
        this.realm.sharedRealm.capabilities.checkCanDeliverNotification(ASYNC_QUERY_WRONG_THREAD_MESSAGE);
        return createRealmResults(this.query, SortDescriptor.getInstanceForSort(getSchemaConnector(), this.query.getTable(), strArr, sortArr), null, false);
    }

    public RealmResults<E> findAllSorted(String str, Sort sort, String str2, Sort sort2) {
        return findAllSorted(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    public RealmResults<E> findAllSortedAsync(String str, Sort sort, String str2, Sort sort2) {
        return findAllSortedAsync(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    public E findFirst() {
        this.realm.checkIfValid();
        long sourceRowIndexForFirstObject = getSourceRowIndexForFirstObject();
        if (sourceRowIndexForFirstObject < 0) {
            return null;
        }
        return (E) this.realm.get(this.clazz, this.className, sourceRowIndexForFirstObject);
    }

    public E findFirstAsync() {
        Row row;
        DynamicRealmObject dynamicRealmObject;
        this.realm.checkIfValid();
        this.realm.sharedRealm.capabilities.checkCanDeliverNotification(ASYNC_QUERY_WRONG_THREAD_MESSAGE);
        if (this.realm.isInTransaction()) {
            row = new Collection(this.realm.sharedRealm, this.query).firstUncheckedRow();
        } else {
            row = new PendingRow(this.realm.sharedRealm, this.query, null, isDynamicQuery());
        }
        if (isDynamicQuery()) {
            dynamicRealmObject = new DynamicRealmObject(this.realm, row);
        } else {
            dynamicRealmObject = (E) this.realm.getConfiguration().getSchemaMediator().newInstance(this.clazz, this.realm, row, this.realm.getSchema().getColumnInfo(this.clazz), false, Collections.emptyList());
        }
        if (row instanceof PendingRow) {
            ((PendingRow) row).setFrontEnd(((RealmObjectProxy) dynamicRealmObject).realmGet$proxyState());
        }
        return dynamicRealmObject;
    }

    private RealmResults<E> createRealmResults(TableQuery tableQuery, SortDescriptor sortDescriptor, SortDescriptor sortDescriptor2, boolean z) {
        RealmResults<E> realmResults;
        Collection collection = new Collection(this.realm.sharedRealm, tableQuery, sortDescriptor, sortDescriptor2);
        if (isDynamicQuery()) {
            realmResults = new RealmResults<>(this.realm, collection, this.className);
        } else {
            realmResults = new RealmResults<>(this.realm, collection, this.clazz);
        }
        if (z) {
            realmResults.load();
        }
        return realmResults;
    }

    private long getSourceRowIndexForFirstObject() {
        return this.query.find();
    }

    private SchemaConnector getSchemaConnector() {
        return new SchemaConnector(this.realm.getSchema());
    }
}
