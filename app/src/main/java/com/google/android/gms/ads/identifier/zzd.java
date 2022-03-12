package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.common.zzo;

/* loaded from: classes.dex */
public final class zzd {
    private SharedPreferences zzamn;

    public zzd(Context context) {
        try {
            Context remoteContext = zzo.getRemoteContext(context);
            this.zzamn = remoteContext == null ? null : remoteContext.getSharedPreferences("google_ads_flags", 0);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while getting SharedPreferences ", th);
            this.zzamn = null;
        }
    }

    public final boolean getBoolean(String str, boolean z) {
        try {
            if (this.zzamn == null) {
                return false;
            }
            return this.zzamn.getBoolean(str, false);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return false;
        }
    }

    public final float getFloat(String str, float f) {
        try {
            if (this.zzamn == null) {
                return 0.0f;
            }
            return this.zzamn.getFloat(str, 0.0f);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return 0.0f;
        }
    }

    public final String getString(String str, String str2) {
        try {
            return this.zzamn == null ? str2 : this.zzamn.getString(str, str2);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return str2;
        }
    }
}
