package kr.timehub.beplan.fragment.SideForm.formShop;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentFormShopProject_ViewBinding implements Unbinder {
    private FragmentFormShopProject target;

    @UiThread
    public FragmentFormShopProject_ViewBinding(FragmentFormShopProject fragmentFormShopProject, View view) {
        this.target = fragmentFormShopProject;
        fragmentFormShopProject.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentFormShopProject.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentFormShopProject.mTvDefault = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_default, "field 'mTvDefault'", TextView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentFormShopProject fragmentFormShopProject = this.target;
        if (fragmentFormShopProject == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentFormShopProject.mBaseRv = null;
        fragmentFormShopProject.mEtKeyword = null;
        fragmentFormShopProject.mTvDefault = null;
    }
}
