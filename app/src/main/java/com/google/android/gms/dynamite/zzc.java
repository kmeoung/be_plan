package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

/* loaded from: classes.dex */
final class zzc implements DynamiteModule.zzd {
    @Override // com.google.android.gms.dynamite.DynamiteModule.zzd
    public final zzj zza(Context context, String str, zzi zziVar) throws DynamiteModule.zzc {
        zzj zzjVar = new zzj();
        zzjVar.zzgum = zziVar.zzab(context, str);
        if (zzjVar.zzgum != 0) {
            zzjVar.zzguo = -1;
            return zzjVar;
        }
        zzjVar.zzgun = zziVar.zzc(context, str, true);
        if (zzjVar.zzgun != 0) {
            zzjVar.zzguo = 1;
        }
        return zzjVar;
    }
}
