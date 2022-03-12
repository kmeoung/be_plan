package cz.msebera.android.httpclient.util;

import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public class VersionInfo {
    public static final String PROPERTY_MODULE = "info.module";
    public static final String PROPERTY_RELEASE = "info.release";
    public static final String PROPERTY_TIMESTAMP = "info.timestamp";
    public static final String UNAVAILABLE = "UNAVAILABLE";
    public static final String VERSION_PROPERTY_FILE = "version.properties";
    private final String infoClassloader;
    private final String infoModule;
    private final String infoPackage;
    private final String infoRelease;
    private final String infoTimestamp;

    protected VersionInfo(String str, String str2, String str3, String str4, String str5) {
        Args.notNull(str, "Package identifier");
        this.infoPackage = str;
        this.infoModule = str2 == null ? UNAVAILABLE : str2;
        this.infoRelease = str3 == null ? UNAVAILABLE : str3;
        this.infoTimestamp = str4 == null ? UNAVAILABLE : str4;
        this.infoClassloader = str5 == null ? UNAVAILABLE : str5;
    }

    public final String getPackage() {
        return this.infoPackage;
    }

    public final String getModule() {
        return this.infoModule;
    }

    public final String getRelease() {
        return this.infoRelease;
    }

    public final String getTimestamp() {
        return this.infoTimestamp;
    }

    public final String getClassloader() {
        return this.infoClassloader;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.infoPackage.length() + 20 + this.infoModule.length() + this.infoRelease.length() + this.infoTimestamp.length() + this.infoClassloader.length());
        sb.append("VersionInfo(");
        sb.append(this.infoPackage);
        sb.append(':');
        sb.append(this.infoModule);
        if (!UNAVAILABLE.equals(this.infoRelease)) {
            sb.append(':');
            sb.append(this.infoRelease);
        }
        if (!UNAVAILABLE.equals(this.infoTimestamp)) {
            sb.append(':');
            sb.append(this.infoTimestamp);
        }
        sb.append(')');
        if (!UNAVAILABLE.equals(this.infoClassloader)) {
            sb.append('@');
            sb.append(this.infoClassloader);
        }
        return sb.toString();
    }

    public static VersionInfo[] loadVersionInfo(String[] strArr, ClassLoader classLoader) {
        Args.notNull(strArr, "Package identifier array");
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            VersionInfo loadVersionInfo = loadVersionInfo(str, classLoader);
            if (loadVersionInfo != null) {
                arrayList.add(loadVersionInfo);
            }
        }
        return (VersionInfo[]) arrayList.toArray(new VersionInfo[arrayList.size()]);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static cz.msebera.android.httpclient.util.VersionInfo loadVersionInfo(java.lang.String r4, java.lang.ClassLoader r5) {
        /*
            java.lang.String r0 = "Package identifier"
            cz.msebera.android.httpclient.util.Args.notNull(r4, r0)
            if (r5 == 0) goto L_0x0008
            goto L_0x0010
        L_0x0008:
            java.lang.Thread r5 = java.lang.Thread.currentThread()
            java.lang.ClassLoader r5 = r5.getContextClassLoader()
        L_0x0010:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: IOException -> 0x0046
            r1.<init>()     // Catch: IOException -> 0x0046
            r2 = 46
            r3 = 47
            java.lang.String r2 = r4.replace(r2, r3)     // Catch: IOException -> 0x0046
            r1.append(r2)     // Catch: IOException -> 0x0046
            java.lang.String r2 = "/"
            r1.append(r2)     // Catch: IOException -> 0x0046
            java.lang.String r2 = "version.properties"
            r1.append(r2)     // Catch: IOException -> 0x0046
            java.lang.String r1 = r1.toString()     // Catch: IOException -> 0x0046
            java.io.InputStream r1 = r5.getResourceAsStream(r1)     // Catch: IOException -> 0x0046
            if (r1 == 0) goto L_0x0046
            java.util.Properties r2 = new java.util.Properties     // Catch: all -> 0x0041
            r2.<init>()     // Catch: all -> 0x0041
            r2.load(r1)     // Catch: all -> 0x0041
            r1.close()     // Catch: IOException -> 0x0047
            goto L_0x0047
        L_0x0041:
            r2 = move-exception
            r1.close()     // Catch: IOException -> 0x0046
            throw r2     // Catch: IOException -> 0x0046
        L_0x0046:
            r2 = r0
        L_0x0047:
            if (r2 == 0) goto L_0x004d
            cz.msebera.android.httpclient.util.VersionInfo r0 = fromMap(r4, r2, r5)
        L_0x004d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.util.VersionInfo.loadVersionInfo(java.lang.String, java.lang.ClassLoader):cz.msebera.android.httpclient.util.VersionInfo");
    }

    protected static VersionInfo fromMap(String str, Map<?, ?> map, ClassLoader classLoader) {
        String str2;
        String str3;
        String str4;
        Args.notNull(str, "Package identifier");
        String str5 = null;
        if (map != null) {
            str4 = (String) map.get(PROPERTY_MODULE);
            if (str4 != null && str4.length() < 1) {
                str4 = null;
            }
            str3 = (String) map.get(PROPERTY_RELEASE);
            if (str3 != null && (str3.length() < 1 || str3.equals("${pom.version}"))) {
                str3 = null;
            }
            String str6 = (String) map.get(PROPERTY_TIMESTAMP);
            str2 = (str6 == null || (str6.length() >= 1 && !str6.equals("${mvn.timestamp}"))) ? str6 : null;
        } else {
            str4 = null;
            str3 = null;
            str2 = null;
        }
        if (classLoader != null) {
            str5 = classLoader.toString();
        }
        return new VersionInfo(str, str4, str3, str2, str5);
    }

    public static String getUserAgent(String str, String str2, Class<?> cls) {
        VersionInfo loadVersionInfo = loadVersionInfo(str2, cls.getClassLoader());
        return String.format("%s/%s (Java/%s)", str, loadVersionInfo != null ? loadVersionInfo.getRelease() : UNAVAILABLE, System.getProperty("java.version"));
    }
}
