package android.support.v4.graphics;

import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String TAG = "TypefaceCompatApi21Impl";

    private File getFile(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            String readlink = Os.readlink("/proc/self/fd/" + parcelFileDescriptor.getFd());
            if (OsConstants.S_ISREG(Os.stat(readlink).st_mode)) {
                return new File(readlink);
            }
            return null;
        } catch (ErrnoException unused) {
            return null;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:23:0x0047
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:90)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
        */
    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl, android.support.v4.graphics.TypefaceCompat.TypefaceCompatImpl
    public android.graphics.Typeface createFromFontInfo(android.content.Context r5, android.os.CancellationSignal r6, @android.support.annotation.NonNull android.support.v4.provider.FontsContractCompat.FontInfo[] r7, int r8) {
        /*
            r4 = this;
            int r0 = r7.length
            r1 = 0
            r2 = 1
            if (r0 >= r2) goto L_0x0006
            return r1
        L_0x0006:
            android.support.v4.provider.FontsContractCompat$FontInfo r7 = r4.findBestInfo(r7, r8)
            android.content.ContentResolver r8 = r5.getContentResolver()
            android.net.Uri r7 = r7.getUri()     // Catch: IOException -> 0x007b
            java.lang.String r0 = "r"
            android.os.ParcelFileDescriptor r6 = r8.openFileDescriptor(r7, r0, r6)     // Catch: IOException -> 0x007b
            java.io.File r7 = r4.getFile(r6)     // Catch: Throwable -> 0x0064
            if (r7 == 0) goto L_0x002f
            boolean r8 = r7.canRead()     // Catch: Throwable -> 0x0064
            if (r8 != 0) goto L_0x0025
            goto L_0x002f
        L_0x0025:
            android.graphics.Typeface r5 = android.graphics.Typeface.createFromFile(r7)     // Catch: Throwable -> 0x0064
            if (r6 == 0) goto L_0x002e
            r6.close()     // Catch: IOException -> 0x007b
        L_0x002e:
            return r5
        L_0x002f:
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch: Throwable -> 0x0064
            java.io.FileDescriptor r8 = r6.getFileDescriptor()     // Catch: Throwable -> 0x0064
            r7.<init>(r8)     // Catch: Throwable -> 0x0064
            android.graphics.Typeface r5 = super.createFromInputStream(r5, r7)     // Catch: Throwable -> 0x004a
            if (r7 == 0) goto L_0x0041
            r7.close()     // Catch: Throwable -> 0x0064
        L_0x0041:
            if (r6 == 0) goto L_0x0046
            r6.close()     // Catch: IOException -> 0x007b
        L_0x0046:
            return r5
        L_0x0047:
            r5 = move-exception
            r8 = r1
            goto L_0x0050
        L_0x004a:
            r5 = move-exception
            throw r5     // Catch: all -> 0x004c
        L_0x004c:
            r8 = move-exception
            r3 = r8
            r8 = r5
            r5 = r3
        L_0x0050:
            if (r7 == 0) goto L_0x0060
            if (r8 == 0) goto L_0x005d
            r7.close()     // Catch: Throwable -> 0x0058
            goto L_0x0060
        L_0x0058:
            r7 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r8, r7)     // Catch: Throwable -> 0x0064
            goto L_0x0060
        L_0x005d:
            r7.close()     // Catch: Throwable -> 0x0064
        L_0x0060:
            throw r5     // Catch: Throwable -> 0x0064
        L_0x0061:
            r5 = move-exception
            r7 = r1
            goto L_0x006a
        L_0x0064:
            r5 = move-exception
            throw r5     // Catch: all -> 0x0066
        L_0x0066:
            r7 = move-exception
            r3 = r7
            r7 = r5
            r5 = r3
        L_0x006a:
            if (r6 == 0) goto L_0x007a
            if (r7 == 0) goto L_0x0077
            r6.close()     // Catch: Throwable -> 0x0072
            goto L_0x007a
        L_0x0072:
            r6 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r7, r6)     // Catch: IOException -> 0x007b
            goto L_0x007a
        L_0x0077:
            r6.close()     // Catch: IOException -> 0x007b
        L_0x007a:
            throw r5     // Catch: IOException -> 0x007b
        L_0x007b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatApi21Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }
}
