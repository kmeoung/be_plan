package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentMyInfo_ViewBinding implements Unbinder {
    private FragmentMyInfo target;
    private View view2131361987;
    private View view2131362046;

    @UiThread
    public FragmentMyInfo_ViewBinding(final FragmentMyInfo fragmentMyInfo, View view) {
        this.target = fragmentMyInfo;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_photo, "field 'mIvPhoto' and method 'onViewClicked'");
        fragmentMyInfo.mIvPhoto = (ImageView) Utils.castView(findRequiredView, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
        this.view2131361987 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentMyInfo_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentMyInfo.onViewClicked(view2);
            }
        });
        fragmentMyInfo.mEtName = (EditText) Utils.findRequiredViewAsType(view, R.id.et_name, "field 'mEtName'", EditText.class);
        fragmentMyInfo.mTvEmail = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_email, "field 'mTvEmail'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.ll_save, "field 'mLlSave' and method 'onViewClicked'");
        fragmentMyInfo.mLlSave = (LinearLayout) Utils.castView(findRequiredView2, R.id.ll_save, "field 'mLlSave'", LinearLayout.class);
        this.view2131362046 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentMyInfo_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentMyInfo.onViewClicked(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentMyInfo fragmentMyInfo = this.target;
        if (fragmentMyInfo == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentMyInfo.mIvPhoto = null;
        fragmentMyInfo.mEtName = null;
        fragmentMyInfo.mTvEmail = null;
        fragmentMyInfo.mLlSave = null;
        this.view2131361987.setOnClickListener(null);
        this.view2131361987 = null;
        this.view2131362046.setOnClickListener(null);
        this.view2131362046 = null;
    }
}
