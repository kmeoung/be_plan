package com.google.android.gms.internal;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.entity.BasicHttpEntity;

/* loaded from: classes.dex */
public final class zzan implements zzam {
    private final zzao zzcb;
    private final SSLSocketFactory zzcc;

    public zzan() {
        this(null);
    }

    private zzan(zzao zzaoVar) {
        this(null, null);
    }

    private zzan(zzao zzaoVar, SSLSocketFactory sSLSocketFactory) {
        this.zzcb = null;
        this.zzcc = null;
    }

    private static HttpEntity zza(HttpURLConnection httpURLConnection) {
        InputStream inputStream;
        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
        try {
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException unused) {
            inputStream = httpURLConnection.getErrorStream();
        }
        basicHttpEntity.setContent(inputStream);
        basicHttpEntity.setContentLength(httpURLConnection.getContentLength());
        basicHttpEntity.setContentEncoding(httpURLConnection.getContentEncoding());
        basicHttpEntity.setContentType(httpURLConnection.getContentType());
        return basicHttpEntity;
    }

    private static void zza(HttpURLConnection httpURLConnection, zzp<?> zzpVar) throws IOException, zza {
        byte[] zzg = zzpVar.zzg();
        if (zzg != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", zzp.zzf());
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.write(zzg);
            dataOutputStream.close();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00cd  */
    @Override // com.google.android.gms.internal.zzam
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.apache.http.HttpResponse zza(com.google.android.gms.internal.zzp<?> r7, java.util.Map<java.lang.String, java.lang.String> r8) throws java.io.IOException, com.google.android.gms.internal.zza {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzan.zza(com.google.android.gms.internal.zzp, java.util.Map):org.apache.http.HttpResponse");
    }
}
