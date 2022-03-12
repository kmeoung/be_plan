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
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogUrlLink_ViewBinding implements Unbinder {
    private DialogUrlLink target;
    private View view2131361844;
    private View view2131361849;
    private View view2131361954;
    private View view2131362231;

    @UiThread
    public DialogUrlLink_ViewBinding(DialogUrlLink dialogUrlLink) {
        this(dialogUrlLink, dialogUrlLink.getWindow().getDecorView());
    }

    @UiThread
    public DialogUrlLink_ViewBinding(final DialogUrlLink dialogUrlLink, View view) {
        this.target = dialogUrlLink;
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onViewClicked'");
        dialogUrlLink.mIvClose = (ImageView) Utils.castView(findRequiredView, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogUrlLink_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogUrlLink.onViewClicked(view2);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.tv_preview, "field 'mTvPreview' and method 'onViewClicked'");
        dialogUrlLink.mTvPreview = (TextView) Utils.castView(findRequiredView2, R.id.tv_preview, "field 'mTvPreview'", TextView.class);
        this.view2131362231 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogUrlLink_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogUrlLink.onViewClicked(view2);
            }
        });
        dialogUrlLink.mIvPreview = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_preview, "field 'mIvPreview'", ImageView.class);
        dialogUrlLink.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        dialogUrlLink.mTvSubTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_sub_title, "field 'mTvSubTitle'", TextView.class);
        View findRequiredView3 = Utils.findRequiredView(view, R.id.btn_add, "field 'mBtnAdd' and method 'onViewClicked'");
        dialogUrlLink.mBtnAdd = (Button) Utils.castView(findRequiredView3, R.id.btn_add, "field 'mBtnAdd'", Button.class);
        this.view2131361844 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogUrlLink_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogUrlLink.onViewClicked(view2);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, R.id.btn_close, "field 'mBtnClose' and method 'onViewClicked'");
        dialogUrlLink.mBtnClose = (Button) Utils.castView(findRequiredView4, R.id.btn_close, "field 'mBtnClose'", Button.class);
        this.view2131361849 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogUrlLink_ViewBinding.4
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogUrlLink.onViewClicked(view2);
            }
        });
        dialogUrlLink.mEtUrl = (EditText) Utils.findRequiredViewAsType(view, R.id.et_url, "field 'mEtUrl'", EditText.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogUrlLink dialogUrlLink = this.target;
        if (dialogUrlLink == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogUrlLink.mIvClose = null;
        dialogUrlLink.mTvPreview = null;
        dialogUrlLink.mIvPreview = null;
        dialogUrlLink.mTvTitle = null;
        dialogUrlLink.mTvSubTitle = null;
        dialogUrlLink.mBtnAdd = null;
        dialogUrlLink.mBtnClose = null;
        dialogUrlLink.mEtUrl = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
        this.view2131362231.setOnClickListener(null);
        this.view2131362231 = null;
        this.view2131361844.setOnClickListener(null);
        this.view2131361844 = null;
        this.view2131361849.setOnClickListener(null);
        this.view2131361849 = null;
    }
}
