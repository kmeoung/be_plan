package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzfdm {
    private final byte[] buffer;
    private final zzfdv zzpaq;

    private zzfdm(int i) {
        this.buffer = new byte[i];
        this.zzpaq = zzfdv.zzbb(this.buffer);
    }

    public /* synthetic */ zzfdm(int i, zzfdi zzfdiVar) {
        this(i);
    }

    public final zzfdh zzctq() {
        this.zzpaq.zzcus();
        return new zzfdo(this.buffer);
    }

    public final zzfdv zzctr() {
        return this.zzpaq;
    }
}
