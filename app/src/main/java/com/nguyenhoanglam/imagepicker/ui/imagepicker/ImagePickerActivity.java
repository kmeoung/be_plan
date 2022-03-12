package com.nguyenhoanglam.imagepicker.ui.imagepicker;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.helper.CameraHelper;
import com.nguyenhoanglam.imagepicker.helper.LogHelper;
import com.nguyenhoanglam.imagepicker.helper.PermissionHelper;
import com.nguyenhoanglam.imagepicker.listener.OnBackAction;
import com.nguyenhoanglam.imagepicker.listener.OnFolderClickListener;
import com.nguyenhoanglam.imagepicker.listener.OnImageClickListener;
import com.nguyenhoanglam.imagepicker.listener.OnImageSelectionListener;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Folder;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.widget.ImagePickerToolbar;
import com.nguyenhoanglam.imagepicker.widget.ProgressWheel;
import com.nguyenhoanglam.imagepicker.widget.SnackBarView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ImagePickerActivity extends AppCompatActivity implements ImagePickerView {
    private Config config;
    private View emptyLayout;
    private Handler handler;
    private ContentObserver observer;
    private ImagePickerPresenter presenter;
    private ProgressWheel progressWheel;
    private RecyclerView recyclerView;
    private RecyclerViewManager recyclerViewManager;
    private SnackBarView snackBar;
    private ImagePickerToolbar toolbar;
    private LogHelper logger = LogHelper.getInstance();
    private OnImageClickListener imageClickListener = new OnImageClickListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.1
        @Override // com.nguyenhoanglam.imagepicker.listener.OnImageClickListener
        public boolean onImageClick(View view, int i, boolean z) {
            return ImagePickerActivity.this.recyclerViewManager.selectImage();
        }
    };
    private OnFolderClickListener folderClickListener = new OnFolderClickListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.2
        @Override // com.nguyenhoanglam.imagepicker.listener.OnFolderClickListener
        public void onFolderClick(Folder folder) {
            ImagePickerActivity.this.setImageAdapter(folder.getImages(), folder.getFolderName());
        }
    };
    private View.OnClickListener backClickListener = new View.OnClickListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.3
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ImagePickerActivity.this.onBackPressed();
        }
    };
    private View.OnClickListener cameraClickListener = new View.OnClickListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.4
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ImagePickerActivity.this.captureImageWithPermission();
        }
    };
    private View.OnClickListener doneClickListener = new View.OnClickListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.5
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ImagePickerActivity.this.onDone();
        }
    };

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
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
        setContentView(R.layout.imagepicker_activity_picker);
        setupView();
        setupComponents();
        setupToolbar();
    }

    private void setupView() {
        this.toolbar = (ImagePickerToolbar) findViewById(R.id.toolbar);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.progressWheel = (ProgressWheel) findViewById(R.id.progressWheel);
        this.emptyLayout = findViewById(R.id.layout_empty);
        this.snackBar = (SnackBarView) findViewById(R.id.snackbar);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(this.config.getStatusBarColor());
        }
        this.progressWheel.setBarColor(this.config.getProgressBarColor());
        findViewById(R.id.container).setBackgroundColor(this.config.getBackgroundColor());
    }

    private void setupComponents() {
        this.recyclerViewManager = new RecyclerViewManager(this.recyclerView, this.config, getResources().getConfiguration().orientation);
        this.recyclerViewManager.setupAdapters(this.imageClickListener, this.folderClickListener);
        this.recyclerViewManager.setOnImageSelectionListener(new OnImageSelectionListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.6
            @Override // com.nguyenhoanglam.imagepicker.listener.OnImageSelectionListener
            public void onSelectionUpdate(List<Image> list) {
                ImagePickerActivity.this.invalidateToolbar();
                if (!ImagePickerActivity.this.config.isMultipleMode() && !list.isEmpty()) {
                    ImagePickerActivity.this.onDone();
                }
            }
        });
        this.presenter = new ImagePickerPresenter(new ImageFileLoader(this));
        this.presenter.attachView(this);
    }

    private void setupToolbar() {
        this.toolbar.config(this.config);
        this.toolbar.setOnBackClickListener(this.backClickListener);
        this.toolbar.setOnCameraClickListener(this.cameraClickListener);
        this.toolbar.setOnDoneClickListener(this.doneClickListener);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getDataWithPermission();
    }

    public void setImageAdapter(List<Image> list, String str) {
        this.recyclerViewManager.setImageAdapter(list, str);
        invalidateToolbar();
    }

    private void setFolderAdapter(List<Folder> list) {
        this.recyclerViewManager.setFolderAdapter(list);
        invalidateToolbar();
    }

    public void invalidateToolbar() {
        this.toolbar.setTitle(this.recyclerViewManager.getTitle());
        this.toolbar.showDoneButton(this.recyclerViewManager.isShowDoneButton());
    }

    public void onDone() {
        this.presenter.onDoneSelectImages(this.recyclerViewManager.getSelectedImages());
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.recyclerViewManager.changeOrientation(configuration.orientation);
    }

    private void getDataWithPermission() {
        final String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
        PermissionHelper.checkPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE", new PermissionHelper.PermissionAskListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.7
            @Override // com.nguyenhoanglam.imagepicker.helper.PermissionHelper.PermissionAskListener
            public void onNeedPermission() {
                PermissionHelper.requestAllPermissions(ImagePickerActivity.this, strArr, 102);
            }

            @Override // com.nguyenhoanglam.imagepicker.helper.PermissionHelper.PermissionAskListener
            public void onPermissionPreviouslyDenied() {
                PermissionHelper.requestAllPermissions(ImagePickerActivity.this, strArr, 102);
            }

            @Override // com.nguyenhoanglam.imagepicker.helper.PermissionHelper.PermissionAskListener
            public void onPermissionDisabled() {
                ImagePickerActivity.this.snackBar.show(R.string.imagepicker_msg_no_write_external_storage_permission, new View.OnClickListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.7.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        PermissionHelper.openAppSettings(ImagePickerActivity.this);
                    }
                });
            }

            @Override // com.nguyenhoanglam.imagepicker.helper.PermissionHelper.PermissionAskListener
            public void onPermissionGranted() {
                ImagePickerActivity.this.getData();
            }
        });
    }

    public void getData() {
        this.presenter.abortLoading();
        this.presenter.loadImages(this.config.isFolderMode());
    }

    public void captureImageWithPermission() {
        final String[] strArr = {"android.permission.CAMERA"};
        PermissionHelper.checkPermission(this, "android.permission.CAMERA", new PermissionHelper.PermissionAskListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.8
            @Override // com.nguyenhoanglam.imagepicker.helper.PermissionHelper.PermissionAskListener
            public void onNeedPermission() {
                PermissionHelper.requestAllPermissions(ImagePickerActivity.this, strArr, 103);
            }

            @Override // com.nguyenhoanglam.imagepicker.helper.PermissionHelper.PermissionAskListener
            public void onPermissionPreviouslyDenied() {
                PermissionHelper.requestAllPermissions(ImagePickerActivity.this, strArr, 103);
            }

            @Override // com.nguyenhoanglam.imagepicker.helper.PermissionHelper.PermissionAskListener
            public void onPermissionDisabled() {
                ImagePickerActivity.this.snackBar.show(R.string.imagepicker_msg_no_camera_permission, new View.OnClickListener() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.8.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        PermissionHelper.openAppSettings(ImagePickerActivity.this);
                    }
                });
            }

            @Override // com.nguyenhoanglam.imagepicker.helper.PermissionHelper.PermissionAskListener
            public void onPermissionGranted() {
                ImagePickerActivity.this.captureImage();
            }
        });
    }

    public void captureImage() {
        if (CameraHelper.checkCameraAvailability(this)) {
            this.presenter.captureImage(this, this.config, 101);
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101 && i2 == -1) {
            this.presenter.finishCaptureImage(this, intent, this.config);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        switch (i) {
            case 102:
                if (!PermissionHelper.hasGranted(iArr)) {
                    LogHelper logHelper = this.logger;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Permission not granted: results len = ");
                    sb.append(iArr.length);
                    sb.append(" Result code = ");
                    sb.append(iArr.length > 0 ? Integer.valueOf(iArr[0]) : "(empty)");
                    logHelper.e(sb.toString());
                    finish();
                    break;
                } else {
                    this.logger.d("Write External permission granted");
                    getData();
                    return;
                }
            case 103:
                break;
            default:
                LogHelper logHelper2 = this.logger;
                logHelper2.d("Got unexpected permission result: " + i);
                super.onRequestPermissionsResult(i, strArr, iArr);
                return;
        }
        if (PermissionHelper.hasGranted(iArr)) {
            this.logger.d("Camera permission granted");
            captureImage();
            return;
        }
        LogHelper logHelper3 = this.logger;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Permission not granted: results len = ");
        sb2.append(iArr.length);
        sb2.append(" Result code = ");
        sb2.append(iArr.length > 0 ? Integer.valueOf(iArr[0]) : "(empty)");
        logHelper3.e(sb2.toString());
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        if (this.handler == null) {
            this.handler = new Handler();
        }
        this.observer = new ContentObserver(this.handler) { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.9
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                ImagePickerActivity.this.getData();
            }
        };
        getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, this.observer);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (this.presenter != null) {
            this.presenter.abortLoading();
            this.presenter.detachView();
        }
        if (this.observer != null) {
            getContentResolver().unregisterContentObserver(this.observer);
            this.observer = null;
        }
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
            this.handler = null;
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        this.recyclerViewManager.handleBack(new OnBackAction() { // from class: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity.10
            @Override // com.nguyenhoanglam.imagepicker.listener.OnBackAction
            public void onBackToFolder() {
                ImagePickerActivity.this.invalidateToolbar();
            }

            @Override // com.nguyenhoanglam.imagepicker.listener.OnBackAction
            public void onFinishImagePicker() {
                ImagePickerActivity.this.setResult(0);
                ImagePickerActivity.this.finish();
            }
        });
    }

    @Override // com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerView
    public void showLoading(boolean z) {
        int i = 0;
        this.progressWheel.setVisibility(z ? 0 : 8);
        RecyclerView recyclerView = this.recyclerView;
        if (z) {
            i = 8;
        }
        recyclerView.setVisibility(i);
        this.emptyLayout.setVisibility(8);
    }

    @Override // com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerView
    public void showFetchCompleted(List<Image> list, List<Folder> list2) {
        if (this.config.isFolderMode()) {
            setFolderAdapter(list2);
        } else {
            setImageAdapter(list, this.config.getImageTitle());
        }
    }

    @Override // com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerView
    public void showError(Throwable th) {
        String string = getString(R.string.imagepicker_error_unknown);
        if (th != null && (th instanceof NullPointerException)) {
            string = getString(R.string.imagepicker_error_images_not_exist);
        }
        Toast.makeText(this, string, 0).show();
    }

    @Override // com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerView
    public void showEmpty() {
        this.progressWheel.setVisibility(8);
        this.recyclerView.setVisibility(8);
        this.emptyLayout.setVisibility(0);
    }

    @Override // com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerView
    public void showCapturedImage(List<Image> list) {
        if (this.recyclerViewManager.selectImage()) {
            this.recyclerViewManager.addSelectedImages(list);
        }
        getDataWithPermission();
    }

    @Override // com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerView
    public void finishPickImages(List<Image> list) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Config.EXTRA_IMAGES, (ArrayList) list);
        setResult(-1, intent);
        finish();
    }
}
