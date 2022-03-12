package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzfgp extends zzfgm {
    private static int zza(byte[] bArr, int i, long j, int i2) {
        int zzlt;
        int zzaj;
        int zzi;
        switch (i2) {
            case 0:
                zzlt = zzfgl.zzlt(i);
                return zzlt;
            case 1:
                zzaj = zzfgl.zzaj(i, zzfgj.zzb(bArr, j));
                return zzaj;
            case 2:
                zzi = zzfgl.zzi(i, zzfgj.zzb(bArr, j), zzfgj.zzb(bArr, j + 1));
                return zzi;
            default:
                throw new AssertionError();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x00a9, code lost:
        return -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int zzb(byte[] r9, long r10, int r12) {
        /*
            r0 = 0
            r1 = 1
            r3 = 16
            if (r12 >= r3) goto L_0x0009
            r3 = 0
            goto L_0x001b
        L_0x0009:
            r4 = r10
            r3 = 0
        L_0x000b:
            if (r3 >= r12) goto L_0x001a
            long r6 = r4 + r1
            byte r4 = com.google.android.gms.internal.zzfgj.zzb(r9, r4)
            if (r4 >= 0) goto L_0x0016
            goto L_0x001b
        L_0x0016:
            int r3 = r3 + 1
            r4 = r6
            goto L_0x000b
        L_0x001a:
            r3 = r12
        L_0x001b:
            int r12 = r12 - r3
            long r3 = (long) r3
            long r5 = r10 + r3
        L_0x001f:
            r10 = 0
        L_0x0020:
            if (r12 <= 0) goto L_0x0031
            long r10 = r5 + r1
            byte r3 = com.google.android.gms.internal.zzfgj.zzb(r9, r5)
            if (r3 < 0) goto L_0x002f
            int r12 = r12 + (-1)
            r5 = r10
            r10 = r3
            goto L_0x0020
        L_0x002f:
            r5 = r10
            r10 = r3
        L_0x0031:
            if (r12 != 0) goto L_0x0034
            return r0
        L_0x0034:
            int r12 = r12 + (-1)
            r11 = -32
            r3 = -65
            r4 = -1
            if (r10 >= r11) goto L_0x0052
            if (r12 != 0) goto L_0x0040
            return r10
        L_0x0040:
            int r12 = r12 + (-1)
            r11 = -62
            if (r10 < r11) goto L_0x0051
            long r10 = r5 + r1
            byte r5 = com.google.android.gms.internal.zzfgj.zzb(r9, r5)
            if (r5 <= r3) goto L_0x004f
            return r4
        L_0x004f:
            r5 = r10
            goto L_0x001f
        L_0x0051:
            return r4
        L_0x0052:
            r7 = -16
            if (r10 >= r7) goto L_0x007e
            r7 = 2
            if (r12 >= r7) goto L_0x005e
            int r9 = zza(r9, r10, r5, r12)
            return r9
        L_0x005e:
            int r12 = r12 + (-2)
            long r7 = r5 + r1
            byte r5 = com.google.android.gms.internal.zzfgj.zzb(r9, r5)
            if (r5 > r3) goto L_0x007d
            r6 = -96
            if (r10 != r11) goto L_0x006e
            if (r5 < r6) goto L_0x007d
        L_0x006e:
            r11 = -19
            if (r10 != r11) goto L_0x0074
            if (r5 >= r6) goto L_0x007d
        L_0x0074:
            r10 = 0
            long r5 = r7 + r1
            byte r10 = com.google.android.gms.internal.zzfgj.zzb(r9, r7)
            if (r10 <= r3) goto L_0x001f
        L_0x007d:
            return r4
        L_0x007e:
            r11 = 3
            if (r12 >= r11) goto L_0x0086
            int r9 = zza(r9, r10, r5, r12)
            return r9
        L_0x0086:
            int r12 = r12 + (-3)
            long r7 = r5 + r1
            byte r11 = com.google.android.gms.internal.zzfgj.zzb(r9, r5)
            if (r11 > r3) goto L_0x00a9
            int r10 = r10 << 28
            int r11 = r11 + 112
            int r10 = r10 + r11
            int r10 = r10 >> 30
            if (r10 != 0) goto L_0x00a9
            long r10 = r7 + r1
            byte r5 = com.google.android.gms.internal.zzfgj.zzb(r9, r7)
            if (r5 > r3) goto L_0x00a9
            long r5 = r10 + r1
            byte r10 = com.google.android.gms.internal.zzfgj.zzb(r9, r10)
            if (r10 <= r3) goto L_0x001f
        L_0x00a9:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfgp.zzb(byte[], long, int):int");
    }

    @Override // com.google.android.gms.internal.zzfgm
    public final int zzb(int i, byte[] bArr, int i2, int i3) {
        if ((i2 | i3 | (bArr.length - i3)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        long j = i2;
        return zzb(bArr, j, (int) (i3 - j));
    }

    @Override // com.google.android.gms.internal.zzfgm
    public final int zzb(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int i3;
        char c;
        char charAt;
        long j = i;
        long j2 = j + i2;
        int length = charSequence.length();
        if (length > i2 || bArr.length - i2 < i) {
            char charAt2 = charSequence.charAt(length - 1);
            StringBuilder sb = new StringBuilder(37);
            sb.append("Failed writing ");
            sb.append(charAt2);
            sb.append(" at index ");
            sb.append(i + i2);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        int i4 = 0;
        while (i4 < length && (charAt = charSequence.charAt(i4)) < 128) {
            j++;
            zzfgj.zza(bArr, j, (byte) charAt);
            i4++;
        }
        if (i4 == length) {
            return (int) j;
        }
        while (i4 < length) {
            char charAt3 = charSequence.charAt(i4);
            if (charAt3 >= 128 || j >= j2) {
                if (charAt3 < 2048 && j <= j2 - 2) {
                    long j3 = j + 1;
                    zzfgj.zza(bArr, j, (byte) ((charAt3 >>> 6) | 960));
                    j = j3 + 1;
                    zzfgj.zza(bArr, j3, (byte) ((charAt3 & '?') | 128));
                } else if ((charAt3 < 55296 || 57343 < charAt3) && j <= j2 - 3) {
                    long j4 = j + 1;
                    zzfgj.zza(bArr, j, (byte) ((charAt3 >>> '\f') | 480));
                    j = j4 + 1;
                    zzfgj.zza(bArr, j4, (byte) (((charAt3 >>> 6) & 63) | 128));
                    j++;
                    c = (charAt3 & '?') | 128;
                } else if (j <= j2 - 4) {
                    int i5 = i4 + 1;
                    if (i5 != length) {
                        char charAt4 = charSequence.charAt(i5);
                        if (Character.isSurrogatePair(charAt3, charAt4)) {
                            int codePoint = Character.toCodePoint(charAt3, charAt4);
                            long j5 = j + 1;
                            zzfgj.zza(bArr, j, (byte) ((codePoint >>> 18) | 240));
                            long j6 = j5 + 1;
                            zzfgj.zza(bArr, j5, (byte) (((codePoint >>> 12) & 63) | 128));
                            long j7 = j6 + 1;
                            zzfgj.zza(bArr, j6, (byte) (((codePoint >>> 6) & 63) | 128));
                            j = j7 + 1;
                            zzfgj.zza(bArr, j7, (byte) ((codePoint & 63) | 128));
                            i4 = i5;
                        }
                    } else {
                        i5 = i4;
                    }
                    throw new zzfgo(i5 - 1, length);
                } else if (55296 > charAt3 || charAt3 > 57343 || ((i3 = i4 + 1) != length && Character.isSurrogatePair(charAt3, charSequence.charAt(i3)))) {
                    StringBuilder sb2 = new StringBuilder(46);
                    sb2.append("Failed writing ");
                    sb2.append(charAt3);
                    sb2.append(" at index ");
                    sb2.append(j);
                    throw new ArrayIndexOutOfBoundsException(sb2.toString());
                } else {
                    throw new zzfgo(i4, length);
                }
                i4++;
            } else {
                j++;
                c = charAt3;
            }
            zzfgj.zza(bArr, j, c == 1 ? (byte) 1 : (byte) 0);
            i4++;
        }
        return (int) j;
    }
}
