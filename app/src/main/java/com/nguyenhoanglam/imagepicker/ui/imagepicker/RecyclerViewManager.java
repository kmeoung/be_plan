package com.nguyenhoanglam.imagepicker.ui.imagepicker;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.adapter.FolderPickerAdapter;
import com.nguyenhoanglam.imagepicker.adapter.ImagePickerAdapter;
import com.nguyenhoanglam.imagepicker.listener.OnBackAction;
import com.nguyenhoanglam.imagepicker.listener.OnFolderClickListener;
import com.nguyenhoanglam.imagepicker.listener.OnImageClickListener;
import com.nguyenhoanglam.imagepicker.listener.OnImageSelectionListener;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Folder;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.widget.GridSpacingItemDecoration;
import java.util.List;

/* loaded from: classes.dex */
public class RecyclerViewManager {
    private Config config;
    private Context context;
    private FolderPickerAdapter folderAdapter;
    private int folderColumns;
    private Parcelable foldersState;
    private ImagePickerAdapter imageAdapter;
    private int imageColumns;
    private ImageLoader imageLoader = new ImageLoader();
    private boolean isShowingFolder;
    private GridSpacingItemDecoration itemOffsetDecoration;
    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private String title;

    public RecyclerViewManager(RecyclerView recyclerView, Config config, int i) {
        this.recyclerView = recyclerView;
        this.config = config;
        this.context = recyclerView.getContext();
        changeOrientation(i);
        this.isShowingFolder = config.isFolderMode();
    }

    public void setupAdapters(OnImageClickListener onImageClickListener, final OnFolderClickListener onFolderClickListener) {
        this.imageAdapter = new ImagePickerAdapter(this.context, this.imageLoader, (!this.config.isMultipleMode() || this.config.getSelectedImages().isEmpty()) ? null : this.config.getSelectedImages(), onImageClickListener);
        this.folderAdapter = new FolderPickerAdapter(this.context, this.imageLoader, new OnFolderClickListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.RecyclerViewManager.1
            @Override // com.nguyenhoanglam.imagepicker.listener.OnFolderClickListener
            public void onFolderClick(Folder folder) {
                RecyclerViewManager.this.foldersState = RecyclerViewManager.this.recyclerView.getLayoutManager().onSaveInstanceState();
                onFolderClickListener.onFolderClick(folder);
            }
        });
    }

    public void changeOrientation(int i) {
        this.imageColumns = i == 1 ? 3 : 5;
        this.folderColumns = i == 1 ? 2 : 4;
        int i2 = this.isShowingFolder ? this.folderColumns : this.imageColumns;
        this.layoutManager = new GridLayoutManager(this.context, i2);
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.setHasFixedSize(true);
        setItemDecoration(i2);
    }

    private void setItemDecoration(int i) {
        if (this.itemOffsetDecoration != null) {
            this.recyclerView.removeItemDecoration(this.itemOffsetDecoration);
        }
        this.itemOffsetDecoration = new GridSpacingItemDecoration(i, this.context.getResources().getDimensionPixelSize(R.dimen.imagepicker_item_padding), false);
        this.recyclerView.addItemDecoration(this.itemOffsetDecoration);
        this.layoutManager.setSpanCount(i);
    }

    public void setOnImageSelectionListener(OnImageSelectionListener onImageSelectionListener) {
        checkAdapterIsInitialized();
        this.imageAdapter.setOnImageSelectionListener(onImageSelectionListener);
    }

    public List<Image> getSelectedImages() {
        checkAdapterIsInitialized();
        return this.imageAdapter.getSelectedImages();
    }

    public void addSelectedImages(List<Image> list) {
        this.imageAdapter.addSelected(list);
    }

    private void checkAdapterIsInitialized() {
        if (this.imageAdapter == null) {
            throw new IllegalStateException("Must call setupAdapters first!");
        }
    }

    public boolean selectImage() {
        if (this.config.isMultipleMode()) {
            if (this.imageAdapter.getSelectedImages().size() >= this.config.getMaxSize()) {
                Toast.makeText(this.context, String.format(this.config.getLimitMessage(), Integer.valueOf(this.config.getMaxSize())), 0).show();
                return false;
            }
        } else if (this.imageAdapter.getItemCount() > 0) {
            this.imageAdapter.removeAllSelected();
        }
        return true;
    }

    public void handleBack(OnBackAction onBackAction) {
        if (!this.config.isFolderMode() || this.isShowingFolder) {
            onBackAction.onFinishImagePicker();
            return;
        }
        setFolderAdapter(null);
        onBackAction.onBackToFolder();
    }

    public void setImageAdapter(List<Image> list, String str) {
        this.imageAdapter.setData(list);
        setItemDecoration(this.imageColumns);
        this.recyclerView.setAdapter(this.imageAdapter);
        this.title = str;
        this.isShowingFolder = false;
    }

    public void setFolderAdapter(List<Folder> list) {
        this.folderAdapter.setData(list);
        setItemDecoration(this.folderColumns);
        this.recyclerView.setAdapter(this.folderAdapter);
        this.isShowingFolder = true;
        if (this.foldersState != null) {
            this.layoutManager.setSpanCount(this.folderColumns);
            this.recyclerView.getLayoutManager().onRestoreInstanceState(this.foldersState);
        }
    }

    public String getTitle() {
        if (this.isShowingFolder) {
            return this.config.getFolderTitle();
        }
        if (this.config.isFolderMode()) {
            return this.title;
        }
        return this.config.getImageTitle();
    }

    public boolean isShowDoneButton() {
        return this.config.isMultipleMode() && this.imageAdapter.getSelectedImages().size() > 0;
    }
}
