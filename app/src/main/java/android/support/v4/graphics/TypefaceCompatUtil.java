package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    public static File getTempFile(Context context) {
        String str = CACHE_FILE_PREFIX + Process.myPid() + "-" + Process.myTid() + "-";
        for (int i = 0; i < 100; i++) {
            File file = new File(context.getCacheDir(), str + i);
            if (file.createNewFile()) {
                return file;
            }
        }
        return null;
    }

    @RequiresApi(19)
    private static ByteBuffer mmap(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel channel = fileInputStream.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return map;
        } catch (IOException unused) {
            return null;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:11:0x002f
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:90)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
        */
    @android.support.annotation.RequiresApi(19)
    public static java.nio.ByteBuffer mmap(android.content.Context r8, android.os.CancellationSignal r9, android.net.Uri r10) {
        /*
            android.content.ContentResolver r8 = r8.getContentResolver()
            r0 = 0
            java.lang.String r1 = "r"
            android.os.ParcelFileDescriptor r8 = r8.openFileDescriptor(r10, r1, r9)     // Catch: IOException -> 0x0063
            java.io.FileInputStream r9 = new java.io.FileInputStream     // Catch: Throwable -> 0x004c
            java.io.FileDescriptor r10 = r8.getFileDescriptor()     // Catch: Throwable -> 0x004c
            r9.<init>(r10)     // Catch: Throwable -> 0x004c
            java.nio.channels.FileChannel r1 = r9.getChannel()     // Catch: Throwable -> 0x0032
            long r5 = r1.size()     // Catch: Throwable -> 0x0032
            java.nio.channels.FileChannel$MapMode r2 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch: Throwable -> 0x0032
            r3 = 0
            java.nio.MappedByteBuffer r10 = r1.map(r2, r3, r5)     // Catch: Throwable -> 0x0032
            if (r9 == 0) goto L_0x0029
            r9.close()     // Catch: Throwable -> 0x004c
        L_0x0029:
            if (r8 == 0) goto L_0x002e
            r8.close()     // Catch: IOException -> 0x0063
        L_0x002e:
            return r10
        L_0x002f:
            r10 = move-exception
            r1 = r0
            goto L_0x0038
        L_0x0032:
            r10 = move-exception
            throw r10     // Catch: all -> 0x0034
        L_0x0034:
            r1 = move-exception
            r7 = r1
            r1 = r10
            r10 = r7
        L_0x0038:
            if (r9 == 0) goto L_0x0048
            if (r1 == 0) goto L_0x0045
            r9.close()     // Catch: Throwable -> 0x0040
            goto L_0x0048
        L_0x0040:
            r9 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r9)     // Catch: Throwable -> 0x004c
            goto L_0x0048
        L_0x0045:
            r9.close()     // Catch: Throwable -> 0x004c
        L_0x0048:
            throw r10     // Catch: Throwable -> 0x004c
        L_0x0049:
            r9 = move-exception
            r10 = r0
            goto L_0x0052
        L_0x004c:
            r9 = move-exception
            throw r9     // Catch: all -> 0x004e
        L_0x004e:
            r10 = move-exception
            r7 = r10
            r10 = r9
            r9 = r7
        L_0x0052:
            if (r8 == 0) goto L_0x0062
            if (r10 == 0) goto L_0x005f
            r8.close()     // Catch: Throwable -> 0x005a
            goto L_0x0062
        L_0x005a:
            r8 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r10, r8)     // Catch: IOException -> 0x0063
            goto L_0x0062
        L_0x005f:
            r8.close()     // Catch: IOException -> 0x0063
        L_0x0062:
            throw r9     // Catch: IOException -> 0x0063
        L_0x0063:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer");
    }

    @RequiresApi(19)
    public static ByteBuffer copyToDirectBuffer(Context context, Resources resources, int i) {
        File tempFile = getTempFile(context);
        if (tempFile == null) {
            return null;
        }
        try {
            if (!copyToFile(tempFile, resources, i)) {
                return null;
            }
            return mmap(tempFile);
        } finally {
            tempFile.delete();
        }
    }

    public static boolean copyToFile(File file, InputStream inputStream) {
        Throwable th;
        IOException e;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(file, false);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    closeQuietly(fileOutputStream);
                    return true;
                }
            }
        } catch (IOException e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
            closeQuietly(fileOutputStream2);
            return false;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream2 = fileOutputStream;
            closeQuietly(fileOutputStream2);
            throw th;
        }
    }

    public static boolean copyToFile(File file, Resources resources, int i) {
        InputStream inputStream;
        Throwable th;
        try {
            inputStream = resources.openRawResource(i);
            try {
                boolean copyToFile = copyToFile(file, inputStream);
                closeQuietly(inputStream);
                return copyToFile;
            } catch (Throwable th2) {
                th = th2;
                closeQuietly(inputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}
