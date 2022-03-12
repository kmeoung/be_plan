package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzfhn {
    private static int zzphh = 11;
    private static int zzphi = 12;
    private static int zzphj = 16;
    private static int zzphk = 26;
    public static final int[] zzphl = new int[0];
    public static final long[] zzphm = new long[0];
    public static final float[] zzphn = new float[0];
    private static double[] zzpho = new double[0];
    public static final boolean[] zzphp = new boolean[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final byte[][] zzphq = new byte[0];
    public static final byte[] zzphr = new byte[0];

    public static final int zzb(zzfhb zzfhbVar, int i) throws IOException {
        int position = zzfhbVar.getPosition();
        zzfhbVar.zzkg(i);
        int i2 = 1;
        while (zzfhbVar.zzcts() == i) {
            zzfhbVar.zzkg(i);
            i2++;
        }
        zzfhbVar.zzam(position, i);
        return i2;
    }
}
