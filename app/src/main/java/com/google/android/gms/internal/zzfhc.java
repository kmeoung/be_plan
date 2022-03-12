package com.google.android.gms.internal;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* loaded from: classes.dex */
public final class zzfhc {
    private final ByteBuffer zzpgx;

    private zzfhc(ByteBuffer byteBuffer) {
        this.zzpgx = byteBuffer;
        this.zzpgx.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzfhc(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
        return r8 + r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int zza(java.lang.CharSequence r6, byte[] r7, int r8, int r9) {
        /*
            Method dump skipped, instructions count: 248
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfhc.zza(java.lang.CharSequence, byte[], int, int):int");
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(zza(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (ArrayIndexOutOfBoundsException e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        } else {
            zzb(charSequence, byteBuffer);
        }
    }

    public static int zzad(int i, int i2) {
        return zzkw(i) + zzkx(i2);
    }

    public static int zzb(int i, zzfhk zzfhkVar) {
        int zzkw = zzkw(i);
        int zzhl = zzfhkVar.zzhl();
        return zzkw + zzly(zzhl) + zzhl;
    }

    private static void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i;
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            char c = charAt;
            if (charAt >= 128) {
                if (charAt < 2048) {
                    i = (charAt >>> 6) | 960;
                } else if (charAt < 55296 || 57343 < charAt) {
                    byteBuffer.put((byte) ((charAt >>> '\f') | 480));
                    i = ((charAt >>> 6) & 63) | 128;
                } else {
                    int i3 = i2 + 1;
                    if (i3 != charSequence.length()) {
                        char charAt2 = charSequence.charAt(i3);
                        if (!Character.isSurrogatePair(charAt, charAt2)) {
                            i2 = i3;
                        } else {
                            int codePoint = Character.toCodePoint(charAt, charAt2);
                            byteBuffer.put((byte) ((codePoint >>> 18) | 240));
                            byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                            byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                            byteBuffer.put((byte) ((codePoint & 63) | 128));
                            i2 = i3;
                            i2++;
                        }
                    }
                    StringBuilder sb = new StringBuilder(39);
                    sb.append("Unpaired surrogate at index ");
                    sb.append(i2 - 1);
                    throw new IllegalArgumentException(sb.toString());
                }
                byteBuffer.put((byte) i);
                c = (charAt & '?') | 128;
            }
            byteBuffer.put(c == 1 ? (byte) 1 : (byte) 0);
            i2++;
        }
    }

    public static zzfhc zzbe(byte[] bArr) {
        return zzo(bArr, 0, bArr.length);
    }

    public static int zzbf(byte[] bArr) {
        return zzly(bArr.length) + bArr.length;
    }

    public static int zzc(int i, long j) {
        return zzkw(i) + zzdh(j);
    }

    public static int zzd(int i, byte[] bArr) {
        return zzkw(i) + zzbf(bArr);
    }

    private static int zzd(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt = charSequence.charAt(i2);
            if (charAt < 2048) {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 2048) {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) < 65536) {
                                StringBuilder sb = new StringBuilder(39);
                                sb.append("Unpaired surrogate at index ");
                                sb.append(i2);
                                throw new IllegalArgumentException(sb.toString());
                            }
                            i2++;
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        StringBuilder sb2 = new StringBuilder(54);
        sb2.append("UTF-8 length does not fit in int: ");
        sb2.append(i3 + 4294967296L);
        throw new IllegalArgumentException(sb2.toString());
    }

    private static long zzda(long j) {
        return (j << 1) ^ (j >> 63);
    }

    private final void zzdg(long j) throws IOException {
        while ((j & (-128)) != 0) {
            zzlw((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzlw((int) j);
    }

    public static int zzdh(long j) {
        if ((j & (-128)) == 0) {
            return 1;
        }
        if ((j & (-16384)) == 0) {
            return 2;
        }
        if ((j & (-2097152)) == 0) {
            return 3;
        }
        if ((j & (-268435456)) == 0) {
            return 4;
        }
        if ((j & (-34359738368L)) == 0) {
            return 5;
        }
        if ((j & (-4398046511104L)) == 0) {
            return 6;
        }
        if ((j & (-562949953421312L)) == 0) {
            return 7;
        }
        if ((j & (-72057594037927936L)) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    private final void zzdi(long j) throws IOException {
        if (this.zzpgx.remaining() < 8) {
            throw new zzfhd(this.zzpgx.position(), this.zzpgx.limit());
        }
        this.zzpgx.putLong(j);
    }

    public static int zzh(int i, long j) {
        return zzkw(i) + zzdh(zzda(j));
    }

    public static int zzkw(int i) {
        return zzly(i << 3);
    }

    public static int zzkx(int i) {
        if (i >= 0) {
            return zzly(i);
        }
        return 10;
    }

    public static int zzle(int i) {
        return (i >> 31) ^ (i << 1);
    }

    private final void zzlw(int i) throws IOException {
        byte b = (byte) i;
        if (!this.zzpgx.hasRemaining()) {
            throw new zzfhd(this.zzpgx.position(), this.zzpgx.limit());
        }
        this.zzpgx.put(b);
    }

    public static int zzly(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static int zzo(int i, String str) {
        return zzkw(i) + zztd(str);
    }

    public static zzfhc zzo(byte[] bArr, int i, int i2) {
        return new zzfhc(bArr, 0, i2);
    }

    public static int zztd(String str) {
        int zzd = zzd(str);
        return zzly(zzd) + zzd;
    }

    public final void zza(int i, double d) throws IOException {
        zzz(i, 1);
        zzdi(Double.doubleToLongBits(d));
    }

    public final void zza(int i, long j) throws IOException {
        zzz(i, 0);
        zzdg(j);
    }

    public final void zza(int i, zzfhk zzfhkVar) throws IOException {
        zzz(i, 2);
        zzb(zzfhkVar);
    }

    public final void zzaa(int i, int i2) throws IOException {
        zzz(i, 0);
        if (i2 >= 0) {
            zzlx(i2);
        } else {
            zzdg(i2);
        }
    }

    public final void zzb(int i, long j) throws IOException {
        zzz(i, 1);
        zzdi(j);
    }

    public final void zzb(zzfhk zzfhkVar) throws IOException {
        zzlx(zzfhkVar.zzcxl());
        zzfhkVar.zza(this);
    }

    public final void zzbg(byte[] bArr) throws IOException {
        int length = bArr.length;
        if (this.zzpgx.remaining() >= length) {
            this.zzpgx.put(bArr, 0, length);
            return;
        }
        throw new zzfhd(this.zzpgx.position(), this.zzpgx.limit());
    }

    public final void zzc(int i, float f) throws IOException {
        zzz(i, 5);
        int floatToIntBits = Float.floatToIntBits(f);
        if (this.zzpgx.remaining() < 4) {
            throw new zzfhd(this.zzpgx.position(), this.zzpgx.limit());
        }
        this.zzpgx.putInt(floatToIntBits);
    }

    public final void zzc(int i, byte[] bArr) throws IOException {
        zzz(i, 2);
        zzlx(bArr.length);
        zzbg(bArr);
    }

    public final void zzcus() {
        if (this.zzpgx.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", Integer.valueOf(this.zzpgx.remaining())));
        }
    }

    public final void zzf(int i, long j) throws IOException {
        zzz(i, 0);
        zzdg(j);
    }

    public final void zzg(int i, long j) throws IOException {
        zzz(i, 0);
        zzdg(zzda(j));
    }

    public final void zzl(int i, boolean z) throws IOException {
        zzz(i, 0);
        byte b = z ? (byte) 1 : (byte) 0;
        if (!this.zzpgx.hasRemaining()) {
            throw new zzfhd(this.zzpgx.position(), this.zzpgx.limit());
        }
        this.zzpgx.put(b);
    }

    public final void zzlx(int i) throws IOException {
        while ((i & (-128)) != 0) {
            zzlw((i & 127) | 128);
            i >>>= 7;
        }
        zzlw(i);
    }

    public final void zzn(int i, String str) throws IOException {
        zzz(i, 2);
        try {
            int zzly = zzly(str.length());
            if (zzly == zzly(str.length() * 3)) {
                int position = this.zzpgx.position();
                if (this.zzpgx.remaining() < zzly) {
                    throw new zzfhd(position + zzly, this.zzpgx.limit());
                }
                this.zzpgx.position(position + zzly);
                zza(str, this.zzpgx);
                int position2 = this.zzpgx.position();
                this.zzpgx.position(position);
                zzlx((position2 - position) - zzly);
                this.zzpgx.position(position2);
                return;
            }
            zzlx(zzd(str));
            zza(str, this.zzpgx);
        } catch (BufferOverflowException e) {
            zzfhd zzfhdVar = new zzfhd(this.zzpgx.position(), this.zzpgx.limit());
            zzfhdVar.initCause(e);
            throw zzfhdVar;
        }
    }

    public final void zzz(int i, int i2) throws IOException {
        zzlx((i << 3) | i2);
    }
}
