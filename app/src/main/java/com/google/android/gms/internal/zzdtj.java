package com.google.android.gms.internal;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes.dex */
final class zzdtj extends zzdtg {
    private final zzdth zzlwe = new zzdth();

    @Override // com.google.android.gms.internal.zzdtg
    public final void zza(Throwable th, PrintStream printStream) {
        ThrowableExtension.printStackTrace(th, printStream);
        List<Throwable> zza = this.zzlwe.zza(th, false);
        if (zza != null) {
            synchronized (zza) {
                for (Throwable th2 : zza) {
                    printStream.print("Suppressed: ");
                    ThrowableExtension.printStackTrace(th2, printStream);
                }
            }
        }
    }

    @Override // com.google.android.gms.internal.zzdtg
    public final void zza(Throwable th, PrintWriter printWriter) {
        ThrowableExtension.printStackTrace(th, printWriter);
        List<Throwable> zza = this.zzlwe.zza(th, false);
        if (zza != null) {
            synchronized (zza) {
                for (Throwable th2 : zza) {
                    printWriter.print("Suppressed: ");
                    ThrowableExtension.printStackTrace(th2, printWriter);
                }
            }
        }
    }
}
