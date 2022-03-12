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
public class ActivitySavePlanList_ViewBinding implements Unbinder {
    private ActivitySavePlanList target;

    @UiThread
    public ActivitySavePlanList_ViewBinding(ActivitySavePlanList activitySavePlanList) {
        this(activitySavePlanList, activitySavePlanList.getWindow().getDecorView());
    }

    @UiThread
    public ActivitySavePlanList_ViewBinding(ActivitySavePlanList activitySavePlanList, View view) {
        this.target = activitySavePlanList;
        activitySavePlanList.mVState = Utils.findRequiredView(view, R.id.v_state, "field 'mVState'");
        activitySavePlanList.mBaseToolbar = (BaseMainToolBar) Utils.findRequiredViewAsType(view, R.id.base_toolbar, "field 'mBaseToolbar'", BaseMainToolBar.class);
        activitySavePlanList.mEtProject = (EditText) Utils.findRequiredViewAsType(view, R.id.et_project, "field 'mEtProject'", EditText.class);
        activitySavePlanList.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        activitySavePlanList.mBtnSave = (Button) Utils.findRequiredViewAsType(view, R.id.btn_save, "field 'mBtnSave'", Button.class);
        activitySavePlanList.mBtnCancel = (Button) Utils.findRequiredViewAsType(view, R.id.btn_cancel, "field 'mBtnCancel'", Button.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivitySavePlanList activitySavePlanList = this.target;
        if (activitySavePlanList == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activitySavePlanList.mVState = null;
        activitySavePlanList.mBaseToolbar = null;
        activitySavePlanList.mEtProject = null;
        activitySavePlanList.mBaseRv = null;
        activitySavePlanList.mBtnSave = null;
        activitySavePlanList.mBtnCancel = null;
    }
}
