package com.nguyenhoanglam.imagepicker.ui.camera;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import com.nguyenhoanglam.imagepicker.helper.ImageHelper;
import com.nguyenhoanglam.imagepicker.model.Config;
import java.io.File;
import java.io.Serializable;
import java.util.Locale;

/* loaded from: classes.dex */
public class DefaultCameraModule implements CameraModule, Serializable {
    protected String imagePath;

    @Override // com.nguyenhoanglam.imagepicker.ui.camera.CameraModule
    public Intent getCameraIntent(Context context, Config config) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        new ImageHelper();
        File createImageFile = ImageHelper.createImageFile(config.getSavePath());
        if (createImageFile == null) {
            return null;
        }
        Context applicationContext = context.getApplicationContext();
        Uri uriForFile = FileProvider.getUriForFile(applicationContext, String.format(Locale.ENGLISH, "%s%s", applicationContext.getPackageName(), ".fileprovider"), createImageFile);
        this.imagePath = createImageFile.getAbsolutePath();
        intent.putExtra("output", uriForFile);
        ImageHelper.grantAppPermission(context, intent, uriForFile);
        return intent;
    }

    @Override // com.nguyenhoanglam.imagepicker.ui.camera.CameraModule
    public void getImage(final Context context, Intent intent, final OnImageReadyListener onImageReadyListener) {
        if (onImageReadyListener == null) {
            throw new IllegalStateException("OnImageReadyListener must not be null");
        } else if (this.imagePath == null) {
            onImageReadyListener.onImageReady(null);
        } else {
            final Uri parse = Uri.parse(new File(this.imagePath).toString());
            if (parse != null) {
                MediaScannerConnection.scanFile(context.getApplicationContext(), new String[]{parse.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.nguyenhoanglam.imagepicker.ui.camera.DefaultCameraModule.1
                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                    public void onScanCompleted(String str, Uri uri) {
                        if (str != null) {
                            str = DefaultCameraModule.this.imagePath;
                        }
                        onImageReadyListener.onImageReady(ImageHelper.singleListFromPath(str));
                        ImageHelper.revokeAppPermission(context, parse);
                    }
                });
            }
        }
    }
}
