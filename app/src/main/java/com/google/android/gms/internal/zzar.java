package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import java.io.File;

/* loaded from: classes.dex */
public final class zzar {
    public static zzs zza(Context context, zzam zzamVar) {
        File file = new File(context.getCacheDir(), "volley");
        String str = "volley/0";
        try {
            String packageName = context.getPackageName();
            int i = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
            StringBuilder sb = new StringBuilder(String.valueOf(packageName).length() + 12);
            sb.append(packageName);
            sb.append("/");
            sb.append(i);
            str = sb.toString();
        } catch (PackageManager.NameNotFoundException unused) {
        }
        zzs zzsVar = new zzs(new zzag(file), new zzad(Build.VERSION.SDK_INT >= 9 ? new zzan() : new zzaj(AndroidHttpClient.newInstance(str))));
        zzsVar.start();
        return zzsVar;
    }
}
