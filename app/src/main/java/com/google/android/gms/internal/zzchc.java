package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public interface zzchc {
    BroadcastReceiver.PendingResult doGoAsync();

    void doStartService(Context context, Intent intent);
}
