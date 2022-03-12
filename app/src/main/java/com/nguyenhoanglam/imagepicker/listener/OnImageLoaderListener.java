package com.nguyenhoanglam.imagepicker.listener;

import com.nguyenhoanglam.imagepicker.model.Folder;
import com.nguyenhoanglam.imagepicker.model.Image;
import java.util.List;

/* loaded from: classes.dex */
public interface OnImageLoaderListener {
    void onFailed(Throwable th);

    void onImageLoaded(List<Image> list, List<Folder> list2);
}
