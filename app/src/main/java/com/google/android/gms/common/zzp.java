package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgc;

/* loaded from: classes.dex */
public class zzp {
    private static zzp zzfiv;
    private final Context mContext;

    private zzp(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static zzg zza(PackageInfo packageInfo, zzg... zzgVarArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzh zzhVar = new zzh(packageInfo.signatures[0].toByteArray());
        for (int i = 0; i < zzgVarArr.length; i++) {
            if (zzgVarArr[i].equals(zzhVar)) {
                return zzgVarArr[i];
            }
        }
        return null;
    }

    private static boolean zza(PackageInfo packageInfo, boolean z) {
        if (!(packageInfo == null || packageInfo.signatures == null)) {
            if (zza(packageInfo, z ? zzj.zzfil : new zzg[]{zzj.zzfil[0]}) != null) {
                return true;
            }
        }
        return false;
    }

    private static boolean zzb(PackageInfo packageInfo, boolean z) {
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return false;
        }
        zzh zzhVar = new zzh(packageInfo.signatures[0].toByteArray());
        String str = packageInfo.packageName;
        boolean zzb = z ? zzf.zzb(str, zzhVar) : zzf.zza(str, zzhVar);
        if (!zzb) {
            StringBuilder sb = new StringBuilder(27);
            sb.append("Cert not in list. atk=");
            sb.append(z);
            Log.d("GoogleSignatureVerifier", sb.toString());
        }
        return zzb;
    }

    public static zzp zzcg(Context context) {
        zzbq.checkNotNull(context);
        synchronized (zzp.class) {
            if (zzfiv == null) {
                zzf.zzce(context);
                zzfiv = new zzp(context);
            }
        }
        return zzfiv;
    }

    private final boolean zzft(String str) {
        try {
            PackageInfo packageInfo = zzbgc.zzcy(this.mContext).getPackageInfo(str, 64);
            if (packageInfo == null) {
                return false;
            }
            if (zzo.zzcf(this.mContext)) {
                return zzb(packageInfo, true);
            }
            boolean zzb = zzb(packageInfo, false);
            if (!zzb && zzb(packageInfo, true)) {
                Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
            }
            return zzb;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean zza(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zza(packageInfo, false)) {
            return true;
        }
        if (zza(packageInfo, true)) {
            if (zzo.zzcf(this.mContext)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    public final boolean zzbq(int i) {
        String[] packagesForUid = zzbgc.zzcy(this.mContext).getPackagesForUid(i);
        if (packagesForUid == null || packagesForUid.length == 0) {
            return false;
        }
        for (String str : packagesForUid) {
            if (zzft(str)) {
                return true;
            }
        }
        return false;
    }
}
