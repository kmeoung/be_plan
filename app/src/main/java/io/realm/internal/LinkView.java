package io.realm.internal;

/* loaded from: classes.dex */
public class LinkView implements NativeObject {
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    final long columnIndexInParent;
    private final NativeContext context;
    private final long nativePtr;
    final Table parent;

    public static native void nativeAdd(long j, long j2);

    public static native void nativeClear(long j);

    private native long nativeFind(long j, long j2);

    private static native long nativeGetFinalizerPtr();

    private native long nativeGetTargetRowIndex(long j, long j2);

    private native long nativeGetTargetTable(long j);

    private native void nativeInsert(long j, long j2, long j3);

    private native boolean nativeIsAttached(long j);

    private native boolean nativeIsEmpty(long j);

    private native void nativeMove(long j, long j2, long j3);

    private native void nativeRemove(long j, long j2);

    private native void nativeRemoveAllTargetRows(long j);

    private native void nativeRemoveTargetRow(long j, long j2);

    private native void nativeSet(long j, long j2, long j3);

    private native long nativeSize(long j);

    public native long nativeGetRow(long j, long j2);

    protected native long nativeWhere(long j);

    public LinkView(NativeContext nativeContext, Table table, long j, long j2) {
        this.context = nativeContext;
        this.parent = table;
        this.columnIndexInParent = j;
        this.nativePtr = j2;
        nativeContext.addReference(this);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    public UncheckedRow getUncheckedRow(long j) {
        return UncheckedRow.getByRowIndex(this.context, this, j);
    }

    public CheckedRow getCheckedRow(long j) {
        return CheckedRow.get(this.context, this, j);
    }

    public long getTargetRowIndex(long j) {
        return nativeGetTargetRowIndex(this.nativePtr, j);
    }

    public void add(long j) {
        checkImmutable();
        nativeAdd(this.nativePtr, j);
    }

    public void insert(long j, long j2) {
        checkImmutable();
        nativeInsert(this.nativePtr, j, j2);
    }

    public void set(long j, long j2) {
        checkImmutable();
        nativeSet(this.nativePtr, j, j2);
    }

    public void move(long j, long j2) {
        checkImmutable();
        nativeMove(this.nativePtr, j, j2);
    }

    public void remove(long j) {
        checkImmutable();
        nativeRemove(this.nativePtr, j);
    }

    public void clear() {
        checkImmutable();
        nativeClear(this.nativePtr);
    }

    public boolean contains(long j) {
        return nativeFind(this.nativePtr, j) != -1;
    }

    public long size() {
        return nativeSize(this.nativePtr);
    }

    public boolean isEmpty() {
        return nativeIsEmpty(this.nativePtr);
    }

    public TableQuery where() {
        return new TableQuery(this.context, getTargetTable(), nativeWhere(this.nativePtr));
    }

    public boolean isAttached() {
        return nativeIsAttached(this.nativePtr);
    }

    public void removeAllTargetRows() {
        checkImmutable();
        nativeRemoveAllTargetRows(this.nativePtr);
    }

    public void removeTargetRow(int i) {
        checkImmutable();
        nativeRemoveTargetRow(this.nativePtr, i);
    }

    public Table getTargetTable() {
        return new Table(this.parent, nativeGetTargetTable(this.nativePtr));
    }

    private void checkImmutable() {
        if (this.parent.isImmutable()) {
            throw new IllegalStateException("Changing Realm data can only be done from inside a write transaction.");
        }
    }
}
