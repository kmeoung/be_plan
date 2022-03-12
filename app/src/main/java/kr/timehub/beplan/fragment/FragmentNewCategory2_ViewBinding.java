package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentNewCategory2_ViewBinding implements Unbinder {
    private FragmentNewCategory2 target;
    private View view2131361859;
    private View view2131362230;

    @UiThread
    public FragmentNewCategory2_ViewBinding(final FragmentNewCategory2 fragmentNewCategory2, View view) {
        this.target = fragmentNewCategory2;
        fragmentNewCategory2.mEtCategotyTitle = (EditText) Utils.findRequiredViewAsType(view, R.id.et_categoty_title, "field 'mEtCategotyTitle'", EditText.class);
        fragmentNewCategory2.mEtBusinessContent = (EditText) Utils.findRequiredViewAsType(view, R.id.et_business_content, "field 'mEtBusinessContent'", EditText.class);
        fragmentNewCategory2.mTvRep = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_rep, "field 'mTvRep'", TextView.class);
        fragmentNewCategory2.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentNewCategory2.mRlRep = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_rep, "field 'mRlRep'", RelativeLayout.class);
        fragmentNewCategory2.mIvPhoto = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
        fragmentNewCategory2.mTvName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_name, "field 'mTvName'", TextView.class);
        fragmentNewCategory2.mIvMe = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_me, "field 'mIvMe'", ImageView.class);
        fragmentNewCategory2.mTvEmail = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_email, "field 'mTvEmail'", TextView.class);
        fragmentNewCategory2.mLlCancel = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_cancel, "field 'mLlCancel'", LinearLayout.class);
        fragmentNewCategory2.mLlSelectRep = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_select_rep, "field 'mLlSelectRep'", LinearLayout.class);
        fragmentNewCategory2.mEtBusinessComment = (EditText) Utils.findRequiredViewAsType(view, R.id.et_business_comment, "field 'mEtBusinessComment'", EditText.class);
        fragmentNewCategory2.mIvLeft = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_left, "field 'mIvLeft'", ImageView.class);
        fragmentNewCategory2.mBaseVp = (BaseFileViewPager) Utils.findRequiredViewAsType(view, R.id.base_vp, "field 'mBaseVp'", BaseFileViewPager.class);
        fragmentNewCategory2.mIvRight = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_right, "field 'mIvRight'", ImageView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.tv_photo, "field 'mTvPhoto' and method 'submitPhoto'");
        fragmentNewCategory2.mTvPhoto = (TextView) Utils.castView(findRequiredView, R.id.tv_photo, "field 'mTvPhoto'", TextView.class);
        this.view2131362230 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentNewCategory2.submitPhoto();
            }
        });
        fragmentNewCategory2.mTvVideo = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_video, "field 'mTvVideo'", TextView.class);
        fragmentNewCategory2.mTvLink = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_link, "field 'mTvLink'", TextView.class);
        fragmentNewCategory2.mIvLimitSwitch = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_limit_switch, "field 'mIvLimitSwitch'", ImageView.class);
        fragmentNewCategory2.mTvTermStartDate = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_term_start_date, "field 'mTvTermStartDate'", TextView.class);
        fragmentNewCategory2.mTvTermFinishDate = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_term_finish_date, "field 'mTvTermFinishDate'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_save, "field 'mBtnSave' and method 'onClick'");
        fragmentNewCategory2.mBtnSave = (Button) Utils.castView(findRequiredView2, R.id.btn_save, "field 'mBtnSave'", Button.class);
        this.view2131361859 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentNewCategory2.onClick();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentNewCategory2 fragmentNewCategory2 = this.target;
        if (fragmentNewCategory2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentNewCategory2.mEtCategotyTitle = null;
        fragmentNewCategory2.mEtBusinessContent = null;
        fragmentNewCategory2.mTvRep = null;
        fragmentNewCategory2.mSpDropdown = null;
        fragmentNewCategory2.mRlRep = null;
        fragmentNewCategory2.mIvPhoto = null;
        fragmentNewCategory2.mTvName = null;
        fragmentNewCategory2.mIvMe = null;
        fragmentNewCategory2.mTvEmail = null;
        fragmentNewCategory2.mLlCancel = null;
        fragmentNewCategory2.mLlSelectRep = null;
        fragmentNewCategory2.mEtBusinessComment = null;
        fragmentNewCategory2.mIvLeft = null;
        fragmentNewCategory2.mBaseVp = null;
        fragmentNewCategory2.mIvRight = null;
        fragmentNewCategory2.mTvPhoto = null;
        fragmentNewCategory2.mTvVideo = null;
        fragmentNewCategory2.mTvLink = null;
        fragmentNewCategory2.mIvLimitSwitch = null;
        fragmentNewCategory2.mTvTermStartDate = null;
        fragmentNewCategory2.mTvTermFinishDate = null;
        fragmentNewCategory2.mBtnSave = null;
        this.view2131362230.setOnClickListener(null);
        this.view2131362230 = null;
        this.view2131361859.setOnClickListener(null);
        this.view2131361859 = null;
    }
}
