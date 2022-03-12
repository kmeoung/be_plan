package com.google.android.gms.internal;

import android.os.Binder;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzx;
import com.google.android.gms.common.zzo;
import com.google.android.gms.common.zzp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* loaded from: classes.dex */
public final class zzcho extends zzcgc {
    private final zzchj zzitk;
    private Boolean zzjdq;
    @Nullable
    private String zzjdr;

    public zzcho(zzchj zzchjVar) {
        this(zzchjVar, null);
    }

    private zzcho(zzchj zzchjVar, @Nullable String str) {
        zzbq.checkNotNull(zzchjVar);
        this.zzitk = zzchjVar;
        this.zzjdr = null;
    }

    @BinderThread
    private final void zzb(zzcff zzcffVar, boolean z) {
        zzbq.checkNotNull(zzcffVar);
        zzf(zzcffVar.packageName, false);
        this.zzitk.zzawi().zzka(zzcffVar.zziux);
    }

    @BinderThread
    private final void zzf(String str, boolean z) {
        boolean z2;
        if (TextUtils.isEmpty(str)) {
            this.zzitk.zzawm().zzayr().log("Measurement Service called without app package");
            throw new SecurityException("Measurement Service called without app package");
        }
        if (z) {
            try {
                if (this.zzjdq == null) {
                    if (!"com.google.android.gms".equals(this.zzjdr) && !zzx.zzf(this.zzitk.getContext(), Binder.getCallingUid()) && !zzp.zzcg(this.zzitk.getContext()).zzbq(Binder.getCallingUid())) {
                        z2 = false;
                        this.zzjdq = Boolean.valueOf(z2);
                    }
                    z2 = true;
                    this.zzjdq = Boolean.valueOf(z2);
                }
                if (this.zzjdq.booleanValue()) {
                    return;
                }
            } catch (SecurityException e) {
                this.zzitk.zzawm().zzayr().zzj("Measurement Service called with invalid calling package. appId", zzcgj.zzje(str));
                throw e;
            }
        }
        if (this.zzjdr == null && zzo.zzb(this.zzitk.getContext(), Binder.getCallingUid(), str)) {
            this.zzjdr = str;
        }
        if (!str.equals(this.zzjdr)) {
            throw new SecurityException(String.format("Unknown calling package name '%s'.", str));
        }
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final List<zzckk> zza(zzcff zzcffVar, boolean z) {
        zzb(zzcffVar, false);
        try {
            List<zzckm> list = (List) this.zzitk.zzawl().zzc(new zzcie(this, zzcffVar)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzckm zzckmVar : list) {
                if (z || !zzckn.zzkc(zzckmVar.mName)) {
                    arrayList.add(new zzckk(zzckmVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzitk.zzawm().zzayr().zze("Failed to get user attributes. appId", zzcgj.zzje(zzcffVar.packageName), e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final List<zzcfi> zza(String str, String str2, zzcff zzcffVar) {
        zzb(zzcffVar, false);
        try {
            return (List) this.zzitk.zzawl().zzc(new zzchw(this, zzcffVar, str, str2)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zzitk.zzawm().zzayr().zzj("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final List<zzckk> zza(String str, String str2, String str3, boolean z) {
        zzf(str, true);
        try {
            List<zzckm> list = (List) this.zzitk.zzawl().zzc(new zzchv(this, str, str2, str3)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzckm zzckmVar : list) {
                if (z || !zzckn.zzkc(zzckmVar.mName)) {
                    arrayList.add(new zzckk(zzckmVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzitk.zzawm().zzayr().zze("Failed to get user attributes. appId", zzcgj.zzje(str), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final List<zzckk> zza(String str, String str2, boolean z, zzcff zzcffVar) {
        zzb(zzcffVar, false);
        try {
            List<zzckm> list = (List) this.zzitk.zzawl().zzc(new zzchu(this, zzcffVar, str, str2)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzckm zzckmVar : list) {
                if (z || !zzckn.zzkc(zzckmVar.mName)) {
                    arrayList.add(new zzckk(zzckmVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzitk.zzawm().zzayr().zze("Failed to get user attributes. appId", zzcgj.zzje(zzcffVar.packageName), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final void zza(long j, String str, String str2, String str3) {
        this.zzitk.zzawl().zzg(new zzcig(this, str2, str3, str, j));
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final void zza(zzcff zzcffVar) {
        zzb(zzcffVar, false);
        zzcif zzcifVar = new zzcif(this, zzcffVar);
        if (this.zzitk.zzawl().zzazg()) {
            zzcifVar.run();
        } else {
            this.zzitk.zzawl().zzg(zzcifVar);
        }
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final void zza(zzcfi zzcfiVar, zzcff zzcffVar) {
        zzche zzawl;
        Runnable zzchrVar;
        zzbq.checkNotNull(zzcfiVar);
        zzbq.checkNotNull(zzcfiVar.zzivl);
        zzb(zzcffVar, false);
        zzcfi zzcfiVar2 = new zzcfi(zzcfiVar);
        zzcfiVar2.packageName = zzcffVar.packageName;
        if (zzcfiVar.zzivl.getValue() == null) {
            zzawl = this.zzitk.zzawl();
            zzchrVar = new zzchq(this, zzcfiVar2, zzcffVar);
        } else {
            zzawl = this.zzitk.zzawl();
            zzchrVar = new zzchr(this, zzcfiVar2, zzcffVar);
        }
        zzawl.zzg(zzchrVar);
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final void zza(zzcfx zzcfxVar, zzcff zzcffVar) {
        zzbq.checkNotNull(zzcfxVar);
        zzb(zzcffVar, false);
        this.zzitk.zzawl().zzg(new zzchz(this, zzcfxVar, zzcffVar));
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final void zza(zzcfx zzcfxVar, String str, String str2) {
        zzbq.checkNotNull(zzcfxVar);
        zzbq.zzgh(str);
        zzf(str, true);
        this.zzitk.zzawl().zzg(new zzcia(this, zzcfxVar, str));
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final void zza(zzckk zzckkVar, zzcff zzcffVar) {
        zzche zzawl;
        Runnable zzcidVar;
        zzbq.checkNotNull(zzckkVar);
        zzb(zzcffVar, false);
        if (zzckkVar.getValue() == null) {
            zzawl = this.zzitk.zzawl();
            zzcidVar = new zzcic(this, zzckkVar, zzcffVar);
        } else {
            zzawl = this.zzitk.zzawl();
            zzcidVar = new zzcid(this, zzckkVar, zzcffVar);
        }
        zzawl.zzg(zzcidVar);
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final byte[] zza(zzcfx zzcfxVar, String str) {
        zzbq.zzgh(str);
        zzbq.checkNotNull(zzcfxVar);
        zzf(str, true);
        this.zzitk.zzawm().zzayw().zzj("Log and bundle. event", this.zzitk.zzawh().zzjb(zzcfxVar.name));
        long nanoTime = this.zzitk.zzwh().nanoTime() / 1000000;
        try {
            byte[] bArr = (byte[]) this.zzitk.zzawl().zzd(new zzcib(this, zzcfxVar, str)).get();
            if (bArr == null) {
                this.zzitk.zzawm().zzayr().zzj("Log and bundle returned null. appId", zzcgj.zzje(str));
                bArr = new byte[0];
            }
            this.zzitk.zzawm().zzayw().zzd("Log and bundle processed. event, size, time_ms", this.zzitk.zzawh().zzjb(zzcfxVar.name), Integer.valueOf(bArr.length), Long.valueOf((this.zzitk.zzwh().nanoTime() / 1000000) - nanoTime));
            return bArr;
        } catch (InterruptedException | ExecutionException e) {
            this.zzitk.zzawm().zzayr().zzd("Failed to log and bundle. appId, event, error", zzcgj.zzje(str), this.zzitk.zzawh().zzjb(zzcfxVar.name), e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final void zzb(zzcff zzcffVar) {
        zzb(zzcffVar, false);
        this.zzitk.zzawl().zzg(new zzchp(this, zzcffVar));
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final void zzb(zzcfi zzcfiVar) {
        zzche zzawl;
        Runnable zzchtVar;
        zzbq.checkNotNull(zzcfiVar);
        zzbq.checkNotNull(zzcfiVar.zzivl);
        zzf(zzcfiVar.packageName, true);
        zzcfi zzcfiVar2 = new zzcfi(zzcfiVar);
        if (zzcfiVar.zzivl.getValue() == null) {
            zzawl = this.zzitk.zzawl();
            zzchtVar = new zzchs(this, zzcfiVar2);
        } else {
            zzawl = this.zzitk.zzawl();
            zzchtVar = new zzcht(this, zzcfiVar2);
        }
        zzawl.zzg(zzchtVar);
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final String zzc(zzcff zzcffVar) {
        zzb(zzcffVar, false);
        return this.zzitk.zzjr(zzcffVar.packageName);
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final void zzd(zzcff zzcffVar) {
        zzf(zzcffVar.packageName, false);
        this.zzitk.zzawl().zzg(new zzchy(this, zzcffVar));
    }

    @Override // com.google.android.gms.internal.zzcgb
    @BinderThread
    public final List<zzcfi> zzj(String str, String str2, String str3) {
        zzf(str, true);
        try {
            return (List) this.zzitk.zzawl().zzc(new zzchx(this, str, str2, str3)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zzitk.zzawm().zzayr().zzj("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }
}
