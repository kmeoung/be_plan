package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Process;
import com.google.android.gms.common.util.zzq;

/* loaded from: classes.dex */
public final class zzbgb {
    private Context mContext;

    public zzbgb(Context context) {
        this.mContext = context;
    }

    public final int checkCallingOrSelfPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str);
    }

    public final int checkPermission(String str, String str2) {
        return this.mContext.getPackageManager().checkPermission(str, str2);
    }

    public final ApplicationInfo getApplicationInfo(String str, int i) throws PackageManager.NameNotFoundException {
        return this.mContext.getPackageManager().getApplicationInfo(str, i);
    }

    public final PackageInfo getPackageInfo(String str, int i) throws PackageManager.NameNotFoundException {
        return this.mContext.getPackageManager().getPackageInfo(str, i);
    }

    public final String[] getPackagesForUid(int i) {
        return this.mContext.getPackageManager().getPackagesForUid(i);
    }

    public final boolean zzami() {
        String nameForUid;
        if (Binder.getCallingUid() == Process.myUid()) {
            return zzbga.zzcw(this.mContext);
        }
        if (!zzq.isAtLeastO() || (nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid())) == null) {
            return false;
        }
        return this.mContext.getPackageManager().isInstantApp(nameForUid);
    }

    @TargetApi(19)
    public final boolean zzf(int i, String str) {
        if (zzq.zzalz()) {
            try {
                ((AppOpsManager) this.mContext.getSystemService("appops")).checkPackage(i, str);
                return true;
            } catch (SecurityException unused) {
                return false;
            }
        } else {
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
            if (!(str == null || packagesForUid == null)) {
                for (String str2 : packagesForUid) {
                    if (str.equals(str2)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public final CharSequence zzgo(String str) throws PackageManager.NameNotFoundException {
        return this.mContext.getPackageManager().getApplicationLabel(this.mContext.getPackageManager().getApplicationInfo(str, 0));
    }
}
