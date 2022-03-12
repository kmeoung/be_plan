package com.nguyenhoanglam.imagepicker.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.helper.ImageHelper;
import com.nguyenhoanglam.imagepicker.listener.OnImageClickListener;
import com.nguyenhoanglam.imagepicker.listener.OnImageSelectionListener;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.common.BaseRecyclerViewAdapter;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageLoader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ImagePickerAdapter extends BaseRecyclerViewAdapter<ImageViewHolder> {
    private OnImageSelectionListener imageSelectionListener;
    private OnImageClickListener itemClickListener;
    private List<Image> images = new ArrayList();
    private List<Image> selectedImages = new ArrayList();

    public ImagePickerAdapter(Context context, ImageLoader imageLoader, List<Image> list, OnImageClickListener onImageClickListener) {
        super(context, imageLoader);
        this.itemClickListener = onImageClickListener;
        if (list != null && !list.isEmpty()) {
            this.selectedImages.addAll(list);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ImageViewHolder(getInflater().inflate(R.layout.imagepicker_item_image, viewGroup, false));
    }

    public void onBindViewHolder(final ImageViewHolder imageViewHolder, final int i) {
        final Image image = this.images.get(i);
        final boolean isSelected = isSelected(image);
        getImageLoader().loadImage(image.getPath(), imageViewHolder.image);
        imageViewHolder.gifIndicator.setVisibility(ImageHelper.isGifFormat(image) ? 0 : 8);
        imageViewHolder.alphaView.setAlpha(isSelected ? 0.5f : 0.0f);
        imageViewHolder.container.setForeground(isSelected ? ContextCompat.getDrawable(getContext(), R.drawable.imagepicker_ic_selected) : null);
        imageViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.nguyenhoanglam.imagepicker.adapter.ImagePickerAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                boolean onImageClick = ImagePickerAdapter.this.itemClickListener.onImageClick(view, imageViewHolder.getAdapterPosition(), !isSelected);
                if (isSelected) {
                    ImagePickerAdapter.this.removeSelected(image, i);
                } else if (onImageClick) {
                    ImagePickerAdapter.this.addSelected(image, i);
                }
            }
        });
    }

    private boolean isSelected(Image image) {
        for (Image image2 : this.selectedImages) {
            if (image2.getPath().equals(image.getPath())) {
                return true;
            }
        }
        return false;
    }

    public void setOnImageSelectionListener(OnImageSelectionListener onImageSelectionListener) {
        this.imageSelectionListener = onImageSelectionListener;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.images.size();
    }

    public void setData(List<Image> list) {
        if (list != null) {
            this.images.clear();
            this.images.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addSelected(List<Image> list) {
        this.selectedImages.addAll(list);
        notifySelectionChanged();
    }

    public void addSelected(Image image, int i) {
        this.selectedImages.add(image);
        notifyItemChanged(i);
        notifySelectionChanged();
    }

    public void removeSelected(Image image, int i) {
        this.selectedImages.remove(image);
        notifyItemChanged(i);
        notifySelectionChanged();
    }

    public void removeAllSelected() {
        this.selectedImages.clear();
        notifyDataSetChanged();
        notifySelectionChanged();
    }

    private void notifySelectionChanged() {
        if (this.imageSelectionListener != null) {
            this.imageSelectionListener.onSelectionUpdate(this.selectedImages);
        }
    }

    public List<Image> getSelectedImages() {
        return this.selectedImages;
    }

    /* loaded from: classes.dex */
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private View alphaView;
        private FrameLayout container;
        private View gifIndicator;
        private ImageView image;

        public ImageViewHolder(View view) {
            super(view);
            this.container = (FrameLayout) view;
            this.image = (ImageView) view.findViewById(R.id.image_thumbnail);
            this.alphaView = view.findViewById(R.id.view_alpha);
            this.gifIndicator = view.findViewById(R.id.gif_indicator);
        }
    }
}
