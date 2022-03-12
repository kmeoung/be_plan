package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.measurement.AppMeasurement;

/* loaded from: classes.dex */
public final class zzcgh extends zzcii {
    private static String[] zziyv = new String[AppMeasurement.Event.zzitl.length];
    private static String[] zziyw = new String[AppMeasurement.Param.zzitn.length];
    private static String[] zziyx = new String[AppMeasurement.UserProperty.zzits.length];

    public zzcgh(zzchj zzchjVar) {
        super(zzchjVar);
    }

    @Nullable
    private static String zza(String str, String[] strArr, String[] strArr2, String[] strArr3) {
        String str2;
        zzbq.checkNotNull(strArr);
        zzbq.checkNotNull(strArr2);
        zzbq.checkNotNull(strArr3);
        boolean z = true;
        zzbq.checkArgument(strArr.length == strArr2.length);
        if (strArr.length != strArr3.length) {
            z = false;
        }
        zzbq.checkArgument(z);
        for (int i = 0; i < strArr.length; i++) {
            if (zzckn.zzas(str, strArr[i])) {
                synchronized (strArr3) {
                    if (strArr3[i] == null) {
                        strArr3[i] = strArr2[i] + "(" + strArr[i] + ")";
                    }
                    str2 = strArr3[i];
                }
                return str2;
            }
        }
        return str;
    }

