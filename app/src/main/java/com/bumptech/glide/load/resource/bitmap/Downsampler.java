package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/* loaded from: classes.dex */
public final class Downsampler {
    private static final int MARK_POSITION = 10485760;
    static final String TAG = "Downsampler";
    private static final String WBMP_MIME_TYPE = "image/vnd.wap.wbmp";
    private final BitmapPool bitmapPool;
    private final ArrayPool byteArrayPool;
    private final DisplayMetrics displayMetrics;
    private final HardwareConfigState hardwareConfigState = HardwareConfigState.getInstance();
    private final List<ImageHeaderParser> parsers;
    public static final Option<DecodeFormat> DECODE_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeFormat", DecodeFormat.DEFAULT);
    public static final Option<DownsampleStrategy> DOWNSAMPLE_STRATEGY = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.DownsampleStrategy", DownsampleStrategy.DEFAULT);
    public static final Option<Boolean> FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.FixBitmapSize", false);
    public static final Option<Boolean> ALLOW_HARDWARE_CONFIG = Option.memory("com.bumtpech.glide.load.resource.bitmap.Downsampler.AllowHardwareDecode", null);
    private static final String ICO_MIME_TYPE = "image/x-ico";
    private static final Set<String> NO_DOWNSAMPLE_PRE_N_MIME_TYPES = Collections.unmodifiableSet(new HashSet(Arrays.asList("image/vnd.wap.wbmp", ICO_MIME_TYPE)));
    private static final DecodeCallbacks EMPTY_CALLBACKS = new DecodeCallbacks() { // from class: com.bumptech.glide.load.resource.bitmap.Downsampler.1
        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks
        public void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) throws IOException {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks
        public void onObtainBounds() {
        }
    };
    private static final Set<ImageHeaderParser.ImageType> TYPES_THAT_USE_POOL_PRE_KITKAT = Collections.unmodifiableSet(EnumSet.of(ImageHeaderParser.ImageType.JPEG, ImageHeaderParser.ImageType.PNG_A, ImageHeaderParser.ImageType.PNG));
    private static final Queue<BitmapFactory.Options> OPTIONS_QUEUE = Util.createQueue(0);

    /* loaded from: classes.dex */
    public interface DecodeCallbacks {
        void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) throws IOException;

