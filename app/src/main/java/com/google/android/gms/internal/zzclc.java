package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzclc extends zzfhe<zzclc> {
    public long[] zzjju = zzfhn.zzphm;
    public long[] zzjjv = zzfhn.zzphm;

    public zzclc() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzclc)) {
            return false;
        }
        zzclc zzclcVar = (zzclc) obj;
        if (zzfhi.equals(this.zzjju, zzclcVar.zzjju) && zzfhi.equals(this.zzjjv, zzclcVar.zzjjv)) {
            return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzclcVar.zzpgy == null || zzclcVar.zzpgy.isEmpty() : this.zzpgy.equals(zzclcVar.zzpgy);
        }
        return false;
    }

    public final int hashCode() {
        return ((((((getClass().getName().hashCode() + 527) * 31) + zzfhi.hashCode(this.zzjju)) * 31) + zzfhi.hashCode(this.zzjjv)) * 31) + ((this.zzpgy == null || this.zzpgy.isEmpty()) ? 0 : this.zzpgy.hashCode());
    }

    @Override // com.google.android.gms.internal.zzfhk
    public final /* synthetic */ zzfhk zza(zzfhb zzfhbVar) throws IOException {
        int zzki;
        while (true) {
            int zzcts = zzfhbVar.zzcts();
            if (zzcts == 0) {
                return this;
            }
            if (zzcts != 8) {
                if (zzcts == 10) {
                    zzki = zzfhbVar.zzki(zzfhbVar.zzcuh());
                    int position = zzfhbVar.getPosition();
                    int i = 0;
                    while (zzfhbVar.zzcuj() > 0) {
                        zzfhbVar.zzcum();
                        i++;
                    }
                    zzfhbVar.zzlv(position);
                    int length = this.zzjju == null ? 0 : this.zzjju.length;
                    long[] jArr = new long[i + length];
                    if (length != 0) {
                        System.arraycopy(this.zzjju, 0, jArr, 0, length);
                    }
                    while (length < jArr.length) {
                        jArr[length] = zzfhbVar.zzcum();
                        length++;
                    }
                    this.zzjju = jArr;
                } else if (zzcts == 16) {
                    int zzb = zzfhn.zzb(zzfhbVar, 16);
                    int length2 = this.zzjjv == null ? 0 : this.zzjjv.length;
                    long[] jArr2 = new long[zzb + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzjjv, 0, jArr2, 0, length2);
                    }
                    while (length2 < jArr2.length - 1) {
                        jArr2[length2] = zzfhbVar.zzcum();
                        zzfhbVar.zzcts();
                        length2++;
                    }
                    jArr2[length2] = zzfhbVar.zzcum();
                    this.zzjjv = jArr2;
                } else if (zzcts == 18) {
                    zzki = zzfhbVar.zzki(zzfhbVar.zzcuh());
                    int position2 = zzfhbVar.getPosition();
                    int i2 = 0;
                    while (zzfhbVar.zzcuj() > 0) {
                        zzfhbVar.zzcum();
                        i2++;
                    }
                    zzfhbVar.zzlv(position2);
                    int length3 = this.zzjjv == null ? 0 : this.zzjjv.length;
                    long[] jArr3 = new long[i2 + length3];
                    if (length3 != 0) {
                        System.arraycopy(this.zzjjv, 0, jArr3, 0, length3);
                    }
                    while (length3 < jArr3.length) {
                        jArr3[length3] = zzfhbVar.zzcum();
                        length3++;
                    }
                    this.zzjjv = jArr3;
                } else if (!super.zza(zzfhbVar, zzcts)) {
                    return this;
                }
                zzfhbVar.zzkj(zzki);
            } else {
                int zzb2 = zzfhn.zzb(zzfhbVar, 8);
                int length4 = this.zzjju == null ? 0 : this.zzjju.length;
                long[] jArr4 = new long[zzb2 + length4];
                if (length4 != 0) {
                    System.arraycopy(this.zzjju, 0, jArr4, 0, length4);
                }
                while (length4 < jArr4.length - 1) {
                    jArr4[length4] = zzfhbVar.zzcum();
                    zzfhbVar.zzcts();
                    length4++;
                }
                jArr4[length4] = zzfhbVar.zzcum();
                this.zzjju = jArr4;
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjju != null && this.zzjju.length > 0) {
            for (int i = 0; i < this.zzjju.length; i++) {
                zzfhcVar.zza(1, this.zzjju[i]);
            }
        }
        if (this.zzjjv != null && this.zzjjv.length > 0) {
            for (int i2 = 0; i2 < this.zzjjv.length; i2++) {
                zzfhcVar.zza(2, this.zzjjv[i2]);
            }
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjju != null && this.zzjju.length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < this.zzjju.length; i2++) {
                i += zzfhc.zzdh(this.zzjju[i2]);
            }
            zzo = zzo + i + (this.zzjju.length * 1);
        }
        if (this.zzjjv == null || this.zzjjv.length <= 0) {
            return zzo;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.zzjjv.length; i4++) {
            i3 += zzfhc.zzdh(this.zzjjv[i4]);
        }
        return zzo + i3 + (this.zzjjv.length * 1);
    }
}
