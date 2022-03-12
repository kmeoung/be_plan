package io.realm.internal;

/* loaded from: classes.dex */
public interface Capabilities {
    boolean canDeliverNotification();

    void checkCanDeliverNotification(String str);

    boolean isMainThread();
}
