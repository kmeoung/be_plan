package jp.wasabeef.glide.transformations.gpu;

import android.graphics.PointF;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;

/* loaded from: classes.dex */
public class SwirlFilterTransformation extends GPUFilterTransformation {
    private float angle;
    private PointF center;
    private float radius;

    public SwirlFilterTransformation() {
        this(0.5f, 1.0f, new PointF(0.5f, 0.5f));
    }

    public SwirlFilterTransformation(float f, float f2, PointF pointF) {
        super(new GPUImageSwirlFilter());
        this.radius = f;
        this.angle = f2;
        this.center = pointF;
        GPUImageSwirlFilter gPUImageSwirlFilter = (GPUImageSwirlFilter) getFilter();
        gPUImageSwirlFilter.setRadius(this.radius);
        gPUImageSwirlFilter.setAngle(this.angle);
        gPUImageSwirlFilter.setCenter(this.center);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation
    public String key() {
        return "SwirlFilterTransformation(radius=" + this.radius + ",angle=" + this.angle + ",center=" + this.center.toString() + ")";
    }
}
