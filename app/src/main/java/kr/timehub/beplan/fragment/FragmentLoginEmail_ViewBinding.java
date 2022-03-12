package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentLoginEmail_ViewBinding implements Unbinder {
    private FragmentLoginEmail target;
    private View view2131362041;
    private View view2131362219;

    @UiThread
    public FragmentLoginEmail_ViewBinding(final FragmentLoginEmail fragmentLoginEmail, View view) {
        this.target = fragmentLoginEmail;
        View findRequiredView = Utils.findRequiredView(view, R.id.ll_ok, "field 'mLlOk' and method 'onClick'");
        fragmentLoginEmail.mLlOk = (LinearLayout) Utils.castView(findRequiredView, R.id.ll_ok, "field 'mLlOk'", LinearLayout.class);
        this.view2131362041 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentLoginEmail_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentLoginEmail.onClick(view2);
            }
        });
        fragmentLoginEmail.mEtEmail = (EditText) Utils.findRequiredViewAsType(view, R.id.et_email, "field 'mEtEmail'", EditText.class);
        fragmentLoginEmail.mEtPassword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_password, "field 'mEtPassword'", EditText.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.tv_lose_password, "field 'mTvLosePassword' and method 'onClick'");
        fragmentLoginEmail.mTvLosePassword = (TextView) Utils.castView(findRequiredView2, R.id.tv_lose_password, "field 'mTvLosePassword'", TextView.class);
        this.view2131362219 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentLoginEmail_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentLoginEmail.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentLoginEmail fragmentLoginEmail = this.target;
        if (fragmentLoginEmail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentLoginEmail.mLlOk = null;
        fragmentLoginEmail.mEtEmail = null;
        fragmentLoginEmail.mEtPassword = null;
        fragmentLoginEmail.mTvLosePassword = null;
        this.view2131362041.setOnClickListener(null);
        this.view2131362041 = null;
        this.view2131362219.setOnClickListener(null);
        this.view2131362219 = null;
    }
}
