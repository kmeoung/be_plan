package com.google.android.gms.internal;

import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

/* loaded from: classes.dex */
public final class zzal {
    public static String zza(Map<String, String> map) {
        String str = map.get("Content-Type");
        if (str != null) {
            String[] split = str.split(";");
            for (int i = 1; i < split.length; i++) {
                String[] split2 = split[i].trim().split("=");
                if (split2.length == 2 && split2[0].equals(HttpRequest.PARAM_CHARSET)) {
                    return split2[1];
                }
            }
        }
        return "ISO-8859-1";
    }

    public static zzc zzb(zzn zznVar) {
        boolean z;
        long j;
        boolean z2;
        long j2;
        long j3;
        long j4;
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = zznVar.zzy;
        String str = map.get("Date");
        long zzf = str != null ? zzf(str) : 0L;
        String str2 = map.get("Cache-Control");
        if (str2 != null) {
            j2 = 0;
            z2 = false;
            j = 0;
            for (String str3 : str2.split(",")) {
                String trim = str3.trim();
                if (trim.equals(HeaderConstants.CACHE_CONTROL_NO_CACHE) || trim.equals(HeaderConstants.CACHE_CONTROL_NO_STORE)) {
                    return null;
                }
                if (trim.startsWith("max-age=")) {
                    try {
                        j2 = Long.parseLong(trim.substring(8));
                    } catch (Exception unused) {
                    }
                } else if (trim.startsWith("stale-while-revalidate=")) {
                    j = Long.parseLong(trim.substring(23));
                } else if (trim.equals(HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE) || trim.equals(HeaderConstants.CACHE_CONTROL_PROXY_REVALIDATE)) {
                    z2 = true;
                }
            }
            z = true;
        } else {
            j2 = 0;
            z2 = false;
            j = 0;
            z = false;
        }
        String str4 = map.get("Expires");
        long zzf2 = str4 != null ? zzf(str4) : 0L;
        String str5 = map.get("Last-Modified");
        long zzf3 = str5 != null ? zzf(str5) : 0L;
        String str6 = map.get("ETag");
        if (z) {
            long j5 = currentTimeMillis + (j2 * 1000);
            j4 = z2 ? j5 : j5 + (j * 1000);
            j3 = j5;
        } else if (zzf <= 0 || zzf2 < zzf) {
            j4 = 0;
            j3 = 0;
        } else {
            j3 = currentTimeMillis + (zzf2 - zzf);
            j4 = j3;
        }
        zzc zzcVar = new zzc();
        zzcVar.data = zznVar.data;
        zzcVar.zza = str6;
        zzcVar.zze = j3;
        zzcVar.zzd = j4;
        zzcVar.zzb = zzf;
        zzcVar.zzc = zzf3;
        zzcVar.zzf = map;
        return zzcVar;
    }

    private static long zzf(String str) {
        try {
            return DateUtils.parseDate(str).getTime();
        } catch (DateParseException unused) {
            return 0L;
        }
    }
}
