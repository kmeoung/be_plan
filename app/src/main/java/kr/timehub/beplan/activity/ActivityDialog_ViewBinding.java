package kr.timehub.beplan.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class ActivityDialog_ViewBinding implements Unbinder {
    private ActivityDialog target;

    @UiThread
    public ActivityDialog_ViewBinding(ActivityDialog activityDialog) {
        this(activityDialog, activityDialog.getWindow().getDecorView());
    }

    @UiThread
    public ActivityDialog_ViewBinding(ActivityDialog activityDialog, View view) {
        this.target = activityDialog;
        activityDialog.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivityDialog activityDialog = this.target;
        if (activityDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activityDialog.mVStatus = null;
    }
}
