package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzfhg implements Cloneable {
    private static final zzfhh zzpha = new zzfhh();
    private int mSize;
    private boolean zzphb;
    private int[] zzphc;
    private zzfhh[] zzphd;

    public zzfhg() {
        this(10);
    }

    private zzfhg(int i) {
        this.zzphb = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzphc = new int[idealIntArraySize];
        this.zzphd = new zzfhh[idealIntArraySize];
        this.mSize = 0;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        int i3 = 4;
        while (true) {
            if (i3 >= 32) {
                break;
            }
            int i4 = (1 << i3) - 12;
            if (i2 <= i4) {
                i2 = i4;
                break;
            }
            i3++;
        }
        return i2 / 4;
    }

    private final int zzmb(int i) {
        int i2 = this.mSize - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzphc[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i2 = i4 - 1;
            }
        }
        return i3 ^ (-1);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        zzfhg zzfhgVar = new zzfhg(i);
        System.arraycopy(this.zzphc, 0, zzfhgVar.zzphc, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzphd[i2] != null) {
                zzfhgVar.zzphd[i2] = (zzfhh) this.zzphd[i2].clone();
            }
        }
        zzfhgVar.mSize = i;
        return zzfhgVar;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfhg)) {
            return false;
        }
        zzfhg zzfhgVar = (zzfhg) obj;
        if (this.mSize != zzfhgVar.mSize) {
            return false;
        }
        int[] iArr = this.zzphc;
        int[] iArr2 = zzfhgVar.zzphc;
        int i = this.mSize;
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
            zzfhh[] zzfhhVarArr = this.zzphd;
            zzfhh[] zzfhhVarArr2 = zzfhgVar.zzphd;
            int i3 = this.mSize;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    z2 = true;
                    break;
                } else if (!zzfhhVarArr[i4].equals(zzfhhVarArr2[i4])) {
                    z2 = false;
                    break;
                } else {
                    i4++;
                }
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzphc[i2]) * 31) + this.zzphd[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    public final int size() {
        return this.mSize;
    }

    public final void zza(int i, zzfhh zzfhhVar) {
        int zzmb = zzmb(i);
        if (zzmb >= 0) {
            this.zzphd[zzmb] = zzfhhVar;
            return;
        }
        int i2 = zzmb ^ (-1);
        if (i2 >= this.mSize || this.zzphd[i2] != zzpha) {
            if (this.mSize >= this.zzphc.length) {
                int idealIntArraySize = idealIntArraySize(this.mSize + 1);
                int[] iArr = new int[idealIntArraySize];
                zzfhh[] zzfhhVarArr = new zzfhh[idealIntArraySize];
                System.arraycopy(this.zzphc, 0, iArr, 0, this.zzphc.length);
                System.arraycopy(this.zzphd, 0, zzfhhVarArr, 0, this.zzphd.length);
                this.zzphc = iArr;
                this.zzphd = zzfhhVarArr;
            }
            if (this.mSize - i2 != 0) {
                int i3 = i2 + 1;
                System.arraycopy(this.zzphc, i2, this.zzphc, i3, this.mSize - i2);
                System.arraycopy(this.zzphd, i2, this.zzphd, i3, this.mSize - i2);
            }
            this.zzphc[i2] = i;
            this.zzphd[i2] = zzfhhVar;
            this.mSize++;
            return;
        }
        this.zzphc[i2] = i;
        this.zzphd[i2] = zzfhhVar;
    }

    public final zzfhh zzlz(int i) {
        int zzmb = zzmb(i);
        if (zzmb < 0 || this.zzphd[zzmb] == zzpha) {
            return null;
        }
        return this.zzphd[zzmb];
    }

    public final zzfhh zzma(int i) {
        return this.zzphd[i];
    }
}
