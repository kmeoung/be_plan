package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentTimeLine_ViewBinding implements Unbinder {
    private FragmentTimeLine target;
    private View view2131361971;
    private View view2131361995;

    @UiThread
    public FragmentTimeLine_ViewBinding(final FragmentTimeLine fragmentTimeLine, View view) {
        this.target = fragmentTimeLine;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_left, "field 'mIvLeft' and method 'onViewClicked'");
        fragmentTimeLine.mIvLeft = (ImageView) Utils.castView(findRequiredView, R.id.iv_left, "field 'mIvLeft'", ImageView.class);
        this.view2131361971 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTimeLine_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentTimeLine.onViewClicked(view2);
            }
        });
        fragmentTimeLine.mTvDate = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_date, "field 'mTvDate'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.iv_right, "field 'mIvRight' and method 'onViewClicked'");
        fragmentTimeLine.mIvRight = (ImageView) Utils.castView(findRequiredView2, R.id.iv_right, "field 'mIvRight'", ImageView.class);
        this.view2131361995 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTimeLine_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentTimeLine.onViewClicked(view2);
            }
        });
        fragmentTimeLine.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentTimeLine fragmentTimeLine = this.target;
        if (fragmentTimeLine == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentTimeLine.mIvLeft = null;
        fragmentTimeLine.mTvDate = null;
        fragmentTimeLine.mIvRight = null;
        fragmentTimeLine.mBaseRv = null;
        this.view2131361971.setOnClickListener(null);
        this.view2131361971 = null;
        this.view2131361995.setOnClickListener(null);
        this.view2131361995 = null;
    }
}
