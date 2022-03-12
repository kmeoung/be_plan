package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Messenger;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.iid.MessengerCompat;
import com.google.android.gms.tasks.Tasks;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* loaded from: classes.dex */
public final class zzw {
    private static PendingIntent zzhzr;
    private static int zzicx;
    private static long zzidg;
    private static int zznur;
    private static int zznus;
    private Context zzaif;
    private Messenger zzida;
    private MessengerCompat zzidb;
    private final SimpleArrayMap<String, zzz> zznut = new SimpleArrayMap<>();
    private Messenger zzhzv = new Messenger(new zzx(this, Looper.getMainLooper()));

    public zzw(Context context) {
        this.zzaif = context.getApplicationContext();
    }

    private static String zza(KeyPair keyPair, String... strArr) {
        String str;
        String str2;
        byte[] bytes;
        try {
            bytes = TextUtils.join("\n", strArr).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e = e;
            str = "FirebaseInstanceId";
            str2 = "Unable to encode string";
        }
        try {
            PrivateKey privateKey = keyPair.getPrivate();
            Signature instance = Signature.getInstance(privateKey instanceof RSAPrivateKey ? "SHA256withRSA" : "SHA256withECDSA");
            instance.initSign(privateKey);
            instance.update(bytes);
            return FirebaseInstanceId.zzn(instance.sign());
        } catch (GeneralSecurityException e2) {
            e = e2;
            str = "FirebaseInstanceId";
            str2 = "Unable to sign registration request";
            Log.e(str, str2, e);
            return null;
        }
    }

    private final Bundle zzae(Bundle bundle) throws IOException {
        Bundle zzaf = zzaf(bundle);
        if (zzaf == null || !zzaf.containsKey("google.messenger")) {
            return zzaf;
        }
        Bundle zzaf2 = zzaf(bundle);
        if (zzaf2 == null || !zzaf2.containsKey("google.messenger")) {
            return zzaf2;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0150 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x013e -> B:67:0x0149). Please submit an issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x0144 -> B:67:0x0149). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final android.os.Bundle zzaf(android.os.Bundle r10) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzw.zzaf(android.os.Bundle):android.os.Bundle");
    }

    private static synchronized String zzauw() {
        String num;
        synchronized (zzw.class) {
            int i = zzicx;
            zzicx = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    private final void zzbl(String str, String str2) {
        synchronized (this.zznut) {
            if (str == null) {
                for (int i = 0; i < this.zznut.size(); i++) {
                    this.zznut.valueAt(i).onError(str2);
                }
                this.zznut.clear();
            } else {
                zzz remove = this.zznut.remove(str);
                if (remove == null) {
                    String valueOf = String.valueOf(str);
                    Log.w("FirebaseInstanceId", valueOf.length() != 0 ? "Missing callback for ".concat(valueOf) : new String("Missing callback for "));
                    return;
                }
                remove.onError(str2);
            }
        }
    }

    public static synchronized void zzc(Context context, Intent intent) {
        synchronized (zzw.class) {
            if (zzhzr == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                zzhzr = PendingIntent.getBroadcast(context, 0, intent2, 0);
            }
            intent.putExtra(SettingsJsonConstants.APP_KEY, zzhzr);
        }
    }

    public static boolean zzeu(Context context) {
        return zzev(context) != 0;
    }

    @VisibleForTesting
    private static synchronized int zzev(Context context) {
        synchronized (zzw.class) {
            if (zznur != 0) {
                return zznur;
            }
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", "com.google.android.gms") == -1) {
                Log.e("FirebaseInstanceId", "Google Play services missing or without correct permission.");
                return 0;
            }
            if (!zzq.isAtLeastO()) {
                Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                intent.setPackage("com.google.android.gms");
                List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 0);
                if (queryIntentServices != null && queryIntentServices.size() > 0) {
                    zznur = 1;
                    return 1;
                }
            }
            Intent intent2 = new Intent("com.google.iid.TOKEN_REQUEST");
            intent2.setPackage("com.google.android.gms");
            List<ResolveInfo> queryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent2, 0);
            if (queryBroadcastReceivers == null || queryBroadcastReceivers.size() <= 0) {
                Log.w("FirebaseInstanceId", "Failed to resolve IID implementation package, falling back");
                if (zzq.isAtLeastO()) {
                    zznur = 2;
                } else {
                    zznur = 1;
                }
                return zznur;
            }
            zznur = 2;
            return 2;
        }
    }

    public final Bundle zzc(Bundle bundle, KeyPair keyPair) throws IOException {
        int zzam = FirebaseInstanceId.zzam(this.zzaif, "com.google.android.gms");
        bundle.putString("gmsv", Integer.toString(zzam));
        bundle.putString("osv", Integer.toString(Build.VERSION.SDK_INT));
        bundle.putString("app_ver", Integer.toString(FirebaseInstanceId.zzes(this.zzaif)));
        bundle.putString("app_ver_name", FirebaseInstanceId.zzdk(this.zzaif));
        bundle.putString("cliv", "fiid-11720000");
        bundle.putString("appid", FirebaseInstanceId.zza(keyPair));
        String zzn = FirebaseInstanceId.zzn(keyPair.getPublic().getEncoded());
        bundle.putString("pub2", zzn);
        bundle.putString("sig", zza(keyPair, this.zzaif.getPackageName(), zzn));
        if (zzam < 12000000) {
            return zzae(bundle);
        }
        try {
            return (Bundle) Tasks.await(zzk.zzet(this.zzaif).zzi(1, bundle));
        } catch (InterruptedException | ExecutionException e) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
                sb.append("Error making request: ");
                sb.append(valueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            if (!(e.getCause() instanceof zzu) || ((zzu) e.getCause()).getErrorCode() != 4) {
                return null;
            }
            return zzae(bundle);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x020e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzc(android.os.Message r12) {
        /*
            Method dump skipped, instructions count: 581
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzw.zzc(android.os.Message):void");
    }
}
