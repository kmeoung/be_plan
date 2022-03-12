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
public class DialogLogout_ViewBinding implements Unbinder {
    private DialogLogout target;
    private View view2131361854;
    private View view2131361869;
    private View view2131361954;

    @UiThread
    public DialogLogout_ViewBinding(DialogLogout dialogLogout) {
        this(dialogLogout, dialogLogout.getWindow().getDecorView());
    }

    @UiThread
    public DialogLogout_ViewBinding(final DialogLogout dialogLogout, View view) {
        this.target = dialogLogout;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onClick'");
        dialogLogout.mIvClose = (ImageView) Utils.castView(findRequiredView, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogLogout_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogLogout.onClick(view2);
            }
        });
        dialogLogout.mTvComment = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_comment, "field 'mTvComment'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_yes, "field 'mBtnYes' and method 'onClick'");
        dialogLogout.mBtnYes = (Button) Utils.castView(findRequiredView2, R.id.btn_yes, "field 'mBtnYes'", Button.class);
        this.view2131361869 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogLogout_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogLogout.onClick(view2);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.btn_no, "field 'mBtnNo' and method 'onClick'");
        dialogLogout.mBtnNo = (Button) Utils.castView(findRequiredView3, R.id.btn_no, "field 'mBtnNo'", Button.class);
        this.view2131361854 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogLogout_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogLogout.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogLogout dialogLogout = this.target;
        if (dialogLogout == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogLogout.mIvClose = null;
        dialogLogout.mTvComment = null;
        dialogLogout.mBtnYes = null;
        dialogLogout.mBtnNo = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
        this.view2131361869.setOnClickListener(null);
        this.view2131361869 = null;
        this.view2131361854.setOnClickListener(null);
        this.view2131361854 = null;
    }
}
