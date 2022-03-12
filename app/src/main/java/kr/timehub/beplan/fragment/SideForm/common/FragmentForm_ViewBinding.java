package kr.timehub.beplan.fragment.SideForm.common;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class FragmentForm_ViewBinding implements Unbinder {
    private FragmentForm target;

    @UiThread
    public FragmentForm_ViewBinding(FragmentForm fragmentForm, View view) {
        this.target = fragmentForm;
        fragmentForm.mTabLayout = (TabLayout) Utils.findRequiredViewAsType(view, R.id.tab_layout, "field 'mTabLayout'", TabLayout.class);
        fragmentForm.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentForm.mIvDropdown = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_dropdown, "field 'mIvDropdown'", ImageView.class);
        fragmentForm.mLlDropdown = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_dropdown, "field 'mLlDropdown'", LinearLayout.class);
        fragmentForm.mLlDown = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_down, "field 'mLlDown'", LinearLayout.class);
        fragmentForm.mVPager = (ViewPager) Utils.findRequiredViewAsType(view, R.id.vPager, "field 'mVPager'", ViewPager.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentForm fragmentForm = this.target;
        if (fragmentForm == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentForm.mTabLayout = null;
        fragmentForm.mSpDropdown = null;
        fragmentForm.mIvDropdown = null;
        fragmentForm.mLlDropdown = null;
        fragmentForm.mLlDown = null;
        fragmentForm.mVPager = null;
    }
}
