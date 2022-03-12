package com.bumptech.glide.load.model;

import android.util.Log;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class StreamEncoder implements Encoder<InputStream> {
    private static final String TAG = "StreamEncoder";
    private final ArrayPool byteArrayPool;

    public StreamEncoder(ArrayPool arrayPool) {
        this.byteArrayPool = arrayPool;
    }

    public boolean encode(InputStream inputStream, File file, Options options) {
        Throwable th;
        IOException e;
        FileOutputStream fileOutputStream;
        byte[] bArr = (byte[]) this.byteArrayPool.get(65536, byte[].class);
        boolean z = false;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file);
                    while (true) {
                        try {
                            int read = inputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        } catch (IOException e2) {
                            e = e2;
                            fileOutputStream2 = fileOutputStream;
                            if (Log.isLoggable(TAG, 3)) {
                                Log.d(TAG, "Failed to encode data onto the OutputStream", e);
                            }
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.close();
                            }
                            this.byteArrayPool.put(bArr, byte[].class);
                            return z;
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream2 = fileOutputStream;
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException unused) {
                                }
                            }
                            this.byteArrayPool.put(bArr, byte[].class);
                            throw th;
                        }
                    }
                    fileOutputStream.close();
                    z = true;
                } catch (IOException unused2) {
                }
            } catch (IOException e3) {
                e = e3;
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            this.byteArrayPool.put(bArr, byte[].class);
            return z;
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
