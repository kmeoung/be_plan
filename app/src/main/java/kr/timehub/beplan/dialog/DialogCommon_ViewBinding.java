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
public class DialogCommon_ViewBinding implements Unbinder {
    private DialogCommon target;
    private View view2131361853;
    private View view2131361857;
    private View view2131361954;

    @UiThread
    public DialogCommon_ViewBinding(DialogCommon dialogCommon) {
        this(dialogCommon, dialogCommon.getWindow().getDecorView());
    }

    @UiThread
    public DialogCommon_ViewBinding(final DialogCommon dialogCommon, View view) {
        this.target = dialogCommon;
        dialogCommon.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onViewClicked'");
        dialogCommon.mIvClose = (ImageView) Utils.castView(findRequiredView, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogCommon_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogCommon.onViewClicked();
            }
        });
        dialogCommon.mTvContents = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_contents, "field 'mTvContents'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_positive, "field 'mBtnPositive' and method 'onViewClicked'");
        dialogCommon.mBtnPositive = (Button) Utils.castView(findRequiredView2, R.id.btn_positive, "field 'mBtnPositive'", Button.class);
        this.view2131361857 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogCommon_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogCommon.onViewClicked(view2);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.btn_negative, "field 'mBtnNegative' and method 'onViewClicked'");
        dialogCommon.mBtnNegative = (Button) Utils.castView(findRequiredView3, R.id.btn_negative, "field 'mBtnNegative'", Button.class);
        this.view2131361853 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogCommon_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogCommon.onViewClicked(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogCommon dialogCommon = this.target;
        if (dialogCommon == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogCommon.mTvTitle = null;
        dialogCommon.mIvClose = null;
        dialogCommon.mTvContents = null;
        dialogCommon.mBtnPositive = null;
        dialogCommon.mBtnNegative = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
        this.view2131361857.setOnClickListener(null);
        this.view2131361857 = null;
        this.view2131361853.setOnClickListener(null);
        this.view2131361853 = null;
    }
}
