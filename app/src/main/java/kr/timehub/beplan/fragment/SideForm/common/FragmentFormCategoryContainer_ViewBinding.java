package kr.timehub.beplan.fragment.SideForm.common;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class FragmentFormCategoryContainer_ViewBinding implements Unbinder {
    private FragmentFormCategoryContainer target;

    @UiThread
    public FragmentFormCategoryContainer_ViewBinding(FragmentFormCategoryContainer fragmentFormCategoryContainer, View view) {
        this.target = fragmentFormCategoryContainer;
        fragmentFormCategoryContainer.mBaseCategoryContainer = (FrameLayout) Utils.findRequiredViewAsType(view, R.id.base_category_container, "field 'mBaseCategoryContainer'", FrameLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentFormCategoryContainer fragmentFormCategoryContainer = this.target;
        if (fragmentFormCategoryContainer == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentFormCategoryContainer.mBaseCategoryContainer = null;
    }
}
