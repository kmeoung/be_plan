package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* loaded from: classes.dex */
public class DrawableThumbnailImageViewTarget extends ThumbnailImageViewTarget<Drawable> {
    public Drawable getDrawable(Drawable drawable) {
        return drawable;
    }

    public DrawableThumbnailImageViewTarget(ImageView imageView) {
        super(imageView);
    }

    public DrawableThumbnailImageViewTarget(ImageView imageView, boolean z) {
        super(imageView, z);
    }
}
