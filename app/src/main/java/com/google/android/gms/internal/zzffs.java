package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/* loaded from: classes.dex */
public final class zzffs implements Iterator<zzfdn> {
    private final Stack<zzffp> zzpdn;
    private zzfdn zzpdo;

    private zzffs(zzfdh zzfdhVar) {
        this.zzpdn = new Stack<>();
        this.zzpdo = zzap(zzfdhVar);
    }

    private final zzfdn zzap(zzfdh zzfdhVar) {
        while (zzfdhVar instanceof zzffp) {
            zzffp zzffpVar = (zzffp) zzfdhVar;
            this.zzpdn.push(zzffpVar);
            zzfdhVar = zzffpVar.zzpdi;
        }
        return (zzfdn) zzfdhVar;
    }

    private final zzfdn zzcwh() {
        zzfdh zzfdhVar;
        while (!this.zzpdn.isEmpty()) {
            zzfdhVar = this.zzpdn.pop().zzpdj;
            zzfdn zzap = zzap(zzfdhVar);
            if (!zzap.isEmpty()) {
                return zzap;
            }
        }
        return null;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzpdo != null;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ zzfdn next() {
        if (this.zzpdo == null) {
            throw new NoSuchElementException();
        }
        zzfdn zzfdnVar = this.zzpdo;
        this.zzpdo = zzcwh();
        return zzfdnVar;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
