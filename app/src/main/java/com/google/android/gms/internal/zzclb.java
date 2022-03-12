package com.google.android.gms.internal;

import cz.msebera.android.httpclient.HttpStatus;
import java.io.IOException;

/* loaded from: classes.dex */
public final class zzclb extends zzfhe<zzclb> {
    private static volatile zzclb[] zzjis;
    public Integer zzjit = null;
    public zzcky[] zzjiu = zzcky.zzbav();
    public zzcld[] zzjiv = zzcld.zzbay();
    public Long zzjiw = null;
    public Long zzjix = null;
    public Long zzjiy = null;
    public Long zzjiz = null;
    public Long zzjja = null;
    public String zzjjb = null;
    public String zzcv = null;
    public String zzjjc = null;
    public String zzjjd = null;
    public Integer zzjje = null;
    public String zziuy = null;
    public String zzch = null;
    public String zzicq = null;
    public Long zzjjf = null;
    public Long zzjjg = null;
    public String zzjjh = null;
    public Boolean zzjji = null;
    public String zzjjj = null;
    public Long zzjjk = null;
    public Integer zzjjl = null;
    public String zzivb = null;
    public String zziux = null;
    public Boolean zzjjm = null;
    public zzckx[] zzjjn = zzckx.zzbau();
    public String zzivf = null;
    public Integer zzjjo = null;
    private Integer zzjjp = null;
    private Integer zzjjq = null;
    public String zzjjr = null;
    public Long zzjjs = null;
    public Long zzfhr = null;
    public String zzjjt = null;

