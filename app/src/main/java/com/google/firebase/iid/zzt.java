package com.google.firebase.iid;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class zzt<T> {
    final int what;
    final TaskCompletionSource<T> zzgow = new TaskCompletionSource<>();
    final int zzicx;
    final Bundle zznuq;

    public zzt(int i, int i2, Bundle bundle) {
        this.zzicx = i;
        this.what = i2;
        this.zznuq = bundle;
    }

    public final void finish(T t) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(t);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 16 + String.valueOf(valueOf2).length());
            sb.append("Finishing ");
            sb.append(valueOf);
            sb.append(" with ");
            sb.append(valueOf2);
            Log.d("MessengerIpcClient", sb.toString());
        }
        this.zzgow.setResult(t);
    }

    public String toString() {
        int i = this.what;
        int i2 = this.zzicx;
        boolean zzchj = zzchj();
        StringBuilder sb = new StringBuilder(55);
        sb.append("Request { what=");
        sb.append(i);
        sb.append(" id=");
        sb.append(i2);
        sb.append(" oneWay=");
        sb.append(zzchj);
        sb.append("}");
        return sb.toString();
    }

    public abstract void zzad(Bundle bundle);

    public final void zzb(zzu zzuVar) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(zzuVar);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 14 + String.valueOf(valueOf2).length());
            sb.append("Failing ");
            sb.append(valueOf);
            sb.append(" with ");
            sb.append(valueOf2);
            Log.d("MessengerIpcClient", sb.toString());
        }
        this.zzgow.setException(zzuVar);
    }

    public abstract boolean zzchj();
}
