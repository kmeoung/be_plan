package com.nguyenhoanglam.imagepicker.helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.model.SavePath;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes.dex */
public class ImageHelper {
    private static final String TAG = "ImageHelper";

    public static File createImageFile(SavePath savePath) {
        String path = savePath.getPath();
        File file = savePath.isFullPath() ? new File(path) : new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), path);
        if (file.exists() || file.mkdirs()) {
            String str = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            try {
                return File.createTempFile(str, ".jpg", file);
            } catch (IOException unused) {
                Log.d(TAG, "Oops! Failed create " + str + " file");
                return null;
            }
        } else {
            Log.d(TAG, "Oops! Failed create " + path);
            return null;
        }
    }

    public static String getNameFromFilePath(String str) {
        return str.contains(File.separator) ? str.substring(str.lastIndexOf(File.separator) + 1) : str;
    }

    public static void grantAppPermission(Context context, Intent intent, Uri uri) {
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 65536)) {
            context.grantUriPermission(resolveInfo.activityInfo.packageName, uri, 3);
        }
    }

    public static void revokeAppPermission(Context context, Uri uri) {
        context.revokeUriPermission(uri, 3);
    }

    public static List<Image> singleListFromPath(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Image(0L, getNameFromFilePath(str), str));
        return arrayList;
    }

    public static boolean isGifFormat(Image image) {
        return image.getPath().substring(image.getPath().lastIndexOf(".") + 1, image.getPath().length()).equalsIgnoreCase("gif");
    }
}
