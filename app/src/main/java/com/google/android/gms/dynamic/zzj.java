package com.google.android.gms.dynamic;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

@SuppressLint({"NewApi"})
/* loaded from: classes.dex */
public final class zzj extends zzl {
    private Fragment zzgts;

    private zzj(Fragment fragment) {
        this.zzgts = fragment;
    }

    public static zzj zza(Fragment fragment) {
        if (fragment != null) {
            return new zzj(fragment);
        }
        return null;
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final Bundle getArguments() {
        return this.zzgts.getArguments();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final int getId() {
        return this.zzgts.getId();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean getRetainInstance() {
        return this.zzgts.getRetainInstance();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final String getTag() {
        return this.zzgts.getTag();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final int getTargetRequestCode() {
        return this.zzgts.getTargetRequestCode();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean getUserVisibleHint() {
        return this.zzgts.getUserVisibleHint();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final IObjectWrapper getView() {
        return zzn.zzy(this.zzgts.getView());
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isAdded() {
        return this.zzgts.isAdded();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isDetached() {
        return this.zzgts.isDetached();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isHidden() {
        return this.zzgts.isHidden();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isInLayout() {
        return this.zzgts.isInLayout();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isRemoving() {
        return this.zzgts.isRemoving();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isResumed() {
        return this.zzgts.isResumed();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isVisible() {
        return this.zzgts.isVisible();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void setHasOptionsMenu(boolean z) {
        this.zzgts.setHasOptionsMenu(z);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void setMenuVisibility(boolean z) {
        this.zzgts.setMenuVisibility(z);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void setRetainInstance(boolean z) {
        this.zzgts.setRetainInstance(z);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void setUserVisibleHint(boolean z) {
        this.zzgts.setUserVisibleHint(z);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void startActivity(Intent intent) {
        this.zzgts.startActivity(intent);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void startActivityForResult(Intent intent, int i) {
        this.zzgts.startActivityForResult(intent, i);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final IObjectWrapper zzapl() {
        return zzn.zzy(this.zzgts.getActivity());
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final zzk zzapm() {
        return zza(this.zzgts.getParentFragment());
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final IObjectWrapper zzapn() {
        return zzn.zzy(this.zzgts.getResources());
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final zzk zzapo() {
        return zza(this.zzgts.getTargetFragment());
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void zzv(IObjectWrapper iObjectWrapper) {
        this.zzgts.registerForContextMenu((View) zzn.zzx(iObjectWrapper));
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void zzw(IObjectWrapper iObjectWrapper) {
        this.zzgts.unregisterForContextMenu((View) zzn.zzx(iObjectWrapper));
    }
}
