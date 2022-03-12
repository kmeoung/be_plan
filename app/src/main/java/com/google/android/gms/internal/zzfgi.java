package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class zzfgi {
    private static final zzfgi zzpel = new zzfgi(0, new int[0], new Object[0], false);
    private int count;
    private boolean zzpah;
    private int zzpbt;
    private int[] zzpem;
    private Object[] zzpen;

    private zzfgi() {
        this(0, new int[8], new Object[8], true);
    }

    private zzfgi(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzpbt = -1;
        this.count = i;
        this.zzpem = iArr;
        this.zzpen = objArr;
        this.zzpah = z;
    }

    private final zzfgi zza(zzfdq zzfdqVar) throws IOException {
        int zzcts;
        do {
            zzcts = zzfdqVar.zzcts();
            if (zzcts == 0) {
                break;
            }
        } while (zzb(zzcts, zzfdqVar));
        return this;
    }

    public static zzfgi zzb(zzfgi zzfgiVar, zzfgi zzfgiVar2) {
        int i = zzfgiVar.count + zzfgiVar2.count;
        int[] copyOf = Arrays.copyOf(zzfgiVar.zzpem, i);
        System.arraycopy(zzfgiVar2.zzpem, 0, copyOf, zzfgiVar.count, zzfgiVar2.count);
        Object[] copyOf2 = Arrays.copyOf(zzfgiVar.zzpen, i);
        System.arraycopy(zzfgiVar2.zzpen, 0, copyOf2, zzfgiVar.count, zzfgiVar2.count);
        return new zzfgi(i, copyOf, copyOf2, true);
    }

    private void zzb(int i, Object obj) {
        if (this.count == this.zzpem.length) {
            int i2 = this.count + (this.count < 4 ? 8 : this.count >> 1);
            this.zzpem = Arrays.copyOf(this.zzpem, i2);
            this.zzpen = Arrays.copyOf(this.zzpen, i2);
        }
        this.zzpem[this.count] = i;
        this.zzpen[this.count] = obj;
        this.count++;
    }

    public static zzfgi zzcwu() {
        return zzpel;
    }

    public static zzfgi zzcwv() {
        return new zzfgi();
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzfgi)) {
            return false;
        }
        zzfgi zzfgiVar = (zzfgi) obj;
        if (this.count == zzfgiVar.count) {
            int[] iArr = this.zzpem;
            int[] iArr2 = zzfgiVar.zzpem;
            int i = this.count;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    z = true;
                    break;
                } else if (iArr[i2] != iArr2[i2]) {
                    z = false;
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                Object[] objArr = this.zzpen;
                Object[] objArr2 = zzfgiVar.zzpen;
                int i3 = this.count;
                int i4 = 0;
                while (true) {
                    if (i4 >= i3) {
                        z2 = true;
                        break;
                    } else if (!objArr[i4].equals(objArr2[i4])) {
                        z2 = false;
                        break;
                    } else {
                        i4++;
                    }
                }
                return z2;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((((this.count + 527) * 31) + Arrays.hashCode(this.zzpem)) * 31) + Arrays.deepHashCode(this.zzpen);
    }

    public final void zza(zzfdv zzfdvVar) throws IOException {
        for (int i = 0; i < this.count; i++) {
            int i2 = this.zzpem[i];
            int i3 = i2 >>> 3;
            int i4 = i2 & 7;
            if (i4 != 5) {
                switch (i4) {
                    case 0:
                        zzfdvVar.zza(i3, ((Long) this.zzpen[i]).longValue());
                        continue;
                    case 1:
                        zzfdvVar.zzb(i3, ((Long) this.zzpen[i]).longValue());
                        continue;
                    case 2:
                        zzfdvVar.zza(i3, (zzfdh) this.zzpen[i]);
                        continue;
                    case 3:
                        zzfdvVar.zzz(i3, 3);
                        ((zzfgi) this.zzpen[i]).zza(zzfdvVar);
                        zzfdvVar.zzz(i3, 4);
                        continue;
                    default:
                        throw zzfew.zzcvw();
                }
            } else {
                zzfdvVar.zzac(i3, ((Integer) this.zzpen[i]).intValue());
            }
        }
    }

    public final boolean zzb(int i, zzfdq zzfdqVar) throws IOException {
        if (!this.zzpah) {
            throw new UnsupportedOperationException();
        }
        int i2 = i >>> 3;
        switch (i & 7) {
            case 0:
                zzb(i, Long.valueOf(zzfdqVar.zzctu()));
                return true;
            case 1:
                zzb(i, Long.valueOf(zzfdqVar.zzctw()));
                return true;
            case 2:
                zzb(i, zzfdqVar.zzcua());
                return true;
            case 3:
                zzfgi zzfgiVar = new zzfgi();
                zzfgiVar.zza(zzfdqVar);
                zzfdqVar.zzkf((i2 << 3) | 4);
                zzb(i, zzfgiVar);
                return true;
            case 4:
                return false;
            case 5:
                zzb(i, Integer.valueOf(zzfdqVar.zzctx()));
                return true;
            default:
                throw zzfew.zzcvw();
        }
    }

    public final void zzbim() {
        this.zzpah = false;
    }

    public final void zzd(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            zzffl.zzb(sb, i, String.valueOf(this.zzpem[i2] >>> 3), this.zzpen[i2]);
        }
    }

    public final int zzhl() {
        int zzaf;
        int i = this.zzpbt;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            int i4 = this.zzpem[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 != 5) {
                switch (i6) {
                    case 0:
                        zzaf = zzfdv.zzd(i5, ((Long) this.zzpen[i3]).longValue());
                        break;
                    case 1:
                        zzaf = zzfdv.zze(i5, ((Long) this.zzpen[i3]).longValue());
                        break;
                    case 2:
                        zzaf = zzfdv.zzb(i5, (zzfdh) this.zzpen[i3]);
                        break;
                    case 3:
                        zzaf = (zzfdv.zzkw(i5) << 1) + ((zzfgi) this.zzpen[i3]).zzhl();
                        break;
                    default:
                        throw new IllegalStateException(zzfew.zzcvw());
                }
            } else {
                zzaf = zzfdv.zzaf(i5, ((Integer) this.zzpen[i3]).intValue());
            }
            i2 += zzaf;
        }
        this.zzpbt = i2;
        return i2;
    }
}
