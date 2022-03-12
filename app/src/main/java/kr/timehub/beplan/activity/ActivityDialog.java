package kr.timehub.beplan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.dialog.DialogCommon;
import kr.timehub.beplan.service.ServiceClock;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class ActivityDialog extends Activity {
    @BindView(R.id.v_status)
    View mVStatus;

    @Override // android.app.Activity
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_transparent);
        ButterKnife.bind(this);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        showTimer();
        showDialog();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        hideTimer();
    }

    public void showTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_SHOW_TIMER);
            startService(intent);
        }
    }

    public void hideTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_HIDE_TIMER);
            startService(intent);
        }
    }

    private void showDialog() {
        DialogCommon newInstance = DialogCommon.newInstance(this, getString(R.string.dialog_stop_timer_title), getString(R.string.dialog_stop_timer_contents), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.activity.ActivityDialog.1
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                ActivityDialog.this.stopTimer();
                dialogCommon.dismiss();
                ActivityDialog.this.finish();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                ActivityDialog.this.closeDialog();
                dialogCommon.dismiss();
                ActivityDialog.this.finish();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText(ActivityDialog.this.getString(R.string.btn_yes));
                dialogCommon.getmBtnNegative().setText(ActivityDialog.this.getString(R.string.btn_no));
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
                ActivityDialog.this.closeDialog();
                ActivityDialog.this.finish();
            }
        });
        newInstance.setCancelable(false);
        newInstance.show();
    }

    public void stopTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_RUNNING_STOP);
            startService(intent);
        }
    }

    public void closeDialog() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_ONLY_CLOSE_DIALOG);
            startService(intent);
        }
    }
}
