package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class zzcfu extends zzbej implements Iterable<String> {
    public static final Parcelable.Creator<zzcfu> CREATOR = new zzcfw();
    private final Bundle zzdyg;

    public zzcfu(Bundle bundle) {
        this.zzdyg = bundle;
    }

    public final Object get(String str) {
        return this.zzdyg.get(str);
    }

    public final Double getDouble(String str) {
        return Double.valueOf(this.zzdyg.getDouble(str));
    }

    public final Long getLong(String str) {
        return Long.valueOf(this.zzdyg.getLong(str));
    }

    public final String getString(String str) {
        return this.zzdyg.getString(str);
    }

    @Override // java.lang.Iterable
    public final Iterator<String> iterator() {
        return new zzcfv(this);
    }

    public final int size() {
        return this.zzdyg.size();
    }

    public final String toString() {
        return this.zzdyg.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbem.zze(parcel);
        zzbem.zza(parcel, 2, zzayl(), false);
        zzbem.zzai(parcel, zze);
    }

    public final Bundle zzayl() {
        return new Bundle(this.zzdyg);
    }
}
