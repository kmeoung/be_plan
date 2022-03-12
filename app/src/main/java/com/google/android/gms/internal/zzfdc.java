package com.google.android.gms.internal;

import com.google.android.gms.internal.zzffi;

/* loaded from: classes.dex */
public abstract class zzfdc<MessageType extends zzffi> implements zzffm<MessageType> {
    private static final zzfea zzpaj = zzfea.zzcuz();

    @Override // com.google.android.gms.internal.zzffm
    public final /* synthetic */ Object zzc(zzfdq zzfdqVar, zzfea zzfeaVar) throws zzfew {
        zzffi zzffiVar = (zzffi) zze(zzfdqVar, zzfeaVar);
        if (zzffiVar == null || zzffiVar.isInitialized()) {
            return zzffiVar;
        }
        throw (zzffiVar instanceof zzfcz ? new zzfgh((zzfcz) zzffiVar) : zzffiVar instanceof zzfdb ? new zzfgh((zzfdb) zzffiVar) : new zzfgh(zzffiVar)).zzcwt().zzh(zzffiVar);
    }
}
