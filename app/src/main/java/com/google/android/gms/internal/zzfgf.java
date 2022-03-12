package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzfgf extends zzfee<zzfgf, zza> implements zzffk {
    private static volatile zzffm<zzfgf> zzbas;
    private static final zzfgf zzpej;
    private long zzpeh;
    private int zzpei;

    /* loaded from: classes.dex */
    public static final class zza extends zzfef<zzfgf, zza> implements zzffk {
        private zza() {
            super(zzfgf.zzpej);
        }

        /* synthetic */ zza(zzfgg zzfggVar) {
            this();
        }

        public final zza zzdf(long j) {
            zzcvi();
            ((zzfgf) this.zzpbv).zzde(j);
            return this;
        }

        public final zza zzls(int i) {
            zzcvi();
            ((zzfgf) this.zzpbv).setNanos(i);
            return this;
        }
    }

    static {
        zzfgf zzfgfVar = new zzfgf();
        zzpej = zzfgfVar;
        zzfgfVar.zza(zzfem.zzpcf, (Object) null, (Object) null);
        zzfgfVar.zzpbs.zzbim();
    }

    private zzfgf() {
    }

    public final void setNanos(int i) {
        this.zzpei = i;
    }

    public static zza zzcwq() {
        zzfgf zzfgfVar = zzpej;
        zzfef zzfefVar = (zzfef) zzfgfVar.zza(zzfem.zzpch, (Object) null, (Object) null);
        zzfefVar.zza((zzfef) zzfgfVar);
        return (zza) zzfefVar;
    }

    public static zzfgf zzcwr() {
        return zzpej;
    }

    public final void zzde(long j) {
        this.zzpeh = j;
    }

    public final int getNanos() {
        return this.zzpei;
    }

    public final long getSeconds() {
        return this.zzpeh;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.zzfee
    public final Object zza(int i, Object obj, Object obj2) {
        boolean z;
        boolean z2 = true;
        switch (zzfgg.zzbaq[i - 1]) {
            case 1:
                return new zzfgf();
            case 2:
                return zzpej;
            case 3:
                return null;
            case 4:
                return new zza(null);
            case 5:
                zzfen zzfenVar = (zzfen) obj;
                zzfgf zzfgfVar = (zzfgf) obj2;
                this.zzpeh = zzfenVar.zza(this.zzpeh != 0, this.zzpeh, zzfgfVar.zzpeh != 0, zzfgfVar.zzpeh);
                boolean z3 = this.zzpei != 0;
                int i2 = this.zzpei;
                if (zzfgfVar.zzpei == 0) {
                    z2 = false;
                }
                this.zzpei = zzfenVar.zza(z3, i2, z2, zzfgfVar.zzpei);
                return this;
            case 6:
                zzfdq zzfdqVar = (zzfdq) obj;
                if (((zzfea) obj2) != null) {
                    boolean z4 = false;
                    while (!z4) {
                        try {
                            int zzcts = zzfdqVar.zzcts();
                            if (zzcts != 0) {
                                if (zzcts == 8) {
                                    this.zzpeh = zzfdqVar.zzctu();
                                } else if (zzcts != 16) {
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
                                    this.zzpei = zzfdqVar.zzctv();
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
                    synchronized (zzfgf.class) {
                        if (zzbas == null) {
                            zzbas = new zzfeg(zzpej);
                        }
                    }
                }
                return zzbas;
            default:
                throw new UnsupportedOperationException();
        }
        return zzpej;
    }

    @Override // com.google.android.gms.internal.zzffi
    public final void zza(zzfdv zzfdvVar) throws IOException {
        if (this.zzpeh != 0) {
            zzfdvVar.zza(1, this.zzpeh);
        }
        if (this.zzpei != 0) {
            zzfdvVar.zzaa(2, this.zzpei);
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
        if (this.zzpeh != 0) {
            i2 = 0 + zzfdv.zzc(1, this.zzpeh);
        }
        if (this.zzpei != 0) {
            i2 += zzfdv.zzad(2, this.zzpei);
        }
        int zzhl = i2 + this.zzpbs.zzhl();
        this.zzpbt = zzhl;
        return zzhl;
    }
}
