package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzcjd extends zzcii {
    private zzcgb zzjfi;
    private volatile Boolean zzjfj;
    private final zzcfp zzjfk;
    private final zzckh zzjfl;
    private final zzcfp zzjfn;
    private final List<Runnable> zzjfm = new ArrayList();
    private final zzcjr zzjfh = new zzcjr(this);

    public zzcjd(zzchj zzchjVar) {
        super(zzchjVar);
        this.zzjfl = new zzckh(zzchjVar.zzwh());
        this.zzjfk = new zzcje(this, zzchjVar);
        this.zzjfn = new zzcjj(this, zzchjVar);
    }

    @WorkerThread
    public final void onServiceDisconnected(ComponentName componentName) {
        zzut();
        if (this.zzjfi != null) {
            this.zzjfi = null;
            zzawm().zzayx().zzj("Disconnected from device MeasurementService", componentName);
            zzut();
            zzxr();
        }
    }

    @WorkerThread
    public final void zzbah() {
        zzut();
        zzawm().zzayx().zzj("Processing queued up service tasks", Integer.valueOf(this.zzjfm.size()));
        for (Runnable runnable : this.zzjfm) {
            try {
                runnable.run();
            } catch (Throwable th) {
                zzawm().zzayr().zzj("Task exception while flushing queue", th);
            }
        }
        this.zzjfm.clear();
        this.zzjfn.cancel();
    }

    @WorkerThread
    @Nullable
    private final zzcff zzbq(boolean z) {
        return zzawb().zzja(z ? zzawm().zzayy() : null);
    }

    @WorkerThread
    private final void zzj(Runnable runnable) throws IllegalStateException {
        zzut();
        if (isConnected()) {
            runnable.run();
        } else if (this.zzjfm.size() >= 1000) {
            zzawm().zzayr().log("Discarding data. Max runnable queue size reached");
        } else {
            this.zzjfm.add(runnable);
            this.zzjfn.zzr(60000L);
            zzxr();
        }
    }

    @WorkerThread
    public final void zzxg() {
        zzut();
        this.zzjfl.start();
        this.zzjfk.zzr(zzcfz.zziyo.get().longValue());
    }

    @WorkerThread
    public final void zzxh() {
        zzut();
        if (isConnected()) {
            zzawm().zzayx().log("Inactivity, disconnecting from the service");
            disconnect();
        }
    }

    @WorkerThread
    public final void disconnect() {
        zzut();
        zzwu();
        try {
            zza.zzalq();
            getContext().unbindService(this.zzjfh);
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        this.zzjfi = null;
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    public final boolean isConnected() {
        zzut();
        zzwu();
        return this.zzjfi != null;
    }

    @WorkerThread
    public final void resetAnalyticsData() {
        zzut();
        zzwu();
        zzcff zzbq = zzbq(false);
        zzawf().resetAnalyticsData();
        zzj(new zzcjf(this, zzbq));
    }

    @WorkerThread
    public final void zza(zzcgb zzcgbVar) {
        zzut();
        zzbq.checkNotNull(zzcgbVar);
        this.zzjfi = zzcgbVar;
        zzxg();
        zzbah();
    }

    @WorkerThread
    public final void zza(zzcgb zzcgbVar, zzbej zzbejVar, zzcff zzcffVar) {
        int i;
        zzcgl zzayr;
        String str;
        zzut();
        zzwu();
        int i2 = 0;
        int i3 = 100;
        while (i2 < 1001 && i3 == 100) {
            ArrayList arrayList = new ArrayList();
            List<zzbej> zzeb = zzawf().zzeb(100);
            if (zzeb != null) {
                arrayList.addAll(zzeb);
                i = zzeb.size();
            } else {
                i = 0;
            }
            if (zzbejVar != null && i < 100) {
                arrayList.add(zzbejVar);
            }
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i4 = 0;
            while (i4 < size) {
                Object obj = arrayList2.get(i4);
                i4++;
                zzbej zzbejVar2 = (zzbej) obj;
                if (zzbejVar2 instanceof zzcfx) {
                    try {
                        zzcgbVar.zza((zzcfx) zzbejVar2, zzcffVar);
                    } catch (RemoteException e) {
                        e = e;
                        zzayr = zzawm().zzayr();
                        str = "Failed to send event to the service";
                        zzayr.zzj(str, e);
                    }
                } else if (zzbejVar2 instanceof zzckk) {
                    try {
                        zzcgbVar.zza((zzckk) zzbejVar2, zzcffVar);
                    } catch (RemoteException e2) {
                        e = e2;
                        zzayr = zzawm().zzayr();
                        str = "Failed to send attribute to the service";
                        zzayr.zzj(str, e);
                    }
                } else if (zzbejVar2 instanceof zzcfi) {
                    try {
                        zzcgbVar.zza((zzcfi) zzbejVar2, zzcffVar);
                    } catch (RemoteException e3) {
                        e = e3;
                        zzayr = zzawm().zzayr();
                        str = "Failed to send conditional property to the service";
                        zzayr.zzj(str, e);
                    }
                } else {
                    zzawm().zzayr().log("Discarding data. Unrecognized parcel type.");
                }
            }
            i2++;
            i3 = i;
        }
    }

    @WorkerThread
    public final void zza(AppMeasurement.zzb zzbVar) {
        zzut();
        zzwu();
        zzj(new zzcji(this, zzbVar));
    }

    @WorkerThread
    public final void zza(AtomicReference<String> atomicReference) {
        zzut();
        zzwu();
        zzj(new zzcjg(this, atomicReference, zzbq(false)));
    }

    @WorkerThread
    public final void zza(AtomicReference<List<zzcfi>> atomicReference, String str, String str2, String str3) {
        zzut();
        zzwu();
        zzj(new zzcjn(this, atomicReference, str, str2, str3, zzbq(false)));
    }

    @WorkerThread
    public final void zza(AtomicReference<List<zzckk>> atomicReference, String str, String str2, String str3, boolean z) {
        zzut();
        zzwu();
        zzj(new zzcjo(this, atomicReference, str, str2, str3, z, zzbq(false)));
    }

    @WorkerThread
    public final void zza(AtomicReference<List<zzckk>> atomicReference, boolean z) {
        zzut();
        zzwu();
        zzj(new zzcjq(this, atomicReference, zzbq(false), z));
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzavw() {
        super.zzavw();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzavx() {
        super.zzavx();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfa zzavy() {
        return super.zzavy();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfh zzavz() {
        return super.zzavz();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcik zzawa() {
        return super.zzawa();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcge zzawb() {
        return super.zzawb();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfr zzawc() {
        return super.zzawc();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcjd zzawd() {
        return super.zzawd();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzciz zzawe() {
        return super.zzawe();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgf zzawf() {
        return super.zzawf();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfl zzawg() {
        return super.zzawg();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgh zzawh() {
        return super.zzawh();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzckn zzawi() {
        return super.zzawi();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzchd zzawj() {
        return super.zzawj();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzckc zzawk() {
        return super.zzawk();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzche zzawl() {
        return super.zzawl();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgj zzawm() {
        return super.zzawm();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcgu zzawn() {
        return super.zzawn();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzcfk zzawo() {
        return super.zzawo();
    }

    @Override // com.google.android.gms.internal.zzcii
    protected final boolean zzaxn() {
        return false;
    }

    @WorkerThread
    public final void zzb(zzckk zzckkVar) {
        zzut();
        zzwu();
        zzj(new zzcjp(this, zzawf().zza(zzckkVar), zzckkVar, zzbq(true)));
    }

    @WorkerThread
    public final void zzbae() {
        zzut();
        zzwu();
        zzj(new zzcjk(this, zzbq(true)));
    }

    @WorkerThread
    public final void zzbaf() {
        zzut();
        zzwu();
        zzj(new zzcjh(this, zzbq(true)));
    }

    public final Boolean zzbag() {
        return this.zzjfj;
    }

    @WorkerThread
    public final void zzc(zzcfx zzcfxVar, String str) {
        zzbq.checkNotNull(zzcfxVar);
        zzut();
        zzwu();
        zzj(new zzcjl(this, true, zzawf().zza(zzcfxVar), zzcfxVar, zzbq(true), str));
    }

    @WorkerThread
    public final void zzf(zzcfi zzcfiVar) {
        zzbq.checkNotNull(zzcfiVar);
        zzut();
        zzwu();
        zzj(new zzcjm(this, true, zzawf().zzc(zzcfiVar), new zzcfi(zzcfiVar), zzbq(true), zzcfiVar));
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzut() {
        super.zzut();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzd zzwh() {
        return super.zzwh();
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00f3  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzxr() {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcjd.zzxr():void");
    }
}
