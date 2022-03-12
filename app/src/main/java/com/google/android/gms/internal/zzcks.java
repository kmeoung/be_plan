package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzcks extends zzfhe<zzcks> {
    private static volatile zzcks[] zzjhq;
    public Integer zzjhb = null;
    public String zzjhr = null;
    public zzckq zzjhs = null;

    public zzcks() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzcks[] zzbar() {
        if (zzjhq == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjhq == null) {
                    zzjhq = new zzcks[0];
                }
            }
        }
        return zzjhq;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcks)) {
            return false;
        }
        zzcks zzcksVar = (zzcks) obj;
        if (this.zzjhb == null) {
            if (zzcksVar.zzjhb != null) {
                return false;
            }
        } else if (!this.zzjhb.equals(zzcksVar.zzjhb)) {
            return false;
        }
        if (this.zzjhr == null) {
            if (zzcksVar.zzjhr != null) {
                return false;
            }
        } else if (!this.zzjhr.equals(zzcksVar.zzjhr)) {
            return false;
        }
        if (this.zzjhs == null) {
            if (zzcksVar.zzjhs != null) {
                return false;
            }
        } else if (!this.zzjhs.equals(zzcksVar.zzjhs)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzcksVar.zzpgy == null || zzcksVar.zzpgy.isEmpty() : this.zzpgy.equals(zzcksVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((getClass().getName().hashCode() + 527) * 31) + (this.zzjhb == null ? 0 : this.zzjhb.hashCode())) * 31) + (this.zzjhr == null ? 0 : this.zzjhr.hashCode());
        zzckq zzckqVar = this.zzjhs;
        int hashCode2 = ((hashCode * 31) + (zzckqVar == null ? 0 : zzckqVar.hashCode())) * 31;
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
                this.zzjhr = zzfhbVar.readString();
            } else if (zzcts == 26) {
                if (this.zzjhs == null) {
                    this.zzjhs = new zzckq();
                }
                zzfhbVar.zza(this.zzjhs);
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
        if (this.zzjhr != null) {
            zzfhcVar.zzn(2, this.zzjhr);
        }
        if (this.zzjhs != null) {
            zzfhcVar.zza(3, this.zzjhs);
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjhb != null) {
            zzo += zzfhc.zzad(1, this.zzjhb.intValue());
        }
        if (this.zzjhr != null) {
            zzo += zzfhc.zzo(2, this.zzjhr);
        }
        return this.zzjhs != null ? zzo + zzfhc.zzb(3, this.zzjhs) : zzo;
    }
}
