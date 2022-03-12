package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.zzal;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.Locale;

/* loaded from: classes.dex */
public final class zzbfd {
    private final String mTag;
    private final int zzdsq;
    private final String zzfyf;
    private final zzal zzfzd;

    private zzbfd(String str, String str2) {
        this.zzfyf = str2;
        this.mTag = str;
        this.zzfzd = new zzal(str);
        this.zzdsq = getLogLevel();
    }

    public zzbfd(String str, String... strArr) {
        this(str, zzc(strArr));
    }

    private final String format(String str, @Nullable Object... objArr) {
        if (objArr != null && objArr.length > 0) {
            str = String.format(Locale.US, str, objArr);
        }
        return this.zzfyf.concat(str);
    }

    private final int getLogLevel() {
        int i = 2;
        while (7 >= i && !Log.isLoggable(this.mTag, i)) {
            i++;
        }
        return i;
    }

    private static String zzc(String... strArr) {
        if (strArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (String str : strArr) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            sb.append(str);
        }
        sb.append(']');
        sb.append(TokenParser.SP);
        return sb.toString();
    }

    public final void zza(String str, Throwable th, @Nullable Object... objArr) {
        Log.wtf(this.mTag, format(str, objArr), th);
    }

    public final void zzb(String str, @Nullable Object... objArr) {
        if (this.zzdsq <= 3) {
            Log.d(this.mTag, format(str, objArr));
        }
    }

    public final void zze(String str, @Nullable Object... objArr) {
        Log.i(this.mTag, format(str, objArr));
    }

    public final void zze(Throwable th) {
        Log.wtf(this.mTag, th);
    }

    public final void zzf(String str, @Nullable Object... objArr) {
        Log.w(this.mTag, format(str, objArr));
    }
}
