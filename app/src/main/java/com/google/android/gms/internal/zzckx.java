package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzckx extends zzfhe<zzckx> {
    private static volatile zzckx[] zzjih;
    public Integer zzjgx = null;
    public zzclc zzjii = null;
    public zzclc zzjij = null;
    public Boolean zzjik = null;

    public zzckx() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzckx[] zzbau() {
        if (zzjih == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjih == null) {
                    zzjih = new zzckx[0];
                }
            }
        }
        return zzjih;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzckx)) {
            return false;
        }
        zzckx zzckxVar = (zzckx) obj;
        if (this.zzjgx == null) {
            if (zzckxVar.zzjgx != null) {
                return false;
            }
        } else if (!this.zzjgx.equals(zzckxVar.zzjgx)) {
            return false;
        }
        if (this.zzjii == null) {
            if (zzckxVar.zzjii != null) {
                return false;
            }
        } else if (!this.zzjii.equals(zzckxVar.zzjii)) {
            return false;
        }
        if (this.zzjij == null) {
            if (zzckxVar.zzjij != null) {
                return false;
            }
        } else if (!this.zzjij.equals(zzckxVar.zzjij)) {
            return false;
        }
        if (this.zzjik == null) {
            if (zzckxVar.zzjik != null) {
                return false;
            }
        } else if (!this.zzjik.equals(zzckxVar.zzjik)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzckxVar.zzpgy == null || zzckxVar.zzpgy.isEmpty() : this.zzpgy.equals(zzckxVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((getClass().getName().hashCode() + 527) * 31) + (this.zzjgx == null ? 0 : this.zzjgx.hashCode());
        zzclc zzclcVar = this.zzjii;
        int hashCode2 = (hashCode * 31) + (zzclcVar == null ? 0 : zzclcVar.hashCode());
        zzclc zzclcVar2 = this.zzjij;
        int hashCode3 = ((((hashCode2 * 31) + (zzclcVar2 == null ? 0 : zzclcVar2.hashCode())) * 31) + (this.zzjik == null ? 0 : this.zzjik.hashCode())) * 31;
        if (this.zzpgy != null && !this.zzpgy.isEmpty()) {
            i = this.zzpgy.hashCode();
        }
        return hashCode3 + i;
    }

    @Override // com.google.android.gms.internal.zzfhk
    public final /* synthetic */ zzfhk zza(zzfhb zzfhbVar) throws IOException {
        zzclc zzclcVar;
        while (true) {
            int zzcts = zzfhbVar.zzcts();
            if (zzcts == 0) {
                return this;
            }
            if (zzcts != 8) {
                if (zzcts == 18) {
                    if (this.zzjii == null) {
                        this.zzjii = new zzclc();
                    }
                    zzclcVar = this.zzjii;
                } else if (zzcts == 26) {
                    if (this.zzjij == null) {
                        this.zzjij = new zzclc();
                    }
                    zzclcVar = this.zzjij;
                } else if (zzcts == 32) {
                    this.zzjik = Boolean.valueOf(zzfhbVar.zzcty());
                } else if (!super.zza(zzfhbVar, zzcts)) {
                    return this;
                }
                zzfhbVar.zza(zzclcVar);
            } else {
                this.zzjgx = Integer.valueOf(zzfhbVar.zzcuh());
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjgx != null) {
            zzfhcVar.zzaa(1, this.zzjgx.intValue());
        }
        if (this.zzjii != null) {
            zzfhcVar.zza(2, this.zzjii);
        }
        if (this.zzjij != null) {
            zzfhcVar.zza(3, this.zzjij);
        }
        if (this.zzjik != null) {
            zzfhcVar.zzl(4, this.zzjik.booleanValue());
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjgx != null) {
            zzo += zzfhc.zzad(1, this.zzjgx.intValue());
        }
        if (this.zzjii != null) {
            zzo += zzfhc.zzb(2, this.zzjii);
        }
        if (this.zzjij != null) {
            zzo += zzfhc.zzb(3, this.zzjij);
        }
        if (this.zzjik == null) {
            return zzo;
        }
        this.zzjik.booleanValue();
        return zzo + zzfhc.zzkw(4) + 1;
    }
}
