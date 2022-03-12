package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

/* loaded from: classes.dex */
public class zzad implements zzk {
    private static boolean DEBUG = zzab.DEBUG;
    private zzam zzbm;
    private zzae zzbn;

    public zzad(zzam zzamVar) {
        this(zzamVar, new zzae(4096));
    }

    private zzad(zzam zzamVar, zzae zzaeVar) {
        this.zzbm = zzamVar;
        this.zzbn = zzaeVar;
    }

    private static Map<String, String> zza(Header[] headerArr) {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < headerArr.length; i++) {
            treeMap.put(headerArr[i].getName(), headerArr[i].getValue());
        }
        return treeMap;
    }

    private static void zza(String str, zzp<?> zzpVar, zzaa zzaaVar) throws zzaa {
        zzx zzj = zzpVar.zzj();
        int zzi = zzpVar.zzi();
        try {
            zzj.zza(zzaaVar);
            zzpVar.zzb(String.format("%s-retry [timeout=%s]", str, Integer.valueOf(zzi)));
        } catch (zzaa e) {
            zzpVar.zzb(String.format("%s-timeout-giveup [timeout=%s]", str, Integer.valueOf(zzi)));
            throw e;
        }
    }

    private final byte[] zza(HttpEntity httpEntity) throws IOException, zzy {
        Throwable th;
        zzap zzapVar = new zzap(this.zzbn, (int) httpEntity.getContentLength());
        byte[] bArr = null;
        try {
            InputStream content = httpEntity.getContent();
            if (content == null) {
                throw new zzy();
            }
            byte[] zzb = this.zzbn.zzb(1024);
            while (true) {
                try {
                    int read = content.read(zzb);
                    if (read == -1) {
                        break;
                    }
                    zzapVar.write(zzb, 0, read);
                } catch (Throwable th2) {
                    th = th2;
                    bArr = zzb;
                    try {
                        httpEntity.consumeContent();
                    } catch (IOException unused) {
                        zzab.zza("Error occurred when calling consumingContent", new Object[0]);
                    }
                    this.zzbn.zza(bArr);
                    zzapVar.close();
                    throw th;
                }
            }
            byte[] byteArray = zzapVar.toByteArray();
            try {
                httpEntity.consumeContent();
            } catch (IOException unused2) {
                zzab.zza("Error occurred when calling consumingContent", new Object[0]);
            }
            this.zzbn.zza(zzb);
            zzapVar.close();
            return byteArray;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b7, code lost:
        if (r6 > 3000) goto L_0x00b9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0118, code lost:
        throw new java.io.IOException();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:108:0x019d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0131  */
    /* JADX WARN: Type inference failed for: r14v11, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v22, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r7v25 */
    /* JADX WARN: Type inference failed for: r7v26 */
    /* JADX WARN: Type inference failed for: r7v27 */
    /* JADX WARN: Type inference failed for: r7v7, types: [com.google.android.gms.internal.zza, com.google.android.gms.internal.zzaa] */
    /* JADX WARN: Unknown variable types count: 3 */
    @Override // com.google.android.gms.internal.zzk
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.gms.internal.zzn zza(com.google.android.gms.internal.zzp<?> r24) throws com.google.android.gms.internal.zzaa {
        /*
            Method dump skipped, instructions count: 473
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzad.zza(com.google.android.gms.internal.zzp):com.google.android.gms.internal.zzn");
    }
}
