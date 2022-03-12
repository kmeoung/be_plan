package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor;

/* loaded from: classes.dex */
public final class zzbzu {
    private boolean zzaqh = false;
    private zzbzv zzhfz = null;

    public final void initialize(Context context) {
        synchronized (this) {
            if (!this.zzaqh) {
                try {
                    this.zzhfz = zzbzw.asInterface(DynamiteModule.zza(context, DynamiteModule.zzguh, ModuleDescriptor.MODULE_ID).zzgw("com.google.android.gms.flags.impl.FlagProviderImpl"));
                    this.zzhfz.init(zzn.zzy(context));
                    this.zzaqh = true;
                } catch (RemoteException | DynamiteModule.zzc e) {
                    Log.w("FlagValueProvider", "Failed to initialize flags module.", e);
                }
            }
        }
    }

    public final <T> T zzb(zzbzn<T> zzbznVar) {
        synchronized (this) {
            if (this.zzaqh) {
                return zzbznVar.zza(this.zzhfz);
            }
            return zzbznVar.zzip();
        }
    }
}
