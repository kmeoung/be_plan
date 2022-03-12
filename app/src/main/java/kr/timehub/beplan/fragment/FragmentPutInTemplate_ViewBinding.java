package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentPutInTemplate_ViewBinding implements Unbinder {
    private FragmentPutInTemplate target;
    private View view2131361859;
    private View view2131361966;

    @UiThread
    public FragmentPutInTemplate_ViewBinding(final FragmentPutInTemplate fragmentPutInTemplate, View view) {
        this.target = fragmentPutInTemplate;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_icn_check, "field 'mIvIcnCheck' and method 'onViewClicked'");
        fragmentPutInTemplate.mIvIcnCheck = (ImageView) Utils.castView(findRequiredView, R.id.iv_icn_check, "field 'mIvIcnCheck'", ImageView.class);
        this.view2131361966 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentPutInTemplate_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentPutInTemplate.onViewClicked(view2);
            }
        });
        fragmentPutInTemplate.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_save, "field 'mBtnSave' and method 'onViewClicked'");
        fragmentPutInTemplate.mBtnSave = (Button) Utils.castView(findRequiredView2, R.id.btn_save, "field 'mBtnSave'", Button.class);
        this.view2131361859 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentPutInTemplate_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentPutInTemplate.onViewClicked(view2);
            }
        });
        fragmentPutInTemplate.mTvList = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_list, "field 'mTvList'", TextView.class);
        fragmentPutInTemplate.mLlList = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_list, "field 'mLlList'", LinearLayout.class);
        fragmentPutInTemplate.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentPutInTemplate.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentPutInTemplate fragmentPutInTemplate = this.target;
        if (fragmentPutInTemplate == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentPutInTemplate.mIvIcnCheck = null;
        fragmentPutInTemplate.mBaseRv = null;
        fragmentPutInTemplate.mBtnSave = null;
        fragmentPutInTemplate.mTvList = null;
        fragmentPutInTemplate.mLlList = null;
        fragmentPutInTemplate.mEtKeyword = null;
        fragmentPutInTemplate.mSpDropdown = null;
        this.view2131361966.setOnClickListener(null);
        this.view2131361966 = null;
        this.view2131361859.setOnClickListener(null);
        this.view2131361859 = null;
    }
}
