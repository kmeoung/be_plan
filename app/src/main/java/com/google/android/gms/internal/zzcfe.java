package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;

/* loaded from: classes.dex */
public final class zzcfe {
    private final String mAppId;
    private String zzcvg;
    private String zzdoc;
    private String zzgdh;
    private final zzchj zzitk;
    private String zziub;
    private String zziuc;
    private long zziud;
    private long zziue;
    private long zziuf;
    private long zziug;
    private String zziuh;
    private long zziui;
    private long zziuj;
    private boolean zziuk;
    private long zziul;
    private boolean zzium;
    private long zziun;
    private long zziuo;
    private long zziup;
    private long zziuq;
    private long zziur;
    private long zzius;
    private String zziut;
    private boolean zziuu;
    private long zziuv;
    private long zziuw;

    @WorkerThread
    public zzcfe(zzchj zzchjVar, String str) {
        zzbq.checkNotNull(zzchjVar);
        zzbq.zzgh(str);
        this.zzitk = zzchjVar;
        this.mAppId = str;
        this.zzitk.zzawl().zzut();
    }

    @WorkerThread
    public final String getAppId() {
        this.zzitk.zzawl().zzut();
        return this.mAppId;
    }

    @WorkerThread
    public final String getAppInstanceId() {
        this.zzitk.zzawl().zzut();
        return this.zzgdh;
    }

    @WorkerThread
    public final String getGmpAppId() {
        this.zzitk.zzawl().zzut();
        return this.zzcvg;
    }

