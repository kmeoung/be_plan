package io.realm.internal;

import android.os.Build;
import android.support.v4.os.EnvironmentCompat;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class Util {
    static native String nativeGetTablePrefix();

    public static String getTablePrefix() {
        return nativeGetTablePrefix();
    }

    public static Class<? extends RealmModel> getOriginalModelClass(Class<? extends RealmModel> cls) {
        Class superclass = cls.getSuperclass();
        return (superclass.equals(Object.class) || superclass.equals(RealmObject.class)) ? cls : superclass;
    }

    public static String getStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        ThrowableExtension.printStackTrace(th, new PrintWriter((Writer) stringWriter, true));
        return stringWriter.getBuffer().toString();
    }

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith(EnvironmentCompat.MEDIA_UNKNOWN) || Build.MODEL.contains(CommonUtils.GOOGLE_SDK) || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || CommonUtils.GOOGLE_SDK.equals(Build.PRODUCT);
    }

    public static boolean isEmptyString(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean deleteRealm(String str, File file, String str2) {
        boolean z;
        File file2 = new File(file, str2 + ".management");
        File[] listFiles = file2.listFiles();
        if (listFiles != null) {
            z = true;
            for (File file3 : listFiles) {
                z = z && file3.delete();
            }
        } else {
            z = true;
        }
        return (z && file2.delete()) && deletes(str, file, str2);
    }

    private static boolean deletes(String str, File file, String str2) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        for (File file2 : Arrays.asList(new File(file, str2), new File(file, str2 + ".lock"), new File(file, str2 + ".log_a"), new File(file, str2 + ".log_b"), new File(file, str2 + ".log"), new File(str))) {
            if (file2.exists() && !file2.delete()) {
                atomicBoolean.set(false);
                RealmLog.warn("Could not delete the file %s", file2);
            }
        }
        return atomicBoolean.get();
    }
}
