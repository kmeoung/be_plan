package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentRegisterEmail_ViewBinding implements Unbinder {
    private FragmentRegisterEmail target;
    private View view2131362050;
    private View view2131362269;

    @UiThread
    public FragmentRegisterEmail_ViewBinding(final FragmentRegisterEmail fragmentRegisterEmail, View view) {
        this.target = fragmentRegisterEmail;
        fragmentRegisterEmail.mIvPhoto = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
        fragmentRegisterEmail.mEtName = (EditText) Utils.findRequiredViewAsType(view, R.id.et_name, "field 'mEtName'", EditText.class);
        fragmentRegisterEmail.mEtEmail = (EditText) Utils.findRequiredViewAsType(view, R.id.et_email, "field 'mEtEmail'", EditText.class);
        fragmentRegisterEmail.mEtPassword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_password, "field 'mEtPassword'", EditText.class);
        fragmentRegisterEmail.mEtConfirmPassword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_confirm_password, "field 'mEtConfirmPassword'", EditText.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.ll_start, "field 'mLlStart' and method 'onClick'");
        fragmentRegisterEmail.mLlStart = (LinearLayout) Utils.castView(findRequiredView, R.id.ll_start, "field 'mLlStart'", LinearLayout.class);
        this.view2131362050 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRegisterEmail_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRegisterEmail.onClick(view2);
            }
        });
        fragmentRegisterEmail.mTextView = (TextView) Utils.findRequiredViewAsType(view, R.id.textView, "field 'mTextView'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.tv_terms, "field 'mTvTerms' and method 'onClick'");
        fragmentRegisterEmail.mTvTerms = (TextView) Utils.castView(findRequiredView2, R.id.tv_terms, "field 'mTvTerms'", TextView.class);
        this.view2131362269 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRegisterEmail_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRegisterEmail.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentRegisterEmail fragmentRegisterEmail = this.target;
        if (fragmentRegisterEmail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentRegisterEmail.mIvPhoto = null;
        fragmentRegisterEmail.mEtName = null;
        fragmentRegisterEmail.mEtEmail = null;
        fragmentRegisterEmail.mEtPassword = null;
        fragmentRegisterEmail.mEtConfirmPassword = null;
        fragmentRegisterEmail.mLlStart = null;
        fragmentRegisterEmail.mTextView = null;
        fragmentRegisterEmail.mTvTerms = null;
        this.view2131362050.setOnClickListener(null);
        this.view2131362050 = null;
        this.view2131362269.setOnClickListener(null);
        this.view2131362269 = null;
    }
}
