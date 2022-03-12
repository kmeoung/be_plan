package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public abstract class zzd<T extends IInterface> {
    private static String[] zzfwe = {"service_esmobile", "service_googleme"};
    private final Context mContext;
    final Handler mHandler;
    private final Object mLock;
    private final Looper zzakm;
    private final zze zzfni;
    private int zzfvj;
    private long zzfvk;
    private long zzfvl;
    private int zzfvm;
    private long zzfvn;
    private zzam zzfvo;
    private final zzag zzfvp;
    private final Object zzfvq;
    private zzay zzfvr;
    protected zzj zzfvs;
    private T zzfvt;
    private final ArrayList<zzi<?>> zzfvu;
    private zzl zzfvv;
    private int zzfvw;
    private final zzf zzfvx;
    private final zzg zzfvy;
    private final int zzfvz;
    private final String zzfwa;
    private ConnectionResult zzfwb;
    private boolean zzfwc;
    protected AtomicInteger zzfwd;

    public zzd(Context context, Looper looper, int i, zzf zzfVar, zzg zzgVar, String str) {
        this(context, looper, zzag.zzcl(context), zze.zzafm(), i, (zzf) zzbq.checkNotNull(zzfVar), (zzg) zzbq.checkNotNull(zzgVar), null);
    }

    protected zzd(Context context, Looper looper, zzag zzagVar, zze zzeVar, int i, zzf zzfVar, zzg zzgVar, String str) {
        this.mLock = new Object();
        this.zzfvq = new Object();
        this.zzfvu = new ArrayList<>();
        this.zzfvw = 1;
        this.zzfwb = null;
        this.zzfwc = false;
        this.zzfwd = new AtomicInteger(0);
        this.mContext = (Context) zzbq.checkNotNull(context, "Context must not be null");
        this.zzakm = (Looper) zzbq.checkNotNull(looper, "Looper must not be null");
        this.zzfvp = (zzag) zzbq.checkNotNull(zzagVar, "Supervisor must not be null");
        this.zzfni = (zze) zzbq.checkNotNull(zzeVar, "API availability must not be null");
        this.mHandler = new zzh(this, looper);
        this.zzfvz = i;
        this.zzfvx = zzfVar;
        this.zzfvy = zzgVar;
        this.zzfwa = str;
    }

    public final void zza(int i, T t) {
        boolean z = true;
        if ((i == 4) != (t != null)) {
            z = false;
        }
        zzbq.checkArgument(z);
        synchronized (this.mLock) {
            this.zzfvw = i;
            this.zzfvt = t;
            switch (i) {
                case 1:
                    if (this.zzfvv != null) {
                        this.zzfvp.zza(zzhf(), zzajv(), 129, this.zzfvv, zzajw());
                        this.zzfvv = null;
                        break;
                    }
                    break;
                case 2:
                case 3:
                    if (!(this.zzfvv == null || this.zzfvo == null)) {
                        String zzalc = this.zzfvo.zzalc();
                        String packageName = this.zzfvo.getPackageName();
                        StringBuilder sb = new StringBuilder(String.valueOf(zzalc).length() + 70 + String.valueOf(packageName).length());
                        sb.append("Calling connect() while still connected, missing disconnect() for ");
                        sb.append(zzalc);
                        sb.append(" on ");
                        sb.append(packageName);
                        Log.e("GmsClient", sb.toString());
                        this.zzfvp.zza(this.zzfvo.zzalc(), this.zzfvo.getPackageName(), this.zzfvo.zzaky(), this.zzfvv, zzajw());
                        this.zzfwd.incrementAndGet();
                    }
                    this.zzfvv = new zzl(this, this.zzfwd.get());
                    this.zzfvo = new zzam(zzajv(), zzhf(), false, 129);
                    if (!this.zzfvp.zza(new zzah(this.zzfvo.zzalc(), this.zzfvo.getPackageName(), this.zzfvo.zzaky()), this.zzfvv, zzajw())) {
                        String zzalc2 = this.zzfvo.zzalc();
                        String packageName2 = this.zzfvo.getPackageName();
                        StringBuilder sb2 = new StringBuilder(String.valueOf(zzalc2).length() + 34 + String.valueOf(packageName2).length());
                        sb2.append("unable to connect to service: ");
                        sb2.append(zzalc2);
                        sb2.append(" on ");
                        sb2.append(packageName2);
                        Log.e("GmsClient", sb2.toString());
                        zza(16, (Bundle) null, this.zzfwd.get());
                        break;
                    }
                    break;
                case 4:
                    zza((zzd<T>) t);
                    break;
            }
        }
    }

    public final boolean zza(int i, int i2, T t) {
        synchronized (this.mLock) {
            if (this.zzfvw != i) {
                return false;
            }
            zza(i2, (int) t);
            return true;
        }
    }

    @Nullable
    private final String zzajw() {
        return this.zzfwa == null ? this.mContext.getClass().getName() : this.zzfwa;
    }

    private final boolean zzajy() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzfvw == 3;
        }
        return z;
    }

    public final boolean zzake() {
        if (this.zzfwc || TextUtils.isEmpty(zzhg()) || TextUtils.isEmpty(null)) {
            return false;
        }
        try {
            Class.forName(zzhg());
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public final void zzcf(int i) {
        int i2;
        if (zzajy()) {
            i2 = 5;
            this.zzfwc = true;
        } else {
            i2 = 4;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(i2, this.zzfwd.get(), 16));
    }

    public void disconnect() {
        this.zzfwd.incrementAndGet();
        synchronized (this.zzfvu) {
            int size = this.zzfvu.size();
            for (int i = 0; i < size; i++) {
                this.zzfvu.get(i).removeListener();
            }
            this.zzfvu.clear();
        }
        synchronized (this.zzfvq) {
            this.zzfvr = null;
        }
        zza(1, (int) null);
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        T t;
        zzay zzayVar;
        String str2;
        String str3;
        synchronized (this.mLock) {
            i = this.zzfvw;
            t = this.zzfvt;
        }
        synchronized (this.zzfvq) {
            zzayVar = this.zzfvr;
        }
        printWriter.append((CharSequence) str).append("mConnectState=");
        switch (i) {
            case 1:
                str2 = "DISCONNECTED";
                break;
            case 2:
                str2 = "REMOTE_CONNECTING";
                break;
            case 3:
                str2 = "LOCAL_CONNECTING";
                break;
            case 4:
                str2 = "CONNECTED";
                break;
            case 5:
                str2 = "DISCONNECTING";
                break;
            default:
                str2 = "UNKNOWN";
                break;
        }
        printWriter.print(str2);
        printWriter.append(" mService=");
        if (t == null) {
            printWriter.append("null");
        } else {
            printWriter.append((CharSequence) zzhg()).append("@").append((CharSequence) Integer.toHexString(System.identityHashCode(t.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (zzayVar == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(zzayVar.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzfvl > 0) {
            PrintWriter append = printWriter.append((CharSequence) str).append("lastConnectedTime=");
            long j = this.zzfvl;
            String format = simpleDateFormat.format(new Date(this.zzfvl));
            StringBuilder sb = new StringBuilder(String.valueOf(format).length() + 21);
            sb.append(j);
            sb.append(" ");
            sb.append(format);
            append.println(sb.toString());
        }
        if (this.zzfvk > 0) {
            printWriter.append((CharSequence) str).append("lastSuspendedCause=");
            switch (this.zzfvj) {
                case 1:
                    str3 = "CAUSE_SERVICE_DISCONNECTED";
                    break;
                case 2:
                    str3 = "CAUSE_NETWORK_LOST";
                    break;
                default:
                    str3 = String.valueOf(this.zzfvj);
                    break;
            }
            printWriter.append((CharSequence) str3);
            PrintWriter append2 = printWriter.append(" lastSuspendedTime=");
            long j2 = this.zzfvk;
            String format2 = simpleDateFormat.format(new Date(this.zzfvk));
            StringBuilder sb2 = new StringBuilder(String.valueOf(format2).length() + 21);
            sb2.append(j2);
            sb2.append(" ");
            sb2.append(format2);
            append2.println(sb2.toString());
        }
        if (this.zzfvn > 0) {
            printWriter.append((CharSequence) str).append("lastFailedStatus=").append((CharSequence) CommonStatusCodes.getStatusCodeString(this.zzfvm));
            PrintWriter append3 = printWriter.append(" lastFailedTime=");
            long j3 = this.zzfvn;
            String format3 = simpleDateFormat.format(new Date(this.zzfvn));
            StringBuilder sb3 = new StringBuilder(String.valueOf(format3).length() + 21);
            sb3.append(j3);
            sb3.append(" ");
            sb3.append(format3);
            append3.println(sb3.toString());
        }
    }

    public Account getAccount() {
        return null;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zzakm;
    }

    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    public final boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzfvw == 4;
        }
        return z;
    }

    public final boolean isConnecting() {
        boolean z;
        synchronized (this.mLock) {
            if (!(this.zzfvw == 2 || this.zzfvw == 3)) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    @CallSuper
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzfvm = connectionResult.getErrorCode();
        this.zzfvn = System.currentTimeMillis();
    }

    @CallSuper
    public void onConnectionSuspended(int i) {
        this.zzfvj = i;
        this.zzfvk = System.currentTimeMillis();
    }

    public final void zza(int i, @Nullable Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, i2, -1, new zzo(this, i, null)));
    }

    public void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, i2, -1, new zzn(this, i, iBinder, bundle)));
    }

    @CallSuper
    protected void zza(@NonNull T t) {
        this.zzfvl = System.currentTimeMillis();
    }

    @WorkerThread
    public final void zza(zzan zzanVar, Set<Scope> set) {
        Bundle zzaae = zzaae();
        zzz zzzVar = new zzz(this.zzfvz);
        zzzVar.zzfwz = this.mContext.getPackageName();
        zzzVar.zzfxc = zzaae;
        if (set != null) {
            zzzVar.zzfxb = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (zzaam()) {
            zzzVar.zzfxd = getAccount() != null ? getAccount() : new Account("<<default account>>", "com.google");
            if (zzanVar != null) {
                zzzVar.zzfxa = zzanVar.asBinder();
            }
        } else if (zzakc()) {
            zzzVar.zzfxd = getAccount();
        }
        zzzVar.zzfxe = zzajz();
        try {
            try {
                synchronized (this.zzfvq) {
                    if (this.zzfvr != null) {
                        this.zzfvr.zza(new zzk(this, this.zzfwd.get()), zzzVar);
                    } else {
                        Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                    }
                }
            } catch (RemoteException | RuntimeException e) {
                Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
                zza(8, (IBinder) null, (Bundle) null, this.zzfwd.get());
            }
        } catch (DeadObjectException e2) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e2);
            zzce(1);
        } catch (SecurityException e3) {
            throw e3;
        }
    }

    public void zza(@NonNull zzj zzjVar) {
        this.zzfvs = (zzj) zzbq.checkNotNull(zzjVar, "Connection progress callbacks cannot be null.");
        zza(2, (int) null);
    }

    protected final void zza(@NonNull zzj zzjVar, int i, @Nullable PendingIntent pendingIntent) {
        this.zzfvs = (zzj) zzbq.checkNotNull(zzjVar, "Connection progress callbacks cannot be null.");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzfwd.get(), i, pendingIntent));
    }

    public void zza(@NonNull zzp zzpVar) {
        zzpVar.zzait();
    }

    protected Bundle zzaae() {
        return new Bundle();
    }

    public boolean zzaam() {
        return false;
    }

    public boolean zzaaw() {
        return false;
    }

    public Bundle zzaew() {
        return null;
    }

    public boolean zzafu() {
        return true;
    }

    @Nullable
    public final IBinder zzafv() {
        synchronized (this.zzfvq) {
            if (this.zzfvr == null) {
                return null;
            }
            return this.zzfvr.asBinder();
        }
    }

    protected String zzajv() {
        return "com.google.android.gms";
    }

    public final void zzajx() {
        int isGooglePlayServicesAvailable = this.zzfni.isGooglePlayServicesAvailable(this.mContext);
        if (isGooglePlayServicesAvailable != 0) {
            zza(1, (int) null);
            zza(new zzm(this), isGooglePlayServicesAvailable, (PendingIntent) null);
            return;
        }
        zza(new zzm(this));
    }

    public zzc[] zzajz() {
        return new zzc[0];
    }

    protected final void zzaka() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public final T zzakb() throws DeadObjectException {
        T t;
        synchronized (this.mLock) {
            if (this.zzfvw == 5) {
                throw new DeadObjectException();
            }
            zzaka();
            zzbq.zza(this.zzfvt != null, "Client is connected but service is null");
            t = this.zzfvt;
        }
        return t;
    }

    public boolean zzakc() {
        return false;
    }

    public Set<Scope> zzakd() {
        return Collections.EMPTY_SET;
    }

    public final void zzce(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6, this.zzfwd.get(), i));
    }

    @Nullable
    public abstract T zzd(IBinder iBinder);

    @NonNull
    protected abstract String zzhf();

    @NonNull
    public abstract String zzhg();
}
