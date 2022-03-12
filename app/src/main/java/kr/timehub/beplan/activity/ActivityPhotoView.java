package kr.timehub.beplan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.naver.temy123.baseproject.base.Widgets.BaseActivity;
import kr.timehub.beplan.R;
import kr.timehub.beplan.service.ServiceClock;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class ActivityPhotoView extends BaseActivity {
    public static final String args_url = "args_url";
    @BindView(R.id.base_pv)
    PhotoView mBasePv;
    @BindView(R.id.v_status)
    View mVStatus;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_photoview);
        ButterKnife.bind(this);
        onAction();
    }

    private void onAction() {
        if (getIntent().getStringExtra(args_url) != null) {
            Glide.with((FragmentActivity) this).load(getIntent().getStringExtra(args_url)).into(this.mBasePv);
            return;
        }
        Toast.makeText(this, "이미지를 불러오는데 실패했습니다.", 0).show();
        finish();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        hideTimer();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        showTimer();
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
}
