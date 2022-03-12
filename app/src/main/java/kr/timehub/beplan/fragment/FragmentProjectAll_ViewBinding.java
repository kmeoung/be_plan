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
public class FragmentProjectAll_ViewBinding implements Unbinder {
    private FragmentProjectAll target;

    @UiThread
    public FragmentProjectAll_ViewBinding(FragmentProjectAll fragmentProjectAll, View view) {
        this.target = fragmentProjectAll;
        fragmentProjectAll.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentProjectAll.mTvList = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_list, "field 'mTvList'", TextView.class);
        fragmentProjectAll.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentProjectAll.mLlList = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_list, "field 'mLlList'", LinearLayout.class);
        fragmentProjectAll.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentProjectAll fragmentProjectAll = this.target;
        if (fragmentProjectAll == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentProjectAll.mBaseRv = null;
        fragmentProjectAll.mTvList = null;
        fragmentProjectAll.mSpDropdown = null;
        fragmentProjectAll.mLlList = null;
        fragmentProjectAll.mEtKeyword = null;
    }
}
