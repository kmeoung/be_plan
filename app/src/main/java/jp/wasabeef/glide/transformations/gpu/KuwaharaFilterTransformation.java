package jp.wasabeef.glide.transformations.gpu;

import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;

/* loaded from: classes.dex */
public class KuwaharaFilterTransformation extends GPUFilterTransformation {
    private int radius;

    public KuwaharaFilterTransformation() {
        this(25);
    }

    public KuwaharaFilterTransformation(int i) {
        super(new GPUImageKuwaharaFilter());
        this.radius = i;
        ((GPUImageKuwaharaFilter) getFilter()).setRadius(this.radius);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "KuwaharaFilterTransformation(radius=" + this.radius + ")";
    }
}
