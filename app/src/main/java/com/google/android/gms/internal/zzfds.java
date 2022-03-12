package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class zzfds extends zzfdq {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzpaw;
    private int zzpax;
    private int zzpay;
    private int zzpaz;
    private int zzpba;

    private zzfds(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzpba = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i2 + i;
        this.pos = i;
        this.zzpay = this.pos;
        this.zzpaw = z;
    }

    private final long zzcum() throws IOException {
        long j;
        long j2;
        long j3;
        int i;
        int i2 = this.pos;
        if (this.limit != i2) {
            byte[] bArr = this.buffer;
            int i3 = i2 + 1;
            byte b = bArr[i2];
            if (b >= 0) {
                this.pos = i3;
                return b;
            } else if (this.limit - i3 >= 9) {
                int i4 = i3 + 1;
                int i5 = b ^ (bArr[i3] << 7);
                if (i5 < 0) {
                    i = i5 ^ (-128);
                } else {
                    int i6 = i4 + 1;
                    int i7 = i5 ^ (bArr[i4] << 14);
                    if (i7 >= 0) {
                        j = i7 ^ 16256;
                        i4 = i6;
                        this.pos = i4;
                        return j;
                    }
                    i4 = i6 + 1;
                    int i8 = i7 ^ (bArr[i6] << 21);
                    if (i8 < 0) {
                        i = i8 ^ (-2080896);
                    } else {
                        long j4 = i8;
                        i4++;
                        long j5 = j4 ^ (bArr[i4] << 28);
                        if (j5 >= 0) {
                            j3 = 266354560;
                        } else {
                            i4++;
                            long j6 = j5 ^ (bArr[i4] << 35);
                            if (j6 < 0) {
                                j2 = -34093383808L;
                            } else {
                                i4++;
                                j5 = j6 ^ (bArr[i4] << 42);
                                if (j5 >= 0) {
                                    j3 = 4363953127296L;
                                } else {
                                    i4++;
                                    j6 = j5 ^ (bArr[i4] << 49);
                                    if (j6 < 0) {
                                        j2 = -558586000294016L;
                                    } else {
                                        i4++;
                                        long j7 = (j6 ^ (bArr[i4] << 56)) ^ 71499008037633920L;
                                        if (j7 < 0) {
                                            i4++;
                                            if (bArr[i4] >= 0) {
                                            }
                                        }
                                        j = j7;
                                        this.pos = i4;
                                        return j;
                                    }
                                }
                            }
                            j = j6 ^ j2;
                            this.pos = i4;
                            return j;
                        }
                        j = j5 ^ j3;
                        this.pos = i4;
                        return j;
                    }
                }
                j = i;
                this.pos = i4;
                return j;
            }
        }
        return zzcui();
    }

    private final int zzcun() throws IOException {
        int i = this.pos;
        if (this.limit - i < 4) {
            throw zzfew.zzcvr();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private final long zzcuo() throws IOException {
        int i = this.pos;
        if (this.limit - i < 8) {
            throw zzfew.zzcvr();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48) | ((bArr[i + 7] & 255) << 56);
    }

    private final void zzcup() {
        this.limit += this.zzpax;
        int i = this.limit - this.zzpay;
        if (i > this.zzpba) {
            this.zzpax = i - this.zzpba;
            this.limit -= this.zzpax;
            return;
        }
        this.zzpax = 0;
    }

    private final byte zzcuq() throws IOException {
        if (this.pos == this.limit) {
            throw zzfew.zzcvr();
        }
        byte[] bArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i];
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzcuo());
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzcun());
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final String readString() throws IOException {
        int zzcuh = zzcuh();
        if (zzcuh > 0 && zzcuh <= this.limit - this.pos) {
            String str = new String(this.buffer, this.pos, zzcuh, zzfer.UTF_8);
            this.pos += zzcuh;
            return str;
        } else if (zzcuh == 0) {
            return "";
        } else {
            if (zzcuh < 0) {
                throw zzfew.zzcvs();
            }
            throw zzfew.zzcvr();
        }
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final <T extends zzfee<T, ?>> T zza(T t, zzfea zzfeaVar) throws IOException {
        int zzcuh = zzcuh();
        if (this.zzpar >= this.zzpas) {
            throw zzfew.zzcvx();
        }
        int zzki = zzki(zzcuh);
        this.zzpar++;
        T t2 = (T) zzfee.zza(t, this, zzfeaVar);
        zzkf(0);
        this.zzpar--;
        zzkj(zzki);
        return t2;
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final void zza(zzffj zzffjVar, zzfea zzfeaVar) throws IOException {
        int zzcuh = zzcuh();
        if (this.zzpar >= this.zzpas) {
            throw zzfew.zzcvx();
        }
        int zzki = zzki(zzcuh);
        this.zzpar++;
        zzffjVar.zzb(this, zzfeaVar);
        zzkf(0);
        this.zzpar--;
        zzkj(zzki);
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzcts() throws IOException {
        if (zzcuk()) {
            this.zzpaz = 0;
            return 0;
        }
        this.zzpaz = zzcuh();
        if ((this.zzpaz >>> 3) != 0) {
            return this.zzpaz;
        }
        throw zzfew.zzcvu();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final long zzctt() throws IOException {
        return zzcum();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final long zzctu() throws IOException {
        return zzcum();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzctv() throws IOException {
        return zzcuh();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final long zzctw() throws IOException {
        return zzcuo();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzctx() throws IOException {
        return zzcun();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final boolean zzcty() throws IOException {
        return zzcum() != 0;
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final String zzctz() throws IOException {
        int zzcuh = zzcuh();
        if (zzcuh <= 0 || zzcuh > this.limit - this.pos) {
            if (zzcuh == 0) {
                return "";
            }
            if (zzcuh <= 0) {
                throw zzfew.zzcvs();
            }
            throw zzfew.zzcvr();
        } else if (!zzfgl.zzk(this.buffer, this.pos, this.pos + zzcuh)) {
            throw zzfew.zzcvz();
        } else {
            int i = this.pos;
            this.pos += zzcuh;
            return new String(this.buffer, i, zzcuh, zzfer.UTF_8);
        }
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final zzfdh zzcua() throws IOException {
        byte[] bArr;
        int zzcuh = zzcuh();
        if (zzcuh > 0 && zzcuh <= this.limit - this.pos) {
            zzfdh zze = zzfdh.zze(this.buffer, this.pos, zzcuh);
            this.pos += zzcuh;
            return zze;
        } else if (zzcuh == 0) {
            return zzfdh.zzpal;
        } else {
            if (zzcuh > 0 && zzcuh <= this.limit - this.pos) {
                int i = this.pos;
                this.pos += zzcuh;
                bArr = Arrays.copyOfRange(this.buffer, i, this.pos);
            } else if (zzcuh > 0) {
                throw zzfew.zzcvr();
            } else if (zzcuh == 0) {
                bArr = zzfer.EMPTY_BYTE_ARRAY;
            } else {
                throw zzfew.zzcvs();
            }
            return zzfdh.zzaz(bArr);
        }
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzcub() throws IOException {
        return zzcuh();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzcuc() throws IOException {
        return zzcuh();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzcud() throws IOException {
        return zzcun();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final long zzcue() throws IOException {
        return zzcuo();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzcuf() throws IOException {
        return zzkl(zzcuh());
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final long zzcug() throws IOException {
        return zzcr(zzcum());
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0068, code lost:
        if (r1[r2] >= 0) goto L_0x006a;
     */
    @Override // com.google.android.gms.internal.zzfdq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int zzcuh() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.pos
            int r1 = r5.limit
            if (r1 == r0) goto L_0x006d
            byte[] r1 = r5.buffer
            int r2 = r0 + 1
            byte r0 = r1[r0]
            if (r0 < 0) goto L_0x0011
            r5.pos = r2
            return r0
        L_0x0011:
            int r3 = r5.limit
            int r3 = r3 - r2
            r4 = 9
            if (r3 < r4) goto L_0x006d
            int r3 = r2 + 1
            byte r2 = r1[r2]
            int r2 = r2 << 7
            r0 = r0 ^ r2
            if (r0 >= 0) goto L_0x0024
            r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
            goto L_0x006a
        L_0x0024:
            int r2 = r3 + 1
            byte r3 = r1[r3]
            int r3 = r3 << 14
            r0 = r0 ^ r3
            if (r0 < 0) goto L_0x0031
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
        L_0x002f:
            r3 = r2
            goto L_0x006a
        L_0x0031:
            int r3 = r2 + 1
            byte r2 = r1[r2]
            int r2 = r2 << 21
            r0 = r0 ^ r2
            if (r0 >= 0) goto L_0x003f
            r1 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r1
            goto L_0x006a
        L_0x003f:
            int r2 = r3 + 1
            byte r3 = r1[r3]
            int r4 = r3 << 28
            r0 = r0 ^ r4
            r4 = 266354560(0xfe03f80, float:2.2112565E-29)
            r0 = r0 ^ r4
            if (r3 >= 0) goto L_0x002f
            int r3 = r2 + 1
            byte r2 = r1[r2]
            if (r2 >= 0) goto L_0x006a
            int r2 = r3 + 1
            byte r3 = r1[r3]
            if (r3 >= 0) goto L_0x002f
            int r3 = r2 + 1
            byte r2 = r1[r2]
            if (r2 >= 0) goto L_0x006a
            int r2 = r3 + 1
            byte r3 = r1[r3]
            if (r3 >= 0) goto L_0x002f
            int r3 = r2 + 1
            byte r1 = r1[r2]
            if (r1 < 0) goto L_0x006d
        L_0x006a:
            r5.pos = r3
            return r0
        L_0x006d:
            long r0 = r5.zzcui()
            int r0 = (int) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfds.zzcuh():int");
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final long zzcui() throws IOException {
        long j = 0;
        int i = 0;
        while (i < 64) {
            byte zzcuq = zzcuq();
            long j2 = j | ((zzcuq & Byte.MAX_VALUE) << i);
            if ((zzcuq & 128) == 0) {
                return j2;
            }
            i += 7;
            j = j2;
        }
        throw zzfew.zzcvt();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzcuj() {
        if (this.zzpba == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzpba - zzcul();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final boolean zzcuk() throws IOException {
        return this.pos == this.limit;
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzcul() {
        return this.pos - this.zzpay;
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final void zzkf(int i) throws zzfew {
        if (this.zzpaz != i) {
            throw zzfew.zzcvv();
        }
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final boolean zzkg(int i) throws IOException {
        int zzcts;
        int i2 = 0;
        switch (i & 7) {
            case 0:
                if (this.limit - this.pos >= 10) {
                    while (i2 < 10) {
                        byte[] bArr = this.buffer;
                        int i3 = this.pos;
                        this.pos = i3 + 1;
                        if (bArr[i3] < 0) {
                            i2++;
                        }
                    }
                    throw zzfew.zzcvt();
                }
                while (i2 < 10) {
                    if (zzcuq() < 0) {
                        i2++;
                    }
                }
                throw zzfew.zzcvt();
                return true;
            case 1:
                zzkk(8);
                return true;
            case 2:
                zzkk(zzcuh());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzkk(4);
                return true;
            default:
                throw zzfew.zzcvw();
        }
        do {
            zzcts = zzcts();
            if (zzcts != 0) {
            }
            zzkf(((i >>> 3) << 3) | 4);
            return true;
        } while (zzkg(zzcts));
        zzkf(((i >>> 3) << 3) | 4);
        return true;
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzki(int i) throws zzfew {
        if (i < 0) {
            throw zzfew.zzcvs();
        }
        int zzcul = i + zzcul();
        int i2 = this.zzpba;
        if (zzcul > i2) {
            throw zzfew.zzcvr();
        }
        this.zzpba = zzcul;
        zzcup();
        return i2;
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final void zzkj(int i) {
        this.zzpba = i;
        zzcup();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final void zzkk(int i) throws IOException {
        if (i >= 0 && i <= this.limit - this.pos) {
            this.pos += i;
        } else if (i < 0) {
            throw zzfew.zzcvs();
        } else {
            throw zzfew.zzcvr();
        }
    }
}
