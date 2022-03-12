package kr.timehub.beplan.base.widgets;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class BaseMainToolBar_ViewBinding implements Unbinder {
    private BaseMainToolBar target;
    private View view2131361951;
    private View view2131361954;
    private View view2131361978;
    private View view2131361979;

    @UiThread
    public BaseMainToolBar_ViewBinding(BaseMainToolBar baseMainToolBar) {
        this(baseMainToolBar, baseMainToolBar);
    }

    @UiThread
    public BaseMainToolBar_ViewBinding(final BaseMainToolBar baseMainToolBar, View view) {
        this.target = baseMainToolBar;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_menu, "field 'mIvMenu' and method 'onClick'");
        baseMainToolBar.mIvMenu = (ImageView) Utils.castView(findRequiredView, R.id.iv_menu, "field 'mIvMenu'", ImageView.class);
        this.view2131361978 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.base.widgets.BaseMainToolBar_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                baseMainToolBar.onClick(view2);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onClick'");
        baseMainToolBar.mIvClose = (ImageView) Utils.castView(findRequiredView2, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.base.widgets.BaseMainToolBar_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                baseMainToolBar.onClick(view2);
            }
        });
        baseMainToolBar.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        View findRequiredView3 = Utils.findRequiredView(view, R.id.iv_add, "field 'mIvAdd' and method 'onClick'");
        baseMainToolBar.mIvAdd = (ImageView) Utils.castView(findRequiredView3, R.id.iv_add, "field 'mIvAdd'", ImageView.class);
        this.view2131361951 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.base.widgets.BaseMainToolBar_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                baseMainToolBar.onClick(view2);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, R.id.iv_more, "field 'mIvMore' and method 'onClick'");
        baseMainToolBar.mIvMore = (ImageView) Utils.castView(findRequiredView4, R.id.iv_more, "field 'mIvMore'", ImageView.class);
        this.view2131361979 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.base.widgets.BaseMainToolBar_ViewBinding.4
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                baseMainToolBar.onClick(view2);
            }
        });
        baseMainToolBar.mTvCash = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_cash, "field 'mTvCash'", TextView.class);
        baseMainToolBar.mLlMyCash = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_my_cash, "field 'mLlMyCash'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        BaseMainToolBar baseMainToolBar = this.target;
        if (baseMainToolBar == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        baseMainToolBar.mIvMenu = null;
        baseMainToolBar.mIvClose = null;
        baseMainToolBar.mTvTitle = null;
        baseMainToolBar.mIvAdd = null;
        baseMainToolBar.mIvMore = null;
        baseMainToolBar.mTvCash = null;
        baseMainToolBar.mLlMyCash = null;
        this.view2131361978.setOnClickListener(null);
        this.view2131361978 = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
        this.view2131361951.setOnClickListener(null);
        this.view2131361951 = null;
        this.view2131361979.setOnClickListener(null);
        this.view2131361979 = null;
    }
}
