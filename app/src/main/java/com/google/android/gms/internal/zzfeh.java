package com.google.android.gms.internal;

/* loaded from: classes.dex */
final class zzfeh implements zzfen {
    static final zzfeh zzpbx = new zzfeh();
    private static zzfei zzpby = new zzfei();

    private zzfeh() {
    }

    @Override // com.google.android.gms.internal.zzfen
    public final double zza(boolean z, double d, boolean z2, double d2) {
        if (z == z2 && d == d2) {
            return d;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final int zza(boolean z, int i, boolean z2, int i2) {
        if (z == z2 && i == i2) {
            return i;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final long zza(boolean z, long j, boolean z2, long j2) {
        if (z == z2 && j == j2) {
            return j;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final zzfdh zza(boolean z, zzfdh zzfdhVar, boolean z2, zzfdh zzfdhVar2) {
        if (z == z2 && zzfdhVar.equals(zzfdhVar2)) {
            return zzfdhVar;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final zzfeu zza(zzfeu zzfeuVar, zzfeu zzfeuVar2) {
        if (zzfeuVar.equals(zzfeuVar2)) {
            return zzfeuVar;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final <T> zzfev<T> zza(zzfev<T> zzfevVar, zzfev<T> zzfevVar2) {
        if (zzfevVar.equals(zzfevVar2)) {
            return zzfevVar;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final <K, V> zzffh<K, V> zza(zzffh<K, V> zzffhVar, zzffh<K, V> zzffhVar2) {
        if (zzffhVar.equals(zzffhVar2)) {
            return zzffhVar;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final <T extends zzffi> T zza(T t, T t2) {
        if (t == null && t2 == null) {
            return null;
        }
        if (t == null || t2 == null) {
            throw zzpby;
        }
        zzfee zzfeeVar = (zzfee) t;
        if (zzfeeVar != t2 && ((zzfee) zzfeeVar.zza(zzfem.zzpci, (Object) null, (Object) null)).getClass().isInstance(t2)) {
            zzfee zzfeeVar2 = (zzfee) t2;
            zzfeeVar.zza(zzfem.zzpcd, this, zzfeeVar2);
            zzfeeVar.zzpbs = zza(zzfeeVar.zzpbs, zzfeeVar2.zzpbs);
        }
        return t;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final zzfgi zza(zzfgi zzfgiVar, zzfgi zzfgiVar2) {
        if (zzfgiVar.equals(zzfgiVar2)) {
            return zzfgiVar;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zza(boolean z, Object obj, Object obj2) {
        if (z && obj.equals(obj2)) {
            return obj;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final String zza(boolean z, String str, boolean z2, String str2) {
        if (z == z2 && str.equals(str2)) {
            return str;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final boolean zza(boolean z, boolean z2, boolean z3, boolean z4) {
        if (z == z3 && z2 == z4) {
            return z2;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzb(boolean z, Object obj, Object obj2) {
        if (z && obj.equals(obj2)) {
            return obj;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzc(boolean z, Object obj, Object obj2) {
        if (z && obj.equals(obj2)) {
            return obj;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzd(boolean z, Object obj, Object obj2) {
        if (z && obj.equals(obj2)) {
            return obj;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final void zzdb(boolean z) {
        if (z) {
            throw zzpby;
        }
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zze(boolean z, Object obj, Object obj2) {
        if (z && obj.equals(obj2)) {
            return obj;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzf(boolean z, Object obj, Object obj2) {
        if (z && obj.equals(obj2)) {
            return obj;
        }
        throw zzpby;
    }

    @Override // com.google.android.gms.internal.zzfen
    public final Object zzg(boolean z, Object obj, Object obj2) {
        if (z) {
            zzfee zzfeeVar = (zzfee) obj;
            zzffi zzffiVar = (zzffi) obj2;
            boolean z2 = true;
            if (zzfeeVar != zzffiVar) {
                if (!((zzfee) zzfeeVar.zza(zzfem.zzpci, (Object) null, (Object) null)).getClass().isInstance(zzffiVar)) {
                    z2 = false;
                } else {
                    zzfee zzfeeVar2 = (zzfee) zzffiVar;
                    zzfeeVar.zza(zzfem.zzpcd, this, zzfeeVar2);
                    zzfeeVar.zzpbs = zza(zzfeeVar.zzpbs, zzfeeVar2.zzpbs);
                }
            }
            if (z2) {
                return obj;
            }
        }
        throw zzpby;
    }
}
