package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class zzffp extends zzfdh {
    private static final int[] zzpdg;
    private final int zzpdh;
    private final zzfdh zzpdi;
    private final zzfdh zzpdj;
    private final int zzpdk;
    private final int zzpdl;

    static {
        ArrayList arrayList = new ArrayList();
        int i = 1;
        int i2 = 1;
        while (i > 0) {
            arrayList.add(Integer.valueOf(i));
            i = i2 + i;
            i2 = i;
        }
        arrayList.add(Integer.MAX_VALUE);
        zzpdg = new int[arrayList.size()];
        for (int i3 = 0; i3 < zzpdg.length; i3++) {
            zzpdg[i3] = ((Integer) arrayList.get(i3)).intValue();
        }
    }

    private zzffp(zzfdh zzfdhVar, zzfdh zzfdhVar2) {
        this.zzpdi = zzfdhVar;
        this.zzpdj = zzfdhVar2;
        this.zzpdk = zzfdhVar.size();
        this.zzpdh = this.zzpdk + zzfdhVar2.size();
        this.zzpdl = Math.max(zzfdhVar.zzctm(), zzfdhVar2.zzctm()) + 1;
    }

    public static zzfdh zza(zzfdh zzfdhVar, zzfdh zzfdhVar2) {
        zzfdh zzc;
        if (zzfdhVar2.size() == 0) {
            return zzfdhVar;
        }
        if (zzfdhVar.size() == 0) {
            return zzfdhVar2;
        }
        int size = zzfdhVar.size() + zzfdhVar2.size();
        if (size < 128) {
            return zzb(zzfdhVar, zzfdhVar2);
        }
        if (zzfdhVar instanceof zzffp) {
            zzffp zzffpVar = (zzffp) zzfdhVar;
            if (zzffpVar.zzpdj.size() + zzfdhVar2.size() < 128) {
                return new zzffp(zzffpVar.zzpdi, zzb(zzffpVar.zzpdj, zzfdhVar2));
            } else if (zzffpVar.zzpdi.zzctm() > zzffpVar.zzpdj.zzctm() && zzffpVar.zzctm() > zzfdhVar2.zzctm()) {
                return new zzffp(zzffpVar.zzpdi, new zzffp(zzffpVar.zzpdj, zzfdhVar2));
            }
        }
        if (size >= zzpdg[Math.max(zzfdhVar.zzctm(), zzfdhVar2.zzctm()) + 1]) {
            return new zzffp(zzfdhVar, zzfdhVar2);
        }
        zzc = new zzffr().zzc(zzfdhVar, zzfdhVar2);
        return zzc;
    }

    private static zzfdh zzb(zzfdh zzfdhVar, zzfdh zzfdhVar2) {
        int size = zzfdhVar.size();
        int size2 = zzfdhVar2.size();
        byte[] bArr = new byte[size + size2];
        zzfdhVar.zza(bArr, 0, 0, size);
        zzfdhVar2.zza(bArr, 0, size, size2);
        return zzfdh.zzaz(bArr);
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfdh)) {
            return false;
        }
        zzfdh zzfdhVar = (zzfdh) obj;
        if (this.zzpdh != zzfdhVar.size()) {
            return false;
        }
        if (this.zzpdh == 0) {
            return true;
        }
        int zzcto = zzcto();
        int zzcto2 = zzfdhVar.zzcto();
        if (zzcto != 0 && zzcto2 != 0 && zzcto != zzcto2) {
            return false;
        }
        zzffs zzffsVar = new zzffs(this);
        zzfdn next = zzffsVar.next();
        zzffs zzffsVar2 = new zzffs(zzfdhVar);
        zzfdn next2 = zzffsVar2.next();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int size = next.size() - i;
            int size2 = next2.size() - i2;
            int min = Math.min(size, size2);
            if (!(i == 0 ? next.zza(next2, i2, min) : next2.zza(next, i, min))) {
                return false;
            }
            i3 += min;
            if (i3 < this.zzpdh) {
                if (min == size) {
                    i = 0;
                    next = zzffsVar.next();
                } else {
                    i += min;
                    next = next;
                }
                if (min == size2) {
                    next2 = zzffsVar2.next();
                    i2 = 0;
                } else {
                    i2 += min;
                }
            } else if (i3 == this.zzpdh) {
                return true;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final int size() {
        return this.zzpdh;
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final void zza(zzfdg zzfdgVar) throws IOException {
        this.zzpdi.zza(zzfdgVar);
        this.zzpdj.zza(zzfdgVar);
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final void zzb(byte[] bArr, int i, int i2, int i3) {
        if (i + i3 <= this.zzpdk) {
            this.zzpdi.zzb(bArr, i, i2, i3);
        } else if (i >= this.zzpdk) {
            this.zzpdj.zzb(bArr, i - this.zzpdk, i2, i3);
        } else {
            int i4 = this.zzpdk - i;
            this.zzpdi.zzb(bArr, i, i2, i4);
            this.zzpdj.zzb(bArr, 0, i2 + i4, i3 - i4);
        }
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final zzfdq zzctl() {
        return zzfdq.zzi(new zzfft(this));
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final int zzctm() {
        return this.zzpdl;
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final boolean zzctn() {
        return this.zzpdh >= zzpdg[this.zzpdl];
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final int zzg(int i, int i2, int i3) {
        if (i2 + i3 <= this.zzpdk) {
            return this.zzpdi.zzg(i, i2, i3);
        }
        if (i2 >= this.zzpdk) {
            return this.zzpdj.zzg(i, i2 - this.zzpdk, i3);
        }
        int i4 = this.zzpdk - i2;
        return this.zzpdj.zzg(this.zzpdi.zzg(i, i2, i4), 0, i3 - i4);
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final byte zzkd(int i) {
        zzfdh zzfdhVar;
        zzy(i, this.zzpdh);
        if (i < this.zzpdk) {
            zzfdhVar = this.zzpdi;
        } else {
            zzfdhVar = this.zzpdj;
            i -= this.zzpdk;
        }
        return zzfdhVar.zzkd(i);
    }

    @Override // com.google.android.gms.internal.zzfdh
    public final zzfdh zzx(int i, int i2) {
        zzfdh zzfdhVar;
        int zzh = zzh(i, i2, this.zzpdh);
        if (zzh == 0) {
            return zzfdh.zzpal;
        }
        if (zzh == this.zzpdh) {
            return this;
        }
        if (i2 <= this.zzpdk) {
            zzfdhVar = this.zzpdi;
        } else if (i >= this.zzpdk) {
            zzfdhVar = this.zzpdj;
            i -= this.zzpdk;
            i2 -= this.zzpdk;
        } else {
            zzfdh zzfdhVar2 = this.zzpdi;
            return new zzffp(zzfdhVar2.zzx(i, zzfdhVar2.size()), this.zzpdj.zzx(0, i2 - this.zzpdk));
        }
        return zzfdhVar.zzx(i, i2);
    }
}
