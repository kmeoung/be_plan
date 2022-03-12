package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzckw extends zzfhe<zzckw> {
    private static volatile zzckw[] zzjig;
    public String key = null;
    public String value = null;

    public zzckw() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzckw[] zzbat() {
        if (zzjig == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjig == null) {
                    zzjig = new zzckw[0];
                }
            }
        }
        return zzjig;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzckw)) {
            return false;
        }
        zzckw zzckwVar = (zzckw) obj;
        if (this.key == null) {
            if (zzckwVar.key != null) {
                return false;
            }
        } else if (!this.key.equals(zzckwVar.key)) {
            return false;
        }
        if (this.value == null) {
            if (zzckwVar.value != null) {
                return false;
            }
        } else if (!this.value.equals(zzckwVar.value)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzckwVar.zzpgy == null || zzckwVar.zzpgy.isEmpty() : this.zzpgy.equals(zzckwVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + (this.key == null ? 0 : this.key.hashCode())) * 31) + (this.value == null ? 0 : this.value.hashCode())) * 31;
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
                this.key = zzfhbVar.readString();
            } else if (zzcts == 18) {
                this.value = zzfhbVar.readString();
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.key != null) {
            zzfhcVar.zzn(1, this.key);
        }
        if (this.value != null) {
            zzfhcVar.zzn(2, this.value);
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.key != null) {
            zzo += zzfhc.zzo(1, this.key);
        }
        return this.value != null ? zzo + zzfhc.zzo(2, this.value) : zzo;
    }
}
