package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzckp extends zzfhe<zzckp> {
    private static volatile zzckp[] zzjha;
    public Integer zzjhb = null;
    public String zzjhc = null;
    public zzckq[] zzjhd = zzckq.zzbaq();
    private Boolean zzjhe = null;
    public zzckr zzjhf = null;

    public zzckp() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzckp[] zzbap() {
        if (zzjha == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjha == null) {
                    zzjha = new zzckp[0];
                }
            }
        }
        return zzjha;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzckp)) {
            return false;
        }
        zzckp zzckpVar = (zzckp) obj;
        if (this.zzjhb == null) {
            if (zzckpVar.zzjhb != null) {
                return false;
            }
        } else if (!this.zzjhb.equals(zzckpVar.zzjhb)) {
            return false;
        }
        if (this.zzjhc == null) {
            if (zzckpVar.zzjhc != null) {
                return false;
            }
        } else if (!this.zzjhc.equals(zzckpVar.zzjhc)) {
            return false;
        }
        if (!zzfhi.equals(this.zzjhd, zzckpVar.zzjhd)) {
            return false;
        }
        if (this.zzjhe == null) {
            if (zzckpVar.zzjhe != null) {
                return false;
            }
        } else if (!this.zzjhe.equals(zzckpVar.zzjhe)) {
            return false;
        }
        if (this.zzjhf == null) {
            if (zzckpVar.zzjhf != null) {
                return false;
            }
        } else if (!this.zzjhf.equals(zzckpVar.zzjhf)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzckpVar.zzpgy == null || zzckpVar.zzpgy.isEmpty() : this.zzpgy.equals(zzckpVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzjhb == null ? 0 : this.zzjhb.hashCode())) * 31) + (this.zzjhc == null ? 0 : this.zzjhc.hashCode())) * 31) + zzfhi.hashCode(this.zzjhd)) * 31) + (this.zzjhe == null ? 0 : this.zzjhe.hashCode());
        zzckr zzckrVar = this.zzjhf;
        int hashCode2 = ((hashCode * 31) + (zzckrVar == null ? 0 : zzckrVar.hashCode())) * 31;
        if (this.zzpgy != null && !this.zzpgy.isEmpty()) {
            i = this.zzpgy.hashCode();
        }
        return hashCode2 + i;
    }

    @Override // com.google.android.gms.internal.zzfhk
    public final /* synthetic */ zzfhk zza(zzfhb zzfhbVar) throws IOException {
        while (true) {
            int zzcts = zzfhbVar.zzcts();
            if (zzcts == 0) {
                return this;
            }
            if (zzcts == 8) {
                this.zzjhb = Integer.valueOf(zzfhbVar.zzcuh());
            } else if (zzcts == 18) {
                this.zzjhc = zzfhbVar.readString();
            } else if (zzcts == 26) {
                int zzb = zzfhn.zzb(zzfhbVar, 26);
                int length = this.zzjhd == null ? 0 : this.zzjhd.length;
                zzckq[] zzckqVarArr = new zzckq[zzb + length];
                if (length != 0) {
                    System.arraycopy(this.zzjhd, 0, zzckqVarArr, 0, length);
                }
                while (length < zzckqVarArr.length - 1) {
                    zzckqVarArr[length] = new zzckq();
                    zzfhbVar.zza(zzckqVarArr[length]);
                    zzfhbVar.zzcts();
                    length++;
                }
                zzckqVarArr[length] = new zzckq();
                zzfhbVar.zza(zzckqVarArr[length]);
                this.zzjhd = zzckqVarArr;
            } else if (zzcts == 32) {
                this.zzjhe = Boolean.valueOf(zzfhbVar.zzcty());
            } else if (zzcts == 42) {
                if (this.zzjhf == null) {
                    this.zzjhf = new zzckr();
                }
                zzfhbVar.zza(this.zzjhf);
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjhb != null) {
            zzfhcVar.zzaa(1, this.zzjhb.intValue());
        }
        if (this.zzjhc != null) {
            zzfhcVar.zzn(2, this.zzjhc);
        }
        if (this.zzjhd != null && this.zzjhd.length > 0) {
            for (int i = 0; i < this.zzjhd.length; i++) {
                zzckq zzckqVar = this.zzjhd[i];
                if (zzckqVar != null) {
                    zzfhcVar.zza(3, zzckqVar);
                }
            }
        }
        if (this.zzjhe != null) {
            zzfhcVar.zzl(4, this.zzjhe.booleanValue());
        }
        if (this.zzjhf != null) {
            zzfhcVar.zza(5, this.zzjhf);
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjhb != null) {
            zzo += zzfhc.zzad(1, this.zzjhb.intValue());
        }
        if (this.zzjhc != null) {
            zzo += zzfhc.zzo(2, this.zzjhc);
        }
        if (this.zzjhd != null && this.zzjhd.length > 0) {
            for (int i = 0; i < this.zzjhd.length; i++) {
                zzckq zzckqVar = this.zzjhd[i];
                if (zzckqVar != null) {
                    zzo += zzfhc.zzb(3, zzckqVar);
                }
            }
        }
        if (this.zzjhe != null) {
            this.zzjhe.booleanValue();
            zzo += zzfhc.zzkw(4) + 1;
        }
        return this.zzjhf != null ? zzo + zzfhc.zzb(5, this.zzjhf) : zzo;
    }
}
