package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;

/* loaded from: classes.dex */
public class FragmentEditCategoryRep_ViewBinding implements Unbinder {
    private FragmentEditCategoryRep target;
    private View view2131361859;

    @UiThread
    public FragmentEditCategoryRep_ViewBinding(final FragmentEditCategoryRep fragmentEditCategoryRep, View view) {
        this.target = fragmentEditCategoryRep;
        fragmentEditCategoryRep.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.btn_save, "field 'mBtnSave' and method 'onViewClicked'");
        fragmentEditCategoryRep.mBtnSave = (Button) Utils.castView(findRequiredView, R.id.btn_save, "field 'mBtnSave'", Button.class);
        this.view2131361859 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentEditCategoryRep_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentEditCategoryRep.onViewClicked();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentEditCategoryRep fragmentEditCategoryRep = this.target;
        if (fragmentEditCategoryRep == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentEditCategoryRep.mBaseRv = null;
        fragmentEditCategoryRep.mBtnSave = null;
        this.view2131361859.setOnClickListener(null);
        this.view2131361859 = null;
    }
}
