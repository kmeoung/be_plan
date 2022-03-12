package io.realm.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/* loaded from: classes.dex */
public final class NativeObjectReference extends PhantomReference<NativeObject> {
    private static ReferencePool referencePool = new ReferencePool();
    private final NativeContext context;
    private final long nativeFinalizerPtr;
    private final long nativePtr;
    private NativeObjectReference next;
    private NativeObjectReference prev;

    private static native void nativeCleanUp(long j, long j2);

    /* loaded from: classes.dex */
    public static class ReferencePool {
        NativeObjectReference head;

        private ReferencePool() {
        }

        synchronized void add(NativeObjectReference nativeObjectReference) {
            nativeObjectReference.prev = null;
            nativeObjectReference.next = this.head;
            if (this.head != null) {
                this.head.prev = nativeObjectReference;
            }
            this.head = nativeObjectReference;
        }

        synchronized void remove(NativeObjectReference nativeObjectReference) {
            NativeObjectReference nativeObjectReference2 = nativeObjectReference.next;
            NativeObjectReference nativeObjectReference3 = nativeObjectReference.prev;
            nativeObjectReference.next = null;
            nativeObjectReference.prev = null;
            if (nativeObjectReference3 != null) {
                nativeObjectReference3.next = nativeObjectReference2;
            } else {
                this.head = nativeObjectReference2;
            }
            if (nativeObjectReference2 != null) {
                nativeObjectReference2.prev = nativeObjectReference3;
            }
        }
    }

    public NativeObjectReference(NativeContext nativeContext, NativeObject nativeObject, ReferenceQueue<? super NativeObject> referenceQueue) {
        super(nativeObject, referenceQueue);
        this.nativePtr = nativeObject.getNativePtr();
        this.nativeFinalizerPtr = nativeObject.getNativeFinalizerPtr();
        this.context = nativeContext;
        referencePool.add(this);
    }

    public void cleanup() {
        synchronized (this.context) {
            nativeCleanUp(this.nativeFinalizerPtr, this.nativePtr);
        }
        referencePool.remove(this);
    }
}
