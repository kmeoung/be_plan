package com.google.firebase.messaging;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.util.zzq;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zza {
    private static zza zznvh;
    private final Context mContext;
    private Bundle zzftk;
    private Method zznvi;
    private Method zznvj;
    private final AtomicInteger zznvk = new AtomicInteger((int) SystemClock.elapsedRealtime());

    private zza(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @TargetApi(26)
    private final Notification zza(CharSequence charSequence, String str, int i, Integer num, Uri uri, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str2) {
        Notification.Builder smallIcon = new Notification.Builder(this.mContext).setAutoCancel(true).setSmallIcon(i);
        if (!TextUtils.isEmpty(charSequence)) {
            smallIcon.setContentTitle(charSequence);
        }
        if (!TextUtils.isEmpty(str)) {
            smallIcon.setContentText(str);
            smallIcon.setStyle(new Notification.BigTextStyle().bigText(str));
        }
        if (num != null) {
            smallIcon.setColor(num.intValue());
        }
        if (uri != null) {
            smallIcon.setSound(uri);
        }
        if (pendingIntent != null) {
            smallIcon.setContentIntent(pendingIntent);
        }
        if (pendingIntent2 != null) {
            smallIcon.setDeleteIntent(pendingIntent2);
        }
        if (str2 != null) {
            if (this.zznvi == null) {
                this.zznvi = zzrb("setChannelId");
            }
            if (this.zznvi == null) {
                this.zznvi = zzrb("setChannel");
            }
            if (this.zznvi == null) {
                Log.e("FirebaseMessaging", "Error while setting the notification channel");
            } else {
                try {
                    this.zznvi.invoke(smallIcon, str2);
                } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
                    Log.e("FirebaseMessaging", "Error while setting the notification channel", e);
                }
            }
        }
        return smallIcon.build();
    }

    private static void zza(Intent intent, Bundle bundle) {
        for (String str : bundle.keySet()) {
            if (str.startsWith("google.c.a.") || str.equals("from")) {
                intent.putExtra(str, bundle.getString(str));
            }
        }
    }

    public static boolean zzah(Bundle bundle) {
        return "1".equals(zze(bundle, "gcm.n.e")) || zze(bundle, "gcm.n.icon") != null;
    }

    @Nullable
    public static Uri zzai(@NonNull Bundle bundle) {
        String zze = zze(bundle, "gcm.n.link_android");
        if (TextUtils.isEmpty(zze)) {
            zze = zze(bundle, "gcm.n.link");
        }
        if (!TextUtils.isEmpty(zze)) {
            return Uri.parse(zze);
        }
        return null;
    }

    public static String zzaj(Bundle bundle) {
        String zze = zze(bundle, "gcm.n.sound2");
        return TextUtils.isEmpty(zze) ? zze(bundle, "gcm.n.sound") : zze;
    }

    private final Bundle zzaui() {
        if (this.zzftk != null) {
            return this.zzftk;
        }
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (applicationInfo == null || applicationInfo.metaData == null) {
            return Bundle.EMPTY;
        }
        this.zzftk = applicationInfo.metaData;
        return this.zzftk;
    }

    public static String zze(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    public static synchronized zza zzex(Context context) {
        zza zzaVar;
        synchronized (zza.class) {
            if (zznvh == null) {
                zznvh = new zza(context);
            }
            zzaVar = zznvh;
        }
        return zzaVar;
    }

    public static String zzh(Bundle bundle, String str) {
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_key");
        return zze(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Object[] zzi(Bundle bundle, String str) {
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_args");
        String zze = zze(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (TextUtils.isEmpty(zze)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(zze);
            String[] strArr = new String[jSONArray.length()];
            for (int i = 0; i < strArr.length; i++) {
                strArr[i] = jSONArray.opt(i);
            }
            return strArr;
        } catch (JSONException unused) {
            String valueOf3 = String.valueOf(str);
            String valueOf4 = String.valueOf("_loc_args");
            String substring = (valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3)).substring(6);
            StringBuilder sb = new StringBuilder(String.valueOf(substring).length() + 41 + String.valueOf(zze).length());
            sb.append("Malformed ");
            sb.append(substring);
            sb.append(": ");
            sb.append(zze);
            sb.append("  Default value will be used.");
            Log.w("FirebaseMessaging", sb.toString());
            return null;
        }
    }

    private final String zzj(Bundle bundle, String str) {
        String zze = zze(bundle, str);
        if (!TextUtils.isEmpty(zze)) {
            return zze;
        }
        String zzh = zzh(bundle, str);
        if (TextUtils.isEmpty(zzh)) {
            return null;
        }
        Resources resources = this.mContext.getResources();
        int identifier = resources.getIdentifier(zzh, "string", this.mContext.getPackageName());
        if (identifier == 0) {
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf("_loc_key");
            String substring = (valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)).substring(6);
            StringBuilder sb = new StringBuilder(String.valueOf(substring).length() + 49 + String.valueOf(zzh).length());
            sb.append(substring);
            sb.append(" resource not found: ");
            sb.append(zzh);
            sb.append(" Default value will be used.");
            Log.w("FirebaseMessaging", sb.toString());
            return null;
        }
        Object[] zzi = zzi(bundle, str);
        if (zzi == null) {
            return resources.getString(identifier);
        }
        try {
            return resources.getString(identifier, zzi);
        } catch (MissingFormatArgumentException e) {
            String arrays = Arrays.toString(zzi);
            StringBuilder sb2 = new StringBuilder(String.valueOf(zzh).length() + 58 + String.valueOf(arrays).length());
            sb2.append("Missing format argument for ");
            sb2.append(zzh);
            sb2.append(": ");
            sb2.append(arrays);
            sb2.append(" Default value will be used.");
            Log.w("FirebaseMessaging", sb2.toString(), e);
            return null;
        }
    }

    @TargetApi(26)
    private static Method zzrb(String str) {
        try {
            return Notification.Builder.class.getMethod(str, String.class);
        } catch (NoSuchMethodException | SecurityException unused) {
            return null;
        }
    }

    private final Integer zzrc(String str) {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.valueOf(Color.parseColor(str));
            } catch (IllegalArgumentException unused) {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 54);
                sb.append("Color ");
                sb.append(str);
                sb.append(" not valid. Notification will use default color.");
                Log.w("FirebaseMessaging", sb.toString());
            }
        }
        int i = zzaui().getInt("com.google.firebase.messaging.default_notification_color", 0);
        if (i != 0) {
            try {
                return Integer.valueOf(ContextCompat.getColor(this.mContext, i));
            } catch (Resources.NotFoundException unused2) {
                Log.w("FirebaseMessaging", "Cannot find the color resource referenced in AndroidManifest.");
            }
        }
        return null;
    }

    @TargetApi(26)
    private final String zzrd(String str) {
        String str2;
        String str3;
        if (!zzq.isAtLeastO()) {
            return null;
        }
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        try {
            if (this.zznvj == null) {
                this.zznvj = notificationManager.getClass().getMethod("getNotificationChannel", String.class);
            }
            if (!TextUtils.isEmpty(str)) {
                if (this.zznvj.invoke(notificationManager, str) != null) {
                    return str;
                }
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 122);
                sb.append("Notification Channel requested (");
                sb.append(str);
                sb.append(") has not been created by the app. Manifest configuration, or default, value will be used.");
                Log.w("FirebaseMessaging", sb.toString());
            }
            String string = zzaui().getString("com.google.firebase.messaging.default_notification_channel_id");
            if (TextUtils.isEmpty(string)) {
                str2 = "FirebaseMessaging";
                str3 = "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.";
            } else if (this.zznvj.invoke(notificationManager, string) != null) {
                return string;
            } else {
                str2 = "FirebaseMessaging";
                str3 = "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.";
            }
            Log.w(str2, str3);
            if (this.zznvj.invoke(notificationManager, "fcm_fallback_notification_channel") != null) {
                return "fcm_fallback_notification_channel";
            }
            Class<?> cls = Class.forName("android.app.NotificationChannel");
            notificationManager.getClass().getMethod("createNotificationChannel", cls).invoke(notificationManager, cls.getConstructor(String.class, CharSequence.class, Integer.TYPE).newInstance("fcm_fallback_notification_channel", this.mContext.getString(R.string.fcm_fallback_notification_channel_label), 3));
            return "fcm_fallback_notification_channel";
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | LinkageError | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e);
            return null;
        }
    }

    private final PendingIntent zzt(Bundle bundle) {
        Intent intent;
        String zze = zze(bundle, "gcm.n.click_action");
        if (!TextUtils.isEmpty(zze)) {
            intent = new Intent(zze);
            intent.setPackage(this.mContext.getPackageName());
            intent.setFlags(268435456);
        } else {
            Uri zzai = zzai(bundle);
            if (zzai != null) {
                intent = new Intent("android.intent.action.VIEW");
                intent.setPackage(this.mContext.getPackageName());
                intent.setData(zzai);
            } else {
                intent = this.mContext.getPackageManager().getLaunchIntentForPackage(this.mContext.getPackageName());
                if (intent == null) {
                    Log.w("FirebaseMessaging", "No activity found to launch app");
                }
            }
        }
        if (intent == null) {
            return null;
        }
        intent.addFlags(67108864);
        Bundle bundle2 = new Bundle(bundle);
        FirebaseMessagingService.zzq(bundle2);
        intent.putExtras(bundle2);
        for (String str : bundle2.keySet()) {
            if (str.startsWith("gcm.n.") || str.startsWith("gcm.notification.")) {
                intent.removeExtra(str);
            }
        }
        return PendingIntent.getActivity(this.mContext, this.zznvk.incrementAndGet(), intent, 1073741824);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x023c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzs(android.os.Bundle r13) {
        /*
            Method dump skipped, instructions count: 599
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zza.zzs(android.os.Bundle):boolean");
    }
}
