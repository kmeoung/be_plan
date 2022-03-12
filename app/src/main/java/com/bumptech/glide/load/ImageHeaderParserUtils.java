package com.bumptech.glide.load;

import android.support.annotation.Nullable;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

/* loaded from: classes.dex */
public final class ImageHeaderParserUtils {
    private static final int MARK_POSITION = 5242880;

    private ImageHeaderParserUtils() {
    }

    public static ImageHeaderParser.ImageType getType(List<ImageHeaderParser> list, @Nullable InputStream inputStream, ArrayPool arrayPool) throws IOException {
        if (inputStream == null) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
        if (!inputStream.markSupported()) {
            inputStream = new RecyclableBufferedInputStream(inputStream, arrayPool);
        }
        inputStream.mark(MARK_POSITION);
        for (ImageHeaderParser imageHeaderParser : list) {
            try {
                ImageHeaderParser.ImageType type = imageHeaderParser.getType(inputStream);
                if (type != ImageHeaderParser.ImageType.UNKNOWN) {
                    return type;
                }
            } finally {
                inputStream.reset();
            }
        }
        return ImageHeaderParser.ImageType.UNKNOWN;
    }

    public static ImageHeaderParser.ImageType getType(List<ImageHeaderParser> list, @Nullable ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer == null) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
        for (ImageHeaderParser imageHeaderParser : list) {
            ImageHeaderParser.ImageType type = imageHeaderParser.getType(byteBuffer);
            if (type != ImageHeaderParser.ImageType.UNKNOWN) {
                return type;
            }
        }
        return ImageHeaderParser.ImageType.UNKNOWN;
    }

    public static int getOrientation(List<ImageHeaderParser> list, @Nullable InputStream inputStream, ArrayPool arrayPool) throws IOException {
        if (inputStream == null) {
            return -1;
        }
        if (!inputStream.markSupported()) {
            inputStream = new RecyclableBufferedInputStream(inputStream, arrayPool);
        }
        inputStream.mark(MARK_POSITION);
        for (ImageHeaderParser imageHeaderParser : list) {
            try {
                int orientation = imageHeaderParser.getOrientation(inputStream, arrayPool);
                if (orientation != -1) {
                    return orientation;
                }
            } finally {
                inputStream.reset();
            }
        }
        return -1;
    }
}
