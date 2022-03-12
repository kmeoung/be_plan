package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhe;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class zzfhe<M extends zzfhe<M>> extends zzfhk {
    protected zzfhg zzpgy;

    public final <T> T zza(zzfhf<M, T> zzfhfVar) {
        zzfhh zzlz;
        if (this.zzpgy == null || (zzlz = this.zzpgy.zzlz(zzfhfVar.tag >>> 3)) == null) {
            return null;
        }
        return (T) zzlz.zzb(zzfhfVar);
    }

    @Override // com.google.android.gms.internal.zzfhk
    public void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzpgy != null) {
            for (int i = 0; i < this.zzpgy.size(); i++) {
                this.zzpgy.zzma(i).zza(zzfhcVar);
            }
        }
    }

    public final boolean zza(zzfhb zzfhbVar, int i) throws IOException {
        int position = zzfhbVar.getPosition();
        if (!zzfhbVar.zzkg(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzfhm zzfhmVar = new zzfhm(i, zzfhbVar.zzal(position, zzfhbVar.getPosition() - position));
        zzfhh zzfhhVar = null;
        if (this.zzpgy == null) {
            this.zzpgy = new zzfhg();
        } else {
            zzfhhVar = this.zzpgy.zzlz(i2);
        }
        if (zzfhhVar == null) {
            zzfhhVar = new zzfhh();
            this.zzpgy.zza(i2, zzfhhVar);
        }
        zzfhhVar.zza(zzfhmVar);
        return true;
    }

    /* renamed from: zzcxe */
    public M clone() throws CloneNotSupportedException {
        M m = (M) ((zzfhe) super.clone());
        zzfhi.zza(this, m);
        return m;
    }

    @Override // com.google.android.gms.internal.zzfhk
    public /* synthetic */ zzfhk zzcxf() throws CloneNotSupportedException {
        return (zzfhe) clone();
    }

    @Override // com.google.android.gms.internal.zzfhk
    public int zzo() {
        if (this.zzpgy == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.zzpgy.size(); i2++) {
            i += this.zzpgy.zzma(i2).zzo();
        }
        return i;
    }
}
