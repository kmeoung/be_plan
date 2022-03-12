package jp.wasabeef.glide.transformations.gpu;

import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;

/* loaded from: classes.dex */
public class SketchFilterTransformation extends GPUFilterTransformation {
    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "SketchFilterTransformation()";
    }

    public SketchFilterTransformation() {
        super(new GPUImageSketchFilter());
    }
}
