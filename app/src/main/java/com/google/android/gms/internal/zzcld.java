package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzcld extends zzfhe<zzcld> {
    private static volatile zzcld[] zzjjw;
    public Long zzjjx = null;
    public String name = null;
    public String zzfzi = null;
    public Long zzjiq = null;
    private Float zzjgp = null;
    public Double zzjgq = null;

    public zzcld() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzcld[] zzbay() {
        if (zzjjw == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjjw == null) {
                    zzjjw = new zzcld[0];
                }
            }
        }
        return zzjjw;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcld)) {
            return false;
        }
        zzcld zzcldVar = (zzcld) obj;
        if (this.zzjjx == null) {
            if (zzcldVar.zzjjx != null) {
                return false;
            }
        } else if (!this.zzjjx.equals(zzcldVar.zzjjx)) {
            return false;
        }
        if (this.name == null) {
            if (zzcldVar.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzcldVar.name)) {
            return false;
        }
        if (this.zzfzi == null) {
            if (zzcldVar.zzfzi != null) {
                return false;
            }
        } else if (!this.zzfzi.equals(zzcldVar.zzfzi)) {
            return false;
        }
        if (this.zzjiq == null) {
            if (zzcldVar.zzjiq != null) {
                return false;
            }
        } else if (!this.zzjiq.equals(zzcldVar.zzjiq)) {
            return false;
        }
        if (this.zzjgp == null) {
            if (zzcldVar.zzjgp != null) {
                return false;
            }
        } else if (!this.zzjgp.equals(zzcldVar.zzjgp)) {
            return false;
        }
        if (this.zzjgq == null) {
            if (zzcldVar.zzjgq != null) {
                return false;
            }
        } else if (!this.zzjgq.equals(zzcldVar.zzjgq)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzcldVar.zzpgy == null || zzcldVar.zzpgy.isEmpty() : this.zzpgy.equals(zzcldVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzjjx == null ? 0 : this.zzjjx.hashCode())) * 31) + (this.name == null ? 0 : this.name.hashCode())) * 31) + (this.zzfzi == null ? 0 : this.zzfzi.hashCode())) * 31) + (this.zzjiq == null ? 0 : this.zzjiq.hashCode())) * 31) + (this.zzjgp == null ? 0 : this.zzjgp.hashCode())) * 31) + (this.zzjgq == null ? 0 : this.zzjgq.hashCode())) * 31;
        if (this.zzpgy != null && !this.zzpgy.isEmpty()) {
            i = this.zzpgy.hashCode();
        }
        return hashCode + i;
    }

    @Override // com.google.android.gms.internal.zzfhk
    public final /* synthetic */ zzfhk zza(zzfhb zzfhbVar) throws IOException {
        while (true) {
            int zzcts = zzfhbVar.zzcts();
            if (zzcts == 0) {
                return this;
            }
            if (zzcts == 8) {
                this.zzjjx = Long.valueOf(zzfhbVar.zzcum());
            } else if (zzcts == 18) {
                this.name = zzfhbVar.readString();
            } else if (zzcts == 26) {
                this.zzfzi = zzfhbVar.readString();
            } else if (zzcts == 32) {
                this.zzjiq = Long.valueOf(zzfhbVar.zzcum());
            } else if (zzcts == 45) {
                this.zzjgp = Float.valueOf(Float.intBitsToFloat(zzfhbVar.zzcun()));
            } else if (zzcts == 49) {
                this.zzjgq = Double.valueOf(Double.longBitsToDouble(zzfhbVar.zzcuo()));
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjjx != null) {
            zzfhcVar.zzf(1, this.zzjjx.longValue());
        }
        if (this.name != null) {
            zzfhcVar.zzn(2, this.name);
        }
        if (this.zzfzi != null) {
            zzfhcVar.zzn(3, this.zzfzi);
        }
        if (this.zzjiq != null) {
            zzfhcVar.zzf(4, this.zzjiq.longValue());
        }
        if (this.zzjgp != null) {
            zzfhcVar.zzc(5, this.zzjgp.floatValue());
        }
        if (this.zzjgq != null) {
            zzfhcVar.zza(6, this.zzjgq.doubleValue());
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjjx != null) {
            zzo += zzfhc.zzc(1, this.zzjjx.longValue());
        }
        if (this.name != null) {
            zzo += zzfhc.zzo(2, this.name);
        }
        if (this.zzfzi != null) {
            zzo += zzfhc.zzo(3, this.zzfzi);
        }
        if (this.zzjiq != null) {
            zzo += zzfhc.zzc(4, this.zzjiq.longValue());
        }
        if (this.zzjgp != null) {
            this.zzjgp.floatValue();
            zzo += zzfhc.zzkw(5) + 4;
        }
        if (this.zzjgq == null) {
            return zzo;
        }
        this.zzjgq.doubleValue();
        return zzo + zzfhc.zzkw(6) + 8;
    }
}
