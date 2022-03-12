package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzfel implements zzfen {
    public static final zzfel zzpcb = new zzfel();

    private zzfel() {
    }

    @Override // com.google.android.gms.internal.zzfen
    public final double zza(boolean z, double d, boolean z2, double d2) {
        return z2 ? d2 : d;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final int zza(boolean z, int i, boolean z2, int i2) {
        return z2 ? i2 : i;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final long zza(boolean z, long j, boolean z2, long j2) {
        return z2 ? j2 : j;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final zzfdh zza(boolean z, zzfdh zzfdhVar, boolean z2, zzfdh zzfdhVar2) {
        return z2 ? zzfdhVar2 : zzfdhVar;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final zzfeu zza(zzfeu zzfeuVar, zzfeu zzfeuVar2) {
        int size = zzfeuVar.size();
        int size2 = zzfeuVar2.size();
        if (size > 0 && size2 > 0) {
            if (!zzfeuVar.zzcth()) {
                zzfeuVar = zzfeuVar.zzlj(size2 + size);
            }
            zzfeuVar.addAll(zzfeuVar2);
        }
        return size > 0 ? zzfeuVar : zzfeuVar2;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final <T> zzfev<T> zza(zzfev<T> zzfevVar, zzfev<T> zzfevVar2) {
        int size = zzfevVar.size();
        int size2 = zzfevVar2.size();
        if (size > 0 && size2 > 0) {
            if (!zzfevVar.zzcth()) {
                zzfevVar = zzfevVar.zzln(size2 + size);
            }
            zzfevVar.addAll(zzfevVar2);
        }
        return size > 0 ? zzfevVar : zzfevVar2;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final <K, V> zzffh<K, V> zza(zzffh<K, V> zzffhVar, zzffh<K, V> zzffhVar2) {
        if (!zzffhVar2.isEmpty()) {
            if (!zzffhVar.isMutable()) {
                zzffhVar = zzffhVar.zzcwd();
            }
            zzffhVar.zza(zzffhVar2);
        }
        return zzffhVar;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final <T extends zzffi> T zza(T t, T t2) {
        return (t == null || t2 == null) ? t != null ? t : t2 : (T) t.zzcvg().zzc(t2).zzcvm();
    }

    @Override // com.google.android.gms.internal.zzfen
    public final zzfgi zza(zzfgi zzfgiVar, zzfgi zzfgiVar2) {
        return zzfgiVar2 == zzfgi.zzcwu() ? zzfgiVar : zzfgi.zzb(zzfgiVar, zzfgiVar2);
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zza(boolean z, Object obj, Object obj2) {
        return obj2;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final String zza(boolean z, String str, boolean z2, String str2) {
        return z2 ? str2 : str;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final boolean zza(boolean z, boolean z2, boolean z3, boolean z4) {
        return z3 ? z4 : z2;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzb(boolean z, Object obj, Object obj2) {
        return obj2;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzc(boolean z, Object obj, Object obj2) {
        return obj2;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzd(boolean z, Object obj, Object obj2) {
        return obj2;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final void zzdb(boolean z) {
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zze(boolean z, Object obj, Object obj2) {
        return obj2;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzf(boolean z, Object obj, Object obj2) {
        return obj2;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzg(boolean z, Object obj, Object obj2) {
        return z ? zza((zzffi) obj, (zzffi) obj2) : obj2;
    }
}
