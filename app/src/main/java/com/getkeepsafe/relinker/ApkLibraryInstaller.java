package com.getkeepsafe.relinker;

import com.getkeepsafe.relinker.ReLinker;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class ApkLibraryInstaller implements ReLinker.LibraryInstaller {
    private static final int COPY_BUFFER_SIZE = 4096;
    private static final int MAX_TRIES = 5;

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:
        if (r9 != null) goto L_0x0037;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:?, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:?, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0028, code lost:
        r23.log("FATAL! Couldn't find application APK!");
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002d, code lost:
        if (r9 == null) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x002f, code lost:
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0032, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0033, code lost:
        r2 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0037, code lost:
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0103, code lost:
        if (r9 != null) goto L_0x0105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0105, code lost:
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0108, code lost:
        throw r2;
     */
    @Override // com.getkeepsafe.relinker.ReLinker.LibraryInstaller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void installLibrary(android.content.Context r19, java.lang.String[] r20, java.lang.String r21, java.io.File r22, com.getkeepsafe.relinker.ReLinkerInstance r23) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.getkeepsafe.relinker.ApkLibraryInstaller.installLibrary(android.content.Context, java.lang.String[], java.lang.String, java.io.File, com.getkeepsafe.relinker.ReLinkerInstance):void");
    }

    private long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                outputStream.flush();
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += read;
        }
    }

    private void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}
