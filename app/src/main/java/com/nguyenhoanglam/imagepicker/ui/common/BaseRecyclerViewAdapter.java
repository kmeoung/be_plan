package com.nguyenhoanglam.imagepicker.ui.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageLoader;

/* loaded from: classes.dex */
public abstract class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    private final Context context;
    private final ImageLoader imageLoader;
    private final LayoutInflater inflater;

    public BaseRecyclerViewAdapter(Context context, ImageLoader imageLoader) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.imageLoader = imageLoader;
    }

    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }

    public Context getContext() {
        return this.context;
    }

    public LayoutInflater getInflater() {
        return this.inflater;
    }
}
