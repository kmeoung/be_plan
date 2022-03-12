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
public class FragmentNewTask_ViewBinding implements Unbinder {
    private FragmentNewTask target;
    private View view2131361859;
    private View view2131362230;

    @UiThread
    public FragmentNewTask_ViewBinding(final FragmentNewTask fragmentNewTask, View view) {
        this.target = fragmentNewTask;
        fragmentNewTask.mTvCategotyTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_categoty_title, "field 'mTvCategotyTitle'", TextView.class);
        fragmentNewTask.mEtBusinessContent = (EditText) Utils.findRequiredViewAsType(view, R.id.et_business_content, "field 'mEtBusinessContent'", EditText.class);
        fragmentNewTask.mTvRep = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_rep, "field 'mTvRep'", TextView.class);
        fragmentNewTask.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        fragmentNewTask.mRlRep = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_rep, "field 'mRlRep'", RelativeLayout.class);
        fragmentNewTask.mIvPhoto = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
        fragmentNewTask.mTvName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_name, "field 'mTvName'", TextView.class);
        fragmentNewTask.mIvMe = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_me, "field 'mIvMe'", ImageView.class);
        fragmentNewTask.mTvEmail = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_email, "field 'mTvEmail'", TextView.class);
        fragmentNewTask.mLlCancel = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_cancel, "field 'mLlCancel'", LinearLayout.class);
        fragmentNewTask.mLlSelectRep = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_select_rep, "field 'mLlSelectRep'", LinearLayout.class);
        fragmentNewTask.mEtBusinessComment = (EditText) Utils.findRequiredViewAsType(view, R.id.et_business_comment, "field 'mEtBusinessComment'", EditText.class);
        fragmentNewTask.mIvLeft = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_left, "field 'mIvLeft'", ImageView.class);
        fragmentNewTask.mBaseVp = (BaseFileViewPager) Utils.findRequiredViewAsType(view, R.id.base_vp, "field 'mBaseVp'", BaseFileViewPager.class);
        fragmentNewTask.mIvRight = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_right, "field 'mIvRight'", ImageView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.tv_photo, "field 'mTvPhoto' and method 'submitPhoto'");
        fragmentNewTask.mTvPhoto = (TextView) Utils.castView(findRequiredView, R.id.tv_photo, "field 'mTvPhoto'", TextView.class);
        this.view2131362230 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewTask_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentNewTask.submitPhoto();
            }
        });
        fragmentNewTask.mTvVideo = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_video, "field 'mTvVideo'", TextView.class);
        fragmentNewTask.mTvLink = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_link, "field 'mTvLink'", TextView.class);
        fragmentNewTask.mIvLimitSwitch = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_limit_switch, "field 'mIvLimitSwitch'", ImageView.class);
        fragmentNewTask.mTvTermStartDate = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_term_start_date, "field 'mTvTermStartDate'", TextView.class);
        fragmentNewTask.mTvTermFinishDate = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_term_finish_date, "field 'mTvTermFinishDate'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_save, "field 'mBtnSave' and method 'onClick'");
        fragmentNewTask.mBtnSave = (Button) Utils.castView(findRequiredView2, R.id.btn_save, "field 'mBtnSave'", Button.class);
        this.view2131361859 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewTask_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentNewTask.onClick();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentNewTask fragmentNewTask = this.target;
        if (fragmentNewTask == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentNewTask.mTvCategotyTitle = null;
        fragmentNewTask.mEtBusinessContent = null;
        fragmentNewTask.mTvRep = null;
        fragmentNewTask.mSpDropdown = null;
        fragmentNewTask.mRlRep = null;
        fragmentNewTask.mIvPhoto = null;
        fragmentNewTask.mTvName = null;
        fragmentNewTask.mIvMe = null;
        fragmentNewTask.mTvEmail = null;
        fragmentNewTask.mLlCancel = null;
        fragmentNewTask.mLlSelectRep = null;
        fragmentNewTask.mEtBusinessComment = null;
        fragmentNewTask.mIvLeft = null;
        fragmentNewTask.mBaseVp = null;
        fragmentNewTask.mIvRight = null;
        fragmentNewTask.mTvPhoto = null;
        fragmentNewTask.mTvVideo = null;
        fragmentNewTask.mTvLink = null;
        fragmentNewTask.mIvLimitSwitch = null;
        fragmentNewTask.mTvTermStartDate = null;
        fragmentNewTask.mTvTermFinishDate = null;
        fragmentNewTask.mBtnSave = null;
        this.view2131362230.setOnClickListener(null);
        this.view2131362230 = null;
        this.view2131361859.setOnClickListener(null);
        this.view2131361859 = null;
    }
}
