package kr.timehub.beplan.fragment.SideForm.myForm;

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
public class FragmentMyFormProjectDetail_ViewBinding implements Unbinder {
    private FragmentMyFormProjectDetail target;
    private View view2131362056;

    @UiThread
    public FragmentMyFormProjectDetail_ViewBinding(final FragmentMyFormProjectDetail fragmentMyFormProjectDetail, View view) {
        this.target = fragmentMyFormProjectDetail;
        fragmentMyFormProjectDetail.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentMyFormProjectDetail.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentMyFormProjectDetail.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        fragmentMyFormProjectDetail.mTvUse = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_use, "field 'mTvUse'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.ll_use, "field 'mLlUse' and method 'submitUseProject'");
        fragmentMyFormProjectDetail.mLlUse = (LinearLayout) Utils.castView(findRequiredView, R.id.ll_use, "field 'mLlUse'", LinearLayout.class);
        this.view2131362056 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormProjectDetail_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentMyFormProjectDetail.submitUseProject();
            }
        });
        fragmentMyFormProjectDetail.mIvUse = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_use, "field 'mIvUse'", ImageView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentMyFormProjectDetail fragmentMyFormProjectDetail = this.target;
        if (fragmentMyFormProjectDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentMyFormProjectDetail.mBaseRv = null;
        fragmentMyFormProjectDetail.mEtKeyword = null;
        fragmentMyFormProjectDetail.mTvTitle = null;
        fragmentMyFormProjectDetail.mTvUse = null;
        fragmentMyFormProjectDetail.mLlUse = null;
        fragmentMyFormProjectDetail.mIvUse = null;
        this.view2131362056.setOnClickListener(null);
        this.view2131362056 = null;
    }
}
