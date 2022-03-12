package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
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
public class FragmentTeamDetail_ViewBinding implements Unbinder {
    private FragmentTeamDetail target;
    private View view2131361859;
    private View view2131361860;

    @UiThread
    public FragmentTeamDetail_ViewBinding(final FragmentTeamDetail fragmentTeamDetail, View view) {
        this.target = fragmentTeamDetail;
        fragmentTeamDetail.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        fragmentTeamDetail.mBtnClose = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_close, "field 'mBtnClose'", ImageButton.class);
        fragmentTeamDetail.mBtnMore = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_more, "field 'mBtnMore'", ImageButton.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.btn_search, "field 'mBtnSearch' and method 'onViewClicked'");
        fragmentTeamDetail.mBtnSearch = (ImageButton) Utils.castView(findRequiredView, R.id.btn_search, "field 'mBtnSearch'", ImageButton.class);
        this.view2131361860 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTeamDetail_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentTeamDetail.onViewClicked(view2);
            }
        });
        fragmentTeamDetail.mEtTeamTitle = (EditText) Utils.findRequiredViewAsType(view, R.id.et_team_title, "field 'mEtTeamTitle'", EditText.class);
        fragmentTeamDetail.mSpTeamMember = (BaseSpinner) Utils.findRequiredViewAsType(view, R.id.sp_team_member, "field 'mSpTeamMember'", BaseSpinner.class);
        fragmentTeamDetail.mEtEmail = (kr.timehub.beplan.base.widgets.EditText) Utils.findRequiredViewAsType(view, R.id.et_email, "field 'mEtEmail'", kr.timehub.beplan.base.widgets.EditText.class);
        fragmentTeamDetail.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentTeamDetail.mTvSelectTeam = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_select_team, "field 'mTvSelectTeam'", TextView.class);
        fragmentTeamDetail.mLlSelectTeam = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_select_team, "field 'mLlSelectTeam'", LinearLayout.class);
        fragmentTeamDetail.mTvTeamMember = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_team_member, "field 'mTvTeamMember'", TextView.class);
        fragmentTeamDetail.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_save, "field 'mBtnSave' and method 'onViewClicked'");
        fragmentTeamDetail.mBtnSave = (Button) Utils.castView(findRequiredView2, R.id.btn_save, "field 'mBtnSave'", Button.class);
        this.view2131361859 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTeamDetail_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentTeamDetail.onViewClicked();
            }
        });
        fragmentTeamDetail.mBaseScroll = (NestedScrollView) Utils.findRequiredViewAsType(view, R.id.base_scroll, "field 'mBaseScroll'", NestedScrollView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentTeamDetail fragmentTeamDetail = this.target;
        if (fragmentTeamDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentTeamDetail.mVStatus = null;
        fragmentTeamDetail.mBtnClose = null;
        fragmentTeamDetail.mBtnMore = null;
        fragmentTeamDetail.mBtnSearch = null;
        fragmentTeamDetail.mEtTeamTitle = null;
        fragmentTeamDetail.mSpTeamMember = null;
        fragmentTeamDetail.mEtEmail = null;
        fragmentTeamDetail.mSpDropdown = null;
        fragmentTeamDetail.mTvSelectTeam = null;
        fragmentTeamDetail.mLlSelectTeam = null;
        fragmentTeamDetail.mTvTeamMember = null;
        fragmentTeamDetail.mBaseRv = null;
        fragmentTeamDetail.mBtnSave = null;
        fragmentTeamDetail.mBaseScroll = null;
        this.view2131361860.setOnClickListener(null);
        this.view2131361860 = null;
        this.view2131361859.setOnClickListener(null);
        this.view2131361859 = null;
    }
}
