package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class FragmentTeamManagement_ViewBinding implements Unbinder {
    private FragmentTeamManagement target;

    @UiThread
    public FragmentTeamManagement_ViewBinding(FragmentTeamManagement fragmentTeamManagement, View view) {
        this.target = fragmentTeamManagement;
        fragmentTeamManagement.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentTeamManagement fragmentTeamManagement = this.target;
        if (fragmentTeamManagement == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentTeamManagement.mBaseRv = null;
    }
}
