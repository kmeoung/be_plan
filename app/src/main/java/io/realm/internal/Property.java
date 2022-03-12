package io.realm.internal;

import io.realm.RealmFieldType;

/* loaded from: classes.dex */
public class Property implements NativeObject {
    public static final boolean INDEXED = true;
    public static final boolean PRIMARY_KEY = true;
    public static final boolean REQUIRED = true;
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private long nativePtr;

    private static native long nativeCreateProperty(String str, int i, String str2);

    private static native long nativeCreateProperty(String str, int i, boolean z, boolean z2, boolean z3);

    private static native long nativeGetFinalizerPtr();

    public Property(String str, RealmFieldType realmFieldType, boolean z, boolean z2, boolean z3) {
        this.nativePtr = nativeCreateProperty(str, realmFieldType.getNativeValue(), z, z2, !z3);
        NativeContext.dummyContext.addReference(this);
    }

    public Property(String str, RealmFieldType realmFieldType, String str2) {
        this.nativePtr = nativeCreateProperty(str, realmFieldType.getNativeValue(), str2);
        NativeContext.dummyContext.addReference(this);
    }

    protected Property(long j) {
        this.nativePtr = j;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }
}
