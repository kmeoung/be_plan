package com.google.android.gms.common.api.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.gms.common.util.zzq;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class zzk implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private static final zzk zzfll = new zzk();
    private final AtomicBoolean zzflm = new AtomicBoolean();
    private final AtomicBoolean zzfln = new AtomicBoolean();
    private final ArrayList<zzl> zzflo = new ArrayList<>();
    private boolean zzdqd = false;

    private zzk() {
    }

    public static void zza(Application application) {
        synchronized (zzfll) {
            if (!zzfll.zzdqd) {
                application.registerActivityLifecycleCallbacks(zzfll);
                application.registerComponentCallbacks(zzfll);
                zzfll.zzdqd = true;
            }
        }
    }

    public static zzk zzagp() {
        return zzfll;
    }

    private final void zzbe(boolean z) {
        synchronized (zzfll) {
            ArrayList<zzl> arrayList = this.zzflo;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                zzl zzlVar = arrayList.get(i);
                i++;
                zzlVar.zzbe(z);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean compareAndSet = this.zzflm.compareAndSet(true, false);
        this.zzfln.set(true);
        if (compareAndSet) {
            zzbe(false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        boolean compareAndSet = this.zzflm.compareAndSet(true, false);
        this.zzfln.set(true);
        if (compareAndSet) {
            zzbe(false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
    }

    @Override // android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
        if (i == 20 && this.zzflm.compareAndSet(false, true)) {
            this.zzfln.set(true);
            zzbe(true);
        }
    }

    public final void zza(zzl zzlVar) {
        synchronized (zzfll) {
            this.zzflo.add(zzlVar);
        }
    }

    public final boolean zzagq() {
        return this.zzflm.get();
    }

    @TargetApi(16)
    public final boolean zzbd(boolean z) {
        if (!this.zzfln.get()) {
            if (!zzq.zzalw()) {
                return true;
            }
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (!this.zzfln.getAndSet(true) && runningAppProcessInfo.importance > 100) {
                this.zzflm.set(true);
            }
        }
        return this.zzflm.get();
    }
}
