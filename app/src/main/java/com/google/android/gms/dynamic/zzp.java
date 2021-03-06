package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.zzo;

/* loaded from: classes.dex */
public abstract class zzp<T> {
    private final String zzgtt;
    private T zzgtu;

    protected zzp(String str) {
        this.zzgtt = str;
    }

    protected final T zzdb(Context context) throws zzq {
        if (this.zzgtu == null) {
            zzbq.checkNotNull(context);
            Context remoteContext = zzo.getRemoteContext(context);
            if (remoteContext == null) {
                throw new zzq("Could not get remote context.");
            }
            try {
                this.zzgtu = zze((IBinder) remoteContext.getClassLoader().loadClass(this.zzgtt).newInstance());
            } catch (ClassNotFoundException e) {
                throw new zzq("Could not load creator class.", e);
            } catch (IllegalAccessException e2) {
                throw new zzq("Could not access creator.", e2);
            } catch (InstantiationException e3) {
                throw new zzq("Could not instantiate creator.", e3);
            }
        }
        return this.zzgtu;
    }

    protected abstract T zze(IBinder iBinder);
}
