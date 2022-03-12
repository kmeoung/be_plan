package kr.timehub.beplan.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogEmailCheck_ViewBinding implements Unbinder {
    private DialogEmailCheck target;
    private View view2131361853;
    private View view2131361857;
    private View view2131361954;

    @UiThread
    public DialogEmailCheck_ViewBinding(DialogEmailCheck dialogEmailCheck) {
        this(dialogEmailCheck, dialogEmailCheck.getWindow().getDecorView());
    }

    @UiThread
    public DialogEmailCheck_ViewBinding(final DialogEmailCheck dialogEmailCheck, View view) {
        this.target = dialogEmailCheck;
        dialogEmailCheck.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onViewClicked'");
        dialogEmailCheck.mIvClose = (ImageView) Utils.castView(findRequiredView, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogEmailCheck_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogEmailCheck.onViewClicked(view2);
            }
        });
        dialogEmailCheck.mTvContents = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_contents, "field 'mTvContents'", TextView.class);
        dialogEmailCheck.mTvEmail = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_email, "field 'mTvEmail'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_positive, "field 'mBtnPositive' and method 'onViewClicked'");
        dialogEmailCheck.mBtnPositive = (Button) Utils.castView(findRequiredView2, R.id.btn_positive, "field 'mBtnPositive'", Button.class);
        this.view2131361857 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogEmailCheck_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogEmailCheck.onViewClicked(view2);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.btn_negative, "field 'mBtnNegative' and method 'onViewClicked'");
        dialogEmailCheck.mBtnNegative = (Button) Utils.castView(findRequiredView3, R.id.btn_negative, "field 'mBtnNegative'", Button.class);
        this.view2131361853 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogEmailCheck_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogEmailCheck.onViewClicked(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogEmailCheck dialogEmailCheck = this.target;
        if (dialogEmailCheck == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogEmailCheck.mTvTitle = null;
        dialogEmailCheck.mIvClose = null;
        dialogEmailCheck.mTvContents = null;
        dialogEmailCheck.mTvEmail = null;
        dialogEmailCheck.mBtnPositive = null;
        dialogEmailCheck.mBtnNegative = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
        this.view2131361857.setOnClickListener(null);
        this.view2131361857 = null;
        this.view2131361853.setOnClickListener(null);
        this.view2131361853 = null;
    }
}
