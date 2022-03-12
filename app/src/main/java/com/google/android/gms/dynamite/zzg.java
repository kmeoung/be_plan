package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

/* loaded from: classes.dex */
final class zzg implements DynamiteModule.zzd {
    @Override // com.google.android.gms.dynamite.DynamiteModule.zzd
    public final zzj zza(Context context, String str, zzi zziVar) throws DynamiteModule.zzc {
        zzj zzjVar = new zzj();
        zzjVar.zzgum = zziVar.zzab(context, str);
        zzjVar.zzgun = zzjVar.zzgum != 0 ? zziVar.zzc(context, str, false) : zziVar.zzc(context, str, true);
        if (zzjVar.zzgum == 0 && zzjVar.zzgun == 0) {
            zzjVar.zzguo = 0;
            return zzjVar;
        } else if (zzjVar.zzgun >= zzjVar.zzgum) {
            zzjVar.zzguo = 1;
            return zzjVar;
        } else {
            zzjVar.zzguo = -1;
            return zzjVar;
        }
    }
}
