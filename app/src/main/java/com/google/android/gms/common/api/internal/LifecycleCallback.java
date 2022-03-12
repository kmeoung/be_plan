package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class LifecycleCallback {
    protected final zzci zzfrj;

    public LifecycleCallback(zzci zzciVar) {
        this.zzfrj = zzciVar;
    }

    @Keep
    private static zzci getChimeraLifecycleFragmentImpl(zzch zzchVar) {
        throw new IllegalStateException("Method not available in SDK.");
    }

    protected static zzci zzb(zzch zzchVar) {
        if (zzchVar.zzaix()) {
            return zzdd.zza(zzchVar.zzaja());
        }
        if (zzchVar.zzaiy()) {
            return zzcj.zzo(zzchVar.zzaiz());
        }
        throw new IllegalArgumentException("Can't get fragment for unexpected activity.");
    }

    public static zzci zzn(Activity activity) {
        return zzb(new zzch(activity));
    }

    @MainThread
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final Activity getActivity() {
        return this.zzfrj.zzajb();
    }

    @MainThread
    public void onActivityResult(int i, int i2, Intent intent) {
    }

    @MainThread
    public void onCreate(Bundle bundle) {
    }

    @MainThread
    public void onDestroy() {
    }

    @MainThread
    public void onResume() {
    }

    @MainThread
    public void onSaveInstanceState(Bundle bundle) {
    }

    @MainThread
    public void onStart() {
    }

    @MainThread
    public void onStop() {
    }
}
