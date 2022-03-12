package kr.timehub.beplan.fragment.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentDrawer_ViewBinding implements Unbinder {
    private FragmentDrawer target;

    @UiThread
    public FragmentDrawer_ViewBinding(FragmentDrawer fragmentDrawer, View view) {
        this.target = fragmentDrawer;
        fragmentDrawer.mBtnAlarm = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_alarm, "field 'mBtnAlarm'", ImageButton.class);
        fragmentDrawer.mBtnOption = (ImageButton) Utils.findRequiredViewAsType(view, R.id.btn_option, "field 'mBtnOption'", ImageButton.class);
        fragmentDrawer.mIvProfile = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_profile, "field 'mIvProfile'", ImageView.class);
        fragmentDrawer.mTvName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_name, "field 'mTvName'", TextView.class);
        fragmentDrawer.mTvEmail = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_email, "field 'mTvEmail'", TextView.class);
        fragmentDrawer.mLayoutProfile = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.layout_profile, "field 'mLayoutProfile'", LinearLayout.class);
        fragmentDrawer.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_recyclerview, "field 'mBaseRv'", RecyclerView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentDrawer fragmentDrawer = this.target;
        if (fragmentDrawer == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentDrawer.mBtnAlarm = null;
        fragmentDrawer.mBtnOption = null;
        fragmentDrawer.mIvProfile = null;
        fragmentDrawer.mTvName = null;
        fragmentDrawer.mTvEmail = null;
        fragmentDrawer.mLayoutProfile = null;
        fragmentDrawer.mBaseRv = null;
    }
}
