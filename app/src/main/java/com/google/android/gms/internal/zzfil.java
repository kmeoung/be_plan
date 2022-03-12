package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzfil extends zzfhe<zzfil> {
    private static volatile zzfil[] zzplc;
    public String zzpld = "";

    public zzfil() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzfil[] zzcyc() {
        if (zzplc == null) {
            synchronized (zzfhi.zzphg) {
                if (zzplc == null) {
                    zzplc = new zzfil[0];
                }
            }
        }
        return zzplc;
    }

    @Override // com.google.android.gms.internal.zzfhk
    public final /* synthetic */ zzfhk zza(zzfhb zzfhbVar) throws IOException {
        while (true) {
            int zzcts = zzfhbVar.zzcts();
            if (zzcts == 0) {
                return this;
            }
            if (zzcts == 10) {
                this.zzpld = zzfhbVar.readString();
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzpld != null && !this.zzpld.equals("")) {
            zzfhcVar.zzn(1, this.zzpld);
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        return (this.zzpld == null || this.zzpld.equals("")) ? zzo : zzo + zzfhc.zzo(1, this.zzpld);
    }
}
