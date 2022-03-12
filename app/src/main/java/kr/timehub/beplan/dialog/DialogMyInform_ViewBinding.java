package kr.timehub.beplan.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogMyInform_ViewBinding implements Unbinder {
    private DialogMyInform target;
    private View view2131361849;
    private View view2131361987;
    private View view2131362046;

    @UiThread
    public DialogMyInform_ViewBinding(DialogMyInform dialogMyInform) {
        this(dialogMyInform, dialogMyInform.getWindow().getDecorView());
    }

    @UiThread
    public DialogMyInform_ViewBinding(final DialogMyInform dialogMyInform, View view) {
        this.target = dialogMyInform;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_photo, "field 'mIvPhoto' and method 'onClick'");
        dialogMyInform.mIvPhoto = (ImageView) Utils.castView(findRequiredView, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
        this.view2131361987 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogMyInform_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogMyInform.onClick(view2);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.btn_close, "field 'mBtnClose' and method 'onClick'");
        dialogMyInform.mBtnClose = (ImageButton) Utils.castView(findRequiredView2, R.id.btn_close, "field 'mBtnClose'", ImageButton.class);
        this.view2131361849 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogMyInform_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogMyInform.onClick(view2);
            }
        });
        dialogMyInform.mEtName = (EditText) Utils.findRequiredViewAsType(view, R.id.et_name, "field 'mEtName'", EditText.class);
        dialogMyInform.mTvEmail = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_email, "field 'mTvEmail'", TextView.class);
        View findRequiredView3 = Utils.findRequiredView(view, R.id.ll_save, "field 'mLlSave' and method 'onClick'");
        dialogMyInform.mLlSave = (LinearLayout) Utils.castView(findRequiredView3, R.id.ll_save, "field 'mLlSave'", LinearLayout.class);
        this.view2131362046 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogMyInform_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogMyInform.onClick(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogMyInform dialogMyInform = this.target;
        if (dialogMyInform == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogMyInform.mIvPhoto = null;
        dialogMyInform.mBtnClose = null;
        dialogMyInform.mEtName = null;
        dialogMyInform.mTvEmail = null;
        dialogMyInform.mLlSave = null;
        this.view2131361987.setOnClickListener(null);
        this.view2131361987 = null;
        this.view2131361849.setOnClickListener(null);
        this.view2131361849 = null;
        this.view2131362046.setOnClickListener(null);
        this.view2131362046 = null;
    }
}
