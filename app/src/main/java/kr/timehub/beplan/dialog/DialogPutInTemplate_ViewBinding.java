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
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogPutInTemplate_ViewBinding implements Unbinder {
    private DialogPutInTemplate target;
    private View view2131361954;
    private View view2131361984;

    @UiThread
    public DialogPutInTemplate_ViewBinding(DialogPutInTemplate dialogPutInTemplate) {
        this(dialogPutInTemplate, dialogPutInTemplate.getWindow().getDecorView());
    }

    @UiThread
    public DialogPutInTemplate_ViewBinding(final DialogPutInTemplate dialogPutInTemplate, View view) {
        this.target = dialogPutInTemplate;
        dialogPutInTemplate.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onViewClicked'");
        dialogPutInTemplate.mIvClose = (ImageView) Utils.castView(findRequiredView, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogPutInTemplate_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogPutInTemplate.onViewClicked(view2);
            }
        });
        dialogPutInTemplate.mTvVersion = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_version, "field 'mTvVersion'", TextView.class);
        dialogPutInTemplate.mLlVersion = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_version, "field 'mLlVersion'", LinearLayout.class);
        dialogPutInTemplate.mLlTitle = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_title, "field 'mLlTitle'", LinearLayout.class);
        dialogPutInTemplate.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        dialogPutInTemplate.mRlBg = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_bg, "field 'mRlBg'", RelativeLayout.class);
        dialogPutInTemplate.mEtInputTitle = (EditText) Utils.findRequiredViewAsType(view, R.id.et_input_title, "field 'mEtInputTitle'", EditText.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.iv_ok, "field 'mIvOk' and method 'onViewClicked'");
        dialogPutInTemplate.mIvOk = (ImageView) Utils.castView(findRequiredView2, R.id.iv_ok, "field 'mIvOk'", ImageView.class);
        this.view2131361984 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogPutInTemplate_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogPutInTemplate.onViewClicked(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogPutInTemplate dialogPutInTemplate = this.target;
        if (dialogPutInTemplate == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogPutInTemplate.mTvTitle = null;
        dialogPutInTemplate.mIvClose = null;
        dialogPutInTemplate.mTvVersion = null;
        dialogPutInTemplate.mLlVersion = null;
        dialogPutInTemplate.mLlTitle = null;
        dialogPutInTemplate.mSpDropdown = null;
        dialogPutInTemplate.mRlBg = null;
        dialogPutInTemplate.mEtInputTitle = null;
        dialogPutInTemplate.mIvOk = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
        this.view2131361984.setOnClickListener(null);
        this.view2131361984 = null;
    }
}
