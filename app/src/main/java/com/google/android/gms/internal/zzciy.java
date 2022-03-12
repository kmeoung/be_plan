package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.text.TextUtils;

@TargetApi(14)
@MainThread
/* loaded from: classes.dex */
public final class zzciy implements Application.ActivityLifecycleCallbacks {
    private /* synthetic */ zzcik zzjeh;

    private zzciy(zzcik zzcikVar) {
        this.zzjeh = zzcikVar;
    }

    public /* synthetic */ zzciy(zzcik zzcikVar, zzcil zzcilVar) {
        this(zzcikVar);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        Bundle bundle2;
        Uri data;
        try {
            this.zzjeh.zzawm().zzayx().log("onActivityCreated");
            Intent intent = activity.getIntent();
            if (!(intent == null || (data = intent.getData()) == null || !data.isHierarchical())) {
                if (bundle == null) {
                    Bundle zzp = this.zzjeh.zzawi().zzp(data);
                    this.zzjeh.zzawi();
                    String str = zzckn.zzo(intent) ? "gs" : "auto";
                    if (zzp != null) {
                        this.zzjeh.zzc(str, "_cmp", zzp);
                    }
                }
                String queryParameter = data.getQueryParameter("referrer");
                if (!TextUtils.isEmpty(queryParameter)) {
                    if (!(queryParameter.contains("gclid") && (queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium") || queryParameter.contains("utm_term") || queryParameter.contains("utm_content")))) {
                        this.zzjeh.zzawm().zzayw().log("Activity created with data 'referrer' param without gclid and at least one utm field");
                        return;
                    }
                    this.zzjeh.zzawm().zzayw().zzj("Activity created with referrer", queryParameter);
                    if (!TextUtils.isEmpty(queryParameter)) {
                        this.zzjeh.zzb("auto", "_ldl", queryParameter);
                    }
                } else {
                    return;
                }
            }
        } catch (Throwable th) {
            this.zzjeh.zzawm().zzayr().zzj("Throwable caught in onActivityCreated", th);
        }
        zzciz zzawe = this.zzjeh.zzawe();
        if (bundle != null && (bundle2 = bundle.getBundle("com.google.firebase.analytics.screen_service")) != null) {
            zzcjc zzq = zzawe.zzq(activity);
            zzq.zzitr = bundle2.getLong("id");
            zzq.zzitp = bundle2.getString("name");
            zzq.zzitq = bundle2.getString("referrer_name");
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        this.zzjeh.zzawe().onActivityDestroyed(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityPaused(Activity activity) {
        this.zzjeh.zzawe().onActivityPaused(activity);
        zzckc zzawk = this.zzjeh.zzawk();
        zzawk.zzawl().zzg(new zzckg(zzawk, zzawk.zzwh().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityResumed(Activity activity) {
        this.zzjeh.zzawe().onActivityResumed(activity);
        zzckc zzawk = this.zzjeh.zzawk();
        zzawk.zzawl().zzg(new zzckf(zzawk, zzawk.zzwh().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zzjeh.zzawe().onActivitySaveInstanceState(activity, bundle);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }
}
