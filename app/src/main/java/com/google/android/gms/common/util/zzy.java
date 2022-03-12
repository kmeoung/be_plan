package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.WorkSource;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.internal.zzbgc;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class zzy {
    private static final Method zzgcc = zzamd();
    private static final Method zzgcd = zzame();
    private static final Method zzgce = zzamf();
    private static final Method zzgcf = zzamg();
    private static final Method zzgcg = zzamh();

    private static int zza(WorkSource workSource) {
        if (zzgce != null) {
            try {
                return ((Integer) zzgce.invoke(workSource, new Object[0])).intValue();
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        }
        return 0;
    }

    @Nullable
    private static String zza(WorkSource workSource, int i) {
        if (zzgcg == null) {
            return null;
        }
        try {
            return (String) zzgcg.invoke(workSource, Integer.valueOf(i));
        } catch (Exception e) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            return null;
        }
    }

    @Nullable
    public static WorkSource zzaa(Context context, @Nullable String str) {
        if (context == null || context.getPackageManager() == null || str == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = zzbgc.zzcy(context).getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                return zze(applicationInfo.uid, str);
            }
            String valueOf = String.valueOf(str);
            Log.e("WorkSourceUtil", valueOf.length() != 0 ? "Could not get applicationInfo from package: ".concat(valueOf) : new String("Could not get applicationInfo from package: "));
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            String valueOf2 = String.valueOf(str);
            Log.e("WorkSourceUtil", valueOf2.length() != 0 ? "Could not find package: ".concat(valueOf2) : new String("Could not find package: "));
            return null;
        }
    }

    private static Method zzamd() {
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE);
        } catch (Exception unused) {
            return null;
        }
    }

    private static Method zzame() {
        if (!zzq.zzaly()) {
            return null;
        }
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE, String.class);
        } catch (Exception unused) {
            return null;
        }
    }

    private static Method zzamf() {
        try {
            return WorkSource.class.getMethod("size", new Class[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    private static Method zzamg() {
        try {
            return WorkSource.class.getMethod("get", Integer.TYPE);
        } catch (Exception unused) {
            return null;
        }
    }

    private static Method zzamh() {
        if (!zzq.zzaly()) {
            return null;
        }
        try {
            return WorkSource.class.getMethod("getName", Integer.TYPE);
        } catch (Exception unused) {
            return null;
        }
    }

    public static List<String> zzb(@Nullable WorkSource workSource) {
        int zza = workSource == null ? 0 : zza(workSource);
        if (zza == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < zza; i++) {
            String zza2 = zza(workSource, i);
            if (!zzu.zzgn(zza2)) {
                arrayList.add(zza2);
            }
        }
        return arrayList;
    }

    public static boolean zzcv(Context context) {
        return (context == null || context.getPackageManager() == null || zzbgc.zzcy(context).checkPermission("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) != 0) ? false : true;
    }

    private static WorkSource zze(int i, String str) {
        WorkSource workSource = new WorkSource();
        try {
            if (zzgcd != null) {
                if (str == null) {
                    str = "";
                }
                zzgcd.invoke(workSource, Integer.valueOf(i), str);
                return workSource;
            }
            if (zzgcc != null) {
                zzgcc.invoke(workSource, Integer.valueOf(i));
            }
            return workSource;
        } catch (Exception e) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            return workSource;
        }
    }
}
