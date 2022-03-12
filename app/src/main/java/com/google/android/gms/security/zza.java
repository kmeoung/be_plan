package com.google.android.gms.security;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.zze;
import com.google.android.gms.security.ProviderInstaller;

/* loaded from: classes.dex */
final class zza extends AsyncTask<Void, Void, Integer> {
    private /* synthetic */ Context val$context;
    private /* synthetic */ ProviderInstaller.ProviderInstallListener zzjyw;

    public zza(Context context, ProviderInstaller.ProviderInstallListener providerInstallListener) {
        this.val$context = context;
        this.zzjyw = providerInstallListener;
    }

    /* renamed from: zzb */
    public final Integer doInBackground(Void... voidArr) {
        int connectionStatusCode;
        try {
            ProviderInstaller.installIfNeeded(this.val$context);
            connectionStatusCode = 0;
        } catch (GooglePlayServicesNotAvailableException e) {
            connectionStatusCode = e.errorCode;
        } catch (GooglePlayServicesRepairableException e2) {
            connectionStatusCode = e2.getConnectionStatusCode();
        }
        return Integer.valueOf(connectionStatusCode);
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ void onPostExecute(Integer num) {
        zze unused;
        Integer num2 = num;
        if (num2.intValue() == 0) {
            this.zzjyw.onProviderInstalled();
            return;
        }
        unused = ProviderInstaller.zzjyu;
        this.zzjyw.onProviderInstallFailed(num2.intValue(), zze.zza(this.val$context, num2.intValue(), "pi"));
    }
}
