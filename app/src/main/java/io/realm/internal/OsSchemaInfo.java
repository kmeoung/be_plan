package io.realm.internal;

import java.util.Collection;

/* loaded from: classes.dex */
public class OsSchemaInfo implements NativeObject {
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private long nativePtr;

    private static native long nativeCreateFromList(long[] jArr);

    private static native long nativeGetFinalizerPtr();

    public OsSchemaInfo(Collection<OsObjectSchemaInfo> collection) {
        long[] jArr = new long[collection.size()];
        int i = 0;
        for (OsObjectSchemaInfo osObjectSchemaInfo : collection) {
            jArr[i] = osObjectSchemaInfo.getNativePtr();
            i++;
        }
        this.nativePtr = nativeCreateFromList(jArr);
        NativeContext.dummyContext.addReference(this);
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
