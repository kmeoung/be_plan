package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzcky extends zzfhe<zzcky> {
    private static volatile zzcky[] zzjil;
    public zzckz[] zzjim = zzckz.zzbaw();
    public String name = null;
    public Long zzjin = null;
    public Long zzjio = null;
    public Integer count = null;

    public zzcky() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzcky[] zzbav() {
        if (zzjil == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjil == null) {
                    zzjil = new zzcky[0];
                }
            }
        }
        return zzjil;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcky)) {
            return false;
        }
        zzcky zzckyVar = (zzcky) obj;
        if (!zzfhi.equals(this.zzjim, zzckyVar.zzjim)) {
            return false;
        }
        if (this.name == null) {
            if (zzckyVar.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzckyVar.name)) {
            return false;
        }
        if (this.zzjin == null) {
            if (zzckyVar.zzjin != null) {
                return false;
            }
        } else if (!this.zzjin.equals(zzckyVar.zzjin)) {
            return false;
        }
        if (this.zzjio == null) {
            if (zzckyVar.zzjio != null) {
                return false;
            }
        } else if (!this.zzjio.equals(zzckyVar.zzjio)) {
            return false;
        }
        if (this.count == null) {
            if (zzckyVar.count != null) {
                return false;
            }
        } else if (!this.count.equals(zzckyVar.count)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzckyVar.zzpgy == null || zzckyVar.zzpgy.isEmpty() : this.zzpgy.equals(zzckyVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + zzfhi.hashCode(this.zzjim)) * 31) + (this.name == null ? 0 : this.name.hashCode())) * 31) + (this.zzjin == null ? 0 : this.zzjin.hashCode())) * 31) + (this.zzjio == null ? 0 : this.zzjio.hashCode())) * 31) + (this.count == null ? 0 : this.count.hashCode())) * 31;
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
                int zzb = zzfhn.zzb(zzfhbVar, 10);
                int length = this.zzjim == null ? 0 : this.zzjim.length;
                zzckz[] zzckzVarArr = new zzckz[zzb + length];
                if (length != 0) {
                    System.arraycopy(this.zzjim, 0, zzckzVarArr, 0, length);
                }
                while (length < zzckzVarArr.length - 1) {
                    zzckzVarArr[length] = new zzckz();
                    zzfhbVar.zza(zzckzVarArr[length]);
                    zzfhbVar.zzcts();
                    length++;
                }
                zzckzVarArr[length] = new zzckz();
                zzfhbVar.zza(zzckzVarArr[length]);
                this.zzjim = zzckzVarArr;
            } else if (zzcts == 18) {
                this.name = zzfhbVar.readString();
            } else if (zzcts == 24) {
                this.zzjin = Long.valueOf(zzfhbVar.zzcum());
            } else if (zzcts == 32) {
                this.zzjio = Long.valueOf(zzfhbVar.zzcum());
            } else if (zzcts == 40) {
                this.count = Integer.valueOf(zzfhbVar.zzcuh());
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjim != null && this.zzjim.length > 0) {
            for (int i = 0; i < this.zzjim.length; i++) {
                zzckz zzckzVar = this.zzjim[i];
                if (zzckzVar != null) {
                    zzfhcVar.zza(1, zzckzVar);
                }
            }
        }
        if (this.name != null) {
            zzfhcVar.zzn(2, this.name);
        }
        if (this.zzjin != null) {
            zzfhcVar.zzf(3, this.zzjin.longValue());
        }
        if (this.zzjio != null) {
            zzfhcVar.zzf(4, this.zzjio.longValue());
        }
        if (this.count != null) {
            zzfhcVar.zzaa(5, this.count.intValue());
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjim != null && this.zzjim.length > 0) {
            for (int i = 0; i < this.zzjim.length; i++) {
                zzckz zzckzVar = this.zzjim[i];
                if (zzckzVar != null) {
                    zzo += zzfhc.zzb(1, zzckzVar);
                }
            }
        }
        if (this.name != null) {
            zzo += zzfhc.zzo(2, this.name);
        }
        if (this.zzjin != null) {
            zzo += zzfhc.zzc(3, this.zzjin.longValue());
        }
        if (this.zzjio != null) {
            zzo += zzfhc.zzc(4, this.zzjio.longValue());
        }
        return this.count != null ? zzo + zzfhc.zzad(5, this.count.intValue()) : zzo;
    }
}
