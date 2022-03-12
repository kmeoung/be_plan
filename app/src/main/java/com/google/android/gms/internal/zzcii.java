package com.google.android.gms.internal;

/* loaded from: classes.dex */
public abstract class zzcii extends zzcih {
    private boolean zzdqd;

    public zzcii(zzchj zzchjVar) {
        super(zzchjVar);
        this.zzitk.zzb(this);
    }

    public final void initialize() {
        if (this.zzdqd) {
            throw new IllegalStateException("Can't initialize twice");
        } else if (!zzaxn()) {
            this.zzitk.zzazy();
            this.zzdqd = true;
        }
    }

    public final boolean isInitialized() {
        return this.zzdqd;
    }

    protected abstract boolean zzaxn();

    protected void zzaym() {
    }

    public final void zzazk() {
        if (this.zzdqd) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzaym();
        this.zzitk.zzazy();
        this.zzdqd = true;
    }

    public final void zzwu() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }
}
