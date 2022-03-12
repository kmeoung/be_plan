package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentProjectDetail_ViewBinding implements Unbinder {
    private FragmentProjectDetail target;

    @UiThread
    public FragmentProjectDetail_ViewBinding(FragmentProjectDetail fragmentProjectDetail, View view) {
        this.target = fragmentProjectDetail;
        fragmentProjectDetail.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        fragmentProjectDetail.mBtnClose = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_close, "field 'mBtnClose'", ImageButton.class);
        fragmentProjectDetail.mBtnMore = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_more, "field 'mBtnMore'", ImageButton.class);
        fragmentProjectDetail.mTvProject = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_project, "field 'mTvProject'", TextView.class);
        fragmentProjectDetail.mTvProjectMember = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_project_member, "field 'mTvProjectMember'", TextView.class);
        fragmentProjectDetail.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentProjectDetail fragmentProjectDetail = this.target;
        if (fragmentProjectDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentProjectDetail.mVStatus = null;
        fragmentProjectDetail.mBtnClose = null;
        fragmentProjectDetail.mBtnMore = null;
        fragmentProjectDetail.mTvProject = null;
        fragmentProjectDetail.mTvProjectMember = null;
        fragmentProjectDetail.mBaseRv = null;
    }
}
