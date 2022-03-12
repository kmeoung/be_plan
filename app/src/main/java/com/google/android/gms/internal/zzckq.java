package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzckq extends zzfhe<zzckq> {
    private static volatile zzckq[] zzjhg;
    public zzckt zzjhh = null;
    public zzckr zzjhi = null;
    public Boolean zzjhj = null;
    public String zzjhk = null;

    public zzckq() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzckq[] zzbaq() {
        if (zzjhg == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjhg == null) {
                    zzjhg = new zzckq[0];
                }
            }
        }
        return zzjhg;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzckq)) {
            return false;
        }
        zzckq zzckqVar = (zzckq) obj;
        if (this.zzjhh == null) {
            if (zzckqVar.zzjhh != null) {
                return false;
            }
        } else if (!this.zzjhh.equals(zzckqVar.zzjhh)) {
            return false;
        }
        if (this.zzjhi == null) {
            if (zzckqVar.zzjhi != null) {
                return false;
            }
        } else if (!this.zzjhi.equals(zzckqVar.zzjhi)) {
            return false;
        }
        if (this.zzjhj == null) {
            if (zzckqVar.zzjhj != null) {
                return false;
            }
        } else if (!this.zzjhj.equals(zzckqVar.zzjhj)) {
            return false;
        }
        if (this.zzjhk == null) {
            if (zzckqVar.zzjhk != null) {
                return false;
            }
        } else if (!this.zzjhk.equals(zzckqVar.zzjhk)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzckqVar.zzpgy == null || zzckqVar.zzpgy.isEmpty() : this.zzpgy.equals(zzckqVar.zzpgy);
    }

    public final int hashCode() {
        zzckt zzcktVar = this.zzjhh;
        int i = 0;
        int hashCode = ((getClass().getName().hashCode() + 527) * 31) + (zzcktVar == null ? 0 : zzcktVar.hashCode());
        zzckr zzckrVar = this.zzjhi;
        int hashCode2 = ((((((hashCode * 31) + (zzckrVar == null ? 0 : zzckrVar.hashCode())) * 31) + (this.zzjhj == null ? 0 : this.zzjhj.hashCode())) * 31) + (this.zzjhk == null ? 0 : this.zzjhk.hashCode())) * 31;
        if (this.zzpgy != null && !this.zzpgy.isEmpty()) {
            i = this.zzpgy.hashCode();
        }
        return hashCode2 + i;
    }

    @Override // com.google.android.gms.internal.zzfhk
    public final /* synthetic */ zzfhk zza(zzfhb zzfhbVar) throws IOException {
        zzfhk zzfhkVar;
        while (true) {
            int zzcts = zzfhbVar.zzcts();
            if (zzcts == 0) {
                return this;
            }
            if (zzcts == 10) {
                if (this.zzjhh == null) {
                    this.zzjhh = new zzckt();
                }
                zzfhkVar = this.zzjhh;
            } else if (zzcts == 18) {
                if (this.zzjhi == null) {
                    this.zzjhi = new zzckr();
                }
                zzfhkVar = this.zzjhi;
            } else if (zzcts == 24) {
                this.zzjhj = Boolean.valueOf(zzfhbVar.zzcty());
            } else if (zzcts == 34) {
                this.zzjhk = zzfhbVar.readString();
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
            zzfhbVar.zza(zzfhkVar);
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjhh != null) {
            zzfhcVar.zza(1, this.zzjhh);
        }
        if (this.zzjhi != null) {
            zzfhcVar.zza(2, this.zzjhi);
        }
        if (this.zzjhj != null) {
            zzfhcVar.zzl(3, this.zzjhj.booleanValue());
        }
        if (this.zzjhk != null) {
            zzfhcVar.zzn(4, this.zzjhk);
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjhh != null) {
            zzo += zzfhc.zzb(1, this.zzjhh);
        }
        if (this.zzjhi != null) {
            zzo += zzfhc.zzb(2, this.zzjhi);
        }
        if (this.zzjhj != null) {
            this.zzjhj.booleanValue();
            zzo += zzfhc.zzkw(3) + 1;
        }
        return this.zzjhk != null ? zzo + zzfhc.zzo(4, this.zzjhk) : zzo;
    }
}
