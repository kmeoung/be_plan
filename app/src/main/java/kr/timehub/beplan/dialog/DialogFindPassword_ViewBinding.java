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
import kr.timehub.beplan.base.widgets.EditText;

/* loaded from: classes.dex */
public class DialogFindPassword_ViewBinding implements Unbinder {
    private DialogFindPassword target;
    private View view2131361855;
    private View view2131361954;

    @UiThread
    public DialogFindPassword_ViewBinding(DialogFindPassword dialogFindPassword) {
        this(dialogFindPassword, dialogFindPassword.getWindow().getDecorView());
    }

    @UiThread
    public DialogFindPassword_ViewBinding(final DialogFindPassword dialogFindPassword, View view) {
        this.target = dialogFindPassword;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onClick'");
        dialogFindPassword.mIvClose = (ImageView) Utils.castView(findRequiredView, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogFindPassword_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogFindPassword.onClick(view2);
            }
        });
        dialogFindPassword.mEtEmail = (EditText) Utils.findRequiredViewAsType(view, R.id.et_email, "field 'mEtEmail'", EditText.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_ok, "field 'mBtnOk' and method 'onClick'");
        dialogFindPassword.mBtnOk = (Button) Utils.castView(findRequiredView2, R.id.btn_ok, "field 'mBtnOk'", Button.class);
        this.view2131361855 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogFindPassword_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogFindPassword.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogFindPassword dialogFindPassword = this.target;
        if (dialogFindPassword == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogFindPassword.mIvClose = null;
        dialogFindPassword.mEtEmail = null;
        dialogFindPassword.mBtnOk = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
        this.view2131361855.setOnClickListener(null);
        this.view2131361855 = null;
    }
}
