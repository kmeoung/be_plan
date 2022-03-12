package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzckt extends zzfhe<zzckt> {
    public Integer zzjht = null;
    public String zzjhu = null;
    public Boolean zzjhv = null;
    public String[] zzjhw = zzfhn.EMPTY_STRING_ARRAY;

    public zzckt() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    /* renamed from: zzi */
    public final zzckt zza(zzfhb zzfhbVar) throws IOException {
        while (true) {
            int zzcts = zzfhbVar.zzcts();
            if (zzcts == 0) {
                return this;
            }
            if (zzcts == 8) {
                int position = zzfhbVar.getPosition();
                try {
                    int zzcuh = zzfhbVar.zzcuh();
                    switch (zzcuh) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            this.zzjht = Integer.valueOf(zzcuh);
                            break;
                        default:
                            StringBuilder sb = new StringBuilder(41);
                            sb.append(zzcuh);
                            sb.append(" is not a valid enum MatchType");
                            throw new IllegalArgumentException(sb.toString());
                            break;
                    }
                } catch (IllegalArgumentException unused) {
                    zzfhbVar.zzlv(position);
                    zza(zzfhbVar, zzcts);
                }
            } else if (zzcts == 18) {
                this.zzjhu = zzfhbVar.readString();
            } else if (zzcts == 24) {
                this.zzjhv = Boolean.valueOf(zzfhbVar.zzcty());
            } else if (zzcts == 34) {
                int zzb = zzfhn.zzb(zzfhbVar, 34);
                int length = this.zzjhw == null ? 0 : this.zzjhw.length;
                String[] strArr = new String[zzb + length];
                if (length != 0) {
                    System.arraycopy(this.zzjhw, 0, strArr, 0, length);
                }
                while (length < strArr.length - 1) {
                    strArr[length] = zzfhbVar.readString();
                    zzfhbVar.zzcts();
                    length++;
                }
                strArr[length] = zzfhbVar.readString();
                this.zzjhw = strArr;
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzckt)) {
            return false;
        }
        zzckt zzcktVar = (zzckt) obj;
        if (this.zzjht == null) {
            if (zzcktVar.zzjht != null) {
                return false;
            }
        } else if (!this.zzjht.equals(zzcktVar.zzjht)) {
            return false;
        }
        if (this.zzjhu == null) {
            if (zzcktVar.zzjhu != null) {
                return false;
            }
        } else if (!this.zzjhu.equals(zzcktVar.zzjhu)) {
            return false;
        }
        if (this.zzjhv == null) {
            if (zzcktVar.zzjhv != null) {
                return false;
            }
        } else if (!this.zzjhv.equals(zzcktVar.zzjhv)) {
            return false;
        }
        if (!zzfhi.equals(this.zzjhw, zzcktVar.zzjhw)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzcktVar.zzpgy == null || zzcktVar.zzpgy.isEmpty() : this.zzpgy.equals(zzcktVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzjht == null ? 0 : this.zzjht.intValue())) * 31) + (this.zzjhu == null ? 0 : this.zzjhu.hashCode())) * 31) + (this.zzjhv == null ? 0 : this.zzjhv.hashCode())) * 31) + zzfhi.hashCode(this.zzjhw)) * 31;
        if (this.zzpgy != null && !this.zzpgy.isEmpty()) {
            i = this.zzpgy.hashCode();
        }
        return hashCode + i;
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjht != null) {
            zzfhcVar.zzaa(1, this.zzjht.intValue());
        }
        if (this.zzjhu != null) {
            zzfhcVar.zzn(2, this.zzjhu);
        }
        if (this.zzjhv != null) {
            zzfhcVar.zzl(3, this.zzjhv.booleanValue());
        }
        if (this.zzjhw != null && this.zzjhw.length > 0) {
            for (int i = 0; i < this.zzjhw.length; i++) {
                String str = this.zzjhw[i];
                if (str != null) {
                    zzfhcVar.zzn(4, str);
                }
            }
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjht != null) {
            zzo += zzfhc.zzad(1, this.zzjht.intValue());
        }
        if (this.zzjhu != null) {
            zzo += zzfhc.zzo(2, this.zzjhu);
        }
        if (this.zzjhv != null) {
            this.zzjhv.booleanValue();
            zzo += zzfhc.zzkw(3) + 1;
        }
        if (this.zzjhw == null || this.zzjhw.length <= 0) {
            return zzo;
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzjhw.length; i3++) {
            String str = this.zzjhw[i3];
            if (str != null) {
                i2++;
                i += zzfhc.zztd(str);
            }
        }
        return zzo + i + (i2 * 1);
    }
}
