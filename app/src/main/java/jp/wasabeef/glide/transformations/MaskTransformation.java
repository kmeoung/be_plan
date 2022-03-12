package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import jp.wasabeef.glide.transformations.internal.Utils;

/* loaded from: classes.dex */
public class MaskTransformation extends BitmapTransformation {
    private static Paint paint = new Paint();
    private int maskId;

    static {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    public MaskTransformation(int i) {
        this.maskId = i;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmap2 = bitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        bitmap2.setHasAlpha(true);
        Drawable maskDrawable = Utils.getMaskDrawable(context.getApplicationContext(), this.maskId);
        Canvas canvas = new Canvas(bitmap2);
        maskDrawable.setBounds(0, 0, width, height);
        maskDrawable.draw(canvas);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return bitmap2;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "MaskTransformation(maskId=" + this.maskId + ")";
    }
}