        void onObtainBounds();
    }

    private static int round(double d) {
        return (int) (d + 0.5d);
    }

    public boolean handles(InputStream inputStream) {
        return true;
    }

    public boolean handles(ByteBuffer byteBuffer) {
        return true;
    }

    public Downsampler(List<ImageHeaderParser> list, DisplayMetrics displayMetrics, BitmapPool bitmapPool, ArrayPool arrayPool) {
        this.parsers = list;
        this.displayMetrics = (DisplayMetrics) Preconditions.checkNotNull(displayMetrics);
        this.bitmapPool = (BitmapPool) Preconditions.checkNotNull(bitmapPool);
        this.byteArrayPool = (ArrayPool) Preconditions.checkNotNull(arrayPool);
    }

    public Resource<Bitmap> decode(InputStream inputStream, int i, int i2, Options options) throws IOException {
        return decode(inputStream, i, i2, options, EMPTY_CALLBACKS);
    }

    public Resource<Bitmap> decode(InputStream inputStream, int i, int i2, Options options, DecodeCallbacks decodeCallbacks) throws IOException {
        Preconditions.checkArgument(inputStream.markSupported(), "You must provide an InputStream that supports mark()");
        byte[] bArr = (byte[]) this.byteArrayPool.get(65536, byte[].class);
        BitmapFactory.Options defaultOptions = getDefaultOptions();
        defaultOptions.inTempStorage = bArr;
        DecodeFormat decodeFormat = (DecodeFormat) options.get(DECODE_FORMAT);
        DownsampleStrategy downsampleStrategy = (DownsampleStrategy) options.get(DOWNSAMPLE_STRATEGY);
        boolean booleanValue = ((Boolean) options.get(FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS)).booleanValue();
        boolean z = options.get(ALLOW_HARDWARE_CONFIG) != null && ((Boolean) options.get(ALLOW_HARDWARE_CONFIG)).booleanValue();
        if (decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE) {
            z = false;
        }
        try {
            return BitmapResource.obtain(decodeFromWrappedStreams(inputStream, defaultOptions, downsampleStrategy, decodeFormat, z, i, i2, booleanValue, decodeCallbacks), this.bitmapPool);
        } finally {
            releaseOptions(defaultOptions);
            this.byteArrayPool.put(bArr, byte[].class);
        }
    }

    private Bitmap decodeFromWrappedStreams(InputStream inputStream, BitmapFactory.Options options, DownsampleStrategy downsampleStrategy, DecodeFormat decodeFormat, boolean z, int i, int i2, boolean z2, DecodeCallbacks decodeCallbacks) throws IOException {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        long logTime = LogTime.getLogTime();
        int[] dimensions = getDimensions(inputStream, options, decodeCallbacks, this.bitmapPool);
        boolean z3 = false;
        int i8 = dimensions[0];
        int i9 = dimensions[1];
        String str = options.outMimeType;
        boolean z4 = (i8 == -1 || i9 == -1) ? false : z;
        int orientation = ImageHeaderParserUtils.getOrientation(this.parsers, inputStream, this.byteArrayPool);
        int exifOrientationDegrees = TransformationUtils.getExifOrientationDegrees(orientation);
        boolean isExifOrientationRequired = TransformationUtils.isExifOrientationRequired(orientation);
        if (i == Integer.MIN_VALUE) {
            i4 = i2;
            i3 = i8;
        } else {
            i4 = i2;
            i3 = i;
        }
        int i10 = i4 == Integer.MIN_VALUE ? i9 : i4;
        ImageHeaderParser.ImageType type = ImageHeaderParserUtils.getType(this.parsers, inputStream, this.byteArrayPool);
        calculateScaling(type, inputStream, decodeCallbacks, this.bitmapPool, downsampleStrategy, exifOrientationDegrees, i8, i9, i3, i10, options);
        calculateConfig(inputStream, decodeFormat, z4, isExifOrientationRequired, options, i3, i10);
        if (Build.VERSION.SDK_INT >= 19) {
            z3 = true;
        }
        if ((options.inSampleSize == 1 || z3) && shouldUsePool(type)) {
            if (i8 < 0 || i9 < 0 || !z2 || !z3) {
                float f = isScaling(options) ? options.inTargetDensity / options.inDensity : 1.0f;
                int i11 = options.inSampleSize;
                float f2 = i8;
                float f3 = i11;
                i7 = Math.round(((int) Math.ceil(f2 / f3)) * f);
                i6 = Math.round(((int) Math.ceil(i9 / f3)) * f);
                if (Log.isLoggable(TAG, 2)) {
                    Log.v(TAG, "Calculated target [" + i7 + "x" + i6 + "] for source [" + i8 + "x" + i9 + "], sampleSize: " + i11 + ", targetDensity: " + options.inTargetDensity + ", density: " + options.inDensity + ", density multiplier: " + f);
                }
            } else {
                i7 = i3;
                i6 = i10;
            }
            if (i7 > 0 && i6 > 0) {
                setInBitmap(options, this.bitmapPool, i7, i6);
            }
        }
        Bitmap decodeStream = decodeStream(inputStream, options, decodeCallbacks, this.bitmapPool);
        decodeCallbacks.onDecodeComplete(this.bitmapPool, decodeStream);
        if (Log.isLoggable(TAG, 2)) {
            i5 = orientation;
            logDecode(i8, i9, str, options, decodeStream, i, i2, logTime);
        } else {
            i5 = orientation;
        }
        Bitmap bitmap = null;
        if (decodeStream != null) {
            decodeStream.setDensity(this.displayMetrics.densityDpi);
            bitmap = TransformationUtils.rotateImageExif(this.bitmapPool, decodeStream, i5);
            if (!decodeStream.equals(bitmap)) {
                this.bitmapPool.put(decodeStream);
            }
        }
        return bitmap;
    }

    static void calculateScaling(ImageHeaderParser.ImageType imageType, InputStream inputStream, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool, DownsampleStrategy downsampleStrategy, int i, int i2, int i3, int i4, int i5, BitmapFactory.Options options) throws IOException {
        float f;
        int i6;
        int i7;
        int i8;
        int i9;
        if (i2 > 0 && i3 > 0) {
            if (i == 90 || i == 270) {
                f = downsampleStrategy.getScaleFactor(i3, i2, i4, i5);
            } else {
                f = downsampleStrategy.getScaleFactor(i2, i3, i4, i5);
            }
            if (f <= 0.0f) {
                throw new IllegalArgumentException("Cannot scale with factor: " + f + " from: " + downsampleStrategy + ", source: [" + i2 + "x" + i3 + "], target: [" + i4 + "x" + i5 + "]");
            }
            DownsampleStrategy.SampleSizeRounding sampleSizeRounding = downsampleStrategy.getSampleSizeRounding(i2, i3, i4, i5);
            if (sampleSizeRounding == null) {
                throw new IllegalArgumentException("Cannot round with null rounding");
            }
            float f2 = i2;
            float f3 = i3;
            int round = i2 / round(f * f2);
            int round2 = i3 / round(f * f3);
            if (sampleSizeRounding == DownsampleStrategy.SampleSizeRounding.MEMORY) {
                i6 = Math.max(round, round2);
            } else {
                i6 = Math.min(round, round2);
            }
            if (Build.VERSION.SDK_INT > 23 || !NO_DOWNSAMPLE_PRE_N_MIME_TYPES.contains(options.outMimeType)) {
                int max = Math.max(1, Integer.highestOneBit(i6));
                i7 = (sampleSizeRounding != DownsampleStrategy.SampleSizeRounding.MEMORY || ((float) max) >= 1.0f / f) ? max : max << 1;
            } else {
                i7 = 1;
            }
            options.inSampleSize = i7;
            if (imageType == ImageHeaderParser.ImageType.JPEG) {
                float min = Math.min(i7, 8);
                i8 = (int) Math.ceil(f2 / min);
                i9 = (int) Math.ceil(f3 / min);
                int i10 = i7 / 8;
                if (i10 > 0) {
                    i8 /= i10;
                    i9 /= i10;
                }
            } else if (imageType == ImageHeaderParser.ImageType.PNG || imageType == ImageHeaderParser.ImageType.PNG_A) {
                float f4 = i7;
                i8 = (int) Math.floor(f2 / f4);
                i9 = (int) Math.floor(f3 / f4);
            } else if (imageType == ImageHeaderParser.ImageType.WEBP || imageType == ImageHeaderParser.ImageType.WEBP_A) {
                if (Build.VERSION.SDK_INT >= 24) {
                    float f5 = i7;
                    i8 = Math.round(f2 / f5);
                    i9 = Math.round(f3 / f5);
                } else {
                    float f6 = i7;
                    i8 = (int) Math.floor(f2 / f6);
                    i9 = (int) Math.floor(f3 / f6);
                }
            } else if (i2 % i7 == 0 && i3 % i7 == 0) {
                i8 = i2 / i7;
                i9 = i3 / i7;
            } else {
                int[] dimensions = getDimensions(inputStream, options, decodeCallbacks, bitmapPool);
                i8 = dimensions[0];
                i9 = dimensions[1];
            }
            double scaleFactor = downsampleStrategy.getScaleFactor(i8, i9, i4, i5);
            if (Build.VERSION.SDK_INT >= 19) {
                options.inTargetDensity = adjustTargetDensityForError(scaleFactor);
                options.inDensity = getDensityMultiplier(scaleFactor);
            }
            if (isScaling(options)) {
                options.inScaled = true;
            } else {
                options.inTargetDensity = 0;
                options.inDensity = 0;
            }
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Calculate scaling, source: [" + i2 + "x" + i3 + "], target: [" + i4 + "x" + i5 + "], power of two scaled: [" + i8 + "x" + i9 + "], exact scale factor: " + f + ", power of 2 sample size: " + i7 + ", adjusted scale factor: " + scaleFactor + ", target density: " + options.inTargetDensity + ", density: " + options.inDensity);
            }
        } else if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Unable to determine dimensions for: " + imageType + " with target [" + i4 + "x" + i5 + "]");
        }
    }

    private static int adjustTargetDensityForError(double d) {
        int densityMultiplier = getDensityMultiplier(d);
        int round = round(densityMultiplier * d);
        return round((d / (round / densityMultiplier)) * round);
    }

    private static int getDensityMultiplier(double d) {
        if (d > 1.0d) {
            d = 1.0d / d;
        }
        return (int) Math.round(d * 2.147483647E9d);
    }

    private boolean shouldUsePool(ImageHeaderParser.ImageType imageType) throws IOException {
        if (Build.VERSION.SDK_INT >= 19) {
            return true;
        }
        return TYPES_THAT_USE_POOL_PRE_KITKAT.contains(imageType);
    }

    private void calculateConfig(InputStream inputStream, DecodeFormat decodeFormat, boolean z, boolean z2, BitmapFactory.Options options, int i, int i2) throws IOException {
        boolean z3;
        if (!this.hardwareConfigState.setHardwareConfigIfAllowed(i, i2, options, decodeFormat, z, z2)) {
            if (decodeFormat == DecodeFormat.PREFER_ARGB_8888 || decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE || Build.VERSION.SDK_INT == 16) {
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                return;
            }
            try {
                z3 = ImageHeaderParserUtils.getType(this.parsers, inputStream, this.byteArrayPool).hasAlpha();
            } catch (IOException e) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Cannot determine whether the image has alpha or not from header, format " + decodeFormat, e);
                }
                z3 = false;
            }
            options.inPreferredConfig = z3 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            if (options.inPreferredConfig == Bitmap.Config.RGB_565 || options.inPreferredConfig == Bitmap.Config.ARGB_4444 || options.inPreferredConfig == Bitmap.Config.ALPHA_8) {
                options.inDither = true;
            }
        }
    }

    private static int[] getDimensions(InputStream inputStream, BitmapFactory.Options options, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool) throws IOException {
        options.inJustDecodeBounds = true;
        decodeStream(inputStream, options, decodeCallbacks, bitmapPool);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static Bitmap decodeStream(InputStream inputStream, BitmapFactory.Options options, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool) throws IOException {
        if (options.inJustDecodeBounds) {
            inputStream.mark(MARK_POSITION);
        } else {
            decodeCallbacks.onObtainBounds();
        }
        int i = options.outWidth;
        int i2 = options.outHeight;
        String str = options.outMimeType;
        TransformationUtils.getBitmapDrawableLock().lock();
        try {
            try {
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
                TransformationUtils.getBitmapDrawableLock().unlock();
                if (options.inJustDecodeBounds) {
                    inputStream.reset();
                }
                return decodeStream;
            } catch (IllegalArgumentException e) {
                IOException newIoExceptionForInBitmapAssertion = newIoExceptionForInBitmapAssertion(e, i, i2, str, options);
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Failed to decode with inBitmap, trying again without Bitmap re-use", newIoExceptionForInBitmapAssertion);
                }
                if (options.inBitmap != null) {
                    try {
                        inputStream.reset();
                        bitmapPool.put(options.inBitmap);
                        options.inBitmap = null;
                        Bitmap decodeStream2 = decodeStream(inputStream, options, decodeCallbacks, bitmapPool);
                        TransformationUtils.getBitmapDrawableLock().unlock();
                        return decodeStream2;
                    } catch (IOException unused) {
                        throw newIoExceptionForInBitmapAssertion;
                    }
                } else {
                    throw newIoExceptionForInBitmapAssertion;
                }
            }
        } catch (Throwable th) {
            TransformationUtils.getBitmapDrawableLock().unlock();
            throw th;
        }
    }

    private static boolean isScaling(BitmapFactory.Options options) {
        return options.inTargetDensity > 0 && options.inDensity > 0 && options.inTargetDensity != options.inDensity;
    }

    private static void logDecode(int i, int i2, String str, BitmapFactory.Options options, Bitmap bitmap, int i3, int i4, long j) {
        Log.v(TAG, "Decoded " + getBitmapString(bitmap) + " from [" + i + "x" + i2 + "] " + str + " with inBitmap " + getInBitmapString(options) + " for [" + i3 + "x" + i4 + "], sample size: " + options.inSampleSize + ", density: " + options.inDensity + ", target density: " + options.inTargetDensity + ", thread: " + Thread.currentThread().getName() + ", duration: " + LogTime.getElapsedMillis(j));
    }

    private static String getInBitmapString(BitmapFactory.Options options) {
        return getBitmapString(options.inBitmap);
    }

    @TargetApi(19)
    @Nullable
    private static String getBitmapString(Bitmap bitmap) {
        String str;
        if (bitmap == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            str = " (" + bitmap.getAllocationByteCount() + ")";
        } else {
            str = "";
        }
        return "[" + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig() + str;
    }

    private static IOException newIoExceptionForInBitmapAssertion(IllegalArgumentException illegalArgumentException, int i, int i2, String str, BitmapFactory.Options options) {
        return new IOException("Exception decoding bitmap, outWidth: " + i + ", outHeight: " + i2 + ", outMimeType: " + str + ", inBitmap: " + getInBitmapString(options), illegalArgumentException);
    }

    @TargetApi(26)
    private static void setInBitmap(BitmapFactory.Options options, BitmapPool bitmapPool, int i, int i2) {
        if (Build.VERSION.SDK_INT < 26 || options.inPreferredConfig != Bitmap.Config.HARDWARE) {
            options.inBitmap = bitmapPool.getDirty(i, i2, options.inPreferredConfig);
        }
    }

    private static synchronized BitmapFactory.Options getDefaultOptions() {
        BitmapFactory.Options poll;
        synchronized (Downsampler.class) {
            synchronized (OPTIONS_QUEUE) {
                poll = OPTIONS_QUEUE.poll();
            }
            if (poll == null) {
                poll = new BitmapFactory.Options();
                resetOptions(poll);
            }
        }
        return poll;
    }

    private static void releaseOptions(BitmapFactory.Options options) {
        resetOptions(options);
        synchronized (OPTIONS_QUEUE) {
            OPTIONS_QUEUE.offer(options);
        }
    }

    private static void resetOptions(BitmapFactory.Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;
        options.inBitmap = null;
        options.inMutable = true;
    }
}
