package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.stats.zza;

/* loaded from: classes.dex */
public final class zzcjr implements ServiceConnection, zzf, zzg {
    final /* synthetic */ zzcjd zzjfo;
    private volatile boolean zzjfv;
    private volatile zzcgi zzjfw;

    public zzcjr(zzcjd zzcjdVar) {
        this.zzjfo = zzcjdVar;
    }

    @Override // com.google.android.gms.common.internal.zzf
    @MainThread
    public final void onConnected(@Nullable Bundle bundle) {
        zzbq.zzfz("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                this.zzjfw = null;
                this.zzjfo.zzawl().zzg(new zzcju(this, this.zzjfw.zzakb()));
            } catch (DeadObjectException | IllegalStateException unused) {
                this.zzjfw = null;
                this.zzjfv = false;
            }
        }
    }

    @Override // com.google.android.gms.common.internal.zzg
    @MainThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zzbq.zzfz("MeasurementServiceConnection.onConnectionFailed");
        zzcgj zzazl = this.zzjfo.zzitk.zzazl();
        if (zzazl != null) {
            zzazl.zzayt().zzj("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzjfv = false;
            this.zzjfw = null;
        }
        this.zzjfo.zzawl().zzg(new zzcjw(this));
    }

    @Override // com.google.android.gms.common.internal.zzf
    @MainThread
    public final void onConnectionSuspended(int i) {
        zzbq.zzfz("MeasurementServiceConnection.onConnectionSuspended");
        this.zzjfo.zzawm().zzayw().log("Service connection suspended");
        this.zzjfo.zzawl().zzg(new zzcjv(this));
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzcjr zzcjrVar;
        zzbq.zzfz("MeasurementServiceConnection.onServiceConnected");
        synchronized (this) {
            if (iBinder == null) {
                this.zzjfv = false;
                this.zzjfo.zzawm().zzayr().log("Service connected with null binder");
                return;
            }
            zzcgb zzcgbVar = null;
            try {
                String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                if ("com.google.android.gms.measurement.internal.IMeasurementService".equals(interfaceDescriptor)) {
                    if (iBinder != null) {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
                        zzcgbVar = queryLocalInterface instanceof zzcgb ? (zzcgb) queryLocalInterface : new zzcgd(iBinder);
                    }
                    this.zzjfo.zzawm().zzayx().log("Bound to IMeasurementService interface");
                } else {
                    this.zzjfo.zzawm().zzayr().zzj("Got binder with a wrong descriptor", interfaceDescriptor);
                }
            } catch (RemoteException unused) {
                this.zzjfo.zzawm().zzayr().log("Service connect failed to get IMeasurementService");
            }
            if (zzcgbVar == null) {
                this.zzjfv = false;
                try {
                    zza.zzalq();
                    Context context = this.zzjfo.getContext();
                    zzcjrVar = this.zzjfo.zzjfh;
                    context.unbindService(zzcjrVar);
                } catch (IllegalArgumentException unused2) {
                }
            } else {
                this.zzjfo.zzawl().zzg(new zzcjs(this, zzcgbVar));
            }
        }
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        zzbq.zzfz("MeasurementServiceConnection.onServiceDisconnected");
        this.zzjfo.zzawm().zzayw().log("Service disconnected");
        this.zzjfo.zzawl().zzg(new zzcjt(this, componentName));
    }

    @WorkerThread
    public final void zzbai() {
        this.zzjfo.zzut();
        Context context = this.zzjfo.getContext();
        synchronized (this) {
            if (this.zzjfv) {
                this.zzjfo.zzawm().zzayx().log("Connection attempt already in progress");
            } else if (this.zzjfw != null) {
                this.zzjfo.zzawm().zzayx().log("Already awaiting connection attempt");
            } else {
                this.zzjfw = new zzcgi(context, Looper.getMainLooper(), this, this);
                this.zzjfo.zzawm().zzayx().log("Connecting to remote service");
                this.zzjfv = true;
                this.zzjfw.zzajx();
            }
        }
    }

    @WorkerThread
    public final void zzn(Intent intent) {
        zzcjr zzcjrVar;
        this.zzjfo.zzut();
        Context context = this.zzjfo.getContext();
        zza zzalq = zza.zzalq();
        synchronized (this) {
            if (this.zzjfv) {
                this.zzjfo.zzawm().zzayx().log("Connection attempt already in progress");
                return;
            }
            this.zzjfo.zzawm().zzayx().log("Using local app measurement service");
            this.zzjfv = true;
            zzcjrVar = this.zzjfo.zzjfh;
            zzalq.zza(context, intent, zzcjrVar, 129);
        }
    }
}
