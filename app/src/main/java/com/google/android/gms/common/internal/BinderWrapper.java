package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
/* loaded from: classes.dex */
public final class BinderWrapper implements Parcelable {
    public static final Parcelable.Creator<BinderWrapper> CREATOR = new zzq();
    private IBinder zzfwl;

    public BinderWrapper() {
        this.zzfwl = null;
    }

    public BinderWrapper(IBinder iBinder) {
        this.zzfwl = null;
        this.zzfwl = iBinder;
    }

    private BinderWrapper(Parcel parcel) {
        this.zzfwl = null;
        this.zzfwl = parcel.readStrongBinder();
    }

    public /* synthetic */ BinderWrapper(Parcel parcel, zzq zzqVar) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.zzfwl);
    }
}
