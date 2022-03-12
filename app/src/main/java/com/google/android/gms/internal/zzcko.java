package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzcko extends zzfhe<zzcko> {
    private static volatile zzcko[] zzjgw;
    public Integer zzjgx = null;
    public zzcks[] zzjgy = zzcks.zzbar();
    public zzckp[] zzjgz = zzckp.zzbap();

    public zzcko() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzcko[] zzbao() {
        if (zzjgw == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjgw == null) {
                    zzjgw = new zzcko[0];
                }
            }
        }
        return zzjgw;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcko)) {
            return false;
        }
        zzcko zzckoVar = (zzcko) obj;
        if (this.zzjgx == null) {
            if (zzckoVar.zzjgx != null) {
                return false;
            }
        } else if (!this.zzjgx.equals(zzckoVar.zzjgx)) {
            return false;
        }
        if (zzfhi.equals(this.zzjgy, zzckoVar.zzjgy) && zzfhi.equals(this.zzjgz, zzckoVar.zzjgz)) {
            return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzckoVar.zzpgy == null || zzckoVar.zzpgy.isEmpty() : this.zzpgy.equals(zzckoVar.zzpgy);
        }
        return false;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((getClass().getName().hashCode() + 527) * 31) + (this.zzjgx == null ? 0 : this.zzjgx.hashCode())) * 31) + zzfhi.hashCode(this.zzjgy)) * 31) + zzfhi.hashCode(this.zzjgz)) * 31;
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
                this.zzjgx = Integer.valueOf(zzfhbVar.zzcuh());
            } else if (zzcts == 18) {
                int zzb = zzfhn.zzb(zzfhbVar, 18);
                int length = this.zzjgy == null ? 0 : this.zzjgy.length;
                zzcks[] zzcksVarArr = new zzcks[zzb + length];
                if (length != 0) {
                    System.arraycopy(this.zzjgy, 0, zzcksVarArr, 0, length);
                }
                while (length < zzcksVarArr.length - 1) {
                    zzcksVarArr[length] = new zzcks();
                    zzfhbVar.zza(zzcksVarArr[length]);
                    zzfhbVar.zzcts();
                    length++;
                }
                zzcksVarArr[length] = new zzcks();
                zzfhbVar.zza(zzcksVarArr[length]);
                this.zzjgy = zzcksVarArr;
            } else if (zzcts == 26) {
                int zzb2 = zzfhn.zzb(zzfhbVar, 26);
                int length2 = this.zzjgz == null ? 0 : this.zzjgz.length;
                zzckp[] zzckpVarArr = new zzckp[zzb2 + length2];
                if (length2 != 0) {
                    System.arraycopy(this.zzjgz, 0, zzckpVarArr, 0, length2);
                }
                while (length2 < zzckpVarArr.length - 1) {
                    zzckpVarArr[length2] = new zzckp();
                    zzfhbVar.zza(zzckpVarArr[length2]);
                    zzfhbVar.zzcts();
                    length2++;
                }
                zzckpVarArr[length2] = new zzckp();
                zzfhbVar.zza(zzckpVarArr[length2]);
                this.zzjgz = zzckpVarArr;
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjgx != null) {
            zzfhcVar.zzaa(1, this.zzjgx.intValue());
        }
        if (this.zzjgy != null && this.zzjgy.length > 0) {
            for (int i = 0; i < this.zzjgy.length; i++) {
                zzcks zzcksVar = this.zzjgy[i];
                if (zzcksVar != null) {
                    zzfhcVar.zza(2, zzcksVar);
                }
            }
        }
        if (this.zzjgz != null && this.zzjgz.length > 0) {
            for (int i2 = 0; i2 < this.zzjgz.length; i2++) {
                zzckp zzckpVar = this.zzjgz[i2];
                if (zzckpVar != null) {
                    zzfhcVar.zza(3, zzckpVar);
                }
            }
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjgx != null) {
            zzo += zzfhc.zzad(1, this.zzjgx.intValue());
        }
        if (this.zzjgy != null && this.zzjgy.length > 0) {
            int i = zzo;
            for (int i2 = 0; i2 < this.zzjgy.length; i2++) {
                zzcks zzcksVar = this.zzjgy[i2];
                if (zzcksVar != null) {
                    i += zzfhc.zzb(2, zzcksVar);
                }
            }
            zzo = i;
        }
        if (this.zzjgz != null && this.zzjgz.length > 0) {
            for (int i3 = 0; i3 < this.zzjgz.length; i3++) {
                zzckp zzckpVar = this.zzjgz[i3];
                if (zzckpVar != null) {
                    zzo += zzfhc.zzb(3, zzckpVar);
                }
            }
        }
        return zzo;
    }
}
