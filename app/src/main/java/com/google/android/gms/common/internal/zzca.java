package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.R;

/* loaded from: classes.dex */
public final class zzca {
    private final Resources zzfyz;
    private final String zzfza;

    public zzca(Context context) {
        zzbq.checkNotNull(context);
        this.zzfyz = context.getResources();
        this.zzfza = this.zzfyz.getResourcePackageName(R.string.common_google_play_services_unknown_issue);
    }

    public final String getString(String str) {
        int identifier = this.zzfyz.getIdentifier(str, "string", this.zzfza);
        if (identifier == 0) {
            return null;
        }
        return this.zzfyz.getString(identifier);
    }
}
