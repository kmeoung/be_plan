package jp.wasabeef.glide.transformations.gpu;

import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;

/* loaded from: classes.dex */
public class PixelationFilterTransformation extends GPUFilterTransformation {
    private float pixel;

    public PixelationFilterTransformation() {
        this(10.0f);
    }

    public PixelationFilterTransformation(float f) {
        super(new GPUImagePixelationFilter());
        this.pixel = f;
        ((GPUImagePixelationFilter) getFilter()).setPixel(this.pixel);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "PixelationFilterTransformation(pixel=" + this.pixel + ")";
    }
}
