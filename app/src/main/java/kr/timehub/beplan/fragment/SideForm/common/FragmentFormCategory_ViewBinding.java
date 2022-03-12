package kr.timehub.beplan.fragment.SideForm.common;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;

/* loaded from: classes.dex */
public class FragmentFormCategory_ViewBinding implements Unbinder {
    private FragmentFormCategory target;

    @UiThread
    public FragmentFormCategory_ViewBinding(FragmentFormCategory fragmentFormCategory, View view) {
        this.target = fragmentFormCategory;
        fragmentFormCategory.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentFormCategory.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentFormCategory fragmentFormCategory = this.target;
        if (fragmentFormCategory == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentFormCategory.mBaseRv = null;
        fragmentFormCategory.mEtKeyword = null;
    }
}
