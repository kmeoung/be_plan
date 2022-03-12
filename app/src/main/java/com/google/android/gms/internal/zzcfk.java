package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzs;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public final class zzcfk extends zzcih {
    private Boolean zzdsn;

    public zzcfk(zzchj zzchjVar) {
        super(zzchjVar);
    }

    public static long zzaxp() {
        return zzcfz.zziyl.get().longValue();
    }

    public static long zzaxq() {
        return zzcfz.zzixl.get().longValue();
    }

    public static boolean zzaxs() {
        return zzcfz.zzixg.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final long zza(String str, zzcga<Long> zzcgaVar) {
        if (str != null) {
            String zzam = zzawj().zzam(str, zzcgaVar.getKey());
            if (!TextUtils.isEmpty(zzam)) {
                try {
                    return zzcgaVar.get(Long.valueOf(Long.valueOf(zzam).longValue())).longValue();
                } catch (NumberFormatException unused) {
                }
            }
        }
        return zzcgaVar.get().longValue();
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

    public final boolean zzaxo() {
        Boolean zzis = zzis("firebase_analytics_collection_deactivated");
        return zzis != null && zzis.booleanValue();
    }

    public final String zzaxr() {
        String str;
        zzcgl zzcglVar;
        Object e;
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class).invoke(null, "debug.firebase.analytics.app", "");
        } catch (ClassNotFoundException e2) {
            e = e2;
            zzcglVar = zzawm().zzayr();
            str = "Could not find SystemProperties class";
            zzcglVar.zzj(str, e);
            return "";
        } catch (IllegalAccessException e3) {
            e = e3;
            zzcglVar = zzawm().zzayr();
            str = "Could not access SystemProperties.get()";
            zzcglVar.zzj(str, e);
            return "";
        } catch (NoSuchMethodException e4) {
            e = e4;
            zzcglVar = zzawm().zzayr();
            str = "Could not find SystemProperties.get() method";
            zzcglVar.zzj(str, e);
            return "";
        } catch (InvocationTargetException e5) {
            e = e5;
            zzcglVar = zzawm().zzayr();
            str = "SystemProperties.get() threw an exception";
            zzcglVar.zzj(str, e);
            return "";
        }
    }

    public final int zzb(String str, zzcga<Integer> zzcgaVar) {
        if (str != null) {
            String zzam = zzawj().zzam(str, zzcgaVar.getKey());
            if (!TextUtils.isEmpty(zzam)) {
                try {
                    return zzcgaVar.get(Integer.valueOf(Integer.valueOf(zzam).intValue())).intValue();
                } catch (NumberFormatException unused) {
                }
            }
        }
        return zzcgaVar.get().intValue();
    }

    public final int zzir(@Size(min = 1) String str) {
        return zzb(str, zzcfz.zzixw);
    }

    @Nullable
    public final Boolean zzis(@Size(min = 1) String str) {
        zzbq.zzgh(str);
        try {
            if (getContext().getPackageManager() == null) {
                zzawm().zzayr().log("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo applicationInfo = zzbgc.zzcy(getContext()).getApplicationInfo(getContext().getPackageName(), 128);
            if (applicationInfo == null) {
                zzawm().zzayr().log("Failed to load metadata: ApplicationInfo is null");
                return null;
            } else if (applicationInfo.metaData == null) {
                zzawm().zzayr().log("Failed to load metadata: Metadata bundle is null");
                return null;
            } else if (!applicationInfo.metaData.containsKey(str)) {
                return null;
            } else {
                return Boolean.valueOf(applicationInfo.metaData.getBoolean(str));
            }
        } catch (PackageManager.NameNotFoundException e) {
            zzawm().zzayr().zzj("Failed to load metadata: Package name not found", e);
            return null;
        }
    }

    public final boolean zzit(String str) {
        return "1".equals(zzawj().zzam(str, "gaia_collection_enabled"));
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzut() {
        super.zzut();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzd zzwh() {
        return super.zzwh();
    }

    public final boolean zzye() {
        if (this.zzdsn == null) {
            synchronized (this) {
                if (this.zzdsn == null) {
                    ApplicationInfo applicationInfo = getContext().getApplicationInfo();
                    String zzamc = zzs.zzamc();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.zzdsn = Boolean.valueOf(str != null && str.equals(zzamc));
                    }
                    if (this.zzdsn == null) {
                        this.zzdsn = Boolean.TRUE;
                        zzawm().zzayr().log("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzdsn.booleanValue();
    }
}
