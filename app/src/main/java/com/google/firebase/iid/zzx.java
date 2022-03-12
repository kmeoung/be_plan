package com.google.firebase.iid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes.dex */
public final class zzx extends Handler {
    private /* synthetic */ zzw zznuu;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzx(zzw zzwVar, Looper looper) {
        super(looper);
        this.zznuu = zzwVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        this.zznuu.zzc(message);
    }
}
