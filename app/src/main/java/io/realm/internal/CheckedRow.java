package io.realm.internal;

import io.realm.RealmFieldType;

/* loaded from: classes.dex */
public class CheckedRow extends UncheckedRow {
    private UncheckedRow originalRow;

    @Override // io.realm.internal.UncheckedRow
    protected native boolean nativeGetBoolean(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native byte[] nativeGetByteArray(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native long nativeGetColumnCount(long j);

    @Override // io.realm.internal.UncheckedRow
    protected native long nativeGetColumnIndex(long j, String str);

    @Override // io.realm.internal.UncheckedRow
    protected native String nativeGetColumnName(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native int nativeGetColumnType(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native double nativeGetDouble(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native float nativeGetFloat(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native long nativeGetLink(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native long nativeGetLinkView(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native long nativeGetLong(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native String nativeGetString(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native long nativeGetTimestamp(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native boolean nativeIsNullLink(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native void nativeNullifyLink(long j, long j2);

    @Override // io.realm.internal.UncheckedRow
    protected native void nativeSetBoolean(long j, long j2, boolean z);

    @Override // io.realm.internal.UncheckedRow
    protected native void nativeSetByteArray(long j, long j2, byte[] bArr);

    @Override // io.realm.internal.UncheckedRow
    protected native void nativeSetDouble(long j, long j2, double d);

    @Override // io.realm.internal.UncheckedRow
    protected native void nativeSetFloat(long j, long j2, float f);

    @Override // io.realm.internal.UncheckedRow
    protected native void nativeSetLink(long j, long j2, long j3);

    @Override // io.realm.internal.UncheckedRow
    protected native void nativeSetLong(long j, long j2, long j3);

    @Override // io.realm.internal.UncheckedRow
    protected native void nativeSetString(long j, long j2, String str);

    @Override // io.realm.internal.UncheckedRow
    protected native void nativeSetTimestamp(long j, long j2, long j3);

    private CheckedRow(NativeContext nativeContext, Table table, long j) {
        super(nativeContext, table, j);
    }

    private CheckedRow(UncheckedRow uncheckedRow) {
        super(uncheckedRow);
        this.originalRow = uncheckedRow;
    }

    public static CheckedRow get(NativeContext nativeContext, Table table, long j) {
        return new CheckedRow(nativeContext, table, table.nativeGetRowPtr(table.getNativePtr(), j));
    }

    public static CheckedRow get(NativeContext nativeContext, LinkView linkView, long j) {
        return new CheckedRow(nativeContext, linkView.getTargetTable(), linkView.nativeGetRow(linkView.getNativePtr(), j));
    }

    public static CheckedRow getFromRow(UncheckedRow uncheckedRow) {
        return new CheckedRow(uncheckedRow);
    }

    @Override // io.realm.internal.UncheckedRow, io.realm.internal.Row
    public boolean isNullLink(long j) {
        RealmFieldType columnType = getColumnType(j);
        if (columnType == RealmFieldType.OBJECT || columnType == RealmFieldType.LIST) {
            return super.isNullLink(j);
        }
        return false;
    }

    @Override // io.realm.internal.UncheckedRow, io.realm.internal.Row
    public boolean isNull(long j) {
        return super.isNull(j);
    }

    @Override // io.realm.internal.UncheckedRow, io.realm.internal.Row
    public void setNull(long j) {
        if (getColumnType(j) == RealmFieldType.BINARY) {
            super.setBinaryByteArray(j, null);
        } else {
            super.setNull(j);
        }
    }
}
