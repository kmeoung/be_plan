package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class zzdld {
    private static HashMap<String, String> zzljv;
    private static Object zzlka;
    private static boolean zzlkb;
    private static Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static Uri zzljr = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    private static Pattern zzljs = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    private static Pattern zzljt = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzlju = new AtomicBoolean();
    private static HashMap<String, Boolean> zzljw = new HashMap<>();
    private static HashMap<String, Integer> zzljx = new HashMap<>();
    private static HashMap<String, Long> zzljy = new HashMap<>();
    private static HashMap<String, Float> zzljz = new HashMap<>();
    private static String[] zzlkc = new String[0];

    public static long getLong(ContentResolver contentResolver, String str, long j) {
        Object zzb = zzb(contentResolver);
        long j2 = 0;
        Long l = (Long) zza((HashMap<String, long>) zzljy, str, 0L);
        if (l != null) {
            return l.longValue();
        }
        String zza = zza(contentResolver, str, (String) null);
        if (zza != null) {
            try {
                long parseLong = Long.parseLong(zza);
                l = Long.valueOf(parseLong);
                j2 = parseLong;
            } catch (NumberFormatException unused) {
            }
        }
        zza(zzb, zzljy, str, l);
        return j2;
    }

    private static <T> T zza(HashMap<String, T> hashMap, String str, T t) {
        synchronized (zzdld.class) {
            if (!hashMap.containsKey(str)) {
                return null;
            }
            T t2 = hashMap.get(str);
            if (t2 == null) {
                t2 = t;
            }
            return t2;
        }
    }

    public static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzdld.class) {
            zza(contentResolver);
            Object obj = zzlka;
            if (zzljv.containsKey(str)) {
                String str3 = zzljv.get(str);
                if (str3 == null) {
                    str3 = null;
                }
                return str3;
            }
            for (String str4 : zzlkc) {
                if (str.startsWith(str4)) {
                    if (!zzlkb || zzljv.isEmpty()) {
                        zzljv.putAll(zza(contentResolver, zzlkc));
                        zzlkb = true;
                        if (zzljv.containsKey(str)) {
                            String str5 = zzljv.get(str);
                            if (str5 == null) {
                                str5 = null;
                            }
                            return str5;
                        }
                    }
                    return null;
                }
            }
            Cursor query = contentResolver.query(CONTENT_URI, null, null, new String[]{str}, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(1);
                        if (string != null && string.equals(null)) {
                            string = null;
                        }
                        zza(obj, str, string);
                        if (string == null) {
                            string = null;
                        }
                        return string;
                    }
                } finally {
                    if (query != null) {
                        query.close();
                    }
                }
            }
            zza(obj, str, (String) null);
            if (query != null) {
                query.close();
            }
            return null;
        }
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(zzljr, null, null, strArr, null);
        TreeMap treeMap = new TreeMap();
        if (query == null) {
            return treeMap;
        }
        while (query.moveToNext()) {
            try {
                treeMap.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzljv == null) {
            zzlju.set(false);
            zzljv = new HashMap<>();
            zzlka = new Object();
            zzlkb = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new zzdle(null));
        } else if (zzlju.getAndSet(false)) {
            zzljv.clear();
            zzljw.clear();
            zzljx.clear();
            zzljy.clear();
            zzljz.clear();
            zzlka = new Object();
            zzlkb = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzdld.class) {
            if (obj == zzlka) {
                zzljv.put(str, str2);
            }
        }
    }

    private static <T> void zza(Object obj, HashMap<String, T> hashMap, String str, T t) {
        synchronized (zzdld.class) {
            if (obj == zzlka) {
                hashMap.put(str, t);
                zzljv.remove(str);
            }
        }
    }

    public static boolean zza(ContentResolver contentResolver, String str, boolean z) {
        Object zzb = zzb(contentResolver);
        boolean z2 = false;
        Boolean bool = (Boolean) zza((HashMap<String, boolean>) zzljw, str, false);
        if (bool != null) {
            return bool.booleanValue();
        }
        String zza = zza(contentResolver, str, (String) null);
        if (zza != null && !zza.equals("")) {
            if (zzljs.matcher(zza).matches()) {
                bool = true;
                z2 = true;
            } else if (zzljt.matcher(zza).matches()) {
                bool = false;
            } else {
                Log.w("Gservices", "attempt to read gservices key " + str + " (value \"" + zza + "\") as boolean");
            }
        }
        zza(zzb, zzljw, str, bool);
        return z2;
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (zzdld.class) {
            zza(contentResolver);
            obj = zzlka;
        }
        return obj;
    }
}
