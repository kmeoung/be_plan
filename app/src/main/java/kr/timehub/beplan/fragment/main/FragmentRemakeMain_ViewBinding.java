package kr.timehub.beplan.fragment.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentRemakeMain_ViewBinding implements Unbinder {
    private FragmentRemakeMain target;
    private View view2131362226;

    @UiThread
    public FragmentRemakeMain_ViewBinding(final FragmentRemakeMain fragmentRemakeMain, View view) {
        this.target = fragmentRemakeMain;
        fragmentRemakeMain.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentRemakeMain.mTvProjectSize = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_project_size, "field 'mTvProjectSize'", TextView.class);
        fragmentRemakeMain.mTvMyWorkSize = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_my_work_size, "field 'mTvMyWorkSize'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.tv_notification_size, "field 'mTvNotificationSize' and method 'submitConfirm'");
        fragmentRemakeMain.mTvNotificationSize = (TextView) Utils.castView(findRequiredView, R.id.tv_notification_size, "field 'mTvNotificationSize'", TextView.class);
        this.view2131362226 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentRemakeMain_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentRemakeMain.submitConfirm();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentRemakeMain fragmentRemakeMain = this.target;
        if (fragmentRemakeMain == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentRemakeMain.mBaseRv = null;
        fragmentRemakeMain.mTvProjectSize = null;
        fragmentRemakeMain.mTvMyWorkSize = null;
        fragmentRemakeMain.mTvNotificationSize = null;
        this.view2131362226.setOnClickListener(null);
        this.view2131362226 = null;
    }
}
