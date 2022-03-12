package jp.wasabeef.glide.transformations.gpu;

import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;

/* loaded from: classes.dex */
public class ToonFilterTransformation extends GPUFilterTransformation {
    private float quantizationLevels;
    private float threshold;

    public ToonFilterTransformation() {
        this(0.2f, 10.0f);
    }

    public ToonFilterTransformation(float f, float f2) {
        super(new GPUImageToonFilter());
        this.threshold = f;
        this.quantizationLevels = f2;
        GPUImageToonFilter gPUImageToonFilter = (GPUImageToonFilter) getFilter();
        gPUImageToonFilter.setThreshold(this.threshold);
        gPUImageToonFilter.setQuantizationLevels(this.quantizationLevels);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "ToonFilterTransformation(threshold=" + this.threshold + ",quantizationLevels=" + this.quantizationLevels + ")";
    }
}
