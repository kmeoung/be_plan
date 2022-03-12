package io.realm.internal;

import io.realm.RealmFieldType;
import java.util.Date;

/* loaded from: classes.dex */
public class UncheckedRow implements NativeObject, Row {
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private final NativeContext context;
    private final long nativePtr;
    private final Table parent;

    private static native long nativeGetFinalizerPtr();

    protected native boolean nativeGetBoolean(long j, long j2);

    protected native byte[] nativeGetByteArray(long j, long j2);

    protected native long nativeGetColumnCount(long j);

    protected native long nativeGetColumnIndex(long j, String str);

    protected native String nativeGetColumnName(long j, long j2);

    protected native int nativeGetColumnType(long j, long j2);

    protected native double nativeGetDouble(long j, long j2);

    protected native float nativeGetFloat(long j, long j2);

    protected native long nativeGetIndex(long j);

    protected native long nativeGetLink(long j, long j2);

    protected native long nativeGetLinkView(long j, long j2);

    protected native long nativeGetLong(long j, long j2);

    protected native String nativeGetString(long j, long j2);

    protected native long nativeGetTimestamp(long j, long j2);

    protected native boolean nativeHasColumn(long j, String str);

    protected native boolean nativeIsAttached(long j);

    protected native boolean nativeIsNull(long j, long j2);

    protected native boolean nativeIsNullLink(long j, long j2);

    protected native void nativeNullifyLink(long j, long j2);

    protected native void nativeSetBoolean(long j, long j2, boolean z);

    protected native void nativeSetByteArray(long j, long j2, byte[] bArr);

    protected native void nativeSetDouble(long j, long j2, double d);

    protected native void nativeSetFloat(long j, long j2, float f);

    protected native void nativeSetLink(long j, long j2, long j3);

    protected native void nativeSetLong(long j, long j2, long j3);

    protected native void nativeSetNull(long j, long j2);

    protected native void nativeSetString(long j, long j2, String str);

    protected native void nativeSetTimestamp(long j, long j2, long j3);

    public UncheckedRow(NativeContext nativeContext, Table table, long j) {
        this.context = nativeContext;
        this.parent = table;
        this.nativePtr = j;
        nativeContext.addReference(this);
    }

