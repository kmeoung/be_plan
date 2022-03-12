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
public class FragmentSNSInformation_ViewBinding implements Unbinder {
    private FragmentSNSInformation target;
    private View view2131362050;

    @UiThread
    public FragmentSNSInformation_ViewBinding(final FragmentSNSInformation fragmentSNSInformation, View view) {
        this.target = fragmentSNSInformation;
        fragmentSNSInformation.mEtName = (EditText) Utils.findRequiredViewAsType(view, R.id.et_name, "field 'mEtName'", EditText.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.ll_start, "field 'mLlStart' and method 'submitStart'");
        fragmentSNSInformation.mLlStart = (LinearLayout) Utils.castView(findRequiredView, R.id.ll_start, "field 'mLlStart'", LinearLayout.class);
        this.view2131362050 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentSNSInformation_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentSNSInformation.submitStart();
            }
        });
        fragmentSNSInformation.mTextView = (TextView) Utils.findRequiredViewAsType(view, R.id.textView, "field 'mTextView'", TextView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentSNSInformation fragmentSNSInformation = this.target;
        if (fragmentSNSInformation == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentSNSInformation.mEtName = null;
        fragmentSNSInformation.mLlStart = null;
        fragmentSNSInformation.mTextView = null;
        this.view2131362050.setOnClickListener(null);
        this.view2131362050 = null;
    }
}
