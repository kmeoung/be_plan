package kr.timehub.beplan.activity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.naver.temy123.baseproject.base.Widgets.BaseActivity;
import java.io.IOException;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanStopWatch;
import kr.timehub.beplan.service.ServiceClock;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class ActivityTimer extends BaseActivity {
    public static final String EXTRA_BEAN = "EXTRA_BEAN";
    @BindView(R.id.btn_stop_timer)
    Button mBtnStopTimer;
    @BindView(R.id.tv_cg_title)
    TextView mTvCgTitle;
    @BindView(R.id.tv_p_title)
    TextView mTvPTitle;
    @BindView(R.id.tv_t_title)
    TextView mTvTTitle;
    @BindView(R.id.v_status)
    View mVStatus;
    private MediaPlayer player;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);
        updateStatusbarTranslate(this.mVStatus);
        onAction();
    }

    private void onAction() {
        BeanStopWatch beanStopWatch = (BeanStopWatch) getIntent().getSerializableExtra(EXTRA_BEAN);
        if (beanStopWatch != null) {
            Utils.setTextColorGradient(this, this.mTvPTitle);
            this.mTvPTitle.setText(beanStopWatch.getpTitle());
            this.mTvCgTitle.setText(beanStopWatch.getCgTitle());
            this.mTvTTitle.setText(beanStopWatch.gettTitle());
        }
        stopTimer();
        initPlayer();
        MediaPlayerStart(true);
        if (this.player != null) {
            this.player.start();
        }
    }

    private void initPlayer() {
        this.player = new MediaPlayer();
    }

    public void stopTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_RUNNING_STOP);
            startService(intent);
        }
    }

    private void MediaPlayerStart(boolean z) {
        Uri defaultUri = RingtoneManager.getDefaultUri(4);
        if (this.player != null) {
            try {
                this.player.setDataSource(this, defaultUri);
                this.player.setLooping(z);
                if (Build.VERSION.SDK_INT >= 21) {
                    this.player.setAudioAttributes(new AudioAttributes.Builder().setContentType(2).setFlags(1).setUsage(4).build());
                } else {
                    this.player.setAudioStreamType(4);
                }
                this.player.prepare();
            } catch (IOException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    private void AlarmFalse() {
        if (this.player != null) {
            try {
                if (this.player.isPlaying()) {
                    this.player.stop();
                    this.player.reset();
                    this.player.release();
                }
            } catch (IllegalStateException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    @OnClick({R.id.btn_stop_timer})
    public void onViewClicked() {
        finish();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        AlarmFalse();
    }
}
