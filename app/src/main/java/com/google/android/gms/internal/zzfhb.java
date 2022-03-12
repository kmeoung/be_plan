package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzfhb {
    private final byte[] buffer;
    private int zzpar;
    private int zzpax;
    private int zzpaz;
    private int zzpbc;
    private int zzpgv;
    private int zzpgw;
    private int zzpba = Integer.MAX_VALUE;
    private int zzpas = 64;
    private int zzpat = 67108864;

    private zzfhb(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzpgv = i;
        this.zzpbc = i2 + i;
        this.zzpgw = i;
    }

    public static zzfhb zzbd(byte[] bArr) {
        return zzn(bArr, 0, bArr.length);
    }

    private final void zzcup() {
        this.zzpbc += this.zzpax;
        int i = this.zzpbc;
        if (i > this.zzpba) {
            this.zzpax = i - this.zzpba;
            this.zzpbc -= this.zzpax;
            return;
        }
        this.zzpax = 0;
    }

    private final byte zzcuq() throws IOException {
        if (this.zzpgw == this.zzpbc) {
            throw zzfhj.zzcxh();
        }
        byte[] bArr = this.buffer;
        int i = this.zzpgw;
        this.zzpgw = i + 1;
        return bArr[i];
    }

    private final void zzkk(int i) throws IOException {
        if (i < 0) {
            throw zzfhj.zzcxi();
        } else if (this.zzpgw + i > this.zzpba) {
            zzkk(this.zzpba - this.zzpgw);
            throw zzfhj.zzcxh();
        } else if (i <= this.zzpbc - this.zzpgw) {
            this.zzpgw += i;
        } else {
            throw zzfhj.zzcxh();
        }
    }

    public static zzfhb zzn(byte[] bArr, int i, int i2) {
        return new zzfhb(bArr, 0, i2);
    }

    public final int getPosition() {
        return this.zzpgw - this.zzpgv;
    }

    public final byte[] readBytes() throws IOException {
        int zzcuh = zzcuh();
        if (zzcuh < 0) {
            throw zzfhj.zzcxi();
        } else if (zzcuh == 0) {
            return zzfhn.zzphr;
        } else {
            if (zzcuh > this.zzpbc - this.zzpgw) {
                throw zzfhj.zzcxh();
            }
            byte[] bArr = new byte[zzcuh];
            System.arraycopy(this.buffer, this.zzpgw, bArr, 0, zzcuh);
            this.zzpgw += zzcuh;
            return bArr;
        }
    }

    public final String readString() throws IOException {
        int zzcuh = zzcuh();
        if (zzcuh < 0) {
            throw zzfhj.zzcxi();
        } else if (zzcuh > this.zzpbc - this.zzpgw) {
            throw zzfhj.zzcxh();
        } else {
            String str = new String(this.buffer, this.zzpgw, zzcuh, zzfhi.UTF_8);
            this.zzpgw += zzcuh;
            return str;
        }
    }

    public final void zza(zzfhk zzfhkVar) throws IOException {
        int zzcuh = zzcuh();
        if (this.zzpar >= this.zzpas) {
            throw zzfhj.zzcxk();
        }
        int zzki = zzki(zzcuh);
        this.zzpar++;
        zzfhkVar.zza(this);
        zzkf(0);
        this.zzpar--;
        zzkj(zzki);
    }

    public final void zza(zzfhk zzfhkVar, int i) throws IOException {
        if (this.zzpar >= this.zzpas) {
            throw zzfhj.zzcxk();
        }
        this.zzpar++;
        zzfhkVar.zza(this);
        zzkf((i << 3) | 4);
        this.zzpar--;
    }

    public final byte[] zzal(int i, int i2) {
        if (i2 == 0) {
            return zzfhn.zzphr;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.buffer, this.zzpgv + i, bArr, 0, i2);
        return bArr;
    }

    public final void zzam(int i, int i2) {
        if (i > this.zzpgw - this.zzpgv) {
            StringBuilder sb = new StringBuilder(50);
            sb.append("Position ");
            sb.append(i);
            sb.append(" is beyond current ");
            sb.append(this.zzpgw - this.zzpgv);
            throw new IllegalArgumentException(sb.toString());
        } else if (i < 0) {
            StringBuilder sb2 = new StringBuilder(24);
            sb2.append("Bad position ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        } else {
            this.zzpgw = this.zzpgv + i;
            this.zzpaz = i2;
        }
    }

    public final int zzcts() throws IOException {
        if (this.zzpgw == this.zzpbc) {
            this.zzpaz = 0;
            return 0;
        }
        this.zzpaz = zzcuh();
        if (this.zzpaz != 0) {
            return this.zzpaz;
        }
        throw new zzfhj("Protocol message contained an invalid tag (zero).");
    }

    public final long zzctu() throws IOException {
        return zzcum();
    }

    public final int zzctv() throws IOException {
        return zzcuh();
    }

    public final boolean zzcty() throws IOException {
        return zzcuh() != 0;
    }

    public final long zzcug() throws IOException {
        long zzcum = zzcum();
        return (zzcum >>> 1) ^ (-(zzcum & 1));
    }

    public final int zzcuh() throws IOException {
        int i;
        byte zzcuq = zzcuq();
        if (zzcuq >= 0) {
            return zzcuq;
        }
        int i2 = zzcuq & Byte.MAX_VALUE;
        byte zzcuq2 = zzcuq();
        if (zzcuq2 >= 0) {
            i = zzcuq2 << 7;
        } else {
            i2 |= (zzcuq2 & Byte.MAX_VALUE) << 7;
            byte zzcuq3 = zzcuq();
            if (zzcuq3 >= 0) {
                i = zzcuq3 << 14;
            } else {
                i2 |= (zzcuq3 & Byte.MAX_VALUE) << 14;
                byte zzcuq4 = zzcuq();
                if (zzcuq4 >= 0) {
                    i = zzcuq4 << 21;
                } else {
                    int i3 = i2 | ((zzcuq4 & Byte.MAX_VALUE) << 21);
                    byte zzcuq5 = zzcuq();
                    int i4 = i3 | (zzcuq5 << 28);
                    if (zzcuq5 >= 0) {
                        return i4;
                    }
                    for (int i5 = 0; i5 < 5; i5++) {
                        if (zzcuq() >= 0) {
                            return i4;
                        }
                    }
                    throw zzfhj.zzcxj();
                }
            }
        }
        return i2 | i;
    }

    public final int zzcuj() {
        if (this.zzpba == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzpba - this.zzpgw;
    }

    public final long zzcum() throws IOException {
        int i = 0;
        long j = 0;
        while (i < 64) {
            byte zzcuq = zzcuq();
            long j2 = j | ((zzcuq & Byte.MAX_VALUE) << i);
            if ((zzcuq & 128) == 0) {
                return j2;
            }
            i += 7;
            j = j2;
        }
        throw zzfhj.zzcxj();
    }

    public final int zzcun() throws IOException {
        return (zzcuq() & 255) | ((zzcuq() & 255) << 8) | ((zzcuq() & 255) << 16) | ((zzcuq() & 255) << 24);
    }

    public final long zzcuo() throws IOException {
        return (zzcuq() & 255) | ((zzcuq() & 255) << 8) | ((zzcuq() & 255) << 16) | ((zzcuq() & 255) << 24) | ((zzcuq() & 255) << 32) | ((zzcuq() & 255) << 40) | ((zzcuq() & 255) << 48) | ((zzcuq() & 255) << 56);
    }

    public final void zzkf(int i) throws zzfhj {
        if (this.zzpaz != i) {
            throw new zzfhj("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzkg(int i) throws IOException {
        int zzcts;
        switch (i & 7) {
            case 0:
                zzcuh();
                return true;
            case 1:
                zzcuo();
                return true;
            case 2:
                zzkk(zzcuh());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzcun();
                return true;
            default:
                throw new zzfhj("Protocol message tag had invalid wire type.");
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

    public final int zzki(int i) throws zzfhj {
        if (i < 0) {
            throw zzfhj.zzcxi();
        }
        int i2 = i + this.zzpgw;
        int i3 = this.zzpba;
        if (i2 > i3) {
            throw zzfhj.zzcxh();
        }
        this.zzpba = i2;
        zzcup();
        return i3;
    }

    public final void zzkj(int i) {
        this.zzpba = i;
        zzcup();
    }

    public final void zzlv(int i) {
        zzam(i, this.zzpaz);
    }
}
