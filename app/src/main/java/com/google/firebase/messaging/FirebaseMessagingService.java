package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.firebase.iid.zzaa;
import com.google.firebase.iid.zzb;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/* loaded from: classes.dex */
public class FirebaseMessagingService extends zzb {
    private static final Queue<String> zznvo = new ArrayDeque(10);

    public static boolean zzak(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return "1".equals(bundle.getString("google.c.a.e"));
    }

    public static void zzq(Bundle bundle) {
        Iterator<String> it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (next != null && next.startsWith("google.c.")) {
                it.remove();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00e0, code lost:
        if (r1.equals("send_error") != false) goto L_0x00f8;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:99:? A[RETURN, SYNTHETIC] */
    @Override // com.google.firebase.iid.zzb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleIntent(android.content.Intent r10) {
        /*
            Method dump skipped, instructions count: 438
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.FirebaseMessagingService.handleIntent(android.content.Intent):void");
    }

    @WorkerThread
    public void onDeletedMessages() {
    }

    @WorkerThread
    public void onMessageReceived(RemoteMessage remoteMessage) {
    }

    @WorkerThread
    public void onMessageSent(String str) {
    }

    @WorkerThread
    public void onSendError(String str, Exception exc) {
    }

    @Override // com.google.firebase.iid.zzb
    protected final Intent zzp(Intent intent) {
        return zzaa.zzchl().zzchm();
    }

    @Override // com.google.firebase.iid.zzb
    public final boolean zzq(Intent intent) {
        if (!"com.google.firebase.messaging.NOTIFICATION_OPEN".equals(intent.getAction())) {
            return false;
        }
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("pending_intent");
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException unused) {
                Log.e("FirebaseMessaging", "Notification pending intent canceled");
            }
        }
        if (!zzak(intent.getExtras())) {
            return true;
        }
        zzd.zzf(this, intent);
        return true;
    }
}
