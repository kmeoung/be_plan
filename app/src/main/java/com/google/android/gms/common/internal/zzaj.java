package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.google.android.gms.common.stats.zza;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public final class zzaj implements ServiceConnection {
    private ComponentName mComponentName;
    private IBinder zzfwl;
    private boolean zzfxx;
    private final zzah zzfxy;
    private /* synthetic */ zzai zzfxz;
    private final Set<ServiceConnection> zzfxw = new HashSet();
    private int mState = 2;

    public zzaj(zzai zzaiVar, zzah zzahVar) {
        this.zzfxz = zzaiVar;
        this.zzfxy = zzahVar;
    }

    public final IBinder getBinder() {
        return this.zzfwl;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean isBound() {
        return this.zzfxx;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        HashMap hashMap;
        Handler handler;
        hashMap = this.zzfxz.zzfxs;
        synchronized (hashMap) {
            handler = this.zzfxz.mHandler;
            handler.removeMessages(1, this.zzfxy);
            this.zzfwl = iBinder;
            this.mComponentName = componentName;
            for (ServiceConnection serviceConnection : this.zzfxw) {
                serviceConnection.onServiceConnected(componentName, iBinder);
            }
            this.mState = 1;
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        HashMap hashMap;
        Handler handler;
        hashMap = this.zzfxz.zzfxs;
        synchronized (hashMap) {
            handler = this.zzfxz.mHandler;
            handler.removeMessages(1, this.zzfxy);
            this.zzfwl = null;
            this.mComponentName = componentName;
            for (ServiceConnection serviceConnection : this.zzfxw) {
                serviceConnection.onServiceDisconnected(componentName);
            }
            this.mState = 2;
        }
    }

    public final void zza(ServiceConnection serviceConnection, String str) {
        zza unused;
        Context unused2;
        unused = this.zzfxz.zzfxt;
        unused2 = this.zzfxz.mApplicationContext;
        this.zzfxy.zzakz();
        this.zzfxw.add(serviceConnection);
    }

    public final boolean zza(ServiceConnection serviceConnection) {
        return this.zzfxw.contains(serviceConnection);
    }

    public final boolean zzala() {
        return this.zzfxw.isEmpty();
    }

    public final void zzb(ServiceConnection serviceConnection, String str) {
        zza unused;
        Context unused2;
        unused = this.zzfxz.zzfxt;
        unused2 = this.zzfxz.mApplicationContext;
        this.zzfxw.remove(serviceConnection);
    }

    public final void zzgd(String str) {
        zza zzaVar;
        Context context;
        Context context2;
        Handler handler;
        Handler handler2;
        long j;
        zza unused;
        this.mState = 3;
        zzaVar = this.zzfxz.zzfxt;
        context = this.zzfxz.mApplicationContext;
        this.zzfxx = zzaVar.zza(context, str, this.zzfxy.zzakz(), this, this.zzfxy.zzaky());
        if (this.zzfxx) {
            handler = this.zzfxz.mHandler;
            Message obtainMessage = handler.obtainMessage(1, this.zzfxy);
            handler2 = this.zzfxz.mHandler;
            j = this.zzfxz.zzfxv;
            handler2.sendMessageDelayed(obtainMessage, j);
            return;
        }
        this.mState = 2;
        try {
            unused = this.zzfxz.zzfxt;
            context2 = this.zzfxz.mApplicationContext;
            context2.unbindService(this);
        } catch (IllegalArgumentException unused2) {
        }
    }

    public final void zzge(String str) {
        Handler handler;
        Context context;
        zza unused;
        handler = this.zzfxz.mHandler;
        handler.removeMessages(1, this.zzfxy);
        unused = this.zzfxz.zzfxt;
        context = this.zzfxz.mApplicationContext;
        context.unbindService(this);
        this.zzfxx = false;
        this.mState = 2;
    }
}
