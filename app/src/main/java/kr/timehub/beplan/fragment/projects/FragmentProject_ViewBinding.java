package kr.timehub.beplan.fragment.projects;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class FragmentProject_ViewBinding implements Unbinder {
    private FragmentProject target;

    @UiThread
    public FragmentProject_ViewBinding(FragmentProject fragmentProject, View view) {
        this.target = fragmentProject;
        fragmentProject.mTabLayout = (TabLayout) Utils.findRequiredViewAsType(view, R.id.tab_layout, "field 'mTabLayout'", TabLayout.class);
        fragmentProject.mVPager = (ViewPager) Utils.findRequiredViewAsType(view, R.id.vPager, "field 'mVPager'", ViewPager.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentProject fragmentProject = this.target;
        if (fragmentProject == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentProject.mTabLayout = null;
        fragmentProject.mVPager = null;
    }
}
