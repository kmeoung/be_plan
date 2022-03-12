package jp.wasabeef.glide.transformations.gpu;

import android.graphics.PointF;
import java.util.Arrays;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;

/* loaded from: classes.dex */
public class VignetteFilterTransformation extends GPUFilterTransformation {
    private PointF center;
    private float[] vignetteColor;
    private float vignetteEnd;
    private float vignetteStart;

    public VignetteFilterTransformation() {
        this(new PointF(0.5f, 0.5f), new float[]{0.0f, 0.0f, 0.0f}, 0.0f, 0.75f);
    }

    public VignetteFilterTransformation(PointF pointF, float[] fArr, float f, float f2) {
        super(new GPUImageVignetteFilter());
        this.center = pointF;
        this.vignetteColor = fArr;
        this.vignetteStart = f;
        this.vignetteEnd = f2;
        GPUImageVignetteFilter gPUImageVignetteFilter = (GPUImageVignetteFilter) getFilter();
        gPUImageVignetteFilter.setVignetteCenter(this.center);
        gPUImageVignetteFilter.setVignetteColor(this.vignetteColor);
        gPUImageVignetteFilter.setVignetteStart(this.vignetteStart);
        gPUImageVignetteFilter.setVignetteEnd(this.vignetteEnd);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "VignetteFilterTransformation(center=" + this.center.toString() + ",color=" + Arrays.toString(this.vignetteColor) + ",start=" + this.vignetteStart + ",end=" + this.vignetteEnd + ")";
    }
}