    public zzclb() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    public static zzclb[] zzbax() {
        if (zzjis == null) {
            synchronized (zzfhi.zzphg) {
                if (zzjis == null) {
                    zzjis = new zzclb[0];
                }
            }
        }
        return zzjis;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzclb)) {
            return false;
        }
        zzclb zzclbVar = (zzclb) obj;
        if (this.zzjit == null) {
            if (zzclbVar.zzjit != null) {
                return false;
            }
        } else if (!this.zzjit.equals(zzclbVar.zzjit)) {
            return false;
        }
        if (!zzfhi.equals(this.zzjiu, zzclbVar.zzjiu) || !zzfhi.equals(this.zzjiv, zzclbVar.zzjiv)) {
            return false;
        }
        if (this.zzjiw == null) {
            if (zzclbVar.zzjiw != null) {
                return false;
            }
        } else if (!this.zzjiw.equals(zzclbVar.zzjiw)) {
            return false;
        }
        if (this.zzjix == null) {
            if (zzclbVar.zzjix != null) {
                return false;
            }
        } else if (!this.zzjix.equals(zzclbVar.zzjix)) {
            return false;
        }
        if (this.zzjiy == null) {
            if (zzclbVar.zzjiy != null) {
                return false;
            }
        } else if (!this.zzjiy.equals(zzclbVar.zzjiy)) {
            return false;
        }
        if (this.zzjiz == null) {
            if (zzclbVar.zzjiz != null) {
                return false;
            }
        } else if (!this.zzjiz.equals(zzclbVar.zzjiz)) {
            return false;
        }
        if (this.zzjja == null) {
            if (zzclbVar.zzjja != null) {
                return false;
            }
        } else if (!this.zzjja.equals(zzclbVar.zzjja)) {
            return false;
        }
        if (this.zzjjb == null) {
            if (zzclbVar.zzjjb != null) {
                return false;
            }
        } else if (!this.zzjjb.equals(zzclbVar.zzjjb)) {
            return false;
        }
        if (this.zzcv == null) {
            if (zzclbVar.zzcv != null) {
                return false;
            }
        } else if (!this.zzcv.equals(zzclbVar.zzcv)) {
            return false;
        }
        if (this.zzjjc == null) {
            if (zzclbVar.zzjjc != null) {
                return false;
            }
        } else if (!this.zzjjc.equals(zzclbVar.zzjjc)) {
            return false;
        }
        if (this.zzjjd == null) {
            if (zzclbVar.zzjjd != null) {
                return false;
            }
        } else if (!this.zzjjd.equals(zzclbVar.zzjjd)) {
            return false;
        }
        if (this.zzjje == null) {
            if (zzclbVar.zzjje != null) {
                return false;
            }
        } else if (!this.zzjje.equals(zzclbVar.zzjje)) {
            return false;
        }
        if (this.zziuy == null) {
            if (zzclbVar.zziuy != null) {
                return false;
            }
        } else if (!this.zziuy.equals(zzclbVar.zziuy)) {
            return false;
        }
        if (this.zzch == null) {
            if (zzclbVar.zzch != null) {
                return false;
            }
        } else if (!this.zzch.equals(zzclbVar.zzch)) {
            return false;
        }
        if (this.zzicq == null) {
            if (zzclbVar.zzicq != null) {
                return false;
            }
        } else if (!this.zzicq.equals(zzclbVar.zzicq)) {
            return false;
        }
        if (this.zzjjf == null) {
            if (zzclbVar.zzjjf != null) {
                return false;
            }
        } else if (!this.zzjjf.equals(zzclbVar.zzjjf)) {
            return false;
        }
        if (this.zzjjg == null) {
            if (zzclbVar.zzjjg != null) {
                return false;
            }
        } else if (!this.zzjjg.equals(zzclbVar.zzjjg)) {
            return false;
        }
        if (this.zzjjh == null) {
            if (zzclbVar.zzjjh != null) {
                return false;
            }
        } else if (!this.zzjjh.equals(zzclbVar.zzjjh)) {
            return false;
        }
        if (this.zzjji == null) {
            if (zzclbVar.zzjji != null) {
                return false;
            }
        } else if (!this.zzjji.equals(zzclbVar.zzjji)) {
            return false;
        }
        if (this.zzjjj == null) {
            if (zzclbVar.zzjjj != null) {
                return false;
            }
        } else if (!this.zzjjj.equals(zzclbVar.zzjjj)) {
            return false;
        }
        if (this.zzjjk == null) {
            if (zzclbVar.zzjjk != null) {
                return false;
            }
        } else if (!this.zzjjk.equals(zzclbVar.zzjjk)) {
            return false;
        }
        if (this.zzjjl == null) {
            if (zzclbVar.zzjjl != null) {
                return false;
            }
        } else if (!this.zzjjl.equals(zzclbVar.zzjjl)) {
            return false;
        }
        if (this.zzivb == null) {
            if (zzclbVar.zzivb != null) {
                return false;
            }
        } else if (!this.zzivb.equals(zzclbVar.zzivb)) {
            return false;
        }
        if (this.zziux == null) {
            if (zzclbVar.zziux != null) {
                return false;
            }
        } else if (!this.zziux.equals(zzclbVar.zziux)) {
            return false;
        }
        if (this.zzjjm == null) {
            if (zzclbVar.zzjjm != null) {
                return false;
            }
        } else if (!this.zzjjm.equals(zzclbVar.zzjjm)) {
            return false;
        }
        if (!zzfhi.equals(this.zzjjn, zzclbVar.zzjjn)) {
            return false;
        }
        if (this.zzivf == null) {
            if (zzclbVar.zzivf != null) {
                return false;
            }
        } else if (!this.zzivf.equals(zzclbVar.zzivf)) {
            return false;
        }
        if (this.zzjjo == null) {
            if (zzclbVar.zzjjo != null) {
                return false;
            }
        } else if (!this.zzjjo.equals(zzclbVar.zzjjo)) {
            return false;
        }
        if (this.zzjjp == null) {
            if (zzclbVar.zzjjp != null) {
                return false;
            }
        } else if (!this.zzjjp.equals(zzclbVar.zzjjp)) {
            return false;
        }
        if (this.zzjjq == null) {
            if (zzclbVar.zzjjq != null) {
                return false;
            }
        } else if (!this.zzjjq.equals(zzclbVar.zzjjq)) {
            return false;
        }
        if (this.zzjjr == null) {
            if (zzclbVar.zzjjr != null) {
                return false;
            }
        } else if (!this.zzjjr.equals(zzclbVar.zzjjr)) {
            return false;
        }
        if (this.zzjjs == null) {
            if (zzclbVar.zzjjs != null) {
                return false;
            }
        } else if (!this.zzjjs.equals(zzclbVar.zzjjs)) {
            return false;
        }
        if (this.zzfhr == null) {
            if (zzclbVar.zzfhr != null) {
                return false;
            }
        } else if (!this.zzfhr.equals(zzclbVar.zzfhr)) {
            return false;
        }
        if (this.zzjjt == null) {
            if (zzclbVar.zzjjt != null) {
                return false;
            }
        } else if (!this.zzjjt.equals(zzclbVar.zzjjt)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzclbVar.zzpgy == null || zzclbVar.zzpgy.isEmpty() : this.zzpgy.equals(zzclbVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzjit == null ? 0 : this.zzjit.hashCode())) * 31) + zzfhi.hashCode(this.zzjiu)) * 31) + zzfhi.hashCode(this.zzjiv)) * 31) + (this.zzjiw == null ? 0 : this.zzjiw.hashCode())) * 31) + (this.zzjix == null ? 0 : this.zzjix.hashCode())) * 31) + (this.zzjiy == null ? 0 : this.zzjiy.hashCode())) * 31) + (this.zzjiz == null ? 0 : this.zzjiz.hashCode())) * 31) + (this.zzjja == null ? 0 : this.zzjja.hashCode())) * 31) + (this.zzjjb == null ? 0 : this.zzjjb.hashCode())) * 31) + (this.zzcv == null ? 0 : this.zzcv.hashCode())) * 31) + (this.zzjjc == null ? 0 : this.zzjjc.hashCode())) * 31) + (this.zzjjd == null ? 0 : this.zzjjd.hashCode())) * 31) + (this.zzjje == null ? 0 : this.zzjje.hashCode())) * 31) + (this.zziuy == null ? 0 : this.zziuy.hashCode())) * 31) + (this.zzch == null ? 0 : this.zzch.hashCode())) * 31) + (this.zzicq == null ? 0 : this.zzicq.hashCode())) * 31) + (this.zzjjf == null ? 0 : this.zzjjf.hashCode())) * 31) + (this.zzjjg == null ? 0 : this.zzjjg.hashCode())) * 31) + (this.zzjjh == null ? 0 : this.zzjjh.hashCode())) * 31) + (this.zzjji == null ? 0 : this.zzjji.hashCode())) * 31) + (this.zzjjj == null ? 0 : this.zzjjj.hashCode())) * 31) + (this.zzjjk == null ? 0 : this.zzjjk.hashCode())) * 31) + (this.zzjjl == null ? 0 : this.zzjjl.hashCode())) * 31) + (this.zzivb == null ? 0 : this.zzivb.hashCode())) * 31) + (this.zziux == null ? 0 : this.zziux.hashCode())) * 31) + (this.zzjjm == null ? 0 : this.zzjjm.hashCode())) * 31) + zzfhi.hashCode(this.zzjjn)) * 31) + (this.zzivf == null ? 0 : this.zzivf.hashCode())) * 31) + (this.zzjjo == null ? 0 : this.zzjjo.hashCode())) * 31) + (this.zzjjp == null ? 0 : this.zzjjp.hashCode())) * 31) + (this.zzjjq == null ? 0 : this.zzjjq.hashCode())) * 31) + (this.zzjjr == null ? 0 : this.zzjjr.hashCode())) * 31) + (this.zzjjs == null ? 0 : this.zzjjs.hashCode())) * 31) + (this.zzfhr == null ? 0 : this.zzfhr.hashCode())) * 31) + (this.zzjjt == null ? 0 : this.zzjjt.hashCode())) * 31;
        if (this.zzpgy != null && !this.zzpgy.isEmpty()) {
            i = this.zzpgy.hashCode();
        }
        return hashCode + i;
    }

    @Override // com.google.android.gms.internal.zzfhk
    public final /* synthetic */ zzfhk zza(zzfhb zzfhbVar) throws IOException {
        while (true) {
            int zzcts = zzfhbVar.zzcts();
            switch (zzcts) {
                case 0:
                    return this;
                case 8:
                    this.zzjit = Integer.valueOf(zzfhbVar.zzcuh());
                    break;
                case 18:
                    int zzb = zzfhn.zzb(zzfhbVar, 18);
                    int length = this.zzjiu == null ? 0 : this.zzjiu.length;
                    zzcky[] zzckyVarArr = new zzcky[zzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzjiu, 0, zzckyVarArr, 0, length);
                    }
                    while (length < zzckyVarArr.length - 1) {
                        zzckyVarArr[length] = new zzcky();
                        zzfhbVar.zza(zzckyVarArr[length]);
                        zzfhbVar.zzcts();
                        length++;
                    }
                    zzckyVarArr[length] = new zzcky();
                    zzfhbVar.zza(zzckyVarArr[length]);
                    this.zzjiu = zzckyVarArr;
                    break;
                case 26:
                    int zzb2 = zzfhn.zzb(zzfhbVar, 26);
                    int length2 = this.zzjiv == null ? 0 : this.zzjiv.length;
                    zzcld[] zzcldVarArr = new zzcld[zzb2 + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzjiv, 0, zzcldVarArr, 0, length2);
                    }
                    while (length2 < zzcldVarArr.length - 1) {
                        zzcldVarArr[length2] = new zzcld();
                        zzfhbVar.zza(zzcldVarArr[length2]);
                        zzfhbVar.zzcts();
                        length2++;
                    }
                    zzcldVarArr[length2] = new zzcld();
                    zzfhbVar.zza(zzcldVarArr[length2]);
                    this.zzjiv = zzcldVarArr;
                    break;
                case 32:
                    this.zzjiw = Long.valueOf(zzfhbVar.zzcum());
                    break;
                case 40:
                    this.zzjix = Long.valueOf(zzfhbVar.zzcum());
                    break;
                case 48:
                    this.zzjiy = Long.valueOf(zzfhbVar.zzcum());
                    break;
                case 56:
                    this.zzjja = Long.valueOf(zzfhbVar.zzcum());
                    break;
                case 66:
                    this.zzjjb = zzfhbVar.readString();
                    break;
                case 74:
                    this.zzcv = zzfhbVar.readString();
                    break;
                case 82:
                    this.zzjjc = zzfhbVar.readString();
                    break;
                case 90:
                    this.zzjjd = zzfhbVar.readString();
                    break;
                case 96:
                    this.zzjje = Integer.valueOf(zzfhbVar.zzcuh());
                    break;
                case 106:
                    this.zziuy = zzfhbVar.readString();
                    break;
                case 114:
                    this.zzch = zzfhbVar.readString();
                    break;
                case 130:
                    this.zzicq = zzfhbVar.readString();
                    break;
                case 136:
                    this.zzjjf = Long.valueOf(zzfhbVar.zzcum());
                    break;
                case 144:
                    this.zzjjg = Long.valueOf(zzfhbVar.zzcum());
                    break;
                case 154:
                    this.zzjjh = zzfhbVar.readString();
                    break;
                case 160:
                    this.zzjji = Boolean.valueOf(zzfhbVar.zzcty());
                    break;
                case 170:
                    this.zzjjj = zzfhbVar.readString();
                    break;
                case 176:
                    this.zzjjk = Long.valueOf(zzfhbVar.zzcum());
                    break;
                case 184:
                    this.zzjjl = Integer.valueOf(zzfhbVar.zzcuh());
                    break;
                case 194:
                    this.zzivb = zzfhbVar.readString();
                    break;
                case HttpStatus.SC_ACCEPTED /* 202 */:
                    this.zziux = zzfhbVar.readString();
                    break;
                case 208:
                    this.zzjiz = Long.valueOf(zzfhbVar.zzcum());
                    break;
                case 224:
                    this.zzjjm = Boolean.valueOf(zzfhbVar.zzcty());
                    break;
                case 234:
                    int zzb3 = zzfhn.zzb(zzfhbVar, 234);
                    int length3 = this.zzjjn == null ? 0 : this.zzjjn.length;
                    zzckx[] zzckxVarArr = new zzckx[zzb3 + length3];
                    if (length3 != 0) {
                        System.arraycopy(this.zzjjn, 0, zzckxVarArr, 0, length3);
                    }
                    while (length3 < zzckxVarArr.length - 1) {
                        zzckxVarArr[length3] = new zzckx();
                        zzfhbVar.zza(zzckxVarArr[length3]);
                        zzfhbVar.zzcts();
                        length3++;
                    }
                    zzckxVarArr[length3] = new zzckx();
                    zzfhbVar.zza(zzckxVarArr[length3]);
                    this.zzjjn = zzckxVarArr;
                    break;
                case 242:
                    this.zzivf = zzfhbVar.readString();
                    break;
                case 248:
                    this.zzjjo = Integer.valueOf(zzfhbVar.zzcuh());
                    break;
                case 256:
                    this.zzjjp = Integer.valueOf(zzfhbVar.zzcuh());
                    break;
                case 264:
                    this.zzjjq = Integer.valueOf(zzfhbVar.zzcuh());
                    break;
                case 274:
                    this.zzjjr = zzfhbVar.readString();
                    break;
                case 280:
                    this.zzjjs = Long.valueOf(zzfhbVar.zzcum());
                    break;
                case 288:
                    this.zzfhr = Long.valueOf(zzfhbVar.zzcum());
                    break;
                case 298:
                    this.zzjjt = zzfhbVar.readString();
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
        if (this.zzjit != null) {
            zzfhcVar.zzaa(1, this.zzjit.intValue());
        }
        if (this.zzjiu != null && this.zzjiu.length > 0) {
            for (int i = 0; i < this.zzjiu.length; i++) {
                zzcky zzckyVar = this.zzjiu[i];
                if (zzckyVar != null) {
                    zzfhcVar.zza(2, zzckyVar);
                }
            }
        }
        if (this.zzjiv != null && this.zzjiv.length > 0) {
            for (int i2 = 0; i2 < this.zzjiv.length; i2++) {
                zzcld zzcldVar = this.zzjiv[i2];
                if (zzcldVar != null) {
                    zzfhcVar.zza(3, zzcldVar);
                }
            }
        }
        if (this.zzjiw != null) {
            zzfhcVar.zzf(4, this.zzjiw.longValue());
        }
        if (this.zzjix != null) {
            zzfhcVar.zzf(5, this.zzjix.longValue());
        }
        if (this.zzjiy != null) {
            zzfhcVar.zzf(6, this.zzjiy.longValue());
        }
        if (this.zzjja != null) {
            zzfhcVar.zzf(7, this.zzjja.longValue());
        }
        if (this.zzjjb != null) {
            zzfhcVar.zzn(8, this.zzjjb);
        }
        if (this.zzcv != null) {
            zzfhcVar.zzn(9, this.zzcv);
        }
        if (this.zzjjc != null) {
            zzfhcVar.zzn(10, this.zzjjc);
        }
        if (this.zzjjd != null) {
            zzfhcVar.zzn(11, this.zzjjd);
        }
        if (this.zzjje != null) {
            zzfhcVar.zzaa(12, this.zzjje.intValue());
        }
        if (this.zziuy != null) {
            zzfhcVar.zzn(13, this.zziuy);
        }
        if (this.zzch != null) {
            zzfhcVar.zzn(14, this.zzch);
        }
        if (this.zzicq != null) {
            zzfhcVar.zzn(16, this.zzicq);
        }
        if (this.zzjjf != null) {
            zzfhcVar.zzf(17, this.zzjjf.longValue());
        }
        if (this.zzjjg != null) {
            zzfhcVar.zzf(18, this.zzjjg.longValue());
        }
        if (this.zzjjh != null) {
            zzfhcVar.zzn(19, this.zzjjh);
        }
        if (this.zzjji != null) {
            zzfhcVar.zzl(20, this.zzjji.booleanValue());
        }
        if (this.zzjjj != null) {
            zzfhcVar.zzn(21, this.zzjjj);
        }
        if (this.zzjjk != null) {
            zzfhcVar.zzf(22, this.zzjjk.longValue());
        }
        if (this.zzjjl != null) {
            zzfhcVar.zzaa(23, this.zzjjl.intValue());
        }
        if (this.zzivb != null) {
            zzfhcVar.zzn(24, this.zzivb);
        }
        if (this.zziux != null) {
            zzfhcVar.zzn(25, this.zziux);
        }
        if (this.zzjiz != null) {
            zzfhcVar.zzf(26, this.zzjiz.longValue());
        }
        if (this.zzjjm != null) {
            zzfhcVar.zzl(28, this.zzjjm.booleanValue());
        }
        if (this.zzjjn != null && this.zzjjn.length > 0) {
            for (int i3 = 0; i3 < this.zzjjn.length; i3++) {
                zzckx zzckxVar = this.zzjjn[i3];
                if (zzckxVar != null) {
                    zzfhcVar.zza(29, zzckxVar);
                }
            }
        }
        if (this.zzivf != null) {
            zzfhcVar.zzn(30, this.zzivf);
        }
        if (this.zzjjo != null) {
            zzfhcVar.zzaa(31, this.zzjjo.intValue());
        }
        if (this.zzjjp != null) {
            zzfhcVar.zzaa(32, this.zzjjp.intValue());
        }
        if (this.zzjjq != null) {
            zzfhcVar.zzaa(33, this.zzjjq.intValue());
        }
        if (this.zzjjr != null) {
            zzfhcVar.zzn(34, this.zzjjr);
        }
        if (this.zzjjs != null) {
            zzfhcVar.zzf(35, this.zzjjs.longValue());
        }
        if (this.zzfhr != null) {
            zzfhcVar.zzf(36, this.zzfhr.longValue());
        }
        if (this.zzjjt != null) {
            zzfhcVar.zzn(37, this.zzjjt);
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjit != null) {
            zzo += zzfhc.zzad(1, this.zzjit.intValue());
        }
        if (this.zzjiu != null && this.zzjiu.length > 0) {
            int i = zzo;
            for (int i2 = 0; i2 < this.zzjiu.length; i2++) {
                zzcky zzckyVar = this.zzjiu[i2];
                if (zzckyVar != null) {
                    i += zzfhc.zzb(2, zzckyVar);
                }
            }
            zzo = i;
        }
        if (this.zzjiv != null && this.zzjiv.length > 0) {
            int i3 = zzo;
            for (int i4 = 0; i4 < this.zzjiv.length; i4++) {
                zzcld zzcldVar = this.zzjiv[i4];
                if (zzcldVar != null) {
                    i3 += zzfhc.zzb(3, zzcldVar);
                }
            }
            zzo = i3;
        }
        if (this.zzjiw != null) {
            zzo += zzfhc.zzc(4, this.zzjiw.longValue());
        }
        if (this.zzjix != null) {
            zzo += zzfhc.zzc(5, this.zzjix.longValue());
        }
        if (this.zzjiy != null) {
            zzo += zzfhc.zzc(6, this.zzjiy.longValue());
        }
        if (this.zzjja != null) {
            zzo += zzfhc.zzc(7, this.zzjja.longValue());
        }
        if (this.zzjjb != null) {
            zzo += zzfhc.zzo(8, this.zzjjb);
        }
        if (this.zzcv != null) {
            zzo += zzfhc.zzo(9, this.zzcv);
        }
        if (this.zzjjc != null) {
            zzo += zzfhc.zzo(10, this.zzjjc);
        }
        if (this.zzjjd != null) {
            zzo += zzfhc.zzo(11, this.zzjjd);
        }
        if (this.zzjje != null) {
            zzo += zzfhc.zzad(12, this.zzjje.intValue());
        }
        if (this.zziuy != null) {
            zzo += zzfhc.zzo(13, this.zziuy);
        }
        if (this.zzch != null) {
            zzo += zzfhc.zzo(14, this.zzch);
        }
        if (this.zzicq != null) {
            zzo += zzfhc.zzo(16, this.zzicq);
        }
        if (this.zzjjf != null) {
            zzo += zzfhc.zzc(17, this.zzjjf.longValue());
        }
        if (this.zzjjg != null) {
            zzo += zzfhc.zzc(18, this.zzjjg.longValue());
        }
        if (this.zzjjh != null) {
            zzo += zzfhc.zzo(19, this.zzjjh);
        }
        if (this.zzjji != null) {
            this.zzjji.booleanValue();
            zzo += zzfhc.zzkw(20) + 1;
        }
        if (this.zzjjj != null) {
            zzo += zzfhc.zzo(21, this.zzjjj);
        }
        if (this.zzjjk != null) {
            zzo += zzfhc.zzc(22, this.zzjjk.longValue());
        }
        if (this.zzjjl != null) {
            zzo += zzfhc.zzad(23, this.zzjjl.intValue());
        }
        if (this.zzivb != null) {
            zzo += zzfhc.zzo(24, this.zzivb);
        }
        if (this.zziux != null) {
            zzo += zzfhc.zzo(25, this.zziux);
        }
        if (this.zzjiz != null) {
            zzo += zzfhc.zzc(26, this.zzjiz.longValue());
        }
        if (this.zzjjm != null) {
            this.zzjjm.booleanValue();
            zzo += zzfhc.zzkw(28) + 1;
        }
        if (this.zzjjn != null && this.zzjjn.length > 0) {
            for (int i5 = 0; i5 < this.zzjjn.length; i5++) {
                zzckx zzckxVar = this.zzjjn[i5];
                if (zzckxVar != null) {
                    zzo += zzfhc.zzb(29, zzckxVar);
                }
            }
        }
        if (this.zzivf != null) {
            zzo += zzfhc.zzo(30, this.zzivf);
        }
        if (this.zzjjo != null) {
            zzo += zzfhc.zzad(31, this.zzjjo.intValue());
        }
        if (this.zzjjp != null) {
            zzo += zzfhc.zzad(32, this.zzjjp.intValue());
        }
        if (this.zzjjq != null) {
            zzo += zzfhc.zzad(33, this.zzjjq.intValue());
        }
        if (this.zzjjr != null) {
            zzo += zzfhc.zzo(34, this.zzjjr);
        }
        if (this.zzjjs != null) {
            zzo += zzfhc.zzc(35, this.zzjjs.longValue());
        }
        if (this.zzfhr != null) {
            zzo += zzfhc.zzc(36, this.zzfhr.longValue());
        }
        return this.zzjjt != null ? zzo + zzfhc.zzo(37, this.zzjjt) : zzo;
    }
}
