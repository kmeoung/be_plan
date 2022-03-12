package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class VideoBitmapDecoder implements ResourceDecoder<ParcelFileDescriptor, Bitmap> {
    public static final long DEFAULT_FRAME = -1;
    private final BitmapPool bitmapPool;
    private final MediaMetadataRetrieverFactory factory;
    public static final Option<Long> TARGET_FRAME = Option.disk("com.bumptech.glide.load.resource.bitmap.VideoBitmapDecode.TargetFrame", -1L, new Option.CacheKeyUpdater<Long>() { // from class: com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder.1
        private final ByteBuffer buffer = ByteBuffer.allocate(8);

        public void update(byte[] bArr, Long l, MessageDigest messageDigest) {
            messageDigest.update(bArr);
            synchronized (this.buffer) {
                this.buffer.position(0);
                messageDigest.update(this.buffer.putLong(l.longValue()).array());
            }
        }
    });
    public static final Option<Integer> FRAME_OPTION = Option.disk("com.bumptech.glide.load.resource.bitmap.VideoBitmapDecode.FrameOption", null, new Option.CacheKeyUpdater<Integer>() { // from class: com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder.2
        private final ByteBuffer buffer = ByteBuffer.allocate(4);

        public void update(byte[] bArr, Integer num, MessageDigest messageDigest) {
            if (num != null) {
                messageDigest.update(bArr);
                synchronized (this.buffer) {
                    this.buffer.position(0);
                    messageDigest.update(this.buffer.putInt(num.intValue()).array());
                }
            }
        }
    });
    private static final MediaMetadataRetrieverFactory DEFAULT_FACTORY = new MediaMetadataRetrieverFactory();

    public boolean handles(ParcelFileDescriptor parcelFileDescriptor, Options options) {
        return true;
    }

    public VideoBitmapDecoder(Context context) {
        this(Glide.get(context).getBitmapPool());
    }

    public VideoBitmapDecoder(BitmapPool bitmapPool) {
        this(bitmapPool, DEFAULT_FACTORY);
    }

    VideoBitmapDecoder(BitmapPool bitmapPool, MediaMetadataRetrieverFactory mediaMetadataRetrieverFactory) {
        this.bitmapPool = bitmapPool;
        this.factory = mediaMetadataRetrieverFactory;
    }

    /* JADX WARN: Finally extract failed */
    public Resource<Bitmap> decode(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, Options options) throws IOException {
        Bitmap bitmap;
        long longValue = ((Long) options.get(TARGET_FRAME)).longValue();
        if (longValue >= 0 || longValue == -1) {
            Integer num = (Integer) options.get(FRAME_OPTION);
            MediaMetadataRetriever build = this.factory.build();
            try {
                try {
                    build.setDataSource(parcelFileDescriptor.getFileDescriptor());
                    if (longValue == -1) {
                        bitmap = build.getFrameAtTime();
                    } else if (num == null) {
                        bitmap = build.getFrameAtTime(longValue);
                    } else {
                        bitmap = build.getFrameAtTime(longValue, num.intValue());
                    }
                    build.release();
                    parcelFileDescriptor.close();
                    return BitmapResource.obtain(bitmap, this.bitmapPool);
                } catch (RuntimeException e) {
                    throw new IOException(e);
                }
            } catch (Throwable th) {
                build.release();
                throw th;
            }
        } else {
            throw new IllegalArgumentException("Requested frame must be non-negative, or DEFAULT_FRAME, given: " + longValue);
        }
    }

    /* loaded from: classes.dex */
    public static class MediaMetadataRetrieverFactory {
        MediaMetadataRetrieverFactory() {
        }

        public MediaMetadataRetriever build() {
            return new MediaMetadataRetriever();
        }
    }
}
