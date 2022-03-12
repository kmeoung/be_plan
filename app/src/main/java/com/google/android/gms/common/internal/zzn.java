package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes.dex */
public final class zzn extends zze {
    private /* synthetic */ zzd zzfwg;
    private IBinder zzfwk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @BinderThread
    public zzn(zzd zzdVar, int i, IBinder iBinder, Bundle bundle) {
        super(zzdVar, i, bundle);
        this.zzfwg = zzdVar;
        this.zzfwk = iBinder;
    }

    @Override // com.google.android.gms.common.internal.zze
    protected final boolean zzakf() {
        boolean zza;
        zzf zzfVar;
        zzf zzfVar2;
        boolean zza2;
        try {
            String interfaceDescriptor = this.zzfwk.getInterfaceDescriptor();
            if (!this.zzfwg.zzhg().equals(interfaceDescriptor)) {
                String zzhg = this.zzfwg.zzhg();
                StringBuilder sb = new StringBuilder(String.valueOf(zzhg).length() + 34 + String.valueOf(interfaceDescriptor).length());
                sb.append("service descriptor mismatch: ");
                sb.append(zzhg);
                sb.append(" vs. ");
                sb.append(interfaceDescriptor);
                Log.e("GmsClient", sb.toString());
                return false;
            }
            IInterface zzd = this.zzfwg.zzd(this.zzfwk);
            if (zzd == null) {
                return false;
            }
            zza = this.zzfwg.zza(2, 4, (int) zzd);
            if (!zza) {
                zza2 = this.zzfwg.zza(3, 4, (int) zzd);
                if (!zza2) {
                    return false;
                }
            }
            this.zzfwg.zzfwb = null;
            Bundle zzaew = this.zzfwg.zzaew();
            zzfVar = this.zzfwg.zzfvx;
            if (zzfVar != null) {
                zzfVar2 = this.zzfwg.zzfvx;
                zzfVar2.onConnected(zzaew);
            }
            return true;
        } catch (RemoteException unused) {
            Log.w("GmsClient", "service probably died");
            return false;
        }
    }

    @Override // com.google.android.gms.common.internal.zze
    protected final void zzj(ConnectionResult connectionResult) {
        zzg zzgVar;
        zzg zzgVar2;
        zzgVar = this.zzfwg.zzfvy;
        if (zzgVar != null) {
            zzgVar2 = this.zzfwg.zzfvy;
            zzgVar2.onConnectionFailed(connectionResult);
        }
        this.zzfwg.onConnectionFailed(connectionResult);
    }
}
