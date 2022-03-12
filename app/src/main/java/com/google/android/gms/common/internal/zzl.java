package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes.dex */
public final class zzl implements ServiceConnection {
    private /* synthetic */ zzd zzfwg;
    private final int zzfwj;

    public zzl(zzd zzdVar, int i) {
        this.zzfwg = zzdVar;
        this.zzfwj = i;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Object obj;
        zzay zzayVar;
        if (iBinder == null) {
            this.zzfwg.zzcf(16);
            return;
        }
        obj = this.zzfwg.zzfvq;
        synchronized (obj) {
            zzd zzdVar = this.zzfwg;
            if (iBinder == null) {
                zzayVar = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                zzayVar = (queryLocalInterface == null || !(queryLocalInterface instanceof zzay)) ? new zzaz(iBinder) : (zzay) queryLocalInterface;
            }
            zzdVar.zzfvr = zzayVar;
        }
        this.zzfwg.zza(0, (Bundle) null, this.zzfwj);
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        Object obj;
        obj = this.zzfwg.zzfvq;
        synchronized (obj) {
            this.zzfwg.zzfvr = null;
        }
        this.zzfwg.mHandler.sendMessage(this.zzfwg.mHandler.obtainMessage(6, this.zzfwj, 1));
    }
}
