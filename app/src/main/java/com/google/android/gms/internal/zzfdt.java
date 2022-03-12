package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class zzfdt extends zzfdq {
    private final byte[] buffer;
    private int pos;
    private int zzpax;
    private int zzpaz;
    private int zzpba;
    private final InputStream zzpbb;
    private int zzpbc;
    private int zzpbd;
    private zzfdu zzpbe;

    private zzfdt(InputStream inputStream, int i) {
        super();
        this.zzpba = Integer.MAX_VALUE;
        this.zzpbe = null;
        zzfer.zzc(inputStream, "input");
        this.zzpbb = inputStream;
        this.buffer = new byte[i];
        this.zzpbc = 0;
        this.pos = 0;
        this.zzpbd = 0;
    }

    private final long zzcum() throws IOException {
        long j;
        long j2;
        long j3;
        int i;
        int i2 = this.pos;
        if (this.zzpbc != i2) {
            byte[] bArr = this.buffer;
            int i3 = i2 + 1;
            byte b = bArr[i2];
            if (b >= 0) {
                this.pos = i3;
                return b;
            } else if (this.zzpbc - i3 >= 9) {
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
        if (this.zzpbc - i < 4) {
            zzkm(4);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private final long zzcuo() throws IOException {
        int i = this.pos;
        if (this.zzpbc - i < 8) {
            zzkm(8);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48) | ((bArr[i + 7] & 255) << 56);
    }

    private final void zzcup() {
        this.zzpbc += this.zzpax;
        int i = this.zzpbd + this.zzpbc;
        if (i > this.zzpba) {
            this.zzpax = i - this.zzpba;
            this.zzpbc -= this.zzpax;
            return;
        }
        this.zzpax = 0;
    }

    private final byte zzcuq() throws IOException {
        if (this.pos == this.zzpbc) {
            zzkm(1);
        }
        byte[] bArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i];
    }

    private final void zzkm(int i) throws IOException {
        if (zzkn(i)) {
            return;
        }
        if (i > (this.zzpat - this.zzpbd) - this.pos) {
            throw zzfew.zzcvy();
        }
        throw zzfew.zzcvr();
    }

    private final boolean zzkn(int i) throws IOException {
        while (this.pos + i > this.zzpbc) {
            if (i > (this.zzpat - this.zzpbd) - this.pos || this.zzpbd + this.pos + i > this.zzpba) {
                return false;
            }
            int i2 = this.pos;
            if (i2 > 0) {
                if (this.zzpbc > i2) {
                    System.arraycopy(this.buffer, i2, this.buffer, 0, this.zzpbc - i2);
                }
                this.zzpbd += i2;
                this.zzpbc -= i2;
                this.pos = 0;
            }
            int read = this.zzpbb.read(this.buffer, this.zzpbc, Math.min(this.buffer.length - this.zzpbc, (this.zzpat - this.zzpbd) - this.zzpbc));
            if (read == 0 || read < -1 || read > this.buffer.length) {
                StringBuilder sb = new StringBuilder(102);
                sb.append("InputStream#read(byte[]) returned invalid result: ");
                sb.append(read);
                sb.append("\nThe InputStream implementation is buggy.");
                throw new IllegalStateException(sb.toString());
            } else if (read <= 0) {
                return false;
            } else {
                this.zzpbc += read;
                zzcup();
                if (this.zzpbc >= i) {
                    return true;
                }
            }
        }
        StringBuilder sb2 = new StringBuilder(77);
        sb2.append("refillBuffer() called when ");
        sb2.append(i);
        sb2.append(" bytes were already available in buffer");
        throw new IllegalStateException(sb2.toString());
    }

    private final byte[] zzko(int i) throws IOException {
        byte[] zzkp = zzkp(i);
        if (zzkp != null) {
            return zzkp;
        }
        int i2 = this.pos;
        int i3 = this.zzpbc - this.pos;
        this.zzpbd += this.zzpbc;
        this.pos = 0;
        this.zzpbc = 0;
        List<byte[]> zzkq = zzkq(i - i3);
        byte[] bArr = new byte[i];
        System.arraycopy(this.buffer, i2, bArr, 0, i3);
        for (byte[] bArr2 : zzkq) {
            System.arraycopy(bArr2, 0, bArr, i3, bArr2.length);
            i3 += bArr2.length;
        }
        return bArr;
    }

    private final byte[] zzkp(int i) throws IOException {
        if (i == 0) {
            return zzfer.EMPTY_BYTE_ARRAY;
        }
        if (i < 0) {
            throw zzfew.zzcvs();
        }
        int i2 = this.zzpbd + this.pos + i;
        if (i2 - this.zzpat > 0) {
            throw zzfew.zzcvy();
        } else if (i2 > this.zzpba) {
            zzkk((this.zzpba - this.zzpbd) - this.pos);
            throw zzfew.zzcvr();
        } else {
            int i3 = this.zzpbc - this.pos;
            int i4 = i - i3;
            if (i4 >= 4096 && i4 > this.zzpbb.available()) {
                return null;
            }
            byte[] bArr = new byte[i];
            System.arraycopy(this.buffer, this.pos, bArr, 0, i3);
            this.zzpbd += this.zzpbc;
            this.pos = 0;
            this.zzpbc = 0;
            while (i3 < bArr.length) {
                int read = this.zzpbb.read(bArr, i3, i - i3);
                if (read == -1) {
                    throw zzfew.zzcvr();
                }
                this.zzpbd += read;
                i3 += read;
            }
            return bArr;
        }
    }

    private final List<byte[]> zzkq(int i) throws IOException {
        ArrayList arrayList = new ArrayList();
        while (i > 0) {
            byte[] bArr = new byte[Math.min(i, 4096)];
            int i2 = 0;
            while (i2 < bArr.length) {
                int read = this.zzpbb.read(bArr, i2, bArr.length - i2);
                if (read == -1) {
                    throw zzfew.zzcvr();
                }
                this.zzpbd += read;
                i2 += read;
            }
            i -= bArr.length;
            arrayList.add(bArr);
        }
        return arrayList;
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
        if (zzcuh > 0 && zzcuh <= this.zzpbc - this.pos) {
            String str = new String(this.buffer, this.pos, zzcuh, zzfer.UTF_8);
            this.pos += zzcuh;
            return str;
        } else if (zzcuh == 0) {
            return "";
        } else {
            if (zzcuh > this.zzpbc) {
                return new String(zzko(zzcuh), zzfer.UTF_8);
            }
            zzkm(zzcuh);
            String str2 = new String(this.buffer, this.pos, zzcuh, zzfer.UTF_8);
            this.pos += zzcuh;
            return str2;
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
        byte[] bArr;
        int zzcuh = zzcuh();
        int i = this.pos;
        int i2 = 0;
        if (zzcuh <= this.zzpbc - i && zzcuh > 0) {
            bArr = this.buffer;
            this.pos = i + zzcuh;
            i2 = i;
        } else if (zzcuh == 0) {
            return "";
        } else {
            if (zzcuh <= this.zzpbc) {
                zzkm(zzcuh);
                bArr = this.buffer;
                this.pos = zzcuh;
            } else {
                bArr = zzko(zzcuh);
            }
        }
        if (zzfgl.zzk(bArr, i2, i2 + zzcuh)) {
            return new String(bArr, i2, zzcuh, zzfer.UTF_8);
        }
        throw zzfew.zzcvz();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final zzfdh zzcua() throws IOException {
        int zzcuh = zzcuh();
        if (zzcuh <= this.zzpbc - this.pos && zzcuh > 0) {
            zzfdh zze = zzfdh.zze(this.buffer, this.pos, zzcuh);
            this.pos += zzcuh;
            return zze;
        } else if (zzcuh == 0) {
            return zzfdh.zzpal;
        } else {
            byte[] zzkp = zzkp(zzcuh);
            if (zzkp != null) {
                return zzfdh.zzaz(zzkp);
            }
            int i = this.pos;
            int i2 = this.zzpbc - this.pos;
            this.zzpbd += this.zzpbc;
            this.pos = 0;
            this.zzpbc = 0;
            List<byte[]> zzkq = zzkq(zzcuh - i2);
            ArrayList arrayList = new ArrayList(zzkq.size() + 1);
            arrayList.add(zzfdh.zze(this.buffer, i, i2));
            for (byte[] bArr : zzkq) {
                arrayList.add(zzfdh.zzaz(bArr));
            }
            return zzfdh.zzf(arrayList);
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
            int r1 = r5.zzpbc
            if (r1 == r0) goto L_0x006d
            byte[] r1 = r5.buffer
            int r2 = r0 + 1
            byte r0 = r1[r0]
            if (r0 < 0) goto L_0x0011
            r5.pos = r2
            return r0
        L_0x0011:
            int r3 = r5.zzpbc
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfdt.zzcuh():int");
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
        return this.zzpba - (this.zzpbd + this.pos);
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final boolean zzcuk() throws IOException {
        return this.pos == this.zzpbc && !zzkn(1);
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final int zzcul() {
        return this.zzpbd + this.pos;
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
                if (this.zzpbc - this.pos >= 10) {
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
        int i2 = i + this.zzpbd + this.pos;
        int i3 = this.zzpba;
        if (i2 > i3) {
            throw zzfew.zzcvr();
        }
        this.zzpba = i2;
        zzcup();
        return i3;
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final void zzkj(int i) {
        this.zzpba = i;
        zzcup();
    }

    @Override // com.google.android.gms.internal.zzfdq
    public final void zzkk(int i) throws IOException {
        if (i <= this.zzpbc - this.pos && i >= 0) {
            this.pos += i;
        } else if (i < 0) {
            throw zzfew.zzcvs();
        } else if (this.zzpbd + this.pos + i > this.zzpba) {
            zzkk((this.zzpba - this.zzpbd) - this.pos);
            throw zzfew.zzcvr();
        } else {
            int i2 = this.zzpbc - this.pos;
            this.pos = this.zzpbc;
            while (true) {
                zzkm(1);
                int i3 = i - i2;
                if (i3 > this.zzpbc) {
                    i2 += this.zzpbc;
                    this.pos = this.zzpbc;
                } else {
                    this.pos = i3;
                    return;
                }
            }
        }
    }
}
