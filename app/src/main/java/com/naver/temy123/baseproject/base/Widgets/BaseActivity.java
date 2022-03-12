package com.naver.temy123.baseproject.base.Widgets;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback;
import com.naver.temy123.baseproject.base.Interface.OnPermissionListener;
import com.naver.temy123.baseproject.base.Utils.PermissionHelper;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Response;

/* loaded from: classes.dex */
public abstract class BaseActivity extends AppCompatActivity implements OnHwNetworkCallback {
    private OnPermissionListener onPermissionListener;

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onFailed(Intent intent, IOException iOException) {
    }

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onNetworkResponsed(Call call, Intent intent, Response response, String str, int i) {
    }

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
    }

    public void requestPermission(String[] strArr, int i, OnPermissionListener onPermissionListener) {
        PermissionHelper.requestPermission(this, i, strArr, onPermissionListener);
    }

    public void requestPermission(String[] strArr, OnPermissionListener onPermissionListener) {
        PermissionHelper.requestPermission(this, strArr, onPermissionListener);
    }

    public void requestPermission(String[] strArr) {
        PermissionHelper.requestPermission(this, strArr, this.onPermissionListener);
    }

    public boolean isCheckPermissions(String str) {
        return PermissionHelper.isGrantedPermission(this, str);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (getOnPermissionListener() != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < strArr.length; i2++) {
                String str = strArr[i2];
                if (iArr[i2] == -1) {
                    arrayList2.add(str);
                } else if (iArr[i2] == 0) {
                    arrayList.add(str);
                }
            }
            getOnPermissionListener().onGranted(i, arrayList);
            getOnPermissionListener().onDenied(i, arrayList2);
            setOnPermissionListener(null);
        }
    }

    public void updateStatusbarTranslate() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().setFlags(67108864, 67108864);
        }
    }

    public void updateStatusbarTranslate(View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE));
            view.setLayoutParams(layoutParams);
            getWindow().setFlags(67108864, 67108864);
        }
    }

    public void updateNavigationBarTranslate() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().setFlags(134217728, 134217728);
        }
    }

    public void setStatusbarColor(int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(i);
        }
    }

    public void setNavigationbarColor(int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(i);
        }
    }

    public void replaceFragment(int i, Fragment fragment, boolean z) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(i, fragment);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.commit();
    }

    public void replaceFragment(int i, Fragment fragment, boolean z, int i2, int i3) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(i2, i3);
        beginTransaction.replace(i, fragment);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.commit();
    }

    public void replaceFragment(int i, Fragment fragment, boolean z, int i2, int i3, int i4, int i5) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(i2, i3, i4, i5);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.replace(i, fragment);
        beginTransaction.commit();
    }

    public OnPermissionListener getOnPermissionListener() {
        return this.onPermissionListener;
    }

    public void setOnPermissionListener(OnPermissionListener onPermissionListener) {
        this.onPermissionListener = onPermissionListener;
    }
}
