package io.realm.internal;

import java.lang.ref.ReferenceQueue;

/* loaded from: classes.dex */
public class NativeContext {
    private static final ReferenceQueue<NativeObject> referenceQueue = new ReferenceQueue<>();
    private static final Thread finalizingThread = new Thread(new FinalizerRunnable(referenceQueue));
    static final NativeContext dummyContext = new NativeContext();

    static {
        finalizingThread.setName("RealmFinalizingDaemon");
        finalizingThread.start();
    }

    public void addReference(NativeObject nativeObject) {
        new NativeObjectReference(this, nativeObject, referenceQueue);
    }
}
