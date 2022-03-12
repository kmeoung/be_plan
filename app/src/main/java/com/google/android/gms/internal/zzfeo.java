package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzfeo extends zzfee<zzfeo, zza> implements zzffk {
    private static volatile zzffm<zzfeo> zzbas;
    private static final zzfeo zzpcp;
    private int zzpco;

    /* loaded from: classes.dex */
    public static final class zza extends zzfef<zzfeo, zza> implements zzffk {
        private zza() {
            super(zzfeo.zzpcp);
        }

        /* synthetic */ zza(zzfep zzfepVar) {
            this();
        }

        public final zza zzli(int i) {
            zzcvi();
            ((zzfeo) this.zzpbv).setValue(i);
            return this;
        }
    }

    static {
        zzfeo zzfeoVar = new zzfeo();
        zzpcp = zzfeoVar;
        zzfeoVar.zza(zzfem.zzpcf, (Object) null, (Object) null);
        zzfeoVar.zzpbs.zzbim();
    }

    private zzfeo() {
    }

    public final void setValue(int i) {
        this.zzpco = i;
    }

    public static zza zzcvn() {
        zzfeo zzfeoVar = zzpcp;
        zzfef zzfefVar = (zzfef) zzfeoVar.zza(zzfem.zzpch, (Object) null, (Object) null);
        zzfefVar.zza((zzfef) zzfeoVar);
        return (zza) zzfefVar;
    }

    public static zzfeo zzcvo() {
        return zzpcp;
    }

    public final int getValue() {
        return this.zzpco;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.zzfee
    public final Object zza(int i, Object obj, Object obj2) {
        boolean z;
        boolean z2 = true;
        switch (zzfep.zzbaq[i - 1]) {
            case 1:
                return new zzfeo();
            case 2:
                return zzpcp;
            case 3:
                return null;
            case 4:
                return new zza(null);
            case 5:
                zzfen zzfenVar = (zzfen) obj;
                zzfeo zzfeoVar = (zzfeo) obj2;
                boolean z3 = this.zzpco != 0;
                int i2 = this.zzpco;
                if (zzfeoVar.zzpco == 0) {
                    z2 = false;
                }
                this.zzpco = zzfenVar.zza(z3, i2, z2, zzfeoVar.zzpco);
                return this;
            case 6:
                zzfdq zzfdqVar = (zzfdq) obj;
                if (((zzfea) obj2) != null) {
                    boolean z4 = false;
                    while (!z4) {
                        try {
                            int zzcts = zzfdqVar.zzcts();
                            if (zzcts != 0) {
                                if (zzcts != 8) {
                                    if ((zzcts & 7) == 4) {
                                        z = false;
                                    } else {
                                        if (this.zzpbs == zzfgi.zzcwu()) {
                                            this.zzpbs = zzfgi.zzcwv();
                                        }
                                        z = this.zzpbs.zzb(zzcts, zzfdqVar);
                                    }
                                    if (!z) {
                                    }
                                } else {
                                    this.zzpco = zzfdqVar.zzctv();
                                }
                            }
                            z4 = true;
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
                    synchronized (zzfeo.class) {
                        if (zzbas == null) {
                            zzbas = new zzfeg(zzpcp);
                        }
                    }
                }
                return zzbas;
            default:
                throw new UnsupportedOperationException();
        }
        return zzpcp;
    }

    @Override // com.google.android.gms.internal.zzffi
    public final void zza(zzfdv zzfdvVar) throws IOException {
        if (this.zzpco != 0) {
            zzfdvVar.zzaa(1, this.zzpco);
        }
        this.zzpbs.zza(zzfdvVar);
    }

    @Override // com.google.android.gms.internal.zzffi
    public final int zzhl() {
        int i = this.zzpbt;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        if (this.zzpco != 0) {
            i2 = 0 + zzfdv.zzad(1, this.zzpco);
        }
        int zzhl = i2 + this.zzpbs.zzhl();
        this.zzpbt = zzhl;
        return zzhl;
    }
}
