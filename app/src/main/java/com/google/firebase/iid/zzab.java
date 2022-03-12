package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzv;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* loaded from: classes.dex */
public final class zzab {
    private Context zzaif;
    final SharedPreferences zzidi;

    public zzab(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    private zzab(Context context, String str) {
        this.zzaif = context;
        this.zzidi = context.getSharedPreferences(str, 0);
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("-no-backup");
        File file = new File(zzv.getNoBackupFilesDir(this.zzaif), valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !isEmpty()) {
                    Log.i("InstanceID/Store", "App restored, clearing state");
                    zzaux();
                    zzi.zza(this.zzaif, null).zzchc();
                }
            } catch (IOException e) {
                if (Log.isLoggable("InstanceID/Store", 3)) {
                    String valueOf3 = String.valueOf(e.getMessage());
                    Log.d("InstanceID/Store", valueOf3.length() != 0 ? "Error creating file in no backup dir: ".concat(valueOf3) : new String("Error creating file in no backup dir: "));
                }
            }
        }
    }

    private static String zzbm(String str, String str2) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + String.valueOf("|S|").length() + String.valueOf(str2).length());
        sb.append(str);
        sb.append("|S|");
        sb.append(str2);
        return sb.toString();
    }

    private final void zzht(String str) {
        SharedPreferences.Editor edit = this.zzidi.edit();
        for (String str2 : this.zzidi.getAll().keySet()) {
            if (str2.startsWith(str)) {
                edit.remove(str2);
            }
        }
        edit.commit();
    }

    private static String zzn(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf("|T|").length() + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append("|T|");
        sb.append(str2);
        sb.append("|");
        sb.append(str3);
        return sb.toString();
    }

    public final synchronized boolean isEmpty() {
        return this.zzidi.getAll().isEmpty();
    }

    public final synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        String zzc = zzac.zzc(str4, str5, System.currentTimeMillis());
        if (zzc != null) {
            SharedPreferences.Editor edit = this.zzidi.edit();
            edit.putString(zzn(str, str2, str3), zzc);
            edit.commit();
        }
    }

    public final synchronized void zzaux() {
        this.zzidi.edit().clear().commit();
    }

    public final synchronized void zzf(String str, String str2, String str3) {
        String zzn = zzn(str, str2, str3);
        SharedPreferences.Editor edit = this.zzidi.edit();
        edit.remove(zzn);
        edit.commit();
    }

    public final synchronized void zzhu(String str) {
        zzht(String.valueOf(str).concat("|T|"));
    }

    public final synchronized zzac zzo(String str, String str2, String str3) {
        return zzac.zzqx(this.zzidi.getString(zzn(str, str2, str3), null));
    }

    public final synchronized long zzqt(String str) {
        String string = this.zzidi.getString(zzbm(str, "cre"), null);
        if (string != null) {
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException unused) {
            }
        }
        return 0L;
    }

    public final synchronized KeyPair zzqu(String str) {
        KeyPair zzauq;
        zzauq = zza.zzauq();
        long currentTimeMillis = System.currentTimeMillis();
        SharedPreferences.Editor edit = this.zzidi.edit();
        edit.putString(zzbm(str, "|P|"), FirebaseInstanceId.zzn(zzauq.getPublic().getEncoded()));
        edit.putString(zzbm(str, "|K|"), FirebaseInstanceId.zzn(zzauq.getPrivate().getEncoded()));
        edit.putString(zzbm(str, "cre"), Long.toString(currentTimeMillis));
        edit.commit();
        return zzauq;
    }

    public final synchronized void zzqv(String str) {
        zzht(String.valueOf(str).concat("|"));
    }

    public final synchronized KeyPair zzqw(String str) {
        String string = this.zzidi.getString(zzbm(str, "|P|"), null);
        String string2 = this.zzidi.getString(zzbm(str, "|K|"), null);
        if (string == null || string2 == null) {
            return null;
        }
        try {
            byte[] decode = Base64.decode(string, 8);
            byte[] decode2 = Base64.decode(string2, 8);
            KeyFactory instance = KeyFactory.getInstance("RSA");
            return new KeyPair(instance.generatePublic(new X509EncodedKeySpec(decode)), instance.generatePrivate(new PKCS8EncodedKeySpec(decode2)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 19);
            sb.append("Invalid key stored ");
            sb.append(valueOf);
            Log.w("InstanceID/Store", sb.toString());
            zzi.zza(this.zzaif, null).zzchc();
            return null;
        }
    }
}
