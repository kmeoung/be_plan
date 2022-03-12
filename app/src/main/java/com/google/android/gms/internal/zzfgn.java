package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzfgn extends zzfgm {
    @Override // com.google.android.gms.internal.zzfgm
    public final int zzb(int i, byte[] bArr, int i2, int i3) {
        int zzl;
        int zzl2;
        while (i2 < i3 && bArr[i2] >= 0) {
            i2++;
        }
        if (i2 >= i3) {
            return 0;
        }
        while (i2 < i3) {
            i2++;
            byte b = bArr[i2];
            if (b < 0) {
                if (b < -32) {
                    if (i2 >= i3) {
                        return b;
                    }
                    if (b >= -62) {
                        i2++;
                        if (bArr[i2] > -65) {
                        }
                    }
                    return -1;
                } else if (b < -16) {
                    if (i2 >= i3 - 1) {
                        zzl = zzfgl.zzl(bArr, i2, i3);
                        return zzl;
                    }
                    int i4 = i2 + 1;
                    byte b2 = bArr[i2];
                    if (b2 <= -65 && ((b != -32 || b2 >= -96) && (b != -19 || b2 < -96))) {
                        i2 = i4 + 1;
                        if (bArr[i4] > -65) {
                        }
                    }
                    return -1;
                } else if (i2 >= i3 - 2) {
                    zzl2 = zzfgl.zzl(bArr, i2, i3);
                    return zzl2;
                } else {
                    int i5 = i2 + 1;
                    byte b3 = bArr[i2];
                    if (b3 <= -65 && (((b << 28) + (b3 + 112)) >> 30) == 0) {
                        int i6 = i5 + 1;
                        if (bArr[i5] <= -65) {
                            i2 = i6 + 1;
                            if (bArr[i6] > -65) {
                            }
                        }
                    }
                    return -1;
                }
            }
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
        return r10 + r0;
     */
    @Override // com.google.android.gms.internal.zzfgm
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int zzb(java.lang.CharSequence r8, byte[] r9, int r10, int r11) {
        /*
            Method dump skipped, instructions count: 257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfgn.zzb(java.lang.CharSequence, byte[], int, int):int");
    }
}
