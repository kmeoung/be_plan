package com.google.android.gms.internal;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final class zzfer {
    public static final byte[] EMPTY_BYTE_ARRAY;
    private static ByteBuffer zzpcs;
    private static zzfdq zzpct;
    static final Charset UTF_8 = Charset.forName("UTF-8");
    private static Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    static {
        byte[] bArr = new byte[0];
        EMPTY_BYTE_ARRAY = bArr;
        zzpcs = ByteBuffer.wrap(bArr);
        zzpct = zzfdq.zzba(EMPTY_BYTE_ARRAY);
    }

    public static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static int hashCode(byte[] bArr) {
        int length = bArr.length;
        int zza = zza(length, bArr, 0, length);
        if (zza == 0) {
            return 1;
        }
        return zza;
    }

    public static int zza(int i, byte[] bArr, int i2, int i3) {
        int i4 = i;
        for (int i5 = i2; i5 < i2 + i3; i5++) {
            i4 = (i4 * 31) + bArr[i5];
        }
        return i4;
    }

    public static <T> T zzc(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static int zzdc(boolean z) {
        return z ? 1231 : 1237;
    }

    public static int zzdd(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public static boolean zzg(zzffi zzffiVar) {
        return false;
    }
}
