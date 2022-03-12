package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzffe<K, V> {
    private final V value;
    private final K zzmhr;
    private final zzffg<K, V> zzpcz;

    private zzffe(zzfgr zzfgrVar, K k, zzfgr zzfgrVar2, V v) {
        this.zzpcz = new zzffg<>(zzfgrVar, k, zzfgrVar2, v);
        this.zzmhr = k;
        this.value = v;
    }

    private static <K, V> int zza(zzffg<K, V> zzffgVar, K k, V v) {
        return zzfeb.zza(zzffgVar.zzpda, 1, k) + zzfeb.zza(zzffgVar.zzpdc, 2, v);
    }

    public static <K, V> zzffe<K, V> zza(zzfgr zzfgrVar, K k, zzfgr zzfgrVar2, V v) {
        return new zzffe<>(zzfgrVar, k, zzfgrVar2, v);
    }

    private static <T> T zza(zzfdq zzfdqVar, zzfea zzfeaVar, zzfgr zzfgrVar, T t) throws IOException {
        switch (zzfgrVar) {
            case MESSAGE:
                zzffj zzcvg = ((zzffi) t).zzcvg();
                zzfdqVar.zza(zzcvg, zzfeaVar);
                return (T) zzcvg.zzcvl();
            case ENUM:
                return (T) Integer.valueOf(zzfdqVar.zzcuc());
            case GROUP:
                throw new RuntimeException("Groups are not allowed in maps.");
            default:
                return (T) zzfeb.zza(zzfdqVar, zzfgrVar, true);
        }
    }

    public final void zza(zzfdv zzfdvVar, int i, K k, V v) throws IOException {
        zzfdvVar.zzz(i, 2);
        zzfdvVar.zzkt(zza(this.zzpcz, k, v));
        zzffg<K, V> zzffgVar = this.zzpcz;
        zzfeb.zza(zzfdvVar, zzffgVar.zzpda, 1, k);
        zzfeb.zza(zzfdvVar, zzffgVar.zzpdc, 2, v);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zza(zzffh<K, V> zzffhVar, zzfdq zzfdqVar, zzfea zzfeaVar) throws IOException {
        int zzki = zzfdqVar.zzki(zzfdqVar.zzcuh());
        Object obj = this.zzpcz.zzpdb;
        Object obj2 = this.zzpcz.zzjul;
        while (true) {
            int zzcts = zzfdqVar.zzcts();
            if (zzcts == 0) {
                break;
            } else if (zzcts == (this.zzpcz.zzpda.zzcxd() | 8)) {
                obj = zza(zzfdqVar, zzfeaVar, this.zzpcz.zzpda, obj);
            } else if (zzcts == (this.zzpcz.zzpdc.zzcxd() | 16)) {
                obj2 = zza(zzfdqVar, zzfeaVar, this.zzpcz.zzpdc, obj2);
            } else if (!zzfdqVar.zzkg(zzcts)) {
                break;
            }
        }
        zzfdqVar.zzkf(0);
        zzfdqVar.zzkj(zzki);
        zzffhVar.put(obj, obj2);
    }

    public final int zzb(int i, K k, V v) {
        return zzfdv.zzkw(i) + zzfdv.zzld(zza(this.zzpcz, k, v));
    }
}
