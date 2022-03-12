package kr.timehub.beplan.fragment.SideForm;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentFormTaskDetail_ViewBinding implements Unbinder {
    private FragmentFormTaskDetail target;

    @UiThread
    public FragmentFormTaskDetail_ViewBinding(FragmentFormTaskDetail fragmentFormTaskDetail, View view) {
        this.target = fragmentFormTaskDetail;
        fragmentFormTaskDetail.mIvCheck = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_check, "field 'mIvCheck'", ImageView.class);
        fragmentFormTaskDetail.mTvBusinessTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_business_title, "field 'mTvBusinessTitle'", TextView.class);
        fragmentFormTaskDetail.mIvLeft = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_left, "field 'mIvLeft'", ImageView.class);
        fragmentFormTaskDetail.mBaseViewPager = (BaseFileViewPager) Utils.findRequiredViewAsType(view, R.id.base_view_pager, "field 'mBaseViewPager'", BaseFileViewPager.class);
        fragmentFormTaskDetail.mIvRight = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_right, "field 'mIvRight'", ImageView.class);
        fragmentFormTaskDetail.mLlTaskContents = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_task_contents, "field 'mLlTaskContents'", LinearLayout.class);
        fragmentFormTaskDetail.mIvPhoto = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
        fragmentFormTaskDetail.mTvName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_name, "field 'mTvName'", TextView.class);
        fragmentFormTaskDetail.mTvDate = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_date, "field 'mTvDate'", TextView.class);
        fragmentFormTaskDetail.mBaseSv = (NestedScrollView) Utils.findRequiredViewAsType(view, R.id.base_sv, "field 'mBaseSv'", NestedScrollView.class);
        fragmentFormTaskDetail.mRlMaker = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_maker, "field 'mRlMaker'", RelativeLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentFormTaskDetail fragmentFormTaskDetail = this.target;
        if (fragmentFormTaskDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentFormTaskDetail.mIvCheck = null;
        fragmentFormTaskDetail.mTvBusinessTitle = null;
        fragmentFormTaskDetail.mIvLeft = null;
        fragmentFormTaskDetail.mBaseViewPager = null;
        fragmentFormTaskDetail.mIvRight = null;
        fragmentFormTaskDetail.mLlTaskContents = null;
        fragmentFormTaskDetail.mIvPhoto = null;
        fragmentFormTaskDetail.mTvName = null;
        fragmentFormTaskDetail.mTvDate = null;
        fragmentFormTaskDetail.mBaseSv = null;
        fragmentFormTaskDetail.mRlMaker = null;
    }
}
