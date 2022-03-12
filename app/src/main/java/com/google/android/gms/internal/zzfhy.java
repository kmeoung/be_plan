package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzfhy extends zzfee<zzfhy, zza> implements zzffk {
    private static volatile zzffm<zzfhy> zzbas;
    private static final zzfhy zzpjj;
    private int zzltg;
    private int zzpjg;
    private String zzpjh = "";
    private zzfev<zzfde> zzpji = zzcvf();

    /* loaded from: classes.dex */
    public static final class zza extends zzfef<zzfhy, zza> implements zzffk {
        private zza() {
            super(zzfhy.zzpjj);
        }

        /* synthetic */ zza(zzfhz zzfhzVar) {
            this();
        }
    }

    static {
        zzfhy zzfhyVar = new zzfhy();
        zzpjj = zzfhyVar;
        zzfhyVar.zza(zzfem.zzpcf, (Object) null, (Object) null);
        zzfhyVar.zzpbs.zzbim();
    }

    private zzfhy() {
    }

    public static zzfhy zzcxo() {
        return zzpjj;
    }

    public final int getCode() {
        return this.zzpjg;
    }

    public final String getMessage() {
        return this.zzpjh;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.zzfee
    public final Object zza(int i, Object obj, Object obj2) {
        boolean z = false;
        switch (zzfhz.zzbaq[i - 1]) {
            case 1:
                return new zzfhy();
            case 2:
                return zzpjj;
            case 3:
                this.zzpji.zzbim();
                return null;
            case 4:
                return new zza(null);
            case 5:
                zzfen zzfenVar = (zzfen) obj;
                zzfhy zzfhyVar = (zzfhy) obj2;
                boolean z2 = this.zzpjg != 0;
                int i2 = this.zzpjg;
                if (zzfhyVar.zzpjg != 0) {
                    z = true;
                }
                this.zzpjg = zzfenVar.zza(z2, i2, z, zzfhyVar.zzpjg);
                this.zzpjh = zzfenVar.zza(!this.zzpjh.isEmpty(), this.zzpjh, true ^ zzfhyVar.zzpjh.isEmpty(), zzfhyVar.zzpjh);
                this.zzpji = zzfenVar.zza(this.zzpji, zzfhyVar.zzpji);
                if (zzfenVar == zzfel.zzpcb) {
                    this.zzltg |= zzfhyVar.zzltg;
                }
                return this;
            case 6:
                zzfdq zzfdqVar = (zzfdq) obj;
                zzfea zzfeaVar = (zzfea) obj2;
                if (zzfeaVar != null) {
                    while (!z) {
                        try {
                            int zzcts = zzfdqVar.zzcts();
                            if (zzcts != 0) {
                                if (zzcts == 8) {
                                    this.zzpjg = zzfdqVar.zzctv();
                                } else if (zzcts == 18) {
                                    this.zzpjh = zzfdqVar.zzctz();
                                } else if (zzcts == 26) {
                                    if (!this.zzpji.zzcth()) {
                                        zzfev<zzfde> zzfevVar = this.zzpji;
                                        int size = zzfevVar.size();
                                        this.zzpji = zzfevVar.zzln(size == 0 ? 10 : size << 1);
                                    }
                                    this.zzpji.add((zzfde) zzfdqVar.zza((zzfdq) zzfde.zzctj(), zzfeaVar));
                                } else if (!zza(zzcts, zzfdqVar)) {
                                }
                            }
                            z = true;
                        } catch (zzfew e) {
                            throw new RuntimeException(e.zzh(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new zzfew(e2.getMessage()).zzh(this));
                        }
                    }
                    break;
                } else {
                    throw new NullPointerException();
                }
            case 7:
                break;
            case 8:
                if (zzbas == null) {
                    synchronized (zzfhy.class) {
                        if (zzbas == null) {
                            zzbas = new zzfeg(zzpjj);
                        }
                    }
                }
                return zzbas;
            default:
                throw new UnsupportedOperationException();
        }
        return zzpjj;
    }

    @Override // com.google.android.gms.internal.zzffi
    public final void zza(zzfdv zzfdvVar) throws IOException {
        if (this.zzpjg != 0) {
            zzfdvVar.zzaa(1, this.zzpjg);
        }
        if (!this.zzpjh.isEmpty()) {
            zzfdvVar.zzn(2, this.zzpjh);
        }
        for (int i = 0; i < this.zzpji.size(); i++) {
            zzfdvVar.zza(3, this.zzpji.get(i));
        }
        this.zzpbs.zza(zzfdvVar);
    }

    @Override // com.google.android.gms.internal.zzffi
    public final int zzhl() {
        int i = this.zzpbt;
        if (i != -1) {
            return i;
        }
        int zzad = this.zzpjg != 0 ? zzfdv.zzad(1, this.zzpjg) + 0 : 0;
        if (!this.zzpjh.isEmpty()) {
            zzad += zzfdv.zzo(2, this.zzpjh);
        }
        for (int i2 = 0; i2 < this.zzpji.size(); i2++) {
            zzad += zzfdv.zzb(3, this.zzpji.get(i2));
        }
        int zzhl = zzad + this.zzpbs.zzhl();
        this.zzpbt = zzhl;
        return zzhl;
    }
}
