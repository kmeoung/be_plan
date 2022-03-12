package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class zzfdh implements Serializable, Iterable<Byte> {
    public static final zzfdh zzpal = new zzfdo(zzfer.EMPTY_BYTE_ARRAY);
    private static final zzfdl zzpam;
    private int zzlwd = 0;

    static {
        boolean z;
        try {
            Class.forName("android.content.Context");
            z = true;
        } catch (ClassNotFoundException unused) {
            z = false;
        }
        zzpam = z ? new zzfdp(null) : new zzfdj(null);
    }

    private static zzfdh zza(Iterator<zzfdh> it, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException(String.format("length (%s) must be >= 1", Integer.valueOf(i)));
        } else if (i == 1) {
            return it.next();
        } else {
            int i2 = i >>> 1;
            zzfdh zza = zza(it, i2);
            zzfdh zza2 = zza(it, i - i2);
            if (Integer.MAX_VALUE - zza.size() >= zza2.size()) {
                return zzffp.zza(zza, zza2);
            }
            int size = zza.size();
            int size2 = zza2.size();
            StringBuilder sb = new StringBuilder(53);
            sb.append("ByteString would be too long: ");
            sb.append(size);
            sb.append("+");
            sb.append(size2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static zzfdh zzay(byte[] bArr) {
        return zze(bArr, 0, bArr.length);
    }

    public static zzfdh zzaz(byte[] bArr) {
        return new zzfdo(bArr);
    }

    public static zzfdh zze(byte[] bArr, int i, int i2) {
        return new zzfdo(zzpam.zzf(bArr, i, i2));
    }

    public static zzfdh zzf(Iterable<zzfdh> iterable) {
        int size = ((Collection) iterable).size();
        return size == 0 ? zzpal : zza(iterable.iterator(), size);
    }

    public static int zzh(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else {
            StringBuilder sb3 = new StringBuilder(37);
            sb3.append("End index: ");
            sb3.append(i2);
            sb3.append(" >= ");
            sb3.append(i3);
            throw new IndexOutOfBoundsException(sb3.toString());
        }
    }

    public static zzfdm zzke(int i) {
        return new zzfdm(i, null);
    }

    public static zzfdh zztb(String str) {
        return new zzfdo(str.getBytes(zzfer.UTF_8));
    }

    public static void zzy(int i, int i2) {
        if (((i2 - (i + 1)) | i) >= 0) {
            return;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(22);
            sb.append("Index < 0: ");
            sb.append(i);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder(40);
        sb2.append("Index > length: ");
        sb2.append(i);
        sb2.append(", ");
        sb2.append(i2);
        throw new ArrayIndexOutOfBoundsException(sb2.toString());
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int i = this.zzlwd;
        if (i == 0) {
            int size = size();
            i = zzg(size, 0, size);
            if (i == 0) {
                i = 1;
            }
            this.zzlwd = i;
        }
        return i;
    }

    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzfdi(this);
    }

    public abstract int size();

    public final byte[] toByteArray() {
        int size = size();
        if (size == 0) {
            return zzfer.EMPTY_BYTE_ARRAY;
        }
        byte[] bArr = new byte[size];
        zzb(bArr, 0, 0, size);
        return bArr;
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size()));
    }

    public abstract void zza(zzfdg zzfdgVar) throws IOException;

    public final void zza(byte[] bArr, int i, int i2, int i3) {
        zzh(i, i + i3, size());
        zzh(i2, i2 + i3, bArr.length);
        if (i3 > 0) {
            zzb(bArr, i, i2, i3);
        }
    }

    public abstract void zzb(byte[] bArr, int i, int i2, int i3);

    public abstract zzfdq zzctl();

    public abstract int zzctm();

    public abstract boolean zzctn();

    public final int zzcto() {
        return this.zzlwd;
    }

    public abstract int zzg(int i, int i2, int i3);

    public abstract byte zzkd(int i);

    public abstract zzfdh zzx(int i, int i2);
}
