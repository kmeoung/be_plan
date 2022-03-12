package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzcla extends zzfhe<zzcla> {
    public zzclb[] zzjir = zzclb.zzbax();

    public zzcla() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcla)) {
            return false;
        }
        zzcla zzclaVar = (zzcla) obj;
        if (!zzfhi.equals(this.zzjir, zzclaVar.zzjir)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzclaVar.zzpgy == null || zzclaVar.zzpgy.isEmpty() : this.zzpgy.equals(zzclaVar.zzpgy);
    }

    public final int hashCode() {
        return ((((getClass().getName().hashCode() + 527) * 31) + zzfhi.hashCode(this.zzjir)) * 31) + ((this.zzpgy == null || this.zzpgy.isEmpty()) ? 0 : this.zzpgy.hashCode());
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
                int length = this.zzjir == null ? 0 : this.zzjir.length;
                zzclb[] zzclbVarArr = new zzclb[zzb + length];
                if (length != 0) {
                    System.arraycopy(this.zzjir, 0, zzclbVarArr, 0, length);
                }
                while (length < zzclbVarArr.length - 1) {
                    zzclbVarArr[length] = new zzclb();
                    zzfhbVar.zza(zzclbVarArr[length]);
                    zzfhbVar.zzcts();
                    length++;
                }
                zzclbVarArr[length] = new zzclb();
                zzfhbVar.zza(zzclbVarArr[length]);
                this.zzjir = zzclbVarArr;
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjir != null && this.zzjir.length > 0) {
            for (int i = 0; i < this.zzjir.length; i++) {
                zzclb zzclbVar = this.zzjir[i];
                if (zzclbVar != null) {
                    zzfhcVar.zza(1, zzclbVar);
                }
            }
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjir != null && this.zzjir.length > 0) {
            for (int i = 0; i < this.zzjir.length; i++) {
                zzclb zzclbVar = this.zzjir[i];
                if (zzclbVar != null) {
                    zzo += zzfhc.zzb(1, zzclbVar);
                }
            }
        }
        return zzo;
    }
}
