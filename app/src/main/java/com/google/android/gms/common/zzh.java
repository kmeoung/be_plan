package com.google.android.gms.common;

import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzh extends zzg {
    private final byte[] zzfii;

    public zzh(byte[] bArr) {
        super(Arrays.copyOfRange(bArr, 0, 25));
        this.zzfii = bArr;
    }

    @Override // com.google.android.gms.common.zzg
    final byte[] getBytes() {
        return this.zzfii;
    }
}
