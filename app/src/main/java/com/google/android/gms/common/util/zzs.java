package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* loaded from: classes.dex */
public final class zzs {
    private static String zzgbz;
    private static final int zzgca = Process.myPid();

    public static String zzamc() {
        if (zzgbz == null) {
            zzgbz = zzcj(zzgca);
        }
        return zzgbz;
    }

    private static String zzcj(int i) {
        BufferedReader bufferedReader;
        Throwable th;
        StrictMode.ThreadPolicy allowThreadDiskReads;
        BufferedReader bufferedReader2 = null;
        if (i <= 0) {
            return null;
        }
        try {
            allowThreadDiskReads = StrictMode.allowThreadDiskReads();
            StringBuilder sb = new StringBuilder(25);
            sb.append("/proc/");
            sb.append(i);
            sb.append("/cmdline");
            bufferedReader = new BufferedReader(new FileReader(sb.toString()));
        } catch (IOException unused) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            String trim = bufferedReader.readLine().trim();
            zzn.closeQuietly(bufferedReader);
            return trim;
        } catch (IOException unused2) {
            zzn.closeQuietly(bufferedReader);
            return null;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader2 = bufferedReader;
            zzn.closeQuietly(bufferedReader2);
            throw th;
        }
    }
}
