package io.realm.internal;

/* loaded from: classes.dex */
public interface NativeObject {
    public static final long NULLPTR = 0;

    long getNativeFinalizerPtr();

    long getNativePtr();
}
