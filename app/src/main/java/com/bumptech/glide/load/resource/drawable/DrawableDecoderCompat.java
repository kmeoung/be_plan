package com.bumptech.glide.load.resource.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.content.res.AppCompatResources;

/* loaded from: classes.dex */
public final class DrawableDecoderCompat {
    private static volatile boolean shouldCallAppCompatResources = true;

    private DrawableDecoderCompat() {
    }

    public static Drawable getDrawable(Context context, @DrawableRes int i) {
        return getDrawable(context, i, null);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int i, @Nullable Resources.Theme theme) {
        try {
            if (shouldCallAppCompatResources) {
                return loadDrawableV7(context, i);
            }
        } catch (NoClassDefFoundError unused) {
            shouldCallAppCompatResources = false;
        }
        if (theme == null) {
            theme = context.getTheme();
        }
        return loadDrawableV4(context, i, theme);
    }

    private static Drawable loadDrawableV7(Context context, @DrawableRes int i) {
        return AppCompatResources.getDrawable(context, i);
    }

    private static Drawable loadDrawableV4(Context context, @DrawableRes int i, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getDrawable(context.getResources(), i, theme);
    }
}
