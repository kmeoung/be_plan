package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes.dex */
public final class zzaa {
    private static zzaa zznux;
    private final SimpleArrayMap<String, String> zznuy = new SimpleArrayMap<>();
    private Boolean zznuz = null;
    @VisibleForTesting
    final Queue<Intent> zznva = new LinkedList();
    @VisibleForTesting
    private Queue<Intent> zznvb = new LinkedList();

    private zzaa() {
    }

    public static PendingIntent zza(Context context, int i, Intent intent, int i2) {
        Intent intent2 = new Intent(context, FirebaseInstanceIdReceiver.class);
        intent2.setAction("com.google.firebase.MESSAGING_EVENT");
        intent2.putExtra("wrapped_intent", intent);
        return PendingIntent.getBroadcast(context, i, intent2, 1073741824);
    }

    public static synchronized zzaa zzchl() {
        zzaa zzaaVar;
        synchronized (zzaa.class) {
            if (zznux == null) {
                zznux = new zzaa();
            }
            zzaaVar = zznux;
        }
        return zzaaVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00db A[Catch: IllegalStateException -> 0x0110, SecurityException -> 0x0138, TryCatch #4 {IllegalStateException -> 0x0110, SecurityException -> 0x0138, blocks: (B:40:0x00d7, B:42:0x00db, B:45:0x00e4, B:46:0x00ea, B:48:0x00f2, B:49:0x00f7, B:51:0x0104), top: B:67:0x00d7 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00f2 A[Catch: IllegalStateException -> 0x0110, SecurityException -> 0x0138, TryCatch #4 {IllegalStateException -> 0x0110, SecurityException -> 0x0138, blocks: (B:40:0x00d7, B:42:0x00db, B:45:0x00e4, B:46:0x00ea, B:48:0x00f2, B:49:0x00f7, B:51:0x0104), top: B:67:0x00d7 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f7 A[Catch: IllegalStateException -> 0x0110, SecurityException -> 0x0138, TryCatch #4 {IllegalStateException -> 0x0110, SecurityException -> 0x0138, blocks: (B:40:0x00d7, B:42:0x00db, B:45:0x00e4, B:46:0x00ea, B:48:0x00f2, B:49:0x00f7, B:51:0x0104), top: B:67:0x00d7 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0104 A[Catch: IllegalStateException -> 0x0110, SecurityException -> 0x0138, TRY_LEAVE, TryCatch #4 {IllegalStateException -> 0x0110, SecurityException -> 0x0138, blocks: (B:40:0x00d7, B:42:0x00db, B:45:0x00e4, B:46:0x00ea, B:48:0x00f2, B:49:0x00f7, B:51:0x0104), top: B:67:0x00d7 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x010e A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int zzd(android.content.Context r7, android.content.Intent r8) {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzaa.zzd(android.content.Context, android.content.Intent):int");
    }

    public final int zza(Context context, String str, Intent intent) {
        char c;
        Queue<Intent> queue;
        int hashCode = str.hashCode();
        if (hashCode != -842411455) {
            if (hashCode == 41532704 && str.equals("com.google.firebase.MESSAGING_EVENT")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("com.google.firebase.INSTANCE_ID_EVENT")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                queue = this.zznva;
                break;
            case 1:
                queue = this.zznvb;
                break;
            default:
                String valueOf = String.valueOf(str);
                Log.w("FirebaseInstanceId", valueOf.length() != 0 ? "Unknown service action: ".concat(valueOf) : new String("Unknown service action: "));
                return HttpStatus.SC_INTERNAL_SERVER_ERROR;
        }
        queue.offer(intent);
        Intent intent2 = new Intent(str);
        intent2.setPackage(context.getPackageName());
        return zzd(context, intent2);
    }

    public final Intent zzchm() {
        return this.zznvb.poll();
    }
}
