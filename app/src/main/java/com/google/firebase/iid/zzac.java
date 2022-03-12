package com.google.firebase.iid;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzac {
    private static final long zznvc = TimeUnit.DAYS.toMillis(7);
    private long timestamp;
    private String zzicq;
    final String zzlan;

    private zzac(String str, String str2, long j) {
        this.zzlan = str;
        this.zzicq = str2;
        this.timestamp = j;
    }

    public static String zzc(String str, String str2, long j) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("token", str);
            jSONObject.put("appVersion", str2);
            jSONObject.put(AppMeasurement.Param.TIMESTAMP, j);
            return jSONObject.toString();
        } catch (JSONException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
            sb.append("Failed to encode token: ");
            sb.append(valueOf);
            Log.w("InstanceID/Store", sb.toString());
            return null;
        }
    }

    public static zzac zzqx(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!str.startsWith("{")) {
            return new zzac(str, null, 0L);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new zzac(jSONObject.getString("token"), jSONObject.getString("appVersion"), jSONObject.getLong(AppMeasurement.Param.TIMESTAMP));
        } catch (JSONException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
            sb.append("Failed to parse token: ");
            sb.append(valueOf);
            Log.w("InstanceID/Store", sb.toString());
            return null;
        }
    }

    public final boolean zzqy(String str) {
        return System.currentTimeMillis() > this.timestamp + zznvc || !str.equals(this.zzicq);
    }
}
