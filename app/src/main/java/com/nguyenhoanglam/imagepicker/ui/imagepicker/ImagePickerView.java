package com.nguyenhoanglam.imagepicker.ui.imagepicker;

import com.nguyenhoanglam.imagepicker.model.Folder;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.common.MvpView;
import java.util.List;

/* loaded from: classes.dex */
public interface ImagePickerView extends MvpView {
    void finishPickImages(List<Image> list);

    void showCapturedImage(List<Image> list);

    void showEmpty();

    void showError(Throwable th);

    void showFetchCompleted(List<Image> list, List<Folder> list2);

    void showLoading(boolean z);
}
