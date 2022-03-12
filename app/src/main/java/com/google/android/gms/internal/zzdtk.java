package com.google.android.gms.internal;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes.dex */
final class zzdtk extends zzdtg {
    @Override // com.google.android.gms.internal.zzdtg
    public final void zza(Throwable th, PrintStream printStream) {
        ThrowableExtension.printStackTrace(th, printStream);
    }

    @Override // com.google.android.gms.internal.zzdtg
    public final void zza(Throwable th, PrintWriter printWriter) {
        ThrowableExtension.printStackTrace(th, printWriter);
    }
}
