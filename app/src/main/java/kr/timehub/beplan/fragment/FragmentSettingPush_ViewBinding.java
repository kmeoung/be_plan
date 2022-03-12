package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class FragmentSettingPush_ViewBinding implements Unbinder {
    private FragmentSettingPush target;
    private View view2131362046;
    private View view2131362207;
    private View view2131362252;

    @UiThread
    public FragmentSettingPush_ViewBinding(final FragmentSettingPush fragmentSettingPush, View view) {
        this.target = fragmentSettingPush;
        View findRequiredView = Utils.findRequiredView(view, R.id.tv_start_date, "field 'mTvStartTime' and method 'submitClickTime'");
        fragmentSettingPush.mTvStartTime = (TextView) Utils.castView(findRequiredView, R.id.tv_start_date, "field 'mTvStartTime'", TextView.class);
        this.view2131362252 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentSettingPush_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentSettingPush.submitClickTime(view2);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.tv_end_date, "field 'mTvEndTime' and method 'submitClickTime'");
        fragmentSettingPush.mTvEndTime = (TextView) Utils.castView(findRequiredView2, R.id.tv_end_date, "field 'mTvEndTime'", TextView.class);
        this.view2131362207 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentSettingPush_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentSettingPush.submitClickTime(view2);
            }
        });
        fragmentSettingPush.mSwitchPush = (Switch) Utils.findRequiredViewAsType(view, R.id.switch_push, "field 'mSwitchPush'", Switch.class);
        View findRequiredView3 = Utils.findRequiredView(view, R.id.ll_save, "method 'submitClickSave'");
        this.view2131362046 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentSettingPush_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentSettingPush.submitClickSave();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentSettingPush fragmentSettingPush = this.target;
        if (fragmentSettingPush == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentSettingPush.mTvStartTime = null;
        fragmentSettingPush.mTvEndTime = null;
        fragmentSettingPush.mSwitchPush = null;
        this.view2131362252.setOnClickListener(null);
        this.view2131362252 = null;
        this.view2131362207.setOnClickListener(null);
        this.view2131362207 = null;
        this.view2131362046.setOnClickListener(null);
        this.view2131362046 = null;
    }
}
