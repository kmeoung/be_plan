package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

/* loaded from: classes.dex */
public class CropSquareTransformation extends BitmapTransformation {
    private int size;

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        this.size = Math.max(i, i2);
        return TransformationUtils.centerCrop(bitmapPool, bitmap, this.size, this.size);
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "CropSquareTransformation(size=" + this.size + ")";
    }
}
