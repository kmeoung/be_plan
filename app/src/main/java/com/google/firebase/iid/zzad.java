package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.PowerManager;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes.dex */
public final class zzad implements Runnable {
    private final long zznvd;
    private final PowerManager.WakeLock zznve = ((PowerManager) getContext().getSystemService("power")).newWakeLock(1, "fiid-sync");
    private FirebaseInstanceId zznvf;

    public zzad(FirebaseInstanceId firebaseInstanceId, long j) {
        this.zznvf = firebaseInstanceId;
        this.zznvd = j;
        this.zznve.setReferenceCounted(false);
    }

    private final boolean zzchn() {
        zzac zzcgw = this.zznvf.zzcgw();
        if (zzcgw != null && !zzcgw.zzqy(zzi.zzicq)) {
            return true;
        }
        try {
            String zzcgx = this.zznvf.zzcgx();
            if (zzcgx == null) {
                Log.e("FirebaseInstanceId", "Token retrieval failed: null");
                return false;
            }
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "Token successfully retrieved");
            }
            if (zzcgw == null || (zzcgw != null && !zzcgx.equals(zzcgw.zzlan))) {
                Context context = getContext();
                Intent intent = new Intent("com.google.firebase.iid.TOKEN_REFRESH");
                Intent intent2 = new Intent("com.google.firebase.INSTANCE_ID_EVENT");
                intent2.setClass(context, FirebaseInstanceIdReceiver.class);
                intent2.putExtra("wrapped_intent", intent);
                context.sendBroadcast(intent2);
            }
            return true;
        } catch (IOException | SecurityException e) {
            String valueOf = String.valueOf(e.getMessage());
            Log.e("FirebaseInstanceId", valueOf.length() != 0 ? "Token retrieval failed: ".concat(valueOf) : new String("Token retrieval failed: "));
            return false;
        }
    }

    private final boolean zzcho() {
        zzj zzcgy = FirebaseInstanceId.zzcgy();
        while (true) {
            synchronized (this.zznvf) {
                String zzche = zzcgy.zzche();
                if (zzche == null) {
                    Log.d("FirebaseInstanceId", "topic sync succeeded");
                    return true;
                } else if (!zzqz(zzche)) {
                    return false;
                } else {
                    zzcgy.zzqs(zzche);
                }
            }
        }
    }

    private final boolean zzqz(String str) {
        String str2;
        String str3;
        String[] split = str.split("!");
        if (split.length == 2) {
            String str4 = split[0];
            String str5 = split[1];
            char c = 65535;
            try {
                int hashCode = str4.hashCode();
                if (hashCode != 83) {
                    if (hashCode == 85 && str4.equals("U")) {
                        c = 1;
                    }
                } else if (str4.equals("S")) {
                    c = 0;
                }
                switch (c) {
                    case 0:
                        this.zznvf.zzqq(str5);
                        if (FirebaseInstanceId.zzcgz()) {
                            str2 = "FirebaseInstanceId";
                            str3 = "subscribe operation succeeded";
                            Log.d(str2, str3);
                            return true;
                        }
                        break;
                    case 1:
                        this.zznvf.zzqr(str5);
                        if (FirebaseInstanceId.zzcgz()) {
                            str2 = "FirebaseInstanceId";
                            str3 = "unsubscribe operation succeeded";
                            Log.d(str2, str3);
                            return true;
                        }
                        break;
                    default:
                        return true;
                }
            } catch (IOException e) {
                String valueOf = String.valueOf(e.getMessage());
                Log.e("FirebaseInstanceId", valueOf.length() != 0 ? "Topic sync failed: ".concat(valueOf) : new String("Topic sync failed: "));
                return false;
            }
        }
        return true;
    }

    public final Context getContext() {
        return this.zznvf.getApp().getApplicationContext();
    }

    @Override // java.lang.Runnable
    public final void run() {
        FirebaseInstanceId firebaseInstanceId;
        this.zznve.acquire();
        try {
            this.zznvf.zzco(true);
            if (!zzw.zzeu(getContext())) {
                firebaseInstanceId = this.zznvf;
            } else {
                if (!zzchp()) {
                    new zzae(this).zzchq();
                } else if (!zzchn() || !zzcho()) {
                    this.zznvf.zzcb(this.zznvd);
                } else {
                    firebaseInstanceId = this.zznvf;
                }
            }
            firebaseInstanceId.zzco(false);
        } finally {
            this.zznve.release();
        }
    }

    public final boolean zzchp() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        return (connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnected()) ? false : true;
    }
}
