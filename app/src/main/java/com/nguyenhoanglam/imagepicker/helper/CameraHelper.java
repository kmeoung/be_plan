package com.nguyenhoanglam.imagepicker.helper;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.nguyenhoanglam.imagepicker.R;

/* loaded from: classes.dex */
public class CameraHelper {
    public static boolean checkCameraAvailability(Context context) {
        boolean z = new Intent("android.media.action.IMAGE_CAPTURE").resolveActivity(context.getPackageManager()) != null;
        if (!z) {
            Context applicationContext = context.getApplicationContext();
            Toast.makeText(applicationContext, applicationContext.getString(R.string.imagepicker_error_no_camera), 1).show();
        }
        return z;
    }
}
