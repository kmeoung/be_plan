package jp.wasabeef.glide.transformations.gpu;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;

/* loaded from: classes.dex */
public class BrightnessFilterTransformation extends GPUFilterTransformation {
    private float brightness;

    public BrightnessFilterTransformation() {
        this(0.0f);
    }

    public BrightnessFilterTransformation(float f) {
        super(new GPUImageBrightnessFilter());
        this.brightness = f;
        ((GPUImageBrightnessFilter) getFilter()).setBrightness(this.brightness);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "BrightnessFilterTransformation(brightness=" + this.brightness + ")";
    }
}
