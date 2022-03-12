package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/* loaded from: classes.dex */
public final class zzr extends zzl {
    private Fragment zzgtv;

    private zzr(Fragment fragment) {
        this.zzgtv = fragment;
    }

    public static zzr zza(Fragment fragment) {
        if (fragment != null) {
            return new zzr(fragment);
        }
        return null;
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final Bundle getArguments() {
        return this.zzgtv.getArguments();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final int getId() {
        return this.zzgtv.getId();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean getRetainInstance() {
        return this.zzgtv.getRetainInstance();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final String getTag() {
        return this.zzgtv.getTag();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final int getTargetRequestCode() {
        return this.zzgtv.getTargetRequestCode();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean getUserVisibleHint() {
        return this.zzgtv.getUserVisibleHint();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final IObjectWrapper getView() {
        return zzn.zzy(this.zzgtv.getView());
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isAdded() {
        return this.zzgtv.isAdded();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isDetached() {
        return this.zzgtv.isDetached();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isHidden() {
        return this.zzgtv.isHidden();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isInLayout() {
        return this.zzgtv.isInLayout();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isRemoving() {
        return this.zzgtv.isRemoving();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isResumed() {
        return this.zzgtv.isResumed();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final boolean isVisible() {
        return this.zzgtv.isVisible();
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void setHasOptionsMenu(boolean z) {
        this.zzgtv.setHasOptionsMenu(z);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void setMenuVisibility(boolean z) {
        this.zzgtv.setMenuVisibility(z);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void setRetainInstance(boolean z) {
        this.zzgtv.setRetainInstance(z);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void setUserVisibleHint(boolean z) {
        this.zzgtv.setUserVisibleHint(z);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void startActivity(Intent intent) {
        this.zzgtv.startActivity(intent);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void startActivityForResult(Intent intent, int i) {
        this.zzgtv.startActivityForResult(intent, i);
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final IObjectWrapper zzapl() {
        return zzn.zzy(this.zzgtv.getActivity());
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final zzk zzapm() {
        return zza(this.zzgtv.getParentFragment());
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final IObjectWrapper zzapn() {
        return zzn.zzy(this.zzgtv.getResources());
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final zzk zzapo() {
        return zza(this.zzgtv.getTargetFragment());
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void zzv(IObjectWrapper iObjectWrapper) {
        this.zzgtv.registerForContextMenu((View) zzn.zzx(iObjectWrapper));
    }

    @Override // com.google.android.gms.dynamic.zzk
    public final void zzw(IObjectWrapper iObjectWrapper) {
        this.zzgtv.unregisterForContextMenu((View) zzn.zzx(iObjectWrapper));
    }
}
