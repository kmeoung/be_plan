package jp.wasabeef.glide.transformations.gpu;

import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;

/* loaded from: classes.dex */
public class SepiaFilterTransformation extends GPUFilterTransformation {
    private float intensity;

    public SepiaFilterTransformation() {
        this(1.0f);
    }

    public SepiaFilterTransformation(float f) {
        super(new GPUImageSepiaFilter());
        this.intensity = f;
        ((GPUImageSepiaFilter) getFilter()).setIntensity(this.intensity);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "SepiaFilterTransformation(intensity=" + this.intensity + ")";
    }
}
