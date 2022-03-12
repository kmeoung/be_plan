package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Stack;

/* loaded from: classes.dex */
public final class zzffr {
    private final Stack<zzfdh> zzpdm;

    private zzffr() {
        this.zzpdm = new Stack<>();
    }

    private final void zzao(zzfdh zzfdhVar) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        zzfdh zzfdhVar2;
        while (!zzfdhVar.zzctn()) {
            if (zzfdhVar instanceof zzffp) {
                zzffp zzffpVar = (zzffp) zzfdhVar;
                zzfdhVar2 = zzffpVar.zzpdi;
                zzao(zzfdhVar2);
                zzfdhVar = zzffpVar.zzpdj;
            } else {
                String valueOf = String.valueOf(zzfdhVar.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
                sb.append("Has a new type of ByteString been created? Found ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        int zzlo = zzlo(zzfdhVar.size());
        iArr = zzffp.zzpdg;
        int i = iArr[zzlo + 1];
        if (this.zzpdm.isEmpty() || this.zzpdm.peek().size() >= i) {
            this.zzpdm.push(zzfdhVar);
            return;
        }
        iArr2 = zzffp.zzpdg;
        int i2 = iArr2[zzlo];
        zzfdh pop = this.zzpdm.pop();
        while (!this.zzpdm.isEmpty() && this.zzpdm.peek().size() < i2) {
            pop = new zzffp(this.zzpdm.pop(), pop);
        }
        zzffp zzffpVar2 = new zzffp(pop, zzfdhVar);
        while (!this.zzpdm.isEmpty()) {
            int zzlo2 = zzlo(zzffpVar2.size());
            iArr3 = zzffp.zzpdg;
            if (this.zzpdm.peek().size() >= iArr3[zzlo2 + 1]) {
                break;
            }
            zzffpVar2 = new zzffp(this.zzpdm.pop(), zzffpVar2);
        }
        this.zzpdm.push(zzffpVar2);
    }

    public final zzfdh zzc(zzfdh zzfdhVar, zzfdh zzfdhVar2) {
        zzao(zzfdhVar);
        zzao(zzfdhVar2);
        zzfdh pop = this.zzpdm.pop();
        while (!this.zzpdm.isEmpty()) {
            pop = new zzffp(this.zzpdm.pop(), pop);
        }
        return pop;
    }

    private static int zzlo(int i) {
        int[] iArr;
        iArr = zzffp.zzpdg;
        int binarySearch = Arrays.binarySearch(iArr, i);
        return binarySearch < 0 ? (-(binarySearch + 1)) - 1 : binarySearch;
    }
}
