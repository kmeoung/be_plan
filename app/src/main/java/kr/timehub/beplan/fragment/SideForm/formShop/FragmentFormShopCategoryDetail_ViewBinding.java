package kr.timehub.beplan.fragment.SideForm.formShop;

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
public class FragmentFormShopCategoryDetail_ViewBinding implements Unbinder {
    private FragmentFormShopCategoryDetail target;

    @UiThread
    public FragmentFormShopCategoryDetail_ViewBinding(FragmentFormShopCategoryDetail fragmentFormShopCategoryDetail, View view) {
        this.target = fragmentFormShopCategoryDetail;
        fragmentFormShopCategoryDetail.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentFormShopCategoryDetail.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentFormShopCategoryDetail.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        fragmentFormShopCategoryDetail.mTvUse = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_use, "field 'mTvUse'", TextView.class);
        fragmentFormShopCategoryDetail.mLlUse = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_use, "field 'mLlUse'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentFormShopCategoryDetail fragmentFormShopCategoryDetail = this.target;
        if (fragmentFormShopCategoryDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentFormShopCategoryDetail.mBaseRv = null;
        fragmentFormShopCategoryDetail.mEtKeyword = null;
        fragmentFormShopCategoryDetail.mTvTitle = null;
        fragmentFormShopCategoryDetail.mTvUse = null;
        fragmentFormShopCategoryDetail.mLlUse = null;
    }
}
