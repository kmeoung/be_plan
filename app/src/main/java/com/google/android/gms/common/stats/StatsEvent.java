package com.google.android.gms.common.stats;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbej;

/* loaded from: classes.dex */
public abstract class StatsEvent extends zzbej implements ReflectedParcelable {
    public abstract int getEventType();

    public abstract long getTimeMillis();

    public String toString() {
        long timeMillis = getTimeMillis();
        int eventType = getEventType();
        long zzalr = zzalr();
        String zzals = zzals();
        StringBuilder sb = new StringBuilder(String.valueOf("\t").length() + 51 + String.valueOf("\t").length() + String.valueOf(zzals).length());
        sb.append(timeMillis);
        sb.append("\t");
        sb.append(eventType);
        sb.append("\t");
        sb.append(zzalr);
        sb.append(zzals);
        return sb.toString();
    }

    public abstract long zzalr();

    public abstract String zzals();
}
