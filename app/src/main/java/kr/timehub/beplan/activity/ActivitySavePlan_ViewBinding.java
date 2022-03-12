package kr.timehub.beplan.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;

/* loaded from: classes.dex */
public class ActivitySavePlan_ViewBinding implements Unbinder {
    private ActivitySavePlan target;

    @UiThread
    public ActivitySavePlan_ViewBinding(ActivitySavePlan activitySavePlan) {
        this(activitySavePlan, activitySavePlan.getWindow().getDecorView());
    }

    @UiThread
    public ActivitySavePlan_ViewBinding(ActivitySavePlan activitySavePlan, View view) {
        this.target = activitySavePlan;
        activitySavePlan.mVState = Utils.findRequiredView(view, R.id.v_state, "field 'mVState'");
        activitySavePlan.mBaseToolbar = (BaseMainToolBar) Utils.findRequiredViewAsType(view, R.id.base_toolbar, "field 'mBaseToolbar'", BaseMainToolBar.class);
        activitySavePlan.mEtProject = (EditText) Utils.findRequiredViewAsType(view, R.id.et_project, "field 'mEtProject'", EditText.class);
        activitySavePlan.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        activitySavePlan.mBtnSave = (Button) Utils.findRequiredViewAsType(view, R.id.btn_save, "field 'mBtnSave'", Button.class);
        activitySavePlan.mBtnCancel = (Button) Utils.findRequiredViewAsType(view, R.id.btn_cancel, "field 'mBtnCancel'", Button.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivitySavePlan activitySavePlan = this.target;
        if (activitySavePlan == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activitySavePlan.mVState = null;
        activitySavePlan.mBaseToolbar = null;
        activitySavePlan.mEtProject = null;
        activitySavePlan.mBaseRv = null;
        activitySavePlan.mBtnSave = null;
        activitySavePlan.mBtnCancel = null;
    }
}
