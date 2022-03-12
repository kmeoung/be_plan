package kr.timehub.beplan.fragment.SideForm.formShop;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
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
public class FragmentFormShopProjectDetail_ViewBinding implements Unbinder {
    private FragmentFormShopProjectDetail target;
    private View view2131362056;

    @UiThread
    public FragmentFormShopProjectDetail_ViewBinding(final FragmentFormShopProjectDetail fragmentFormShopProjectDetail, View view) {
        this.target = fragmentFormShopProjectDetail;
        fragmentFormShopProjectDetail.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentFormShopProjectDetail.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentFormShopProjectDetail.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        fragmentFormShopProjectDetail.mTvUse = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_use, "field 'mTvUse'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.ll_use, "field 'mLlUse' and method 'onViewClicked'");
        fragmentFormShopProjectDetail.mLlUse = (LinearLayout) Utils.castView(findRequiredView, R.id.ll_use, "field 'mLlUse'", LinearLayout.class);
        this.view2131362056 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProjectDetail_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentFormShopProjectDetail.onViewClicked();
            }
        });
        fragmentFormShopProjectDetail.mIvUse = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_use, "field 'mIvUse'", ImageView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentFormShopProjectDetail fragmentFormShopProjectDetail = this.target;
        if (fragmentFormShopProjectDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentFormShopProjectDetail.mBaseRv = null;
        fragmentFormShopProjectDetail.mEtKeyword = null;
        fragmentFormShopProjectDetail.mTvTitle = null;
        fragmentFormShopProjectDetail.mTvUse = null;
        fragmentFormShopProjectDetail.mLlUse = null;
        fragmentFormShopProjectDetail.mIvUse = null;
        this.view2131362056.setOnClickListener(null);
        this.view2131362056 = null;
    }
}
