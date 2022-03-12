package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.android.gms.common.stats.zza;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/* loaded from: classes.dex */
public final class zzh implements ServiceConnection {
    private final Context zzaif;
    private final Intent zzicf;
    private final ScheduledExecutorService zzicg;
    private final Queue<zzd> zzich;
    private boolean zzicj;
    private zzf zzntr;

    public zzh(Context context, String str) {
        this(context, str, new ScheduledThreadPoolExecutor(0));
    }

    @VisibleForTesting
    private zzh(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
        this.zzich = new LinkedList();
        this.zzicj = false;
        this.zzaif = context.getApplicationContext();
        this.zzicf = new Intent(str).setPackage(this.zzaif.getPackageName());
        this.zzicg = scheduledExecutorService;
    }

    private final synchronized void zzaur() {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "flush queue called");
        }
        while (!this.zzich.isEmpty()) {
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                Log.d("EnhancedIntentService", "found intent to be delivered");
            }
            if (this.zzntr == null || !this.zzntr.isBinderAlive()) {
                if (Log.isLoggable("EnhancedIntentService", 3)) {
                    StringBuilder sb = new StringBuilder(39);
                    sb.append("binder is dead. start connection? ");
                    sb.append(!this.zzicj);
                    Log.d("EnhancedIntentService", sb.toString());
                }
                if (!this.zzicj) {
                    this.zzicj = true;
                    try {
                    } catch (SecurityException e) {
                        Log.e("EnhancedIntentService", "Exception while binding the service", e);
                    }
                    if (!zza.zzalq().zza(this.zzaif, this.zzicf, this, 65)) {
                        Log.e("EnhancedIntentService", "binding to the service failed");
                        while (!this.zzich.isEmpty()) {
                            this.zzich.poll().finish();
                        }
                    } else {
                        return;
                    }
                }
                return;
            }
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                Log.d("EnhancedIntentService", "binder is alive, sending the intent.");
            }
            this.zzntr.zza(this.zzich.poll());
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this) {
            this.zzicj = false;
            this.zzntr = (zzf) iBinder;
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                String valueOf = String.valueOf(componentName);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20);
                sb.append("onServiceConnected: ");
                sb.append(valueOf);
                Log.d("EnhancedIntentService", sb.toString());
            }
            zzaur();
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            String valueOf = String.valueOf(componentName);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
            sb.append("onServiceDisconnected: ");
            sb.append(valueOf);
            Log.d("EnhancedIntentService", sb.toString());
        }
        zzaur();
    }

    public final synchronized void zza(Intent intent, BroadcastReceiver.PendingResult pendingResult) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "new intent queued in the bind-strategy delivery");
        }
        this.zzich.add(new zzd(intent, pendingResult, this.zzicg));
        zzaur();
    }
}
