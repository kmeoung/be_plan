package com.google.firebase.iid;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class FirebaseInstanceId {
    private static zzj zzntt;
    private static ScheduledThreadPoolExecutor zzntu;
    private final FirebaseApp zzmfl;
    private final zzi zzntv;
    private final String zzntw;
    private boolean zzntx = false;
    private static final long zznts = TimeUnit.HOURS.toSeconds(8);
    private static Map<String, FirebaseInstanceId> zzick = new ArrayMap();

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003d, code lost:
        if (r3.isEmpty() != false) goto L_0x0034;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private FirebaseInstanceId(com.google.firebase.FirebaseApp r3, com.google.firebase.iid.zzi r4) {
        /*
            r2 = this;
            r2.<init>()
            r0 = 0
            r2.zzntx = r0
            r2.zzmfl = r3
            r2.zzntv = r4
            com.google.firebase.FirebaseApp r3 = r2.zzmfl
            com.google.firebase.FirebaseOptions r3 = r3.getOptions()
            java.lang.String r3 = r3.getGcmSenderId()
            r4 = 0
            if (r3 == 0) goto L_0x0018
            goto L_0x0040
        L_0x0018:
            com.google.firebase.FirebaseApp r3 = r2.zzmfl
            com.google.firebase.FirebaseOptions r3 = r3.getOptions()
            java.lang.String r3 = r3.getApplicationId()
            java.lang.String r0 = "1:"
            boolean r0 = r3.startsWith(r0)
            if (r0 == 0) goto L_0x0040
            java.lang.String r0 = ":"
            java.lang.String[] r3 = r3.split(r0)
            int r0 = r3.length
            r1 = 2
            if (r0 >= r1) goto L_0x0036
        L_0x0034:
            r3 = r4
            goto L_0x0040
        L_0x0036:
            r0 = 1
            r3 = r3[r0]
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x0040
            goto L_0x0034
        L_0x0040:
            r2.zzntw = r3
            java.lang.String r3 = r2.zzntw
            if (r3 != 0) goto L_0x004e
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "IID failing to initialize, FirebaseApp is missing project ID"
            r3.<init>(r4)
            throw r3
        L_0x004e:
            com.google.firebase.iid.zzac r3 = r2.zzcgw()
            if (r3 == 0) goto L_0x0064
            java.lang.String r4 = com.google.firebase.iid.zzi.zzicq
            boolean r3 = r3.zzqy(r4)
            if (r3 != 0) goto L_0x0064
            com.google.firebase.iid.zzj r3 = com.google.firebase.iid.FirebaseInstanceId.zzntt
            java.lang.String r3 = r3.zzche()
            if (r3 == 0) goto L_0x0067
        L_0x0064:
            r2.startSync()
        L_0x0067:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.FirebaseInstanceId.<init>(com.google.firebase.FirebaseApp, com.google.firebase.iid.zzi):void");
    }

    public static FirebaseInstanceId getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @Keep
    public static synchronized FirebaseInstanceId getInstance(@NonNull FirebaseApp firebaseApp) {
        FirebaseInstanceId firebaseInstanceId;
        synchronized (FirebaseInstanceId.class) {
            firebaseInstanceId = zzick.get(firebaseApp.getOptions().getApplicationId());
            if (firebaseInstanceId == null) {
                zzi zza = zzi.zza(firebaseApp.getApplicationContext(), null);
                if (zzntt == null) {
                    zzntt = new zzj(zzi.zzcha());
                }
                FirebaseInstanceId firebaseInstanceId2 = new FirebaseInstanceId(firebaseApp, zza);
                zzick.put(firebaseApp.getOptions().getApplicationId(), firebaseInstanceId2);
                firebaseInstanceId = firebaseInstanceId2;
            }
        }
        return firebaseInstanceId;
    }

    public static String zza(KeyPair keyPair) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(keyPair.getPublic().getEncoded());
            digest[0] = (byte) ((digest[0] & 15) + 112);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException unused) {
            Log.w("FirebaseInstanceId", "Unexpected error, device missing required algorithms");
            return null;
        }
    }

    private final void zzac(Bundle bundle) {
        bundle.putString("gmp_app_id", this.zzmfl.getOptions().getApplicationId());
    }

    public static int zzam(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
            sb.append("Failed to find package ");
            sb.append(valueOf);
            Log.w("FirebaseInstanceId", sb.toString());
            return 0;
        }
    }

    public static void zzb(Runnable runnable, long j) {
        synchronized (FirebaseInstanceId.class) {
            if (zzntu == null) {
                zzntu = new ScheduledThreadPoolExecutor(1);
            }
            zzntu.schedule(runnable, j, TimeUnit.SECONDS);
        }
    }

    public static zzj zzcgy() {
        return zzntt;
    }

    public static boolean zzcgz() {
        if (!Log.isLoggable("FirebaseInstanceId", 3)) {
            return Build.VERSION.SDK_INT == 23 && Log.isLoggable("FirebaseInstanceId", 3);
        }
        return true;
    }

    public static String zzdk(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 38);
            sb.append("Never happens: can't find own package ");
            sb.append(valueOf);
            Log.w("FirebaseInstanceId", sb.toString());
            return null;
        }
    }

    public static int zzes(Context context) {
        return zzam(context, context.getPackageName());
    }

    public static String zzn(byte[] bArr) {
        return Base64.encodeToString(bArr, 11);
    }

    @WorkerThread
    public void deleteInstanceId() throws IOException {
        this.zzntv.zza("*", "*", (Bundle) null);
        this.zzntv.zzaut();
    }

    @WorkerThread
    public void deleteToken(String str, String str2) throws IOException {
        Bundle bundle = new Bundle();
        zzac(bundle);
        this.zzntv.zza(str, str2, bundle);
    }

    public final FirebaseApp getApp() {
        return this.zzmfl;
    }

    public long getCreationTime() {
        return this.zzntv.getCreationTime();
    }

    @WorkerThread
    public String getId() {
        return zza(this.zzntv.zzaus());
    }

    @Nullable
    public String getToken() {
        zzac zzcgw = zzcgw();
        if (zzcgw == null || zzcgw.zzqy(zzi.zzicq)) {
            startSync();
        }
        if (zzcgw != null) {
            return zzcgw.zzlan;
        }
        return null;
    }

    @WorkerThread
    public String getToken(String str, String str2) throws IOException {
        Bundle bundle = new Bundle();
        zzac(bundle);
        return this.zzntv.getToken(str, str2, bundle);
    }

    public final synchronized void startSync() {
        if (!this.zzntx) {
            zzcb(0L);
        }
    }

    public final synchronized void zzcb(long j) {
        zzb(new zzad(this, Math.min(Math.max(30L, j << 1), zznts)), j);
        this.zzntx = true;
    }

    @Nullable
    public final zzac zzcgw() {
        return zzi.zzcha().zzo("", this.zzntw, "*");
    }

    public final String zzcgx() throws IOException {
        return getToken(this.zzntw, "*");
    }

    public final synchronized void zzco(boolean z) {
        this.zzntx = z;
    }

    public final synchronized void zzqp(String str) {
        zzntt.zzqp(str);
        startSync();
    }

    public final void zzqq(String str) throws IOException {
        zzac zzcgw = zzcgw();
        if (zzcgw == null || zzcgw.zzqy(zzi.zzicq)) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str);
        bundle.putString("gcm.topic", valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        String str2 = zzcgw.zzlan;
        String valueOf3 = String.valueOf("/topics/");
        String valueOf4 = String.valueOf(str);
        String concat = valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3);
        zzac(bundle);
        this.zzntv.zzb(str2, concat, bundle);
    }

    public final void zzqr(String str) throws IOException {
        zzac zzcgw = zzcgw();
        if (zzcgw == null || zzcgw.zzqy(zzi.zzicq)) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str);
        bundle.putString("gcm.topic", valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        zzi zziVar = this.zzntv;
        String str2 = zzcgw.zzlan;
        String valueOf3 = String.valueOf("/topics/");
        String valueOf4 = String.valueOf(str);
        zziVar.zza(str2, valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3), bundle);
    }
}
