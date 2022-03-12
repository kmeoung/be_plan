package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.zzo;
import java.util.HashMap;

/* loaded from: classes.dex */
public class zzb {
    private static zzb zzamj;
    private final Context zzaif;

    private zzb(Context context) {
        this.zzaif = context;
    }

    private final void zza(AdvertisingIdClient.Info info, boolean z, long j) {
        if (Math.random() <= new zzd(this.zzaif).getFloat("gads:ad_id_use_shared_preference:ping_ratio", 0.0f)) {
            new Thread(new Runnable(info, z, j) { // from class: com.google.android.gms.ads.identifier.zzc
                private final AdvertisingIdClient.Info zzamk;
                private final boolean zzaml;
                private final long zzamm;

                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    this.zzamk = info;
                    this.zzaml = z;
                    this.zzamm = j;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    AdvertisingIdClient.Info info2 = this.zzamk;
                    boolean z2 = this.zzaml;
                    long j2 = this.zzamm;
                    HashMap hashMap = new HashMap();
                    hashMap.put("ad_id_size", Integer.toString(info2 == null ? -1 : info2.getId().length()));
                    hashMap.put("has_gmscore", z2 ? "1" : "0");
                    hashMap.put("tag", "AdvertisingIdLightClient");
                    hashMap.put("time_spent", Long.toString(j2));
                    new zze().zzc(hashMap);
                }
            }).start();
        }
    }

    public static zzb zzc(Context context) {
        zzb zzbVar;
        synchronized (zzb.class) {
            if (zzamj == null) {
                zzamj = new zzb(context);
            }
            zzbVar = zzamj;
        }
        return zzbVar;
    }

    public final AdvertisingIdClient.Info getInfo() {
        Context remoteContext = zzo.getRemoteContext(this.zzaif);
        AdvertisingIdClient.Info info = null;
        if (remoteContext == null) {
            zza(null, false, -1L);
            return null;
        }
        SharedPreferences sharedPreferences = remoteContext.getSharedPreferences("adid_settings", 0);
        if (sharedPreferences == null) {
            zza(null, false, -1L);
            return null;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (sharedPreferences.contains("adid_key") && sharedPreferences.contains("enable_limit_ad_tracking")) {
            info = new AdvertisingIdClient.Info(sharedPreferences.getString("adid_key", ""), sharedPreferences.getBoolean("enable_limit_ad_tracking", false));
        }
        zza(info, true, SystemClock.elapsedRealtime() - elapsedRealtime);
        return info;
    }
}
