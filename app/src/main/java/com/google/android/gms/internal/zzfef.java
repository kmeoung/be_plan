package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfee;
import com.google.android.gms.internal.zzfef;
import java.io.IOException;

/* loaded from: classes.dex */
public class zzfef<MessageType extends zzfee<MessageType, BuilderType>, BuilderType extends zzfef<MessageType, BuilderType>> extends zzfda<MessageType, BuilderType> {
    private final MessageType zzpbu;
    protected MessageType zzpbv;
    private boolean zzpbw = false;

    public zzfef(MessageType messagetype) {
        this.zzpbu = messagetype;
        this.zzpbv = (MessageType) ((zzfee) messagetype.zza(zzfem.zzpcg, null, null));
    }

    private static void zza(MessageType messagetype, MessageType messagetype2) {
        zzfel zzfelVar = zzfel.zzpcb;
        messagetype.zza(zzfem.zzpcd, zzfelVar, messagetype2);
        messagetype.zzpbs = zzfelVar.zza(messagetype.zzpbs, messagetype2.zzpbs);
    }

    /* renamed from: zzd */
    public final BuilderType zzb(zzfdq zzfdqVar, zzfea zzfeaVar) throws IOException {
        zzcvi();
        try {
            this.zzpbv.zza(zzfem.zzpce, zzfdqVar, zzfeaVar);
            return this;
        } catch (RuntimeException e) {
            if (e.getCause() instanceof IOException) {
                throw ((IOException) e.getCause());
            }
            throw e;
        }
    }

    @Override // com.google.android.gms.internal.zzfda
    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzfef zzfefVar = (zzfef) this.zzpbu.zza(zzfem.zzpch, null, null);
        if (!this.zzpbw) {
            MessageType messagetype = this.zzpbv;
            messagetype.zza(zzfem.zzpcf, null, null);
            messagetype.zzpbs.zzbim();
            this.zzpbw = true;
        }
        zzfefVar.zza((zzfef) this.zzpbv);
        return zzfefVar;
    }

    @Override // com.google.android.gms.internal.zzffk
    public final boolean isInitialized() {
        return this.zzpbv.zza(zzfem.zzpcc, false, null) != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.zzfda
    protected final /* synthetic */ zzfda zza(zzfcz zzfczVar) {
        return zza((zzfef<MessageType, BuilderType>) ((zzfee) zzfczVar));
    }

    @Override // com.google.android.gms.internal.zzfda
    public final /* synthetic */ zzfda zza(zzfdq zzfdqVar, zzfea zzfeaVar) throws IOException {
        return (zzfef) zzb(zzfdqVar, zzfeaVar);
    }

    public final BuilderType zza(MessageType messagetype) {
        zzcvi();
        zza(this.zzpbv, messagetype);
        return this;
    }

    @Override // com.google.android.gms.internal.zzfda
    public final /* synthetic */ zzfda zzctg() {
        return (zzfef) clone();
    }

    @Override // com.google.android.gms.internal.zzffk
    public final /* synthetic */ zzffi zzcvh() {
        return this.zzpbu;
    }

    public final void zzcvi() {
        if (this.zzpbw) {
            MessageType messagetype = (MessageType) ((zzfee) this.zzpbv.zza(zzfem.zzpcg, null, null));
            zza(messagetype, this.zzpbv);
            this.zzpbv = messagetype;
            this.zzpbw = false;
        }
    }

    public final MessageType zzcvj() {
        if (this.zzpbw) {
            return this.zzpbv;
        }
        MessageType messagetype = this.zzpbv;
        messagetype.zza(zzfem.zzpcf, null, null);
        messagetype.zzpbs.zzbim();
        this.zzpbw = true;
        return this.zzpbv;
    }

    public final MessageType zzcvk() {
        boolean z = true;
        if (!this.zzpbw) {
            MessageType messagetype = this.zzpbv;
            messagetype.zza(zzfem.zzpcf, null, null);
            messagetype.zzpbs.zzbim();
            this.zzpbw = true;
        }
        MessageType messagetype2 = this.zzpbv;
        if (messagetype2.zza(zzfem.zzpcc, Boolean.TRUE, null) == null) {
            z = false;
        }
        if (z) {
            return messagetype2;
        }
        throw new zzfgh(messagetype2);
    }

    @Override // com.google.android.gms.internal.zzffj
    public final /* synthetic */ zzffi zzcvl() {
        if (this.zzpbw) {
            return this.zzpbv;
        }
        MessageType messagetype = this.zzpbv;
        messagetype.zza(zzfem.zzpcf, null, null);
        messagetype.zzpbs.zzbim();
        this.zzpbw = true;
        return this.zzpbv;
    }

    @Override // com.google.android.gms.internal.zzffj
    public final /* synthetic */ zzffi zzcvm() {
        boolean z = true;
        if (!this.zzpbw) {
            MessageType messagetype = this.zzpbv;
            messagetype.zza(zzfem.zzpcf, null, null);
            messagetype.zzpbs.zzbim();
            this.zzpbw = true;
        }
        MessageType messagetype2 = this.zzpbv;
        if (messagetype2.zza(zzfem.zzpcc, Boolean.TRUE, null) == null) {
            z = false;
        }
        if (z) {
            return messagetype2;
        }
        throw new zzfgh(messagetype2);
    }
}
