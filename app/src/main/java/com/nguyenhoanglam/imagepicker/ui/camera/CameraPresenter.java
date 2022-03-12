package com.nguyenhoanglam.imagepicker.ui.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.common.BasePresenter;
import java.util.List;

/* loaded from: classes.dex */
public class CameraPresenter extends BasePresenter<CameraView> {
    private CameraModule cameraModule = new DefaultCameraModule();

    public void captureImage(Activity activity, Config config, int i) {
        Context applicationContext = activity.getApplicationContext();
        Intent cameraIntent = this.cameraModule.getCameraIntent(activity, config);
        if (cameraIntent == null) {
            Toast.makeText(applicationContext, applicationContext.getString(R.string.imagepicker_error_create_image_file), 1).show();
        } else {
            activity.startActivityForResult(cameraIntent, i);
        }
    }

    public void finishCaptureImage(Context context, Intent intent, Config config) {
        this.cameraModule.getImage(context, intent, new OnImageReadyListener() { // from class: com.nguyenhoanglam.imagepicker.ui.camera.CameraPresenter.1
            @Override // com.nguyenhoanglam.imagepicker.ui.camera.OnImageReadyListener
            public void onImageReady(List<Image> list) {
                CameraPresenter.this.getView().finishPickImages(list);
            }
        });
    }
}
