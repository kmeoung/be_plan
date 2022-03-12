package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentProjectMy_ViewBinding implements Unbinder {
    private FragmentProjectMy target;

    @UiThread
    public FragmentProjectMy_ViewBinding(FragmentProjectMy fragmentProjectMy, View view) {
        this.target = fragmentProjectMy;
        fragmentProjectMy.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentProjectMy.mTvList = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_list, "field 'mTvList'", TextView.class);
        fragmentProjectMy.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentProjectMy.mLlList = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_list, "field 'mLlList'", LinearLayout.class);
        fragmentProjectMy.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentProjectMy fragmentProjectMy = this.target;
        if (fragmentProjectMy == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentProjectMy.mBaseRv = null;
        fragmentProjectMy.mTvList = null;
        fragmentProjectMy.mSpDropdown = null;
        fragmentProjectMy.mLlList = null;
        fragmentProjectMy.mEtKeyword = null;
    }
}
