package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.util.zzx;
import com.google.android.gms.internal.zzbgc;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class zzo {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 11720000;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    private static boolean zzfip = false;
    private static boolean zzfiq = false;
    private static boolean zzfir = false;
    private static boolean zzfis = false;
    static final AtomicBoolean zzfit = new AtomicBoolean();
    private static final AtomicBoolean zzfiu = new AtomicBoolean();

    zzo() {
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return zze.zzafm().getErrorResolutionPendingIntent(context, i, i2);
    }

    @Deprecated
    public static String getErrorString(int i) {
        return ConnectionResult.getStatusString(i);
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext("com.google.android.gms", 3);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication("com.google.android.gms");
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00be, code lost:
        if (com.google.android.gms.common.zzp.zza(r6, r8) == null) goto L_0x00c0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00c0, code lost:
        r8 = "GooglePlayServicesUtil";
        r0 = "Google Play services signature invalid.";
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00cb, code lost:
        if (com.google.android.gms.common.zzp.zza(r6, com.google.android.gms.common.zzj.zzfil) == null) goto L_0x00c0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00d6, code lost:
        if ((r6.versionCode / 1000) >= (com.google.android.gms.common.zzo.GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000)) goto L_0x00fe;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00d8, code lost:
        r0 = com.google.android.gms.common.zzo.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        r1 = r6.versionCode;
        r3 = new java.lang.StringBuilder(77);
        r3.append("Google Play services out of date.  Requires ");
        r3.append(r0);
        r3.append(" but found ");
        r3.append(r1);
        android.util.Log.w("GooglePlayServicesUtil", r3.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00fd, code lost:
        return 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00fe, code lost:
        r8 = r6.applicationInfo;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0100, code lost:
        if (r8 != null) goto L_0x0112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0102, code lost:
        r8 = r0.getApplicationInfo("com.google.android.gms", 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0109, code lost:
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x010a, code lost:
        android.util.Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0111, code lost:
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0114, code lost:
        if (r8.enabled != false) goto L_0x0118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0116, code lost:
        return 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0118, code lost:
        return 0;
     */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int isGooglePlayServicesAvailable(android.content.Context r8) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zzo.isGooglePlayServicesAvailable(android.content.Context):int");
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        if (i == 9) {
            return true;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    @TargetApi(19)
    @Deprecated
    public static boolean zzb(Context context, int i, String str) {
        return zzx.zzb(context, i, str);
    }

    @Deprecated
    public static void zzbn(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = zze.zzafm().isGooglePlayServicesAvailable(context);
        if (isGooglePlayServicesAvailable != 0) {
            zze.zzafm();
            Intent zza = zze.zza(context, isGooglePlayServicesAvailable, "e");
            StringBuilder sb = new StringBuilder(57);
            sb.append("GooglePlayServices not available due to error ");
            sb.append(isGooglePlayServicesAvailable);
            Log.e("GooglePlayServicesUtil", sb.toString());
            if (zza == null) {
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
            }
            throw new GooglePlayServicesRepairableException(isGooglePlayServicesAvailable, "Google Play Services not available", zza);
        }
    }

    @Deprecated
    public static void zzcc(Context context) {
        if (!zzfit.getAndSet(true)) {
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                if (notificationManager != null) {
                    notificationManager.cancel(10436);
                }
            } catch (SecurityException unused) {
            }
        }
    }

    @Deprecated
    public static int zzcd(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 0;
        }
    }

    public static boolean zzcf(Context context) {
        PackageInfo packageInfo;
        try {
            if (!zzfis) {
                try {
                    packageInfo = zzbgc.zzcy(context).getPackageInfo("com.google.android.gms", 64);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.w("GooglePlayServicesUtil", "Cannot find Google Play services package name.", e);
                }
                if (packageInfo != null) {
                    zzp.zzcg(context);
                    if (zzp.zza(packageInfo, zzj.zzfil[1]) != null) {
                        zzfir = true;
                    }
                }
                zzfir = false;
            }
            return zzfir || !"user".equals(Build.TYPE);
        } finally {
            zzfis = true;
        }
    }

    @Deprecated
    public static boolean zze(Context context, int i) {
        if (i == 18) {
            return true;
        }
        if (i == 1) {
            return zzv(context, "com.google.android.gms");
        }
        return false;
    }

    @Deprecated
    public static boolean zzf(Context context, int i) {
        return zzx.zzf(context, i);
    }

    @TargetApi(21)
    static boolean zzv(Context context, String str) {
        ApplicationInfo applicationInfo;
        Bundle applicationRestrictions;
        boolean equals = str.equals("com.google.android.gms");
        if (zzq.zzamb()) {
            try {
                for (PackageInstaller.SessionInfo sessionInfo : context.getPackageManager().getPackageInstaller().getAllSessions()) {
                    if (str.equals(sessionInfo.getAppPackageName())) {
                        return true;
                    }
                }
            } catch (Exception unused) {
                return false;
            }
        }
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 8192);
        } catch (PackageManager.NameNotFoundException unused2) {
        }
        if (equals) {
            return applicationInfo.enabled;
        }
        if (applicationInfo.enabled) {
            if (!(zzq.zzaly() && (applicationRestrictions = ((UserManager) context.getSystemService("user")).getApplicationRestrictions(context.getPackageName())) != null && "true".equals(applicationRestrictions.getString("restricted_profile")))) {
                return true;
            }
        }
        return false;
    }
}
