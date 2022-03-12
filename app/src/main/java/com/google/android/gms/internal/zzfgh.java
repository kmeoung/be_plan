package com.google.android.gms.internal;

import java.util.List;

/* loaded from: classes.dex */
public final class zzfgh extends RuntimeException {
    private final List<String> zzpek = null;

    public zzfgh(zzffi zzffiVar) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
    }

    public final zzfew zzcwt() {
        return new zzfew(getMessage());
    }
}
