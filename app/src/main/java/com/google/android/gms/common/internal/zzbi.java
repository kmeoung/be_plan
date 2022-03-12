package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class zzbi {
    private final Object zzdbh;
    private final List<String> zzfyk;

    private zzbi(Object obj) {
        this.zzdbh = zzbq.checkNotNull(obj);
        this.zzfyk = new ArrayList();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append(this.zzdbh.getClass().getSimpleName());
        sb.append('{');
        int size = this.zzfyk.size();
        for (int i = 0; i < size; i++) {
            sb.append(this.zzfyk.get(i));
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public final zzbi zzg(String str, Object obj) {
        List<String> list = this.zzfyk;
        String str2 = (String) zzbq.checkNotNull(str);
        String valueOf = String.valueOf(obj);
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 1 + String.valueOf(valueOf).length());
        sb.append(str2);
        sb.append("=");
        sb.append(valueOf);
        list.add(sb.toString());
        return this;
    }
}
