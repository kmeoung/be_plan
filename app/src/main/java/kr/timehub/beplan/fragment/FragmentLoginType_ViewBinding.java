package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentLoginType_ViewBinding implements Unbinder {
    private FragmentLoginType target;
    private View view2131362027;
    private View view2131362028;
    private View view2131362206;

    @UiThread
    public FragmentLoginType_ViewBinding(final FragmentLoginType fragmentLoginType, View view) {
        this.target = fragmentLoginType;
        View findRequiredView = Utils.findRequiredView(view, R.id.ll_facebook, "field 'mLlFacebook' and method 'onClick'");
        fragmentLoginType.mLlFacebook = (LinearLayout) Utils.castView(findRequiredView, R.id.ll_facebook, "field 'mLlFacebook'", LinearLayout.class);
        this.view2131362027 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentLoginType_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentLoginType.onClick(view2);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.ll_google, "field 'mLlGoogle' and method 'onClick'");
        fragmentLoginType.mLlGoogle = (LinearLayout) Utils.castView(findRequiredView2, R.id.ll_google, "field 'mLlGoogle'", LinearLayout.class);
        this.view2131362028 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentLoginType_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentLoginType.onClick(view2);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.tv_email, "field 'mTvEmail' and method 'onClick'");
        fragmentLoginType.mTvEmail = (TextView) Utils.castView(findRequiredView3, R.id.tv_email, "field 'mTvEmail'", TextView.class);
        this.view2131362206 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentLoginType_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentLoginType.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentLoginType fragmentLoginType = this.target;
        if (fragmentLoginType == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentLoginType.mLlFacebook = null;
        fragmentLoginType.mLlGoogle = null;
        fragmentLoginType.mTvEmail = null;
        this.view2131362027.setOnClickListener(null);
        this.view2131362027 = null;
        this.view2131362028.setOnClickListener(null);
        this.view2131362028 = null;
        this.view2131362206.setOnClickListener(null);
        this.view2131362206 = null;
    }
}
