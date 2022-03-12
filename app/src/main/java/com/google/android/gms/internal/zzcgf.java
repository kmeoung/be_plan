package com.google.android.gms.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.util.zzd;

/* loaded from: classes.dex */
public final class zzcgf extends zzcii {
    private final zzcgg zziys = new zzcgg(this, getContext(), "google_app_measurement_local.db");
    private boolean zziyt;

    public zzcgf(zzchj zzchjVar) {
        super(zzchjVar);
    }

    @WorkerThread
    private final SQLiteDatabase getWritableDatabase() {
        if (this.zziyt) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zziys.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zziyt = true;
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0140 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0140 A[SYNTHETIC] */
    @android.support.annotation.WorkerThread
    @android.annotation.TargetApi(11)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean zzb(int r20, byte[] r21) {
        /*
            Method dump skipped, instructions count: 355
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcgf.zzb(int, byte[]):boolean");
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    public final void resetAnalyticsData() {
        zzut();
        try {
            int delete = getWritableDatabase().delete("messages", null, null) + 0;
            if (delete > 0) {
                zzawm().zzayx().zzj("Reset local analytics data. records", Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzawm().zzayr().zzj("Error resetting local analytics data. error", e);
        }
    }

    public final boolean zza(zzcfx zzcfxVar) {
        Parcel obtain = Parcel.obtain();
        zzcfxVar.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zzb(0, marshall);
        }
        zzawm().zzayt().log("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zza(zzckk zzckkVar) {
        Parcel obtain = Parcel.obtain();
        zzckkVar.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zzb(1, marshall);
        }
        zzawm().zzayt().log("User property too long for local database. Sending directly to service");
        return false;
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

    public final boolean zzc(zzcfi zzcfiVar) {
        zzawi();
        byte[] zza = zzckn.zza(zzcfiVar);
        if (zza.length <= 131072) {
            return zzb(2, zza);
        }
        zzawm().zzayt().log("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x019d A[Catch: all -> 0x01ee, TryCatch #10 {all -> 0x01ee, blocks: (B:103:0x0197, B:105:0x019d, B:107:0x01a1, B:109:0x01aa, B:111:0x01b0, B:112:0x01b3, B:119:0x01d1), top: B:140:0x0197 }] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01aa A[Catch: all -> 0x01ee, TryCatch #10 {all -> 0x01ee, blocks: (B:103:0x0197, B:105:0x019d, B:107:0x01a1, B:109:0x01aa, B:111:0x01b0, B:112:0x01b3, B:119:0x01d1), top: B:140:0x0197 }] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01e8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01e8 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.util.List<com.google.android.gms.internal.zzbej>] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Unknown variable types count: 1 */
    @android.annotation.TargetApi(11)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<com.google.android.gms.internal.zzbej> zzeb(int r22) {
        /*
            Method dump skipped, instructions count: 523
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcgf.zzeb(int):java.util.List");
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzut() {
        super.zzut();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzd zzwh() {
        return super.zzwh();
    }
}
