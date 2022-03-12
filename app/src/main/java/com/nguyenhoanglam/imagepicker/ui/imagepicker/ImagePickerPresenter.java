package com.nguyenhoanglam.imagepicker.ui.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.listener.OnImageLoaderListener;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Folder;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.camera.CameraModule;
import com.nguyenhoanglam.imagepicker.ui.camera.DefaultCameraModule;
import com.nguyenhoanglam.imagepicker.ui.camera.OnImageReadyListener;
import com.nguyenhoanglam.imagepicker.ui.common.BasePresenter;
import java.io.File;
import java.util.List;

/* loaded from: classes.dex */
public class ImagePickerPresenter extends BasePresenter<ImagePickerView> {
    private CameraModule cameraModule = new DefaultCameraModule();
    private Handler handler = new Handler(Looper.getMainLooper());
    private ImageFileLoader imageLoader;

    public ImagePickerPresenter(ImageFileLoader imageFileLoader) {
        this.imageLoader = imageFileLoader;
    }

    public void abortLoading() {
        this.imageLoader.abortLoadImages();
    }

    public void loadImages(boolean z) {
        if (isViewAttached()) {
            getView().showLoading(true);
            this.imageLoader.loadDeviceImages(z, new OnImageLoaderListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerPresenter.1
                @Override // com.nguyenhoanglam.imagepicker.listener.OnImageLoaderListener
                public void onImageLoaded(final List<Image> list, final List<Folder> list2) {
                    ImagePickerPresenter.this.handler.post(new Runnable() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerPresenter.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (ImagePickerPresenter.this.isViewAttached()) {
                                ImagePickerPresenter.this.getView().showFetchCompleted(list, list2);
                                if ((list2 != null ? list2 : list).isEmpty()) {
                                    ImagePickerPresenter.this.getView().showEmpty();
                                } else {
                                    ImagePickerPresenter.this.getView().showLoading(false);
                                }
                            }
                        }
                    });
                }

                @Override // com.nguyenhoanglam.imagepicker.listener.OnImageLoaderListener
                public void onFailed(final Throwable th) {
                    ImagePickerPresenter.this.handler.post(new Runnable() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerPresenter.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (ImagePickerPresenter.this.isViewAttached()) {
                                ImagePickerPresenter.this.getView().showError(th);
                            }
                        }
                    });
                }
            });
        }
    }

    public void captureImage(Activity activity, Config config, int i) {
        Context applicationContext = activity.getApplicationContext();
        Intent cameraIntent = this.cameraModule.getCameraIntent(activity, config);
        if (cameraIntent == null) {
            Toast.makeText(applicationContext, applicationContext.getString(R.string.imagepicker_error_create_image_file), 1).show();
        } else {
            activity.startActivityForResult(cameraIntent, i);
        }
    }

    public void finishCaptureImage(Context context, Intent intent, final Config config) {
        this.cameraModule.getImage(context, intent, new OnImageReadyListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerPresenter.2
            @Override // com.nguyenhoanglam.imagepicker.ui.camera.OnImageReadyListener
            public void onImageReady(List<Image> list) {
                if (!config.isMultipleMode()) {
                    ImagePickerPresenter.this.getView().finishPickImages(list);
                } else {
                    ImagePickerPresenter.this.getView().showCapturedImage(list);
                }
            }
        });
    }

    public void onDoneSelectImages(List<Image> list) {
        if (list != null && !list.isEmpty()) {
            int i = 0;
            while (i < list.size()) {
                if (!new File(list.get(i).getPath()).exists()) {
                    list.remove(i);
                    i--;
                }
                i++;
            }
        }
        getView().finishPickImages(list);
    }
}
