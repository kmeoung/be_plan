package io.realm.internal.android;

import android.os.Looper;
import io.realm.internal.Capabilities;

/* loaded from: classes.dex */
public class AndroidCapabilities implements Capabilities {
    private final Looper looper = Looper.myLooper();
    private final boolean isIntentServiceThread = isIntentServiceThread();

    @Override // io.realm.internal.Capabilities
    public boolean canDeliverNotification() {
        return hasLooper() && !this.isIntentServiceThread;
    }

    @Override // io.realm.internal.Capabilities
    public void checkCanDeliverNotification(String str) {
        String str2;
        String str3;
        if (!hasLooper()) {
            if (str == null) {
                str3 = "";
            } else {
                str3 = str + " Realm cannot be automatically updated on a thread without a looper.";
            }
            throw new IllegalStateException(str3);
        } else if (this.isIntentServiceThread) {
            if (str == null) {
                str2 = "";
            } else {
                str2 = str + " Realm cannot be automatically updated on an IntentService thread.";
            }
            throw new IllegalStateException(str2);
        }
    }

    @Override // io.realm.internal.Capabilities
    public boolean isMainThread() {
        return this.looper != null && this.looper == Looper.getMainLooper();
    }

    private boolean hasLooper() {
        return this.looper != null;
    }

    private static boolean isIntentServiceThread() {
        String name = Thread.currentThread().getName();
        return name != null && name.startsWith("IntentService[");
    }
}
