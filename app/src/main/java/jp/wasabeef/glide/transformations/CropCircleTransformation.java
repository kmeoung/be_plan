package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

@Deprecated
/* loaded from: classes.dex */
public class CropCircleTransformation extends BitmapTransformation {
    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "CropCircleTransformation()";
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        return TransformationUtils.circleCrop(bitmapPool, bitmap, i, i2);
    }
}
