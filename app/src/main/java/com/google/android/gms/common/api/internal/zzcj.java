package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public final class zzcj extends Fragment implements zzci {
    private static WeakHashMap<Activity, WeakReference<zzcj>> zzfrk = new WeakHashMap<>();
    private Bundle zzfrm;
    private Map<String, LifecycleCallback> zzfrl = new ArrayMap();
    private int zzbzn = 0;

    public static zzcj zzo(Activity activity) {
        zzcj zzcjVar;
        WeakReference<zzcj> weakReference = zzfrk.get(activity);
        if (weakReference != null && (zzcjVar = weakReference.get()) != null) {
            return zzcjVar;
        }
        try {
            zzcj zzcjVar2 = (zzcj) activity.getFragmentManager().findFragmentByTag("LifecycleFragmentImpl");
            if (zzcjVar2 == null || zzcjVar2.isRemoving()) {
                zzcjVar2 = new zzcj();
                activity.getFragmentManager().beginTransaction().add(zzcjVar2, "LifecycleFragmentImpl").commitAllowingStateLoss();
            }
            zzfrk.put(activity, new WeakReference<>(zzcjVar2));
            return zzcjVar2;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Fragment with tag LifecycleFragmentImpl is not a LifecycleFragmentImpl", e);
        }
    }

    @Override // android.app.Fragment
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @Override // android.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.onActivityResult(i, i2, intent);
        }
    }

    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzbzn = 1;
        this.zzfrm = bundle;
        for (Map.Entry<String, LifecycleCallback> entry : this.zzfrl.entrySet()) {
            entry.getValue().onCreate(bundle != null ? bundle.getBundle(entry.getKey()) : null);
        }
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.zzbzn = 5;
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.onDestroy();
        }
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        this.zzbzn = 3;
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.onResume();
        }
    }

    @Override // android.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            for (Map.Entry<String, LifecycleCallback> entry : this.zzfrl.entrySet()) {
                Bundle bundle2 = new Bundle();
                entry.getValue().onSaveInstanceState(bundle2);
                bundle.putBundle(entry.getKey(), bundle2);
            }
        }
    }

    @Override // android.app.Fragment
    public final void onStart() {
        super.onStart();
        this.zzbzn = 2;
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.onStart();
        }
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        this.zzbzn = 4;
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.onStop();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zzci
    public final <T extends LifecycleCallback> T zza(String str, Class<T> cls) {
        return cls.cast(this.zzfrl.get(str));
    }

    @Override // com.google.android.gms.common.api.internal.zzci
    public final void zza(String str, @NonNull LifecycleCallback lifecycleCallback) {
        if (!this.zzfrl.containsKey(str)) {
            this.zzfrl.put(str, lifecycleCallback);
            if (this.zzbzn > 0) {
                new Handler(Looper.getMainLooper()).post(new zzck(this, lifecycleCallback, str));
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 59);
        sb.append("LifecycleCallback with tag ");
        sb.append(str);
        sb.append(" already added to this fragment.");
        throw new IllegalArgumentException(sb.toString());
    }

    @Override // com.google.android.gms.common.api.internal.zzci
    public final Activity zzajb() {
        return getActivity();
    }
}
