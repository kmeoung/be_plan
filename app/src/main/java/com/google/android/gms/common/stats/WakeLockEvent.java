package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.internal.zzbem;
import java.util.List;

/* loaded from: classes.dex */
public final class WakeLockEvent extends StatsEvent {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zzd();
    private final long mTimeout;
    private int zzdzm;
    private final long zzgar;
    private int zzgas;
    private final String zzgat;
    private final String zzgau;
    private final String zzgav;
    private final int zzgaw;
    private final List<String> zzgax;
    private final String zzgay;
    private final long zzgaz;
    private int zzgba;
    private final String zzgbb;
    private final float zzgbc;
    private long zzgbd;

    public WakeLockEvent(int i, long j, int i2, String str, int i3, List<String> list, String str2, long j2, int i4, String str3, String str4, float f, long j3, String str5) {
        this.zzdzm = i;
        this.zzgar = j;
        this.zzgas = i2;
        this.zzgat = str;
        this.zzgau = str3;
        this.zzgav = str5;
        this.zzgaw = i3;
        this.zzgbd = -1L;
        this.zzgax = list;
        this.zzgay = str2;
        this.zzgaz = j2;
        this.zzgba = i4;
        this.zzgbb = str4;
        this.zzgbc = f;
        this.mTimeout = j3;
    }

    public WakeLockEvent(long j, int i, String str, int i2, List<String> list, String str2, long j2, int i3, String str3, String str4, float f, long j3, String str5) {
        this(2, j, i, str, i2, list, str2, j2, i3, str3, str4, f, j3, str5);
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final int getEventType() {
        return this.zzgas;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long getTimeMillis() {
        return this.zzgar;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbem.zze(parcel);
        zzbem.zzc(parcel, 1, this.zzdzm);
        zzbem.zza(parcel, 2, getTimeMillis());
        zzbem.zza(parcel, 4, this.zzgat, false);
        zzbem.zzc(parcel, 5, this.zzgaw);
        zzbem.zzb(parcel, 6, this.zzgax, false);
        zzbem.zza(parcel, 8, this.zzgaz);
        zzbem.zza(parcel, 10, this.zzgau, false);
        zzbem.zzc(parcel, 11, getEventType());
        zzbem.zza(parcel, 12, this.zzgay, false);
        zzbem.zza(parcel, 13, this.zzgbb, false);
        zzbem.zzc(parcel, 14, this.zzgba);
        zzbem.zza(parcel, 15, this.zzgbc);
        zzbem.zza(parcel, 16, this.mTimeout);
        zzbem.zza(parcel, 17, this.zzgav, false);
        zzbem.zzai(parcel, zze);
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long zzalr() {
        return this.zzgbd;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final String zzals() {
        String str = this.zzgat;
        int i = this.zzgaw;
        String join = this.zzgax == null ? "" : TextUtils.join(",", this.zzgax);
        int i2 = this.zzgba;
        String str2 = this.zzgau == null ? "" : this.zzgau;
        String str3 = this.zzgbb == null ? "" : this.zzgbb;
        float f = this.zzgbc;
        String str4 = this.zzgav == null ? "" : this.zzgav;
        StringBuilder sb = new StringBuilder(String.valueOf("\t").length() + 37 + String.valueOf(str).length() + String.valueOf("\t").length() + String.valueOf("\t").length() + String.valueOf(join).length() + String.valueOf("\t").length() + String.valueOf("\t").length() + String.valueOf(str2).length() + String.valueOf("\t").length() + String.valueOf(str3).length() + String.valueOf("\t").length() + String.valueOf("\t").length() + String.valueOf(str4).length());
        sb.append("\t");
        sb.append(str);
        sb.append("\t");
        sb.append(i);
        sb.append("\t");
        sb.append(join);
        sb.append("\t");
        sb.append(i2);
        sb.append("\t");
        sb.append(str2);
        sb.append("\t");
        sb.append(str3);
        sb.append("\t");
        sb.append(f);
        sb.append("\t");
        sb.append(str4);
        return sb.toString();
    }
}
