package kr.timehub.beplan.base.objects;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class BaseToolBar_ViewBinding implements Unbinder {
    private BaseToolBar target;
    private View view2131361952;
    private View view2131361981;

    @UiThread
    public BaseToolBar_ViewBinding(BaseToolBar baseToolBar) {
        this(baseToolBar, baseToolBar);
    }

    @UiThread
    public BaseToolBar_ViewBinding(final BaseToolBar baseToolBar, View view) {
        this.target = baseToolBar;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_back, "field 'mIvBack' and method 'onClick'");
        baseToolBar.mIvBack = (ImageView) Utils.castView(findRequiredView, R.id.iv_back, "field 'mIvBack'", ImageView.class);
        this.view2131361952 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.base.objects.BaseToolBar_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                baseToolBar.onClick(view2);
            }
        });
        baseToolBar.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.iv_next, "field 'mIvNext' and method 'onClick'");
        baseToolBar.mIvNext = (ImageView) Utils.castView(findRequiredView2, R.id.iv_next, "field 'mIvNext'", ImageView.class);
        this.view2131361981 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.base.objects.BaseToolBar_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                baseToolBar.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        BaseToolBar baseToolBar = this.target;
        if (baseToolBar == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        baseToolBar.mIvBack = null;
        baseToolBar.mTvTitle = null;
        baseToolBar.mIvNext = null;
        this.view2131361952.setOnClickListener(null);
        this.view2131361952 = null;
        this.view2131361981.setOnClickListener(null);
        this.view2131361981 = null;
    }
}
