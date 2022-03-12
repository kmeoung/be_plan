package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzckv extends zzfhe<zzckv> {
    public Long zzjib = null;
    public String zziux = null;
    private Integer zzjic = null;
    public zzckw[] zzjid = zzckw.zzbat();
    public zzcku[] zzjie = zzcku.zzbas();
    public zzcko[] zzjif = zzcko.zzbao();

    public zzckv() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzckv)) {
            return false;
        }
        zzckv zzckvVar = (zzckv) obj;
        if (this.zzjib == null) {
            if (zzckvVar.zzjib != null) {
                return false;
            }
        } else if (!this.zzjib.equals(zzckvVar.zzjib)) {
            return false;
        }
        if (this.zziux == null) {
            if (zzckvVar.zziux != null) {
                return false;
            }
        } else if (!this.zziux.equals(zzckvVar.zziux)) {
            return false;
        }
        if (this.zzjic == null) {
            if (zzckvVar.zzjic != null) {
                return false;
            }
        } else if (!this.zzjic.equals(zzckvVar.zzjic)) {
            return false;
        }
        if (zzfhi.equals(this.zzjid, zzckvVar.zzjid) && zzfhi.equals(this.zzjie, zzckvVar.zzjie) && zzfhi.equals(this.zzjif, zzckvVar.zzjif)) {
            return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzckvVar.zzpgy == null || zzckvVar.zzpgy.isEmpty() : this.zzpgy.equals(zzckvVar.zzpgy);
        }
        return false;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzjib == null ? 0 : this.zzjib.hashCode())) * 31) + (this.zziux == null ? 0 : this.zziux.hashCode())) * 31) + (this.zzjic == null ? 0 : this.zzjic.hashCode())) * 31) + zzfhi.hashCode(this.zzjid)) * 31) + zzfhi.hashCode(this.zzjie)) * 31) + zzfhi.hashCode(this.zzjif)) * 31;
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
                this.zzjib = Long.valueOf(zzfhbVar.zzcum());
            } else if (zzcts == 18) {
                this.zziux = zzfhbVar.readString();
            } else if (zzcts == 24) {
                this.zzjic = Integer.valueOf(zzfhbVar.zzcuh());
            } else if (zzcts == 34) {
                int zzb = zzfhn.zzb(zzfhbVar, 34);
                int length = this.zzjid == null ? 0 : this.zzjid.length;
                zzckw[] zzckwVarArr = new zzckw[zzb + length];
                if (length != 0) {
                    System.arraycopy(this.zzjid, 0, zzckwVarArr, 0, length);
                }
                while (length < zzckwVarArr.length - 1) {
                    zzckwVarArr[length] = new zzckw();
                    zzfhbVar.zza(zzckwVarArr[length]);
                    zzfhbVar.zzcts();
                    length++;
                }
                zzckwVarArr[length] = new zzckw();
                zzfhbVar.zza(zzckwVarArr[length]);
                this.zzjid = zzckwVarArr;
            } else if (zzcts == 42) {
                int zzb2 = zzfhn.zzb(zzfhbVar, 42);
                int length2 = this.zzjie == null ? 0 : this.zzjie.length;
                zzcku[] zzckuVarArr = new zzcku[zzb2 + length2];
                if (length2 != 0) {
                    System.arraycopy(this.zzjie, 0, zzckuVarArr, 0, length2);
                }
                while (length2 < zzckuVarArr.length - 1) {
                    zzckuVarArr[length2] = new zzcku();
                    zzfhbVar.zza(zzckuVarArr[length2]);
                    zzfhbVar.zzcts();
                    length2++;
                }
                zzckuVarArr[length2] = new zzcku();
                zzfhbVar.zza(zzckuVarArr[length2]);
                this.zzjie = zzckuVarArr;
            } else if (zzcts == 50) {
                int zzb3 = zzfhn.zzb(zzfhbVar, 50);
                int length3 = this.zzjif == null ? 0 : this.zzjif.length;
                zzcko[] zzckoVarArr = new zzcko[zzb3 + length3];
                if (length3 != 0) {
                    System.arraycopy(this.zzjif, 0, zzckoVarArr, 0, length3);
                }
                while (length3 < zzckoVarArr.length - 1) {
                    zzckoVarArr[length3] = new zzcko();
                    zzfhbVar.zza(zzckoVarArr[length3]);
                    zzfhbVar.zzcts();
                    length3++;
                }
                zzckoVarArr[length3] = new zzcko();
                zzfhbVar.zza(zzckoVarArr[length3]);
                this.zzjif = zzckoVarArr;
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjib != null) {
            zzfhcVar.zzf(1, this.zzjib.longValue());
        }
        if (this.zziux != null) {
            zzfhcVar.zzn(2, this.zziux);
        }
        if (this.zzjic != null) {
            zzfhcVar.zzaa(3, this.zzjic.intValue());
        }
        if (this.zzjid != null && this.zzjid.length > 0) {
            for (int i = 0; i < this.zzjid.length; i++) {
                zzckw zzckwVar = this.zzjid[i];
                if (zzckwVar != null) {
                    zzfhcVar.zza(4, zzckwVar);
                }
            }
        }
        if (this.zzjie != null && this.zzjie.length > 0) {
            for (int i2 = 0; i2 < this.zzjie.length; i2++) {
                zzcku zzckuVar = this.zzjie[i2];
                if (zzckuVar != null) {
                    zzfhcVar.zza(5, zzckuVar);
                }
            }
        }
        if (this.zzjif != null && this.zzjif.length > 0) {
            for (int i3 = 0; i3 < this.zzjif.length; i3++) {
                zzcko zzckoVar = this.zzjif[i3];
                if (zzckoVar != null) {
                    zzfhcVar.zza(6, zzckoVar);
                }
            }
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjib != null) {
            zzo += zzfhc.zzc(1, this.zzjib.longValue());
        }
        if (this.zziux != null) {
            zzo += zzfhc.zzo(2, this.zziux);
        }
        if (this.zzjic != null) {
            zzo += zzfhc.zzad(3, this.zzjic.intValue());
        }
        if (this.zzjid != null && this.zzjid.length > 0) {
            int i = zzo;
            for (int i2 = 0; i2 < this.zzjid.length; i2++) {
                zzckw zzckwVar = this.zzjid[i2];
                if (zzckwVar != null) {
                    i += zzfhc.zzb(4, zzckwVar);
                }
            }
            zzo = i;
        }
        if (this.zzjie != null && this.zzjie.length > 0) {
            int i3 = zzo;
            for (int i4 = 0; i4 < this.zzjie.length; i4++) {
                zzcku zzckuVar = this.zzjie[i4];
                if (zzckuVar != null) {
                    i3 += zzfhc.zzb(5, zzckuVar);
                }
            }
            zzo = i3;
        }
        if (this.zzjif != null && this.zzjif.length > 0) {
            for (int i5 = 0; i5 < this.zzjif.length; i5++) {
                zzcko zzckoVar = this.zzjif[i5];
                if (zzckoVar != null) {
                    zzo += zzfhc.zzb(6, zzckoVar);
                }
            }
        }
        return zzo;
    }
}
