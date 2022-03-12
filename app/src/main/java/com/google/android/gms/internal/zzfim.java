package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzfim extends zzfhe<zzfim> {
    public String zzpld = "";
    public String zzple = "";
    public long zzplf = 0;
    public String zzplg = "";
    public long zzplh = 0;
    public long zzgew = 0;
    public String zzpli = "";
    public String zzplj = "";
    public String zzplk = "";
    public String zzpll = "";
    public String zzplm = "";
    public int zzpln = 0;
    public zzfil[] zzplo = zzfil.zzcyc();

    public zzfim() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzfim zzbh(byte[] bArr) throws zzfhj {
        return (zzfim) zzfhk.zza(new zzfim(), bArr);
    }

    @Override // com.google.android.gms.internal.zzfhk
    public final /* synthetic */ zzfhk zza(zzfhb zzfhbVar) throws IOException {
        while (true) {
            int zzcts = zzfhbVar.zzcts();
            switch (zzcts) {
                case 0:
                    return this;
                case 10:
                    this.zzpld = zzfhbVar.readString();
                    break;
                case 18:
                    this.zzple = zzfhbVar.readString();
                    break;
                case 24:
                    this.zzplf = zzfhbVar.zzctu();
                    break;
                case 34:
                    this.zzplg = zzfhbVar.readString();
                    break;
                case 40:
                    this.zzplh = zzfhbVar.zzctu();
                    break;
                case 48:
                    this.zzgew = zzfhbVar.zzctu();
                    break;
                case 58:
                    this.zzpli = zzfhbVar.readString();
                    break;
                case 66:
                    this.zzplj = zzfhbVar.readString();
                    break;
                case 74:
                    this.zzplk = zzfhbVar.readString();
                    break;
                case 82:
                    this.zzpll = zzfhbVar.readString();
                    break;
                case 90:
                    this.zzplm = zzfhbVar.readString();
                    break;
                case 96:
                    this.zzpln = zzfhbVar.zzctv();
                    break;
                case 106:
                    int zzb = zzfhn.zzb(zzfhbVar, 106);
                    int length = this.zzplo == null ? 0 : this.zzplo.length;
                    zzfil[] zzfilVarArr = new zzfil[zzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzplo, 0, zzfilVarArr, 0, length);
                    }
                    while (length < zzfilVarArr.length - 1) {
                        zzfilVarArr[length] = new zzfil();
                        zzfhbVar.zza(zzfilVarArr[length]);
                        zzfhbVar.zzcts();
                        length++;
                    }
                    zzfilVarArr[length] = new zzfil();
                    zzfhbVar.zza(zzfilVarArr[length]);
                    this.zzplo = zzfilVarArr;
                    break;
                default:
                    if (super.zza(zzfhbVar, zzcts)) {
                        break;
                    } else {
                        return this;
                    }
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzpld != null && !this.zzpld.equals("")) {
            zzfhcVar.zzn(1, this.zzpld);
        }
        if (this.zzple != null && !this.zzple.equals("")) {
            zzfhcVar.zzn(2, this.zzple);
        }
        if (this.zzplf != 0) {
            zzfhcVar.zzf(3, this.zzplf);
        }
        if (this.zzplg != null && !this.zzplg.equals("")) {
            zzfhcVar.zzn(4, this.zzplg);
        }
        if (this.zzplh != 0) {
            zzfhcVar.zzf(5, this.zzplh);
        }
        if (this.zzgew != 0) {
            zzfhcVar.zzf(6, this.zzgew);
        }
        if (this.zzpli != null && !this.zzpli.equals("")) {
            zzfhcVar.zzn(7, this.zzpli);
        }
        if (this.zzplj != null && !this.zzplj.equals("")) {
            zzfhcVar.zzn(8, this.zzplj);
        }
        if (this.zzplk != null && !this.zzplk.equals("")) {
            zzfhcVar.zzn(9, this.zzplk);
        }
        if (this.zzpll != null && !this.zzpll.equals("")) {
            zzfhcVar.zzn(10, this.zzpll);
        }
        if (this.zzplm != null && !this.zzplm.equals("")) {
            zzfhcVar.zzn(11, this.zzplm);
        }
        if (this.zzpln != 0) {
            zzfhcVar.zzaa(12, this.zzpln);
        }
        if (this.zzplo != null && this.zzplo.length > 0) {
            for (int i = 0; i < this.zzplo.length; i++) {
                zzfil zzfilVar = this.zzplo[i];
                if (zzfilVar != null) {
                    zzfhcVar.zza(13, zzfilVar);
                }
            }
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzpld != null && !this.zzpld.equals("")) {
            zzo += zzfhc.zzo(1, this.zzpld);
        }
        if (this.zzple != null && !this.zzple.equals("")) {
            zzo += zzfhc.zzo(2, this.zzple);
        }
        if (this.zzplf != 0) {
            zzo += zzfhc.zzc(3, this.zzplf);
        }
        if (this.zzplg != null && !this.zzplg.equals("")) {
            zzo += zzfhc.zzo(4, this.zzplg);
        }
        if (this.zzplh != 0) {
            zzo += zzfhc.zzc(5, this.zzplh);
        }
        if (this.zzgew != 0) {
            zzo += zzfhc.zzc(6, this.zzgew);
        }
        if (this.zzpli != null && !this.zzpli.equals("")) {
            zzo += zzfhc.zzo(7, this.zzpli);
        }
        if (this.zzplj != null && !this.zzplj.equals("")) {
            zzo += zzfhc.zzo(8, this.zzplj);
        }
        if (this.zzplk != null && !this.zzplk.equals("")) {
            zzo += zzfhc.zzo(9, this.zzplk);
        }
        if (this.zzpll != null && !this.zzpll.equals("")) {
            zzo += zzfhc.zzo(10, this.zzpll);
        }
        if (this.zzplm != null && !this.zzplm.equals("")) {
            zzo += zzfhc.zzo(11, this.zzplm);
        }
        if (this.zzpln != 0) {
            zzo += zzfhc.zzad(12, this.zzpln);
        }
        if (this.zzplo != null && this.zzplo.length > 0) {
            for (int i = 0; i < this.zzplo.length; i++) {
                zzfil zzfilVar = this.zzplo[i];
                if (zzfilVar != null) {
                    zzo += zzfhc.zzb(13, zzfilVar);
                }
            }
        }
        return zzo;
    }
}
