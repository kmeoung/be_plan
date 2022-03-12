package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/* loaded from: classes.dex */
public class DrawableImageViewTarget extends ImageViewTarget<Drawable> {
    public DrawableImageViewTarget(ImageView imageView) {
        super(imageView);
    }

    public DrawableImageViewTarget(ImageView imageView, boolean z) {
        super(imageView, z);
    }

    public void setResource(@Nullable Drawable drawable) {
        ((ImageView) this.view).setImageDrawable(drawable);
    }
}
