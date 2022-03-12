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
public class FragmentProjectReceive_ViewBinding implements Unbinder {
    private FragmentProjectReceive target;

    @UiThread
    public FragmentProjectReceive_ViewBinding(FragmentProjectReceive fragmentProjectReceive, View view) {
        this.target = fragmentProjectReceive;
        fragmentProjectReceive.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentProjectReceive.mTvList = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_list, "field 'mTvList'", TextView.class);
        fragmentProjectReceive.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentProjectReceive.mLlList = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_list, "field 'mLlList'", LinearLayout.class);
        fragmentProjectReceive.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentProjectReceive.mTvDefault = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_default, "field 'mTvDefault'", TextView.class);
        fragmentProjectReceive.mLlSearch = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_search, "field 'mLlSearch'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentProjectReceive fragmentProjectReceive = this.target;
        if (fragmentProjectReceive == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentProjectReceive.mBaseRv = null;
        fragmentProjectReceive.mTvList = null;
        fragmentProjectReceive.mSpDropdown = null;
        fragmentProjectReceive.mLlList = null;
        fragmentProjectReceive.mEtKeyword = null;
        fragmentProjectReceive.mTvDefault = null;
        fragmentProjectReceive.mLlSearch = null;
    }
}
