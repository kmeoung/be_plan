package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzfek implements zzfen {
    int zzpca = 0;

    @Override // com.google.android.gms.internal.zzfen
    public final double zza(boolean z, double d, boolean z2, double d2) {
        this.zzpca = (this.zzpca * 53) + zzfer.zzdd(Double.doubleToLongBits(d));
        return d;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final int zza(boolean z, int i, boolean z2, int i2) {
        this.zzpca = (this.zzpca * 53) + i;
        return i;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final long zza(boolean z, long j, boolean z2, long j2) {
        this.zzpca = (this.zzpca * 53) + zzfer.zzdd(j);
        return j;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final zzfdh zza(boolean z, zzfdh zzfdhVar, boolean z2, zzfdh zzfdhVar2) {
        this.zzpca = (this.zzpca * 53) + zzfdhVar.hashCode();
        return zzfdhVar;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final zzfeu zza(zzfeu zzfeuVar, zzfeu zzfeuVar2) {
        this.zzpca = (this.zzpca * 53) + zzfeuVar.hashCode();
        return zzfeuVar;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final <T> zzfev<T> zza(zzfev<T> zzfevVar, zzfev<T> zzfevVar2) {
        this.zzpca = (this.zzpca * 53) + zzfevVar.hashCode();
        return zzfevVar;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final <K, V> zzffh<K, V> zza(zzffh<K, V> zzffhVar, zzffh<K, V> zzffhVar2) {
        this.zzpca = (this.zzpca * 53) + zzffhVar.hashCode();
        return zzffhVar;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final <T extends zzffi> T zza(T t, T t2) {
        int i;
        if (t == null) {
            i = 37;
        } else if (t instanceof zzfee) {
            zzfee zzfeeVar = (zzfee) t;
            if (zzfeeVar.zzpaf == 0) {
                int i2 = this.zzpca;
                this.zzpca = 0;
                zzfeeVar.zza(zzfem.zzpcd, this, zzfeeVar);
                zzfeeVar.zzpbs = zza(zzfeeVar.zzpbs, zzfeeVar.zzpbs);
                zzfeeVar.zzpaf = this.zzpca;
                this.zzpca = i2;
            }
            i = zzfeeVar.zzpaf;
        } else {
            i = t.hashCode();
        }
        this.zzpca = (this.zzpca * 53) + i;
        return t;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final zzfgi zza(zzfgi zzfgiVar, zzfgi zzfgiVar2) {
        this.zzpca = (this.zzpca * 53) + zzfgiVar.hashCode();
        return zzfgiVar;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zza(boolean z, Object obj, Object obj2) {
        this.zzpca = (this.zzpca * 53) + zzfer.zzdc(((Boolean) obj).booleanValue());
        return obj;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final String zza(boolean z, String str, boolean z2, String str2) {
        this.zzpca = (this.zzpca * 53) + str.hashCode();
        return str;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final boolean zza(boolean z, boolean z2, boolean z3, boolean z4) {
        this.zzpca = (this.zzpca * 53) + zzfer.zzdc(z2);
        return z2;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzb(boolean z, Object obj, Object obj2) {
        this.zzpca = (this.zzpca * 53) + ((Integer) obj).intValue();
        return obj;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzc(boolean z, Object obj, Object obj2) {
        this.zzpca = (this.zzpca * 53) + zzfer.zzdd(Double.doubleToLongBits(((Double) obj).doubleValue()));
        return obj;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzd(boolean z, Object obj, Object obj2) {
        this.zzpca = (this.zzpca * 53) + zzfer.zzdd(((Long) obj).longValue());
        return obj;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final void zzdb(boolean z) {
        if (z) {
            throw new IllegalStateException();
        }
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zze(boolean z, Object obj, Object obj2) {
        this.zzpca = (this.zzpca * 53) + obj.hashCode();
        return obj;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzf(boolean z, Object obj, Object obj2) {
        this.zzpca = (this.zzpca * 53) + obj.hashCode();
        return obj;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzg(boolean z, Object obj, Object obj2) {
        return zza((zzffi) obj, (zzffi) obj2);
    }
}
