package kr.timehub.beplan.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.EditText;

/* loaded from: classes.dex */
public class FragmentTaskDetail_ViewBinding implements Unbinder {
    private FragmentTaskDetail target;
    private View view2131361866;
    private View view2131361868;

    @UiThread
    public FragmentTaskDetail_ViewBinding(final FragmentTaskDetail fragmentTaskDetail, View view) {
        this.target = fragmentTaskDetail;
        fragmentTaskDetail.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
        fragmentTaskDetail.mIvCommentLeft = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_comment_left, "field 'mIvCommentLeft'", ImageView.class);
        fragmentTaskDetail.mBaseCommentViewpager = (BaseFileViewPager) Utils.findRequiredViewAsType(view, R.id.base_comment_viewpager, "field 'mBaseCommentViewpager'", BaseFileViewPager.class);
        fragmentTaskDetail.mIvCommentRight = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_comment_right, "field 'mIvCommentRight'", ImageView.class);
        fragmentTaskDetail.mLlCommentContents = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_comment_contents, "field 'mLlCommentContents'", LinearLayout.class);
        fragmentTaskDetail.mEtKeyword = (EditText) Utils.findRequiredViewAsType(view, R.id.et_keyword, "field 'mEtKeyword'", EditText.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.btn_upload, "field 'mBtnUpload' and method 'submitImgUpload'");
        fragmentTaskDetail.mBtnUpload = (ImageButton) Utils.castView(findRequiredView, R.id.btn_upload, "field 'mBtnUpload'", ImageButton.class);
        this.view2131361866 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentTaskDetail.submitImgUpload();
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_write, "field 'mBtnWrite' and method 'submitWrite'");
        fragmentTaskDetail.mBtnWrite = (ImageButton) Utils.castView(findRequiredView2, R.id.btn_write, "field 'mBtnWrite'", ImageButton.class);
        this.view2131361868 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                fragmentTaskDetail.submitWrite();
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FragmentTaskDetail fragmentTaskDetail = this.target;
        if (fragmentTaskDetail == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        fragmentTaskDetail.mBaseRv = null;
        fragmentTaskDetail.mIvCommentLeft = null;
        fragmentTaskDetail.mBaseCommentViewpager = null;
        fragmentTaskDetail.mIvCommentRight = null;
        fragmentTaskDetail.mLlCommentContents = null;
        fragmentTaskDetail.mEtKeyword = null;
        fragmentTaskDetail.mBtnUpload = null;
        fragmentTaskDetail.mBtnWrite = null;
        this.view2131361866.setOnClickListener(null);
        this.view2131361866 = null;
        this.view2131361868.setOnClickListener(null);
        this.view2131361868 = null;
    }
}
