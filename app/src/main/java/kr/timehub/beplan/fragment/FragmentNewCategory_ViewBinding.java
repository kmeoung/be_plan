package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;

/* loaded from: classes.dex */
public class FragmentNewCategory_ViewBinding implements Unbinder {
    private FragmentNewCategory target;
    private View view2131361859;

    @UiThread
    public FragmentNewCategory_ViewBinding(final FragmentNewCategory fragmentNewCategory, View view) {
        this.target = fragmentNewCategory;
        fragmentNewCategory.mEtCategotyTitle = (EditText) Utils.findRequiredViewAsType(view, R.id.et_categoty_title, "field 'mEtCategotyTitle'", EditText.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.btn_save, "field 'mBtnSave' and method 'onClick'");
        fragmentNewCategory.mBtnSave = (Button) Utils.castView(findRequiredView, R.id.btn_save, "field 'mBtnSave'", Button.class);
        this.view2131361859 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentNewCategory.onClick();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentNewCategory fragmentNewCategory = this.target;
        if (fragmentNewCategory == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentNewCategory.mEtCategotyTitle = null;
        fragmentNewCategory.mBtnSave = null;
        this.view2131361859.setOnClickListener(null);
        this.view2131361859 = null;
    }
}
