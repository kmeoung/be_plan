package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzah {
    final String key;
    final String zza;
    final long zzb;
    long zzbx;
    final long zzc;
    final long zzd;
    final long zze;
    final Map<String, String> zzf;

    public zzah(String str, zzc zzcVar) {
        this(str, zzcVar.zza, zzcVar.zzb, zzcVar.zzc, zzcVar.zzd, zzcVar.zze, zzcVar.zzf);
        this.zzbx = zzcVar.data.length;
    }

    private zzah(String str, String str2, long j, long j2, long j3, long j4, Map<String, String> map) {
        this.key = str;
        this.zza = "".equals(str2) ? null : str2;
        this.zzb = j;
        this.zzc = j2;
        this.zzd = j3;
        this.zze = j4;
        this.zzf = map;
    }

    public static zzah zzc(zzai zzaiVar) throws IOException {
        if (zzag.zzb((InputStream) zzaiVar) == 538247942) {
            return new zzah(zzag.zza(zzaiVar), zzag.zza(zzaiVar), zzag.zzc(zzaiVar), zzag.zzc(zzaiVar), zzag.zzc(zzaiVar), zzag.zzc(zzaiVar), zzag.zzb(zzaiVar));
        }
        throw new IOException();
    }

    public final boolean zza(OutputStream outputStream) {
        try {
            zzag.zza(outputStream, 538247942);
            zzag.zza(outputStream, this.key);
            zzag.zza(outputStream, this.zza == null ? "" : this.zza);
            zzag.zza(outputStream, this.zzb);
            zzag.zza(outputStream, this.zzc);
            zzag.zza(outputStream, this.zzd);
            zzag.zza(outputStream, this.zze);
            Map<String, String> map = this.zzf;
            if (map != null) {
                zzag.zza(outputStream, map.size());
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    zzag.zza(outputStream, entry.getKey());
                    zzag.zza(outputStream, entry.getValue());
                }
            } else {
                zzag.zza(outputStream, 0);
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            zzab.zzb("%s", e.toString());
            return false;
        }
    }
}
