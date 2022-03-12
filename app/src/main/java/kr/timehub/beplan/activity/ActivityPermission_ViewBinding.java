package kr.timehub.beplan.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class ActivityPermission_ViewBinding implements Unbinder {
    private ActivityPermission target;
    private View view2131362197;

    @UiThread
    public ActivityPermission_ViewBinding(ActivityPermission activityPermission) {
        this(activityPermission, activityPermission.getWindow().getDecorView());
    }

    @UiThread
    public ActivityPermission_ViewBinding(final ActivityPermission activityPermission, View view) {
        this.target = activityPermission;
        activityPermission.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        View findRequiredView = Utils.findRequiredView(view, R.id.tv_confirm, "field 'mTvConfirm' and method 'submitConfirm'");
        activityPermission.mTvConfirm = (TextView) Utils.castView(findRequiredView, R.id.tv_confirm, "field 'mTvConfirm'", TextView.class);
        this.view2131362197 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.activity.ActivityPermission_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                activityPermission.submitConfirm();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivityPermission activityPermission = this.target;
        if (activityPermission == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activityPermission.mVStatus = null;
        activityPermission.mTvConfirm = null;
        this.view2131362197.setOnClickListener(null);
        this.view2131362197 = null;
    }
}
