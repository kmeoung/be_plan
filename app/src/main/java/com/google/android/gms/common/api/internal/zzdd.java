package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public final class zzdd extends Fragment implements zzci {
    private static WeakHashMap<FragmentActivity, WeakReference<zzdd>> zzfrk = new WeakHashMap<>();
    private Bundle zzfrm;
    private Map<String, LifecycleCallback> zzfrl = new ArrayMap();
    private int zzbzn = 0;

    public static zzdd zza(FragmentActivity fragmentActivity) {
        zzdd zzddVar;
        WeakReference<zzdd> weakReference = zzfrk.get(fragmentActivity);
        if (weakReference != null && (zzddVar = weakReference.get()) != null) {
            return zzddVar;
        }
        try {
            zzdd zzddVar2 = (zzdd) fragmentActivity.getSupportFragmentManager().findFragmentByTag("SupportLifecycleFragmentImpl");
            if (zzddVar2 == null || zzddVar2.isRemoving()) {
                zzddVar2 = new zzdd();
                fragmentActivity.getSupportFragmentManager().beginTransaction().add(zzddVar2, "SupportLifecycleFragmentImpl").commitAllowingStateLoss();
            }
            zzfrk.put(fragmentActivity, new WeakReference<>(zzddVar2));
            return zzddVar2;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Fragment with tag SupportLifecycleFragmentImpl is not a SupportLifecycleFragmentImpl", e);
        }
    }

    @Override // android.support.v4.app.Fragment
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @Override // android.support.v4.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.onActivityResult(i, i2, intent);
        }
    }

    @Override // android.support.v4.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzbzn = 1;
        this.zzfrm = bundle;
        for (Map.Entry<String, LifecycleCallback> entry : this.zzfrl.entrySet()) {
            entry.getValue().onCreate(bundle != null ? bundle.getBundle(entry.getKey()) : null);
        }
    }

    @Override // android.support.v4.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.zzbzn = 5;
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.onDestroy();
        }
    }

    @Override // android.support.v4.app.Fragment
    public final void onResume() {
        super.onResume();
        this.zzbzn = 3;
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.onResume();
        }
    }

    @Override // android.support.v4.app.Fragment
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

    @Override // android.support.v4.app.Fragment
    public final void onStart() {
        super.onStart();
        this.zzbzn = 2;
        for (LifecycleCallback lifecycleCallback : this.zzfrl.values()) {
            lifecycleCallback.onStart();
        }
    }

    @Override // android.support.v4.app.Fragment
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
                new Handler(Looper.getMainLooper()).post(new zzde(this, lifecycleCallback, str));
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
    public final /* synthetic */ Activity zzajb() {
        return getActivity();
    }
}
