package com.google.android.gms.common;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzba;
import com.google.android.gms.common.internal.zzbb;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzf {
    private static zzba zzfie;
    private static final Object zzfif = new Object();
    private static Context zzfig;

    public static boolean zza(String str, zzg zzgVar) {
        return zza(str, zzgVar, false);
    }

    private static boolean zza(String str, zzg zzgVar, boolean z) {
        if (!zzafn()) {
            return false;
        }
        zzbq.checkNotNull(zzfig);
        try {
            return zzfie.zza(new zzm(str, zzgVar, z), zzn.zzy(zzfig.getPackageManager()));
        } catch (RemoteException e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    private static boolean zzafn() {
        if (zzfie != null) {
            return true;
        }
        zzbq.checkNotNull(zzfig);
        synchronized (zzfif) {
            if (zzfie == null) {
                try {
                    zzfie = zzbb.zzam(DynamiteModule.zza(zzfig, DynamiteModule.zzguf, "com.google.android.gms.googlecertificates").zzgw("com.google.android.gms.common.GoogleCertificatesImpl"));
                } catch (DynamiteModule.zzc e) {
                    Log.e("GoogleCertificates", "Failed to load com.google.android.gms.googlecertificates", e);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean zzb(String str, zzg zzgVar) {
        return zza(str, zzgVar, true);
    }

    public static synchronized void zzce(Context context) {
        synchronized (zzf.class) {
            if (zzfig != null) {
                Log.w("GoogleCertificates", "GoogleCertificates has been initialized already");
            } else if (context != null) {
                zzfig = context.getApplicationContext();
            }
        }
    }
}
