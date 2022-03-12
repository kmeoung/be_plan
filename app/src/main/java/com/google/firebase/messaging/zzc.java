package com.google.firebase.messaging;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.internal.zzfhj;
import com.google.android.gms.internal.zzfil;
import com.google.android.gms.internal.zzfim;
import com.google.android.gms.measurement.AppMeasurement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class zzc {
    private static Bundle zza(@NonNull zzfim zzfimVar) {
        return zzaz(zzfimVar.zzpld, zzfimVar.zzple);
    }

    @Nullable
    private static Object zza(@NonNull zzfim zzfimVar, @NonNull String str, @NonNull zzb zzbVar) {
        Exception e;
        Object obj;
        Class<?> cls;
        Bundle zza;
        String str2 = null;
        try {
            cls = Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
            zza = zza(zzfimVar);
            obj = cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e2) {
            e = e2;
            obj = null;
        }
        try {
            cls.getField("mOrigin").set(obj, str);
            cls.getField("mCreationTimestamp").set(obj, Long.valueOf(zzfimVar.zzplf));
            cls.getField("mName").set(obj, zzfimVar.zzpld);
            cls.getField("mValue").set(obj, zzfimVar.zzple);
            if (!TextUtils.isEmpty(zzfimVar.zzplg)) {
                str2 = zzfimVar.zzplg;
            }
            cls.getField("mTriggerEventName").set(obj, str2);
            cls.getField("mTimedOutEventName").set(obj, !TextUtils.isEmpty(zzfimVar.zzpll) ? zzfimVar.zzpll : zzbVar.zzbpd());
            cls.getField("mTimedOutEventParams").set(obj, zza);
            cls.getField("mTriggerTimeout").set(obj, Long.valueOf(zzfimVar.zzplh));
            cls.getField("mTriggeredEventName").set(obj, !TextUtils.isEmpty(zzfimVar.zzplj) ? zzfimVar.zzplj : zzbVar.zzbpc());
            cls.getField("mTriggeredEventParams").set(obj, zza);
            cls.getField("mTimeToLive").set(obj, Long.valueOf(zzfimVar.zzgew));
            cls.getField("mExpiredEventName").set(obj, !TextUtils.isEmpty(zzfimVar.zzplm) ? zzfimVar.zzplm : zzbVar.zzbpe());
            cls.getField("mExpiredEventParams").set(obj, zza);
            return obj;
        } catch (Exception e3) {
            e = e3;
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            return obj;
        }
    }

    private static String zza(@Nullable zzfim zzfimVar, @NonNull zzb zzbVar) {
        return (zzfimVar == null || TextUtils.isEmpty(zzfimVar.zzplk)) ? zzbVar.zzbpf() : zzfimVar.zzplk;
    }

    private static List<Object> zza(@NonNull AppMeasurement appMeasurement, @NonNull String str) {
        List<Object> list = new ArrayList<>();
        try {
            Method declaredMethod = AppMeasurement.class.getDeclaredMethod("getConditionalUserProperties", String.class, String.class);
            declaredMethod.setAccessible(true);
            list = (List) declaredMethod.invoke(appMeasurement, str, "");
        } catch (Exception e) {
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
        }
        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
            int size = list.size();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 55);
            sb.append("Number of currently set _Es for origin: ");
            sb.append(str);
            sb.append(" is ");
            sb.append(size);
            Log.v("FirebaseAbtUtil", sb.toString());
        }
        return list;
    }

    private static void zza(@NonNull Context context, @NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4) {
        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
            String valueOf = String.valueOf(str);
            Log.v("FirebaseAbtUtil", valueOf.length() != 0 ? "_CE(experimentId) called by ".concat(valueOf) : new String("_CE(experimentId) called by "));
        }
        if (zzeq(context)) {
            AppMeasurement zzcz = zzcz(context);
            try {
                Method declaredMethod = AppMeasurement.class.getDeclaredMethod("clearConditionalUserProperty", String.class, String.class, Bundle.class);
                declaredMethod.setAccessible(true);
                if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                    StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 17 + String.valueOf(str3).length());
                    sb.append("Clearing _E: [");
                    sb.append(str2);
                    sb.append(", ");
                    sb.append(str3);
                    sb.append("]");
                    Log.v("FirebaseAbtUtil", sb.toString());
                }
                declaredMethod.invoke(zzcz, str2, str4, zzaz(str2, str3));
            } catch (Exception e) {
                Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            }
        }
    }

    public static void zza(@NonNull Context context, @NonNull String str, @NonNull byte[] bArr, @NonNull zzb zzbVar, int i) {
        boolean z;
        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
            String valueOf = String.valueOf(str);
            Log.v("FirebaseAbtUtil", valueOf.length() != 0 ? "_SE called by ".concat(valueOf) : new String("_SE called by "));
        }
        if (zzeq(context)) {
            AppMeasurement zzcz = zzcz(context);
            zzfim zzal = zzal(bArr);
            if (zzal != null) {
                try {
                    Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
                    boolean z2 = false;
                    for (Object obj : zza(zzcz, str)) {
                        String zzaz = zzaz(obj);
                        String zzba = zzba(obj);
                        long longValue = ((Long) Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty").getField("mCreationTimestamp").get(obj)).longValue();
                        if (!zzal.zzpld.equals(zzaz) || !zzal.zzple.equals(zzba)) {
                            zzfil[] zzfilVarArr = zzal.zzplo;
                            int length = zzfilVarArr.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length) {
                                    z = false;
                                    break;
                                } else if (zzfilVarArr[i2].zzpld.equals(zzaz)) {
                                    if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                                        StringBuilder sb = new StringBuilder(String.valueOf(zzaz).length() + 33 + String.valueOf(zzba).length());
                                        sb.append("_E is found in the _OE list. [");
                                        sb.append(zzaz);
                                        sb.append(", ");
                                        sb.append(zzba);
                                        sb.append("]");
                                        Log.v("FirebaseAbtUtil", sb.toString());
                                    }
                                    z = true;
                                } else {
                                    i2++;
                                }
                            }
                            if (!z) {
                                if (zzal.zzplf > longValue) {
                                    if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                                        StringBuilder sb2 = new StringBuilder(String.valueOf(zzaz).length() + 115 + String.valueOf(zzba).length());
                                        sb2.append("Clearing _E as it was not in the _OE list, andits start time is older than the start time of the _E to be set. [");
                                        sb2.append(zzaz);
                                        sb2.append(", ");
                                        sb2.append(zzba);
                                        sb2.append("]");
                                        Log.v("FirebaseAbtUtil", sb2.toString());
                                    }
                                    zza(context, str, zzaz, zzba, zza(zzal, zzbVar));
                                } else if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                                    StringBuilder sb3 = new StringBuilder(String.valueOf(zzaz).length() + 109 + String.valueOf(zzba).length());
                                    sb3.append("_E was not found in the _OE list, but not clearing it as it has a new start time than the _E to be set.  [");
                                    sb3.append(zzaz);
                                    sb3.append(", ");
                                    sb3.append(zzba);
                                    sb3.append("]");
                                    Log.v("FirebaseAbtUtil", sb3.toString());
                                }
                            }
                        } else {
                            if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                                StringBuilder sb4 = new StringBuilder(String.valueOf(zzaz).length() + 23 + String.valueOf(zzba).length());
                                sb4.append("_E is already set. [");
                                sb4.append(zzaz);
                                sb4.append(", ");
                                sb4.append(zzba);
                                sb4.append("]");
                                Log.v("FirebaseAbtUtil", sb4.toString());
                            }
                            z2 = true;
                        }
                    }
                    if (!z2) {
                        zza(zzcz, context, str, zzal, zzbVar, 1);
                    } else if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                        String str2 = zzal.zzpld;
                        String str3 = zzal.zzple;
                        StringBuilder sb5 = new StringBuilder(String.valueOf(str2).length() + 44 + String.valueOf(str3).length());
                        sb5.append("_E is already set. Not setting it again [");
                        sb5.append(str2);
                        sb5.append(", ");
                        sb5.append(str3);
                        sb5.append("]");
                        Log.v("FirebaseAbtUtil", sb5.toString());
                    }
                } catch (Exception e) {
                    Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
                }
            } else if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                Log.v("FirebaseAbtUtil", "_SE failed; either _P was not set, or we couldn't deserialize the _P.");
            }
        }
    }

    private static void zza(@NonNull AppMeasurement appMeasurement, @NonNull Context context, @NonNull String str, @NonNull zzfim zzfimVar, @NonNull zzb zzbVar, int i) {
        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
            String str2 = zzfimVar.zzpld;
            String str3 = zzfimVar.zzple;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 7 + String.valueOf(str3).length());
            sb.append("_SEI: ");
            sb.append(str2);
            sb.append(" ");
            sb.append(str3);
            Log.v("FirebaseAbtUtil", sb.toString());
        }
        try {
            Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
            List<Object> zza = zza(appMeasurement, str);
            if (zza(appMeasurement, str).size() >= zzb(appMeasurement, str)) {
                if ((zzfimVar.zzpln != 0 ? zzfimVar.zzpln : 1) == 1) {
                    Object obj = zza.get(0);
                    String zzaz = zzaz(obj);
                    String zzba = zzba(obj);
                    if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                        StringBuilder sb2 = new StringBuilder(String.valueOf(zzaz).length() + 38);
                        sb2.append("Clearing _E due to overflow policy: [");
                        sb2.append(zzaz);
                        sb2.append("]");
                        Log.v("FirebaseAbtUtil", sb2.toString());
                    }
                    zza(context, str, zzaz, zzba, zza(zzfimVar, zzbVar));
                } else if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                    String str4 = zzfimVar.zzpld;
                    String str5 = zzfimVar.zzple;
                    StringBuilder sb3 = new StringBuilder(String.valueOf(str4).length() + 44 + String.valueOf(str5).length());
                    sb3.append("_E won't be set due to overflow policy. [");
                    sb3.append(str4);
                    sb3.append(", ");
                    sb3.append(str5);
                    sb3.append("]");
                    Log.v("FirebaseAbtUtil", sb3.toString());
                    return;
                } else {
                    return;
                }
            }
            for (Object obj2 : zza) {
                String zzaz2 = zzaz(obj2);
                String zzba2 = zzba(obj2);
                if (zzaz2.equals(zzfimVar.zzpld) && !zzba2.equals(zzfimVar.zzple) && Log.isLoggable("FirebaseAbtUtil", 2)) {
                    StringBuilder sb4 = new StringBuilder(String.valueOf(zzaz2).length() + 77 + String.valueOf(zzba2).length());
                    sb4.append("Clearing _E, as only one _V of the same _E can be set atany given time: [");
                    sb4.append(zzaz2);
                    sb4.append(", ");
                    sb4.append(zzba2);
                    sb4.append("].");
                    Log.v("FirebaseAbtUtil", sb4.toString());
                    zza(context, str, zzaz2, zzba2, zza(zzfimVar, zzbVar));
                }
            }
            Object zza2 = zza(zzfimVar, str, zzbVar);
            if (zza2 != null) {
                if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                    String str6 = zzfimVar.zzpld;
                    String str7 = zzfimVar.zzple;
                    String str8 = zzfimVar.zzplg;
                    StringBuilder sb5 = new StringBuilder(String.valueOf(str6).length() + 27 + String.valueOf(str7).length() + String.valueOf(str8).length());
                    sb5.append("Setting _CUP for _E: [");
                    sb5.append(str6);
                    sb5.append(", ");
                    sb5.append(str7);
                    sb5.append(", ");
                    sb5.append(str8);
                    sb5.append("]");
                    Log.v("FirebaseAbtUtil", sb5.toString());
                }
                try {
                    Method declaredMethod = AppMeasurement.class.getDeclaredMethod("setConditionalUserProperty", Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty"));
                    declaredMethod.setAccessible(true);
                    appMeasurement.logEventInternal(str, !TextUtils.isEmpty(zzfimVar.zzpli) ? zzfimVar.zzpli : zzbVar.zzbpb(), zza(zzfimVar));
                    declaredMethod.invoke(appMeasurement, zza2);
                } catch (Exception e) {
                    Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
                }
            } else if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                String str9 = zzfimVar.zzpld;
                String str10 = zzfimVar.zzple;
                StringBuilder sb6 = new StringBuilder(String.valueOf(str9).length() + 42 + String.valueOf(str10).length());
                sb6.append("Could not create _CUP for: [");
                sb6.append(str9);
                sb6.append(", ");
                sb6.append(str10);
                sb6.append("]. Skipping.");
                Log.v("FirebaseAbtUtil", sb6.toString());
            }
        } catch (Exception e2) {
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e2);
        }
    }

    @Nullable
    private static zzfim zzal(@NonNull byte[] bArr) {
        try {
            return zzfim.zzbh(bArr);
        } catch (zzfhj unused) {
            return null;
        }
    }

    private static Bundle zzaz(@NonNull String str, @NonNull String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(str, str2);
        return bundle;
    }

    private static String zzaz(@NonNull Object obj) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return (String) Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty").getField("mName").get(obj);
    }

    private static int zzb(@NonNull AppMeasurement appMeasurement, @NonNull String str) {
        try {
            Method declaredMethod = AppMeasurement.class.getDeclaredMethod("getMaxUserProperties", String.class);
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke(appMeasurement, str)).intValue();
        } catch (Exception e) {
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            return 20;
        }
    }

    private static String zzba(@NonNull Object obj) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return (String) Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty").getField("mValue").get(obj);
    }

    @Nullable
    private static AppMeasurement zzcz(Context context) {
        try {
            return AppMeasurement.getInstance(context);
        } catch (NoClassDefFoundError unused) {
            return null;
        }
    }

    private static boolean zzeq(Context context) {
        if (zzcz(context) == null) {
            if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                Log.v("FirebaseAbtUtil", "Firebase Analytics not available");
            }
            return false;
        }
        try {
            Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
            return true;
        } catch (ClassNotFoundException unused) {
            if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                Log.v("FirebaseAbtUtil", "Firebase Analytics library is missing support for abt. Please update to a more recent version.");
            }
            return false;
        }
    }
}
