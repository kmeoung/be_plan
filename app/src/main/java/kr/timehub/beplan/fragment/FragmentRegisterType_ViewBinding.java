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
public class FragmentRegisterType_ViewBinding implements Unbinder {
    private FragmentRegisterType target;
    private View view2131362027;
    private View view2131362028;
    private View view2131362206;
    private View view2131362269;

    @UiThread
    public FragmentRegisterType_ViewBinding(final FragmentRegisterType fragmentRegisterType, View view) {
        this.target = fragmentRegisterType;
        View findRequiredView = Utils.findRequiredView(view, R.id.ll_facebook, "field 'mLlFacebook' and method 'onClick'");
        fragmentRegisterType.mLlFacebook = (LinearLayout) Utils.castView(findRequiredView, R.id.ll_facebook, "field 'mLlFacebook'", LinearLayout.class);
        this.view2131362027 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRegisterType_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRegisterType.onClick(view2);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.ll_google, "field 'mLlGoogle' and method 'onClick'");
        fragmentRegisterType.mLlGoogle = (LinearLayout) Utils.castView(findRequiredView2, R.id.ll_google, "field 'mLlGoogle'", LinearLayout.class);
        this.view2131362028 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRegisterType_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRegisterType.onClick(view2);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.tv_email, "field 'mTvEmail' and method 'onClick'");
        fragmentRegisterType.mTvEmail = (TextView) Utils.castView(findRequiredView3, R.id.tv_email, "field 'mTvEmail'", TextView.class);
        this.view2131362206 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRegisterType_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRegisterType.onClick(view2);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, R.id.tv_terms, "field 'mTvTerms' and method 'onClick'");
        fragmentRegisterType.mTvTerms = (TextView) Utils.castView(findRequiredView4, R.id.tv_terms, "field 'mTvTerms'", TextView.class);
        this.view2131362269 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRegisterType_ViewBinding.4
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRegisterType.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentRegisterType fragmentRegisterType = this.target;
        if (fragmentRegisterType == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentRegisterType.mLlFacebook = null;
        fragmentRegisterType.mLlGoogle = null;
        fragmentRegisterType.mTvEmail = null;
        fragmentRegisterType.mTvTerms = null;
        this.view2131362027.setOnClickListener(null);
        this.view2131362027 = null;
        this.view2131362028.setOnClickListener(null);
        this.view2131362028 = null;
        this.view2131362206.setOnClickListener(null);
        this.view2131362206 = null;
        this.view2131362269.setOnClickListener(null);
        this.view2131362269 = null;
    }
}
