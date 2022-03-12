package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhe;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class zzfhf<M extends zzfhe<M>, T> {
    public final int tag;
    private int type;
    protected final Class<T> zznan;
    private zzfee<?, ?> zzpbu;
    protected final boolean zzpgz;

    private zzfhf(int i, Class<T> cls, int i2, boolean z) {
        this(11, cls, null, i2, false);
    }

    private zzfhf(int i, Class<T> cls, zzfee<?, ?> zzfeeVar, int i2, boolean z) {
        this.type = i;
        this.zznan = cls;
        this.tag = i2;
        this.zzpgz = false;
        this.zzpbu = null;
    }

    public static <M extends zzfhe<M>, T extends zzfhk> zzfhf<M, T> zza(int i, Class<T> cls, long j) {
        return new zzfhf<>(11, cls, (int) j, false);
    }

    private final Object zzan(zzfhb zzfhbVar) {
        Class componentType = this.zzpgz ? this.zznan.getComponentType() : this.zznan;
        try {
            switch (this.type) {
                case 10:
                    zzfhk zzfhkVar = (zzfhk) componentType.newInstance();
                    zzfhbVar.zza(zzfhkVar, this.tag >>> 3);
                    return zzfhkVar;
                case 11:
                    zzfhk zzfhkVar2 = (zzfhk) componentType.newInstance();
                    zzfhbVar.zza(zzfhkVar2);
                    return zzfhkVar2;
                default:
                    int i = this.type;
                    StringBuilder sb = new StringBuilder(24);
                    sb.append("Unknown type ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading extension field", e);
        } catch (IllegalAccessException e2) {
            String valueOf = String.valueOf(componentType);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 33);
            sb2.append("Error creating instance of class ");
            sb2.append(valueOf);
            throw new IllegalArgumentException(sb2.toString(), e2);
        } catch (InstantiationException e3) {
            String valueOf2 = String.valueOf(componentType);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 33);
            sb3.append("Error creating instance of class ");
            sb3.append(valueOf2);
            throw new IllegalArgumentException(sb3.toString(), e3);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfhf)) {
            return false;
        }
        zzfhf zzfhfVar = (zzfhf) obj;
        return this.type == zzfhfVar.type && this.zznan == zzfhfVar.zznan && this.tag == zzfhfVar.tag && this.zzpgz == zzfhfVar.zzpgz;
    }

    public final int hashCode() {
        return ((((((this.type + 1147) * 31) + this.zznan.hashCode()) * 31) + this.tag) * 31) + (this.zzpgz ? 1 : 0);
    }

    public final void zza(Object obj, zzfhc zzfhcVar) {
        try {
            zzfhcVar.zzlx(this.tag);
            switch (this.type) {
                case 10:
                    ((zzfhk) obj).zza(zzfhcVar);
                    zzfhcVar.zzz(this.tag >>> 3, 4);
                    return;
                case 11:
                    zzfhcVar.zzb((zzfhk) obj);
                    return;
                default:
                    int i = this.type;
                    StringBuilder sb = new StringBuilder(24);
                    sb.append("Unknown type ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public final T zzbp(List<zzfhm> list) {
        if (list == null) {
            return null;
        }
        if (this.zzpgz) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                zzfhm zzfhmVar = list.get(i);
                if (zzfhmVar.zzjkl.length != 0) {
                    arrayList.add(zzan(zzfhb.zzbd(zzfhmVar.zzjkl)));
                }
            }
            int size = arrayList.size();
            if (size == 0) {
                return null;
            }
            T cast = this.zznan.cast(Array.newInstance(this.zznan.getComponentType(), size));
            for (int i2 = 0; i2 < size; i2++) {
                Array.set(cast, i2, arrayList.get(i2));
            }
            return cast;
        } else if (list.isEmpty()) {
            return null;
        } else {
            return this.zznan.cast(zzan(zzfhb.zzbd(list.get(list.size() - 1).zzjkl)));
        }
    }

    public final int zzcn(Object obj) {
        int i = this.tag >>> 3;
        switch (this.type) {
            case 10:
                return (zzfhc.zzkw(i) << 1) + ((zzfhk) obj).zzhl();
            case 11:
                return zzfhc.zzb(i, (zzfhk) obj);
            default:
                int i2 = this.type;
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
        }
    }
}
