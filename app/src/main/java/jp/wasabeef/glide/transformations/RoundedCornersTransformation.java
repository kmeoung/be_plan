package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

/* loaded from: classes.dex */
public class RoundedCornersTransformation extends BitmapTransformation {
    private CornerType cornerType;
    private int diameter;
    private int margin;
    private int radius;

    /* loaded from: classes.dex */
    public enum CornerType {
        ALL,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        OTHER_TOP_LEFT,
        OTHER_TOP_RIGHT,
        OTHER_BOTTOM_LEFT,
        OTHER_BOTTOM_RIGHT,
        DIAGONAL_FROM_TOP_LEFT,
        DIAGONAL_FROM_TOP_RIGHT
    }

    public RoundedCornersTransformation(int i, int i2) {
        this(i, i2, CornerType.ALL);
    }

    public RoundedCornersTransformation(int i, int i2, CornerType cornerType) {
        this.radius = i;
        this.diameter = this.radius * 2;
        this.margin = i2;
        this.cornerType = cornerType;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmap2 = bitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        bitmap2.setHasAlpha(true);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        drawRoundRect(canvas, paint, width, height);
        return bitmap2;
    }

    private void drawRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        float f3 = f - this.margin;
        float f4 = f2 - this.margin;
        switch (this.cornerType) {
            case ALL:
                canvas.drawRoundRect(new RectF(this.margin, this.margin, f3, f4), this.radius, this.radius, paint);
                return;
            case TOP_LEFT:
                drawTopLeftRoundRect(canvas, paint, f3, f4);
                return;
            case TOP_RIGHT:
                drawTopRightRoundRect(canvas, paint, f3, f4);
                return;
            case BOTTOM_LEFT:
                drawBottomLeftRoundRect(canvas, paint, f3, f4);
                return;
            case BOTTOM_RIGHT:
                drawBottomRightRoundRect(canvas, paint, f3, f4);
                return;
            case TOP:
                drawTopRoundRect(canvas, paint, f3, f4);
                return;
            case BOTTOM:
                drawBottomRoundRect(canvas, paint, f3, f4);
                return;
            case LEFT:
                drawLeftRoundRect(canvas, paint, f3, f4);
                return;
            case RIGHT:
                drawRightRoundRect(canvas, paint, f3, f4);
                return;
            case OTHER_TOP_LEFT:
                drawOtherTopLeftRoundRect(canvas, paint, f3, f4);
                return;
            case OTHER_TOP_RIGHT:
                drawOtherTopRightRoundRect(canvas, paint, f3, f4);
                return;
            case OTHER_BOTTOM_LEFT:
                drawOtherBottomLeftRoundRect(canvas, paint, f3, f4);
                return;
            case OTHER_BOTTOM_RIGHT:
                drawOtherBottomRightRoundRect(canvas, paint, f3, f4);
                return;
            case DIAGONAL_FROM_TOP_LEFT:
                drawDiagonalFromTopLeftRoundRect(canvas, paint, f3, f4);
                return;
            case DIAGONAL_FROM_TOP_RIGHT:
                drawDiagonalFromTopRightRoundRect(canvas, paint, f3, f4);
                return;
            default:
                canvas.drawRoundRect(new RectF(this.margin, this.margin, f3, f4), this.radius, this.radius, paint);
                return;
        }
    }

    private void drawTopLeftRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(this.margin, this.margin, this.margin + this.diameter, this.margin + this.diameter), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin + this.radius, this.margin + this.radius, f2), paint);
        canvas.drawRect(new RectF(this.margin + this.radius, this.margin, f, f2), paint);
    }

    private void drawTopRightRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(f - this.diameter, this.margin, f, this.margin + this.diameter), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin, f - this.radius, f2), paint);
        canvas.drawRect(new RectF(f - this.radius, this.margin + this.radius, f, f2), paint);
    }

    private void drawBottomLeftRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(this.margin, f2 - this.diameter, this.margin + this.diameter, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin, this.margin + this.diameter, f2 - this.radius), paint);
        canvas.drawRect(new RectF(this.margin + this.radius, this.margin, f, f2), paint);
    }

    private void drawBottomRightRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(f - this.diameter, f2 - this.diameter, f, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin, f - this.radius, f2), paint);
        canvas.drawRect(new RectF(f - this.radius, this.margin, f, f2 - this.radius), paint);
    }

    private void drawTopRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(this.margin, this.margin, f, this.margin + this.diameter), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin + this.radius, f, f2), paint);
    }

    private void drawBottomRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(this.margin, f2 - this.diameter, f, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin, f, f2 - this.radius), paint);
    }

    private void drawLeftRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(this.margin, this.margin, this.margin + this.diameter, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin + this.radius, this.margin, f, f2), paint);
    }

    private void drawRightRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(f - this.diameter, this.margin, f, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin, f - this.radius, f2), paint);
    }

    private void drawOtherTopLeftRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(this.margin, f2 - this.diameter, f, f2), this.radius, this.radius, paint);
        canvas.drawRoundRect(new RectF(f - this.diameter, this.margin, f, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin, f - this.radius, f2 - this.radius), paint);
    }

    private void drawOtherTopRightRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(this.margin, this.margin, this.margin + this.diameter, f2), this.radius, this.radius, paint);
        canvas.drawRoundRect(new RectF(this.margin, f2 - this.diameter, f, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin + this.radius, this.margin, f, f2 - this.radius), paint);
    }

    private void drawOtherBottomLeftRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(this.margin, this.margin, f, this.margin + this.diameter), this.radius, this.radius, paint);
        canvas.drawRoundRect(new RectF(f - this.diameter, this.margin, f, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin + this.radius, f - this.radius, f2), paint);
    }

    private void drawOtherBottomRightRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(this.margin, this.margin, f, this.margin + this.diameter), this.radius, this.radius, paint);
        canvas.drawRoundRect(new RectF(this.margin, this.margin, this.margin + this.diameter, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin + this.radius, this.margin + this.radius, f, f2), paint);
    }

    private void drawDiagonalFromTopLeftRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(this.margin, this.margin, this.margin + this.diameter, this.margin + this.diameter), this.radius, this.radius, paint);
        canvas.drawRoundRect(new RectF(f - this.diameter, f2 - this.diameter, f, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin + this.radius, f - this.diameter, f2), paint);
        canvas.drawRect(new RectF(this.margin + this.diameter, this.margin, f, f2 - this.radius), paint);
    }

    private void drawDiagonalFromTopRightRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawRoundRect(new RectF(f - this.diameter, this.margin, f, this.margin + this.diameter), this.radius, this.radius, paint);
        canvas.drawRoundRect(new RectF(this.margin, f2 - this.diameter, this.margin + this.diameter, f2), this.radius, this.radius, paint);
        canvas.drawRect(new RectF(this.margin, this.margin, f - this.radius, f2 - this.radius), paint);
        canvas.drawRect(new RectF(this.margin + this.radius, this.margin + this.radius, f, f2), paint);
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "RoundedTransformation(radius=" + this.radius + ", margin=" + this.margin + ", diameter=" + this.diameter + ", cornerType=" + this.cornerType.name() + ")";
    }
}
