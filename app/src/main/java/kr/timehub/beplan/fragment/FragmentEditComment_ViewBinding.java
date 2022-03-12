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
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentEditComment_ViewBinding implements Unbinder {
    private FragmentEditComment target;
    private View view2131362175;
    private View view2131362246;

    @UiThread
    public FragmentEditComment_ViewBinding(final FragmentEditComment fragmentEditComment, View view) {
        this.target = fragmentEditComment;
        fragmentEditComment.mEtComment = (EditText) Utils.findRequiredViewAsType(view, R.id.et_comment, "field 'mEtComment'", EditText.class);
        fragmentEditComment.mIvLeft = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_left, "field 'mIvLeft'", ImageView.class);
        fragmentEditComment.mBaseVp = (BaseFileViewPager) Utils.findRequiredViewAsType(view, R.id.base_vp, "field 'mBaseVp'", BaseFileViewPager.class);
        fragmentEditComment.mIvRight = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_right, "field 'mIvRight'", ImageView.class);
        fragmentEditComment.mLlCommentContents = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_comment_contents, "field 'mLlCommentContents'", LinearLayout.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.tv_save, "field 'mTvSave' and method 'submitSave'");
        fragmentEditComment.mTvSave = (TextView) Utils.castView(findRequiredView, R.id.tv_save, "field 'mTvSave'", TextView.class);
        this.view2131362246 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentEditComment_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentEditComment.submitSave();
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.tv_add_img, "field 'mTvAddImg' and method 'submitAddImg'");
        fragmentEditComment.mTvAddImg = (TextView) Utils.castView(findRequiredView2, R.id.tv_add_img, "field 'mTvAddImg'", TextView.class);
        this.view2131362175 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentEditComment_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentEditComment.submitAddImg();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentEditComment fragmentEditComment = this.target;
        if (fragmentEditComment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentEditComment.mEtComment = null;
        fragmentEditComment.mIvLeft = null;
        fragmentEditComment.mBaseVp = null;
        fragmentEditComment.mIvRight = null;
        fragmentEditComment.mLlCommentContents = null;
        fragmentEditComment.mTvSave = null;
        fragmentEditComment.mTvAddImg = null;
        this.view2131362246.setOnClickListener(null);
        this.view2131362246 = null;
        this.view2131362175.setOnClickListener(null);
        this.view2131362175 = null;
    }
}
