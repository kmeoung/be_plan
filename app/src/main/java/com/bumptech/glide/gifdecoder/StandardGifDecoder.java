package com.bumptech.glide.gifdecoder;

import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.bumptech.glide.gifdecoder.GifDecoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;

/* loaded from: classes.dex */
public class StandardGifDecoder implements GifDecoder {
    private static final int BYTES_PER_INTEGER = 4;
    @ColorInt
    private static final int COLOR_TRANSPARENT_BLACK = 0;
    private static final int INITIAL_FRAME_POINTER = -1;
    private static final int MASK_INT_LOWEST_BYTE = 255;
    private static final int MAX_STACK_SIZE = 4096;
    private static final int NULL_CODE = -1;
    private static final String TAG = "StandardGifDecoder";
    private static final int WORK_BUFFER_SIZE = 16384;
    @ColorInt
    private int[] act;
    @NonNull
    private Bitmap.Config bitmapConfig;
    private GifDecoder.BitmapProvider bitmapProvider;
    private byte[] block;
    private int downsampledHeight;
    private int downsampledWidth;
    private int framePointer;
    private GifHeader header;
    private boolean isFirstFrameTransparent;
    private byte[] mainPixels;
    @ColorInt
    private int[] mainScratch;
    private GifHeaderParser parser;
    @ColorInt
    private final int[] pct;
    private byte[] pixelStack;
    private short[] prefix;
    private Bitmap previousImage;
    private ByteBuffer rawData;
    private int sampleSize;
    private boolean savePrevious;
    private int status;
    private byte[] suffix;
    @Nullable
    private byte[] workBuffer;
    private int workBufferPosition;
    private int workBufferSize;

    public StandardGifDecoder(GifDecoder.BitmapProvider bitmapProvider, GifHeader gifHeader, ByteBuffer byteBuffer) {
        this(bitmapProvider, gifHeader, byteBuffer, 1);
    }

    public StandardGifDecoder(GifDecoder.BitmapProvider bitmapProvider, GifHeader gifHeader, ByteBuffer byteBuffer, int i) {
        this(bitmapProvider);
        setData(gifHeader, byteBuffer, i);
    }

