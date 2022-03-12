package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;

/* loaded from: classes.dex */
public class BitmapEncoder implements ResourceEncoder<Bitmap> {
    private static final String TAG = "BitmapEncoder";
    public static final Option<Integer> COMPRESSION_QUALITY = Option.memory("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionQuality", 90);
    public static final Option<Bitmap.CompressFormat> COMPRESSION_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionFormat");

    /* JADX WARN: Removed duplicated region for block: B:23:0x0081 A[Catch: all -> 0x00d3, TRY_LEAVE, TryCatch #2 {all -> 0x00d3, blocks: (B:3:0x0036, B:9:0x0056, B:20:0x0075, B:21:0x0078, B:23:0x0081, B:27:0x00cf, B:28:0x00d2), top: B:38:0x0036 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean encode(com.bumptech.glide.load.engine.Resource<android.graphics.Bitmap> r8, java.io.File r9, com.bumptech.glide.load.Options r10) {
        /*
            r7 = this;
            java.lang.Object r8 = r8.get()
            android.graphics.Bitmap r8 = (android.graphics.Bitmap) r8
            android.graphics.Bitmap$CompressFormat r0 = r7.getFormat(r8, r10)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "encode: ["
            r1.append(r2)
            int r2 = r8.getWidth()
            r1.append(r2)
            java.lang.String r2 = "x"
            r1.append(r2)
            int r2 = r8.getHeight()
            r1.append(r2)
            java.lang.String r2 = "] "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            android.support.v4.os.TraceCompat.beginSection(r1)
            long r1 = com.bumptech.glide.util.LogTime.getLogTime()     // Catch: all -> 0x00d3
            com.bumptech.glide.load.Option<java.lang.Integer> r3 = com.bumptech.glide.load.resource.bitmap.BitmapEncoder.COMPRESSION_QUALITY     // Catch: all -> 0x00d3
            java.lang.Object r3 = r10.get(r3)     // Catch: all -> 0x00d3
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch: all -> 0x00d3
            int r3 = r3.intValue()     // Catch: all -> 0x00d3
            r4 = 0
            r5 = 0
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch: all -> 0x0060, IOException -> 0x0062
            r6.<init>(r9)     // Catch: all -> 0x0060, IOException -> 0x0062
            r8.compress(r0, r3, r6)     // Catch: all -> 0x005a, IOException -> 0x005d
            r6.close()     // Catch: all -> 0x005a, IOException -> 0x005d
            r4 = 1
            if (r6 == 0) goto L_0x0078
            r6.close()     // Catch: IOException -> 0x0078, all -> 0x00d3
            goto L_0x0078
        L_0x005a:
            r8 = move-exception
            r5 = r6
            goto L_0x00cd
        L_0x005d:
            r9 = move-exception
            r5 = r6
            goto L_0x0063
        L_0x0060:
            r8 = move-exception
            goto L_0x00cd
        L_0x0062:
            r9 = move-exception
        L_0x0063:
            java.lang.String r3 = "BitmapEncoder"
            r6 = 3
            boolean r3 = android.util.Log.isLoggable(r3, r6)     // Catch: all -> 0x0060
            if (r3 == 0) goto L_0x0073
            java.lang.String r3 = "BitmapEncoder"
            java.lang.String r6 = "Failed to encode Bitmap"
            android.util.Log.d(r3, r6, r9)     // Catch: all -> 0x0060
        L_0x0073:
            if (r5 == 0) goto L_0x0078
            r5.close()     // Catch: IOException -> 0x0078, all -> 0x00d3
        L_0x0078:
            java.lang.String r9 = "BitmapEncoder"
            r3 = 2
            boolean r9 = android.util.Log.isLoggable(r9, r3)     // Catch: all -> 0x00d3
            if (r9 == 0) goto L_0x00c9
            java.lang.String r9 = "BitmapEncoder"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: all -> 0x00d3
            r3.<init>()     // Catch: all -> 0x00d3
            java.lang.String r5 = "Compressed with type: "
            r3.append(r5)     // Catch: all -> 0x00d3
            r3.append(r0)     // Catch: all -> 0x00d3
            java.lang.String r0 = " of size "
            r3.append(r0)     // Catch: all -> 0x00d3
            int r0 = com.bumptech.glide.util.Util.getBitmapByteSize(r8)     // Catch: all -> 0x00d3
            r3.append(r0)     // Catch: all -> 0x00d3
            java.lang.String r0 = " in "
            r3.append(r0)     // Catch: all -> 0x00d3
            double r0 = com.bumptech.glide.util.LogTime.getElapsedMillis(r1)     // Catch: all -> 0x00d3
            r3.append(r0)     // Catch: all -> 0x00d3
            java.lang.String r0 = ", options format: "
            r3.append(r0)     // Catch: all -> 0x00d3
            com.bumptech.glide.load.Option<android.graphics.Bitmap$CompressFormat> r0 = com.bumptech.glide.load.resource.bitmap.BitmapEncoder.COMPRESSION_FORMAT     // Catch: all -> 0x00d3
            java.lang.Object r10 = r10.get(r0)     // Catch: all -> 0x00d3
            r3.append(r10)     // Catch: all -> 0x00d3
            java.lang.String r10 = ", hasAlpha: "
            r3.append(r10)     // Catch: all -> 0x00d3
            boolean r8 = r8.hasAlpha()     // Catch: all -> 0x00d3
            r3.append(r8)     // Catch: all -> 0x00d3
            java.lang.String r8 = r3.toString()     // Catch: all -> 0x00d3
            android.util.Log.v(r9, r8)     // Catch: all -> 0x00d3
        L_0x00c9:
            android.support.v4.os.TraceCompat.endSection()
            return r4
        L_0x00cd:
            if (r5 == 0) goto L_0x00d2
            r5.close()     // Catch: IOException -> 0x00d2, all -> 0x00d3
        L_0x00d2:
            throw r8     // Catch: all -> 0x00d3
        L_0x00d3:
            r8 = move-exception
            android.support.v4.os.TraceCompat.endSection()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.BitmapEncoder.encode(com.bumptech.glide.load.engine.Resource, java.io.File, com.bumptech.glide.load.Options):boolean");
    }

    private Bitmap.CompressFormat getFormat(Bitmap bitmap, Options options) {
        Bitmap.CompressFormat compressFormat = (Bitmap.CompressFormat) options.get(COMPRESSION_FORMAT);
        if (compressFormat != null) {
            return compressFormat;
        }
        if (bitmap.hasAlpha()) {
            return Bitmap.CompressFormat.PNG;
        }
        return Bitmap.CompressFormat.JPEG;
    }

    @Override // com.bumptech.glide.load.ResourceEncoder
    public EncodeStrategy getEncodeStrategy(Options options) {
        return EncodeStrategy.TRANSFORMED;
    }
}
