package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzckz extends zzfhe<zzckz> {
    private static volatile zzckz[] zzjip;
    public String name = null;
    public String zzfzi = null;
    public Long zzjiq = null;
    private Float zzjgp = null;
    public Double zzjgq = null;

    public zzckz() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzckz[] zzbaw() {
        if (zzjip == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjip == null) {
                    zzjip = new zzckz[0];
                }
            }
        }
        return zzjip;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzckz)) {
            return false;
        }
        zzckz zzckzVar = (zzckz) obj;
        if (this.name == null) {
            if (zzckzVar.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzckzVar.name)) {
            return false;
        }
        if (this.zzfzi == null) {
            if (zzckzVar.zzfzi != null) {
                return false;
            }
        } else if (!this.zzfzi.equals(zzckzVar.zzfzi)) {
            return false;
        }
        if (this.zzjiq == null) {
            if (zzckzVar.zzjiq != null) {
                return false;
            }
        } else if (!this.zzjiq.equals(zzckzVar.zzjiq)) {
            return false;
        }
        if (this.zzjgp == null) {
            if (zzckzVar.zzjgp != null) {
                return false;
            }
        } else if (!this.zzjgp.equals(zzckzVar.zzjgp)) {
            return false;
        }
        if (this.zzjgq == null) {
            if (zzckzVar.zzjgq != null) {
                return false;
            }
        } else if (!this.zzjgq.equals(zzckzVar.zzjgq)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzckzVar.zzpgy == null || zzckzVar.zzpgy.isEmpty() : this.zzpgy.equals(zzckzVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + (this.name == null ? 0 : this.name.hashCode())) * 31) + (this.zzfzi == null ? 0 : this.zzfzi.hashCode())) * 31) + (this.zzjiq == null ? 0 : this.zzjiq.hashCode())) * 31) + (this.zzjgp == null ? 0 : this.zzjgp.hashCode())) * 31) + (this.zzjgq == null ? 0 : this.zzjgq.hashCode())) * 31;
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
            if (zzcts == 10) {
                this.name = zzfhbVar.readString();
            } else if (zzcts == 18) {
                this.zzfzi = zzfhbVar.readString();
            } else if (zzcts == 24) {
                this.zzjiq = Long.valueOf(zzfhbVar.zzcum());
            } else if (zzcts == 37) {
                this.zzjgp = Float.valueOf(Float.intBitsToFloat(zzfhbVar.zzcun()));
            } else if (zzcts == 41) {
                this.zzjgq = Double.valueOf(Double.longBitsToDouble(zzfhbVar.zzcuo()));
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.name != null) {
            zzfhcVar.zzn(1, this.name);
        }
        if (this.zzfzi != null) {
            zzfhcVar.zzn(2, this.zzfzi);
        }
        if (this.zzjiq != null) {
            zzfhcVar.zzf(3, this.zzjiq.longValue());
        }
        if (this.zzjgp != null) {
            zzfhcVar.zzc(4, this.zzjgp.floatValue());
        }
        if (this.zzjgq != null) {
            zzfhcVar.zza(5, this.zzjgq.doubleValue());
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.name != null) {
            zzo += zzfhc.zzo(1, this.name);
        }
        if (this.zzfzi != null) {
            zzo += zzfhc.zzo(2, this.zzfzi);
        }
        if (this.zzjiq != null) {
            zzo += zzfhc.zzc(3, this.zzjiq.longValue());
        }
        if (this.zzjgp != null) {
            this.zzjgp.floatValue();
            zzo += zzfhc.zzkw(4) + 4;
        }
        if (this.zzjgq == null) {
            return zzo;
        }
        this.zzjgq.doubleValue();
        return zzo + zzfhc.zzkw(5) + 8;
    }
}
