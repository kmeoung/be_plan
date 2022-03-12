package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

/* loaded from: classes.dex */
public class CropTransformation extends BitmapTransformation {
    private CropType cropType;
    private int height;
    private int width;

    /* loaded from: classes.dex */
    public enum CropType {
        TOP,
        CENTER,
        BOTTOM
    }

    public CropTransformation(int i, int i2) {
        this(i, i2, CropType.CENTER);
    }

    public CropTransformation(int i, int i2, CropType cropType) {
        this.cropType = CropType.CENTER;
        this.width = i;
        this.height = i2;
        this.cropType = cropType;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        this.width = this.width == 0 ? bitmap.getWidth() : this.width;
        this.height = this.height == 0 ? bitmap.getHeight() : this.height;
        Bitmap bitmap2 = bitmapPool.get(this.width, this.height, bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888);
        bitmap2.setHasAlpha(true);
        float max = Math.max(this.width / bitmap.getWidth(), this.height / bitmap.getHeight());
        float width = bitmap.getWidth() * max;
        float height = max * bitmap.getHeight();
        float f = (this.width - width) / 2.0f;
        float top = getTop(height);
        new Canvas(bitmap2).drawBitmap(bitmap, (Rect) null, new RectF(f, top, width + f, height + top), (Paint) null);
        return bitmap2;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "CropTransformation(width=" + this.width + ", height=" + this.height + ", cropType=" + this.cropType + ")";
    }

    private float getTop(float f) {
        switch (this.cropType) {
            case TOP:
                return 0.0f;
            case CENTER:
                return (this.height - f) / 2.0f;
            case BOTTOM:
                return this.height - f;
            default:
                return 0.0f;
        }
    }
}
