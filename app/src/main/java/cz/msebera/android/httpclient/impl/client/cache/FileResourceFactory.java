package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Immutable
/* loaded from: classes.dex */
public class FileResourceFactory implements ResourceFactory {
    private final File cacheDir;
    private final BasicIdGenerator idgen = new BasicIdGenerator();

    public FileResourceFactory(File file) {
        this.cacheDir = file;
    }

    private File generateUniqueCacheFile(String str) {
        StringBuilder sb = new StringBuilder();
        this.idgen.generate(sb);
        sb.append('.');
        int min = Math.min(str.length(), 100);
        for (int i = 0; i < min; i++) {
            char charAt = str.charAt(i);
            if (Character.isLetterOrDigit(charAt) || charAt == '.') {
                sb.append(charAt);
            } else {
                sb.append('-');
            }
        }
        return new File(this.cacheDir, sb.toString());
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0027, code lost:
        r11.reached();
     */
    /* JADX WARN: Finally extract failed */
    @Override // cz.msebera.android.httpclient.client.cache.ResourceFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public cz.msebera.android.httpclient.client.cache.Resource generate(java.lang.String r9, java.io.InputStream r10, cz.msebera.android.httpclient.client.cache.InputLimit r11) throws java.io.IOException {
        /*
            r8 = this;
            java.io.File r9 = r8.generateUniqueCacheFile(r9)
            java.io.FileOutputStream r0 = new java.io.FileOutputStream
            r0.<init>(r9)
            r1 = 2048(0x800, float:2.87E-42)
            byte[] r1 = new byte[r1]     // Catch: all -> 0x0036
            r2 = 0
        L_0x000f:
            int r4 = r10.read(r1)     // Catch: all -> 0x0036
            r5 = -1
            if (r4 == r5) goto L_0x002d
            r5 = 0
            r0.write(r1, r5, r4)     // Catch: all -> 0x0036
            long r4 = (long) r4     // Catch: all -> 0x0036
            long r6 = r2 + r4
            if (r11 == 0) goto L_0x002b
            long r2 = r11.getValue()     // Catch: all -> 0x0036
            int r4 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x002b
            r11.reached()     // Catch: all -> 0x0036
            goto L_0x002d
        L_0x002b:
            r2 = r6
            goto L_0x000f
        L_0x002d:
            r0.close()
            cz.msebera.android.httpclient.impl.client.cache.FileResource r10 = new cz.msebera.android.httpclient.impl.client.cache.FileResource
            r10.<init>(r9)
            return r10
        L_0x0036:
            r9 = move-exception
            r0.close()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.client.cache.FileResourceFactory.generate(java.lang.String, java.io.InputStream, cz.msebera.android.httpclient.client.cache.InputLimit):cz.msebera.android.httpclient.client.cache.Resource");
    }

    @Override // cz.msebera.android.httpclient.client.cache.ResourceFactory
    public Resource copy(String str, Resource resource) throws IOException {
        File generateUniqueCacheFile = generateUniqueCacheFile(str);
        if (resource instanceof FileResource) {
            IOUtils.copyFile(((FileResource) resource).getFile(), generateUniqueCacheFile);
        } else {
            IOUtils.copyAndClose(resource.getInputStream(), new FileOutputStream(generateUniqueCacheFile));
        }
        return new FileResource(generateUniqueCacheFile);
    }
}
