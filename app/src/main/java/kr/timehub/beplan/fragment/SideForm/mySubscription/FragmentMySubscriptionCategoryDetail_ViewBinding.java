package kr.timehub.beplan.fragment.SideForm.mySubscription;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentMySubscriptionCategoryDetail_ViewBinding implements Unbinder {
    private FragmentMySubscriptionCategoryDetail target;

    @UiThread
    public FragmentMySubscriptionCategoryDetail_ViewBinding(FragmentMySubscriptionCategoryDetail fragmentMySubscriptionCategoryDetail, View view) {
        this.target = fragmentMySubscriptionCategoryDetail;
        fragmentMySubscriptionCategoryDetail.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentMySubscriptionCategoryDetail.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentMySubscriptionCategoryDetail.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        fragmentMySubscriptionCategoryDetail.mTvVersion = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_version, "field 'mTvVersion'", TextView.class);
        fragmentMySubscriptionCategoryDetail.mLlVersion = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_version, "field 'mLlVersion'", LinearLayout.class);
        fragmentMySubscriptionCategoryDetail.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentMySubscriptionCategoryDetail.mRlBg = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_bg, "field 'mRlBg'", RelativeLayout.class);
        fragmentMySubscriptionCategoryDetail.mTvUse = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_use, "field 'mTvUse'", TextView.class);
        fragmentMySubscriptionCategoryDetail.mLlUse = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_use, "field 'mLlUse'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentMySubscriptionCategoryDetail fragmentMySubscriptionCategoryDetail = this.target;
        if (fragmentMySubscriptionCategoryDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentMySubscriptionCategoryDetail.mBaseRv = null;
        fragmentMySubscriptionCategoryDetail.mEtKeyword = null;
        fragmentMySubscriptionCategoryDetail.mTvTitle = null;
        fragmentMySubscriptionCategoryDetail.mTvVersion = null;
        fragmentMySubscriptionCategoryDetail.mLlVersion = null;
        fragmentMySubscriptionCategoryDetail.mSpDropdown = null;
        fragmentMySubscriptionCategoryDetail.mRlBg = null;
        fragmentMySubscriptionCategoryDetail.mTvUse = null;
        fragmentMySubscriptionCategoryDetail.mLlUse = null;
    }
}
