package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public final class zza implements ServiceConnection {
    private boolean zzfhw = false;
    private final BlockingQueue<IBinder> zzfhx = new LinkedBlockingQueue();

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.zzfhx.add(iBinder);
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
    }

    public final IBinder zza(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        zzbq.zzgi("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
        if (this.zzfhw) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.zzfhw = true;
        IBinder poll = this.zzfhx.poll(10000L, timeUnit);
        if (poll != null) {
            return poll;
        }
        throw new TimeoutException("Timed out waiting for the service connection");
    }

    public final IBinder zzafl() throws InterruptedException {
        zzbq.zzgi("BlockingServiceConnection.getService() called on main thread");
        if (this.zzfhw) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.zzfhw = true;
        return this.zzfhx.take();
    }
}
