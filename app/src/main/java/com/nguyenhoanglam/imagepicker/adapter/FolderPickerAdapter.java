package com.nguyenhoanglam.imagepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.listener.OnFolderClickListener;
import com.nguyenhoanglam.imagepicker.model.Folder;
import com.nguyenhoanglam.imagepicker.ui.common.BaseRecyclerViewAdapter;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageLoader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class FolderPickerAdapter extends BaseRecyclerViewAdapter<FolderViewHolder> {
    private List<Folder> folders = new ArrayList();
    private OnFolderClickListener itemClickListener;

    public FolderPickerAdapter(Context context, ImageLoader imageLoader, OnFolderClickListener onFolderClickListener) {
        super(context, imageLoader);
        this.itemClickListener = onFolderClickListener;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public FolderViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FolderViewHolder(getInflater().inflate(R.layout.imagepicker_item_folder, viewGroup, false));
    }

    public void onBindViewHolder(FolderViewHolder folderViewHolder, int i) {
        final Folder folder = this.folders.get(i);
        getImageLoader().loadImage(folder.getImages().get(0).getPath(), folderViewHolder.image);
        folderViewHolder.name.setText(folder.getFolderName());
        int size = folder.getImages().size();
        TextView textView = folderViewHolder.count;
        textView.setText("" + size);
        folderViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.nguyenhoanglam.imagepicker.adapter.FolderPickerAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FolderPickerAdapter.this.itemClickListener.onFolderClick(folder);
            }
        });
    }

    public void setData(List<Folder> list) {
        if (list != null) {
            this.folders.clear();
            this.folders.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.folders.size();
    }

    /* loaded from: classes.dex */
    public static class FolderViewHolder extends RecyclerView.ViewHolder {
        private TextView count;
        private ImageView image;
        private TextView name;

        public FolderViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.image_folder_thumbnail);
            this.name = (TextView) view.findViewById(R.id.text_folder_name);
            this.count = (TextView) view.findViewById(R.id.text_photo_count);
        }
    }
}
