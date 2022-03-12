package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public class zzfdo extends zzfdn {
    protected final byte[] zzjkl;

    public zzfdo(byte[] bArr) {
        this.zzjkl = bArr;
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfdh) || size() != ((zzfdh) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzfdo)) {
            return obj.equals(this);
        }
        zzfdo zzfdoVar = (zzfdo) obj;
        int zzcto = zzcto();
        int zzcto2 = zzfdoVar.zzcto();
        if (zzcto == 0 || zzcto2 == 0 || zzcto == zzcto2) {
            return zza(zzfdoVar, 0, size());
        }
        return false;
    }

    @Override // com.google.android.gms.internal.zzfdh
    public int size() {
        return this.zzjkl.length;
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final void zza(zzfdg zzfdgVar) throws IOException {
        zzfdgVar.zzd(this.zzjkl, zzctp(), size());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.zzfdn
    public final boolean zza(zzfdh zzfdhVar, int i, int i2) {
        if (i2 > zzfdhVar.size()) {
            int size = size();
            StringBuilder sb = new StringBuilder(40);
            sb.append("Length too large: ");
            sb.append(i2);
            sb.append(size);
            throw new IllegalArgumentException(sb.toString());
        }
        int i3 = i + i2;
        if (i3 > zzfdhVar.size()) {
            int size2 = zzfdhVar.size();
            StringBuilder sb2 = new StringBuilder(59);
            sb2.append("Ran off end of other: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            sb2.append(", ");
            sb2.append(size2);
            throw new IllegalArgumentException(sb2.toString());
        } else if (!(zzfdhVar instanceof zzfdo)) {
            return zzfdhVar.zzx(i, i3).equals(zzx(0, i2));
        } else {
            zzfdo zzfdoVar = (zzfdo) zzfdhVar;
            byte[] bArr = this.zzjkl;
            byte[] bArr2 = zzfdoVar.zzjkl;
            int zzctp = zzctp() + i2;
            int zzctp2 = zzctp();
            int zzctp3 = zzfdoVar.zzctp() + i;
            while (zzctp2 < zzctp) {
                if (bArr[zzctp2] != bArr2[zzctp3]) {
                    return false;
                }
                zzctp2++;
                zzctp3++;
            }
            return true;
        }
    }

    @Override // com.google.android.gms.internal.zzfdh
    public void zzb(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzjkl, i, bArr, i2, i3);
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final zzfdq zzctl() {
        return zzfdq.zzb(this.zzjkl, zzctp(), size(), true);
    }

    public int zzctp() {
        return 0;
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final int zzg(int i, int i2, int i3) {
        return zzfer.zza(i, this.zzjkl, zzctp() + i2, i3);
    }

    @Override // com.google.android.gms.internal.zzfdh
    public byte zzkd(int i) {
        return this.zzjkl[i];
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final zzfdh zzx(int i, int i2) {
        int zzh = zzh(i, i2, size());
        return zzh == 0 ? zzfdh.zzpal : new zzfdk(this.zzjkl, zzctp() + i, zzh);
    }
}
