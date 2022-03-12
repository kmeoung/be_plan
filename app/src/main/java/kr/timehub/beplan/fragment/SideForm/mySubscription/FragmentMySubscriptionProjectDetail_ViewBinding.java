package kr.timehub.beplan.fragment.SideForm.mySubscription;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentMySubscriptionProjectDetail_ViewBinding implements Unbinder {
    private FragmentMySubscriptionProjectDetail target;
    private View view2131362056;

    @UiThread
    public FragmentMySubscriptionProjectDetail_ViewBinding(final FragmentMySubscriptionProjectDetail fragmentMySubscriptionProjectDetail, View view) {
        this.target = fragmentMySubscriptionProjectDetail;
        fragmentMySubscriptionProjectDetail.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentMySubscriptionProjectDetail.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentMySubscriptionProjectDetail.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        fragmentMySubscriptionProjectDetail.mTvVersion = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_version, "field 'mTvVersion'", TextView.class);
        fragmentMySubscriptionProjectDetail.mLlVersion = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_version, "field 'mLlVersion'", LinearLayout.class);
        fragmentMySubscriptionProjectDetail.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentMySubscriptionProjectDetail.mRlBg = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_bg, "field 'mRlBg'", RelativeLayout.class);
        fragmentMySubscriptionProjectDetail.mTvUse = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_use, "field 'mTvUse'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.ll_use, "field 'mLlUse' and method 'onViewClicked'");
        fragmentMySubscriptionProjectDetail.mLlUse = (LinearLayout) Utils.castView(findRequiredView, R.id.ll_use, "field 'mLlUse'", LinearLayout.class);
        this.view2131362056 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentMySubscriptionProjectDetail.onViewClicked();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentMySubscriptionProjectDetail fragmentMySubscriptionProjectDetail = this.target;
        if (fragmentMySubscriptionProjectDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentMySubscriptionProjectDetail.mBaseRv = null;
        fragmentMySubscriptionProjectDetail.mEtKeyword = null;
        fragmentMySubscriptionProjectDetail.mTvTitle = null;
        fragmentMySubscriptionProjectDetail.mTvVersion = null;
        fragmentMySubscriptionProjectDetail.mLlVersion = null;
        fragmentMySubscriptionProjectDetail.mSpDropdown = null;
        fragmentMySubscriptionProjectDetail.mRlBg = null;
        fragmentMySubscriptionProjectDetail.mTvUse = null;
        fragmentMySubscriptionProjectDetail.mLlUse = null;
        this.view2131362056.setOnClickListener(null);
        this.view2131362056 = null;
    }
}
