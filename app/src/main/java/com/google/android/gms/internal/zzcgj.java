package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.measurement.AppMeasurement;

/* loaded from: classes.dex */
public final class zzcgj extends zzcii {
    private final String zzfye = zzcfz.zzixi.get();
    private final long zziui = 11720;
    private final char zziyy;
    private final zzcgl zziyz;
    private final zzcgl zziza;
    private final zzcgl zzizb;
    private final zzcgl zzizc;
    private final zzcgl zzizd;
    private final zzcgl zzize;
    private final zzcgl zzizf;
    private final zzcgl zzizg;
    private final zzcgl zzizh;

    public zzcgj(zzchj zzchjVar) {
        super(zzchjVar);
        this.zziyy = zzawo().zzye() ? 'C' : 'c';
        this.zziyz = new zzcgl(this, 6, false, false);
        this.zziza = new zzcgl(this, 6, true, false);
        this.zzizb = new zzcgl(this, 6, false, true);
        this.zzizc = new zzcgl(this, 5, false, false);
        this.zzizd = new zzcgl(this, 5, true, false);
        this.zzize = new zzcgl(this, 5, false, true);
        this.zzizf = new zzcgl(this, 4, false, false);
        this.zzizg = new zzcgl(this, 3, false, false);
        this.zzizh = new zzcgl(this, 2, false, false);
    }

    private static String zza(boolean z, String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            str = "";
        }
        String zzb = zzb(z, obj);
        String zzb2 = zzb(z, obj2);
        String zzb3 = zzb(z, obj3);
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(zzb)) {
            sb.append(str2);
            sb.append(zzb);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzb2)) {
            sb.append(str2);
            sb.append(zzb2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzb3)) {
            sb.append(str2);
            sb.append(zzb3);
        }
        return sb.toString();
    }

    private static String zzb(boolean z, Object obj) {
        String str;
        StackTraceElement[] stackTrace;
        String className;
        if (obj == null) {
            return "";
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf(((Integer) obj).intValue());
        }
        if (obj instanceof Long) {
            if (!z) {
                return String.valueOf(obj);
            }
            Long l = (Long) obj;
            if (Math.abs(l.longValue()) < 100) {
                return String.valueOf(obj);
            }
            String str2 = String.valueOf(obj).charAt(0) == '-' ? "-" : "";
            String valueOf = String.valueOf(Math.abs(l.longValue()));
            long round = Math.round(Math.pow(10.0d, valueOf.length() - 1));
            long round2 = Math.round(Math.pow(10.0d, valueOf.length()) - 1.0d);
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 43 + String.valueOf(str2).length());
            sb.append(str2);
            sb.append(round);
            sb.append("...");
            sb.append(str2);
            sb.append(round2);
            return sb.toString();
        } else if (obj instanceof Boolean) {
            return String.valueOf(obj);
        } else {
            if (obj instanceof Throwable) {
                Throwable th = (Throwable) obj;
                StringBuilder sb2 = new StringBuilder(z ? th.getClass().getName() : th.toString());
                String zzjf = zzjf(AppMeasurement.class.getCanonicalName());
                String zzjf2 = zzjf(zzchj.class.getCanonicalName());
                for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                    if (!stackTraceElement.isNativeMethod() && (className = stackTraceElement.getClassName()) != null) {
                        String zzjf3 = zzjf(className);
                        if (zzjf3.equals(zzjf) || zzjf3.equals(zzjf2)) {
                            sb2.append(": ");
                            sb2.append(stackTraceElement);
                            break;
                        }
                    }
                }
                return sb2.toString();
            } else if (!(obj instanceof zzcgm)) {
                return z ? "-" : String.valueOf(obj);
            } else {
                str = ((zzcgm) obj).zzguz;
                return str;
            }
        }
    }

    public static Object zzje(String str) {
        if (str == null) {
            return null;
        }
        return new zzcgm(str);
    }

    private static String zzjf(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final void zza(int i, boolean z, boolean z2, String str, Object obj, Object obj2, Object obj3) {
        String str2;
        if (!z && zzae(i)) {
            zzk(i, zza(false, str, obj, obj2, obj3));
        }
        if (!z2 && i >= 5) {
            zzbq.checkNotNull(str);
            zzche zzazm = this.zzitk.zzazm();
            if (zzazm == null) {
                str2 = "Scheduler not set. Not logging error/warn";
            } else if (!zzazm.isInitialized()) {
                str2 = "Scheduler not initialized. Not logging error/warn";
            } else {
                if (i < 0) {
                    i = 0;
                }
                if (i >= 9) {
                    i = 8;
                }
                char charAt = "01VDIWEA?".charAt(i);
                char c = this.zziyy;
                long j = this.zziui;
                String zza = zza(true, str, obj, obj2, obj3);
                StringBuilder sb = new StringBuilder(String.valueOf("2").length() + 23 + String.valueOf(zza).length());
                sb.append("2");
                sb.append(charAt);
                sb.append(c);
                sb.append(j);
                sb.append(":");
                sb.append(zza);
                String sb2 = sb.toString();
                if (sb2.length() > 1024) {
                    sb2 = str.substring(0, 1024);
                }
                zzazm.zzg(new zzcgk(this, sb2));
                return;
            }
            zzk(6, str2);
        }
    }

    public final boolean zzae(int i) {
        return Log.isLoggable(this.zzfye, i);
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

    public final zzcgl zzayr() {
        return this.zziyz;
    }

    public final zzcgl zzays() {
        return this.zziza;
    }

    public final zzcgl zzayt() {
        return this.zzizc;
    }

    public final zzcgl zzayu() {
        return this.zzize;
    }

    public final zzcgl zzayv() {
        return this.zzizf;
    }

    public final zzcgl zzayw() {
        return this.zzizg;
    }

    public final zzcgl zzayx() {
        return this.zzizh;
    }

    public final String zzayy() {
        Pair<String, Long> zzzs = zzawn().zzizv.zzzs();
        if (zzzs == null || zzzs == zzcgu.zzizu) {
            return null;
        }
        String valueOf = String.valueOf(zzzs.second);
        String str = (String) zzzs.first;
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(str).length());
        sb.append(valueOf);
        sb.append(":");
        sb.append(str);
        return sb.toString();
    }

    public final void zzk(int i, String str) {
        Log.println(i, this.zzfye, str);
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
