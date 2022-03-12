package com.google.android.gms.internal;

/* loaded from: classes.dex */
public class zzbdv<T> {
    private static String READ_PERMISSION = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    private static final Object sLock = new Object();
    private static zzbeb zzfsu;
    private static int zzfsv;
    private String zzbfo;
    private T zzbfp;
    private T zzfsw = null;

    public zzbdv(String str, T t) {
        this.zzbfo = str;
        this.zzbfp = t;
    }

    public static zzbdv<Float> zza(String str, Float f) {
        return new zzbdz(str, f);
    }

    public static zzbdv<Integer> zza(String str, Integer num) {
        return new zzbdy(str, num);
    }

    public static zzbdv<Long> zza(String str, Long l) {
        return new zzbdx(str, l);
    }

    public static zzbdv<Boolean> zze(String str, boolean z) {
        return new zzbdw(str, Boolean.valueOf(z));
    }

    public static zzbdv<String> zzs(String str, String str2) {
        return new zzbea(str, str2);
    }
}
