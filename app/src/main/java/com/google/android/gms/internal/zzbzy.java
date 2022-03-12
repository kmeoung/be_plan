package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzbzy {
    private static zzbzy zzhga;
    private final zzbzt zzhgb = new zzbzt();
    private final zzbzu zzhgc = new zzbzu();

    static {
        zzbzy zzbzyVar = new zzbzy();
        synchronized (zzbzy.class) {
            zzhga = zzbzyVar;
        }
    }

    private zzbzy() {
    }

    private static zzbzy zzaqo() {
        zzbzy zzbzyVar;
        synchronized (zzbzy.class) {
            zzbzyVar = zzhga;
        }
        return zzbzyVar;
    }

    public static zzbzt zzaqp() {
        return zzaqo().zzhgb;
    }

    public static zzbzu zzaqq() {
        return zzaqo().zzhgc;
    }
}
