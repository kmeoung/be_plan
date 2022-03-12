package kr.timehub.beplan.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;

/* loaded from: classes.dex */
public class DialogRefuse_ViewBinding implements Unbinder {
    private DialogRefuse target;
    private View view2131361863;
    private View view2131361954;

    @UiThread
    public DialogRefuse_ViewBinding(DialogRefuse dialogRefuse) {
        this(dialogRefuse, dialogRefuse.getWindow().getDecorView());
    }

    @UiThread
    public DialogRefuse_ViewBinding(final DialogRefuse dialogRefuse, View view) {
        this.target = dialogRefuse;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onViewClicked'");
        dialogRefuse.mIvClose = (ImageView) Utils.castView(findRequiredView, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogRefuse_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogRefuse.onViewClicked(view2);
            }
        });
        dialogRefuse.mEtRefuse = (EditText) Utils.findRequiredViewAsType(view, R.id.et_refuse, "field 'mEtRefuse'", EditText.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_submit_ok, "field 'mBtnOk' and method 'onViewClicked'");
        dialogRefuse.mBtnOk = (Button) Utils.castView(findRequiredView2, R.id.btn_submit_ok, "field 'mBtnOk'", Button.class);
        this.view2131361863 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogRefuse_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogRefuse.onViewClicked(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogRefuse dialogRefuse = this.target;
        if (dialogRefuse == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogRefuse.mIvClose = null;
        dialogRefuse.mEtRefuse = null;
        dialogRefuse.mBtnOk = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
        this.view2131361863.setOnClickListener(null);
        this.view2131361863 = null;
    }
}
