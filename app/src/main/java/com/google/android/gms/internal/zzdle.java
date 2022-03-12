package com.google.android.gms.internal;

import android.database.ContentObserver;
import android.os.Handler;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class zzdle extends ContentObserver {
    public zzdle(Handler handler) {
        super(null);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        AtomicBoolean atomicBoolean;
        atomicBoolean = zzdld.zzlju;
        atomicBoolean.set(true);
    }
}
