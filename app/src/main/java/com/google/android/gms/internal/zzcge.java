package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.util.zzd;
import com.google.firebase.iid.FirebaseInstanceId;
import java.math.BigInteger;
import java.util.Locale;

/* loaded from: classes.dex */
public final class zzcge extends zzcii {
    private String mAppId;
    private String zzcvg;
    private String zzdob;
    private String zzdoc;
    private String zziuh;
    private long zziul;
    private int zziyp;
    private long zziyq;
    private int zziyr;

    public zzcge(zzchj zzchjVar) {
        super(zzchjVar);
    }

    @WorkerThread
    private final String zzawr() {
        zzut();
        try {
            return FirebaseInstanceId.getInstance().getId();
        } catch (IllegalStateException unused) {
            zzawm().zzayt().log("Failed to retrieve Firebase Instance Id");
            return null;
        }
    }

    public final String getAppId() {
        zzwu();
        return this.mAppId;
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final String getGmpAppId() {
        zzwu();
        return this.zzcvg;
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
        return true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:2|(1:4)(7:68|5|6|(1:9)(2:10|(1:12))|64|13|(6:15|(1:17)|18|66|19|20))|23|(1:28)(1:27)|(1:(1:31)(1:32))|(3:34|(1:36)(1:(1:(9:46|48|70|49|(1:51)|52|(1:54)|57|(2:59|60)(2:61|62))(1:45))(1:41))|37)|47|48|70|49|(0)|52|(0)|57|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0159, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x015a, code lost:
        zzawm().zzayr().zze("getGoogleAppId or isMeasurementEnabled failed with exception. appId", com.google.android.gms.internal.zzcgj.zzje(r3), r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0147 A[Catch: IllegalStateException -> 0x0159, TRY_LEAVE, TryCatch #3 {IllegalStateException -> 0x0159, blocks: (B:49:0x0137, B:52:0x0143, B:54:0x0147), top: B:70:0x0137 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x017c  */
    @Override // com.google.android.gms.internal.zzcii
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected final void zzaym() {
        /*
            Method dump skipped, instructions count: 383
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcge.zzaym():void");
    }

    @WorkerThread
    public final String zzayn() {
        byte[] bArr = new byte[16];
        zzawi().zzban().nextBytes(bArr);
        return String.format(Locale.US, "%032x", new BigInteger(1, bArr));
    }

    public final int zzayo() {
        zzwu();
        return this.zziyp;
    }

    public final int zzayp() {
        zzwu();
        return this.zziyr;
    }

    @WorkerThread
    public final zzcff zzja(String str) {
        zzut();
        String appId = getAppId();
        String gmpAppId = getGmpAppId();
        zzwu();
        String str2 = this.zzdoc;
        long zzayo = zzayo();
        zzwu();
        String str3 = this.zziuh;
        zzwu();
        zzut();
        if (this.zziyq == 0) {
            this.zziyq = this.zzitk.zzawi().zzaf(getContext(), getContext().getPackageName());
        }
        long j = this.zziyq;
        boolean isEnabled = this.zzitk.isEnabled();
        boolean z = true;
        boolean z2 = !zzawn().zzjao;
        String zzawr = zzawr();
        zzwu();
        long zzazt = this.zzitk.zzazt();
        int zzayp = zzayp();
        Boolean zzis = zzawo().zzis("google_analytics_adid_collection_enabled");
        if (zzis != null && !zzis.booleanValue()) {
            z = false;
        }
        return new zzcff(appId, gmpAppId, str2, zzayo, str3, 11720L, j, str, isEnabled, z2, zzawr, 0L, zzazt, zzayp, Boolean.valueOf(z).booleanValue());
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
