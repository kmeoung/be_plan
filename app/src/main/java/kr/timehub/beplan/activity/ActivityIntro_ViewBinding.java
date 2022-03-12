package kr.timehub.beplan.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class ActivityIntro_ViewBinding implements Unbinder {
    private ActivityIntro target;

    @UiThread
    public ActivityIntro_ViewBinding(ActivityIntro activityIntro) {
        this(activityIntro, activityIntro.getWindow().getDecorView());
    }

    @UiThread
    public ActivityIntro_ViewBinding(ActivityIntro activityIntro, View view) {
        this.target = activityIntro;
        activityIntro.mVState = Utils.findRequiredView(view, R.id.v_state, "field 'mVState'");
        activityIntro.mBaseContainer = (FrameLayout) Utils.findRequiredViewAsType(view, R.id.base_container, "field 'mBaseContainer'", FrameLayout.class);
        activityIntro.mActivityMain = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.activity_main, "field 'mActivityMain'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivityIntro activityIntro = this.target;
        if (activityIntro == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activityIntro.mVState = null;
        activityIntro.mBaseContainer = null;
        activityIntro.mActivityMain = null;
    }
}
