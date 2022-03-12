package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzfde extends zzfee<zzfde, zza> implements zzffk {
    private static volatile zzffm<zzfde> zzbas;
    private static final zzfde zzpak;
    private String zzlso = "";
    private zzfdh zzlsp = zzfdh.zzpal;

    /* loaded from: classes.dex */
    public static final class zza extends zzfef<zzfde, zza> implements zzffk {
        private zza() {
            super(zzfde.zzpak);
        }

        /* synthetic */ zza(zzfdf zzfdfVar) {
            this();
        }
    }

    static {
        zzfde zzfdeVar = new zzfde();
        zzpak = zzfdeVar;
        zzfdeVar.zza(zzfem.zzpcf, (Object) null, (Object) null);
        zzfdeVar.zzpbs.zzbim();
    }

    private zzfde() {
    }

    public static zzfde zzctj() {
        return zzpak;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.zzfee
    public final Object zza(int i, Object obj, Object obj2) {
        boolean z;
        boolean z2 = true;
        switch (zzfdf.zzbaq[i - 1]) {
            case 1:
                return new zzfde();
            case 2:
                return zzpak;
            case 3:
                return null;
            case 4:
                return new zza(null);
            case 5:
                zzfen zzfenVar = (zzfen) obj;
                zzfde zzfdeVar = (zzfde) obj2;
                this.zzlso = zzfenVar.zza(!this.zzlso.isEmpty(), this.zzlso, !zzfdeVar.zzlso.isEmpty(), zzfdeVar.zzlso);
                boolean z3 = this.zzlsp != zzfdh.zzpal;
                zzfdh zzfdhVar = this.zzlsp;
                if (zzfdeVar.zzlsp == zzfdh.zzpal) {
                    z2 = false;
                }
                this.zzlsp = zzfenVar.zza(z3, zzfdhVar, z2, zzfdeVar.zzlsp);
                return this;
            case 6:
                zzfdq zzfdqVar = (zzfdq) obj;
                if (((zzfea) obj2) != null) {
                    boolean z4 = false;
                    while (!z4) {
                        try {
                            int zzcts = zzfdqVar.zzcts();
                            if (zzcts != 0) {
                                if (zzcts == 10) {
                                    this.zzlso = zzfdqVar.zzctz();
                                } else if (zzcts != 18) {
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
                                    this.zzlsp = zzfdqVar.zzcua();
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
                    synchronized (zzfde.class) {
                        if (zzbas == null) {
                            zzbas = new zzfeg(zzpak);
                        }
                    }
                }
                return zzbas;
            default:
                throw new UnsupportedOperationException();
        }
        return zzpak;
    }

    @Override // com.google.android.gms.internal.zzffi
    public final void zza(zzfdv zzfdvVar) throws IOException {
        if (!this.zzlso.isEmpty()) {
            zzfdvVar.zzn(1, this.zzlso);
        }
        if (!this.zzlsp.isEmpty()) {
            zzfdvVar.zza(2, this.zzlsp);
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
        if (!this.zzlso.isEmpty()) {
            i2 = 0 + zzfdv.zzo(1, this.zzlso);
        }
        if (!this.zzlsp.isEmpty()) {
            i2 += zzfdv.zzb(2, this.zzlsp);
        }
        int zzhl = i2 + this.zzpbs.zzhl();
        this.zzpbt = zzhl;
        return zzhl;
    }
}