    public UncheckedRow(UncheckedRow uncheckedRow) {
        this.context = uncheckedRow.context;
        this.parent = uncheckedRow.parent;
        this.nativePtr = uncheckedRow.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    public static UncheckedRow getByRowIndex(NativeContext nativeContext, Table table, long j) {
        return new UncheckedRow(nativeContext, table, table.nativeGetRowPtr(table.getNativePtr(), j));
    }

    public static UncheckedRow getByRowPointer(NativeContext nativeContext, Table table, long j) {
        return new UncheckedRow(nativeContext, table, j);
    }

    public static UncheckedRow getByRowIndex(NativeContext nativeContext, LinkView linkView, long j) {
        return new UncheckedRow(nativeContext, linkView.getTargetTable(), linkView.nativeGetRow(linkView.getNativePtr(), j));
    }

    @Override // io.realm.internal.Row
    public long getColumnCount() {
        return nativeGetColumnCount(this.nativePtr);
    }

    @Override // io.realm.internal.Row
    public String getColumnName(long j) {
        return nativeGetColumnName(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public long getColumnIndex(String str) {
        if (str != null) {
            return nativeGetColumnIndex(this.nativePtr, str);
        }
        throw new IllegalArgumentException("Column name can not be null.");
    }

    @Override // io.realm.internal.Row
    public RealmFieldType getColumnType(long j) {
        return RealmFieldType.fromNativeValue(nativeGetColumnType(this.nativePtr, j));
    }

    @Override // io.realm.internal.Row
    public Table getTable() {
        return this.parent;
    }

    @Override // io.realm.internal.Row
    public long getIndex() {
        return nativeGetIndex(this.nativePtr);
    }

    @Override // io.realm.internal.Row
    public long getLong(long j) {
        return nativeGetLong(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public boolean getBoolean(long j) {
        return nativeGetBoolean(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public float getFloat(long j) {
        return nativeGetFloat(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public double getDouble(long j) {
        return nativeGetDouble(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public Date getDate(long j) {
        return new Date(nativeGetTimestamp(this.nativePtr, j));
    }

    @Override // io.realm.internal.Row
    public String getString(long j) {
        return nativeGetString(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public byte[] getBinaryByteArray(long j) {
        return nativeGetByteArray(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public long getLink(long j) {
        return nativeGetLink(this.nativePtr, j);
    }

    public boolean isNullLink(long j) {
        return nativeIsNullLink(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public LinkView getLinkList(long j) {
        return new LinkView(this.context, this.parent, j, nativeGetLinkView(this.nativePtr, j));
    }

    @Override // io.realm.internal.Row
    public void setLong(long j, long j2) {
        this.parent.checkImmutable();
        getTable().checkIntValueIsLegal(j, getIndex(), j2);
        nativeSetLong(this.nativePtr, j, j2);
    }

    @Override // io.realm.internal.Row
    public void setBoolean(long j, boolean z) {
        this.parent.checkImmutable();
        nativeSetBoolean(this.nativePtr, j, z);
    }

    @Override // io.realm.internal.Row
    public void setFloat(long j, float f) {
        this.parent.checkImmutable();
        nativeSetFloat(this.nativePtr, j, f);
    }

    @Override // io.realm.internal.Row
    public void setDouble(long j, double d) {
        this.parent.checkImmutable();
        nativeSetDouble(this.nativePtr, j, d);
    }

    @Override // io.realm.internal.Row
    public void setDate(long j, Date date) {
        this.parent.checkImmutable();
        if (date == null) {
            throw new IllegalArgumentException("Null Date is not allowed.");
        }
        nativeSetTimestamp(this.nativePtr, j, date.getTime());
    }

    @Override // io.realm.internal.Row
    public void setString(long j, String str) {
        this.parent.checkImmutable();
        if (str == null) {
            getTable().checkDuplicatedNullForPrimaryKeyValue(j, getIndex());
            nativeSetNull(this.nativePtr, j);
            return;
        }
        getTable().checkStringValueIsLegal(j, getIndex(), str);
        nativeSetString(this.nativePtr, j, str);
    }

    @Override // io.realm.internal.Row
    public void setBinaryByteArray(long j, byte[] bArr) {
        this.parent.checkImmutable();
        nativeSetByteArray(this.nativePtr, j, bArr);
    }

    @Override // io.realm.internal.Row
    public void setLink(long j, long j2) {
        this.parent.checkImmutable();
        nativeSetLink(this.nativePtr, j, j2);
    }

    @Override // io.realm.internal.Row
    public void nullifyLink(long j) {
        this.parent.checkImmutable();
        nativeNullifyLink(this.nativePtr, j);
    }

    public boolean isNull(long j) {
        return nativeIsNull(this.nativePtr, j);
    }

    public void setNull(long j) {
        this.parent.checkImmutable();
        getTable().checkDuplicatedNullForPrimaryKeyValue(j, getIndex());
        nativeSetNull(this.nativePtr, j);
    }

    public CheckedRow convertToChecked() {
        return CheckedRow.getFromRow(this);
    }

    @Override // io.realm.internal.Row
    public boolean isAttached() {
        return this.nativePtr != 0 && nativeIsAttached(this.nativePtr);
    }

    @Override // io.realm.internal.Row
    public void checkIfAttached() {
        if (!isAttached()) {
            throw new IllegalStateException("Object is no longer managed by Realm. Has it been deleted?");
        }
    }

    @Override // io.realm.internal.Row
    public boolean hasColumn(String str) {
        return nativeHasColumn(this.nativePtr, str);
    }
}
