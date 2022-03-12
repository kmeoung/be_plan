package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzcik extends zzcii {
    protected zzciy zzjec;
    private AppMeasurement.EventInterceptor zzjed;
    private boolean zzjef;
    private final Set<AppMeasurement.OnEventListener> zzjee = new CopyOnWriteArraySet();
    private final AtomicReference<String> zzjeg = new AtomicReference<>();

    public zzcik(zzchj zzchjVar) {
        super(zzchjVar);
    }

    private final void zza(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        long currentTimeMillis = zzwh().currentTimeMillis();
        zzbq.checkNotNull(conditionalUserProperty);
        zzbq.zzgh(conditionalUserProperty.mName);
        zzbq.zzgh(conditionalUserProperty.mOrigin);
        zzbq.checkNotNull(conditionalUserProperty.mValue);
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        String str = conditionalUserProperty.mName;
        Object obj = conditionalUserProperty.mValue;
        if (zzawi().zzjx(str) != 0) {
            zzawm().zzayr().zzj("Invalid conditional user property name", zzawh().zzjd(str));
        } else if (zzawi().zzl(str, obj) != 0) {
            zzawm().zzayr().zze("Invalid conditional user property value", zzawh().zzjd(str), obj);
        } else {
            Object zzm = zzawi().zzm(str, obj);
            if (zzm == null) {
                zzawm().zzayr().zze("Unable to normalize conditional user property value", zzawh().zzjd(str), obj);
                return;
            }
            conditionalUserProperty.mValue = zzm;
            long j = conditionalUserProperty.mTriggerTimeout;
            if (TextUtils.isEmpty(conditionalUserProperty.mTriggerEventName) || (j <= 15552000000L && j >= 1)) {
                long j2 = conditionalUserProperty.mTimeToLive;
                if (j2 > 15552000000L || j2 < 1) {
                    zzawm().zzayr().zze("Invalid conditional user property time to live", zzawh().zzjd(str), Long.valueOf(j2));
                } else {
                    zzawl().zzg(new zzcim(this, conditionalUserProperty));
                }
            } else {
                zzawm().zzayr().zze("Invalid conditional user property timeout", zzawh().zzjd(str), Long.valueOf(j));
            }
        }
    }

    private final void zza(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        Bundle bundle2;
        if (bundle == null) {
            bundle2 = new Bundle();
        } else {
            Bundle bundle3 = new Bundle(bundle);
            for (String str4 : bundle3.keySet()) {
                Object obj = bundle3.get(str4);
                if (obj instanceof Bundle) {
                    bundle3.putBundle(str4, new Bundle((Bundle) obj));
                } else {
                    int i = 0;
                    if (obj instanceof Parcelable[]) {
                        Parcelable[] parcelableArr = (Parcelable[]) obj;
                        while (i < parcelableArr.length) {
                            if (parcelableArr[i] instanceof Bundle) {
                                parcelableArr[i] = new Bundle((Bundle) parcelableArr[i]);
                            }
                            i++;
                        }
                    } else if (obj instanceof ArrayList) {
                        ArrayList arrayList = (ArrayList) obj;
                        while (i < arrayList.size()) {
                            Object obj2 = arrayList.get(i);
                            if (obj2 instanceof Bundle) {
                                arrayList.set(i, new Bundle((Bundle) obj2));
                            }
                            i++;
                        }
                    }
                }
            }
            bundle2 = bundle3;
        }
        zzawl().zzg(new zzcis(this, str, str2, j, bundle2, z, z2, z3, str3));
    }

    private final void zza(String str, String str2, long j, Object obj) {
        zzawl().zzg(new zzcit(this, str, str2, obj, j));
    }

    private final void zza(String str, String str2, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zza(str, str2, zzwh().currentTimeMillis(), bundle, true, z2, z3, null);
    }

    @WorkerThread
    public final void zza(String str, String str2, Object obj, long j) {
        zzbq.zzgh(str);
        zzbq.zzgh(str2);
        zzut();
        zzwu();
        if (!this.zzitk.isEnabled()) {
            zzawm().zzayw().log("User property not set since app measurement is disabled");
        } else if (this.zzitk.zzazj()) {
            zzawm().zzayw().zze("Setting user property (FE)", zzawh().zzjb(str2), obj);
            zzawd().zzb(new zzckk(str2, j, obj, str));
        }
    }

    private final void zza(String str, String str2, String str3, Bundle bundle) {
        long currentTimeMillis = zzwh().currentTimeMillis();
        zzbq.zzgh(str2);
        AppMeasurement.ConditionalUserProperty conditionalUserProperty = new AppMeasurement.ConditionalUserProperty();
        conditionalUserProperty.mAppId = str;
        conditionalUserProperty.mName = str2;
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        if (str3 != null) {
            conditionalUserProperty.mExpiredEventName = str3;
            conditionalUserProperty.mExpiredEventParams = bundle;
        }
        zzawl().zzg(new zzcin(this, conditionalUserProperty));
    }

    private final Map<String, Object> zzb(String str, String str2, String str3, boolean z) {
        zzcgl zzayt;
        String str4;
        if (zzawl().zzazg()) {
            zzayt = zzawm().zzayr();
            str4 = "Cannot get user properties from analytics worker thread";
        } else {
            zzawl();
            if (zzche.zzas()) {
                zzayt = zzawm().zzayr();
                str4 = "Cannot get user properties from main thread";
            } else {
                AtomicReference atomicReference = new AtomicReference();
                synchronized (atomicReference) {
                    this.zzitk.zzawl().zzg(new zzcip(this, atomicReference, str, str2, str3, z));
                    try {
                        atomicReference.wait(5000L);
                    } catch (InterruptedException e) {
                        zzawm().zzayt().zzj("Interrupted waiting for get user properties", e);
                    }
                }
                List<zzckk> list = (List) atomicReference.get();
                if (list == null) {
                    zzayt = zzawm().zzayt();
                    str4 = "Timed out waiting for get user properties";
                } else {
                    ArrayMap arrayMap = new ArrayMap(list.size());
                    for (zzckk zzckkVar : list) {
                        arrayMap.put(zzckkVar.name, zzckkVar.getValue());
                    }
                    return arrayMap;
                }
            }
        }
        zzayt.log(str4);
        return Collections.emptyMap();
    }

    @WorkerThread
    public final void zzb(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzut();
        zzwu();
        zzbq.checkNotNull(conditionalUserProperty);
        zzbq.zzgh(conditionalUserProperty.mName);
        zzbq.zzgh(conditionalUserProperty.mOrigin);
        zzbq.checkNotNull(conditionalUserProperty.mValue);
        if (!this.zzitk.isEnabled()) {
            zzawm().zzayw().log("Conditional property not sent since Firebase Analytics is disabled");
            return;
        }
        zzckk zzckkVar = new zzckk(conditionalUserProperty.mName, conditionalUserProperty.mTriggeredTimestamp, conditionalUserProperty.mValue, conditionalUserProperty.mOrigin);
        try {
            zzcfx zza = zzawi().zza(conditionalUserProperty.mTriggeredEventName, conditionalUserProperty.mTriggeredEventParams, conditionalUserProperty.mOrigin, 0L, true, false);
            zzawd().zzf(new zzcfi(conditionalUserProperty.mAppId, conditionalUserProperty.mOrigin, zzckkVar, conditionalUserProperty.mCreationTimestamp, false, conditionalUserProperty.mTriggerEventName, zzawi().zza(conditionalUserProperty.mTimedOutEventName, conditionalUserProperty.mTimedOutEventParams, conditionalUserProperty.mOrigin, 0L, true, false), conditionalUserProperty.mTriggerTimeout, zza, conditionalUserProperty.mTimeToLive, zzawi().zza(conditionalUserProperty.mExpiredEventName, conditionalUserProperty.mExpiredEventParams, conditionalUserProperty.mOrigin, 0L, true, false)));
        } catch (IllegalArgumentException unused) {
        }
    }

    @WorkerThread
    public final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        int i;
        zzbq.zzgh(str);
        zzbq.zzgh(str2);
        zzbq.checkNotNull(bundle);
        zzut();
        zzwu();
        if (!this.zzitk.isEnabled()) {
            zzawm().zzayw().log("Event not sent since app measurement is disabled");
            return;
        }
        if (!this.zzjef) {
            this.zzjef = true;
            try {
                try {
                    Class.forName("com.google.android.gms.tagmanager.TagManagerService").getDeclaredMethod("initialize", Context.class).invoke(null, getContext());
                } catch (Exception e) {
                    zzawm().zzayt().zzj("Failed to invoke Tag Manager's initialize() method", e);
                }
            } catch (ClassNotFoundException unused) {
                zzawm().zzayv().log("Tag Manager is not found and thus will not be used");
            }
        }
        boolean equals = "am".equals(str);
        boolean zzkc = zzckn.zzkc(str2);
        if (z && this.zzjed != null && !zzkc && !equals) {
            zzawm().zzayw().zze("Passing event to registered event handler (FE)", zzawh().zzjb(str2), zzawh().zzx(bundle));
            this.zzjed.interceptEvent(str, str2, bundle, j);
        } else if (this.zzitk.zzazj()) {
            int zzjv = zzawi().zzjv(str2);
            if (zzjv != 0) {
                zzawi();
                this.zzitk.zzawi().zza(str3, zzjv, "_ev", zzckn.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                return;
            }
            List<String> singletonList = Collections.singletonList("_o");
            Bundle zza = zzawi().zza(str2, bundle, singletonList, z3, true);
            ArrayList arrayList = new ArrayList();
            arrayList.add(zza);
            long nextLong = zzawi().zzban().nextLong();
            String[] strArr = (String[]) zza.keySet().toArray(new String[bundle.size()]);
            Arrays.sort(strArr);
            int length = strArr.length;
            int i2 = 0;
            int i3 = 0;
            while (i3 < length) {
                String str4 = strArr[i3];
                Object obj = zza.get(str4);
                zzawi();
                Bundle[] zzae = zzckn.zzae(obj);
                if (zzae != null) {
                    zza.putInt(str4, zzae.length);
                    strArr = strArr;
                    int i4 = 0;
                    while (i4 < zzae.length) {
                        Bundle zza2 = zzawi().zza("_ep", zzae[i4], singletonList, z3, false);
                        zza2.putString("_en", str2);
                        zza2.putLong("_eid", nextLong);
                        zza2.putString("_gn", str4);
                        zza2.putInt("_ll", zzae.length);
                        zza2.putInt("_i", i4);
                        arrayList.add(zza2);
                        i4++;
                        str4 = str4;
                        i2 = i2;
                        length = length;
                        equals = equals;
                        i3 = i3;
                    }
                    length = length;
                    equals = equals;
                    i = i3;
                    i2 += zzae.length;
                } else {
                    strArr = strArr;
                    length = length;
                    equals = equals;
                    i = i3;
                }
                i3 = i + 1;
            }
            if (i2 != 0) {
                zza.putLong("_eid", nextLong);
                zza.putInt("_epc", i2);
            }
            zzcjc zzbac = zzawe().zzbac();
            if (zzbac != null && !zza.containsKey("_sc")) {
                zzbac.zzjfg = true;
            }
            int i5 = 0;
            while (i5 < arrayList.size()) {
                Bundle bundle2 = (Bundle) arrayList.get(i5);
                String str5 = i5 != 0 ? "_ep" : str2;
                bundle2.putString("_o", str);
                if (!bundle2.containsKey("_sc")) {
                    zzciz.zza(zzbac, bundle2);
                }
                if (z2) {
                    bundle2 = zzawi().zzy(bundle2);
                }
                zzawm().zzayw().zze("Logging event (FE)", zzawh().zzjb(str2), zzawh().zzx(bundle2));
                zzawd().zzc(new zzcfx(str5, new zzcfu(bundle2), str, j), str3);
                if (!equals) {
                    for (AppMeasurement.OnEventListener onEventListener : this.zzjee) {
                        onEventListener.onEvent(str, str2, new Bundle(bundle2), j);
                    }
                }
                i5++;
                arrayList = arrayList;
            }
            if (zzawe().zzbac() != null && AppMeasurement.Event.APP_EXCEPTION.equals(str2)) {
                zzawk().zzbr(true);
            }
        }
    }

    @WorkerThread
    public final void zzbo(boolean z) {
        zzut();
        zzwu();
        zzawm().zzayw().zzj("Setting app measurement enabled (FE)", Boolean.valueOf(z));
        zzawn().setMeasurementEnabled(z);
        zzawd().zzbae();
    }

    @WorkerThread
    public final void zzc(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzut();
        zzwu();
        zzbq.checkNotNull(conditionalUserProperty);
        zzbq.zzgh(conditionalUserProperty.mName);
        if (!this.zzitk.isEnabled()) {
            zzawm().zzayw().log("Conditional property not cleared since Firebase Analytics is disabled");
            return;
        }
        try {
            zzawd().zzf(new zzcfi(conditionalUserProperty.mAppId, conditionalUserProperty.mOrigin, new zzckk(conditionalUserProperty.mName, 0L, null, null), conditionalUserProperty.mCreationTimestamp, conditionalUserProperty.mActive, conditionalUserProperty.mTriggerEventName, null, conditionalUserProperty.mTriggerTimeout, null, conditionalUserProperty.mTimeToLive, zzawi().zza(conditionalUserProperty.mExpiredEventName, conditionalUserProperty.mExpiredEventParams, conditionalUserProperty.mOrigin, conditionalUserProperty.mCreationTimestamp, true, false)));
        } catch (IllegalArgumentException unused) {
        }
    }

    private final List<AppMeasurement.ConditionalUserProperty> zzk(String str, String str2, String str3) {
        zzcgl zzayr;
        String str4;
        if (zzawl().zzazg()) {
            zzayr = zzawm().zzayr();
            str4 = "Cannot get conditional user properties from analytics worker thread";
        } else {
            zzawl();
            if (zzche.zzas()) {
                zzayr = zzawm().zzayr();
                str4 = "Cannot get conditional user properties from main thread";
            } else {
                AtomicReference atomicReference = new AtomicReference();
                synchronized (atomicReference) {
                    this.zzitk.zzawl().zzg(new zzcio(this, atomicReference, str, str2, str3));
                    try {
                        atomicReference.wait(5000L);
                    } catch (InterruptedException e) {
                        zzawm().zzayt().zze("Interrupted waiting for get conditional user properties", str, e);
                    }
                }
                List<zzcfi> list = (List) atomicReference.get();
                if (list == null) {
                    zzawm().zzayt().zzj("Timed out waiting for get conditional user properties", str);
                    return Collections.emptyList();
                }
                ArrayList arrayList = new ArrayList(list.size());
                for (zzcfi zzcfiVar : list) {
                    AppMeasurement.ConditionalUserProperty conditionalUserProperty = new AppMeasurement.ConditionalUserProperty();
                    conditionalUserProperty.mAppId = str;
                    conditionalUserProperty.mOrigin = str2;
                    conditionalUserProperty.mCreationTimestamp = zzcfiVar.zzivm;
                    conditionalUserProperty.mName = zzcfiVar.zzivl.name;
                    conditionalUserProperty.mValue = zzcfiVar.zzivl.getValue();
                    conditionalUserProperty.mActive = zzcfiVar.zzivn;
                    conditionalUserProperty.mTriggerEventName = zzcfiVar.zzivo;
                    if (zzcfiVar.zzivp != null) {
                        conditionalUserProperty.mTimedOutEventName = zzcfiVar.zzivp.name;
                        if (zzcfiVar.zzivp.zziwy != null) {
                            conditionalUserProperty.mTimedOutEventParams = zzcfiVar.zzivp.zziwy.zzayl();
                        }
                    }
                    conditionalUserProperty.mTriggerTimeout = zzcfiVar.zzivq;
                    if (zzcfiVar.zzivr != null) {
                        conditionalUserProperty.mTriggeredEventName = zzcfiVar.zzivr.name;
                        if (zzcfiVar.zzivr.zziwy != null) {
                            conditionalUserProperty.mTriggeredEventParams = zzcfiVar.zzivr.zziwy.zzayl();
                        }
                    }
                    conditionalUserProperty.mTriggeredTimestamp = zzcfiVar.zzivl.zzjgn;
                    conditionalUserProperty.mTimeToLive = zzcfiVar.zzivs;
                    if (zzcfiVar.zzivt != null) {
                        conditionalUserProperty.mExpiredEventName = zzcfiVar.zzivt.name;
                        if (zzcfiVar.zzivt.zziwy != null) {
                            conditionalUserProperty.mExpiredEventParams = zzcfiVar.zzivt.zziwy.zzayl();
                        }
                    }
                    arrayList.add(conditionalUserProperty);
                }
                return arrayList;
            }
        }
        zzayr.log(str4);
        return Collections.emptyList();
    }

    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        zza((String) null, str, str2, bundle);
    }

    public final void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        zzbq.zzgh(str);
        zzavw();
        zza(str, str2, str3, bundle);
    }

    public final Task<String> getAppInstanceId() {
        try {
            String zzazb = zzawn().zzazb();
            return zzazb != null ? Tasks.forResult(zzazb) : Tasks.call(zzawl().zzazh(), new zzciv(this));
        } catch (Exception e) {
            zzawm().zzayt().log("Failed to schedule task for getAppInstanceId");
            return Tasks.forException(e);
        }
    }

    public final List<AppMeasurement.ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        return zzk(null, str, str2);
    }

    public final List<AppMeasurement.ConditionalUserProperty> getConditionalUserPropertiesAs(String str, String str2, String str3) {
        zzbq.zzgh(str);
        zzavw();
        return zzk(str, str2, str3);
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        return zzb(null, str, str2, z);
    }

    public final Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        zzbq.zzgh(str);
        zzavw();
        return zzb(str, str2, str3, z);
    }

    public final void registerOnMeasurementEventListener(AppMeasurement.OnEventListener onEventListener) {
        zzwu();
        zzbq.checkNotNull(onEventListener);
        if (!this.zzjee.add(onEventListener)) {
            zzawm().zzayt().log("OnEventListener already registered");
        }
    }

    public final void resetAnalyticsData() {
        zzawl().zzg(new zzcix(this));
    }

    public final void setConditionalUserProperty(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzbq.checkNotNull(conditionalUserProperty);
        AppMeasurement.ConditionalUserProperty conditionalUserProperty2 = new AppMeasurement.ConditionalUserProperty(conditionalUserProperty);
        if (!TextUtils.isEmpty(conditionalUserProperty2.mAppId)) {
            zzawm().zzayt().log("Package name should be null when calling setConditionalUserProperty");
        }
        conditionalUserProperty2.mAppId = null;
        zza(conditionalUserProperty2);
    }

    public final void setConditionalUserPropertyAs(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzbq.checkNotNull(conditionalUserProperty);
        zzbq.zzgh(conditionalUserProperty.mAppId);
        zzavw();
        zza(new AppMeasurement.ConditionalUserProperty(conditionalUserProperty));
    }

    @WorkerThread
    public final void setEventInterceptor(AppMeasurement.EventInterceptor eventInterceptor) {
        zzut();
        zzwu();
        if (!(eventInterceptor == null || eventInterceptor == this.zzjed)) {
            zzbq.zza(this.zzjed == null, "EventInterceptor already set.");
        }
        this.zzjed = eventInterceptor;
    }

    public final void setMeasurementEnabled(boolean z) {
        zzwu();
        zzawl().zzg(new zzcil(this, z));
    }

    public final void setMinimumSessionDuration(long j) {
        zzawl().zzg(new zzciq(this, j));
    }

    public final void setSessionTimeoutDuration(long j) {
        zzawl().zzg(new zzcir(this, j));
    }

    public final void unregisterOnMeasurementEventListener(AppMeasurement.OnEventListener onEventListener) {
        zzwu();
        zzbq.checkNotNull(onEventListener);
        if (!this.zzjee.remove(onEventListener)) {
            zzawm().zzayt().log("OnEventListener had not been registered");
        }
    }

    public final void zza(String str, String str2, Bundle bundle, long j) {
        zza(str, str2, j, bundle, false, true, true, null);
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z) {
        zza(str, str2, bundle, true, this.zzjed == null || zzckn.zzkc(str2), true, null);
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
    public final String zzazb() {
        return this.zzjeg.get();
    }

    public final void zzb(String str, String str2, Object obj) {
        zzbq.zzgh(str);
        long currentTimeMillis = zzwh().currentTimeMillis();
        int zzjx = zzawi().zzjx(str2);
        int i = 0;
        if (zzjx != 0) {
            zzawi();
            String zza = zzckn.zza(str2, 24, true);
            if (str2 != null) {
                i = str2.length();
            }
            this.zzitk.zzawi().zza(zzjx, "_ev", zza, i);
        } else if (obj != null) {
            int zzl = zzawi().zzl(str2, obj);
            if (zzl != 0) {
                zzawi();
                String zza2 = zzckn.zza(str2, 24, true);
                if ((obj instanceof String) || (obj instanceof CharSequence)) {
                    i = String.valueOf(obj).length();
                }
                this.zzitk.zzawi().zza(zzl, "_ev", zza2, i);
                return;
            }
            Object zzm = zzawi().zzm(str2, obj);
            if (zzm != null) {
                zza(str, str2, currentTimeMillis, zzm);
            }
        } else {
            zza(str, str2, currentTimeMillis, (Object) null);
        }
    }

    @Nullable
    public final String zzbc(long j) {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzawl().zzg(new zzciw(this, atomicReference));
            try {
                atomicReference.wait(j);
            } catch (InterruptedException unused) {
                zzawm().zzayt().log("Interrupted waiting for app instance id");
                return null;
            }
        }
        return (String) atomicReference.get();
    }

    public final List<zzckk> zzbp(boolean z) {
        zzcgl zzayt;
        String str;
        zzwu();
        zzawm().zzayw().log("Fetching user attributes (FE)");
        if (zzawl().zzazg()) {
            zzayt = zzawm().zzayr();
            str = "Cannot get all user properties from analytics worker thread";
        } else {
            zzawl();
            if (zzche.zzas()) {
                zzayt = zzawm().zzayr();
                str = "Cannot get all user properties from main thread";
            } else {
                AtomicReference atomicReference = new AtomicReference();
                synchronized (atomicReference) {
                    this.zzitk.zzawl().zzg(new zzciu(this, atomicReference, z));
                    try {
                        atomicReference.wait(5000L);
                    } catch (InterruptedException e) {
                        zzawm().zzayt().zzj("Interrupted waiting for get user properties", e);
                    }
                }
                List<zzckk> list = (List) atomicReference.get();
                if (list != null) {
                    return list;
                }
                zzayt = zzawm().zzayt();
                str = "Timed out waiting for get user properties";
            }
        }
        zzayt.log(str);
        return Collections.emptyList();
    }

    public final void zzc(String str, String str2, Bundle bundle) {
        zza(str, str2, bundle, true, this.zzjed == null || zzckn.zzkc(str2), false, null);
    }

    public final void zzjj(@Nullable String str) {
        this.zzjeg.set(str);
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
