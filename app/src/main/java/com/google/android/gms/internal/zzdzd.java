package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzdzd<K, V> extends zzdze<K, V> {
    public zzdzd(K k, V v) {
        super(k, v, zzdyz.zzbrq(), zzdyz.zzbrq());
    }

    public zzdzd(K k, V v, zzdza<K, V> zzdzaVar, zzdza<K, V> zzdzaVar2) {
        super(k, v, zzdzaVar, zzdzaVar2);
    }

    @Override // com.google.android.gms.internal.zzdza
    public final int size() {
        return zzbrr().size() + 1 + zzbrs().size();
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
        return new zzdzd(k, v, zzdzaVar, zzdzaVar2);
    }

    @Override // com.google.android.gms.internal.zzdze
    protected final int zzbro() {
        return zzdzb.zzmho;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final boolean zzbrp() {
        return true;
    }
}
