package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseToolBar;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentLoginContainer_ViewBinding implements Unbinder {
    private FragmentLoginContainer target;

    @UiThread
    public FragmentLoginContainer_ViewBinding(FragmentLoginContainer fragmentLoginContainer, View view) {
        this.target = fragmentLoginContainer;
        fragmentLoginContainer.mIvBack = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_back, "field 'mIvBack'", ImageView.class);
        fragmentLoginContainer.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        fragmentLoginContainer.mIvNext = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_next, "field 'mIvNext'", ImageView.class);
        fragmentLoginContainer.mBaseLoginContainer = (FrameLayout) Utils.findRequiredViewAsType(view, R.id.base_login_container, "field 'mBaseLoginContainer'", FrameLayout.class);
        fragmentLoginContainer.mBaseToolbar = (BaseToolBar) Utils.findRequiredViewAsType(view, R.id.base_toolbar, "field 'mBaseToolbar'", BaseToolBar.class);
        fragmentLoginContainer.mTvAppname = (ImageView) Utils.findRequiredViewAsType(view, R.id.tv_appname, "field 'mTvAppname'", ImageView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentLoginContainer fragmentLoginContainer = this.target;
        if (fragmentLoginContainer == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentLoginContainer.mIvBack = null;
        fragmentLoginContainer.mTvTitle = null;
        fragmentLoginContainer.mIvNext = null;
        fragmentLoginContainer.mBaseLoginContainer = null;
        fragmentLoginContainer.mBaseToolbar = null;
        fragmentLoginContainer.mTvAppname = null;
    }
}
