package com.google.android.gms.internal;

/* loaded from: classes.dex */
public enum zzfgr {
    DOUBLE(zzfgw.DOUBLE, 1),
    FLOAT(zzfgw.FLOAT, 5),
    INT64(zzfgw.LONG, 0),
    UINT64(zzfgw.LONG, 0),
    INT32(zzfgw.INT, 0),
    FIXED64(zzfgw.LONG, 1),
    FIXED32(zzfgw.INT, 5),
    BOOL(zzfgw.BOOLEAN, 0),
    STRING(zzfgw.STRING, 2) { // from class: com.google.android.gms.internal.zzfgs
    },
    GROUP(zzfgw.MESSAGE, 3) { // from class: com.google.android.gms.internal.zzfgt
    },
    MESSAGE(zzfgw.MESSAGE, 2) { // from class: com.google.android.gms.internal.zzfgu
    },
    BYTES(zzfgw.BYTE_STRING, 2) { // from class: com.google.android.gms.internal.zzfgv
    },
    UINT32(zzfgw.INT, 0),
    ENUM(zzfgw.ENUM, 0),
    SFIXED32(zzfgw.INT, 5),
    SFIXED64(zzfgw.LONG, 1),
    SINT32(zzfgw.INT, 0),
    SINT64(zzfgw.LONG, 0);
    
    private final zzfgw zzpgd;
    private final int zzpge;

    zzfgr(zzfgw zzfgwVar, int i) {
        this.zzpgd = zzfgwVar;
        this.zzpge = i;
    }

    /* synthetic */ zzfgr(zzfgw zzfgwVar, int i, zzfgq zzfgqVar) {
        this(zzfgwVar, i);
    }

    public final zzfgw zzcxc() {
        return this.zzpgd;
    }

    public final int zzcxd() {
        return this.zzpge;
    }
}
