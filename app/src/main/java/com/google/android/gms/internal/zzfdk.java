package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzfdk extends zzfdo {
    private final int zzpao;
    private final int zzpap;

    public zzfdk(byte[] bArr, int i, int i2) {
        super(bArr);
        zzh(i, i + i2, bArr.length);
        this.zzpao = i;
        this.zzpap = i2;
    }

    @Override // com.google.android.gms.internal.zzfdo, com.google.android.gms.internal.zzfdh
    public final int size() {
        return this.zzpap;
    }

    @Override // com.google.android.gms.internal.zzfdo, com.google.android.gms.internal.zzfdh
    public final void zzb(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzjkl, zzctp() + i, bArr, i2, i3);
    }

    @Override // com.google.android.gms.internal.zzfdo
    public final int zzctp() {
        return this.zzpao;
    }

    @Override // com.google.android.gms.internal.zzfdo, com.google.android.gms.internal.zzfdh
    public final byte zzkd(int i) {
        zzy(i, size());
        return this.zzjkl[this.zzpao + i];
    }
}
