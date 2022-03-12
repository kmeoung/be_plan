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
public class FragmentProjectSend_ViewBinding implements Unbinder {
    private FragmentProjectSend target;

    @UiThread
    public FragmentProjectSend_ViewBinding(FragmentProjectSend fragmentProjectSend, View view) {
        this.target = fragmentProjectSend;
        fragmentProjectSend.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentProjectSend.mTvList = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_list, "field 'mTvList'", TextView.class);
        fragmentProjectSend.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentProjectSend.mLlList = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_list, "field 'mLlList'", LinearLayout.class);
        fragmentProjectSend.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentProjectSend.mTvDefault = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_default, "field 'mTvDefault'", TextView.class);
        fragmentProjectSend.mLlSearch = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_search, "field 'mLlSearch'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentProjectSend fragmentProjectSend = this.target;
        if (fragmentProjectSend == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentProjectSend.mBaseRv = null;
        fragmentProjectSend.mTvList = null;
        fragmentProjectSend.mSpDropdown = null;
        fragmentProjectSend.mLlList = null;
        fragmentProjectSend.mEtKeyword = null;
        fragmentProjectSend.mTvDefault = null;
        fragmentProjectSend.mLlSearch = null;
    }
}
