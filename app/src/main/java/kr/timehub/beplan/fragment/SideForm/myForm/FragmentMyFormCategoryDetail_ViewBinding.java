package kr.timehub.beplan.fragment.SideForm.myForm;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentMyFormCategoryDetail_ViewBinding implements Unbinder {
    private FragmentMyFormCategoryDetail target;

    @UiThread
    public FragmentMyFormCategoryDetail_ViewBinding(FragmentMyFormCategoryDetail fragmentMyFormCategoryDetail, View view) {
        this.target = fragmentMyFormCategoryDetail;
        fragmentMyFormCategoryDetail.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentMyFormCategoryDetail.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentMyFormCategoryDetail.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        fragmentMyFormCategoryDetail.mTvUse = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_use, "field 'mTvUse'", TextView.class);
        fragmentMyFormCategoryDetail.mLlUse = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_use, "field 'mLlUse'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentMyFormCategoryDetail fragmentMyFormCategoryDetail = this.target;
        if (fragmentMyFormCategoryDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentMyFormCategoryDetail.mBaseRv = null;
        fragmentMyFormCategoryDetail.mEtKeyword = null;
        fragmentMyFormCategoryDetail.mTvTitle = null;
        fragmentMyFormCategoryDetail.mTvUse = null;
        fragmentMyFormCategoryDetail.mLlUse = null;
    }
}
