package com.google.firebase.iid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class zzm implements ServiceConnection {
    int state;
    final Messenger zznuh;
    zzr zznui;
    final Queue<zzt<?>> zznuj;
    final SparseArray<zzt<?>> zznuk;
    final /* synthetic */ zzk zznul;

    private zzm(zzk zzkVar) {
        this.zznul = zzkVar;
        this.state = 0;
        this.zznuh = new Messenger(new Handler(Looper.getMainLooper(), new Handler.Callback(this) { // from class: com.google.firebase.iid.zzn
            private final zzm zznum;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.zznum = this;
            }

            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.zznum.zzd(message);
            }
        }));
        this.zznuj = new LinkedList();
        this.zznuk = new SparseArray<>();
    }

    private final void zza(zzu zzuVar) {
        for (zzt<?> zztVar : this.zznuj) {
            zztVar.zzb(zzuVar);
        }
        this.zznuj.clear();
        for (int i = 0; i < this.zznuk.size(); i++) {
            this.zznuk.valueAt(i).zzb(zzuVar);
        }
        this.zznuk.clear();
    }

    private final void zzchg() {
        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = this.zznul.zznue;
        scheduledExecutorService.execute(new Runnable(this) { // from class: com.google.firebase.iid.zzp
            private final zzm zznum;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.zznum = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                ScheduledExecutorService scheduledExecutorService2;
                Context context;
                final zzm zzmVar = this.zznum;
                while (true) {
                    synchronized (zzmVar) {
                        if (zzmVar.state == 2) {
                            if (zzmVar.zznuj.isEmpty()) {
                                zzmVar.zzchh();
                                return;
                            }
                            final zzt poll = zzmVar.zznuj.poll();
                            zzmVar.zznuk.put(poll.zzicx, poll);
                            scheduledExecutorService2 = zzmVar.zznul.zznue;
                            scheduledExecutorService2.schedule(new Runnable(zzmVar, poll) { // from class: com.google.firebase.iid.zzq
                                private final zzm zznum;
                                private final zzt zznun;

                                /* JADX INFO: Access modifiers changed from: package-private */
                                {
                                    this.zznum = zzmVar;
                                    this.zznun = poll;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.zznum.zzht(this.zznun.zzicx);
                                }
                            }, 30L, TimeUnit.SECONDS);
                            if (Log.isLoggable("MessengerIpcClient", 3)) {
                                String valueOf = String.valueOf(poll);
                                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 8);
                                sb.append("Sending ");
                                sb.append(valueOf);
                                Log.d("MessengerIpcClient", sb.toString());
                            }
                            context = zzmVar.zznul.zzaif;
                            Messenger messenger = zzmVar.zznuh;
                            Message obtain = Message.obtain();
                            obtain.what = poll.what;
                            obtain.arg1 = poll.zzicx;
                            obtain.replyTo = messenger;
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("oneWay", poll.zzchj());
                            bundle.putString("pkg", context.getPackageName());
                            bundle.putBundle("data", poll.zznuq);
                            obtain.setData(bundle);
                            try {
                                zzr zzrVar = zzmVar.zznui;
                                if (zzrVar.zzicr == null) {
                                    if (zzrVar.zznup == null) {
                                        throw new IllegalStateException("Both messengers are null");
                                        break;
                                    }
                                    zzrVar.zznup.send(obtain);
                                } else {
                                    zzrVar.zzicr.send(obtain);
                                }
                            } catch (RemoteException e) {
                                zzmVar.zzm(2, e.getMessage());
                            }
                        } else {
                            return;
                        }
                    }
                }
            }
        });
    }

    @Override // android.content.ServiceConnection
    public final synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service connected");
        }
        if (iBinder == null) {
            zzm(0, "Null service connection");
            return;
        }
        try {
            this.zznui = new zzr(iBinder);
            this.state = 2;
            zzchg();
        } catch (RemoteException e) {
            zzm(0, e.getMessage());
        }
    }

    @Override // android.content.ServiceConnection
    public final synchronized void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service disconnected");
        }
        zzm(2, "Service disconnected");
    }

    public final synchronized boolean zzb(zzt zztVar) {
        Context context;
        ScheduledExecutorService scheduledExecutorService;
        switch (this.state) {
            case 0:
                this.zznuj.add(zztVar);
                zzbq.checkState(this.state == 0);
                if (Log.isLoggable("MessengerIpcClient", 2)) {
                    Log.v("MessengerIpcClient", "Starting bind to GmsCore");
                }
                this.state = 1;
                Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                intent.setPackage("com.google.android.gms");
                zza zzalq = zza.zzalq();
                context = this.zznul.zzaif;
                if (!zzalq.zza(context, intent, this, 1)) {
                    zzm(0, "Unable to bind to service");
                } else {
                    scheduledExecutorService = this.zznul.zznue;
                    scheduledExecutorService.schedule(new Runnable(this) { // from class: com.google.firebase.iid.zzo
                        private final zzm zznum;

                        /* JADX INFO: Access modifiers changed from: package-private */
                        {
                            this.zznum = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            this.zznum.zzchi();
                        }
                    }, 30L, TimeUnit.SECONDS);
                }
                return true;
            case 1:
                this.zznuj.add(zztVar);
                return true;
            case 2:
                this.zznuj.add(zztVar);
                zzchg();
                return true;
            case 3:
            case 4:
                return false;
            default:
                int i = this.state;
                StringBuilder sb = new StringBuilder(26);
                sb.append("Unknown state: ");
                sb.append(i);
                throw new IllegalStateException(sb.toString());
        }
    }

    public final synchronized void zzchh() {
        Context context;
        if (this.state == 2 && this.zznuj.isEmpty() && this.zznuk.size() == 0) {
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Finished handling requests, unbinding");
            }
            this.state = 3;
            zza.zzalq();
            context = this.zznul.zzaif;
            context.unbindService(this);
        }
    }

    public final synchronized void zzchi() {
        if (this.state == 1) {
            zzm(1, "Timed out while binding");
        }
    }

    public final boolean zzd(Message message) {
        int i = message.arg1;
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            StringBuilder sb = new StringBuilder(41);
            sb.append("Received response to request: ");
            sb.append(i);
            Log.d("MessengerIpcClient", sb.toString());
        }
        synchronized (this) {
            zzt<?> zztVar = this.zznuk.get(i);
            if (zztVar == null) {
                StringBuilder sb2 = new StringBuilder(50);
                sb2.append("Received response for unknown request: ");
                sb2.append(i);
                Log.w("MessengerIpcClient", sb2.toString());
                return true;
            }
            this.zznuk.remove(i);
            zzchh();
            Bundle data = message.getData();
            if (data.getBoolean("unsupported", false)) {
                zztVar.zzb(new zzu(4, "Not supported by GmsCore"));
                return true;
            }
            zztVar.zzad(data);
            return true;
        }
    }

    public final synchronized void zzht(int i) {
        zzt<?> zztVar = this.zznuk.get(i);
        if (zztVar != null) {
            StringBuilder sb = new StringBuilder(31);
            sb.append("Timing out request: ");
            sb.append(i);
            Log.w("MessengerIpcClient", sb.toString());
            this.zznuk.remove(i);
            zztVar.zzb(new zzu(3, "Timed out waiting for response"));
            zzchh();
        }
    }

    public final synchronized void zzm(int i, String str) {
        Context context;
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(str);
            Log.d("MessengerIpcClient", valueOf.length() != 0 ? "Disconnected: ".concat(valueOf) : new String("Disconnected: "));
        }
        switch (this.state) {
            case 0:
                throw new IllegalStateException();
            case 1:
            case 2:
                if (Log.isLoggable("MessengerIpcClient", 2)) {
                    Log.v("MessengerIpcClient", "Unbinding service");
                }
                this.state = 4;
                zza.zzalq();
                context = this.zznul.zzaif;
                context.unbindService(this);
                zza(new zzu(i, str));
                return;
            case 3:
                this.state = 4;
                return;
            case 4:
                return;
            default:
                int i2 = this.state;
                StringBuilder sb = new StringBuilder(26);
                sb.append("Unknown state: ");
                sb.append(i2);
                throw new IllegalStateException(sb.toString());
        }
    }
}
