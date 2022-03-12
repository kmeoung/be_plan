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
public class FragmentProject2_ViewBinding implements Unbinder {
    private FragmentProject2 target;

    @UiThread
    public FragmentProject2_ViewBinding(FragmentProject2 fragmentProject2, View view) {
        this.target = fragmentProject2;
        fragmentProject2.mTabLayout = (TabLayout) Utils.findRequiredViewAsType(view, R.id.tab_layout, "field 'mTabLayout'", TabLayout.class);
        fragmentProject2.mVPager = (ViewPager) Utils.findRequiredViewAsType(view, R.id.vPager, "field 'mVPager'", ViewPager.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentProject2 fragmentProject2 = this.target;
        if (fragmentProject2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentProject2.mTabLayout = null;
        fragmentProject2.mVPager = null;
    }
}
