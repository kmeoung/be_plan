package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzckr extends zzfhe<zzckr> {
    public Integer zzjhl = null;
    public Boolean zzjhm = null;
    public String zzjhn = null;
    public String zzjho = null;
    public String zzjhp = null;

    public zzckr() {
        this.zzpgy = null;
        this.zzpai = -1;
    }

    /* renamed from: zzh */
    public final zzckr zza(zzfhb zzfhbVar) throws IOException {
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
                            this.zzjhl = Integer.valueOf(zzcuh);
                            break;
                        default:
                            StringBuilder sb = new StringBuilder(46);
                            sb.append(zzcuh);
                            sb.append(" is not a valid enum ComparisonType");
                            throw new IllegalArgumentException(sb.toString());
                            break;
                    }
                } catch (IllegalArgumentException unused) {
                    zzfhbVar.zzlv(position);
                    zza(zzfhbVar, zzcts);
                }
            } else if (zzcts == 16) {
                this.zzjhm = Boolean.valueOf(zzfhbVar.zzcty());
            } else if (zzcts == 26) {
                this.zzjhn = zzfhbVar.readString();
            } else if (zzcts == 34) {
                this.zzjho = zzfhbVar.readString();
            } else if (zzcts == 42) {
                this.zzjhp = zzfhbVar.readString();
            } else if (!super.zza(zzfhbVar, zzcts)) {
                return this;
            }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzckr)) {
            return false;
        }
        zzckr zzckrVar = (zzckr) obj;
        if (this.zzjhl == null) {
            if (zzckrVar.zzjhl != null) {
                return false;
            }
        } else if (!this.zzjhl.equals(zzckrVar.zzjhl)) {
            return false;
        }
        if (this.zzjhm == null) {
            if (zzckrVar.zzjhm != null) {
                return false;
            }
        } else if (!this.zzjhm.equals(zzckrVar.zzjhm)) {
            return false;
        }
        if (this.zzjhn == null) {
            if (zzckrVar.zzjhn != null) {
                return false;
            }
        } else if (!this.zzjhn.equals(zzckrVar.zzjhn)) {
            return false;
        }
        if (this.zzjho == null) {
            if (zzckrVar.zzjho != null) {
                return false;
            }
        } else if (!this.zzjho.equals(zzckrVar.zzjho)) {
            return false;
        }
        if (this.zzjhp == null) {
            if (zzckrVar.zzjhp != null) {
                return false;
            }
        } else if (!this.zzjhp.equals(zzckrVar.zzjhp)) {
            return false;
        }
        return (this.zzpgy == null || this.zzpgy.isEmpty()) ? zzckrVar.zzpgy == null || zzckrVar.zzpgy.isEmpty() : this.zzpgy.equals(zzckrVar.zzpgy);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzjhl == null ? 0 : this.zzjhl.intValue())) * 31) + (this.zzjhm == null ? 0 : this.zzjhm.hashCode())) * 31) + (this.zzjhn == null ? 0 : this.zzjhn.hashCode())) * 31) + (this.zzjho == null ? 0 : this.zzjho.hashCode())) * 31) + (this.zzjhp == null ? 0 : this.zzjhp.hashCode())) * 31;
        if (this.zzpgy != null && !this.zzpgy.isEmpty()) {
            i = this.zzpgy.hashCode();
        }
        return hashCode + i;
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.zzjhl != null) {
            zzfhcVar.zzaa(1, this.zzjhl.intValue());
        }
        if (this.zzjhm != null) {
            zzfhcVar.zzl(2, this.zzjhm.booleanValue());
        }
        if (this.zzjhn != null) {
            zzfhcVar.zzn(3, this.zzjhn);
        }
        if (this.zzjho != null) {
            zzfhcVar.zzn(4, this.zzjho);
        }
        if (this.zzjhp != null) {
            zzfhcVar.zzn(5, this.zzjhp);
        }
        super.zza(zzfhcVar);
    }

    @Override // com.google.android.gms.internal.zzfhe, com.google.android.gms.internal.zzfhk
    public final int zzo() {
        int zzo = super.zzo();
        if (this.zzjhl != null) {
            zzo += zzfhc.zzad(1, this.zzjhl.intValue());
        }
        if (this.zzjhm != null) {
            this.zzjhm.booleanValue();
            zzo += zzfhc.zzkw(2) + 1;
        }
        if (this.zzjhn != null) {
            zzo += zzfhc.zzo(3, this.zzjhn);
        }
        if (this.zzjho != null) {
            zzo += zzfhc.zzo(4, this.zzjho);
        }
        return this.zzjhp != null ? zzo + zzfhc.zzo(5, this.zzjhp) : zzo;
    }
}
