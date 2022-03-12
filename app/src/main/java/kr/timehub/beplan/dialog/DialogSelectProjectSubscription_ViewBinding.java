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
public class DialogSelectProjectSubscription_ViewBinding implements Unbinder {
    private DialogSelectProjectSubscription target;
    private View view2131361954;
    private View view2131361984;

    @UiThread
    public DialogSelectProjectSubscription_ViewBinding(DialogSelectProjectSubscription dialogSelectProjectSubscription) {
        this(dialogSelectProjectSubscription, dialogSelectProjectSubscription.getWindow().getDecorView());
    }

    @UiThread
    public DialogSelectProjectSubscription_ViewBinding(final DialogSelectProjectSubscription dialogSelectProjectSubscription, View view) {
        this.target = dialogSelectProjectSubscription;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onViewClicked'");
        dialogSelectProjectSubscription.mIvClose = (ImageView) Utils.castView(findRequiredView, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogSelectProjectSubscription_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogSelectProjectSubscription.onViewClicked(view2);
            }
        });
        dialogSelectProjectSubscription.mTvVersion = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_version, "field 'mTvVersion'", TextView.class);
        dialogSelectProjectSubscription.mLlVersion = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_version, "field 'mLlVersion'", LinearLayout.class);
        dialogSelectProjectSubscription.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        dialogSelectProjectSubscription.mSpDropdown = (Spinner) Utils.findRequiredViewAsType(view, R.id.sp_dropdown, "field 'mSpDropdown'", Spinner.class);
        dialogSelectProjectSubscription.mRlBg = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_bg, "field 'mRlBg'", RelativeLayout.class);
        View findRequiredView2 = Utils.findRequiredView(view, R.id.iv_ok, "field 'mIvOk' and method 'onViewClicked'");
        dialogSelectProjectSubscription.mIvOk = (ImageView) Utils.castView(findRequiredView2, R.id.iv_ok, "field 'mIvOk'", ImageView.class);
        this.view2131361984 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogSelectProjectSubscription_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogSelectProjectSubscription.onViewClicked(view2);
            }
        });
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogSelectProjectSubscription dialogSelectProjectSubscription = this.target;
        if (dialogSelectProjectSubscription == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogSelectProjectSubscription.mIvClose = null;
        dialogSelectProjectSubscription.mTvVersion = null;
        dialogSelectProjectSubscription.mLlVersion = null;
        dialogSelectProjectSubscription.mTvTitle = null;
        dialogSelectProjectSubscription.mSpDropdown = null;
        dialogSelectProjectSubscription.mRlBg = null;
        dialogSelectProjectSubscription.mIvOk = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
        this.view2131361984.setOnClickListener(null);
        this.view2131361984 = null;
    }
}
