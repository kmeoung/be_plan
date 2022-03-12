package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentSetting_ViewBinding implements Unbinder {
    private FragmentSetting target;
    private View view2131362218;
    private View view2131362223;
    private View view2131362251;

    @UiThread
    public FragmentSetting_ViewBinding(final FragmentSetting fragmentSetting, View view) {
        this.target = fragmentSetting;
        fragmentSetting.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        View findRequiredView = Utils.findRequiredView(view, R.id.tv_my_inform, "field 'mTvMyInform' and method 'onClick'");
        fragmentSetting.mTvMyInform = (TextView) Utils.castView(findRequiredView, R.id.tv_my_inform, "field 'mTvMyInform'", TextView.class);
        this.view2131362223 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentSetting_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentSetting.onClick(view2);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.tv_login, "field 'mTvLogin' and method 'onClick'");
        fragmentSetting.mTvLogin = (TextView) Utils.castView(findRequiredView2, R.id.tv_login, "field 'mTvLogin'", TextView.class);
        this.view2131362218 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentSetting_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentSetting.onClick(view2);
            }
        });
        fragmentSetting.mTvDecibel = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_decibel, "field 'mTvDecibel'", TextView.class);
        fragmentSetting.mIvSeekbarBg = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_seekbar_bg, "field 'mIvSeekbarBg'", ImageView.class);
        fragmentSetting.mIvDecibel = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_decibel, "field 'mIvDecibel'", ImageView.class);
        fragmentSetting.mSeekbarDecibel = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekbar_decibel, "field 'mSeekbarDecibel'", SeekBar.class);
        View findRequiredView3 = Utils.findRequiredView(view, R.id.tv_setting_push, "method 'onClick'");
        this.view2131362251 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentSetting_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentSetting.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentSetting fragmentSetting = this.target;
        if (fragmentSetting == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentSetting.mVStatus = null;
        fragmentSetting.mTvMyInform = null;
        fragmentSetting.mTvLogin = null;
        fragmentSetting.mTvDecibel = null;
        fragmentSetting.mIvSeekbarBg = null;
        fragmentSetting.mIvDecibel = null;
        fragmentSetting.mSeekbarDecibel = null;
        this.view2131362223.setOnClickListener(null);
        this.view2131362223 = null;
        this.view2131362218.setOnClickListener(null);
        this.view2131362218 = null;
        this.view2131362251.setOnClickListener(null);
        this.view2131362251 = null;
    }
}
