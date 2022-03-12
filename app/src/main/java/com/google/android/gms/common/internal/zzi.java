package com.google.android.gms.common.internal;

import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class zzi<TListener> {
    private TListener zzfrq;
    private /* synthetic */ zzd zzfwg;
    private boolean zzfwh = false;

    public zzi(zzd zzdVar, TListener tlistener) {
        this.zzfwg = zzdVar;
        this.zzfrq = tlistener;
    }

    public final void removeListener() {
        synchronized (this) {
            this.zzfrq = null;
        }
    }

    public final void unregister() {
        ArrayList arrayList;
        ArrayList arrayList2;
        removeListener();
        arrayList = this.zzfwg.zzfvu;
        synchronized (arrayList) {
            arrayList2 = this.zzfwg.zzfvu;
            arrayList2.remove(this);
        }
    }

    public final void zzakg() {
        TListener tlistener;
        synchronized (this) {
            tlistener = this.zzfrq;
            if (this.zzfwh) {
                String valueOf = String.valueOf(this);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47);
                sb.append("Callback proxy ");
                sb.append(valueOf);
                sb.append(" being reused. This is not safe.");
                Log.w("GmsClient", sb.toString());
            }
        }
        if (tlistener != null) {
            try {
                zzv(tlistener);
            } catch (RuntimeException e) {
                throw e;
            }
        }
        synchronized (this) {
            this.zzfwh = true;
        }
        unregister();
    }

    protected abstract void zzv(TListener tlistener);
}
