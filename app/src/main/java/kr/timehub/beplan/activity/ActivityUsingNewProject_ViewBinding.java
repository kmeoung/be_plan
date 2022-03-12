package kr.timehub.beplan.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.BaseSpinner;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class ActivityUsingNewProject_ViewBinding implements Unbinder {
    private ActivityUsingNewProject target;
    private View view2131361859;

    @UiThread
    public ActivityUsingNewProject_ViewBinding(ActivityUsingNewProject activityUsingNewProject) {
        this(activityUsingNewProject, activityUsingNewProject.getWindow().getDecorView());
    }

    @UiThread
    public ActivityUsingNewProject_ViewBinding(final ActivityUsingNewProject activityUsingNewProject, View view) {
        this.target = activityUsingNewProject;
        activityUsingNewProject.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        activityUsingNewProject.mBaseToolbar = (BaseMainToolBar) Utils.findRequiredViewAsType(view, R.id.base_toolbar, "field 'mBaseToolbar'", BaseMainToolBar.class);
        activityUsingNewProject.mBtnClose = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_close, "field 'mBtnClose'", ImageButton.class);
        activityUsingNewProject.mBtnMore = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_more, "field 'mBtnMore'", ImageButton.class);
        activityUsingNewProject.mEtProject = (EditText) Utils.findRequiredViewAsType(view, R.id.et_project, "field 'mEtProject'", EditText.class);
        activityUsingNewProject.mSpTeamMember = (BaseSpinner) Utils.findRequiredViewAsType(view, R.id.sp_team_member, "field 'mSpTeamMember'", BaseSpinner.class);
        activityUsingNewProject.mEtEmail = (kr.timehub.beplan.base.widgets.EditText) Utils.findRequiredViewAsType(view, R.id.et_email, "field 'mEtEmail'", kr.timehub.beplan.base.widgets.EditText.class);
        activityUsingNewProject.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        activityUsingNewProject.mTvSelectTeam = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_select_team, "field 'mTvSelectTeam'", TextView.class);
        activityUsingNewProject.mLlSelectTeam = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_select_team, "field 'mLlSelectTeam'", LinearLayout.class);
        activityUsingNewProject.mTvProjectMember = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_project_member, "field 'mTvProjectMember'", TextView.class);
        activityUsingNewProject.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.btn_save, "field 'mBtnSave' and method 'onViewClicked'");
        activityUsingNewProject.mBtnSave = (Button) Utils.castView(findRequiredView, R.id.btn_save, "field 'mBtnSave'", Button.class);
        this.view2131361859 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.activity.ActivityUsingNewProject_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                activityUsingNewProject.onViewClicked();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivityUsingNewProject activityUsingNewProject = this.target;
        if (activityUsingNewProject == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activityUsingNewProject.mVStatus = null;
        activityUsingNewProject.mBaseToolbar = null;
        activityUsingNewProject.mBtnClose = null;
        activityUsingNewProject.mBtnMore = null;
        activityUsingNewProject.mEtProject = null;
        activityUsingNewProject.mSpTeamMember = null;
        activityUsingNewProject.mEtEmail = null;
        activityUsingNewProject.mSpDropdown = null;
        activityUsingNewProject.mTvSelectTeam = null;
        activityUsingNewProject.mLlSelectTeam = null;
        activityUsingNewProject.mTvProjectMember = null;
        activityUsingNewProject.mBaseRv = null;
        activityUsingNewProject.mBtnSave = null;
        this.view2131361859.setOnClickListener(null);
        this.view2131361859 = null;
    }
}
