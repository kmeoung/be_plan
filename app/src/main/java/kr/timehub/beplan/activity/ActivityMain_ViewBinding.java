package kr.timehub.beplan.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;

/* loaded from: classes.dex */
public class ActivityMain_ViewBinding implements Unbinder {
    private ActivityMain target;

    @UiThread
    public ActivityMain_ViewBinding(ActivityMain activityMain) {
        this(activityMain, activityMain.getWindow().getDecorView());
    }

    @UiThread
    public ActivityMain_ViewBinding(ActivityMain activityMain, View view) {
        this.target = activityMain;
        activityMain.mVState = Utils.findRequiredView(view, R.id.v_state, "field 'mVState'");
        activityMain.mBaseToolbar = (BaseMainToolBar) Utils.findRequiredViewAsType(view, R.id.base_toolbar, "field 'mBaseToolbar'", BaseMainToolBar.class);
        activityMain.mBaseContainer = (FrameLayout) Utils.findRequiredViewAsType(view, R.id.base_container, "field 'mBaseContainer'", FrameLayout.class);
        activityMain.mLayoutSlideMenu = (FrameLayout) Utils.findRequiredViewAsType(view, R.id.layout_slide_menu, "field 'mLayoutSlideMenu'", FrameLayout.class);
        activityMain.mLayoutDrawer = (DrawerLayout) Utils.findRequiredViewAsType(view, R.id.layout_drawer, "field 'mLayoutDrawer'", DrawerLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivityMain activityMain = this.target;
        if (activityMain == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activityMain.mVState = null;
        activityMain.mBaseToolbar = null;
        activityMain.mBaseContainer = null;
        activityMain.mLayoutSlideMenu = null;
        activityMain.mLayoutDrawer = null;
    }
}
