package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Intent;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class zzah {
    private final ComponentName mComponentName;
    private final String zzdor;
    private final String zzfxq;
    private final int zzfxr;

    public zzah(ComponentName componentName, int i) {
        this.zzdor = null;
        this.zzfxq = null;
        this.mComponentName = (ComponentName) zzbq.checkNotNull(componentName);
        this.zzfxr = 129;
    }

    public zzah(String str, String str2, int i) {
        this.zzdor = zzbq.zzgh(str);
        this.zzfxq = zzbq.zzgh(str2);
        this.mComponentName = null;
        this.zzfxr = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzah)) {
            return false;
        }
        zzah zzahVar = (zzah) obj;
        return zzbg.equal(this.zzdor, zzahVar.zzdor) && zzbg.equal(this.zzfxq, zzahVar.zzfxq) && zzbg.equal(this.mComponentName, zzahVar.mComponentName) && this.zzfxr == zzahVar.zzfxr;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final String getPackage() {
        return this.zzfxq;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzdor, this.zzfxq, this.mComponentName, Integer.valueOf(this.zzfxr)});
    }

    public final String toString() {
        return this.zzdor == null ? this.mComponentName.flattenToString() : this.zzdor;
    }

    public final int zzaky() {
        return this.zzfxr;
    }

    public final Intent zzakz() {
        return this.zzdor != null ? new Intent(this.zzdor).setPackage(this.zzfxq) : new Intent().setComponent(this.mComponentName);
    }
}
