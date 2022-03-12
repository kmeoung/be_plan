package kr.timehub.beplan.fragment;

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
import kr.timehub.beplan.base.widgets.BaseSpinner;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentNewProject_ViewBinding implements Unbinder {
    private FragmentNewProject target;
    private View view2131361859;
    private View view2131361860;

    @UiThread
    public FragmentNewProject_ViewBinding(final FragmentNewProject fragmentNewProject, View view) {
        this.target = fragmentNewProject;
        fragmentNewProject.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        fragmentNewProject.mBtnClose = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_close, "field 'mBtnClose'", ImageButton.class);
        fragmentNewProject.mBtnMore = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_more, "field 'mBtnMore'", ImageButton.class);
        fragmentNewProject.mEtProject = (EditText) Utils.findRequiredViewAsType(view, R.id.et_project, "field 'mEtProject'", EditText.class);
        fragmentNewProject.mEtEmail = (kr.timehub.beplan.base.widgets.EditText) Utils.findRequiredViewAsType(view, R.id.et_email, "field 'mEtEmail'", kr.timehub.beplan.base.widgets.EditText.class);
        fragmentNewProject.mTvSelectTeam = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_select_team, "field 'mTvSelectTeam'", TextView.class);
        fragmentNewProject.mLlSelectTeam = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_select_team, "field 'mLlSelectTeam'", LinearLayout.class);
        fragmentNewProject.mTvProjectMember = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_project_member, "field 'mTvProjectMember'", TextView.class);
        fragmentNewProject.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.btn_search, "field 'mBtnSearch' and method 'onSearch'");
        fragmentNewProject.mBtnSearch = (ImageButton) Utils.castView(findRequiredView, R.id.btn_search, "field 'mBtnSearch'", ImageButton.class);
        this.view2131361860 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewProject_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentNewProject.onSearch();
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_save, "field 'mBtnSave' and method 'onViewClicked'");
        fragmentNewProject.mBtnSave = (Button) Utils.castView(findRequiredView2, R.id.btn_save, "field 'mBtnSave'", Button.class);
        this.view2131361859 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewProject_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentNewProject.onViewClicked();
            }
        });
        fragmentNewProject.mSpTeamMember = (BaseSpinner) Utils.findRequiredViewAsType(view, R.id.sp_team_member, "field 'mSpTeamMember'", BaseSpinner.class);
        fragmentNewProject.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentNewProject fragmentNewProject = this.target;
        if (fragmentNewProject == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentNewProject.mVStatus = null;
        fragmentNewProject.mBtnClose = null;
        fragmentNewProject.mBtnMore = null;
        fragmentNewProject.mEtProject = null;
        fragmentNewProject.mEtEmail = null;
        fragmentNewProject.mTvSelectTeam = null;
        fragmentNewProject.mLlSelectTeam = null;
        fragmentNewProject.mTvProjectMember = null;
        fragmentNewProject.mBaseRv = null;
        fragmentNewProject.mBtnSearch = null;
        fragmentNewProject.mBtnSave = null;
        fragmentNewProject.mSpTeamMember = null;
        fragmentNewProject.mSpDropdown = null;
        this.view2131361860.setOnClickListener(null);
        this.view2131361860 = null;
        this.view2131361859.setOnClickListener(null);
        this.view2131361859 = null;
    }
}
