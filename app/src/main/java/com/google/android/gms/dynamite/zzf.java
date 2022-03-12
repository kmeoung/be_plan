package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

/* loaded from: classes.dex */
final class zzf implements DynamiteModule.zzd {
    @Override // com.google.android.gms.dynamite.DynamiteModule.zzd
    public final zzj zza(Context context, String str, zzi zziVar) throws DynamiteModule.zzc {
        int i;
        zzj zzjVar = new zzj();
        zzjVar.zzgum = zziVar.zzab(context, str);
        zzjVar.zzgun = zziVar.zzc(context, str, true);
        if (zzjVar.zzgum == 0 && zzjVar.zzgun == 0) {
            i = 0;
        } else if (zzjVar.zzgun >= zzjVar.zzgum) {
            zzjVar.zzguo = 1;
            return zzjVar;
        } else {
            i = -1;
        }
        zzjVar.zzguo = i;
        return zzjVar;
    }
}
