package com.google.android.gms.internal;

import java.util.Comparator;

/* loaded from: classes.dex */
public abstract class zzdze<K, V> implements zzdza<K, V> {
    private final V value;
    private final K zzmhr;
    private zzdza<K, V> zzmhs;
    private final zzdza<K, V> zzmht;

    public zzdze(K k, V v, zzdza<K, V> zzdzaVar, zzdza<K, V> zzdzaVar2) {
        this.zzmhr = k;
        this.value = v;
        this.zzmhs = zzdzaVar == null ? zzdyz.zzbrq() : zzdzaVar;
        this.zzmht = zzdzaVar2 == null ? zzdyz.zzbrq() : zzdzaVar2;
    }

    private static int zzb(zzdza zzdzaVar) {
        return zzdzaVar.zzbrp() ? zzdzb.zzmhp : zzdzb.zzmho;
    }

    private final zzdze zzb(Object obj, Object obj2, int i, zzdza zzdzaVar, zzdza zzdzaVar2) {
        K k = this.zzmhr;
        V v = this.value;
        if (zzdzaVar == null) {
            zzdzaVar = this.zzmhs;
        }
        if (zzdzaVar2 == null) {
            zzdzaVar2 = this.zzmht;
        }
        return i == zzdzb.zzmho ? new zzdzd(k, v, zzdzaVar, zzdzaVar2) : new zzdyy(k, v, zzdzaVar, zzdzaVar2);
    }

    private final zzdza<K, V> zzbrv() {
        if (this.zzmhs.isEmpty()) {
            return zzdyz.zzbrq();
        }
        zzdze<K, V> zzbrw = (this.zzmhs.zzbrp() || this.zzmhs.zzbrr().zzbrp()) ? this : zzbrw();
        return zzbrw.zza(null, null, ((zzdze) zzbrw.zzmhs).zzbrv(), null).zzbrx();
    }

    private final zzdze<K, V> zzbrw() {
        zzdze<K, V> zzbsa = zzbsa();
        return zzbsa.zzmht.zzbrr().zzbrp() ? zzbsa.zza(null, null, null, ((zzdze) zzbsa.zzmht).zzbrz()).zzbry().zzbsa() : zzbsa;
    }

    private final zzdze<K, V> zzbrx() {
        zzdze<K, V> zzbry = (!this.zzmht.zzbrp() || this.zzmhs.zzbrp()) ? this : zzbry();
        if (zzbry.zzmhs.zzbrp() && ((zzdze) zzbry.zzmhs).zzmhs.zzbrp()) {
            zzbry = zzbry.zzbrz();
        }
        return (!zzbry.zzmhs.zzbrp() || !zzbry.zzmht.zzbrp()) ? zzbry : zzbry.zzbsa();
    }

    private final zzdze<K, V> zzbry() {
        return (zzdze) this.zzmht.zza(null, null, zzbro(), zzb(null, null, zzdzb.zzmho, null, ((zzdze) this.zzmht).zzmhs), null);
    }

    private final zzdze<K, V> zzbrz() {
        return (zzdze) this.zzmhs.zza(null, null, zzbro(), null, zzb(null, null, zzdzb.zzmho, ((zzdze) this.zzmhs).zzmht, null));
    }

    private final zzdze<K, V> zzbsa() {
        return zzb(null, null, zzb(this), this.zzmhs.zza(null, null, zzb(this.zzmhs), null, null), this.zzmht.zza(null, null, zzb(this.zzmht), null, null));
    }

    @Override // com.google.android.gms.internal.zzdza
    public final K getKey() {
        return this.zzmhr;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final V getValue() {
        return this.value;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final boolean isEmpty() {
        return false;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final /* synthetic */ zzdza zza(Object obj, Object obj2, int i, zzdza zzdzaVar, zzdza zzdzaVar2) {
        return zzb(null, null, i, zzdzaVar, zzdzaVar2);
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zza(K k, V v, Comparator<K> comparator) {
        int compare = comparator.compare(k, this.zzmhr);
        return (compare < 0 ? zza(null, null, this.zzmhs.zza(k, v, comparator), null) : compare == 0 ? zza(k, v, null, null) : zza(null, null, null, this.zzmht.zza(k, v, comparator))).zzbrx();
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zza(K k, Comparator<K> comparator) {
        zzdze<K, V> zzdzeVar;
        if (comparator.compare(k, this.zzmhr) < 0) {
            zzdze<K, V> zzbrw = (this.zzmhs.isEmpty() || this.zzmhs.zzbrp() || ((zzdze) this.zzmhs).zzmhs.zzbrp()) ? this : zzbrw();
            zzdzeVar = zzbrw.zza(null, null, zzbrw.zzmhs.zza(k, comparator), null);
        } else {
            zzdze<K, V> zzbrz = this.zzmhs.zzbrp() ? zzbrz() : this;
            if (!zzbrz.zzmht.isEmpty() && !zzbrz.zzmht.zzbrp() && !((zzdze) zzbrz.zzmht).zzmhs.zzbrp()) {
                zzbrz = zzbrz.zzbsa();
                if (zzbrz.zzmhs.zzbrr().zzbrp()) {
                    zzbrz = zzbrz.zzbrz().zzbsa();
                }
            }
            if (comparator.compare(k, zzbrz.zzmhr) == 0) {
                if (zzbrz.zzmht.isEmpty()) {
                    return zzdyz.zzbrq();
                }
                zzdza<K, V> zzbrt = zzbrz.zzmht.zzbrt();
                zzbrz = zzbrz.zza(zzbrt.getKey(), zzbrt.getValue(), null, ((zzdze) zzbrz.zzmht).zzbrv());
            }
            zzdzeVar = zzbrz.zza(null, null, null, zzbrz.zzmht.zza(k, comparator));
        }
        return zzdzeVar.zzbrx();
    }

    protected abstract zzdze<K, V> zza(K k, V v, zzdza<K, V> zzdzaVar, zzdza<K, V> zzdzaVar2);

    public void zza(zzdza<K, V> zzdzaVar) {
        this.zzmhs = zzdzaVar;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final void zza(zzdzc<K, V> zzdzcVar) {
        this.zzmhs.zza(zzdzcVar);
        zzdzcVar.zzg(this.zzmhr, this.value);
        this.zzmht.zza(zzdzcVar);
    }

    protected abstract int zzbro();

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zzbrr() {
        return this.zzmhs;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zzbrs() {
        return this.zzmht;
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zzbrt() {
        return this.zzmhs.isEmpty() ? this : this.zzmhs.zzbrt();
    }

    @Override // com.google.android.gms.internal.zzdza
    public final zzdza<K, V> zzbru() {
        return this.zzmht.isEmpty() ? this : this.zzmht.zzbru();
    }
}
