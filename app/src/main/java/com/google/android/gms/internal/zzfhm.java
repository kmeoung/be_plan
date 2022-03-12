package com.google.android.gms.internal;

import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzfhm {
    final int tag;
    final byte[] zzjkl;

    public zzfhm(int i, byte[] bArr) {
        this.tag = i;
        this.zzjkl = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfhm)) {
            return false;
        }
        zzfhm zzfhmVar = (zzfhm) obj;
        return this.tag == zzfhmVar.tag && Arrays.equals(this.zzjkl, zzfhmVar.zzjkl);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzjkl);
    }
}
