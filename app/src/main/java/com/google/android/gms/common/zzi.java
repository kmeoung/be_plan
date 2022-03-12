package com.google.android.gms.common;

import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
abstract class zzi extends zzg {
    private static final WeakReference<byte[]> zzfik = new WeakReference<>(null);
    private WeakReference<byte[]> zzfij = zzfik;

    public zzi(byte[] bArr) {
        super(bArr);
    }

    @Override // com.google.android.gms.common.zzg
    final byte[] getBytes() {
        byte[] bArr;
        synchronized (this) {
            bArr = this.zzfij.get();
            if (bArr == null) {
                bArr = zzafq();
                this.zzfij = new WeakReference<>(bArr);
            }
        }
        return bArr;
    }

    protected abstract byte[] zzafq();
}
