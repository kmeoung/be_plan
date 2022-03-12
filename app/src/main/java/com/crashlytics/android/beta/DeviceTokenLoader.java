package com.crashlytics.android.beta;

import android.content.Context;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.services.cache.ValueLoader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public class DeviceTokenLoader implements ValueLoader<String> {
    private static final String BETA_APP_PACKAGE_NAME = "io.crash.air";
    private static final String DIRFACTOR_DEVICE_TOKEN_PREFIX = "assets/com.crashlytics.android.beta/dirfactor-device-token=";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.util.zip.ZipInputStream] */
    /* JADX WARN: Type inference failed for: r3v15, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r9v11, types: [io.fabric.sdk.android.Logger] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // io.fabric.sdk.android.services.cache.ValueLoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String load(android.content.Context r9) throws java.lang.Exception {
        /*
            r8 = this;
            long r0 = java.lang.System.nanoTime()
            java.lang.String r2 = ""
            r3 = 0
            java.lang.String r4 = "io.crash.air"
            java.util.zip.ZipInputStream r9 = r8.getZipInputStreamOfApkFrom(r9, r4)     // Catch: all -> 0x0035, IOException -> 0x0037, FileNotFoundException -> 0x0049, NameNotFoundException -> 0x005b
            java.lang.String r3 = r8.determineDeviceToken(r9)     // Catch: all -> 0x0025, IOException -> 0x0029, FileNotFoundException -> 0x002e, NameNotFoundException -> 0x0033
            if (r9 == 0) goto L_0x0023
            r9.close()     // Catch: IOException -> 0x0017
            goto L_0x0023
        L_0x0017:
            r9 = move-exception
            io.fabric.sdk.android.Logger r2 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r4 = "Beta"
            java.lang.String r5 = "Failed to close the APK file"
            r2.e(r4, r5, r9)
        L_0x0023:
            r2 = r3
            goto L_0x0078
        L_0x0025:
            r0 = move-exception
            r3 = r9
            goto L_0x00a5
        L_0x0029:
            r3 = move-exception
            r7 = r3
            r3 = r9
            r9 = r7
            goto L_0x0038
        L_0x002e:
            r3 = move-exception
            r7 = r3
            r3 = r9
            r9 = r7
            goto L_0x004a
        L_0x0033:
            r3 = r9
            goto L_0x005b
        L_0x0035:
            r0 = move-exception
            goto L_0x00a5
        L_0x0037:
            r9 = move-exception
        L_0x0038:
            io.fabric.sdk.android.Logger r4 = io.fabric.sdk.android.Fabric.getLogger()     // Catch: all -> 0x0035
            java.lang.String r5 = "Beta"
            java.lang.String r6 = "Failed to read the APK file"
            r4.e(r5, r6, r9)     // Catch: all -> 0x0035
            if (r3 == 0) goto L_0x0078
            r3.close()     // Catch: IOException -> 0x006c
            goto L_0x0078
        L_0x0049:
            r9 = move-exception
        L_0x004a:
            io.fabric.sdk.android.Logger r4 = io.fabric.sdk.android.Fabric.getLogger()     // Catch: all -> 0x0035
            java.lang.String r5 = "Beta"
            java.lang.String r6 = "Failed to find the APK file"
            r4.e(r5, r6, r9)     // Catch: all -> 0x0035
            if (r3 == 0) goto L_0x0078
            r3.close()     // Catch: IOException -> 0x006c
            goto L_0x0078
        L_0x005b:
            io.fabric.sdk.android.Logger r9 = io.fabric.sdk.android.Fabric.getLogger()     // Catch: all -> 0x0035
            java.lang.String r4 = "Beta"
            java.lang.String r5 = "Beta by Crashlytics app is not installed"
            r9.d(r4, r5)     // Catch: all -> 0x0035
            if (r3 == 0) goto L_0x0078
            r3.close()     // Catch: IOException -> 0x006c
            goto L_0x0078
        L_0x006c:
            r9 = move-exception
            io.fabric.sdk.android.Logger r3 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r4 = "Beta"
            java.lang.String r5 = "Failed to close the APK file"
            r3.e(r4, r5, r9)
        L_0x0078:
            long r3 = java.lang.System.nanoTime()
            long r5 = r3 - r0
            double r0 = (double) r5
            r3 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            double r0 = r0 / r3
            io.fabric.sdk.android.Logger r9 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r3 = "Beta"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Beta device token load took "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r0 = "ms"
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            r9.d(r3, r0)
            return r2
        L_0x00a5:
            if (r3 == 0) goto L_0x00b7
            r3.close()     // Catch: IOException -> 0x00ab
            goto L_0x00b7
        L_0x00ab:
            r9 = move-exception
            io.fabric.sdk.android.Logger r1 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r2 = "Beta"
            java.lang.String r3 = "Failed to close the APK file"
            r1.e(r2, r3, r9)
        L_0x00b7:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.beta.DeviceTokenLoader.load(android.content.Context):java.lang.String");
    }

    ZipInputStream getZipInputStreamOfApkFrom(Context context, String str) throws PackageManager.NameNotFoundException, FileNotFoundException {
        return new ZipInputStream(new FileInputStream(context.getPackageManager().getApplicationInfo(str, 0).sourceDir));
    }

    String determineDeviceToken(ZipInputStream zipInputStream) throws IOException {
        ZipEntry nextEntry = zipInputStream.getNextEntry();
        if (nextEntry == null) {
            return "";
        }
        String name = nextEntry.getName();
        return name.startsWith(DIRFACTOR_DEVICE_TOKEN_PREFIX) ? name.substring(DIRFACTOR_DEVICE_TOKEN_PREFIX.length(), name.length() - 1) : "";
    }
}
