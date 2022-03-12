package kr.timehub.beplan.dialog;

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
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogSelectProjectMyForm_ViewBinding implements Unbinder {
    private DialogSelectProjectMyForm target;
    private View view2131361954;
    private View view2131361984;

    @UiThread
    public DialogSelectProjectMyForm_ViewBinding(DialogSelectProjectMyForm dialogSelectProjectMyForm) {
        this(dialogSelectProjectMyForm, dialogSelectProjectMyForm.getWindow().getDecorView());
    }

    @UiThread
    public DialogSelectProjectMyForm_ViewBinding(final DialogSelectProjectMyForm dialogSelectProjectMyForm, View view) {
        this.target = dialogSelectProjectMyForm;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onViewClicked'");
        dialogSelectProjectMyForm.mIvClose = (ImageView) Utils.castView(findRequiredView, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogSelectProjectMyForm_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogSelectProjectMyForm.onViewClicked(view2);
            }
        });
        dialogSelectProjectMyForm.mTvVersion = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_version, "field 'mTvVersion'", TextView.class);
        dialogSelectProjectMyForm.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        dialogSelectProjectMyForm.mLlVersion = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_version, "field 'mLlVersion'", LinearLayout.class);
        dialogSelectProjectMyForm.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        dialogSelectProjectMyForm.mRlBg = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_bg, "field 'mRlBg'", RelativeLayout.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.iv_ok, "field 'mIvOk' and method 'onViewClicked'");
        dialogSelectProjectMyForm.mIvOk = (ImageView) Utils.castView(findRequiredView2, R.id.iv_ok, "field 'mIvOk'", ImageView.class);
        this.view2131361984 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogSelectProjectMyForm_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogSelectProjectMyForm.onViewClicked(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogSelectProjectMyForm dialogSelectProjectMyForm = this.target;
        if (dialogSelectProjectMyForm == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogSelectProjectMyForm.mIvClose = null;
        dialogSelectProjectMyForm.mTvVersion = null;
        dialogSelectProjectMyForm.mTvTitle = null;
        dialogSelectProjectMyForm.mLlVersion = null;
        dialogSelectProjectMyForm.mSpDropdown = null;
        dialogSelectProjectMyForm.mRlBg = null;
        dialogSelectProjectMyForm.mIvOk = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
        this.view2131361984.setOnClickListener(null);
        this.view2131361984 = null;
    }
}