    private static void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("  ");
        }
    }

    private final void zza(StringBuilder sb, int i, zzckq zzckqVar) {
        String[] strArr;
        if (zzckqVar != null) {
            zza(sb, i);
            sb.append("filter {\n");
            zza(sb, i, "complement", zzckqVar.zzjhj);
            zza(sb, i, "param_name", zzjc(zzckqVar.zzjhk));
            int i2 = i + 1;
            zzckt zzcktVar = zzckqVar.zzjhh;
            if (zzcktVar != null) {
                zza(sb, i2);
                sb.append("string_filter");
                sb.append(" {\n");
                if (zzcktVar.zzjht != null) {
                    String str = "UNKNOWN_MATCH_TYPE";
                    switch (zzcktVar.zzjht.intValue()) {
                        case 1:
                            str = "REGEXP";
                            break;
                        case 2:
                            str = "BEGINS_WITH";
                            break;
                        case 3:
                            str = "ENDS_WITH";
                            break;
                        case 4:
                            str = "PARTIAL";
                            break;
                        case 5:
                            str = "EXACT";
                            break;
                        case 6:
                            str = "IN_LIST";
                            break;
                    }
                    zza(sb, i2, "match_type", str);
                }
                zza(sb, i2, "expression", zzcktVar.zzjhu);
                zza(sb, i2, "case_sensitive", zzcktVar.zzjhv);
                if (zzcktVar.zzjhw.length > 0) {
                    zza(sb, i2 + 1);
                    sb.append("expression_list {\n");
                    for (String str2 : zzcktVar.zzjhw) {
                        zza(sb, i2 + 2);
                        sb.append(str2);
                        sb.append("\n");
                    }
                    sb.append("}\n");
                }
                zza(sb, i2);
                sb.append("}\n");
            }
            zza(sb, i2, "number_filter", zzckqVar.zzjhi);
            zza(sb, i);
            sb.append("}\n");
        }
    }

    private final void zza(StringBuilder sb, int i, String str, zzckr zzckrVar) {
        if (zzckrVar != null) {
            zza(sb, i);
            sb.append(str);
            sb.append(" {\n");
            if (zzckrVar.zzjhl != null) {
                String str2 = "UNKNOWN_COMPARISON_TYPE";
                switch (zzckrVar.zzjhl.intValue()) {
                    case 1:
                        str2 = "LESS_THAN";
                        break;
                    case 2:
                        str2 = "GREATER_THAN";
                        break;
                    case 3:
                        str2 = "EQUAL";
                        break;
                    case 4:
                        str2 = "BETWEEN";
                        break;
                }
                zza(sb, i, "comparison_type", str2);
            }
            zza(sb, i, "match_as_float", zzckrVar.zzjhm);
            zza(sb, i, "comparison_value", zzckrVar.zzjhn);
            zza(sb, i, "min_comparison_value", zzckrVar.zzjho);
            zza(sb, i, "max_comparison_value", zzckrVar.zzjhp);
            zza(sb, i);
            sb.append("}\n");
        }
    }

    private static void zza(StringBuilder sb, int i, String str, zzclc zzclcVar) {
        if (zzclcVar != null) {
            int i2 = i + 1;
            zza(sb, i2);
            sb.append(str);
            sb.append(" {\n");
            if (zzclcVar.zzjjv != null) {
                zza(sb, i2 + 1);
                sb.append("results: ");
                int i3 = 0;
                for (long j : zzclcVar.zzjjv) {
                    Long valueOf = Long.valueOf(j);
                    i3++;
                    if (i3 != 0) {
                        sb.append(", ");
                    }
                    sb.append(valueOf);
                }
                sb.append('\n');
            }
            if (zzclcVar.zzjju != null) {
                zza(sb, i2 + 1);
                sb.append("status: ");
                int i4 = 0;
                for (long j2 : zzclcVar.zzjju) {
                    Long valueOf2 = Long.valueOf(j2);
                    i4++;
                    if (i4 != 0) {
                        sb.append(", ");
                    }
                    sb.append(valueOf2);
                }
                sb.append('\n');
            }
            zza(sb, i2);
            sb.append("}\n");
        }
    }

    private static void zza(StringBuilder sb, int i, String str, Object obj) {
        if (obj != null) {
            zza(sb, i + 1);
            sb.append(str);
            sb.append(": ");
            sb.append(obj);
            sb.append('\n');
        }
    }

    private final void zza(StringBuilder sb, int i, zzckx[] zzckxVarArr) {
        if (zzckxVarArr != null) {
            for (zzckx zzckxVar : zzckxVarArr) {
                if (zzckxVar != null) {
                    zza(sb, 2);
                    sb.append("audience_membership {\n");
                    zza(sb, 2, "audience_id", zzckxVar.zzjgx);
                    zza(sb, 2, "new_audience", zzckxVar.zzjik);
                    zza(sb, 2, "current_data", zzckxVar.zzjii);
                    zza(sb, 2, "previous_data", zzckxVar.zzjij);
                    zza(sb, 2);
                    sb.append("}\n");
                }
            }
        }
    }

    private final void zza(StringBuilder sb, int i, zzcky[] zzckyVarArr) {
        if (zzckyVarArr != null) {
            for (zzcky zzckyVar : zzckyVarArr) {
                if (zzckyVar != null) {
                    zza(sb, 2);
                    sb.append("event {\n");
                    zza(sb, 2, "name", zzjb(zzckyVar.name));
                    zza(sb, 2, "timestamp_millis", zzckyVar.zzjin);
                    zza(sb, 2, "previous_timestamp_millis", zzckyVar.zzjio);
                    zza(sb, 2, "count", zzckyVar.count);
                    zzckz[] zzckzVarArr = zzckyVar.zzjim;
                    if (zzckzVarArr != null) {
                        for (zzckz zzckzVar : zzckzVarArr) {
                            if (zzckzVar != null) {
                                zza(sb, 3);
                                sb.append("param {\n");
                                zza(sb, 3, "name", zzjc(zzckzVar.name));
                                zza(sb, 3, "string_value", zzckzVar.zzfzi);
                                zza(sb, 3, "int_value", zzckzVar.zzjiq);
                                zza(sb, 3, "double_value", zzckzVar.zzjgq);
                                zza(sb, 3);
                                sb.append("}\n");
                            }
                        }
                    }
                    zza(sb, 2);
                    sb.append("}\n");
                }
            }
        }
    }

    private final void zza(StringBuilder sb, int i, zzcld[] zzcldVarArr) {
        if (zzcldVarArr != null) {
            for (zzcld zzcldVar : zzcldVarArr) {
                if (zzcldVar != null) {
                    zza(sb, 2);
                    sb.append("user_property {\n");
                    zza(sb, 2, "set_timestamp_millis", zzcldVar.zzjjx);
                    zza(sb, 2, "name", zzjd(zzcldVar.name));
                    zza(sb, 2, "string_value", zzcldVar.zzfzi);
                    zza(sb, 2, "int_value", zzcldVar.zzjiq);
                    zza(sb, 2, "double_value", zzcldVar.zzjgq);
                    zza(sb, 2);
                    sb.append("}\n");
                }
            }
        }
    }

    private final boolean zzayq() {
        return this.zzitk.zzawm().zzae(3);
    }

    @Nullable
    private final String zzb(zzcfu zzcfuVar) {
        if (zzcfuVar == null) {
            return null;
        }
        return !zzayq() ? zzcfuVar.toString() : zzx(zzcfuVar.zzayl());
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Nullable
    public final String zza(zzcfs zzcfsVar) {
        if (zzcfsVar == null) {
            return null;
        }
        if (!zzayq()) {
            return zzcfsVar.toString();
        }
        return "Event{appId='" + zzcfsVar.mAppId + "', name='" + zzjb(zzcfsVar.mName) + "', params=" + zzb(zzcfsVar.zziwo) + "}";
    }

    public final String zza(zzckp zzckpVar) {
        if (zzckpVar == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nevent_filter {\n");
        zza(sb, 0, "filter_id", zzckpVar.zzjhb);
        zza(sb, 0, "event_name", zzjb(zzckpVar.zzjhc));
        zza(sb, 1, "event_count_filter", zzckpVar.zzjhf);
        sb.append("  filters {\n");
        for (zzckq zzckqVar : zzckpVar.zzjhd) {
            zza(sb, 2, zzckqVar);
        }
        zza(sb, 1);
        sb.append("}\n}\n");
        return sb.toString();
    }

    public final String zza(zzcks zzcksVar) {
        if (zzcksVar == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nproperty_filter {\n");
        zza(sb, 0, "filter_id", zzcksVar.zzjhb);
        zza(sb, 0, "property_name", zzjd(zzcksVar.zzjhr));
        zza(sb, 1, zzcksVar.zzjhs);
        sb.append("}\n");
        return sb.toString();
    }

    public final String zza(zzcla zzclaVar) {
        zzclb[] zzclbVarArr;
        StringBuilder sb = new StringBuilder();
        sb.append("\nbatch {\n");
        if (zzclaVar.zzjir != null) {
            for (zzclb zzclbVar : zzclaVar.zzjir) {
                if (!(zzclbVar == null || zzclbVar == null)) {
                    zza(sb, 1);
                    sb.append("bundle {\n");
                    zza(sb, 1, "protocol_version", zzclbVar.zzjit);
                    zza(sb, 1, "platform", zzclbVar.zzjjb);
                    zza(sb, 1, "gmp_version", zzclbVar.zzjjf);
                    zza(sb, 1, "uploading_gmp_version", zzclbVar.zzjjg);
                    zza(sb, 1, "config_version", zzclbVar.zzjjs);
                    zza(sb, 1, "gmp_app_id", zzclbVar.zziux);
                    zza(sb, 1, "app_id", zzclbVar.zzch);
                    zza(sb, 1, "app_version", zzclbVar.zzicq);
                    zza(sb, 1, "app_version_major", zzclbVar.zzjjo);
                    zza(sb, 1, "firebase_instance_id", zzclbVar.zzivf);
                    zza(sb, 1, "dev_cert_hash", zzclbVar.zzjjk);
                    zza(sb, 1, "app_store", zzclbVar.zziuy);
                    zza(sb, 1, "upload_timestamp_millis", zzclbVar.zzjiw);
                    zza(sb, 1, "start_timestamp_millis", zzclbVar.zzjix);
                    zza(sb, 1, "end_timestamp_millis", zzclbVar.zzjiy);
                    zza(sb, 1, "previous_bundle_start_timestamp_millis", zzclbVar.zzjiz);
                    zza(sb, 1, "previous_bundle_end_timestamp_millis", zzclbVar.zzjja);
                    zza(sb, 1, "app_instance_id", zzclbVar.zzjjj);
                    zza(sb, 1, "resettable_device_id", zzclbVar.zzjjh);
                    zza(sb, 1, "device_id", zzclbVar.zzjjr);
                    zza(sb, 1, "limited_ad_tracking", zzclbVar.zzjji);
                    zza(sb, 1, "os_version", zzclbVar.zzcv);
                    zza(sb, 1, "device_model", zzclbVar.zzjjc);
                    zza(sb, 1, "user_default_language", zzclbVar.zzjjd);
                    zza(sb, 1, "time_zone_offset_minutes", zzclbVar.zzjje);
                    zza(sb, 1, "bundle_sequential_index", zzclbVar.zzjjl);
                    zza(sb, 1, "service_upload", zzclbVar.zzjjm);
                    zza(sb, 1, "health_monitor", zzclbVar.zzivb);
                    if (zzclbVar.zzfhr.longValue() != 0) {
                        zza(sb, 1, "android_id", zzclbVar.zzfhr);
                    }
                    zza(sb, 1, zzclbVar.zzjiv);
                    zza(sb, 1, zzclbVar.zzjjn);
                    zza(sb, 1, zzclbVar.zzjiu);
                    zza(sb, 1);
                    sb.append("}\n");
                }
            }
        }
        sb.append("}\n");
        return sb.toString();
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

    @Nullable
    public final String zzb(zzcfx zzcfxVar) {
        if (zzcfxVar == null) {
            return null;
        }
        if (!zzayq()) {
            return zzcfxVar.toString();
        }
        return "origin=" + zzcfxVar.zzivk + ",name=" + zzjb(zzcfxVar.name) + ",params=" + zzb(zzcfxVar.zziwy);
    }

    @Nullable
    public final String zzjb(String str) {
        if (str == null) {
            return null;
        }
        return !zzayq() ? str : zza(str, AppMeasurement.Event.zzitm, AppMeasurement.Event.zzitl, zziyv);
    }

    @Nullable
    public final String zzjc(String str) {
        if (str == null) {
            return null;
        }
        return !zzayq() ? str : zza(str, AppMeasurement.Param.zzito, AppMeasurement.Param.zzitn, zziyw);
    }

    @Nullable
    public final String zzjd(String str) {
        if (str == null) {
            return null;
        }
        if (!zzayq()) {
            return str;
        }
        if (!str.startsWith("_exp_")) {
            return zza(str, AppMeasurement.UserProperty.zzitt, AppMeasurement.UserProperty.zzits, zziyx);
        }
        return "experiment_id(" + str + ")";
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzut() {
        super.zzut();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzd zzwh() {
        return super.zzwh();
    }

    @Nullable
    public final String zzx(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        if (!zzayq()) {
            return bundle.toString();
        }
        StringBuilder sb = new StringBuilder();
        for (String str : bundle.keySet()) {
            sb.append(sb.length() != 0 ? ", " : "Bundle[{");
            sb.append(zzjc(str));
            sb.append("=");
            sb.append(bundle.get(str));
        }
        sb.append("}]");
        return sb.toString();
    }
}
