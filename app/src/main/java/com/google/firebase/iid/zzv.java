package com.google.firebase.iid;

import android.os.Bundle;

/* loaded from: classes.dex */
public final class zzv extends zzt<Bundle> {
    public zzv(int i, int i2, Bundle bundle) {
        super(i, 1, bundle);
    }

    @Override // com.google.firebase.iid.zzt
    public final void zzad(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle("data");
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        finish(bundle2);
    }

    @Override // com.google.firebase.iid.zzt
    public final boolean zzchj() {
        return false;
    }
}
