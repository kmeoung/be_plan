package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public interface zzci {
    void startActivityForResult(Intent intent, int i);

    <T extends LifecycleCallback> T zza(String str, Class<T> cls);

    void zza(String str, @NonNull LifecycleCallback lifecycleCallback);

    Activity zzajb();
}
