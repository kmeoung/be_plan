package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class FragmentIntro_ViewBinding implements Unbinder {
    private FragmentIntro target;

    @UiThread
    public FragmentIntro_ViewBinding(FragmentIntro fragmentIntro, View view) {
        this.target = fragmentIntro;
        fragmentIntro.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        fragmentIntro.mIvLogo = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_logo, "field 'mIvLogo'", ImageView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentIntro fragmentIntro = this.target;
        if (fragmentIntro == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentIntro.mVStatus = null;
        fragmentIntro.mIvLogo = null;
    }
}
