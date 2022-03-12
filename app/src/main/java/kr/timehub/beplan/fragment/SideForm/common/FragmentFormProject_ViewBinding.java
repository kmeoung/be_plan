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
public class FragmentFormProject_ViewBinding implements Unbinder {
    private FragmentFormProject target;

    @UiThread
    public FragmentFormProject_ViewBinding(FragmentFormProject fragmentFormProject, View view) {
        this.target = fragmentFormProject;
        fragmentFormProject.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentFormProject.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentFormProject fragmentFormProject = this.target;
        if (fragmentFormProject == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentFormProject.mBaseRv = null;
        fragmentFormProject.mEtKeyword = null;
    }
}
