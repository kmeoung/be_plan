package com.nguyenhoanglam.imagepicker.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/* loaded from: classes.dex */
public class PermissionHelper {

    /* loaded from: classes.dex */
    public interface PermissionAskListener {
        void onNeedPermission();

        void onPermissionDisabled();

        void onPermissionGranted();

        void onPermissionPreviouslyDenied();
    }

    public static boolean hasGranted(int i) {
        return i == 0;
    }

    public static void checkPermission(Activity activity, String str, PermissionAskListener permissionAskListener) {
        if (hasSelfPermission(activity, str)) {
            permissionAskListener.onPermissionGranted();
        } else if (shouldShowRequestPermissionRationale(activity, str)) {
            permissionAskListener.onPermissionPreviouslyDenied();
        } else if (PreferenceHelper.isFirstTimeAskingPermission(activity, str)) {
            PreferenceHelper.firstTimeAskingPermission(activity, str, false);
            permissionAskListener.onNeedPermission();
        } else {
            permissionAskListener.onPermissionDisabled();
        }
    }

    public static void openAppSettings(Activity activity) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", activity.getPackageName(), null));
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(268435456);
        intent.addFlags(1073741824);
        intent.addFlags(8388608);
        activity.startActivity(intent);
    }

    public static String[] asArray(@NonNull String... strArr) {
        if (strArr.length == 0) {
            throw new IllegalArgumentException("There is no given permission");
        }
        String[] strArr2 = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr2[i] = strArr[i];
        }
        return strArr2;
    }

    public static boolean hasGranted(int[] iArr) {
        for (int i : iArr) {
            if (!hasGranted(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasSelfPermission(Context context, String str) {
        if (shouldAskPermission()) {
            return permissionHasGranted(context, str);
        }
        return true;
    }

    public static boolean hasSelfPermissions(Context context, String[] strArr) {
        if (!shouldAskPermission()) {
            return true;
        }
        for (String str : strArr) {
            if (!permissionHasGranted(context, str)) {
                return false;
            }
        }
        return true;
    }

    public static void requestAllPermissions(@NonNull Activity activity, @NonNull String[] strArr, int i) {
        if (shouldAskPermission()) {
            internalRequestPermissions(activity, strArr, i);
        }
    }

    @TargetApi(23)
    private static void internalRequestPermissions(Activity activity, String[] strArr, int i) {
        if (activity == null) {
            throw new IllegalArgumentException("Given activity is null.");
        }
        activity.requestPermissions(strArr, i);
    }

    @TargetApi(23)
    private static boolean permissionHasGranted(Context context, String str) {
        return hasGranted(context.checkSelfPermission(str));
    }

    private static boolean shouldAskPermission() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String str) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, str);
    }
}
