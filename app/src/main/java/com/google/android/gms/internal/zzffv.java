package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzffv extends zzffu<FieldDescriptorType, Object> {
    public zzffv(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.zzffu
    public final void zzbim() {
        if (!isImmutable()) {
            for (int i = 0; i < zzcwj(); i++) {
                Map.Entry<FieldDescriptorType, Object> zzlq = zzlq(i);
                if (((zzfed) zzlq.getKey()).zzcvc()) {
                    zzlq.setValue(Collections.unmodifiableList((List) zzlq.getValue()));
                }
            }
            Iterator it = zzcwk().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (((zzfed) entry.getKey()).zzcvc()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzbim();
    }
}
