package kr.timehub.beplan.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogLogout;
import kr.timehub.beplan.dialog.DialogMyInform;
import kr.timehub.beplan.utils.AudioReader;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class FragmentSetting extends BaseFragment {
    private DialogMyInform dialogMyInform;
    AudioReader mAudioReader;
    @BindView(R.id.iv_decibel)
    ImageView mIvDecibel;
    @BindView(R.id.iv_seekbar_bg)
    ImageView mIvSeekbarBg;
    @BindView(R.id.seekbar_decibel)
    SeekBar mSeekbarDecibel;
    @BindView(R.id.tv_decibel)
    TextView mTvDecibel;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_my_inform)
    TextView mTvMyInform;
    @BindView(R.id.v_status)
    View mVStatus;
    Unbinder unbinder;
    private int sampleRate = 8000;
    private int inputBlockSize = 256;
    private int sampleDecimate = 1;
    private int mDecibel = 1;

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_setting, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        getActivity().setTitle("true</false</설정</false</false");
    }

    private void onAction() {
        initReader();
    }

    private void initReader() {
        this.mAudioReader = new AudioReader();
        final Handler handler = new Handler(Looper.getMainLooper());
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mIvDecibel.getLayoutParams();
        this.mAudioReader.startReader(this.sampleRate, this.inputBlockSize * this.sampleDecimate, new AudioReader.Listener() { // from class: kr.timehub.beplan.fragment.FragmentSetting.1
            @Override // kr.timehub.beplan.utils.AudioReader.Listener
            public void onReadError(int i) {
            }

            @Override // kr.timehub.beplan.utils.AudioReader.Listener
            public void onReadComplete(int i) {
                final int i2 = i + 30;
                final int width = (FragmentSetting.this.mIvSeekbarBg != null ? FragmentSetting.this.mIvSeekbarBg.getWidth() : 0) / 30;
                handler.post(new Runnable() { // from class: kr.timehub.beplan.fragment.FragmentSetting.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (i2 >= 0) {
                            layoutParams.width = width * i2;
                            if (FragmentSetting.this.mIvDecibel != null) {
                                FragmentSetting.this.mIvDecibel.setLayoutParams(layoutParams);
                                return;
                            }
                            return;
                        }
                        layoutParams.width = 0;
                        if (FragmentSetting.this.mIvDecibel != null) {
                            FragmentSetting.this.mIvDecibel.setLayoutParams(layoutParams);
                        }
                    }
                });
            }
        });
        if (this.mDecibel != 1) {
            this.mSeekbarDecibel.setProgress(this.mDecibel + 30);
            this.mTvDecibel.setText(String.valueOf(this.mDecibel));
        }
        this.mSeekbarDecibel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: kr.timehub.beplan.fragment.FragmentSetting.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                FragmentSetting.this.mDecibel = seekBar.getProgress() - 30;
                FragmentSetting.this.mTvDecibel.setText(String.valueOf(FragmentSetting.this.mDecibel));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                FragmentSetting.this.mDecibel = seekBar.getProgress() - 30;
                FragmentSetting.this.mTvDecibel.setText(String.valueOf(FragmentSetting.this.mDecibel));
            }
        });
    }

    @OnClick({R.id.tv_my_inform, R.id.tv_setting_push, R.id.tv_login})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_login) {
            ProfileLogin();
        } else if (id == R.id.tv_my_inform) {
            replaceFragment(R.id.base_container, new FragmentMyInfo(), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
        } else if (id == R.id.tv_setting_push) {
            replaceFragment(R.id.base_container, new FragmentSettingPush(), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
        }
    }

    private void ProfileLogin() {
        DialogLogout.newInstance(getContext(), new DialogLogout.OnDialogLogoutListener() { // from class: kr.timehub.beplan.fragment.FragmentSetting.3
            @Override // kr.timehub.beplan.dialog.DialogLogout.OnDialogLogoutListener
            public void onNegative(DialogLogout dialogLogout) {
            }

            @Override // kr.timehub.beplan.dialog.DialogLogout.OnDialogLogoutListener
            public void onPositive(DialogLogout dialogLogout) {
                Utils.Logout((ActivityMain) FragmentSetting.this.getActivity(), FragmentSetting.this.getContext());
            }
        }).show();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
        if (this.mAudioReader != null) {
            this.mAudioReader.stopReader();
        }
    }
}
