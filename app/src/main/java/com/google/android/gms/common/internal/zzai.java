package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import com.google.android.gms.common.stats.zza;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class zzai extends zzag implements Handler.Callback {
    private final Context mApplicationContext;
    private final Handler mHandler;
    private final HashMap<zzah, zzaj> zzfxs = new HashMap<>();
    private final zza zzfxt = zza.zzalq();
    private final long zzfxu = 5000;
    private final long zzfxv = 300000;

    public zzai(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), this);
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                synchronized (this.zzfxs) {
                    zzah zzahVar = (zzah) message.obj;
                    zzaj zzajVar = this.zzfxs.get(zzahVar);
                    if (zzajVar != null && zzajVar.zzala()) {
                        if (zzajVar.isBound()) {
                            zzajVar.zzge("GmsClientSupervisor");
                        }
                        this.zzfxs.remove(zzahVar);
                    }
                }
                return true;
            case 1:
                synchronized (this.zzfxs) {
                    zzah zzahVar2 = (zzah) message.obj;
                    zzaj zzajVar2 = this.zzfxs.get(zzahVar2);
                    if (zzajVar2 != null && zzajVar2.getState() == 3) {
                        String valueOf = String.valueOf(zzahVar2);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47);
                        sb.append("Timeout waiting for ServiceConnection callback ");
                        sb.append(valueOf);
                        Log.wtf("GmsClientSupervisor", sb.toString(), new Exception());
                        ComponentName componentName = zzajVar2.getComponentName();
                        if (componentName == null) {
                            componentName = zzahVar2.getComponentName();
                        }
                        if (componentName == null) {
                            componentName = new ComponentName(zzahVar2.getPackage(), EnvironmentCompat.MEDIA_UNKNOWN);
                        }
                        zzajVar2.onServiceDisconnected(componentName);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    @Override // com.google.android.gms.common.internal.zzag
    public final boolean zza(zzah zzahVar, ServiceConnection serviceConnection, String str) {
        boolean isBound;
        zzbq.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zzfxs) {
            zzaj zzajVar = this.zzfxs.get(zzahVar);
            if (zzajVar != null) {
                this.mHandler.removeMessages(0, zzahVar);
                if (!zzajVar.zza(serviceConnection)) {
                    zzajVar.zza(serviceConnection, str);
                    switch (zzajVar.getState()) {
                        case 1:
                            serviceConnection.onServiceConnected(zzajVar.getComponentName(), zzajVar.getBinder());
                            break;
                        case 2:
                            zzajVar.zzgd(str);
                            break;
                    }
                } else {
                    String valueOf = String.valueOf(zzahVar);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 81);
                    sb.append("Trying to bind a GmsServiceConnection that was already connected before.  config=");
                    sb.append(valueOf);
                    throw new IllegalStateException(sb.toString());
                }
            } else {
                zzajVar = new zzaj(this, zzahVar);
                zzajVar.zza(serviceConnection, str);
                zzajVar.zzgd(str);
                this.zzfxs.put(zzahVar, zzajVar);
            }
            isBound = zzajVar.isBound();
        }
        return isBound;
    }

    @Override // com.google.android.gms.common.internal.zzag
    protected final void zzb(zzah zzahVar, ServiceConnection serviceConnection, String str) {
        zzbq.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zzfxs) {
            zzaj zzajVar = this.zzfxs.get(zzahVar);
            if (zzajVar == null) {
                String valueOf = String.valueOf(zzahVar);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 50);
                sb.append("Nonexistent connection status for service config: ");
                sb.append(valueOf);
                throw new IllegalStateException(sb.toString());
            } else if (!zzajVar.zza(serviceConnection)) {
                String valueOf2 = String.valueOf(zzahVar);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 76);
                sb2.append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=");
                sb2.append(valueOf2);
                throw new IllegalStateException(sb2.toString());
            } else {
                zzajVar.zzb(serviceConnection, str);
                if (zzajVar.zzala()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, zzahVar), this.zzfxu);
                }
            }
        }
    }
}
