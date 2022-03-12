package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzi {
    private static Map<String, zzi> zzick = new ArrayMap();
    static String zzicq;
    private static zzab zznua;
    private static zzw zznub;
    private Context mContext;
    private KeyPair zzicn;
    String zzico;

    private zzi(Context context, String str, Bundle bundle) {
        this.zzico = "";
        this.mContext = context.getApplicationContext();
        this.zzico = str;
    }

    public static synchronized zzi zza(Context context, @Nullable Bundle bundle) {
        zzi zza;
        synchronized (zzi.class) {
            zza = zza(context, (String) null, (Bundle) null);
        }
        return zza;
    }

    public static synchronized zzi zza(Context context, @Nullable String str, @Nullable Bundle bundle) {
        zzi zziVar;
        synchronized (zzi.class) {
            if (str == null) {
                str = "";
            }
            Context applicationContext = context.getApplicationContext();
            if (zznua == null) {
                zznua = new zzab(applicationContext);
                zznub = new zzw(applicationContext);
            }
            zzicq = Integer.toString(FirebaseInstanceId.zzes(applicationContext));
            zziVar = zzick.get(str);
            if (zziVar == null) {
                zziVar = new zzi(applicationContext, str, null);
                zzick.put(str, zziVar);
            }
        }
        return zziVar;
    }

    public static zzab zzcha() {
        return zznua;
    }

    public final long getCreationTime() {
        return zznua.zzqt(this.zzico);
    }

    public final String getToken(String str, String str2, Bundle bundle) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        boolean z = true;
        if (bundle.getString("ttl") != null || "jwt".equals(bundle.getString("type"))) {
            z = false;
        } else {
            zzac zzo = zznua.zzo(this.zzico, str, str2);
            if (zzo != null && !zzo.zzqy(zzicq)) {
                return zzo.zzlan;
            }
        }
        String zzb = zzb(str, str2, bundle);
        if (zzb != null && z) {
            zznua.zza(this.zzico, str, str2, zzb, zzicq);
        }
        return zzb;
    }

    public final void zza(String str, String str2, Bundle bundle) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        zznua.zzf(this.zzico, str, str2);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("delete", "1");
        zzb(str, str2, bundle);
    }

    public final KeyPair zzaus() {
        if (this.zzicn == null) {
            this.zzicn = zznua.zzqw(this.zzico);
        }
        if (this.zzicn == null) {
            this.zzicn = zznua.zzqu(this.zzico);
        }
        return this.zzicn;
    }

    public final void zzaut() {
        zznua.zzqv(this.zzico);
        this.zzicn = null;
    }

    public final String zzb(String str, String str2, Bundle bundle) throws IOException {
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("sender", str);
        if (!"".equals(this.zzico)) {
            str = this.zzico;
        }
        bundle.putString("subtype", str);
        Bundle zzc = zznub.zzc(bundle, zzaus());
        if (zzc == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String string = zzc.getString("registration_id");
        if (string != null) {
            return string;
        }
        String string2 = zzc.getString("unregistered");
        if (string2 != null) {
            return string2;
        }
        String string3 = zzc.getString("error");
        if (string3 != null) {
            throw new IOException(string3);
        }
        String valueOf = String.valueOf(zzc);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20);
        sb.append("Unexpected response ");
        sb.append(valueOf);
        Log.w("FirebaseInstanceId", sb.toString(), new Throwable());
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    public final void zzchb() {
        zzaut();
        FirebaseInstanceId.getInstance().startSync();
    }

    public final void zzchc() {
        if (!zznua.isEmpty()) {
            zzaut();
            zznua.zzaux();
            FirebaseInstanceId.getInstance().startSync();
        }
    }

    public final void zzchd() {
        zznua.zzhu(this.zzico);
        FirebaseInstanceId.getInstance().startSync();
    }
}
