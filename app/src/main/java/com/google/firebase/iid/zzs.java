package com.google.firebase.iid;

import android.os.Bundle;

/* loaded from: classes.dex */
final class zzs extends zzt<Void> {
    public zzs(int i, int i2, Bundle bundle) {
        super(i, 2, bundle);
    }

    @Override // com.google.firebase.iid.zzt
    public final void zzad(Bundle bundle) {
        if (bundle.getBoolean("ack", false)) {
            finish(null);
        } else {
            zzb(new zzu(4, "Invalid response to one way request"));
        }
    }

    @Override // com.google.firebase.iid.zzt
    public final boolean zzchj() {
        return true;
    }
}
