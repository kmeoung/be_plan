package kr.timehub.beplan.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseActivity;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.utils.Comm_Params;

/* loaded from: classes.dex */
public class ActivityPermission extends BaseActivity {
    public static final int REQ_CODE_OVERLAY_PERMISSION = 9999;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    @BindView(R.id.v_status)
    View mVStatus;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_permission);
        ButterKnife.bind(this);
        updateStatusbarTranslate(findViewById(R.id.v_status));
    }

    @OnClick({R.id.tv_confirm})
    public void submitConfirm() {
        tempGetPermission();
    }

    @TargetApi(16)
    private void tempGetPermission() {
        ActivityCompat.requestPermissions(this, Comm_Params.APP_PERMISSIONS, 0);
    }

    @Override // kr.timehub.beplan.base.objects.BaseActivity, com.naver.temy123.baseproject.base.Widgets.BaseActivity, android.support.v4.app.FragmentActivity, android.app.Activity, android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        boolean z = false;
        for (int i2 = 0; i2 < strArr.length; i2++) {
            String str = strArr[i2];
            z = iArr[i2] == 0;
            if (!z) {
                break;
            }
        }
        if (!z) {
            showPermissionDialog();
        } else if (getSystemAlertWindow()) {
            setResult(-1);
            finish();
        }
    }

    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("권한을 허용해주셔야 정상적으로 앱 이용이 가능합니다");
        builder.setPositiveButton(R.string.thub_ok, new DialogInterface.OnClickListener() { // from class: kr.timehub.beplan.activity.ActivityPermission.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 9999 && getSystemAlertWindow()) {
            setResult(-1);
            finish();
        }
    }

    public boolean getSystemAlertWindow() {
        if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(this)) {
            return true;
        }
        startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName())), REQ_CODE_OVERLAY_PERMISSION);
        return false;
    }
}