    public StandardGifDecoder(GifDecoder.BitmapProvider bitmapProvider) {
        this.pct = new int[256];
        this.workBufferSize = 0;
        this.workBufferPosition = 0;
        this.bitmapConfig = Bitmap.Config.ARGB_8888;
        this.bitmapProvider = bitmapProvider;
        this.header = new GifHeader();
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getWidth() {
        return this.header.width;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getHeight() {
        return this.header.height;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public ByteBuffer getData() {
        return this.rawData;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getStatus() {
        return this.status;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void advance() {
        this.framePointer = (this.framePointer + 1) % this.header.frameCount;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getDelay(int i) {
        if (i < 0 || i >= this.header.frameCount) {
            return -1;
        }
        return this.header.frames.get(i).delay;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getNextDelay() {
        if (this.header.frameCount <= 0 || this.framePointer < 0) {
            return 0;
        }
        return getDelay(this.framePointer);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getFrameCount() {
        return this.header.frameCount;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getCurrentFrameIndex() {
        return this.framePointer;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void resetFrameIndex() {
        this.framePointer = -1;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    @Deprecated
    public int getLoopCount() {
        if (this.header.loopCount == -1) {
            return 1;
        }
        return this.header.loopCount;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getNetscapeLoopCount() {
        return this.header.loopCount;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getTotalIterationCount() {
        if (this.header.loopCount == -1) {
            return 1;
        }
        if (this.header.loopCount == 0) {
            return 0;
        }
        return this.header.loopCount + 1;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getByteSize() {
        return this.rawData.limit() + this.mainPixels.length + (this.mainScratch.length * 4);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized Bitmap getNextFrame() {
        if (this.header.frameCount <= 0 || this.framePointer < 0) {
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                Log.d(str, "Unable to decode frame, frameCount=" + this.header.frameCount + ", framePointer=" + this.framePointer);
            }
            this.status = 1;
        }
        if (!(this.status == 1 || this.status == 2)) {
            this.status = 0;
            GifFrame gifFrame = this.header.frames.get(this.framePointer);
            int i = this.framePointer - 1;
            GifFrame gifFrame2 = i >= 0 ? this.header.frames.get(i) : null;
            this.act = gifFrame.lct != null ? gifFrame.lct : this.header.gct;
            if (this.act == null) {
                if (Log.isLoggable(TAG, 3)) {
                    String str2 = TAG;
                    Log.d(str2, "No valid color table found for frame #" + this.framePointer);
                }
                this.status = 1;
                return null;
            }
            if (gifFrame.transparency) {
                System.arraycopy(this.act, 0, this.pct, 0, this.act.length);
                this.act = this.pct;
                this.act[gifFrame.transIndex] = 0;
            }
            return setPixels(gifFrame, gifFrame2);
        }
        if (Log.isLoggable(TAG, 3)) {
            String str3 = TAG;
            Log.d(str3, "Unable to decode frame, status=" + this.status);
        }
        return null;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int read(InputStream inputStream, int i) {
        if (inputStream != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i > 0 ? i + 4096 : 16384);
                byte[] bArr = new byte[16384];
                while (true) {
                    int read = inputStream.read(bArr, 0, bArr.length);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.flush();
                read(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                Log.w(TAG, "Error reading data from stream", e);
            }
        } else {
            this.status = 2;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                Log.w(TAG, "Error closing stream", e2);
            }
        }
        return this.status;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void clear() {
        this.header = null;
        if (this.mainPixels != null) {
            this.bitmapProvider.release(this.mainPixels);
        }
        if (this.mainScratch != null) {
            this.bitmapProvider.release(this.mainScratch);
        }
        if (this.previousImage != null) {
            this.bitmapProvider.release(this.previousImage);
        }
        this.previousImage = null;
        this.rawData = null;
        this.isFirstFrameTransparent = false;
        if (this.block != null) {
            this.bitmapProvider.release(this.block);
        }
        if (this.workBuffer != null) {
            this.bitmapProvider.release(this.workBuffer);
        }
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized void setData(GifHeader gifHeader, byte[] bArr) {
        setData(gifHeader, ByteBuffer.wrap(bArr));
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized void setData(GifHeader gifHeader, ByteBuffer byteBuffer) {
        setData(gifHeader, byteBuffer, 1);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized void setData(GifHeader gifHeader, ByteBuffer byteBuffer, int i) {
        try {
            if (i <= 0) {
                throw new IllegalArgumentException("Sample size must be >=0, not: " + i);
            }
            int highestOneBit = Integer.highestOneBit(i);
            this.status = 0;
            this.header = gifHeader;
            this.isFirstFrameTransparent = false;
            this.framePointer = -1;
            this.rawData = byteBuffer.asReadOnlyBuffer();
            this.rawData.position(0);
            this.rawData.order(ByteOrder.LITTLE_ENDIAN);
            this.savePrevious = false;
            Iterator<GifFrame> it = gifHeader.frames.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().dispose == 3) {
                        this.savePrevious = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            this.sampleSize = highestOneBit;
            this.downsampledWidth = gifHeader.width / highestOneBit;
            this.downsampledHeight = gifHeader.height / highestOneBit;
            this.mainPixels = this.bitmapProvider.obtainByteArray(gifHeader.width * gifHeader.height);
            this.mainScratch = this.bitmapProvider.obtainIntArray(this.downsampledWidth * this.downsampledHeight);
        } catch (Throwable th) {
            throw th;
        }
    }

    private GifHeaderParser getHeaderParser() {
        if (this.parser == null) {
            this.parser = new GifHeaderParser();
        }
        return this.parser;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized int read(byte[] bArr) {
        this.header = getHeaderParser().setData(bArr).parseHeader();
        if (bArr != null) {
            setData(this.header, bArr);
        }
        return this.status;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void setDefaultBitmapConfig(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888 || config == Bitmap.Config.RGB_565) {
            this.bitmapConfig = config;
            return;
        }
        throw new IllegalArgumentException("Unsupported format: " + config + ", must be one of " + Bitmap.Config.ARGB_8888 + " or " + Bitmap.Config.RGB_565);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0048, code lost:
        if (r18.header.bgIndex == r19.transIndex) goto L_0x0051;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.graphics.Bitmap setPixels(com.bumptech.glide.gifdecoder.GifFrame r19, com.bumptech.glide.gifdecoder.GifFrame r20) {
        /*
            Method dump skipped, instructions count: 382
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.gifdecoder.StandardGifDecoder.setPixels(com.bumptech.glide.gifdecoder.GifFrame, com.bumptech.glide.gifdecoder.GifFrame):android.graphics.Bitmap");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0069, code lost:
        return 0;
     */
    @android.support.annotation.ColorInt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int averageColorsNear(int r10, int r11, int r12) {
        /*
            r9 = this;
            r0 = 0
            r1 = r10
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
        L_0x0007:
            int r7 = r9.sampleSize
            int r7 = r7 + r10
            if (r1 >= r7) goto L_0x0036
            byte[] r7 = r9.mainPixels
            int r7 = r7.length
            if (r1 >= r7) goto L_0x0036
            if (r1 >= r11) goto L_0x0036
            byte[] r7 = r9.mainPixels
            byte r7 = r7[r1]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int[] r8 = r9.act
            r7 = r8[r7]
            if (r7 == 0) goto L_0x0033
            int r8 = r7 >> 24
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r2 = r2 + r8
            int r8 = r7 >> 16
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r3 = r3 + r8
            int r8 = r7 >> 8
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r4 = r4 + r8
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r5 = r5 + r7
            int r6 = r6 + 1
        L_0x0033:
            int r1 = r1 + 1
            goto L_0x0007
        L_0x0036:
            int r10 = r10 + r12
            r12 = r10
        L_0x0038:
            int r1 = r9.sampleSize
            int r1 = r1 + r10
            if (r12 >= r1) goto L_0x0067
            byte[] r1 = r9.mainPixels
            int r1 = r1.length
            if (r12 >= r1) goto L_0x0067
            if (r12 >= r11) goto L_0x0067
            byte[] r1 = r9.mainPixels
            byte r1 = r1[r12]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int[] r7 = r9.act
            r1 = r7[r1]
            if (r1 == 0) goto L_0x0064
            int r7 = r1 >> 24
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r2 = r2 + r7
            int r7 = r1 >> 16
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r3 = r3 + r7
            int r7 = r1 >> 8
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r4 = r4 + r7
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r5 = r5 + r1
            int r6 = r6 + 1
        L_0x0064:
            int r12 = r12 + 1
            goto L_0x0038
        L_0x0067:
            if (r6 != 0) goto L_0x006a
            return r0
        L_0x006a:
            int r2 = r2 / r6
            int r10 = r2 << 24
            int r3 = r3 / r6
            int r11 = r3 << 16
            r10 = r10 | r11
            int r4 = r4 / r6
            int r11 = r4 << 8
            r10 = r10 | r11
            int r5 = r5 / r6
            r10 = r10 | r5
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.gifdecoder.StandardGifDecoder.averageColorsNear(int, int, int):int");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void decodeBitmapData(GifFrame gifFrame) {
        short s;
        this.workBufferSize = 0;
        this.workBufferPosition = 0;
        if (gifFrame != null) {
            this.rawData.position(gifFrame.bufferFrameStart);
        }
        int i = gifFrame == null ? this.header.width * this.header.height : gifFrame.ih * gifFrame.iw;
        if (this.mainPixels == null || this.mainPixels.length < i) {
            this.mainPixels = this.bitmapProvider.obtainByteArray(i);
        }
        if (this.prefix == null) {
            this.prefix = new short[4096];
        }
        if (this.suffix == null) {
            this.suffix = new byte[4096];
        }
        if (this.pixelStack == null) {
            this.pixelStack = new byte[FragmentTransaction.TRANSIT_FRAGMENT_OPEN];
        }
        int readByte = readByte();
        int i2 = 1;
        int i3 = 1 << readByte;
        int i4 = i3 + 1;
        int i5 = i3 + 2;
        int i6 = readByte + 1;
        int i7 = (1 << i6) - 1;
        for (int i8 = 0; i8 < i3; i8++) {
            this.prefix[i8] = 0;
            this.suffix[i8] = (byte) i8;
        }
        int i9 = -1;
        int i10 = i6;
        int i11 = i5;
        int i12 = i7;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int i21 = -1;
        while (true) {
            if (i13 >= i) {
                break;
            }
            int i22 = 3;
            if (i14 == 0) {
                i14 = readBlock();
                if (i14 <= 0) {
                    this.status = 3;
                    break;
                }
                i17 = 0;
            }
            i16 += (this.block[i17] & 255) << i18;
            int i23 = i18 + 8;
            i17 += i2;
            i14 += i9;
            int i24 = i13;
            int i25 = i15;
            while (i23 >= i10) {
                int i26 = i16 & i12;
                i16 >>= i10;
                i23 -= i10;
                if (i26 != i3) {
                    if (i26 > i11) {
                        this.status = i22;
                    } else if (i26 != i4) {
                        if (i21 == -1) {
                            i20++;
                            this.pixelStack[i20] = this.suffix[i26];
                            i21 = i26;
                            i19 = i21;
                        } else {
                            if (i26 >= i11) {
                                i20++;
                                i6 = i6;
                                this.pixelStack[i20] = (byte) i19;
                                s = i21;
                            } else {
                                i6 = i6;
                                s = i26;
                            }
                            while (s >= i3) {
                                i20++;
                                this.pixelStack[i20] = this.suffix[s];
                                s = this.prefix[s];
                                i23 = i23;
                            }
                            int i27 = this.suffix[s] & 255;
                            i20++;
                            byte b = (byte) i27;
                            this.pixelStack[i20] = b;
                            if (i11 < 4096) {
                                this.prefix[i11] = (short) i21;
                                this.suffix[i11] = b;
                                i11++;
                                if ((i11 & i12) == 0 && i11 < 4096) {
                                    i10++;
                                    i12 += i11;
                                }
                            }
                            while (i20 > 0) {
                                i25++;
                                i20--;
                                this.mainPixels[i25] = this.pixelStack[i20];
                                i24++;
                            }
                            i19 = i27;
                            i21 = i26;
                            i23 = i23;
                        }
                        i22 = 3;
                    }
                    i21 = i21;
                    i10 = i10;
                    i11 = i11;
                    i13 = i24;
                    i15 = i25;
                    i19 = i19;
                    i2 = 1;
                    i9 = -1;
                    i18 = i23;
                    break;
                }
                i10 = i6;
                i11 = i5;
                i12 = i7;
                i21 = -1;
                i9 = -1;
            }
            i21 = i21;
            i10 = i10;
            i11 = i11;
            i13 = i24;
            i15 = i25;
            i2 = 1;
            i19 = i19;
            i18 = i23;
            i6 = i6;
        }
        while (i15 < i) {
            this.mainPixels[i15] = 0;
            i15++;
        }
    }

    private void readChunkIfNeeded() {
        if (this.workBufferSize <= this.workBufferPosition) {
            if (this.workBuffer == null) {
                this.workBuffer = this.bitmapProvider.obtainByteArray(16384);
            }
            this.workBufferPosition = 0;
            this.workBufferSize = Math.min(this.rawData.remaining(), 16384);
            this.rawData.get(this.workBuffer, 0, this.workBufferSize);
        }
    }

    private int readByte() {
        try {
            readChunkIfNeeded();
            byte[] bArr = this.workBuffer;
            int i = this.workBufferPosition;
            this.workBufferPosition = i + 1;
            return bArr[i] & 255;
        } catch (Exception unused) {
            this.status = 1;
            return 0;
        }
    }

    private int readBlock() {
        int readByte = readByte();
        if (readByte > 0) {
            try {
                if (this.block == null) {
                    this.block = this.bitmapProvider.obtainByteArray(255);
                }
                int i = this.workBufferSize - this.workBufferPosition;
                if (i >= readByte) {
                    System.arraycopy(this.workBuffer, this.workBufferPosition, this.block, 0, readByte);
                    this.workBufferPosition += readByte;
                } else if (this.rawData.remaining() + i >= readByte) {
                    System.arraycopy(this.workBuffer, this.workBufferPosition, this.block, 0, i);
                    this.workBufferPosition = this.workBufferSize;
                    readChunkIfNeeded();
                    int i2 = readByte - i;
                    System.arraycopy(this.workBuffer, 0, this.block, i, i2);
                    this.workBufferPosition += i2;
                } else {
                    this.status = 1;
                }
            } catch (Exception e) {
                Log.w(TAG, "Error Reading Block", e);
                this.status = 1;
            }
        }
        return readByte;
    }

    private Bitmap getNextBitmap() {
        Bitmap obtain = this.bitmapProvider.obtain(this.downsampledWidth, this.downsampledHeight, this.isFirstFrameTransparent ? Bitmap.Config.ARGB_8888 : this.bitmapConfig);
        obtain.setHasAlpha(true);
        return obtain;
    }
}
