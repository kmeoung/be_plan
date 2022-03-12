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
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentRequestDetail_ViewBinding implements Unbinder {
    private FragmentRequestDetail target;
    private View view2131362032;
    private View view2131362044;

    @UiThread
    public FragmentRequestDetail_ViewBinding(final FragmentRequestDetail fragmentRequestDetail, View view) {
        this.target = fragmentRequestDetail;
        fragmentRequestDetail.mIvIcnRequest = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_icn_request, "field 'mIvIcnRequest'", ImageView.class);
        fragmentRequestDetail.mTvBusiness = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_business, "field 'mTvBusiness'", TextView.class);
        fragmentRequestDetail.mTvDate = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_date, "field 'mTvDate'", TextView.class);
        fragmentRequestDetail.mTvName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_name, "field 'mTvName'", TextView.class);
        fragmentRequestDetail.mIvPhoto = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
        fragmentRequestDetail.mIvLeft = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_left, "field 'mIvLeft'", ImageView.class);
        fragmentRequestDetail.mBaseViewPager = (BaseFileViewPager) Utils.findRequiredViewAsType(view, R.id.base_view_pager, "field 'mBaseViewPager'", BaseFileViewPager.class);
        fragmentRequestDetail.mIvRight = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_right, "field 'mIvRight'", ImageView.class);
        fragmentRequestDetail.mLlTaskContents = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_task_contents, "field 'mLlTaskContents'", LinearLayout.class);
        fragmentRequestDetail.mTvBusinessTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_business_title, "field 'mTvBusinessTitle'", TextView.class);
        fragmentRequestDetail.mTvBusinessContent = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_business_content, "field 'mTvBusinessContent'", TextView.class);
        fragmentRequestDetail.mTvRequestTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_request_title, "field 'mTvRequestTitle'", TextView.class);
        fragmentRequestDetail.mTvRequestContent = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_request_content, "field 'mTvRequestContent'", TextView.class);
        fragmentRequestDetail.mTvTermDate = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_term_date, "field 'mTvTermDate'", TextView.class);
        fragmentRequestDetail.mLlTerm = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_term, "field 'mLlTerm'", LinearLayout.class);
        fragmentRequestDetail.mTvLeftBtn = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_left_btn, "field 'mTvLeftBtn'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.ll_left_btn, "field 'mLlLeftBtn' and method 'onClick'");
        fragmentRequestDetail.mLlLeftBtn = (LinearLayout) Utils.castView(findRequiredView, R.id.ll_left_btn, "field 'mLlLeftBtn'", LinearLayout.class);
        this.view2131362032 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRequestDetail_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRequestDetail.onClick(view2);
            }
        });
        fragmentRequestDetail.mTvRightBtn = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_right_btn, "field 'mTvRightBtn'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.ll_right_btn, "field 'mLlRightBtn' and method 'onClick'");
        fragmentRequestDetail.mLlRightBtn = (LinearLayout) Utils.castView(findRequiredView2, R.id.ll_right_btn, "field 'mLlRightBtn'", LinearLayout.class);
        this.view2131362044 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentRequestDetail_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRequestDetail.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentRequestDetail fragmentRequestDetail = this.target;
        if (fragmentRequestDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentRequestDetail.mIvIcnRequest = null;
        fragmentRequestDetail.mTvBusiness = null;
        fragmentRequestDetail.mTvDate = null;
        fragmentRequestDetail.mTvName = null;
        fragmentRequestDetail.mIvPhoto = null;
        fragmentRequestDetail.mIvLeft = null;
        fragmentRequestDetail.mBaseViewPager = null;
        fragmentRequestDetail.mIvRight = null;
        fragmentRequestDetail.mLlTaskContents = null;
        fragmentRequestDetail.mTvBusinessTitle = null;
        fragmentRequestDetail.mTvBusinessContent = null;
        fragmentRequestDetail.mTvRequestTitle = null;
        fragmentRequestDetail.mTvRequestContent = null;
        fragmentRequestDetail.mTvTermDate = null;
        fragmentRequestDetail.mLlTerm = null;
        fragmentRequestDetail.mTvLeftBtn = null;
        fragmentRequestDetail.mLlLeftBtn = null;
        fragmentRequestDetail.mTvRightBtn = null;
        fragmentRequestDetail.mLlRightBtn = null;
        this.view2131362032.setOnClickListener(null);
        this.view2131362032 = null;
        this.view2131362044.setOnClickListener(null);
        this.view2131362044 = null;
    }
}
