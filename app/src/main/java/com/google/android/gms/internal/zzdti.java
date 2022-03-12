package com.google.android.gms.internal;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
final class zzdti extends WeakReference<Throwable> {
    private final int zzlwd;

    public zzdti(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, null);
        if (th == null) {
            throw new NullPointerException("The referent cannot be null");
        }
        this.zzlwd = System.identityHashCode(th);
    }

    public final boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zzdti zzdtiVar = (zzdti) obj;
        return this.zzlwd == zzdtiVar.zzlwd && get() == zzdtiVar.get();
    }

    public final int hashCode() {
        return this.zzlwd;
    }
}
