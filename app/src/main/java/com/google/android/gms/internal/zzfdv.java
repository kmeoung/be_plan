package com.google.android.gms.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public abstract class zzfdv extends zzfdg {
    private static final Logger logger = Logger.getLogger(zzfdv.class.getName());
    private static final boolean zzpbf = zzfgj.zzcww();

    /* loaded from: classes.dex */
    public static abstract class zza extends zzfdv {
        final byte[] buffer;
        final int limit;
        int position;
        int zzpbg;

        zza(int i) {
            super();
            if (i < 0) {
                throw new IllegalArgumentException("bufferSize must be >= 0");
            }
            this.buffer = new byte[Math.max(i, 20)];
            this.limit = this.buffer.length;
        }

        final void zzah(int i, int i2) {
            zzlf((i << 3) | i2);
        }

        final void zzc(byte b) {
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = b;
            this.zzpbg++;
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final int zzcur() {
            throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array or ByteBuffer.");
        }

        final void zzdb(long j) {
            if (zzfdv.zzpbf) {
                long j2 = this.position;
                while ((j & (-128)) != 0) {
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    zzfgj.zza(bArr, i, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                zzfgj.zza(bArr2, i2, (byte) j);
                this.zzpbg += (int) (this.position - j2);
                return;
            }
            while ((j & (-128)) != 0) {
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) ((((int) j) & 127) | 128);
                this.zzpbg++;
                j >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) j;
            this.zzpbg++;
        }

        final void zzdc(long j) {
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = (byte) (j & 255);
            byte[] bArr2 = this.buffer;
            int i2 = this.position;
            this.position = i2 + 1;
            bArr2[i2] = (byte) ((j >> 8) & 255);
            byte[] bArr3 = this.buffer;
            int i3 = this.position;
            this.position = i3 + 1;
            bArr3[i3] = (byte) ((j >> 16) & 255);
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) ((j >> 24) & 255);
            byte[] bArr5 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            bArr5[i5] = (byte) (j >> 32);
            byte[] bArr6 = this.buffer;
            int i6 = this.position;
            this.position = i6 + 1;
            bArr6[i6] = (byte) (j >> 40);
            byte[] bArr7 = this.buffer;
            int i7 = this.position;
            this.position = i7 + 1;
            bArr7[i7] = (byte) (j >> 48);
            byte[] bArr8 = this.buffer;
            int i8 = this.position;
            this.position = i8 + 1;
            bArr8[i8] = (byte) (j >> 56);
            this.zzpbg += 8;
        }

        final void zzlf(int i) {
            if (zzfdv.zzpbf) {
                long j = this.position;
                while ((i & (-128)) != 0) {
                    byte[] bArr = this.buffer;
                    int i2 = this.position;
                    this.position = i2 + 1;
                    zzfgj.zza(bArr, i2, (byte) ((i & 127) | 128));
                    i >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                zzfgj.zza(bArr2, i3, (byte) i);
                this.zzpbg += (int) (this.position - j);
                return;
            }
            while ((i & (-128)) != 0) {
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr3[i4] = (byte) ((i & 127) | 128);
                this.zzpbg++;
                i >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            bArr4[i5] = (byte) i;
            this.zzpbg++;
        }

        final void zzlg(int i) {
            byte[] bArr = this.buffer;
            int i2 = this.position;
            this.position = i2 + 1;
            bArr[i2] = (byte) i;
            byte[] bArr2 = this.buffer;
            int i3 = this.position;
            this.position = i3 + 1;
            bArr2[i3] = (byte) (i >> 8);
            byte[] bArr3 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr3[i4] = (byte) (i >> 16);
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            bArr4[i5] = i >> 24;
            this.zzpbg += 4;
        }
    }

    /* loaded from: classes.dex */
    public static class zzb extends zzfdv {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zzb(byte[] bArr, int i, int i2) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            }
            int i3 = i + i2;
            if ((i | i2 | (bArr.length - i3)) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
            }
            this.buffer = bArr;
            this.offset = i;
            this.position = i;
            this.limit = i3;
        }

        @Override // com.google.android.gms.internal.zzfdv
        public void flush() {
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)), e);
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zza(int i, long j) throws IOException {
            zzz(i, 0);
            zzcs(j);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zza(int i, zzfdh zzfdhVar) throws IOException {
            zzz(i, 2);
            zzam(zzfdhVar);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zza(int i, zzffi zzffiVar) throws IOException {
            zzz(i, 2);
            zzd(zzffiVar);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzaa(int i, int i2) throws IOException {
            zzz(i, 0);
            zzks(i2);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzab(int i, int i2) throws IOException {
            zzz(i, 0);
            zzkt(i2);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzac(int i, int i2) throws IOException {
            zzz(i, 5);
            zzkv(i2);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzam(zzfdh zzfdhVar) throws IOException {
            zzkt(zzfdhVar.size());
            zzfdhVar.zza(this);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzb(byte b) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzb(int i, long j) throws IOException {
            zzz(i, 1);
            zzcu(j);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzcs(long j) throws IOException {
            if (!zzfdv.zzpbf || zzcur() < 10) {
                while ((j & (-128)) != 0) {
                    try {
                        byte[] bArr = this.buffer;
                        int i = this.position;
                        this.position = i + 1;
                        bArr[i] = (byte) ((((int) j) & 127) | 128);
                        j >>>= 7;
                    } catch (IndexOutOfBoundsException e) {
                        throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
                    }
                }
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) j;
                return;
            }
            while ((j & (-128)) != 0) {
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                zzfgj.zza(bArr3, i3, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            zzfgj.zza(bArr4, i4, (byte) j);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzcu(long j) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) j;
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) (j >> 8);
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) (j >> 16);
                byte[] bArr4 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr4[i4] = (byte) (j >> 24);
                byte[] bArr5 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr5[i5] = (byte) (j >> 32);
                byte[] bArr6 = this.buffer;
                int i6 = this.position;
                this.position = i6 + 1;
                bArr6[i6] = (byte) (j >> 40);
                byte[] bArr7 = this.buffer;
                int i7 = this.position;
                this.position = i7 + 1;
                bArr7[i7] = (byte) (j >> 48);
                byte[] bArr8 = this.buffer;
                int i8 = this.position;
                this.position = i8 + 1;
                bArr8[i8] = (byte) (j >> 56);
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final int zzcur() {
            return this.limit - this.position;
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzd(zzffi zzffiVar) throws IOException {
            zzkt(zzffiVar.zzhl());
            zzffiVar.zza(this);
        }

        @Override // com.google.android.gms.internal.zzfdg
        public final void zzd(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzi(byte[] bArr, int i, int i2) throws IOException {
            zzkt(i2);
            write(bArr, 0, i2);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzks(int i) throws IOException {
            if (i >= 0) {
                zzkt(i);
            } else {
                zzcs(i);
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzkt(int i) throws IOException {
            if (!zzfdv.zzpbf || zzcur() < 10) {
                while ((i & (-128)) != 0) {
                    try {
                        byte[] bArr = this.buffer;
                        int i2 = this.position;
                        this.position = i2 + 1;
                        bArr[i2] = (byte) ((i & 127) | 128);
                        i >>>= 7;
                    } catch (IndexOutOfBoundsException e) {
                        throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
                    }
                }
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr2[i3] = (byte) i;
                return;
            }
            while ((i & (-128)) != 0) {
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                zzfgj.zza(bArr3, i4, (byte) ((i & 127) | 128));
                i >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            zzfgj.zza(bArr4, i5, (byte) i);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzkv(int i) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) i;
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr2[i3] = (byte) (i >> 8);
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr3[i4] = (byte) (i >> 16);
                byte[] bArr4 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr4[i5] = i >> 24;
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzl(int i, boolean z) throws IOException {
            zzz(i, 0);
            zzb(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzn(int i, String str) throws IOException {
            zzz(i, 2);
            zztc(str);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zztc(String str) throws IOException {
            int i = this.position;
            try {
                int zzky = zzky(str.length() * 3);
                int zzky2 = zzky(str.length());
                if (zzky2 == zzky) {
                    this.position = i + zzky2;
                    int zza = zzfgl.zza(str, this.buffer, this.position, zzcur());
                    this.position = i;
                    zzkt((zza - i) - zzky2);
                    this.position = zza;
                    return;
                }
                zzkt(zzfgl.zzd(str));
                this.position = zzfgl.zza(str, this.buffer, this.position, zzcur());
            } catch (zzfgo e) {
                this.position = i;
                zza(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new zzc(e2);
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzz(int i, int i2) throws IOException {
            zzkt((i << 3) | i2);
        }
    }

    /* loaded from: classes.dex */
    public static class zzc extends IOException {
        zzc() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        zzc(java.lang.String r3, java.lang.Throwable r4) {
            /*
                r2 = this;
                java.lang.String r0 = "CodedOutputStream was writing to a flat byte array and ran out of space.: "
                java.lang.String r0 = java.lang.String.valueOf(r0)
                java.lang.String r3 = java.lang.String.valueOf(r3)
                int r1 = r3.length()
                if (r1 == 0) goto L_0x0015
                java.lang.String r3 = r0.concat(r3)
                goto L_0x001a
            L_0x0015:
                java.lang.String r3 = new java.lang.String
                r3.<init>(r0)
            L_0x001a:
                r2.<init>(r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfdv.zzc.<init>(java.lang.String, java.lang.Throwable):void");
        }

        zzc(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class zzd extends zza {
        private final OutputStream out;

        zzd(OutputStream outputStream, int i) {
            super(i);
            if (outputStream == null) {
                throw new NullPointerException("out");
            }
            this.out = outputStream;
        }

        private final void doFlush() throws IOException {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }

        private final void zzlh(int i) throws IOException {
            if (this.limit - this.position < i) {
                doFlush();
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void flush() throws IOException {
            if (this.position > 0) {
                doFlush();
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.limit - this.position >= i2) {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } else {
                int i3 = this.limit - this.position;
                System.arraycopy(bArr, i, this.buffer, this.position, i3);
                int i4 = i + i3;
                i2 -= i3;
                this.position = this.limit;
                this.zzpbg += i3;
                doFlush();
                if (i2 <= this.limit) {
                    System.arraycopy(bArr, i4, this.buffer, 0, i2);
                    this.position = i2;
                } else {
                    this.out.write(bArr, i4, i2);
                }
            }
            this.zzpbg += i2;
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zza(int i, long j) throws IOException {
            zzlh(20);
            zzah(i, 0);
            zzdb(j);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zza(int i, zzfdh zzfdhVar) throws IOException {
            zzz(i, 2);
            zzam(zzfdhVar);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zza(int i, zzffi zzffiVar) throws IOException {
            zzz(i, 2);
            zzd(zzffiVar);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzaa(int i, int i2) throws IOException {
            zzlh(20);
            zzah(i, 0);
            if (i2 >= 0) {
                zzlf(i2);
            } else {
                zzdb(i2);
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzab(int i, int i2) throws IOException {
            zzlh(20);
            zzah(i, 0);
            zzlf(i2);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzac(int i, int i2) throws IOException {
            zzlh(14);
            zzah(i, 5);
            zzlg(i2);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzam(zzfdh zzfdhVar) throws IOException {
            zzkt(zzfdhVar.size());
            zzfdhVar.zza(this);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzb(byte b) throws IOException {
            if (this.position == this.limit) {
                doFlush();
            }
            zzc(b);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzb(int i, long j) throws IOException {
            zzlh(18);
            zzah(i, 1);
            zzdc(j);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzcs(long j) throws IOException {
            zzlh(10);
            zzdb(j);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzcu(long j) throws IOException {
            zzlh(8);
            zzdc(j);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzd(zzffi zzffiVar) throws IOException {
            zzkt(zzffiVar.zzhl());
            zzffiVar.zza(this);
        }

        @Override // com.google.android.gms.internal.zzfdg
        public final void zzd(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzi(byte[] bArr, int i, int i2) throws IOException {
            zzkt(i2);
            write(bArr, 0, i2);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzks(int i) throws IOException {
            if (i >= 0) {
                zzkt(i);
            } else {
                zzcs(i);
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzkt(int i) throws IOException {
            zzlh(10);
            zzlf(i);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzkv(int i) throws IOException {
            zzlh(4);
            zzlg(i);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzl(int i, boolean z) throws IOException {
            zzlh(11);
            zzah(i, 0);
            zzc(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzn(int i, String str) throws IOException {
            zzz(i, 2);
            zztc(str);
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zztc(String str) throws IOException {
            int i;
            try {
                int length = str.length() * 3;
                int zzky = zzky(length);
                int i2 = zzky + length;
                if (i2 > this.limit) {
                    byte[] bArr = new byte[length];
                    int zza = zzfgl.zza(str, bArr, 0, length);
                    zzkt(zza);
                    zzd(bArr, 0, zza);
                    return;
                }
                if (i2 > this.limit - this.position) {
                    doFlush();
                }
                int zzky2 = zzky(str.length());
                int i3 = this.position;
                try {
                    if (zzky2 == zzky) {
                        this.position = i3 + zzky2;
                        int zza2 = zzfgl.zza(str, this.buffer, this.position, this.limit - this.position);
                        this.position = i3;
                        i = (zza2 - i3) - zzky2;
                        zzlf(i);
                        this.position = zza2;
                    } else {
                        i = zzfgl.zzd(str);
                        zzlf(i);
                        this.position = zzfgl.zza(str, this.buffer, this.position, i);
                    }
                    this.zzpbg += i;
                } catch (zzfgo e) {
                    this.zzpbg -= this.position - i3;
                    this.position = i3;
                    throw e;
                } catch (ArrayIndexOutOfBoundsException e2) {
                    throw new zzc(e2);
                }
            } catch (zzfgo e3) {
                zza(str, e3);
            }
        }

        @Override // com.google.android.gms.internal.zzfdv
        public final void zzz(int i, int i2) throws IOException {
            zzkt((i << 3) | i2);
        }
    }

    private zzfdv() {
    }

    public static int zza(zzffc zzffcVar) {
        int zzhl = zzffcVar.zzhl();
        return zzky(zzhl) + zzhl;
    }

    public static int zzad(int i, int i2) {
        return zzkw(i) + zzkx(i2);
    }

    public static int zzae(int i, int i2) {
        return zzkw(i) + zzky(i2);
    }

    public static int zzaf(int i, int i2) {
        return zzkw(i) + 4;
    }

    public static int zzag(int i, int i2) {
        return zzkw(i) + zzkx(i2);
    }

    public static int zzan(zzfdh zzfdhVar) {
        int size = zzfdhVar.size();
        return zzky(size) + size;
    }

    public static int zzb(int i, double d) {
        return zzkw(i) + 8;
    }

    public static int zzb(int i, zzfdh zzfdhVar) {
        int zzkw = zzkw(i);
        int size = zzfdhVar.size();
        return zzkw + zzky(size) + size;
    }

    public static int zzb(int i, zzffi zzffiVar) {
        return zzkw(i) + zze(zzffiVar);
    }

    public static zzfdv zzb(OutputStream outputStream, int i) {
        return new zzd(outputStream, i);
    }

    public static zzfdv zzbb(byte[] bArr) {
        return zzh(bArr, 0, bArr.length);
    }

    public static int zzbc(byte[] bArr) {
        int length = bArr.length;
        return zzky(length) + length;
    }

    public static int zzc(int i, long j) {
        return zzkw(i) + zzcw(j);
    }

    public static int zzcv(long j) {
        return zzcw(j);
    }

    public static int zzcw(long j) {
        int i;
        if ((j & (-128)) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if ((j & (-34359738368L)) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if ((j & (-2097152)) != 0) {
            i += 2;
            j >>>= 14;
        }
        return (j & (-16384)) != 0 ? i + 1 : i;
    }

    public static int zzcx(long j) {
        return zzcw(zzda(j));
    }

    public static int zzcy(long j) {
        return 8;
    }

    public static int zzcz(long j) {
        return 8;
    }

    public static int zzd(int i, long j) {
        return zzkw(i) + zzcw(j);
    }

    public static int zzda(boolean z) {
        return 1;
    }

    private static long zzda(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static int zze(int i, long j) {
        return zzkw(i) + 8;
    }

    public static int zze(zzffi zzffiVar) {
        int zzhl = zzffiVar.zzhl();
        return zzky(zzhl) + zzhl;
    }

    public static int zzf(float f) {
        return 4;
    }

    @Deprecated
    public static int zzf(zzffi zzffiVar) {
        return zzffiVar.zzhl();
    }

    public static zzfdv zzh(byte[] bArr, int i, int i2) {
        return new zzb(bArr, i, i2);
    }

    public static int zzkr(int i) {
        if (i > 4096) {
            return 4096;
        }
        return i;
    }

    public static int zzkw(int i) {
        return zzky(i << 3);
    }

    public static int zzkx(int i) {
        if (i >= 0) {
            return zzky(i);
        }
        return 10;
    }

    public static int zzky(int i) {
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

    public static int zzkz(int i) {
        return zzky(zzle(i));
    }

    public static int zzla(int i) {
        return 4;
    }

    public static int zzlb(int i) {
        return 4;
    }

    public static int zzlc(int i) {
        return zzkx(i);
    }

    public static int zzld(int i) {
        return zzky(i) + i;
    }

    private static int zzle(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static int zzm(int i, boolean z) {
        return zzkw(i) + 1;
    }

    public static int zzn(double d) {
        return 8;
    }

    public static int zzo(int i, String str) {
        return zzkw(i) + zztd(str);
    }

    public static int zztd(String str) {
        int i;
        try {
            i = zzfgl.zzd(str);
        } catch (zzfgo unused) {
            i = str.getBytes(zzfer.UTF_8).length;
        }
        return zzky(i) + i;
    }

    public abstract void flush() throws IOException;

    public abstract void write(byte[] bArr, int i, int i2) throws IOException;

    public final void zza(int i, double d) throws IOException {
        zzb(i, Double.doubleToRawLongBits(d));
    }

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzfdh zzfdhVar) throws IOException;

    public abstract void zza(int i, zzffi zzffiVar) throws IOException;

    final void zza(String str, zzfgo zzfgoVar) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) zzfgoVar);
        byte[] bytes = str.getBytes(zzfer.UTF_8);
        try {
            zzkt(bytes.length);
            zzd(bytes, 0, bytes.length);
        } catch (zzc e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new zzc(e2);
        }
    }

    public abstract void zzaa(int i, int i2) throws IOException;

    public abstract void zzab(int i, int i2) throws IOException;

    public abstract void zzac(int i, int i2) throws IOException;

    public abstract void zzam(zzfdh zzfdhVar) throws IOException;

    public abstract void zzb(byte b) throws IOException;

    public abstract void zzb(int i, long j) throws IOException;

    public abstract void zzcs(long j) throws IOException;

    public final void zzct(long j) throws IOException {
        zzcs(zzda(j));
    }

    public abstract void zzcu(long j) throws IOException;

    public abstract int zzcur();

    public final void zzcus() {
        if (zzcur() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public abstract void zzd(zzffi zzffiVar) throws IOException;

    public abstract void zzi(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zzks(int i) throws IOException;

    public abstract void zzkt(int i) throws IOException;

    public final void zzku(int i) throws IOException {
        zzkt(zzle(i));
    }

    public abstract void zzkv(int i) throws IOException;

    public abstract void zzl(int i, boolean z) throws IOException;

    public abstract void zzn(int i, String str) throws IOException;

    public abstract void zztc(String str) throws IOException;

    public abstract void zzz(int i, int i2) throws IOException;
}
