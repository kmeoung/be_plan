package kr.timehub.beplan.fragment.SideForm.common;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class FragmentFormProjectContainer_ViewBinding implements Unbinder {
    private FragmentFormProjectContainer target;

    @UiThread
    public FragmentFormProjectContainer_ViewBinding(FragmentFormProjectContainer fragmentFormProjectContainer, View view) {
        this.target = fragmentFormProjectContainer;
        fragmentFormProjectContainer.mBaseProjectContainer = (FrameLayout) Utils.findRequiredViewAsType(view, R.id.base_project_container, "field 'mBaseProjectContainer'", FrameLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentFormProjectContainer fragmentFormProjectContainer = this.target;
        if (fragmentFormProjectContainer == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentFormProjectContainer.mBaseProjectContainer = null;
    }
}
