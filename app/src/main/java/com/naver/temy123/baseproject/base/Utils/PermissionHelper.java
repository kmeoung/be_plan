package com.naver.temy123.baseproject.base.Utils;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import com.naver.temy123.baseproject.base.Interface.OnPermissionListener;
import com.naver.temy123.baseproject.base.Widgets.BaseActivity;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public class PermissionHelper {
    public static final int REQUEST_CODE = 10;

    public static void requestPermission(BaseActivity baseActivity, String[] strArr, OnPermissionListener onPermissionListener) {
        baseActivity.setOnPermissionListener(onPermissionListener);
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (!isGrantedPermission(baseActivity, str)) {
                arrayList.add(str);
            }
        }
        String[] strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
        if (strArr2 != null && strArr2.length > 0) {
            ActivityCompat.requestPermissions(baseActivity, strArr2, 10);
        } else if (onPermissionListener != null) {
            onPermissionListener.onGranted(10, Arrays.asList(strArr));
        }
    }

    public static void requestPermission(BaseActivity baseActivity, int i, String[] strArr, OnPermissionListener onPermissionListener) {
        baseActivity.setOnPermissionListener(onPermissionListener);
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(baseActivity, str)) {
                arrayList.add(str);
            }
        }
        String[] strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
        if (strArr2 != null && strArr2.length > 0) {
            ActivityCompat.requestPermissions(baseActivity, strArr2, i);
        } else if (onPermissionListener != null) {
            onPermissionListener.onGranted(10, Arrays.asList(strArr));
        }
    }

    public static boolean isGrantedPermission(Context context, String str) {
        return ActivityCompat.checkSelfPermission(context, str) == 0;
    }
}
