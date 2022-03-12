package jp.wasabeef.glide.transformations.gpu;

import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;

/* loaded from: classes.dex */
public class ContrastFilterTransformation extends GPUFilterTransformation {
    private float contrast;

    public ContrastFilterTransformation() {
        this(1.0f);
    }

    public ContrastFilterTransformation(float f) {
        super(new GPUImageContrastFilter());
        this.contrast = f;
        ((GPUImageContrastFilter) getFilter()).setContrast(this.contrast);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "ContrastFilterTransformation(contrast=" + this.contrast + ")";
    }
}
