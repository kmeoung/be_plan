package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzdyy<K, V> extends zzdze<K, V> {
    private int size = -1;

    public zzdyy(K k, V v, zzdza<K, V> zzdzaVar, zzdza<K, V> zzdzaVar2) {
        super(k, v, zzdzaVar, zzdzaVar2);
    }

    @Override // com.google.android.gms.internal.zzdza
    public final int size() {
        if (this.size == -1) {
            this.size = zzbrr().size() + 1 + zzbrs().size();
        }
        return this.size;
    }

    @Override // com.google.android.gms.internal.zzdze
    protected final zzdze<K, V> zza(K k, V v, zzdza<K, V> zzdzaVar, zzdza<K, V> zzdzaVar2) {
        if (k == null) {
            k = getKey();
        }
        if (v == null) {
            v = getValue();
        }
        if (zzdzaVar == null) {
            zzdzaVar = zzbrr();
        }
        if (zzdzaVar2 == null) {
            zzdzaVar2 = zzbrs();
        }
        return new zzdyy(k, v, zzdzaVar, zzdzaVar2);
    }

    @Override // com.google.android.gms.internal.zzdze
    public final void zza(zzdza<K, V> zzdzaVar) {
        if (this.size != -1) {
            throw new IllegalStateException("Can't set left after using size");
        }
        super.zza(zzdzaVar);
    }

    @Override // com.google.android.gms.internal.zzdze
    protected final int zzbro() {
        return zzdzb.zzmhp;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final boolean zzbrp() {
        return false;
    }
}
