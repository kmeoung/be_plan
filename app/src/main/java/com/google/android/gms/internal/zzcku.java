package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzcku extends zzfhe<zzcku> {
    private static volatile zzcku[] zzjhx;
    public String name = null;
    public Boolean zzjhy = null;
    public Boolean zzjhz = null;
    public Integer zzjia = null;

    public zzcku() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzcku[] zzbas() {
        if (zzjhx == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjhx == null) {
                    zzjhx = new zzcku[0];
                }
            }
        }
        return zzjhx;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcku)) {
            return false;
        }
        zzcku zzckuVar = (zzcku) obj;
        if (this.name == null) {
            if (zzckuVar.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzckuVar.name)) {
            return false;
        }
        if (this.zzjhy == null) {
            if (zzckuVar.zzjhy != null) {
                return false;
            }
        } else if (!this.zzjhy.equals(zzckuVar.zzjhy)) {
            return false;
        }
        if (this.zzjhz == null) {
            if (zzckuVar.zzjhz != null) {
                return false;
            }
        } else if (!this.zzjhz.equals(zzckuVar.zzjhz)) {
            return false;
        }
        if (this.zzjia == null) {
            if (zzckuVar.zzjia != null) {
                return false;
            }
        } else if (!this.zzjia.equals(zzckuVar.zzjia)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzckuVar.zzpgy == null || zzckuVar.zzpgy.isEmpty() : this.zzpgy.equals(zzckuVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((getClass().getName().hashCode() + 527) * 31) + (this.name == null ? 0 : this.name.hashCode())) * 31) + (this.zzjhy == null ? 0 : this.zzjhy.hashCode())) * 31) + (this.zzjhz == null ? 0 : this.zzjhz.hashCode())) * 31) + (this.zzjia == null ? 0 : this.zzjia.hashCode())) * 31;
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
            } else if (zzcts == 16) {
                this.zzjhy = Boolean.valueOf(zzfhbVar.zzcty());
            } else if (zzcts == 24) {
                this.zzjhz = Boolean.valueOf(zzfhbVar.zzcty());
            } else if (zzcts == 32) {
                this.zzjia = Integer.valueOf(zzfhbVar.zzcuh());
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
        if (this.zzjhy != null) {
            zzfhcVar.zzl(2, this.zzjhy.booleanValue());
        }
        if (this.zzjhz != null) {
            zzfhcVar.zzl(3, this.zzjhz.booleanValue());
        }
        if (this.zzjia != null) {
            zzfhcVar.zzaa(4, this.zzjia.intValue());
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.name != null) {
            zzo += zzfhc.zzo(1, this.name);
        }
        if (this.zzjhy != null) {
            this.zzjhy.booleanValue();
            zzo += zzfhc.zzkw(2) + 1;
        }
        if (this.zzjhz != null) {
            this.zzjhz.booleanValue();
            zzo += zzfhc.zzkw(3) + 1;
        }
        return this.zzjia != null ? zzo + zzfhc.zzad(4, this.zzjia.intValue()) : zzo;
    }
}
