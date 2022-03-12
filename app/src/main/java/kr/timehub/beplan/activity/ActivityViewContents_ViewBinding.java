package kr.timehub.beplan.activity;

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
import kr.timehub.beplan.base.widgets.BaseImagesViewpager;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class ActivityViewContents_ViewBinding implements Unbinder {
    private ActivityViewContents target;
    private View view2131361954;
    private View view2131361971;
    private View view2131361995;

    @UiThread
    public ActivityViewContents_ViewBinding(ActivityViewContents activityViewContents) {
        this(activityViewContents, activityViewContents.getWindow().getDecorView());
    }

    @UiThread
    public ActivityViewContents_ViewBinding(final ActivityViewContents activityViewContents, View view) {
        this.target = activityViewContents;
        activityViewContents.mBaseVp = (BaseImagesViewpager) Utils.findRequiredViewAsType(view, R.id.base_vp, "field 'mBaseVp'", BaseImagesViewpager.class);
        activityViewContents.mTvImgCount = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_img_count, "field 'mTvImgCount'", TextView.class);
        activityViewContents.mLlHeader = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_header, "field 'mLlHeader'", LinearLayout.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_left, "field 'mIvLeft' and method 'onViewClicked'");
        activityViewContents.mIvLeft = (ImageView) Utils.castView(findRequiredView, R.id.iv_left, "field 'mIvLeft'", ImageView.class);
        this.view2131361971 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.activity.ActivityViewContents_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                activityViewContents.onViewClicked(view2);
            }
        });
        activityViewContents.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.iv_right, "field 'mIvRight' and method 'onViewClicked'");
        activityViewContents.mIvRight = (ImageView) Utils.castView(findRequiredView2, R.id.iv_right, "field 'mIvRight'", ImageView.class);
        this.view2131361995 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.activity.ActivityViewContents_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                activityViewContents.onViewClicked(view2);
            }
        });
        activityViewContents.mLlBottomList = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_bottom_list, "field 'mLlBottomList'", LinearLayout.class);
        View findRequiredView3 = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onViewClicked'");
        activityViewContents.mIvClose = (ImageView) Utils.castView(findRequiredView3, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.activity.ActivityViewContents_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                activityViewContents.onViewClicked(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivityViewContents activityViewContents = this.target;
        if (activityViewContents == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activityViewContents.mBaseVp = null;
        activityViewContents.mTvImgCount = null;
        activityViewContents.mLlHeader = null;
        activityViewContents.mIvLeft = null;
        activityViewContents.mBaseRv = null;
        activityViewContents.mIvRight = null;
        activityViewContents.mLlBottomList = null;
        activityViewContents.mIvClose = null;
        this.view2131361971.setOnClickListener(null);
        this.view2131361971 = null;
        this.view2131361995.setOnClickListener(null);
        this.view2131361995 = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
    }
}
