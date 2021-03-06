package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzdb;

/* loaded from: classes.dex */
public final class zzb implements zzdb {
    @Override // com.google.android.gms.common.api.internal.zzdb
    public final Exception zzt(Status status) {
        return status.getStatusCode() == 8 ? new FirebaseException(status.zzagk()) : new FirebaseApiNotAvailableException(status.zzagk());
    }
}
