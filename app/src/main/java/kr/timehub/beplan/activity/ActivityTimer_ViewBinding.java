package kr.timehub.beplan.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class ActivityTimer_ViewBinding implements Unbinder {
    private ActivityTimer target;
    private View view2131361862;

    @UiThread
    public ActivityTimer_ViewBinding(ActivityTimer activityTimer) {
        this(activityTimer, activityTimer.getWindow().getDecorView());
    }

    @UiThread
    public ActivityTimer_ViewBinding(final ActivityTimer activityTimer, View view) {
        this.target = activityTimer;
        View findRequiredView = Utils.findRequiredView(view, R.id.btn_stop_timer, "field 'mBtnStopTimer' and method 'onViewClicked'");
        activityTimer.mBtnStopTimer = (Button) Utils.castView(findRequiredView, R.id.btn_stop_timer, "field 'mBtnStopTimer'", Button.class);
        this.view2131361862 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.activity.ActivityTimer_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                activityTimer.onViewClicked();
            }
        });
        activityTimer.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        activityTimer.mTvPTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_p_title, "field 'mTvPTitle'", TextView.class);
        activityTimer.mTvCgTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_cg_title, "field 'mTvCgTitle'", TextView.class);
        activityTimer.mTvTTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_t_title, "field 'mTvTTitle'", TextView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivityTimer activityTimer = this.target;
        if (activityTimer == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activityTimer.mBtnStopTimer = null;
        activityTimer.mVStatus = null;
        activityTimer.mTvPTitle = null;
        activityTimer.mTvCgTitle = null;
        activityTimer.mTvTTitle = null;
        this.view2131361862.setOnClickListener(null);
        this.view2131361862 = null;
    }
}
