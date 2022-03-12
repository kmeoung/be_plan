package com.google.firebase.iid;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/* loaded from: classes.dex */
public final class zzj {
    private static final Object sLock = new Object();
    private final zzab zznuc;

    public zzj(zzab zzabVar) {
        this.zznuc = zzabVar;
    }

    @Nullable
    public final String zzche() {
        synchronized (sLock) {
            String string = this.zznuc.zzidi.getString("topic_operaion_queue", null);
            if (string != null) {
                String[] split = string.split(",");
                if (split.length > 1 && !TextUtils.isEmpty(split[1])) {
                    return split[1];
                }
            }
            return null;
        }
    }

    public final void zzqp(String str) {
        synchronized (sLock) {
            String string = this.zznuc.zzidi.getString("topic_operaion_queue", "");
            StringBuilder sb = new StringBuilder(String.valueOf(string).length() + String.valueOf(",").length() + String.valueOf(str).length());
            sb.append(string);
            sb.append(",");
            sb.append(str);
            this.zznuc.zzidi.edit().putString("topic_operaion_queue", sb.toString()).apply();
        }
    }

    public final boolean zzqs(String str) {
        synchronized (sLock) {
            String string = this.zznuc.zzidi.getString("topic_operaion_queue", "");
            String valueOf = String.valueOf(",");
            String valueOf2 = String.valueOf(str);
            if (!string.startsWith(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf))) {
                return false;
            }
            String valueOf3 = String.valueOf(",");
            String valueOf4 = String.valueOf(str);
            this.zznuc.zzidi.edit().putString("topic_operaion_queue", string.substring((valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3)).length())).apply();
            return true;
        }
    }
}
