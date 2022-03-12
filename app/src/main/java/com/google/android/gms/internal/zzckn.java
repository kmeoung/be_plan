package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes.dex */
public final class zzckn extends zzcii {
    private static String[] zzjgs = {"firebase_"};
    private SecureRandom zzjgt;
    private final AtomicLong zzjgu = new AtomicLong(0);
    private int zzjgv;

    public zzckn(zzchj zzchjVar) {
        super(zzchjVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0032 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int zza(java.lang.String r9, java.lang.Object r10, boolean r11) {
        /*
            r8 = this;
            r0 = 0
            if (r11 == 0) goto L_0x0035
            java.lang.String r1 = "param"
            boolean r2 = r10 instanceof android.os.Parcelable[]
            r3 = 1
            if (r2 == 0) goto L_0x000f
            r2 = r10
            android.os.Parcelable[] r2 = (android.os.Parcelable[]) r2
            int r2 = r2.length
            goto L_0x001a
        L_0x000f:
            boolean r2 = r10 instanceof java.util.ArrayList
            if (r2 == 0) goto L_0x0030
            r2 = r10
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            int r2 = r2.size()
        L_0x001a:
            r4 = 1000(0x3e8, float:1.401E-42)
            if (r2 <= r4) goto L_0x0030
            com.google.android.gms.internal.zzcgj r3 = r8.zzawm()
            com.google.android.gms.internal.zzcgl r3 = r3.zzayt()
            java.lang.String r4 = "Parameter array is too long; discarded. Value kind, name, array length"
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3.zzd(r4, r1, r9, r2)
            r3 = 0
        L_0x0030:
            if (r3 != 0) goto L_0x0035
            r9 = 17
            return r9
        L_0x0035:
            boolean r1 = zzkc(r9)
            if (r1 == 0) goto L_0x0048
            java.lang.String r3 = "param"
            r5 = 256(0x100, float:3.59E-43)
            r2 = r8
            r4 = r9
            r6 = r10
            r7 = r11
            boolean r9 = r2.zza(r3, r4, r5, r6, r7)
            goto L_0x0054
        L_0x0048:
            java.lang.String r2 = "param"
            r4 = 100
            r1 = r8
            r3 = r9
            r5 = r10
            r6 = r11
            boolean r9 = r1.zza(r2, r3, r4, r5, r6)
        L_0x0054:
            if (r9 == 0) goto L_0x0057
            return r0
        L_0x0057:
            r9 = 4
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzckn.zza(java.lang.String, java.lang.Object, boolean):int");
    }

    private static Object zza(int i, Object obj, boolean z) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            return obj;
        }
        if (obj instanceof Integer) {
            return Long.valueOf(((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return Long.valueOf(((Byte) obj).byteValue());
        }
        if (obj instanceof Short) {
            return Long.valueOf(((Short) obj).shortValue());
        }
        if (obj instanceof Boolean) {
            return Long.valueOf(((Boolean) obj).booleanValue() ? 1L : 0L);
        } else if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).doubleValue());
        } else {
            if ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) {
                return zza(String.valueOf(obj), i, z);
            }
            return null;
        }
    }

    public static Object zza(zzcky zzckyVar, String str) {
        zzckz[] zzckzVarArr;
        for (zzckz zzckzVar : zzckyVar.zzjim) {
            if (zzckzVar.name.equals(str)) {
                if (zzckzVar.zzfzi != null) {
                    return zzckzVar.zzfzi;
                }
                if (zzckzVar.zzjiq != null) {
                    return zzckzVar.zzjiq;
                }
                if (zzckzVar.zzjgq != null) {
                    return zzckzVar.zzjgq;
                }
            }
        }
        return null;
    }

    public static String zza(String str, int i, boolean z) {
        if (str.codePointCount(0, str.length()) <= i) {
            return str;
        }
        if (z) {
            return String.valueOf(str.substring(0, str.offsetByCodePoints(0, i))).concat("...");
        }
        return null;
    }

    @Nullable
    public static String zza(String str, String[] strArr, String[] strArr2) {
        zzbq.checkNotNull(strArr);
        zzbq.checkNotNull(strArr2);
        int min = Math.min(strArr.length, strArr2.length);
        for (int i = 0; i < min; i++) {
            if (zzas(str, strArr[i])) {
                return strArr2[i];
            }
        }
        return null;
    }

    private final boolean zza(String str, String str2, int i, Object obj, boolean z) {
        Parcelable[] parcelableArr;
        if (obj == null || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Integer) || (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Boolean) || (obj instanceof Double)) {
            return true;
        }
        if ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) {
            String valueOf = String.valueOf(obj);
            if (valueOf.codePointCount(0, valueOf.length()) > i) {
                zzawm().zzayt().zzd("Value is too long; discarded. Value kind, name, value length", str, str2, Integer.valueOf(valueOf.length()));
                return false;
            }
            return true;
        } else if ((obj instanceof Bundle) && z) {
            return true;
        } else {
            if ((obj instanceof Parcelable[]) && z) {
                for (Parcelable parcelable : (Parcelable[]) obj) {
                    if (!(parcelable instanceof Bundle)) {
                        zzawm().zzayt().zze("All Parcelable[] elements must be of type Bundle. Value type, name", parcelable.getClass(), str2);
                        return false;
                    }
                }
                return true;
            } else if (!(obj instanceof ArrayList) || !z) {
                return false;
            } else {
                ArrayList arrayList = (ArrayList) obj;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    if (!(obj2 instanceof Bundle)) {
                        zzawm().zzayt().zze("All ArrayList elements must be of type Bundle. Value type, name", obj2.getClass(), str2);
                        return false;
                    }
                }
                return true;
            }
        }
    }

    private final boolean zza(String str, String[] strArr, String str2) {
        boolean z;
        boolean z2;
        if (str2 == null) {
            zzawm().zzayr().zzj("Name is required and can't be null. Type", str);
            return false;
        }
        zzbq.checkNotNull(str2);
        int i = 0;
        while (true) {
            if (i >= zzjgs.length) {
                z = false;
                break;
            } else if (str2.startsWith(zzjgs[i])) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            zzawm().zzayr().zze("Name starts with reserved prefix. Type, name", str, str2);
            return false;
        }
        if (strArr != null) {
            zzbq.checkNotNull(strArr);
            int i2 = 0;
            while (true) {
                if (i2 >= strArr.length) {
                    z2 = false;
                    break;
                } else if (zzas(str2, strArr[i2])) {
                    z2 = true;
                    break;
                } else {
                    i2++;
                }
            }
            if (z2) {
                zzawm().zzayr().zze("Name is reserved. Type, name", str, str2);
                return false;
            }
        }
        return true;
    }

    public static boolean zza(long[] jArr, int i) {
        return i < (jArr.length << 6) && (jArr[i / 64] & (1 << (i % 64))) != 0;
    }

    public static byte[] zza(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        try {
            parcelable.writeToParcel(obtain, 0);
            return obtain.marshall();
        } finally {
            obtain.recycle();
        }
    }

    public static long[] zza(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = 0;
            for (int i2 = 0; i2 < 64; i2++) {
                int i3 = (i << 6) + i2;
                if (i3 < bitSet.length()) {
                    if (bitSet.get(i3)) {
                        jArr[i] = jArr[i] | (1 << i2);
                    }
                }
            }
        }
        return jArr;
    }

    public static zzckz[] zza(zzckz[] zzckzVarArr, String str, Object obj) {
        for (zzckz zzckzVar : zzckzVarArr) {
            if (Objects.equals(zzckzVar.name, str)) {
                zzckzVar.zzjiq = null;
                zzckzVar.zzfzi = null;
                zzckzVar.zzjgq = null;
                if (obj instanceof Long) {
                    zzckzVar.zzjiq = (Long) obj;
                    return zzckzVarArr;
                } else if (obj instanceof String) {
                    zzckzVar.zzfzi = (String) obj;
                    return zzckzVarArr;
                } else {
                    if (obj instanceof Double) {
                        zzckzVar.zzjgq = (Double) obj;
                    }
                    return zzckzVarArr;
                }
            }
        }
        zzckz[] zzckzVarArr2 = new zzckz[zzckzVarArr.length + 1];
        System.arraycopy(zzckzVarArr, 0, zzckzVarArr2, 0, zzckzVarArr.length);
        zzckz zzckzVar2 = new zzckz();
        zzckzVar2.name = str;
        if (obj instanceof Long) {
            zzckzVar2.zzjiq = (Long) obj;
        } else if (obj instanceof String) {
            zzckzVar2.zzfzi = (String) obj;
        } else if (obj instanceof Double) {
            zzckzVar2.zzjgq = (Double) obj;
        }
        zzckzVarArr2[zzckzVarArr.length] = zzckzVar2;
        return zzckzVarArr2;
    }

    public static Bundle[] zzae(Object obj) {
        Object[] array;
        if (obj instanceof Bundle) {
            return new Bundle[]{(Bundle) obj};
        }
        if (obj instanceof Parcelable[]) {
            Parcelable[] parcelableArr = (Parcelable[]) obj;
            array = Arrays.copyOf(parcelableArr, parcelableArr.length, Bundle[].class);
        } else if (!(obj instanceof ArrayList)) {
            return null;
        } else {
            ArrayList arrayList = (ArrayList) obj;
            array = arrayList.toArray(new Bundle[arrayList.size()]);
        }
        return (Bundle[]) array;
    }

    public static Object zzaf(Object obj) {
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;
        Throwable th;
        try {
            if (obj == null) {
                return null;
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                try {
                    objectOutputStream.writeObject(obj);
                    objectOutputStream.flush();
                    objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                    try {
                        Object readObject = objectInputStream.readObject();
                        objectOutputStream.close();
                        objectInputStream.close();
                        return readObject;
                    } catch (Throwable th2) {
                        th = th2;
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    objectInputStream = null;
                }
            } catch (Throwable th4) {
                th = th4;
                objectInputStream = null;
                objectOutputStream = null;
            }
        } catch (IOException | ClassNotFoundException unused) {
            return null;
        }
    }

    private final boolean zzag(Context context, String str) {
        zzcgl zzcglVar;
        Object e;
        String str2;
        X500Principal x500Principal = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            PackageInfo packageInfo = zzbgc.zzcy(context).getPackageInfo(str, 64);
            if (packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
                return true;
            }
            return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(packageInfo.signatures[0].toByteArray()))).getSubjectX500Principal().equals(x500Principal);
        } catch (PackageManager.NameNotFoundException e2) {
            e = e2;
            zzcglVar = zzawm().zzayr();
            str2 = "Package name not found";
            zzcglVar.zzj(str2, e);
            return true;
        } catch (CertificateException e3) {
            e = e3;
            zzcglVar = zzawm().zzayr();
            str2 = "Error obtaining certificate";
            zzcglVar.zzj(str2, e);
            return true;
        }
    }

    private final boolean zzaq(String str, String str2) {
        if (str2 == null) {
            zzawm().zzayr().zzj("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.length() == 0) {
            zzawm().zzayr().zzj("Name is required and can't be empty. Type", str);
            return false;
        } else {
            int codePointAt = str2.codePointAt(0);
            if (!Character.isLetter(codePointAt)) {
                zzawm().zzayr().zze("Name must start with a letter. Type, name", str, str2);
                return false;
            }
            int length = str2.length();
            int charCount = Character.charCount(codePointAt);
            while (charCount < length) {
                int codePointAt2 = str2.codePointAt(charCount);
                if (codePointAt2 == 95 || Character.isLetterOrDigit(codePointAt2)) {
                    charCount += Character.charCount(codePointAt2);
                } else {
                    zzawm().zzayr().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                    return false;
                }
            }
            return true;
        }
    }

    private final boolean zzar(String str, String str2) {
        if (str2 == null) {
            zzawm().zzayr().zzj("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.length() == 0) {
            zzawm().zzayr().zzj("Name is required and can't be empty. Type", str);
            return false;
        } else {
            int codePointAt = str2.codePointAt(0);
            if (Character.isLetter(codePointAt) || codePointAt == 95) {
                int length = str2.length();
                int charCount = Character.charCount(codePointAt);
                while (charCount < length) {
                    int codePointAt2 = str2.codePointAt(charCount);
                    if (codePointAt2 == 95 || Character.isLetterOrDigit(codePointAt2)) {
                        charCount += Character.charCount(codePointAt2);
                    } else {
                        zzawm().zzayr().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                        return false;
                    }
                }
                return true;
            }
            zzawm().zzayr().zze("Name must start with a letter or _ (underscore). Type, name", str, str2);
            return false;
        }
    }

    public static boolean zzas(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null) {
            return false;
        }
        return str.equals(str2);
    }

    private static void zzb(Bundle bundle, Object obj) {
        zzbq.checkNotNull(bundle);
        if (obj == null) {
            return;
        }
        if ((obj instanceof String) || (obj instanceof CharSequence)) {
            bundle.putLong("_el", String.valueOf(obj).length());
        }
    }

    private final boolean zzb(String str, int i, String str2) {
        if (str2 == null) {
            zzawm().zzayr().zzj("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.codePointCount(0, str2.length()) <= i) {
            return true;
        } else {
            zzawm().zzayr().zzd("Name is too long. Type, maximum supported length, name", str, Integer.valueOf(i), str2);
            return false;
        }
    }

    private static boolean zzd(Bundle bundle, int i) {
        if (bundle.getLong("_err") != 0) {
            return false;
        }
        bundle.putLong("_err", i);
        return true;
    }

    @WorkerThread
    public static boolean zzd(zzcfx zzcfxVar, zzcff zzcffVar) {
        zzbq.checkNotNull(zzcfxVar);
        zzbq.checkNotNull(zzcffVar);
        return !TextUtils.isEmpty(zzcffVar.zziux);
    }

    public static MessageDigest zzed(String str) {
        MessageDigest instance;
        for (int i = 0; i < 2; i++) {
            try {
                instance = MessageDigest.getInstance(str);
            } catch (NoSuchAlgorithmException unused) {
            }
            if (instance != null) {
                return instance;
            }
        }
        return null;
    }

    public static boolean zzjt(String str) {
        zzbq.zzgh(str);
        return str.charAt(0) != '_' || str.equals("_ep");
    }

    private final int zzjy(String str) {
        if (!zzaq("event param", str)) {
            return 3;
        }
        if (!zza("event param", (String[]) null, str)) {
            return 14;
        }
        return !zzb("event param", 40, str) ? 3 : 0;
    }

    private final int zzjz(String str) {
        if (!zzar("event param", str)) {
            return 3;
        }
        if (!zza("event param", (String[]) null, str)) {
            return 14;
        }
        return !zzb("event param", 40, str) ? 3 : 0;
    }

    private static int zzkb(String str) {
        return "_ldl".equals(str) ? 2048 : 36;
    }

    public static boolean zzkc(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
    }

    public static boolean zzke(String str) {
        return str != null && str.matches("(\\+|-)?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && str.length() <= 310;
    }

    @WorkerThread
    public static boolean zzkh(String str) {
        char c;
        zzbq.zzgh(str);
        int hashCode = str.hashCode();
        if (hashCode == 94660) {
            if (str.equals("_in")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 95025) {
            if (hashCode == 95027 && str.equals("_ui")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("_ug")) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    public static boolean zzo(Intent intent) {
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return "android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra) || "https://www.google.com".equals(stringExtra) || "android-app://com.google.appcrawler".equals(stringExtra);
    }

    public static long zzr(byte[] bArr) {
        zzbq.checkNotNull(bArr);
        int i = 0;
        zzbq.checkState(bArr.length > 0);
        long j = 0;
        for (int length = bArr.length - 1; length >= 0 && length >= bArr.length - 8; length--) {
            j += (bArr[length] & 255) << i;
            i += 8;
        }
        return j;
    }

    public static boolean zzt(Context context, String str) {
        ServiceInfo serviceInfo;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (!(packageManager == null || (serviceInfo = packageManager.getServiceInfo(new ComponentName(context, str), 4)) == null)) {
                if (serviceInfo.enabled) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final Bundle zza(String str, Bundle bundle, @Nullable List<String> list, boolean z, boolean z2) {
        int i;
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = new Bundle(bundle);
        int i2 = 0;
        for (String str2 : bundle.keySet()) {
            if (list == null || !list.contains(str2)) {
                i = z ? zzjy(str2) : 0;
                if (i == 0) {
                    i = zzjz(str2);
                }
            } else {
                i = 0;
            }
            if (i == 0) {
                int zza = zza(str2, bundle.get(str2), z2);
                if (zza == 0 || "_ev".equals(str2)) {
                    if (zzjt(str2) && (i2 = i2 + 1) > 25) {
                        StringBuilder sb = new StringBuilder(48);
                        sb.append("Event can't contain more then 25 params");
                        zzawm().zzayr().zze(sb.toString(), zzawh().zzjb(str), zzawh().zzx(bundle));
                        zzd(bundle2, 5);
                    }
                } else if (zzd(bundle2, zza)) {
                    bundle2.putString("_ev", zza(str2, 40, true));
                    zzb(bundle2, bundle.get(str2));
                }
            } else if (zzd(bundle2, i)) {
                bundle2.putString("_ev", zza(str2, 40, true));
                if (i == 3) {
                    zzb(bundle2, str2);
                }
            }
            bundle2.remove(str2);
        }
        return bundle2;
    }

    public final zzcfx zza(String str, Bundle bundle, String str2, long j, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (zzjv(str) != 0) {
            zzawm().zzayr().zzj("Invalid conditional property event name", zzawh().zzjd(str));
            throw new IllegalArgumentException();
        }
        Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle2.putString("_o", str2);
        return new zzcfx(str, new zzcfu(zzy(zza(str, bundle2, Collections.singletonList("_o"), false, false))), str2, j);
    }

    public final void zza(int i, String str, String str2, int i2) {
        zza((String) null, i, str, str2, i2);
    }

    public final void zza(Bundle bundle, String str, Object obj) {
        if (bundle != null) {
            if (obj instanceof Long) {
                bundle.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof String) {
                bundle.putString(str, String.valueOf(obj));
            } else if (obj instanceof Double) {
                bundle.putDouble(str, ((Double) obj).doubleValue());
            } else if (str != null) {
                zzawm().zzayu().zze("Not putting event parameter. Invalid value type. name, type", zzawh().zzjc(str), obj != null ? obj.getClass().getSimpleName() : null);
            }
        }
    }

    public final void zza(zzckz zzckzVar, Object obj) {
        zzbq.checkNotNull(obj);
        zzckzVar.zzfzi = null;
        zzckzVar.zzjiq = null;
        zzckzVar.zzjgq = null;
        if (obj instanceof String) {
            zzckzVar.zzfzi = (String) obj;
        } else if (obj instanceof Long) {
            zzckzVar.zzjiq = (Long) obj;
        } else if (obj instanceof Double) {
            zzckzVar.zzjgq = (Double) obj;
        } else {
            zzawm().zzayr().zzj("Ignoring invalid (type) event param value", obj);
        }
    }

    public final void zza(zzcld zzcldVar, Object obj) {
        zzbq.checkNotNull(obj);
        zzcldVar.zzfzi = null;
        zzcldVar.zzjiq = null;
        zzcldVar.zzjgq = null;
        if (obj instanceof String) {
            zzcldVar.zzfzi = (String) obj;
        } else if (obj instanceof Long) {
            zzcldVar.zzjiq = (Long) obj;
        } else if (obj instanceof Double) {
            zzcldVar.zzjgq = (Double) obj;
        } else {
            zzawm().zzayr().zzj("Ignoring invalid (type) user attribute value", obj);
        }
    }

    public final void zza(String str, int i, String str2, String str3, int i2) {
        Bundle bundle = new Bundle();
        zzd(bundle, i);
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString(str2, str3);
        }
        if (i == 6 || i == 7 || i == 2) {
            bundle.putLong("_el", i2);
        }
        this.zzitk.zzawa().zzc("auto", "_err", bundle);
    }

    @WorkerThread
    public final long zzaf(Context context, String str) {
        zzut();
        zzbq.checkNotNull(context);
        zzbq.zzgh(str);
        PackageManager packageManager = context.getPackageManager();
        MessageDigest zzed = zzed(CommonUtils.MD5_INSTANCE);
        if (zzed == null) {
            zzawm().zzayr().log("Could not get MD5 instance");
            return -1L;
        }
        if (packageManager != null) {
            try {
                if (!zzag(context, str)) {
                    PackageInfo packageInfo = zzbgc.zzcy(context).getPackageInfo(getContext().getPackageName(), 64);
                    if (packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                        return zzr(zzed.digest(packageInfo.signatures[0].toByteArray()));
                    }
                    zzawm().zzayt().log("Could not get signatures");
                    return -1L;
                }
            } catch (PackageManager.NameNotFoundException e) {
                zzawm().zzayr().zzj("Package name not found", e);
            }
        }
        return 0L;
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

    @Override // com.google.android.gms.internal.zzcii
    @WorkerThread
    protected final void zzaym() {
        zzut();
        SecureRandom secureRandom = new SecureRandom();
        long nextLong = secureRandom.nextLong();
        if (nextLong == 0) {
            nextLong = secureRandom.nextLong();
            if (nextLong == 0) {
                zzawm().zzayt().log("Utils falling back to Random for random id");
            }
        }
        this.zzjgu.set(nextLong);
    }

    public final <T extends Parcelable> T zzb(byte[] bArr, Parcelable.Creator<T> creator) {
        if (bArr == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        try {
            obtain.unmarshall(bArr, 0, bArr.length);
            obtain.setDataPosition(0);
            return creator.createFromParcel(obtain);
        } catch (zzbel unused) {
            zzawm().zzayr().log("Failed to load parcelable from buffer");
            return null;
        } finally {
            obtain.recycle();
        }
    }

    public final byte[] zzb(zzcla zzclaVar) {
        try {
            byte[] bArr = new byte[zzclaVar.zzhl()];
            zzfhc zzo = zzfhc.zzo(bArr, 0, bArr.length);
            zzclaVar.zza(zzo);
            zzo.zzcus();
            return bArr;
        } catch (IOException e) {
            zzawm().zzayr().zzj("Data loss. Failed to serialize batch", e);
            return null;
        }
    }

    public final long zzbam() {
        long andIncrement;
        long j;
        if (this.zzjgu.get() == 0) {
            synchronized (this.zzjgu) {
                long nextLong = new Random(System.nanoTime() ^ zzwh().currentTimeMillis()).nextLong();
                int i = this.zzjgv + 1;
                this.zzjgv = i;
                j = nextLong + i;
            }
            return j;
        }
        synchronized (this.zzjgu) {
            this.zzjgu.compareAndSet(-1L, 1L);
            andIncrement = this.zzjgu.getAndIncrement();
        }
        return andIncrement;
    }

    @WorkerThread
    public final SecureRandom zzban() {
        zzut();
        if (this.zzjgt == null) {
            this.zzjgt = new SecureRandom();
        }
        return this.zzjgt;
    }

    @WorkerThread
    public final boolean zzdu(String str) {
        zzut();
        if (zzbgc.zzcy(getContext()).checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        zzawm().zzayw().zzj("Permission not granted", str);
        return false;
    }

    public final boolean zzf(long j, long j2) {
        return j == 0 || j2 <= 0 || Math.abs(zzwh().currentTimeMillis() - j) > j2;
    }

    public final int zzju(String str) {
        if (!zzaq(NotificationCompat.CATEGORY_EVENT, str)) {
            return 2;
        }
        if (!zza(NotificationCompat.CATEGORY_EVENT, AppMeasurement.Event.zzitl, str)) {
            return 13;
        }
        return !zzb(NotificationCompat.CATEGORY_EVENT, 40, str) ? 2 : 0;
    }

    public final int zzjv(String str) {
        if (!zzar(NotificationCompat.CATEGORY_EVENT, str)) {
            return 2;
        }
        if (!zza(NotificationCompat.CATEGORY_EVENT, AppMeasurement.Event.zzitl, str)) {
            return 13;
        }
        return !zzb(NotificationCompat.CATEGORY_EVENT, 40, str) ? 2 : 0;
    }

    public final int zzjw(String str) {
        if (!zzaq("user property", str)) {
            return 6;
        }
        if (!zza("user property", AppMeasurement.UserProperty.zzits, str)) {
            return 15;
        }
        return !zzb("user property", 24, str) ? 6 : 0;
    }

    public final int zzjx(String str) {
        if (!zzar("user property", str)) {
            return 6;
        }
        if (!zza("user property", AppMeasurement.UserProperty.zzits, str)) {
            return 15;
        }
        return !zzb("user property", 24, str) ? 6 : 0;
    }

    public final Object zzk(String str, Object obj) {
        boolean z;
        int i = 256;
        if ("_ev".equals(str)) {
            z = true;
        } else {
            if (!zzkc(str)) {
                i = 100;
            }
            z = false;
        }
        return zza(i, obj, z);
    }

    public final boolean zzka(String str) {
        if (TextUtils.isEmpty(str)) {
            zzawm().zzayr().log("Missing google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI");
            return false;
        }
        zzbq.checkNotNull(str);
        if (str.matches("^1:\\d+:android:[a-f0-9]+$")) {
            return true;
        }
        zzawm().zzayr().zzj("Invalid google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI. provided id", str);
        return false;
    }

    public final boolean zzkd(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return zzawo().zzaxr().equals(str);
    }

    public final boolean zzkf(String str) {
        return "1".equals(zzawj().zzam(str, "measurement.upload.blacklist_internal"));
    }

    public final boolean zzkg(String str) {
        return "1".equals(zzawj().zzam(str, "measurement.upload.blacklist_public"));
    }

    public final int zzl(String str, Object obj) {
        return "_ldl".equals(str) ? zza("user property referrer", str, zzkb(str), obj, false) : zza("user property", str, zzkb(str), obj, false) ? 0 : 7;
    }

    public final Object zzm(String str, Object obj) {
        int zzkb;
        boolean z;
        if ("_ldl".equals(str)) {
            zzkb = zzkb(str);
            z = true;
        } else {
            zzkb = zzkb(str);
            z = false;
        }
        return zza(zzkb, obj, z);
    }

    public final Bundle zzp(@NonNull Uri uri) {
        String str;
        String str2;
        String str3;
        String str4;
        if (uri == null) {
            return null;
        }
        try {
            if (uri.isHierarchical()) {
                str4 = uri.getQueryParameter("utm_campaign");
                str3 = uri.getQueryParameter("utm_source");
                str2 = uri.getQueryParameter("utm_medium");
                str = uri.getQueryParameter("gclid");
            } else {
                str4 = null;
                str3 = null;
                str2 = null;
                str = null;
            }
            if (TextUtils.isEmpty(str4) && TextUtils.isEmpty(str3) && TextUtils.isEmpty(str2) && TextUtils.isEmpty(str)) {
                return null;
            }
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(str4)) {
                bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, str4);
            }
            if (!TextUtils.isEmpty(str3)) {
                bundle.putString("source", str3);
            }
            if (!TextUtils.isEmpty(str2)) {
                bundle.putString(FirebaseAnalytics.Param.MEDIUM, str2);
            }
            if (!TextUtils.isEmpty(str)) {
                bundle.putString("gclid", str);
            }
            String queryParameter = uri.getQueryParameter("utm_term");
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(FirebaseAnalytics.Param.TERM, queryParameter);
            }
            String queryParameter2 = uri.getQueryParameter("utm_content");
            if (!TextUtils.isEmpty(queryParameter2)) {
                bundle.putString(FirebaseAnalytics.Param.CONTENT, queryParameter2);
            }
            String queryParameter3 = uri.getQueryParameter(FirebaseAnalytics.Param.ACLID);
            if (!TextUtils.isEmpty(queryParameter3)) {
                bundle.putString(FirebaseAnalytics.Param.ACLID, queryParameter3);
            }
            String queryParameter4 = uri.getQueryParameter(FirebaseAnalytics.Param.CP1);
            if (!TextUtils.isEmpty(queryParameter4)) {
                bundle.putString(FirebaseAnalytics.Param.CP1, queryParameter4);
            }
            String queryParameter5 = uri.getQueryParameter("anid");
            if (!TextUtils.isEmpty(queryParameter5)) {
                bundle.putString("anid", queryParameter5);
            }
            return bundle;
        } catch (UnsupportedOperationException e) {
            zzawm().zzayt().zzj("Install referrer url isn't a hierarchical URI", e);
            return null;
        }
    }

    public final byte[] zzp(byte[] bArr) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            zzawm().zzayr().zzj("Failed to gzip content", e);
            throw e;
        }
    }

    public final byte[] zzq(byte[] bArr) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int read = gZIPInputStream.read(bArr2);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr2, 0, read);
                } else {
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            zzawm().zzayr().zzj("Failed to ungzip content", e);
            throw e;
        }
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ void zzut() {
        super.zzut();
    }

    @Override // com.google.android.gms.internal.zzcih
    public final /* bridge */ /* synthetic */ zzd zzwh() {
        return super.zzwh();
    }

    public final Bundle zzy(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                Object zzk = zzk(str, bundle.get(str));
                if (zzk == null) {
                    zzawm().zzayt().zzj("Param value can't be null", zzawh().zzjc(str));
                } else {
                    zza(bundle2, str, zzk);
                }
            }
        }
        return bundle2;
    }
}
