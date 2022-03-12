package com.google.android.gms.internal;

import android.os.SystemClock;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzag implements zzb {
    private final Map<String, zzah> zzbt;
    private long zzbu;
    private final File zzbv;
    private final int zzbw;

    public zzag(File file) {
        this(file, 5242880);
    }

    private zzag(File file, int i) {
        this.zzbt = new LinkedHashMap(16, 0.75f, true);
        this.zzbu = 0L;
        this.zzbv = file;
        this.zzbw = 5242880;
    }

    private final synchronized void remove(String str) {
        boolean delete = zze(str).delete();
        removeEntry(str);
        if (!delete) {
            zzab.zzb("Could not delete cache entry for key=%s, filename=%s", str, zzd(str));
        }
    }

    private final void removeEntry(String str) {
        zzah remove = this.zzbt.remove(str);
        if (remove != null) {
            this.zzbu -= remove.zzbx;
        }
    }

    private static int zza(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    private static InputStream zza(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public static String zza(zzai zzaiVar) throws IOException {
        return new String(zza(zzaiVar, zzc(zzaiVar)), "UTF-8");
    }

    public static void zza(OutputStream outputStream, int i) throws IOException {
        outputStream.write(i & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write(i >>> 24);
    }

    public static void zza(OutputStream outputStream, long j) throws IOException {
        outputStream.write((byte) j);
        outputStream.write((byte) (j >>> 8));
        outputStream.write((byte) (j >>> 16));
        outputStream.write((byte) (j >>> 24));
        outputStream.write((byte) (j >>> 32));
        outputStream.write((byte) (j >>> 40));
        outputStream.write((byte) (j >>> 48));
        outputStream.write((byte) (j >>> 56));
    }

    public static void zza(OutputStream outputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        zza(outputStream, bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    private final void zza(String str, zzah zzahVar) {
        if (!this.zzbt.containsKey(str)) {
            this.zzbu += zzahVar.zzbx;
        } else {
            this.zzbu += zzahVar.zzbx - this.zzbt.get(str).zzbx;
        }
        this.zzbt.put(str, zzahVar);
    }

    private static byte[] zza(zzai zzaiVar, long j) throws IOException {
        long zzn = zzaiVar.zzn();
        if (j >= 0 && j <= zzn) {
            int i = (int) j;
            if (i == j) {
                byte[] bArr = new byte[i];
                new DataInputStream(zzaiVar).readFully(bArr);
                return bArr;
            }
        }
        StringBuilder sb = new StringBuilder(73);
        sb.append("streamToBytes length=");
        sb.append(j);
        sb.append(", maxLength=");
        sb.append(zzn);
        throw new IOException(sb.toString());
    }

    public static int zzb(InputStream inputStream) throws IOException {
        return (zza(inputStream) << 24) | zza(inputStream) | 0 | (zza(inputStream) << 8) | (zza(inputStream) << 16);
    }

    public static Map<String, String> zzb(zzai zzaiVar) throws IOException {
        int zzb = zzb((InputStream) zzaiVar);
        Map<String, String> emptyMap = zzb == 0 ? Collections.emptyMap() : new HashMap<>(zzb);
        for (int i = 0; i < zzb; i++) {
            emptyMap.put(zza(zzaiVar).intern(), zza(zzaiVar).intern());
        }
        return emptyMap;
    }

    public static long zzc(InputStream inputStream) throws IOException {
        return (zza(inputStream) & 255) | 0 | ((zza(inputStream) & 255) << 8) | ((zza(inputStream) & 255) << 16) | ((zza(inputStream) & 255) << 24) | ((zza(inputStream) & 255) << 32) | ((zza(inputStream) & 255) << 40) | ((zza(inputStream) & 255) << 48) | ((zza(inputStream) & 255) << 56);
    }

    private static String zzd(String str) {
        int length = str.length() / 2;
        String valueOf = String.valueOf(String.valueOf(str.substring(0, length).hashCode()));
        String valueOf2 = String.valueOf(String.valueOf(str.substring(length).hashCode()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private final File zze(String str) {
        return new File(this.zzbv, zzd(str));
    }

    @Override // com.google.android.gms.internal.zzb
    public final synchronized void initialize() {
        long length;
        zzai zzaiVar;
        if (!this.zzbv.exists()) {
            if (!this.zzbv.mkdirs()) {
                zzab.zzc("Unable to create cache dir %s", this.zzbv.getAbsolutePath());
            }
            return;
        }
        File[] listFiles = this.zzbv.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                try {
                    length = file.length();
                    zzaiVar = new zzai(new BufferedInputStream(zza(file)), length);
                } catch (IOException unused) {
                    file.delete();
                }
                try {
                    zzah zzc = zzah.zzc(zzaiVar);
                    zzc.zzbx = length;
                    zza(zzc.key, zzc);
                    zzaiVar.close();
                } catch (Throwable th) {
                    zzaiVar.close();
                    throw th;
                    break;
                }
            }
        }
    }

    @Override // com.google.android.gms.internal.zzb
    public final synchronized zzc zza(String str) {
        zzah zzahVar = this.zzbt.get(str);
        if (zzahVar == null) {
            return null;
        }
        File zze = zze(str);
        try {
            zzai zzaiVar = new zzai(new BufferedInputStream(zza(zze)), zze.length());
            try {
                zzah zzc = zzah.zzc(zzaiVar);
                if (!TextUtils.equals(str, zzc.key)) {
                    zzab.zzb("%s: key=%s, found=%s", zze.getAbsolutePath(), str, zzc.key);
                    removeEntry(str);
                    return null;
                }
                byte[] zza = zza(zzaiVar, zzaiVar.zzn());
                zzc zzcVar = new zzc();
                zzcVar.data = zza;
                zzcVar.zza = zzahVar.zza;
                zzcVar.zzb = zzahVar.zzb;
                zzcVar.zzc = zzahVar.zzc;
                zzcVar.zzd = zzahVar.zzd;
                zzcVar.zze = zzahVar.zze;
                zzcVar.zzf = zzahVar.zzf;
                return zzcVar;
            } finally {
                zzaiVar.close();
            }
        } catch (IOException e) {
            zzab.zzb("%s: %s", zze.getAbsolutePath(), e.toString());
            remove(str);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.zzb
    public final synchronized void zza(String str, zzc zzcVar) {
        long j;
        Iterator<Map.Entry<String, zzah>> it;
        long length = zzcVar.data.length;
        if (this.zzbu + length >= this.zzbw) {
            if (zzab.DEBUG) {
                zzab.zza("Pruning old cache entries.", new Object[0]);
            }
            long j2 = this.zzbu;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Iterator<Map.Entry<String, zzah>> it2 = this.zzbt.entrySet().iterator();
            int i = 0;
            while (true) {
                if (!it2.hasNext()) {
                    j = elapsedRealtime;
                    break;
                }
                zzah value = it2.next().getValue();
                if (zze(value.key).delete()) {
                    it = it2;
                    j = elapsedRealtime;
                    this.zzbu -= value.zzbx;
                } else {
                    it = it2;
                    j = elapsedRealtime;
                    zzab.zzb("Could not delete cache entry for key=%s, filename=%s", value.key, zzd(value.key));
                }
                it.remove();
                i++;
                if (((float) (this.zzbu + length)) < this.zzbw * 0.9f) {
                    break;
                }
                it2 = it;
                elapsedRealtime = j;
            }
            if (zzab.DEBUG) {
                zzab.zza("pruned %d files, %d bytes, %d ms", Integer.valueOf(i), Long.valueOf(this.zzbu - j2), Long.valueOf(SystemClock.elapsedRealtime() - j));
            }
        }
        File zze = zze(str);
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(zze));
            zzah zzahVar = new zzah(str, zzcVar);
            if (!zzahVar.zza(bufferedOutputStream)) {
                bufferedOutputStream.close();
                zzab.zzb("Failed to write header for %s", zze.getAbsolutePath());
                throw new IOException();
            }
            bufferedOutputStream.write(zzcVar.data);
            bufferedOutputStream.close();
            zza(str, zzahVar);
        } catch (IOException unused) {
            if (!zze.delete()) {
                zzab.zzb("Could not clean up file %s", zze.getAbsolutePath());
            }
        }
    }
}
