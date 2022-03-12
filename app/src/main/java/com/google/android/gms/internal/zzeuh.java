package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzeuh {
    private static final AtomicReference<zzeuh> zzlws = new AtomicReference<>();

    private zzeuh(Context context) {
    }

    @Nullable
    public static zzeuh zzchr() {
        return zzlws.get();
    }

    public static Set<String> zzchs() {
        return Collections.emptySet();
    }

    public static zzeuh zzew(Context context) {
        zzlws.compareAndSet(null, new zzeuh(context));
        return zzlws.get();
    }

    public static void zzf(@NonNull FirebaseApp firebaseApp) {
    }

    public static FirebaseOptions zzra(@NonNull String str) {
        return null;
    }
}
