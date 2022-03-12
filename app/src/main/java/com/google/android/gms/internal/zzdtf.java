package com.google.android.gms.internal;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public final class zzdtf {
    private static zzdtg zzlvz;

    /* loaded from: classes.dex */
    static final class zza extends zzdtg {
        zza() {
        }

        @Override // com.google.android.gms.internal.zzdtg
        public final void zza(Throwable th, PrintStream printStream) {
            ThrowableExtension.printStackTrace(th, printStream);
        }

        @Override // com.google.android.gms.internal.zzdtg
        public final void zza(Throwable th, PrintWriter printWriter) {
            ThrowableExtension.printStackTrace(th, printWriter);
        }
    }

    static {
        zzdtg zzdtgVar;
        try {
            Integer zzbov = zzbov();
            zzdtgVar = (zzbov == null || zzbov.intValue() < 19) ? Boolean.getBoolean(ThrowableExtension.SYSTEM_PROPERTY_TWR_DISABLE_MIMIC) ^ true ? new zzdtj() : new zza() : new zzdtk();
        } catch (Throwable th) {
            PrintStream printStream = System.err;
            String name = zza.class.getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 132);
            sb.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
            sb.append(name);
            sb.append("will be used. The error is: ");
            printStream.println(sb.toString());
            ThrowableExtension.printStackTrace(th, System.err);
            zzdtgVar = new zza();
        }
        zzlvz = zzdtgVar;
    }

    public static void zza(Throwable th, PrintStream printStream) {
        zzlvz.zza(th, printStream);
    }

    public static void zza(Throwable th, PrintWriter printWriter) {
        zzlvz.zza(th, printWriter);
    }

    private static Integer zzbov() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            ThrowableExtension.printStackTrace(e, System.err);
            return null;
        }
    }
}
