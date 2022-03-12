package jp.wasabeef.glide.transformations.gpu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.wasabeef.glide.transformations.BitmapTransformation;

/* loaded from: classes.dex */
public class GPUFilterTransformation extends BitmapTransformation {
    private GPUImageFilter gpuImageFilter;

    public GPUFilterTransformation(GPUImageFilter gPUImageFilter) {
        this.gpuImageFilter = gPUImageFilter;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        GPUImage gPUImage = new GPUImage(context);
        gPUImage.setImage(bitmap);
        gPUImage.setFilter(this.gpuImageFilter);
        return gPUImage.getBitmapWithFilterApplied();
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return getClass().getSimpleName();
    }

    public <T> T getFilter() {
        return (T) this.gpuImageFilter;
    }
}