    @WorkerThread
    public final void setAppVersion(String str) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= !zzckn.zzas(this.zzdoc, str);
        this.zzdoc = str;
    }

    @WorkerThread
    public final void setMeasurementEnabled(boolean z) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziuk != z;
        this.zziuk = z;
    }

    @WorkerThread
    public final void zzak(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziue != j;
        this.zziue = j;
    }

    @WorkerThread
    public final void zzal(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziuf != j;
        this.zziuf = j;
    }

    @WorkerThread
    public final void zzam(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziug != j;
        this.zziug = j;
    }

    @WorkerThread
    public final void zzan(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziui != j;
        this.zziui = j;
    }

    @WorkerThread
    public final void zzao(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziuj != j;
        this.zziuj = j;
    }

    @WorkerThread
    public final void zzap(long j) {
        boolean z = false;
        zzbq.checkArgument(j >= 0);
        this.zzitk.zzawl().zzut();
        boolean z2 = this.zziuu;
        if (this.zziud != j) {
            z = true;
        }
        this.zziuu = z | z2;
        this.zziud = j;
    }

    @WorkerThread
    public final void zzaq(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziuv != j;
        this.zziuv = j;
    }

    @WorkerThread
    public final void zzar(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziuw != j;
        this.zziuw = j;
    }

    @WorkerThread
    public final void zzas(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziun != j;
        this.zziun = j;
    }

    @WorkerThread
    public final void zzat(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziuo != j;
        this.zziuo = j;
    }

    @WorkerThread
    public final void zzau(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziup != j;
        this.zziup = j;
    }

    @WorkerThread
    public final void zzav(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziuq != j;
        this.zziuq = j;
    }

    @WorkerThread
    public final void zzaw(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zzius != j;
        this.zzius = j;
    }

    @WorkerThread
    public final void zzawp() {
        this.zzitk.zzawl().zzut();
        this.zziuu = false;
    }

    @WorkerThread
    public final String zzawq() {
        this.zzitk.zzawl().zzut();
        return this.zziub;
    }

    @WorkerThread
    public final String zzawr() {
        this.zzitk.zzawl().zzut();
        return this.zziuc;
    }

    @WorkerThread
    public final long zzaws() {
        this.zzitk.zzawl().zzut();
        return this.zziue;
    }

    @WorkerThread
    public final long zzawt() {
        this.zzitk.zzawl().zzut();
        return this.zziuf;
    }

    @WorkerThread
    public final long zzawu() {
        this.zzitk.zzawl().zzut();
        return this.zziug;
    }

    @WorkerThread
    public final String zzawv() {
        this.zzitk.zzawl().zzut();
        return this.zziuh;
    }

    @WorkerThread
    public final long zzaww() {
        this.zzitk.zzawl().zzut();
        return this.zziui;
    }

    @WorkerThread
    public final long zzawx() {
        this.zzitk.zzawl().zzut();
        return this.zziuj;
    }

    @WorkerThread
    public final boolean zzawy() {
        this.zzitk.zzawl().zzut();
        return this.zziuk;
    }

    @WorkerThread
    public final long zzawz() {
        this.zzitk.zzawl().zzut();
        return this.zziud;
    }

    @WorkerThread
    public final void zzax(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziur != j;
        this.zziur = j;
    }

    @WorkerThread
    public final long zzaxa() {
        this.zzitk.zzawl().zzut();
        return this.zziuv;
    }

    @WorkerThread
    public final long zzaxb() {
        this.zzitk.zzawl().zzut();
        return this.zziuw;
    }

    @WorkerThread
    public final void zzaxc() {
        this.zzitk.zzawl().zzut();
        long j = this.zziud + 1;
        if (j > 2147483647L) {
            this.zzitk.zzawm().zzayt().zzj("Bundle index overflow. appId", zzcgj.zzje(this.mAppId));
            j = 0;
        }
        this.zziuu = true;
        this.zziud = j;
    }

    @WorkerThread
    public final long zzaxd() {
        this.zzitk.zzawl().zzut();
        return this.zziun;
    }

    @WorkerThread
    public final long zzaxe() {
        this.zzitk.zzawl().zzut();
        return this.zziuo;
    }

    @WorkerThread
    public final long zzaxf() {
        this.zzitk.zzawl().zzut();
        return this.zziup;
    }

    @WorkerThread
    public final long zzaxg() {
        this.zzitk.zzawl().zzut();
        return this.zziuq;
    }

    @WorkerThread
    public final long zzaxh() {
        this.zzitk.zzawl().zzut();
        return this.zzius;
    }

    @WorkerThread
    public final long zzaxi() {
        this.zzitk.zzawl().zzut();
        return this.zziur;
    }

    @WorkerThread
    public final String zzaxj() {
        this.zzitk.zzawl().zzut();
        return this.zziut;
    }

    @WorkerThread
    public final String zzaxk() {
        this.zzitk.zzawl().zzut();
        String str = this.zziut;
        zziq(null);
        return str;
    }

    @WorkerThread
    public final long zzaxl() {
        this.zzitk.zzawl().zzut();
        return this.zziul;
    }

    @WorkerThread
    public final boolean zzaxm() {
        this.zzitk.zzawl().zzut();
        return this.zzium;
    }

    @WorkerThread
    public final void zzay(long j) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= this.zziul != j;
        this.zziul = j;
    }

    @WorkerThread
    public final void zzbk(boolean z) {
        this.zzitk.zzawl().zzut();
        this.zziuu = this.zzium != z;
        this.zzium = z;
    }

    @WorkerThread
    public final void zzil(String str) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= !zzckn.zzas(this.zzgdh, str);
        this.zzgdh = str;
    }

    @WorkerThread
    public final void zzim(String str) {
        this.zzitk.zzawl().zzut();
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zziuu |= !zzckn.zzas(this.zzcvg, str);
        this.zzcvg = str;
    }

    @WorkerThread
    public final void zzin(String str) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= !zzckn.zzas(this.zziub, str);
        this.zziub = str;
    }

    @WorkerThread
    public final void zzio(String str) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= !zzckn.zzas(this.zziuc, str);
        this.zziuc = str;
    }

    @WorkerThread
    public final void zzip(String str) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= !zzckn.zzas(this.zziuh, str);
        this.zziuh = str;
    }

    @WorkerThread
    public final void zziq(String str) {
        this.zzitk.zzawl().zzut();
        this.zziuu |= !zzckn.zzas(this.zziut, str);
        this.zziut = str;
    }

    @WorkerThread
    public final String zzuy() {
        this.zzitk.zzawl().zzut();
        return this.zzdoc;
    }
}
