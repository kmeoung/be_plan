package kr.timehub.beplan.activity;

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
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class ActivityFormTaskDetail_ViewBinding implements Unbinder {
    private ActivityFormTaskDetail target;

    @UiThread
    public ActivityFormTaskDetail_ViewBinding(ActivityFormTaskDetail activityFormTaskDetail) {
        this(activityFormTaskDetail, activityFormTaskDetail.getWindow().getDecorView());
    }

    @UiThread
    public ActivityFormTaskDetail_ViewBinding(ActivityFormTaskDetail activityFormTaskDetail, View view) {
        this.target = activityFormTaskDetail;
        activityFormTaskDetail.mBaseToolbar = (BaseMainToolBar) Utils.findRequiredViewAsType(view, R.id.base_toolbar, "field 'mBaseToolbar'", BaseMainToolBar.class);
        activityFormTaskDetail.mIvCheck = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_check, "field 'mIvCheck'", ImageView.class);
        activityFormTaskDetail.mTvBusinessTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_business_title, "field 'mTvBusinessTitle'", TextView.class);
        activityFormTaskDetail.mIvLeft = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_left, "field 'mIvLeft'", ImageView.class);
        activityFormTaskDetail.mBaseViewPager = (BaseFileViewPager) Utils.findRequiredViewAsType(view, R.id.base_view_pager, "field 'mBaseViewPager'", BaseFileViewPager.class);
        activityFormTaskDetail.mIvRight = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_right, "field 'mIvRight'", ImageView.class);
        activityFormTaskDetail.mLlTaskContents = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_task_contents, "field 'mLlTaskContents'", LinearLayout.class);
        activityFormTaskDetail.mIvPhoto = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
        activityFormTaskDetail.mTvName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_name, "field 'mTvName'", TextView.class);
        activityFormTaskDetail.mTvDate = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_date, "field 'mTvDate'", TextView.class);
        activityFormTaskDetail.mRlMaker = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_maker, "field 'mRlMaker'", RelativeLayout.class);
        activityFormTaskDetail.mBaseSv = (NestedScrollView) Utils.findRequiredViewAsType(view, R.id.base_sv, "field 'mBaseSv'", NestedScrollView.class);
        activityFormTaskDetail.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivityFormTaskDetail activityFormTaskDetail = this.target;
        if (activityFormTaskDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activityFormTaskDetail.mBaseToolbar = null;
        activityFormTaskDetail.mIvCheck = null;
        activityFormTaskDetail.mTvBusinessTitle = null;
        activityFormTaskDetail.mIvLeft = null;
        activityFormTaskDetail.mBaseViewPager = null;
        activityFormTaskDetail.mIvRight = null;
        activityFormTaskDetail.mLlTaskContents = null;
        activityFormTaskDetail.mIvPhoto = null;
        activityFormTaskDetail.mTvName = null;
        activityFormTaskDetail.mTvDate = null;
        activityFormTaskDetail.mRlMaker = null;
        activityFormTaskDetail.mBaseSv = null;
        activityFormTaskDetail.mVStatus = null;
    }
}
