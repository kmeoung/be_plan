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
public class FragmentNewTeam_ViewBinding implements Unbinder {
    private FragmentNewTeam target;
    private View view2131361859;
    private View view2131361860;

    @UiThread
    public FragmentNewTeam_ViewBinding(final FragmentNewTeam fragmentNewTeam, View view) {
        this.target = fragmentNewTeam;
        fragmentNewTeam.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        fragmentNewTeam.mBtnClose = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_close, "field 'mBtnClose'", ImageButton.class);
        fragmentNewTeam.mBtnMore = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_more, "field 'mBtnMore'", ImageButton.class);
        fragmentNewTeam.mEtTeamTitle = (EditText) Utils.findRequiredViewAsType(view, R.id.et_team_title, "field 'mEtTeamTitle'", EditText.class);
        fragmentNewTeam.mSpTeamMember = (BaseSpinner) Utils.findRequiredViewAsType(view, R.id.sp_team_member, "field 'mSpTeamMember'", BaseSpinner.class);
        fragmentNewTeam.mEtEmail = (kr.timehub.beplan.base.widgets.EditText) Utils.findRequiredViewAsType(view, R.id.et_email, "field 'mEtEmail'", kr.timehub.beplan.base.widgets.EditText.class);
        fragmentNewTeam.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentNewTeam.mTvSelectTeam = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_select_team, "field 'mTvSelectTeam'", TextView.class);
        fragmentNewTeam.mLlSelectTeam = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_select_team, "field 'mLlSelectTeam'", LinearLayout.class);
        fragmentNewTeam.mTvTeamMember = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_team_member, "field 'mTvTeamMember'", TextView.class);
        fragmentNewTeam.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.btn_save, "field 'mBtnSave' and method 'onViewClicked'");
        fragmentNewTeam.mBtnSave = (Button) Utils.castView(findRequiredView, R.id.btn_save, "field 'mBtnSave'", Button.class);
        this.view2131361859 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewTeam_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentNewTeam.onViewClicked();
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_search, "field 'mBtnSearch' and method 'onSearch'");
        fragmentNewTeam.mBtnSearch = (ImageButton) Utils.castView(findRequiredView2, R.id.btn_search, "field 'mBtnSearch'", ImageButton.class);
        this.view2131361860 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewTeam_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentNewTeam.onSearch();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentNewTeam fragmentNewTeam = this.target;
        if (fragmentNewTeam == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentNewTeam.mVStatus = null;
        fragmentNewTeam.mBtnClose = null;
        fragmentNewTeam.mBtnMore = null;
        fragmentNewTeam.mEtTeamTitle = null;
        fragmentNewTeam.mSpTeamMember = null;
        fragmentNewTeam.mEtEmail = null;
        fragmentNewTeam.mSpDropdown = null;
        fragmentNewTeam.mTvSelectTeam = null;
        fragmentNewTeam.mLlSelectTeam = null;
        fragmentNewTeam.mTvTeamMember = null;
        fragmentNewTeam.mBaseRv = null;
        fragmentNewTeam.mBtnSave = null;
        fragmentNewTeam.mBtnSearch = null;
        this.view2131361859.setOnClickListener(null);
        this.view2131361859 = null;
        this.view2131361860.setOnClickListener(null);
        this.view2131361860 = null;
    }
}
