package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzo;
import com.google.android.gms.internal.zzex;
import com.google.android.gms.internal.zzey;
import io.fabric.sdk.android.services.common.AdvertisingInfoServiceStrategy;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@KeepForSdkWithMembers
/* loaded from: classes.dex */
public class AdvertisingIdClient {
    private final Context mContext;
    @Nullable
    private com.google.android.gms.common.zza zzalv;
    @Nullable
    private zzex zzalw;
    private boolean zzalx;
    private Object zzaly;
    @Nullable
    private zza zzalz;
    private boolean zzama;
    private long zzamb;

    /* loaded from: classes.dex */
    public static final class Info {
        private final String zzamh;
        private final boolean zzami;

        public Info(String str, boolean z) {
            this.zzamh = str;
            this.zzami = z;
        }

        public final String getId() {
            return this.zzamh;
        }

        public final boolean isLimitAdTrackingEnabled() {
            return this.zzami;
        }

        public final String toString() {
            String str = this.zzamh;
            boolean z = this.zzami;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 7);
            sb.append("{");
            sb.append(str);
            sb.append("}");
            sb.append(z);
            return sb.toString();
        }
    }

    /* loaded from: classes.dex */
    public static class zza extends Thread {
        private WeakReference<AdvertisingIdClient> zzamd;
        private long zzame;
        CountDownLatch zzamf = new CountDownLatch(1);
        boolean zzamg = false;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.zzamd = new WeakReference<>(advertisingIdClient);
            this.zzame = j;
            start();
        }

        private final void disconnect() {
            AdvertisingIdClient advertisingIdClient = this.zzamd.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzamg = true;
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                if (!this.zzamf.await(this.zzame, TimeUnit.MILLISECONDS)) {
                    disconnect();
                }
            } catch (InterruptedException unused) {
                disconnect();
            }
        }
    }

    public AdvertisingIdClient(Context context) {
        this(context, 30000L, false, false);
    }

    public AdvertisingIdClient(Context context, long j, boolean z, boolean z2) {
        Context applicationContext;
        this.zzaly = new Object();
        zzbq.checkNotNull(context);
        if (z && (applicationContext = context.getApplicationContext()) != null) {
            context = applicationContext;
        }
        this.mContext = context;
        this.zzalx = false;
        this.zzamb = j;
        this.zzama = z2;
    }

    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        Info info;
        zzd zzdVar = new zzd(context);
        boolean z = zzdVar.getBoolean("gads:ad_id_app_context:enabled", false);
        float f = zzdVar.getFloat("gads:ad_id_app_context:ping_ratio", 0.0f);
        boolean z2 = zzdVar.getBoolean("gads:ad_id_use_shared_preference:enabled", false);
        String string = zzdVar.getString("gads:ad_id_use_shared_preference:experiment_id", "");
        boolean z3 = zzdVar.getBoolean("com.google.android.gms.ads.identifier.service.PERSISTENT_START", false);
        if (z2 && (info = zzb.zzc(context).getInfo()) != null) {
            return info;
        }
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1L, z, z3);
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            advertisingIdClient.start(false);
            Info info2 = advertisingIdClient.getInfo();
            advertisingIdClient.zza(info2, z, f, SystemClock.elapsedRealtime() - elapsedRealtime, string, null);
            return info2;
        } finally {
            try {
            } finally {
            }
        }
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean z) {
    }

    private final void start(boolean z) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzbq.zzgi("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzalx) {
                finish();
            }
            this.zzalv = zzc(this.mContext, this.zzama);
            this.zzalw = zza(this.mContext, this.zzalv);
            this.zzalx = true;
            if (z) {
                zzbj();
            }
        }
    }

    private static zzex zza(Context context, com.google.android.gms.common.zza zzaVar) throws IOException {
        try {
            return zzey.zzc(zzaVar.zza(10000L, TimeUnit.MILLISECONDS));
        } catch (InterruptedException unused) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }

    private final boolean zza(Info info, boolean z, float f, long j, String str, Throwable th) {
        if (Math.random() > f) {
            return false;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("app_context", z ? "1" : "0");
        if (info != null) {
            hashMap.put("limit_ad_tracking", info.isLimitAdTrackingEnabled() ? "1" : "0");
        }
        if (!(info == null || info.getId() == null)) {
            hashMap.put("ad_id_size", Integer.toString(info.getId().length()));
        }
        if (th != null) {
            hashMap.put("error", th.getClass().getName());
        }
        if (str != null && !str.isEmpty()) {
            hashMap.put("experiment_id", str);
        }
        hashMap.put("tag", "AdvertisingIdClient");
        hashMap.put("time_spent", Long.toString(j));
        new zza(this, hashMap).start();
        return true;
    }

    private final void zzbj() {
        synchronized (this.zzaly) {
            if (this.zzalz != null) {
                this.zzalz.zzamf.countDown();
                try {
                    this.zzalz.join();
                } catch (InterruptedException unused) {
                }
            }
            if (this.zzamb > 0) {
                this.zzalz = new zza(this, this.zzamb);
            }
        }
    }

    private static com.google.android.gms.common.zza zzc(Context context, boolean z) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo(zzo.GOOGLE_PLAY_STORE_PACKAGE, 0);
            int isGooglePlayServicesAvailable = zze.zzafm().isGooglePlayServicesAvailable(context);
            if (isGooglePlayServicesAvailable == 0 || isGooglePlayServicesAvailable == 2) {
                String str = z ? "com.google.android.gms.ads.identifier.service.PERSISTENT_START" : AdvertisingInfoServiceStrategy.GOOGLE_PLAY_SERVICES_INTENT;
                com.google.android.gms.common.zza zzaVar = new com.google.android.gms.common.zza();
                Intent intent = new Intent(str);
                intent.setPackage("com.google.android.gms");
                try {
                    if (com.google.android.gms.common.stats.zza.zzalq().zza(context, intent, zzaVar, 1)) {
                        return zzaVar;
                    }
                    throw new IOException("Connection failure");
                } catch (Throwable th) {
                    throw new IOException(th);
                }
            } else {
                throw new IOException("Google Play services not available");
            }
        } catch (PackageManager.NameNotFoundException unused) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    public void finish() {
        zzbq.zzgi("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.mContext != null && this.zzalv != null) {
                if (this.zzalx) {
                    com.google.android.gms.common.stats.zza.zzalq();
                    this.mContext.unbindService(this.zzalv);
                }
                this.zzalx = false;
                this.zzalw = null;
                this.zzalv = null;
            }
        }
    }

    public Info getInfo() throws IOException {
        Info info;
        zzbq.zzgi("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzalx) {
                synchronized (this.zzaly) {
                    if (this.zzalz != null && this.zzalz.zzamg) {
                    }
                    throw new IOException("AdvertisingIdClient is not connected.");
                }
                try {
                    start(false);
                    if (!this.zzalx) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (Exception e) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e);
                }
            }
            zzbq.checkNotNull(this.zzalv);
            zzbq.checkNotNull(this.zzalw);
            try {
                info = new Info(this.zzalw.getId(), this.zzalw.zzb(true));
            } catch (RemoteException e2) {
                Log.i("AdvertisingIdClient", "GMS remote exception ", e2);
                throw new IOException("Remote exception");
            }
        }
        zzbj();
        return info;
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        start(true);
    }
}
