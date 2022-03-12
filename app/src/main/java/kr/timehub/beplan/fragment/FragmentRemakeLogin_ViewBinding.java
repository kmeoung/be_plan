package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AutoCompleteTextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentRemakeLogin_ViewBinding implements Unbinder {
    private FragmentRemakeLogin target;
    private View view2131362209;
    private View view2131362218;
    private View view2131362239;

    @UiThread
    public FragmentRemakeLogin_ViewBinding(final FragmentRemakeLogin fragmentRemakeLogin, View view) {
        this.target = fragmentRemakeLogin;
        fragmentRemakeLogin.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        fragmentRemakeLogin.mEtEmail = (AutoCompleteTextView) Utils.findRequiredViewAsType(view, R.id.et_email, "field 'mEtEmail'", AutoCompleteTextView.class);
        fragmentRemakeLogin.mEtPassword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_password, "field 'mEtPassword'", EditText.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.tv_login, "field 'mTvLogin' and method 'onViewClicked'");
        fragmentRemakeLogin.mTvLogin = (TextView) Utils.castView(findRequiredView, R.id.tv_login, "field 'mTvLogin'", TextView.class);
        this.view2131362218 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRemakeLogin_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRemakeLogin.onViewClicked(view2);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.tv_register, "field 'mTvRegister' and method 'onViewClicked'");
        fragmentRemakeLogin.mTvRegister = (TextView) Utils.castView(findRequiredView2, R.id.tv_register, "field 'mTvRegister'", TextView.class);
        this.view2131362239 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRemakeLogin_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRemakeLogin.onViewClicked(view2);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.tv_forgot_password, "field 'mTvForgotPassword' and method 'onViewClicked'");
        fragmentRemakeLogin.mTvForgotPassword = (TextView) Utils.castView(findRequiredView3, R.id.tv_forgot_password, "field 'mTvForgotPassword'", TextView.class);
        this.view2131362209 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRemakeLogin_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRemakeLogin.onViewClicked(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentRemakeLogin fragmentRemakeLogin = this.target;
        if (fragmentRemakeLogin == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentRemakeLogin.mVStatus = null;
        fragmentRemakeLogin.mEtEmail = null;
        fragmentRemakeLogin.mEtPassword = null;
        fragmentRemakeLogin.mTvLogin = null;
        fragmentRemakeLogin.mTvRegister = null;
        fragmentRemakeLogin.mTvForgotPassword = null;
        this.view2131362218.setOnClickListener(null);
        this.view2131362218 = null;
        this.view2131362239.setOnClickListener(null);
        this.view2131362239 = null;
        this.view2131362209.setOnClickListener(null);
        this.view2131362209 = null;
    }
}
