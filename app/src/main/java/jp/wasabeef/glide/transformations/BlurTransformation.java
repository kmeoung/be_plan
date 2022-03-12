package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.RSRuntimeException;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import jp.wasabeef.glide.transformations.internal.FastBlur;
import jp.wasabeef.glide.transformations.internal.RSBlur;

/* loaded from: classes.dex */
public class BlurTransformation extends BitmapTransformation {
    private static int DEFAULT_DOWN_SAMPLING = 1;
    private static int MAX_RADIUS = 25;
    private int radius;
    private int sampling;

    public BlurTransformation() {
        this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(int i) {
        this(i, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(int i, int i2) {
        this.radius = i;
        this.sampling = i2;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        Bitmap bitmap2 = bitmapPool.get(bitmap.getWidth() / this.sampling, bitmap.getHeight() / this.sampling, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        canvas.scale(1.0f / this.sampling, 1.0f / this.sampling);
        Paint paint = new Paint();
        paint.setFlags(2);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        if (Build.VERSION.SDK_INT < 18) {
            return FastBlur.blur(bitmap2, this.radius, true);
        }
        try {
            return RSBlur.blur(context, bitmap2, this.radius);
        } catch (RSRuntimeException unused) {
            return FastBlur.blur(bitmap2, this.radius, true);
        }
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "BlurTransformation(radius=" + this.radius + ", sampling=" + this.sampling + ")";
    }
}
