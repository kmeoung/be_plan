package com.google.android.gms.internal;

import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class zzaq extends zzp<String> {
    private final zzv<String> zzcd;

    public zzaq(int i, String str, zzv<String> zzvVar, zzu zzuVar) {
        super(i, str, zzuVar);
        this.zzcd = zzvVar;
    }

    @Override // com.google.android.gms.internal.zzp
    public final zzt<String> zza(zzn zznVar) {
        String str;
        try {
            str = new String(zznVar.data, zzal.zza(zznVar.zzy));
        } catch (UnsupportedEncodingException unused) {
            str = new String(zznVar.data);
        }
        return zzt.zza(str, zzal.zzb(zznVar));
    }

    @Override // com.google.android.gms.internal.zzp
    public final /* synthetic */ void zza(String str) {
        String str2 = str;
        if (this.zzcd != null) {
            this.zzcd.zzb(str2);
        }
    }
}
