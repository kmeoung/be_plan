package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;

/* loaded from: classes.dex */
public class FragmentLogin_ViewBinding implements Unbinder {
    private FragmentLogin target;
    private View view2131361851;
    private View view2131361858;

    @UiThread
    public FragmentLogin_ViewBinding(final FragmentLogin fragmentLogin, View view) {
        this.target = fragmentLogin;
        fragmentLogin.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        View findRequiredView = Utils.findRequiredView(view, R.id.btn_login, "field 'mBtnLogin' and method 'onClick'");
        fragmentLogin.mBtnLogin = (Button) Utils.castView(findRequiredView, R.id.btn_login, "field 'mBtnLogin'", Button.class);
        this.view2131361851 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentLogin_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentLogin.onClick(view2);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_register, "field 'mBtnRegister' and method 'onClick'");
        fragmentLogin.mBtnRegister = (Button) Utils.castView(findRequiredView2, R.id.btn_register, "field 'mBtnRegister'", Button.class);
        this.view2131361858 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentLogin_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentLogin.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentLogin fragmentLogin = this.target;
        if (fragmentLogin == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentLogin.mVStatus = null;
        fragmentLogin.mBtnLogin = null;
        fragmentLogin.mBtnRegister = null;
        this.view2131361851.setOnClickListener(null);
        this.view2131361851 = null;
        this.view2131361858.setOnClickListener(null);
        this.view2131361858 = null;
    }
}
