package com.nguyenhoanglam.imagepicker.ui.camera;

import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.common.MvpView;
import java.util.List;

/* loaded from: classes.dex */
public interface CameraView extends MvpView {
    void finishPickImages(List<Image> list);
}
