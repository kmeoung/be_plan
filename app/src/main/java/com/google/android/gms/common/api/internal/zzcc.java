package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbf;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzca;

@Deprecated
/* loaded from: classes.dex */
public final class zzcc {
    private static final Object sLock = new Object();
    private static zzcc zzfre;
    private final String mAppId;
    private final Status zzfrf;
    private final boolean zzfrg;
    private final boolean zzfrh;

    private zzcc(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", "integer", resources.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
        boolean z = true;
        if (identifier != 0) {
            z = resources.getInteger(identifier) == 0 ? false : z;
            this.zzfrh = !z;
        } else {
            this.zzfrh = false;
        }
        this.zzfrg = z;
        String zzcm = zzbf.zzcm(context);
        zzcm = zzcm == null ? new zzca(context).getString("google_app_id") : zzcm;
        if (TextUtils.isEmpty(zzcm)) {
            this.zzfrf = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.mAppId = null;
            return;
        }
        this.mAppId = zzcm;
        this.zzfrf = Status.zzfko;
    }

    public static String zzaiv() {
        return zzfu("getGoogleAppId").mAppId;
    }

    public static boolean zzaiw() {
        return zzfu("isMeasurementExplicitlyDisabled").zzfrh;
    }

    public static Status zzci(Context context) {
        Status status;
        zzbq.checkNotNull(context, "Context must not be null.");
        synchronized (sLock) {
            if (zzfre == null) {
                zzfre = new zzcc(context);
            }
            status = zzfre.zzfrf;
        }
        return status;
    }

    private static zzcc zzfu(String str) {
        zzcc zzccVar;
        synchronized (sLock) {
            if (zzfre == null) {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
                sb.append("Initialize must be called before ");
                sb.append(str);
                sb.append(".");
                throw new IllegalStateException(sb.toString());
            }
            zzccVar = zzfre;
        }
        return zzccVar;
    }
}
