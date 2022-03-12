package jp.wasabeef.glide.transformations.gpu;

import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;

/* loaded from: classes.dex */
public class InvertFilterTransformation extends GPUFilterTransformation {
    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "InvertFilterTransformation()";
    }

    public InvertFilterTransformation() {
        super(new GPUImageColorInvertFilter());
    }
}
