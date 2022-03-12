package com.google.android.gms.internal;

/* loaded from: classes.dex */
public class zzffc {
    private static final zzfea zzpaj = zzfea.zzcuz();
    private zzfdh zzpcw;
    private volatile zzffi zzpcx;
    private volatile zzfdh zzpcy;

    private zzfdh toByteString() {
        if (this.zzpcy != null) {
            return this.zzpcy;
        }
        synchronized (this) {
            if (this.zzpcy != null) {
                return this.zzpcy;
            }
            this.zzpcy = this.zzpcx == null ? zzfdh.zzpal : this.zzpcx.toByteString();
            return this.zzpcy;
        }
    }

    private zzffi zzi(zzffi zzffiVar) {
        if (this.zzpcx == null) {
            synchronized (this) {
                if (this.zzpcx == null) {
                    try {
                        this.zzpcx = zzffiVar;
                        this.zzpcy = zzfdh.zzpal;
                    } catch (zzfew unused) {
                        this.zzpcx = zzffiVar;
                        this.zzpcy = zzfdh.zzpal;
                    }
                }
            }
        }
        return this.zzpcx;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzffc)) {
            return false;
        }
        zzffc zzffcVar = (zzffc) obj;
        zzffi zzffiVar = this.zzpcx;
        zzffi zzffiVar2 = zzffcVar.zzpcx;
        return (zzffiVar == null && zzffiVar2 == null) ? toByteString().equals(zzffcVar.toByteString()) : (zzffiVar == null || zzffiVar2 == null) ? zzffiVar != null ? zzffiVar.equals(zzffcVar.zzi(zzffiVar.zzcvh())) : zzi(zzffiVar2.zzcvh()).equals(zzffiVar2) : zzffiVar.equals(zzffiVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final int zzhl() {
        if (this.zzpcy != null) {
            return this.zzpcy.size();
        }
        if (this.zzpcx != null) {
            return this.zzpcx.zzhl();
        }
        return 0;
    }

    public final zzffi zzj(zzffi zzffiVar) {
        zzffi zzffiVar2 = this.zzpcx;
        this.zzpcw = null;
        this.zzpcy = null;
        this.zzpcx = zzffiVar;
        return zzffiVar2;
    }
}
