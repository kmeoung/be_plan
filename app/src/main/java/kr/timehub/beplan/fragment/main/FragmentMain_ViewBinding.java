package kr.timehub.beplan.fragment.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;

/* loaded from: classes.dex */
public class FragmentMain_ViewBinding implements Unbinder {
    private FragmentMain target;

    @UiThread
    public FragmentMain_ViewBinding(FragmentMain fragmentMain, View view) {
        this.target = fragmentMain;
        fragmentMain.mTvText = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_text, "field 'mTvText'", TextView.class);
        fragmentMain.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        fragmentMain.baseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'baseRv'", RecyclerView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentMain fragmentMain = this.target;
        if (fragmentMain == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentMain.mTvText = null;
        fragmentMain.mEtKeyword = null;
        fragmentMain.baseRv = null;
    }
}
