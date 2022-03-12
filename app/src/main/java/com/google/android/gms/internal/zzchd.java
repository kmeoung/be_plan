package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzchd extends zzcii {
    private static int zzjbc = 65535;
    private static int zzjbd = 2;
    private final Map<String, Map<String, String>> zzjbe = new ArrayMap();
    private final Map<String, Map<String, Boolean>> zzjbf = new ArrayMap();
    private final Map<String, Map<String, Boolean>> zzjbg = new ArrayMap();
    private final Map<String, zzckv> zzjbh = new ArrayMap();
    private final Map<String, String> zzjbj = new ArrayMap();
    private final Map<String, Map<String, Integer>> zzjbi = new ArrayMap();

    public zzchd(zzchj zzchjVar) {
        super(zzchjVar);
    }

    private static Map<String, String> zza(zzckv zzckvVar) {
        zzckw[] zzckwVarArr;
        ArrayMap arrayMap = new ArrayMap();
        if (!(zzckvVar == null || zzckvVar.zzjid == null)) {
            for (zzckw zzckwVar : zzckvVar.zzjid) {
                if (zzckwVar != null) {
                    arrayMap.put(zzckwVar.key, zzckwVar.value);
                }
            }
        }
        return arrayMap;
    }

    private final void zza(String str, zzckv zzckvVar) {
        zzcku[] zzckuVarArr;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        ArrayMap arrayMap3 = new ArrayMap();
        if (!(zzckvVar == null || zzckvVar.zzjie == null)) {
            for (zzcku zzckuVar : zzckvVar.zzjie) {
                if (TextUtils.isEmpty(zzckuVar.name)) {
                    zzawm().zzayt().log("EventConfig contained null event name");
                } else {
                    String zzik = AppMeasurement.Event.zzik(zzckuVar.name);
                    if (!TextUtils.isEmpty(zzik)) {
                        zzckuVar.name = zzik;
                    }
                    arrayMap.put(zzckuVar.name, zzckuVar.zzjhy);
                    arrayMap2.put(zzckuVar.name, zzckuVar.zzjhz);
                    if (zzckuVar.zzjia != null) {
                        if (zzckuVar.zzjia.intValue() < zzjbd || zzckuVar.zzjia.intValue() > zzjbc) {
                            zzawm().zzayt().zze("Invalid sampling rate. Event name, sample rate", zzckuVar.name, zzckuVar.zzjia);
                        } else {
                            arrayMap3.put(zzckuVar.name, zzckuVar.zzjia);
                        }
                    }
                }
            }
        }
        this.zzjbf.put(str, arrayMap);
        this.zzjbg.put(str, arrayMap2);
        this.zzjbi.put(str, arrayMap3);
    }

    @WorkerThread
    private final zzckv zzc(String str, byte[] bArr) {
        if (bArr == null) {
            return new zzckv();
        }
        zzfhb zzn = zzfhb.zzn(bArr, 0, bArr.length);
        zzckv zzckvVar = new zzckv();
        try {
            zzckvVar.zza(zzn);
            zzawm().zzayx().zze("Parsed config. version, gmp_app_id", zzckvVar.zzjib, zzckvVar.zziux);
            return zzckvVar;
        } catch (IOException e) {
            zzawm().zzayt().zze("Unable to merge remote config. appId", zzcgj.zzje(str), e);
            return new zzckv();
        }
    }

    @WorkerThread
    private final void zzjl(String str) {
        zzwu();
        zzut();
        zzbq.zzgh(str);
        if (this.zzjbh.get(str) == null) {
            byte[] zzix = zzawg().zzix(str);
            if (zzix == null) {
                this.zzjbe.put(str, null);
                this.zzjbf.put(str, null);
                this.zzjbg.put(str, null);
                this.zzjbh.put(str, null);
                this.zzjbj.put(str, null);
                this.zzjbi.put(str, null);
                return;
            }
            zzckv zzc = zzc(str, zzix);
            this.zzjbe.put(str, zza(zzc));
            zza(str, zzc);
            this.zzjbh.put(str, zzc);
            this.zzjbj.put(str, null);
        }
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    public final String zzam(String str, String str2) {
        zzut();
        zzjl(str);
        Map<String, String> map = this.zzjbe.get(str);
        if (map != null) {
            return map.get(str2);
        }
        return null;
    }

    @WorkerThread
    public final boolean zzan(String str, String str2) {
        Boolean bool;
        zzut();
        zzjl(str);
        if (zzawi().zzkf(str) && zzckn.zzkc(str2)) {
            return true;
        }
        if (zzawi().zzkg(str) && zzckn.zzjt(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zzjbf.get(str);
        if (map == null || (bool = map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @WorkerThread
    public final boolean zzao(String str, String str2) {
        Boolean bool;
        zzut();
        zzjl(str);
        if (FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zzjbg.get(str);
        if (map == null || (bool = map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @WorkerThread
    public final int zzap(String str, String str2) {
        Integer num;
        zzut();
        zzjl(str);
        Map<String, Integer> map = this.zzjbi.get(str);
        if (map == null || (num = map.get(str2)) == null) {
            return 1;
        }
        return num.intValue();
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
    public final boolean zzb(String str, byte[] bArr, String str2) {
        byte[] bArr2;
        ContentValues contentValues;
        zzckp[] zzckpVarArr;
        zzwu();
        zzut();
        zzbq.zzgh(str);
        zzckv zzc = zzc(str, bArr);
        if (zzc == null) {
            return false;
        }
        zza(str, zzc);
        this.zzjbh.put(str, zzc);
        this.zzjbj.put(str, str2);
        this.zzjbe.put(str, zza(zzc));
        zzcfh zzavz = zzavz();
        zzcko[] zzckoVarArr = zzc.zzjif;
        zzbq.checkNotNull(zzckoVarArr);
        int length = zzckoVarArr.length;
        int i = 0;
        while (i < length) {
            zzcko zzckoVar = zzckoVarArr[i];
            for (zzckp zzckpVar : zzckoVar.zzjgz) {
                String zzik = AppMeasurement.Event.zzik(zzckpVar.zzjhc);
                if (zzik != null) {
                    zzckpVar.zzjhc = zzik;
                }
                zzckq[] zzckqVarArr = zzckpVar.zzjhd;
                int length2 = zzckqVarArr.length;
                int i2 = 0;
                while (i2 < length2) {
                    zzckq zzckqVar = zzckqVarArr[i2];
                    String zzik2 = AppMeasurement.Param.zzik(zzckqVar.zzjhk);
                    if (zzik2 != null) {
                        zzckqVar.zzjhk = zzik2;
                    }
                    i2++;
                    length = length;
                }
            }
            zzcks[] zzcksVarArr = zzckoVar.zzjgy;
            for (zzcks zzcksVar : zzcksVarArr) {
                String zzik3 = AppMeasurement.UserProperty.zzik(zzcksVar.zzjhr);
                if (zzik3 != null) {
                    zzcksVar.zzjhr = zzik3;
                }
            }
            i++;
            length = length;
        }
        zzavz.zzawg().zza(str, zzckoVarArr);
        try {
            zzc.zzjif = null;
            bArr2 = new byte[zzc.zzhl()];
            zzc.zza(zzfhc.zzo(bArr2, 0, bArr2.length));
        } catch (IOException e) {
            zzawm().zzayt().zze("Unable to serialize reduced-size config. Storing full config instead. appId", zzcgj.zzje(str), e);
            bArr2 = bArr;
        }
        zzcfl zzawg = zzawg();
        zzbq.zzgh(str);
        zzawg.zzut();
        zzawg.zzwu();
        new ContentValues().put("remote_config", bArr2);
        try {
            if (zzawg.getWritableDatabase().update("apps", contentValues, "app_id = ?", new String[]{str}) == 0) {
                zzawg.zzawm().zzayr().zzj("Failed to update remote config (got 0). appId", zzcgj.zzje(str));
                return true;
            }
        } catch (SQLiteException e2) {
            zzawg.zzawm().zzayr().zze("Error storing remote config. appId", zzcgj.zzje(str), e2);
        }
        return true;
    }

    @WorkerThread
    public final zzckv zzjm(String str) {
        zzwu();
        zzut();
        zzbq.zzgh(str);
        zzjl(str);
        return this.zzjbh.get(str);
    }

    @WorkerThread
    public final String zzjn(String str) {
        zzut();
        return this.zzjbj.get(str);
    }

    @WorkerThread
    public final void zzjo(String str) {
        zzut();
        this.zzjbj.put(str, null);
    }

    @WorkerThread
    public final void zzjp(String str) {
        zzut();
        this.zzjbh.remove(str);
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
