package com.google.android.gms.internal;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public interface zzffi extends zzffk {
    byte[] toByteArray();

    zzfdh toByteString();

    void writeTo(OutputStream outputStream) throws IOException;

    void zza(zzfdv zzfdvVar) throws IOException;

    zzffm<? extends zzffi> zzcvd();

    zzffj zzcvg();

    int zzhl();
}
