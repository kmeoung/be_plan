package com.nguyenhoanglam.imagepicker.ui.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.helper.CameraHelper;
import com.nguyenhoanglam.imagepicker.helper.LogHelper;
import com.nguyenhoanglam.imagepicker.helper.PermissionHelper;
import com.nguyenhoanglam.imagepicker.helper.PreferenceHelper;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.widget.SnackBarView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CameraActivty extends AppCompatActivity implements CameraView {
    private Config config;
    private CameraPresenter presenter;
    private SnackBarView snackBar;
    private final String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    private LogHelper logger = LogHelper.getInstance();
    private boolean isOpeningCamera = false;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        this.config = (Config) intent.getParcelableExtra(Config.EXTRA_CONFIG);
        if (this.config.isKeepScreenOn()) {
            getWindow().addFlags(128);
        }
        setContentView(R.layout.imagepicker_activity_camera);
        this.snackBar = (SnackBarView) findViewById(R.id.snackbar);
        this.presenter = new CameraPresenter();
        this.presenter.attachView(this);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (PermissionHelper.hasSelfPermissions(this, this.permissions) && this.isOpeningCamera) {
            this.isOpeningCamera = false;
        } else if (!this.snackBar.isShowing()) {
            captureImageWithPermission();
        }
    }

    private void captureImageWithPermission() {
        if (PermissionHelper.hasSelfPermissions(this, this.permissions)) {
            captureImage();
            return;
        }
        this.logger.w("Camera permission is not granted. Requesting permission");
        requestCameraPermission();
    }

    private void captureImage() {
        if (!CameraHelper.checkCameraAvailability(this)) {
            finish();
            return;
        }
        this.presenter.captureImage(this, this.config, 101);
        this.isOpeningCamera = true;
    }

    private void requestCameraPermission() {
        this.logger.w("Write External permission is not granted. Requesting permission");
        boolean hasSelfPermission = PermissionHelper.hasSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        boolean hasSelfPermission2 = PermissionHelper.hasSelfPermission(this, "android.permission.CAMERA");
        boolean z = true;
        z = !hasSelfPermission && !PermissionHelper.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE") && !PreferenceHelper.isFirstTimeAskingPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        if (hasSelfPermission2 || PermissionHelper.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA") || PreferenceHelper.isFirstTimeAskingPermission(this, "android.permission.CAMERA")) {
        }
        ArrayList arrayList = new ArrayList();
        if (!z) {
            if (!hasSelfPermission) {
                arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
                PreferenceHelper.firstTimeAskingPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE", false);
            }
            if (!hasSelfPermission2) {
                arrayList.add("android.permission.CAMERA");
                PreferenceHelper.firstTimeAskingPermission(this, "android.permission.CAMERA", false);
            }
            PermissionHelper.requestAllPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), 103);
            return;
        }
        this.snackBar.show(R.string.imagepicker_msg_no_write_external_storage_camera_permission, new View.OnClickListener() { // from class: com.nguyenhoanglam.imagepicker.ui.camera.CameraActivty.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PermissionHelper.openAppSettings(CameraActivty.this);
            }
        });
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i != 103) {
            LogHelper logHelper = this.logger;
            logHelper.d("Got unexpected permission result: " + i);
            super.onRequestPermissionsResult(i, strArr, iArr);
            finish();
        } else if (PermissionHelper.hasGranted(iArr)) {
            this.logger.d("Camera permission granted");
            captureImage();
        } else {
            LogHelper logHelper2 = this.logger;
            StringBuilder sb = new StringBuilder();
            sb.append("Permission not granted: results len = ");
            sb.append(iArr.length);
            sb.append(" Result code = ");
            boolean z = false;
            sb.append(iArr.length > 0 ? Integer.valueOf(iArr[0]) : "(empty)");
            logHelper2.e(sb.toString());
            int length = iArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                } else if (PermissionHelper.hasGranted(iArr[i2])) {
                    z = true;
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                this.snackBar.show(R.string.imagepicker_msg_no_write_external_storage_camera_permission, new View.OnClickListener() { // from class: com.nguyenhoanglam.imagepicker.ui.camera.CameraActivty.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        PermissionHelper.openAppSettings(CameraActivty.this);
                    }
                });
            } else {
                finish();
            }
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 101 && i2 == -1) {
            this.presenter.finishCaptureImage(this, intent, this.config);
            return;
        }
        setResult(0, new Intent());
        finish();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        setResult(0);
        finish();
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (this.presenter != null) {
            this.presenter.detachView();
        }
    }

    @Override // com.nguyenhoanglam.imagepicker.ui.camera.CameraView
    public void finishPickImages(List<Image> list) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Config.EXTRA_IMAGES, (ArrayList) list);
        setResult(-1, intent);
        finish();
    }
}
